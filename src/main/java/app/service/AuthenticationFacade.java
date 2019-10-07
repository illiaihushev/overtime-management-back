package app.service;

import org.springframework.security.core.Authentication;

public interface AuthenticationFacade {
    Authentication getAuth();
}
