package com.room414.racingbets.bll.concrete.infrastrucure.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.room414.racingbets.bll.abstraction.infrastructure.jwt.Jwt;
import com.room414.racingbets.dal.domain.enums.Role;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.codec.binary.Base64;


/**
 * @author Alexander Melashchenko
 * @version 1.0 21 Mar 2017
 */
public class JwtUtil {
    private JwtUtil() {

    }

    private static List<String> toList(Collection<Role> roles) {
        return roles
                .stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }

    static String getHeader(Jwt jwt) {
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode header = mapper.createObjectNode();
        header.put("alg", jwt.getAlgorithm());
        header.put("typ", jwt.getType());

        return Base64.encodeBase64String(header.toString().getBytes());
    }

    static String getPayload(Jwt jwt) {
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode payload = mapper.createObjectNode();
        payload.put("exp", jwt.getExpire());
        payload.put("sub", jwt.getUserId());
        payload.put("eml", jwt.getEmail());

        ArrayNode roles = mapper.createArrayNode();
        toList(jwt.getRoles()).forEach(roles::add);

        payload.set("roles", roles);

        return Base64.encodeBase64String(payload.toString().getBytes());
    }

    public static String generateSignature(Jwt jwt, String secret) throws NoSuchAlgorithmException, InvalidKeyException {
        String algorithm = jwt.getAlgorithm();
        Mac mac = Mac.getInstance(algorithm);
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), algorithm);
        mac.init(secretKey);

        String header = getHeader(jwt);
        String payload = getPayload(jwt);

        return Arrays.toString(mac.doFinal((header + "." + payload).getBytes()));
    }
}
