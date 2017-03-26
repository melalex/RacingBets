package com.room414.racingbets.web.model.viewmodels;

import java.io.Serializable;

/**
 * @author Alexander Melashchenko
 * @version 1.0 26 Mar 2017
 */
public class Token implements Serializable {
    private static final long serialVersionUID = -6127596960209995302L;

    private String tokenType;
    private String accessToken;
    private long expiresIn;
    private String refreshToken;

    public Token(String accessToken, long expiresIn, String refreshToken) {
        this("bearer", accessToken, expiresIn, refreshToken);
    }

    public Token(String tokenType, String accessToken, long expiresIn, String refreshToken) {
        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        if (expiresIn != token.expiresIn) return false;
        if (tokenType != null ? !tokenType.equals(token.tokenType) : token.tokenType != null) return false;
        if (accessToken != null ? !accessToken.equals(token.accessToken) : token.accessToken != null) return false;
        return refreshToken != null ? refreshToken.equals(token.refreshToken) : token.refreshToken == null;
    }

    @Override
    public int hashCode() {
        int result = tokenType != null ? tokenType.hashCode() : 0;
        result = 31 * result + (accessToken != null ? accessToken.hashCode() : 0);
        result = 31 * result + (int) (expiresIn ^ (expiresIn >>> 32));
        result = 31 * result + (refreshToken != null ? refreshToken.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Token{" +
                "tokenType='" + tokenType + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", expiresIn=" + expiresIn +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
