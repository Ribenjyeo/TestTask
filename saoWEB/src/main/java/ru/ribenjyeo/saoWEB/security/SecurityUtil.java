package ru.ribenjyeo.saoWEB.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.ribenjyeo.saoWEB.security.service.UserDetailsImpl;

import java.util.Optional;

public class SecurityUtil {

    public static Optional<UserDetailsImpl> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext ();
        return Optional.ofNullable (securityContext.getAuthentication ())
                .map (authentication -> {
                    if (authentication.getPrincipal () instanceof UserDetailsImpl) {
                        return (UserDetailsImpl) authentication.getPrincipal ();
                    }
                    return null;
                });
    }
}
