package com.room414.racingbets.web.controller.impl;

import com.room414.racingbets.bll.abstraction.services.AccountService;
import com.room414.racingbets.bll.abstraction.services.CrudService;
import com.room414.racingbets.web.model.infrastructure.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;

/**
 * @author Alexander Melashchenko
 * @version 1.0 31 Mar 2017
 */
class CrudControllerDelegateTest {
    private CrudControllerDelegate<Target, Target> crudControllerDelegate;
    private HttpServletRequest httpServletRequest;
    private HttpServletResponse httpServletResponse;
    private Target target = new Target(1, 2);
    private List<Target> targetList = Collections.singletonList(target);

    private static class Target {
        int a;
        int b;

        Target() {
        }

        Target(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }

    private interface Service extends CrudService<Target> {

    }

    @BeforeEach
    void setUp() {
        httpServletRequest = Mockito.mock(HttpServletRequest.class);
        httpServletResponse = Mockito.mock(HttpServletResponse.class);

        CrudService<Target> crudService = Mockito.mock(Service.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        Validator<Target, Target> validator = (f, d) -> {};
        Locale locale = new Locale("en");

        Mockito.when(crudService.find(any())).then(i -> target);
        Mockito.when(crudService.findAll(any())).then(i -> targetList);
        Mockito.when(crudService.search(any(), any())).then(i -> targetList);

        crudControllerDelegate = new CrudControllerDelegate<>(
                crudService,
                accountService,
                Target.class,
                Target.class,
                "Target",
                locale,
                validator
        );
    }

    @Test
    void create() {

    }

    @Test
    void update() {
    }

    @Test
    void find() {
    }

    @Test
    void search() {
    }

    @Test
    void delete() {
    }

}