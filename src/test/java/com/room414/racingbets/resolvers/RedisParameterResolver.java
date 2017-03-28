package com.room414.racingbets.resolvers;

import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.infrastructure.factories.TestingRedisFactory;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

/**
 * @author Alexander Melashchenko
 * @version 1.0 15 Mar 2017
 */
public class RedisParameterResolver implements ParameterResolver {
    @Override
    public boolean supports(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Class<?> type = parameterContext.getParameter().getType();

        return type == TestingRedisFactory.class;
    }

    @Override
    public Object resolve(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        try {
            return TestingRedisFactory.createInstance();
        } catch (DalException e) {
            throw new ParameterResolutionException("Exception during creating MySqlTestingUnitOfWork instance", e);
        }
    }
}
