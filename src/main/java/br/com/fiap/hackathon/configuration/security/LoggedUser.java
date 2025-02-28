package br.com.fiap.hackathon.configuration.security;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;

public class LoggedUser {

    public static JwtAuthenticationToken get() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(JwtAuthenticationToken.class::isInstance)
                .map(a -> (JwtAuthenticationToken) a)
                .orElseThrow(() -> new RuntimeException("Usuário não autenticado ou tipo inválido de Authentication."));
    }

}
