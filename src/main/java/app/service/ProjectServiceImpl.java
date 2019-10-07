package app.service;

import app.cache.ProjectsCache;
import app.model.ProjectDAO;
import app.repo.ProjectRepo;
import app.web.dto.ProjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.*;

import static java.util.stream.Collectors.toSet;

@Service
public class ProjectServiceImpl extends GenericServiceAbstr<String, ProjectDAO, ProjectDTO> implements ProjectService {
    private final ProjectRepo projectRepo;
    private final ProjectsCache projectsCache;
    private final PreAuthInfoService infoService;
    private UserService userService;

    private Map<String, ProjectDAO> projectsInitiatorsIrrCache;
    private Map<String, ProjectDAO> projectsInitiatorsRelCache;

    @Autowired
    public ProjectServiceImpl(ProjectsCache projectsCache, PreAuthInfoService infoService,
                              ProjectRepo projectRepo) {

        this.projectsCache = projectsCache;
        this.infoService = infoService;
        this.projectRepo = projectRepo;
    }


    @Override
    public ProjectDAO makeRelevant(ProjectDTO dto, Timestamp timestamp) {
        ProjectDAO irr = projectsInitiatorsIrrCache.get(dto.getName());

        irr.setPmo(userService.getCachedUser(dto.getPmo()));
        irr.setLastUpdate(timestamp);

        return irr;
    }

    @Override
    public ProjectDAO createNew(ProjectDTO dto, Timestamp updateTime) {
        return new ProjectDAO(
                dto.getName(),
                userService.getCachedUser(dto.getPmo()),
                updateTime);
    }

    public Set<ProjectDAO> getCachedProjects(Collection<String> projects) {
        if (projects == null) return new HashSet<>();
        return projects.stream().map(projectsInitiatorsRelCache::get).collect(toSet());
    }

    @Override
    public boolean isIrrelevant(String naturalId) {
        return projectsInitiatorsIrrCache.get(naturalId) != null;
    }

    @Override
    public CrudRepository<ProjectDAO, ?> getRepo() {
        return projectRepo;
    }

    @Override
    public void initCache() {
        String initiator = infoService.getInitiator();

        this.projectsInitiatorsIrrCache = projectsCache.getIrrelevant(initiator);
        this.projectsInitiatorsRelCache = projectsCache.getRelevant(initiator);
    }

    @Override
    @PostConstruct
    public void initWithMyself() {
        infoService.setProjectService(this);
    }

    @Override
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
