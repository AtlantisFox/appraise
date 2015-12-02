package com.example.appraise.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@SuppressWarnings("unchecked")
public abstract class BaseDao<T> {
    // http://1194867672-qq-com.iteye.com/blog/1159918
    private Class<T> clazz;

    public BaseDao() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        clazz = (Class<T>) type.getActualTypeArguments()[0];
    }

    @Autowired
    SessionFactory sessionFactory;

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public void save(T entity) {
        this.getSession().save(entity);
    }

    public void update(T entity) {
        this.getSession().update(entity);
    }

    public void delete(T entity) {
        this.getSession().delete(entity);
    }

    public T findById(Serializable id) {
        return (T) this.getSession().get(this.clazz, id);
    }

    public List<T> findAll() {
        return this.getSession().createCriteria(this.clazz).list();
    }
}
