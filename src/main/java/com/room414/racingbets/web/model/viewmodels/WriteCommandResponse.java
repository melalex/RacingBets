package com.room414.racingbets.web.model.viewmodels;

import java.io.Serializable;

/**
 * @author Alexander Melashchenko
 * @version 1.0 05 Apr 2017
 */
public class WriteCommandResponse implements Serializable {
    private static final long serialVersionUID = -8185855337790815696L;

    public long id;
    public String message;

    public WriteCommandResponse() {
    }

    public WriteCommandResponse(long id, String message) {
        this.id = id;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WriteCommandResponse that = (WriteCommandResponse) o;

        if (id != that.id) return false;
        return message != null ? message.equals(that.message) : that.message == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WriteCommandResponse{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }
}
