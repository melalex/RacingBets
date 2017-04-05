package com.room414.racingbets.web.init;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.room414.racingbets.bll.abstraction.services.*;
import com.room414.racingbets.bll.concrete.facade.BllFacade;
import com.room414.racingbets.bll.dto.entities.*;
import com.room414.racingbets.dal.concrete.facade.DalFacade;
import com.room414.racingbets.dal.domain.enums.Gender;
import com.room414.racingbets.dal.domain.enums.Language;
import com.room414.racingbets.dal.domain.enums.Role;
import com.room414.racingbets.web.model.forms.HorseForm;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import static com.room414.racingbets.web.init.AppInitializer.initApp;

/**
 * @author Alexander Melashchenko
 * @version 1.0 04 Apr 2017
 */
public class DatabaseInitializer {
    private static final String HORSE_INITIAL_DATA = "initialData/Horse.json";
    private static final String JOCKEY_INITIAL_DATA = "initialData/Jockey.json";
    private static final String OWNER_INITIAL_DATA = "initialData/Owner.json";
    private static final String RACECOURSE_INITIAL_DATA = "initialData/Racecourse.json";
    private static final String TRAINER_INITIAL_DATA = "initialData/Trainer.json";
    private static final String USER_INITIAL_DATA = "initialData/User.json";

    private static void initSuperUser() {
        UserDto superUser = new UserDto();
        superUser.setLogin("melalex");
        superUser.setPassword("secure");
        superUser.setEmailConfirmed(true);
        superUser.setEmail("melalex490@gmail.com");
        superUser.setFirstName("Alexander");
        superUser.setLastName("Melashchenko");
        superUser.setLanguage(Language.RUSSIAN);
        superUser.setBalance(BigDecimal.valueOf(1000));
        superUser.addRole(Role.HANDICAPPER);
        superUser.addRole(Role.ADMIN);
        superUser.addRole(Role.BOOKMAKER);

        BllFacade
                .getInstance()
                .getAbstractServiceFactory()
                .createUserServiceFactory()
                .create()
                .create(superUser);
    }

    private static Language getLanguage() {
        long random = Math.round(Math.random() * 1);
        return random == 0 ? Language.ENGLISH : Language.RUSSIAN;
    }

    private static void initUsers() throws IOException {
        try (InputStream in = AppInitializer.class.getClassLoader().getResourceAsStream(USER_INITIAL_DATA)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            ObjectMapper jsonMapper = new ObjectMapper();
            UserService userService = BllFacade
                    .getInstance()
                    .getAbstractServiceFactory()
                    .createUserServiceFactory()
                    .create();

            UserDto userDto;
            String line;

            while (reader.ready()) {
                line = reader.readLine();
                userDto = jsonMapper.readValue(line, UserDto.class);
                userDto.setLanguage(getLanguage());
                userService.create(userDto);
            }
        }
    }

    private static Gender getGender() {
        long random = Math.round(Math.random() * 1);
        return random == 0 ? Gender.STALLION : Gender.MARE;
    }

    private static void initHorses() throws IOException {
        try (InputStream in = AppInitializer.class.getClassLoader().getResourceAsStream(HORSE_INITIAL_DATA)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            ObjectMapper jsonMapper = new ObjectMapper();
            Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
            HorseService horseService = BllFacade
                    .getInstance()
                    .getAbstractServiceFactory()
                    .createHorseServiceFactory()
                    .create();

            HorseForm horseForm;
            HorseDto horseDto;
            String line;

            while (reader.ready()) {
                line = reader.readLine();
                horseForm = jsonMapper.readValue(line, HorseForm.class);
                horseDto = mapper.map(horseForm, HorseDto.class);

                horseDto.setGender(getGender());

                horseService.create(horseDto);
            }
        }
    }

    private static void initJockeys() throws IOException {
        try (InputStream in = AppInitializer.class.getClassLoader().getResourceAsStream(JOCKEY_INITIAL_DATA)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            ObjectMapper jsonMapper = new ObjectMapper();
            JockeyService jockeyService = BllFacade
                    .getInstance()
                    .getAbstractServiceFactory()
                    .createJockeyServiceFactory()
                    .create();

            JockeyDto jockeyDto;
            String line;

            while (reader.ready()) {
                line = reader.readLine();
                jockeyDto = jsonMapper.readValue(line, JockeyDto.class);

                jockeyService.create(jockeyDto);
            }
        }
    }

    private static void initOwners() throws IOException {
        try (InputStream in = AppInitializer.class.getClassLoader().getResourceAsStream(OWNER_INITIAL_DATA)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            ObjectMapper jsonMapper = new ObjectMapper();
            OwnerService ownerService = BllFacade
                    .getInstance()
                    .getAbstractServiceFactory()
                    .createOwnerServiceFactory()
                    .create();

            OwnerDto ownerDto;
            String line;

            while (reader.ready()) {
                line = reader.readLine();
                ownerDto = jsonMapper.readValue(line, OwnerDto.class);

                ownerService.create(ownerDto);
            }
        }
    }

    private static void initRacecourses() throws IOException {
        try (InputStream in = AppInitializer.class.getClassLoader().getResourceAsStream(RACECOURSE_INITIAL_DATA)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            ObjectMapper jsonMapper = new ObjectMapper();
            RacecourseService racecourseService = BllFacade
                    .getInstance()
                    .getAbstractServiceFactory()
                    .createRacecourseServiceFactory()
                    .create();

            RacecourseDto racecourseDto;
            String line;

            while (reader.ready()) {
                line = reader.readLine();
                racecourseDto = jsonMapper.readValue(line, RacecourseDto.class);

                racecourseService.create(racecourseDto);
            }
        }
    }

    private static void initRaces() {

    }

    private static void initTrainers() throws IOException {
        try (InputStream in = AppInitializer.class.getClassLoader().getResourceAsStream(TRAINER_INITIAL_DATA)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            ObjectMapper jsonMapper = new ObjectMapper();
            TrainerService trainerService = BllFacade
                    .getInstance()
                    .getAbstractServiceFactory()
                    .createTrainerServiceFactory()
                    .create();

            TrainerDto trainerDto;
            String line;

            while (reader.ready()) {
                line = reader.readLine();
                trainerDto = jsonMapper.readValue(line, TrainerDto.class);

                trainerService.create(trainerDto);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        initApp();

        initSuperUser();
        initUsers();
        initJockeys();
        initOwners();
        initTrainers();
        initRacecourses();
        initHorses();
        initRaces();

        DalFacade.getInstance().close();
    }
}
