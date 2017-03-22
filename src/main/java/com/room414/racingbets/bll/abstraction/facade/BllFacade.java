package com.room414.racingbets.bll.abstraction.facade;

import com.room414.racingbets.bll.abstraction.factories.services.AbstractServiceFactory;
import com.room414.racingbets.dal.abstraction.facade.DalFacade;

import java.util.Properties;

/**
 * @author Alexander Melashchenko
 * @version 1.0 22 Mar 2017
 */
public interface BllFacade {
    void init(DalFacade dalFacade, Properties properties);
    AbstractServiceFactory getAbstractServiceFactory();
}
