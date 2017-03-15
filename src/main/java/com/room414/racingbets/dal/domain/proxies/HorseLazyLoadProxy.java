package com.room414.racingbets.dal.domain.proxies;

import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.entities.Horse;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.concrete.CachedDalFactory;
import com.room414.racingbets.dal.domain.entities.Owner;
import com.room414.racingbets.dal.domain.entities.Trainer;
import com.room414.racingbets.dal.domain.enums.Gender;

import java.sql.Date;

/**
 * Provides Horse entity lazy load.
 *
 * @author Alexander Melashchenko
 * @version 1.0 26 Feb 2017
 */
public class HorseLazyLoadProxy extends Horse {
    private static final long serialVersionUID = -5739620713735570435L;

    private long id;
    private Horse horse;

    private HorseLazyLoadProxy(long id) {
        this.id = id;
    }

    public static HorseLazyLoadProxy create(long id) {
        if (id == 0) {
            return null;
        }

        return new HorseLazyLoadProxy(id);
    }

    // TODO: is good?
    private Horse getHorse() {
        if (horse == null) {
            try(UnitOfWork unitOfWork = CachedDalFactory.getInstance().createUnitOfWorkFactory().createUnitOfWork()) {
                horse = unitOfWork.getHorseDao().find(id);
            } catch (DalException e) {
                throw new RuntimeException("Can not lazy load horse");
            } catch (Exception e) {
                throw new RuntimeException("Can not close UnitOfWork instance");
            }
        }
        return horse;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
        getHorse().setId(id);
    }

    @Override
    public String getName() {
        return getHorse().getName();
    }

    @Override
    public void setName(String name) {
        getHorse().setName(name);
    }

    @Override
    public Trainer getTrainer() {
        return getHorse().getTrainer();
    }

    @Override
    public void setTrainer(Trainer trainer) {
        getHorse().setTrainer(trainer);
    }

    @Override
    public Owner getOwner() {
        return getHorse().getOwner();
    }

    @Override
    public void setOwner(Owner owner) {
        getHorse().setOwner(owner);
    }

    @Override
    public Date getBirthday() {
        return getHorse().getBirthday();
    }

    @Override
    public void setBirthday(Date birthday) {
        getHorse().setBirthday(birthday);
    }

    @Override
    public Gender getGender() {
        return getHorse().getGender();
    }

    @Override
    public void setGender(Gender gender) {
        getHorse().setGender(gender);
    }

    @Override
    public Horse getSir() {
        return getHorse().getSir();
    }

    @Override
    public void setSir(Horse sir) {
        getHorse().setSir(sir);
    }

    @Override
    public Horse getDam() {
        return getHorse().getDam();
    }

    @Override
    public void setDam(Horse dam) {
        getHorse().setDam(dam);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HorseLazyLoadProxy proxy = (HorseLazyLoadProxy) o;

        return id == proxy.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "HorseLazyLoadProxy{" +
                "id=" + id +
                '}';
    }
}
