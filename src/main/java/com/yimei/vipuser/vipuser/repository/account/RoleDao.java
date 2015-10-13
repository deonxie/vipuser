package com.yimei.vipuser.vipuser.repository.account;

import com.yimei.vipuser.vipuser.entity.account.Role;
import com.yimei.vipuser.vipuser.repository.GenericDao;

/**
 * Created by chenling on 14-3-24.
 */
public interface RoleDao extends GenericDao<Role>{
    Role findByName(String name);
}
