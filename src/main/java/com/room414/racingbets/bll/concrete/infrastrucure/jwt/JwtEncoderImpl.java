package com.room414.racingbets.bll.concrete.infrastrucure.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.room414.racingbets.bll.abstraction.exceptions.BllException;
import com.room414.racingbets.bll.abstraction.infrastructure.jwt.Jwt;
import com.room414.racingbets.bll.abstraction.infrastructure.jwt.JwtEncoder;
import com.room414.racingbets.dal.domain.enums.Role;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.room414.racingbets.bll.concrete.infrastrucure.ErrorMessageUtil.defaultErrorMessage;

/**
 * @author Alexander Melashchenko
 * @version 1.0 21 Mar 2017
 */
public class JwtEncoderImpl implements JwtEncoder {
    private String secret;

    public JwtEncoderImpl(String secret) {
        this.secret = secret;
    }

    private static List<String> toList(Collection<Role> roles) {
        return roles
                .stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }

    private String getHeader(Jwt jwt) {
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode header = mapper.createObjectNode();
        header.put("alg", jwt.getAlgorithm());
        header.put("typ", jwt.getType());

        return Base64.encodeBase64URLSafeString(header.toString().getBytes());
    }

    private String getPayload(Jwt jwt) {
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode payload = mapper.createObjectNode();
        payload.put("exp", jwt.getExpire());
        payload.put("sub", jwt.getUserId());
        payload.put("eml", jwt.getEmail());

        ArrayNode roles = mapper.createArrayNode();
        toList(jwt.getRoles()).forEach(roles::add);

        payload.set("rol", roles);

        return Base64.encodeBase64URLSafeString(payload.toString().getBytes());
    }

    @Override
    public String generateSignature(Jwt jwt) {
        String header = getHeader(jwt);
        String payload = getPayload(jwt);

        return generateSignature(jwt, header, payload);
    }

    private String generateSignature(Jwt jwt, String header, String payload) {
        try {
            String algorithm = jwt.getAlgorithm();
            Mac mac = Mac.getInstance(algorithm);
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), algorithm);
            mac.init(secretKey);

            return new String(mac.doFinal((header + "." + payload).getBytes()));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            String message = defaultErrorMessage("generateSignature", jwt, header, payload);
            throw new BllException(message, e);
        }
    }

    @Override
    public String encode(Jwt jwt) {
        return getHeader(jwt) + "." + getPayload(jwt) + "." + jwt.getSignature();
    }
}
