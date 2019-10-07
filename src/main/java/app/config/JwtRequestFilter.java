package app.config;

import app.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        if (request.getMethod().equals("OPTIONS")) {
            chain.doFilter(request, response);
            return;
        }

        final String requestTokenAuthHeader = request.getHeader("Authorization");

        String username;
        String jwtToken;
        if (requestTokenAuthHeader != null) {
            if (requestTokenAuthHeader.startsWith("Bearer ")) {
                jwtToken = requestTokenAuthHeader.substring(7);
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } else {
                throw new AuthenticationException("Authorization header does not begin with \"Bearer \"");
            }
        } else {
            throw new AuthenticationException("Authorization header is missing");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (username != null && auth == null) {
            if (jwtUserDetailsService.isUserPersisted(username)) {
                UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

                if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            } else {
                logger.info("First log in of: " + username);
            }
        }
        chain.doFilter(request, response);
    }
}