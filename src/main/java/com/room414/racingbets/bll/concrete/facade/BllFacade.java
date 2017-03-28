package com.room414.racingbets.bll.concrete.facade;

import com.room414.racingbets.bll.abstraction.factories.infrastructure.JwtFactory;
import com.room414.racingbets.bll.abstraction.factories.services.AbstractServiceFactory;
import com.room414.racingbets.bll.concrete.factories.infrastructure.JwtFactoryImpl;
import com.room414.racingbets.bll.concrete.factories.services.AbstractServiceFactoryImpl;
import com.room414.racingbets.dal.abstraction.factories.AbstractDalFactory;
import com.room414.racingbets.dal.concrete.facade.DalFacade;

import java.util.Properties;

/**
 * @author Alexander Melashchenko
 * @version 1.0 22 Mar 2017
 */
public class BllFacade {
    private static BllFacade ourInstance = new BllFacade();

    public static BllFacade getInstance() {
        return ourInstance;
    }

    private AbstractServiceFactory serviceFactory;

    private BllFacade() {

    }

    /**
     * @param mail {@code Properties} with JavaMail configuration
     * @param jwt {@code Properties} with {@code JwtFactory} configuration
     * @param bll {@code Properties} with {@code AbstractServiceFactory} configuration
     */
    public void init(Properties mail, Properties jwt, Properties bll) {
        AbstractDalFactory dalFactory = DalFacade.getInstance().getDalFactory();
        JwtFactory jwtFactory = createJwtFactory(jwt);
        serviceFactory = new AbstractServiceFactoryImpl(dalFactory, jwtFactory, mail, bll);
    }

    private JwtFactory createJwtFactory(Properties properties) {
        String secret = properties.getProperty("bll.secret");
        String algorithm = properties.getProperty("bll.algorithm");
        String type = properties.getProperty("bll.type");
        long expire = Long.parseLong(properties.getProperty("bll.expire"));

        return new JwtFactoryImpl(secret, algorithm, type, expire);
    }

    public AbstractServiceFactory getAbstractServiceFactory() {
        return serviceFactory;
    }
}
