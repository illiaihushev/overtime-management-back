package app.service;

import app.cache.AuthCache;
import app.cache.UsersCache;
import app.model.UserDAO;
import app.model.VacationDAO;
import app.repo.UserRepo;
import app.web.dto.UserDTO;
import app.web.dto.VacationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class UserServiceImpl extends GenericServiceAbstr<String, UserDAO, UserDTO> implements UserService {
    private final UserRepo userRepo;
    private final UsersCache usersCache;
    private PreAuthInfoService infoService;
    private final ProjectService projectService;

    private Map<String, UserDAO> usersInitiatorsIrrCache;
    private Map<String, UserDAO> usersInitiatorsRelCache;



    @Autowired
    public UserServiceImpl(UsersCache usersCache, PreAuthInfoService infoService,
                           UserRepo userRepo, ProjectService projectService) {
        this.usersCache = usersCache;
        this.infoService = infoService;

        this.projectService = projectService;
        this.userRepo = userRepo;

    }

    @Override
    @PostConstruct
    public void initWithMyself() {
        this.infoService.setUserService(this);
        this.projectService.setUserService(this);
    }

    @Override
    public UserDAO makeRelevant(UserDTO dto, Timestamp timestamp) {
        UserDAO irr = usersInitiatorsIrrCache.get(dto.getUsername());

        irr.setName(dto.getName());
        irr.setManager(getCachedUser(dto.getManager()));
        irr.setProjects(null);
        irr.setVacations(createVacations(irr, dto.getVacations()));
        irr.setPositionId(dto.getPosition());
        irr.setLastUpdate(timestamp);

        return irr;
    }

    @Override
    public UserDAO createNew(UserDTO dto, Timestamp updateTime) {
        UserDAO dao = new UserDAO(
                dto.getUsername(),
                dto.getName(),
                getCachedUser(dto.getManager()),
                null,
                null,
                dto.getPosition(),
                updateTime
        );
        dao.setVacations(createVacations(dao, dto.getVacations()));
        return dao;
    }

    private List<VacationDAO> createVacations(UserDAO vacationist,Collection<VacationDTO> vacations) {
        return vacations.stream().map(dto -> new VacationDAO(vacationist, dto)).collect(toList());
    }


    @Override
    public UserDAO getCachedUser(String username) {
        return usersInitiatorsRelCache.get(username);
    }

    public boolean isIrrelevant(String naturalId) {
        return usersInitiatorsIrrCache.get(naturalId) != null;
    }

    public UserRepo getRepo() {
        return userRepo;
    }

    @Override
    public void initCache() {
        String initiator = infoService.getInitiator();

        this.usersInitiatorsIrrCache = usersCache.getIrrelevant(initiator);
        this.usersInitiatorsRelCache = usersCache.getRelevant(initiator);
    }


}
