package com.room414.racingbets.bll.concrete.infrastrucure;

import com.room414.racingbets.bll.abstraction.exceptions.BllException;
import com.room414.racingbets.bll.abstraction.infrastructure.Pager;
import com.room414.racingbets.bll.concrete.infrastrucure.decorated.*;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import org.apache.commons.logging.Log;

import java.util.List;

import static com.room414.racingbets.bll.concrete.infrastrucure.ErrorMessageUtil.*;

/**
 * @author Alexander Melashchenko
 * @version 1.0 20 Mar 2017
 */
public class ErrorHandleDecorator<D> {
    private UnitOfWorkFactory factory;
    private Log log;

    public ErrorHandleDecorator(UnitOfWorkFactory factory, Log log) {
        this.factory = factory;
        this.log = log;
    }

    public void create(D dto, CreateMethod<D> method) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            method.call(unitOfWork, dto);
        } catch (DalException e) {
            String message = createErrorMessage(dto);
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = createErrorMessage(dto);
            log.error(message, t);
            throw new BllException(message, t);
        }
    }

    public void update(D dto, UpdateMethod<D> method) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            method.call(unitOfWork, dto);
        } catch (DalException e) {
            String message = updateErrorMessage(dto);
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = updateErrorMessage(dto);
            log.error(message, t);
            throw new BllException(message, t);
        }
    }

    public D find(long id, FindMethod<D> method) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            return method.call(unitOfWork, id);
        } catch (DalException e) {
            String message = findErrorMessage(id);
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = findErrorMessage(id);
            log.error(message, t);
            throw new BllException(message, t);
        }
    }

    public List<D> search(String searchString, Pager pager, SearchMethod<D> method) {
        int limit = pager.getLimit();
        int offset = pager.getOffset();

        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            return method.call(unitOfWork, searchString, pager);
        } catch (DalException e) {
            String message = searchErrorMessage(searchString, limit, offset);
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = searchErrorMessage(searchString, limit, offset);
            log.error(message, t);
            throw new BllException(message, t);
        }
    }

    public List<D> findAll(Pager pager, FindAllMethod<D> method) {
        int limit = pager.getLimit();
        int offset = pager.getOffset();

        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            return method.call(unitOfWork, pager);
        } catch (DalException e) {
            String message = findAllErrorMessage(limit, offset);
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = findAllErrorMessage(limit, offset);
            log.error(message, t);
            throw new BllException(message, t);
        }
    }

    public void delete(long id, DeleteMethod method) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            method.call(unitOfWork, id);
        } catch (DalException e) {
            String message = deleteErrorMessage(id);
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = deleteErrorMessage(id);
            log.error(message, t);
            throw new BllException(message, t);
        }
    }
}
