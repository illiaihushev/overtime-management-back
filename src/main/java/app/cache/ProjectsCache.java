package app.cache;

import app.model.ProjectDAO;
import org.springframework.stereotype.Component;

@Component
public class ProjectsCache extends AuthCache<ProjectDAO, String> {

}
