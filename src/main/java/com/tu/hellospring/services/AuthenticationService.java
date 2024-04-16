package com.tu.hellospring.services;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.tu.hellospring.dtos.requests.AuthenticationRequestDTO;
import com.tu.hellospring.dtos.requests.IntrospectRequestDTO;
import com.tu.hellospring.dtos.respones.AuthenticationResponseDTO;
import com.tu.hellospring.dtos.respones.IntrospectResponseDTO;
import com.tu.hellospring.entities.User;
import com.tu.hellospring.exceptions.AppException;
import com.tu.hellospring.exceptions.ErrorCode;
import com.tu.hellospring.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthenticationService {

    UserRepository userRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        var passwordEncoder = new BCryptPasswordEncoder(10);
        boolean matches = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!matches) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String token = generateToken(user);
        return AuthenticationResponseDTO.builder()
                .token(token)
                .build();
    }

    @SneakyThrows
    public IntrospectResponseDTO introspect(IntrospectRequestDTO request) {
        String token = request.getToken();

        var verifier = new MACVerifier(SIGNER_KEY.getBytes());

        var signedJWT = SignedJWT.parse(token);

        var expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        boolean verified = signedJWT.verify(verifier);

        boolean isValid = verified && expirationTime.after(new Date());

        return IntrospectResponseDTO.builder()
                .isValid(isValid)
                .build();
    }

    @SneakyThrows
    private String generateToken(User user) {
        // header of JWT
        var header = new JWSHeader(JWSAlgorithm.HS512);

        // claim
        var jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("ntuvu.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("scope", buildScope(user))
                .build();
        // payload
        var payload = new Payload(jwtClaimsSet.toJSONObject());

        var jwsObject = new JWSObject(header, payload);

        jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));

        return jwsObject.serialize();
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
                if (!CollectionUtils.isEmpty(role.getPermissions())) {
                    role.getPermissions().forEach(permission -> {
                        stringJoiner.add(permission.getName());
                    });
                }
            });
        }
        return stringJoiner.toString();
    }
}
