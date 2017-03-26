package com.room414.racingbets.web.util;

import com.room414.racingbets.bll.abstraction.infrastructure.pagination.Pager;
import com.room414.racingbets.bll.abstraction.services.AccountService;
import com.room414.racingbets.bll.dto.entities.RaceParticipantThumbnailDto;
import com.room414.racingbets.dal.domain.enums.Role;
import com.room414.racingbets.web.infrastructure.PagerImpl;
import com.room414.racingbets.web.infrastructure.ParticipantGetter;
import com.room414.racingbets.web.model.builders.ResponseBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

import static com.room414.racingbets.web.util.RequestUtil.getIdFromRequest;
import static com.room414.racingbets.web.util.RequestUtil.getPageFromRequest;
import static com.room414.racingbets.web.util.RequestUtil.getTokenFromRequest;
import static com.room414.racingbets.web.util.ResponseUtil.permissionDenied;
import static com.room414.racingbets.web.util.ResponseUtil.writeToResponse;

/**
 * @author Alexander Melashchenko
 * @version 1.0 25 Mar 2017
 */
public class ControllerUtil {
    private static final int PARTICIPANT_LIMIT = 10;
    private static final String PARTICIPANT_TYPE = "RaceParticipantThumbnail";

    private ControllerUtil() {

    }

    public static void findAsParticipant(HttpServletRequest req,
                                  HttpServletResponse resp,
                                  Locale locale,
                                  ParticipantGetter getter) throws IOException {
        long id = getIdFromRequest(req);
        int page = getPageFromRequest(req);

        ResponseBuilder<RaceParticipantThumbnailDto> responseBuilder
                = ResponseUtil.createResponseBuilder(resp, locale, PARTICIPANT_TYPE);

        Pager pager = new PagerImpl(PARTICIPANT_LIMIT, page);

        List<RaceParticipantThumbnailDto> horses = getter.call(id, pager);

        responseBuilder.addAllToResult(horses);
        responseBuilder.setCount(pager.getCount());

        resp.setStatus(HttpServletResponse.SC_FOUND);
        writeToResponse(resp, responseBuilder.buildSuccessResponse());
    }

    public static <T> void delete(HttpServletRequest req,
                                  HttpServletResponse resp,
                                  ResponseBuilder<T> builder,
                                  AccountService accountService,
                                  Locale locale,
                                  Consumer<Long> deleter) throws IOException {
        String token = getTokenFromRequest(req);
        if (token != null && accountService.isInRole(token, Role.ADMIN)) {
            long id = getIdFromRequest(req);

            deleter.accept(id);

            resp.setStatus(HttpServletResponse.SC_OK);

            writeToResponse(resp, builder.buildSuccessResponse());
        } else {
            permissionDenied(resp, builder, locale);
        }
    }
}
