package com.yimei.vipuser.vipuser.repository;

import com.yimei.vipuser.vipuser.entity.IdEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @Title: GenericDao.java
 * @Package com.jlusoft.csair.fsms.repository
 * @Description: 简化DAO接口继承.
 * <pre>
 * And --- 等价于 SQL 中的 and 关键字，比如 findByUsernameAndPassword(String user, Striang pwd)；
 * Or --- 等价于 SQL 中的 or 关键字，比如 findByUsernameOrAddress(String user, String addr)；
 * Between --- 等价于 SQL 中的 between 关键字，比如 findBySalaryBetween(int max, int min)；
 * LessThan --- 等价于 SQL 中的 "<"，比如 findBySalaryLessThan(int max)；
 * GreaterThan --- 等价于 SQL 中的">"，比如 findBySalaryGreaterThan(int min)；
 * IsNull --- 等价于 SQL 中的 "is null"，比如 findByUsernameIsNull()；
 * IsNotNull --- 等价于 SQL 中的 "is not null"，比如 findByUsernameIsNotNull()；
 * NotNull --- 与 IsNotNull 等价；
 * Like --- 等价于 SQL 中的 "like"，比如 findByUsernameLike(String user)；
 * NotLike --- 等价于 SQL 中的 "not like"，比如 findByUsernameNotLike(String user)；
 * OrderBy --- 等价于 SQL 中的 "order by"，比如 findByUsernameOrderBySalaryAsc(String user)；
 * Not --- 等价于 SQL 中的 "！ ="，比如 findByUsernameNot(String user)；
 * In --- 等价于 SQL 中的 "in"，比如 findByUsernameIn(Collection<String> userList) ，方法的参数可以是 Collection 类型，也可以是数组或者不定长参数；
 * NotIn --- 等价于 SQL 中的 "not in"，比如 findByUsernameNotIn(Collection<String> userList) ，方法的参数可以是 Collection 类型，也可以是数组或者不定长参数；
 * </pre>
 * @author chenling
 * @Version: V0.0.1-final.0
 */
@NoRepositoryBean
public interface GenericDao<E extends IdEntity> extends PagingAndSortingRepository<E, Long>, JpaSpecificationExecutor<E> {
}