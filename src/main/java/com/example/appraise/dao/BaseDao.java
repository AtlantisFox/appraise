package com.example.appraise.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * 泛型DAO
 * 抄自http://1194867672-qq-com.iteye.com/blog/1159918。
 *
 * @param <T> 持久化对象的类型
 */
@SuppressWarnings("unchecked")
public abstract class BaseDao<T> {
    @Autowired
    SessionFactory sessionFactory;

    private Class<T> clazz;

    public BaseDao() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        clazz = (Class<T>) type.getActualTypeArguments()[0];
    }

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

    public boolean exist(Serializable id) {
        T tmp = findById(id);
        if (tmp != null) {
            getSession().evict(tmp);
            return true;
        } else {
            return false;
        }
    }
}
