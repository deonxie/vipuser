package com.yimei.vipuser.vipuser.service;


import cn.gd.thinkjoy.modules.persistence.SearchFilter;
import com.yimei.vipuser.vipuser.entity.IdEntity;
import com.yimei.vipuser.vipuser.entity.account.Role;
import com.yimei.vipuser.vipuser.repository.GenericDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Service层基类，实现一些公有的操作
 *
 * @param <E>
 * @param <D>
 * @author chenling
 */
@Transactional(readOnly = true)
public abstract class GenericService<E extends IdEntity, D extends GenericDao<E>> {
    private Class<E> genericClassE = getClazz();
    private GenericDao genericDao;
    protected Logger logger = LoggerFactory.getLogger(getClazz());


    protected Class<E> getClazz() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof Class) {
            return (Class<E>) ((ParameterizedType) ((Class) genericSuperclass).getGenericSuperclass()).getActualTypeArguments()[0];
        } else if (genericSuperclass instanceof ParameterizedType) {
            return (Class<E>) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        }

        return null;
    }

    /**
     * 查找子类中注入的DAO并且赋值给相应的父DAO以便进行相应的操作
     *
     * @return
     */
    private GenericDao getGenericDao() {
        if (this.genericDao == null) {
            Class<D> genericClassD = null;
            Type genericSuperclass = getClass().getGenericSuperclass();
            if (genericSuperclass instanceof Class) {
                genericClassD = (Class<D>) ((ParameterizedType) ((Class) genericSuperclass).getGenericSuperclass()).getActualTypeArguments()[1];
            } else if (genericSuperclass instanceof ParameterizedType) {
                genericClassD = (Class<D>) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[1];
            }

            if (null != genericClassD) {
                for (Field fileld : getClass().getDeclaredFields()) {

                    if (fileld.getGenericType() instanceof Class) {
                        Class fileldClass = (Class) fileld.getGenericType();
                        if (fileldClass.isAssignableFrom(genericClassD)) {
                            fileld.setAccessible(true);
                            try {
                                this.genericDao = (GenericDao) fileld.get(this);
                                break;
                            } catch (IllegalAccessException e) {
                                logger.error("通过子类的实例变量DAO出错!" + e.toString());
                            }
                        }
                    }
                }
            }
        }
        return this.genericDao;
    }


    public E get(final Long id) {
        E entity = (E) getGenericDao().findOne(id);
        if (null == entity) {
            try {
                entity = genericClassE.newInstance();
                entity.setId(0L);
            } catch (InstantiationException e) {
                logger.error("通过泛型实例化实体出错!" + e.toString());
            } catch (IllegalAccessException e) {
                logger.error("通过泛型实例化实体出错!" + e.toString());
            }
        }
        return entity;
    }

    /**
     * 保存实体
     */
    @Transactional(readOnly = false)
    public void save(E entity) {
        getGenericDao().save(entity);
    }

    /**
     * 删除实体
     */
    @Transactional(readOnly = false)
    public void delete(final Long id) {
        getGenericDao().delete(id);
    }

    /**
     * 获取全部的实体
     */
    public List<E> getAll() {
        return (List<E>) getGenericDao().findAll();
    }

    /**
     * 分页查询的实现
     */
    public Page<E> search(Map<String, Object> searchParams, cn.gd.thinkjoy.modules.web.pojo.PageRequest pageRequest) {
        PageRequest springPageRequest = SearchFilter.buildPageRequest(pageRequest);
        Specification<E> spec = SearchFilter.builderSpecification(searchParams, this.genericClassE);
        Page<E> pages = getGenericDao().findAll(spec, springPageRequest);
        /*
         * 设置Page参数方便页面获取
		 */
        pageRequest.setTotalCount(pages.getTotalElements());
        return pages;
    }
}