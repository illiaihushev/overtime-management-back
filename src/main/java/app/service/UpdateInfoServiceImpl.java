package app.service;

import app.cache.ProjectsCache;
import app.cache.UsersCache;
import app.model.ProjectDAO;
import app.model.UserDAO;
import app.repo.ProjectRepo;
import app.repo.UserRepo;
import app.web.dto.InfoToUpdateTitlesDTO;
import app.web.dto.ProjectDTO;
import app.web.dto.RelatedInfoDTO;
import app.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

import static app.service.GenericServiceAbstr.extractNaturalId;
import static app.service.GenericServiceAbstr.partitionByRelevantness;
import static app.service.GenericServiceAbstr.toMapByNaturalId;
import static app.util.CollectionUtil.concatToList;
import static java.util.stream.Collectors.toList;

@Service
public class UpdateInfoServiceImpl implements UpdateInfoService {
    private final UserRepo userRepo;
    private final ProjectRepo projectRepo;
    private final UsersCache usersCache;
    private final ProjectsCache projectsCache;
    private final UserService userService;
    private final ProjectService projectService;

    private Map<String, UserDAO> usersInitiatorsIrrCache;
    private Map<String, UserDAO> usersInitiatorsRelCache;
    private Map<String, ProjectDAO> projectsInitiatorsIrrCache;
    private Map<String, ProjectDAO> projectsInitiatorsRelCache;

    public static final int UPDATE_INTERVAL = 1;

    private Timestamp updateTime;
    private Timestamp lastRelevantTime;

    private String initiator;

    @Autowired
    public UpdateInfoServiceImpl(UserRepo repo, ProjectRepo projectRepo,
                                 UsersCache cache, ProjectsCache projectsCache,
                                 UserService userService, ProjectService projectService) {
        this.userRepo = repo;
        this.projectRepo = projectRepo;
        this.usersCache = cache;
        this.projectsCache = projectsCache;
        this.userService = userService;
        this.projectService = projectService;
    }

    @Override
    public InfoToUpdateTitlesDTO getIrrelevantFrom(InfoToUpdateTitlesDTO titles) {

        setUpdateTime(new Timestamp(System.currentTimeMillis()));
        this.initiator = titles.getInitiator();

        this.usersInitiatorsIrrCache = usersCache.getIrrelevant(initiator);
        this.usersInitiatorsRelCache = usersCache.getRelevant(initiator);
        this.projectsInitiatorsIrrCache = projectsCache.getIrrelevant(initiator);
        this.projectsInitiatorsRelCache = projectsCache.getRelevant(initiator);

        Collection<String> empsLogins = titles.getEmployees();
        Collection<String> directsLogins = titles.getDirectManagers();
        Collection<String> linesLogins = titles.getLineManagers();
        Collection<String> pmosLogins = titles.getPMOs();
        Collection<String> projectsNames = titles.getProjects();

        Map<Boolean, List<UserDAO>> directsByRelevantness = findUsersByRelevantness(directsLogins);
        Map<Boolean, List<UserDAO>> linesByRelevantness = findUsersByRelevantness(linesLogins);
        Map<Boolean, List<UserDAO>> pmosByRelevantness = findUsersByRelevantness(pmosLogins);
        Map<Boolean, List<UserDAO>> empsByRelevantness = findUsersByRelevantness(empsLogins);
        Map<Boolean, List<ProjectDAO>> projectsByRelevantness = findProjectsByRelevantness(projectsNames);

        //caching relevant
        List<UserDAO> relevantDirects = directsByRelevantness.get(true);
        List<UserDAO> relevantLines = linesByRelevantness.get(true);
        List<UserDAO> relevantPmos = pmosByRelevantness.get(true);
        List<UserDAO> relevantEmps = empsByRelevantness.get(true);
        List<ProjectDAO> relevantProjects = projectsByRelevantness.get(true);


        List<UserDAO> relevantUsers = concatToList(
                relevantEmps, relevantDirects, relevantLines, relevantPmos);

        usersInitiatorsRelCache.putAll(
                toMapByNaturalId(relevantUsers));

        projectsInitiatorsRelCache.putAll(
                toMapByNaturalId(relevantProjects));

        //caching irrelevant
        List<UserDAO> irrelevantUsers = concatToList(
                empsByRelevantness.get(false), directsByRelevantness.get(false), linesByRelevantness.get(false), pmosByRelevantness.get(false));

        List<ProjectDAO> irrelevantProjects = projectsByRelevantness.get(false);

        usersInitiatorsIrrCache.putAll(
                toMapByNaturalId(irrelevantUsers));
        projectsInitiatorsIrrCache.putAll(
                toMapByNaturalId(irrelevantProjects));

        directsLogins.removeAll(extractNaturalId(relevantDirects));
        linesLogins.removeAll(extractNaturalId(relevantLines));
        pmosLogins.removeAll(extractNaturalId(relevantPmos));
        empsLogins.removeAll(extractNaturalId(relevantEmps));
        projectsNames.removeAll(extractNaturalId(relevantProjects));


        return new InfoToUpdateTitlesDTO(empsLogins, directsLogins, linesLogins, pmosLogins, projectsNames);
    }

    private Map<Boolean, List<ProjectDAO>> findProjectsByRelevantness(Collection<String> fromNames) {
        return partitionByRelevantness(projectRepo.findByNameIn(fromNames), getLastRelevantTime());
    }

    private Map<Boolean, List<UserDAO>> findUsersByRelevantness(Collection<String> fromLogins) {
        return partitionByRelevantness(userRepo.findByUsernameIn(fromLogins), getLastRelevantTime());
    }


    @Override
    @Transactional
    public void saveExistingAppData(RelatedInfoDTO data) {

        List<UserDTO> highManagers = new ArrayList<>(data.getPmos());
        highManagers.addAll(data.getLines());

        usersInitiatorsRelCache.putAll(toMapByNaturalId(saveAll(highManagers)));

        projectsInitiatorsRelCache.putAll(toMapByNaturalId(saveProjects(data.getProjects())));

        Map<String, UserDAO> directs = toMapByNaturalId(saveAll(data.getDirects()));

        usersInitiatorsRelCache.putAll(directs);

        Map<String, UserDAO> emps = toMapByNaturalId(saveAll(data.getEmployees()));

        saveUsersProjectsRelations(directs, data.getDirects());
        saveUsersProjectsRelations(emps, data.getEmployees());

    }

    @Transactional
    public void saveUsersProjectsRelations(Map<String, UserDAO> daos, List<UserDTO> dtos) {
        for (UserDTO dto : dtos) {
            UserDAO dao = daos.get(dto.getUsername());
            Collection<String> projects = dto.getProjects();
            if (projects != null) {
                for (String project : projects) {
                    userRepo.save(dao.getId(), projectsInitiatorsRelCache.get(project).getId());
                }
            }
        }
    }


    @Transactional
    public List<UserDAO> saveAll(Collection<UserDTO> users) {
        return userService.saveAll(users, getUpdateTime());
    }


    @Transactional
    public List<ProjectDAO> saveProjects(Collection<ProjectDTO> projects) {
        List<ProjectDAO> prjcts =  projectService.saveAll(projects, getUpdateTime());
        return prjcts.stream().peek(dao -> dao.setUsers(new ArrayList<>())).collect(toList());
    }

    private Timestamp getUpdateTime() {
        return updateTime;
    }

    private Timestamp getLastRelevantTime() {
        return this.lastRelevantTime;
    }

    private void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(updateTime.getTime());
        calendar.add(Calendar.DAY_OF_YEAR, -UPDATE_INTERVAL);
        this.lastRelevantTime = new Timestamp(calendar.getTimeInMillis());
    }

}
