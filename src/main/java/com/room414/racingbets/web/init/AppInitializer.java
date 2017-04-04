package com.room414.racingbets.web.init;

import com.room414.racingbets.bll.concrete.facade.BllFacade;
import com.room414.racingbets.dal.concrete.facade.DalFacade;
import com.room414.racingbets.web.model.exceptions.WebException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Alexander Melashchenko
 * @version 1.0 04 Apr 2017
 */
public class AppInitializer {
    private static final Log LOG = LogFactory.getLog(AppInitializer.class);

    private static final String MYSQL_PROPERTIES = "datasource/main/mysql.properties";
    private static final String REDIS_PROPERTIES = "datasource/main/redis.properties";
    private static final String DAL_PROPERTIES = "appConfig/dal.properties";

    private static final String JWT_PROPERTIES = "appConfig/jwt.properties";
    private static final String MAIL_PROPERTIES = "appConfig/mail.properties";
    private static final String BLL_PROPERTIES = "appConfig/bll.properties";

    private AppInitializer() {

    }

    private static Properties readProperties(String name) {
        try (InputStream in = AppInitializer.class.getClassLoader().getResourceAsStream(name)) {
            Properties properties = new Properties();
            properties.load(in);
            return properties;
        } catch (IOException e) {
            String message = String.format("Exception during reading properties from file '%s'", name);
            LOG.error(message, e);
            throw new WebException(message, e);
        }
    }

    private static void initDal() {
        Properties mysql = readProperties(MYSQL_PROPERTIES);
        Properties redis = readProperties(REDIS_PROPERTIES);
        Properties dal = readProperties(DAL_PROPERTIES);

        DalFacade.getInstance().initDal(mysql, redis, dal);
    }

    private static void initBll() {
        Properties jwt = readProperties(JWT_PROPERTIES);
        Properties mail = readProperties(MAIL_PROPERTIES);
        Properties bll = readProperties(BLL_PROPERTIES);

        BllFacade.getInstance().init(mail, jwt, bll);
    }

    public static void initApp() {
        initDal();
        initBll();
    }
}
