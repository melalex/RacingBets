package com.room414.racingbets.resolvers;

import com.room414.racingbets.bll.abstraction.factories.infrastructure.JwtFactory;
import com.room414.racingbets.bll.concrete.factories.infrastructure.JwtFactoryImpl;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

/**
 * @author Alexander Melashchenko
 * @version 1.0 22 Mar 2017
 */
public class JwtFactoryResolver implements ParameterResolver {
    @Override
    public boolean supports(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Class<?> type = parameterContext.getParameter().getType();

        return type == JwtFactory.class;
    }

    @Override
    public Object resolve(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        try {
            return new JwtFactoryImpl("secret", "HmacSHA256", "jwt", 3600);
        } catch (DalException e) {
            throw new ParameterResolutionException("Exception during creating JwtFactory instance", e);
        }
    }

}
