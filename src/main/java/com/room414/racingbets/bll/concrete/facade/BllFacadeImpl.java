package com.room414.racingbets.bll.concrete.facade;

import com.room414.racingbets.bll.abstraction.facade.BllFacade;
import com.room414.racingbets.bll.abstraction.factories.infrastructure.JwtFactory;
import com.room414.racingbets.bll.abstraction.factories.services.AbstractServiceFactory;
import com.room414.racingbets.bll.concrete.factories.infrastructure.JwtFactoryImpl;
import com.room414.racingbets.bll.concrete.factories.services.AbstractServiceFactoryImpl;
import com.room414.racingbets.dal.abstraction.facade.DalFacade;
import com.room414.racingbets.dal.abstraction.factories.AbstractDalFactory;

import java.util.Properties;

/**
 * @author Alexander Melashchenko
 * @version 1.0 22 Mar 2017
 */
public class BllFacadeImpl implements BllFacade {
    private AbstractServiceFactory serviceFactory;

    @Override
    public void init(DalFacade dalFacade, Properties properties) {
        JwtFactory jwtFactory = createJwtFactory(properties);
        serviceFactory = new AbstractServiceFactoryImpl(dalFacade.getDalFactory(), jwtFactory, properties);
    }

    private JwtFactory createJwtFactory(Properties properties) {
        String secret = properties.getProperty("bll.secret");
        String algorithm = properties.getProperty("bll.algorithm");
        String type = properties.getProperty("bll.type");
        long expire = Long.parseLong(properties.getProperty("bll.expire"));

        return new JwtFactoryImpl(secret, algorithm, type, expire);
    }

    @Override
    public AbstractServiceFactory getAbstractServiceFactory() {
        return serviceFactory;
    }
}
