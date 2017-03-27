package com.room414.racingbets.web.servlet;

import com.room414.racingbets.bll.concrete.facade.BllFacade;
import com.room414.racingbets.dal.concrete.facade.DalFacade;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * @author Alexander Melashchenko
 * @version 1.0 28 Mar 2017
 */
class LifeCycle {
    private static final Log LOG = LogFactory.getLog(LifeCycle.class);

    private static final Path DB_CONFIG_FILE_PATH = Paths.get(
            System.getProperty("user.dir"),
            "database",
            "mysql",
            "main",
            "config",
            "mysqlConfig.properties"
    );

    private static final Path REDIS_CONFIG_FILE_PATH = Paths.get(
            System.getProperty("user.dir"),
            "database",
            "redis",
            "main",
            "config",
            "redisConfig.properties"
    );

    private LifeCycle() {

    }

    private Properties readProperties(String name) throws ServletException {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(name)) {
            Properties properties = new Properties();
            properties.load(in);
            return properties;
        } catch (IOException e) {
            String message = String.format("Exception during reading properties from file '%s'", name);
            LOG.error(message, e);
            throw new ServletException(message, e);
        }
    }

    private static void initLog(){

    }

    private static void initDal() {

    }

    private static void initBll() {

    }

    static void startUp(RacingBetsFrontController servlet) {
        initLog();
        initDal();
        initBll();
    }

    static void tearDown() {
        DalFacade.getInstance().close();
    }
}
