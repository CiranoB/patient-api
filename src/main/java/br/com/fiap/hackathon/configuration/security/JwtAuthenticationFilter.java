package br.com.fiap.hackathon.configuration.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${api.security.token.secret}")
    private String secret;

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain chain) throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (token != null && token.startsWith("Bearer ")) {
            try {
                token = token.replace("Bearer ", "");

                final var algorithm = Algorithm.HMAC256(secret);
                final var verifier = JWT.require(algorithm).build();
                final var decodedJWT = verifier.verify(token);

                final var authorities = decodedJWT.getClaim("roles").asList(String.class)
                        .stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).toList();
                
                final var identifier = decodedJWT.getClaim("identifier").asString();

                final var userDetails = new User(decodedJWT.getSubject(), "", authorities);
                final var authentication = new JwtAuthenticationToken(userDetails, token, authorities, identifier);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JWTVerificationException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido ou expirado");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
