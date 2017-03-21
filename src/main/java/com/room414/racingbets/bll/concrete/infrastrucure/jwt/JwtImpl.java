package com.room414.racingbets.bll.concrete.infrastrucure.jwt;

import com.room414.racingbets.bll.abstraction.infrastructure.jwt.Jwt;
import com.room414.racingbets.dal.domain.enums.Role;

import java.util.Collection;

/**
 * @author Alexander Melashchenko
 * @version 1.0 21 Mar 2017
 */
public class JwtImpl implements Jwt {
    private String type;
    private String algorithm;
    private long expire;
    private long id;
    private String email;
    private Collection<Role> roles;
    private String signature;

    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    @Override
    public long getUserId() {
        return id;
    }

    public void setUserId(long id) {
        this.id = id;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean isInRole(Role role) {
        return roles.contains(role);
    }

    @Override
    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JwtImpl jwt = (JwtImpl) o;

        if (expire != jwt.expire) return false;
        if (id != jwt.id) return false;
        if (type != null ? !type.equals(jwt.type) : jwt.type != null) return false;
        if (algorithm != null ? !algorithm.equals(jwt.algorithm) : jwt.algorithm != null) return false;
        if (email != null ? !email.equals(jwt.email) : jwt.email != null) return false;
        if (roles != null ? !roles.equals(jwt.roles) : jwt.roles != null) return false;
        return signature != null ? signature.equals(jwt.signature) : jwt.signature == null;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (algorithm != null ? algorithm.hashCode() : 0);
        result = 31 * result + (int) (expire ^ (expire >>> 32));
        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        result = 31 * result + (signature != null ? signature.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "JwtImpl{" +
                "type='" + type + '\'' +
                ", algorithm='" + algorithm + '\'' +
                ", expire=" + expire +
                ", id=" + id +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                ", signature='" + signature + '\'' +
                '}';
    }
}
