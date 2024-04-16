package com.tu.hellospring.configurations;

import com.tu.hellospring.dtos.requests.IntrospectRequestDTO;
import com.tu.hellospring.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;

@Component
@RequiredArgsConstructor
public class CustomJwtDecoder implements JwtDecoder {

    private final AuthenticationService authenticationService;

    @Value("${jwt.signerKey}")
    private String SIGNER_KEY;

    @Override
    @SneakyThrows
    public Jwt decode(String token) throws JwtException {
        var introspect = authenticationService.introspect(IntrospectRequestDTO.builder()
                .token(token)
                .build());

        if (!introspect.isValid()) {
            throw new JwtException("Token invalid");
        }

        SecretKeySpec secretKeySpec = new SecretKeySpec(SIGNER_KEY.getBytes(), "HS512");
        var jwtDecoder = NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();

        return jwtDecoder.decode(token);
    }
}
