package com.room414.racingbets.bll.concrete.infrastrucure.jwt;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.room414.racingbets.bll.abstraction.exceptions.BllException;
import com.room414.racingbets.bll.abstraction.infrastructure.jwt.Jwt;
import com.room414.racingbets.bll.abstraction.infrastructure.jwt.JwtDecoder;
import com.room414.racingbets.dal.domain.enums.Role;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

import static com.room414.racingbets.bll.concrete.infrastrucure.ErrorMessageUtil.defaultErrorMessage;

/**
 * @author Alexander Melashchenko
 * @version 1.0 21 Mar 2017
 */
public class JwtDecoderImpl implements JwtDecoder {
    private Log log = LogFactory.getLog(JwtDecoderImpl.class);

    private Collection<Role> asCollection(JsonNode nodes) {
        Collection<Role> roles = new HashSet<>();
        if (nodes.isArray()) {
            for (JsonNode node: nodes) {
                roles.add(Role.getRole(node.asText()));
            }
        }
        return roles;
    }

    @Override
    public Jwt decode(String token) {
        try {
            String[] parts = token.split("\\.");
            ObjectMapper mapper = new ObjectMapper();
            JsonNode header = mapper.readTree(Base64.decodeBase64(parts[0]));
            JsonNode payload = mapper.readTree(Base64.decodeBase64(parts[1]));
            String signature = parts[2];

            return new JwtBuilderImpl()
                    .setType(header.get("typ").asText())
                    .setAlgorithm(header.get("alg").asText())
                    .setExpire(payload.get("exp").asLong())
                    .setUserId(payload.get("sub").asLong())
                    .setEmail(payload.get("eml").asText())
                    .setRoles(asCollection(payload.get("rol")))
                    .setSignature(signature)
                    .build();
        } catch (IOException e) {
            String message = defaultErrorMessage("decode", token);
            log.error(message, e);
            throw new BllException(message, e);
        }
    }
}
