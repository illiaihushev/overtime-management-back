package app.service;

import app.model.UserDAO;
import app.web.dto.UserDTO;

public interface UserService extends GenericService<String, UserDAO, UserDTO> {
    UserDAO getCachedUser(String username);
}
