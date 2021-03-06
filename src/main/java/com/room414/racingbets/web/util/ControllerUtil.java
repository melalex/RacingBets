package com.room414.racingbets.web.util;

import com.room414.racingbets.bll.abstraction.services.AccountService;
import com.room414.racingbets.dal.domain.enums.Role;
import com.room414.racingbets.web.model.builders.ResponseBuilder;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.room414.racingbets.web.util.RequestUtil.getIdFromRequest;
import static com.room414.racingbets.web.util.RequestUtil.getJwtToken;
import static com.room414.racingbets.web.util.ResponseUtil.*;

/**
 * @author Alexander Melashchenko
 * @version 1.0 25 Mar 2017
 */
public class ControllerUtil {

    private ControllerUtil() {

    }

    public static <S, R> R map(S source, Class<R> clazz) {
        Mapper beanMapper = DozerBeanMapperSingletonWrapper.getInstance();
        return beanMapper.map(source, clazz);
    }

    public static void delete(HttpServletRequest req,
                              HttpServletResponse resp,
                              ResponseBuilder<String> builder,
                              AccountService accountService,
                              Locale locale,
                              Consumer<Long> deleter) throws IOException {

        String token = getJwtToken(req);
        if (token != null && accountService.isInRole(token, Role.ADMIN)) {
            String message = ResourceBundle.getBundle(SUCCESS_MESSAGE_BUNDLE, locale)
                    .getString("entity.deleted");

            long id = getIdFromRequest(req);

            deleter.accept(id);

            builder.addToResult(message);
            resp.setStatus(HttpServletResponse.SC_OK);

            writeToResponse(resp, builder.buildSuccessResponse());
        } else {
            permissionDenied(resp, builder, locale);
        }
    }

    public static <T> void find(HttpServletRequest req,
                                HttpServletResponse resp,
                                ResponseBuilder<T> builder,
                                Locale locale,
                                Function<Long, T> finder) throws IOException {

        long id = getIdFromRequest(req);

        if (id <= 0) {
            invalidId(resp, builder, locale);
        } else {
            builder.addToResult(finder.apply(id));

            resp.setStatus(HttpServletResponse.SC_OK);
            writeToResponse(resp, builder.buildSuccessResponse());
        }
    }

}
