package app.cache;

import app.model.UserDAO;
import org.springframework.stereotype.Component;

@Component
public class UsersCache extends AuthCache<UserDAO, String> {

}
