package com.antonklintsevich.persistense;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.hibernate.Session;

import com.antonklintsevich.entity.AbstractEntity;
import com.antonklintsevich.exception.MyResourceNotFoundException;

public abstract class AbstractHibernateDao<T extends AbstractEntity> implements IGenericDao<T> {

    private Class<T> clazz;
    @SuppressWarnings("unchecked")
    public AbstractHibernateDao() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        this.clazz = (Class<T>) type.getActualTypeArguments()[0];
    }
    @SuppressWarnings("unchecked")
    @Override
    public Optional<T> findOne(Long id, EntityManager entityManager) {
        return Optional.ofNullable((T) entityManager.find(clazz, id));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findAll(EntityManager entityManager) {
        return entityManager.createQuery("from " + clazz.getName()).getResultList();
    }

    @Override
    public T create(T entity,EntityManager entityManager) {
       Session session=entityManager.unwrap(Session.class);
       session.save(entity);
        return entity;
    }

    @Override
    public T update(T entity,EntityManager entityManager) {
        entityManager.merge(entity);
        return entity;
    }

    @Override
    public void delete(T entity,EntityManager entityManager) {
        entityManager.remove(entity);
    }

    @Override
    public void deleteById(Long entityId,EntityManager entityManager) {
       findOne(entityId,entityManager).ifPresent(entity -> entityManager.remove(entity));
    }
}
