package app.service;

import app.model.ProjectDAO;
import app.web.dto.ProjectDTO;

import java.util.Collection;
import java.util.Set;

public interface ProjectService extends  GenericService<String, ProjectDAO, ProjectDTO> {
    void setUserService(UserService userService);

    Set<ProjectDAO> getCachedProjects(Collection<String> names);
}
