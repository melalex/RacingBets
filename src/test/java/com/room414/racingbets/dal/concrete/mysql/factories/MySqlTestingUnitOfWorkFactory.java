package com.room414.racingbets.dal.concrete.mysql.factories;

import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.concrete.mysql.dao.MySqlTestingUnitOfWork;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


/**
 * @author Alexander Melashchenko
 * @version 1.0 07 Mar 2017
 */
public class MySqlTestingUnitOfWorkFactory implements UnitOfWorkFactory {
    private static final Path CONFIG_FILE_PATH = Paths.get("webproject", "config", "testDbConfig", "properties");
    private static MySqlTestingUnitOfWorkFactory ourInstance = createFactory();

    private String url;
    private String username;
    private String password;

    public static MySqlTestingUnitOfWorkFactory getInstance() {
        return ourInstance;
    }


    private static MySqlTestingUnitOfWorkFactory createFactory()  {
        try {
            Properties properties = new Properties();
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG_FILE_PATH.toString()));
            String url = properties.getProperty("jdbc.url");
            String driver = properties.getProperty("jdbc.driver");
            String username = properties.getProperty("jdbc.username");
            String password = properties.getProperty("jdbc.password");

            Class.forName(driver);

            return new MySqlTestingUnitOfWorkFactory(url, username, password);
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException("Exception during factory creation", e);
        }
    }

    private MySqlTestingUnitOfWorkFactory(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public UnitOfWork create() throws DalException {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            return new MySqlTestingUnitOfWork(connection);
        } catch (SQLException e) {
            throw new DalException("Exception during creating connection", e);
        }
    }
}
