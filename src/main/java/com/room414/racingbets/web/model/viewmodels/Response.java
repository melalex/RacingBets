package com.room414.racingbets.web.model.viewmodels;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.room414.racingbets.web.model.enums.Status;

import java.io.Serializable;
import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 24 Mar 2017
 */
public class Response<T> implements Serializable {
    private static final long serialVersionUID = -3142651256060548802L;

    /**
     * Possible values are: success and error
     */
    private Status status;
    /**
     * Total count of records that match your request. Because of pagination,
     * this is not necessarily the number of records in the response
     */
    private int count;
    /**
     * Records per page
     */
    private int limit;
    /**
     * Resource (sometimes called the Object) type
     */
    private String type;
    /**
     * This contains the actual results, i.e. the object or
     * objects that you requested.
     */
    private List<T> result;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Response<?> response = (Response<?>) o;

        if (count != response.count) return false;
        if (limit != response.limit) return false;
        if (status != response.status) return false;
        if (type != null ? !type.equals(response.type) : response.type != null) return false;
        return result != null ? result.equals(response.result) : response.result == null;
    }

    @Override
    public int hashCode() {
        int result1 = status != null ? status.hashCode() : 0;
        result1 = 31 * result1 + count;
        result1 = 31 * result1 + limit;
        result1 = 31 * result1 + (type != null ? type.hashCode() : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        return result1;
    }

    @Override
    public String toString() {
        return "Response{" +
                "status=" + status +
                ", count=" + count +
                ", limit=" + limit +
                ", type='" + type + '\'' +
                ", result=" + result +
                '}';
    }
}
