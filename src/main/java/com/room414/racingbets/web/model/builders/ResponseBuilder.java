package com.room414.racingbets.web.model.builders;

import com.room414.racingbets.bll.abstraction.infrastructure.pagination.Pager;
import com.room414.racingbets.web.model.enums.Status;
import com.room414.racingbets.web.model.viewmodels.Error;
import com.room414.racingbets.web.model.viewmodels.Response;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 25 Mar 2017
 */
public class ResponseBuilder<T> {
    private int count;
    private int limit;
    private String type;
    private List<T> result = new LinkedList<>();
    private List<Error> errors = new LinkedList<>();

    public boolean hasErrors() {
        return errors.size() > 0;
    }

    public ResponseBuilder<T> setPager(Pager pager) {
        this.count = pager.getCount();
        this.limit = pager.getLimit();
        return this;
    }

    public ResponseBuilder<T> setType(String type) {
        this.type = type;
        return this;
    }

    public String getType() {
        return type;
    }

    public ResponseBuilder<T> addAllToResult(List<T> result) {
        this.result.addAll(result);
        return this;
    }

    public ResponseBuilder<T> addToResult(T result) {
        if (result != null) {
            this.result.add(result);
        }
        return this;
    }

    public ResponseBuilder<T> addAllToErrors(List<Error> errors) {
        this.errors.addAll(errors);
        return this;
    }

    public ResponseBuilder<T> addToErrors(Error error) {
        if (error != null) {
            this.errors.add(error);
        }
        return this;
    }

    public Response<T> buildSuccessResponse() {
        Response<T> response = new Response<>();

        response.setStatus(Status.SUCCESS);
        response.setCount(count != 0 ? count : result.size());
        response.setLimit(limit != 0 ? limit : result.size());
        response.setResult(result);
        response.setType(type);

        return response;
    }

    public Response<Error> buildErrorResponse() {
        Response<Error> response = new Response<>();

        response.setStatus(Status.ERROR);
        response.setCount(count != 0 ? count : errors.size());
        response.setLimit(limit != 0 ? limit : errors.size());
        response.setResult(errors);
        response.setType(type);

        return response;
    }
}
