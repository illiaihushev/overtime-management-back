package app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class PreAuthInfoService {
    private UserService userService;
    private ProjectService projectService;

    private String initiator;


    public String getInitiator() {
        return initiator;
    }

    public void updateInitator(String initiator) {
        this.initiator = initiator;
        userService.initCache();
        projectService.initCache();
    }

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
