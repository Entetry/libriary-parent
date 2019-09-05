package com.antonklintsevich.persistense;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.hibernate.Session;

import com.antonklintsevich.entity.AbstractEntity;

public interface IGenericDao<T extends AbstractEntity> {

    Optional<T> findOne(final Long id,EntityManager entityManager);

    List<T> findAll(EntityManager entityManager);

    T create(final T entity,EntityManager entityManager);

    T update(final T entity,EntityManager entityManager);

    void delete(final T entity,EntityManager entityManager);

    void deleteById(final Long entityId,EntityManager entityManager);
}