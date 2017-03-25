package com.room414.racingbets.web.model.builders;

import com.room414.racingbets.web.model.enums.Status;
import com.room414.racingbets.web.model.viewmodels.ErrorViewModel;
import com.room414.racingbets.web.model.viewmodels.Response;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 25 Mar 2017
 */
public class ResponseBuilder<T> {
    private int count;
    private String type;
    private List<T> result = new LinkedList<>();
    private List<ErrorViewModel> errors = new LinkedList<>();


    public ResponseBuilder<T> setCount(int count) {
        this.count = count;
        return this;
    }

    public ResponseBuilder<T> setType(String type) {
        this.type = type;
        return this;
    }

    public String getType() {
        return type;
    }

    public ResponseBuilder<T> addAllResults(List<T> result) {
        this.result.addAll(result);
        return this;
    }

    public ResponseBuilder<T> addReesponse(T result) {
        this.result.add(result);
        return this;
    }

    public ResponseBuilder<T> addAllErrors(List<ErrorViewModel> errors) {
        this.errors.addAll(errors);
        return this;
    }

    public ResponseBuilder<T> addError(ErrorViewModel error) {
        this.errors.add(error);
        return this;
    }

    public Response<T> buildSuccessResponse() {
        Response<T> response = new Response<>();

        response.setStatus(Status.SUCCESS);
        response.setCount(count != 0 ? count : result.size());
        response.setResult(result);
        response.setType(type);

        return response;
    }

    public Response<ErrorViewModel> buildErrorResponse() {
        Response<ErrorViewModel> response = new Response<>();

        response.setStatus(Status.SUCCESS);
        response.setCount(count != 0 ? count : errors.size());
        response.setResult(errors);
        response.setType(type);

        return response;
    }
}
