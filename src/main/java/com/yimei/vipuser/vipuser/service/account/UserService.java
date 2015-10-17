package com.yimei.vipuser.vipuser.service.account;

import cn.gd.thinkjoy.modules.security.utils.Digests;
import cn.gd.thinkjoy.modules.utils.Clock;
import cn.gd.thinkjoy.modules.utils.Encodes;

import com.yimei.vipuser.vipuser.entity.account.Role;
import com.yimei.vipuser.vipuser.entity.account.User;
import com.yimei.vipuser.vipuser.repository.account.UserDao;
import com.yimei.vipuser.vipuser.service.GenericService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by chenling on 14-3-25.
 */
@Component
@Transactional(readOnly = true)
public class UserService extends GenericService<User, UserDao> {
    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;
    private static final int SALT_SIZE = 8;

    private Clock clock = Clock.DEFAULT;
    @Autowired
    private UserDao userDao;

    public User findUserByLoginName(String loginName,int status) {
        return userDao.findByLoginName(loginName,status);
    }
    public User findUserByLoginName(String loginName) {
        return userDao.findByLoginName(loginName);
    }

    @Transactional(readOnly = false)
    public String registerUser(User user, List<Long> checkedRoleList) {
        if (user.getId() == 0) {
            user.setRegisterDate(clock.getCurrentDate());
            User oldUser = findUserByLoginName(user.getLoginName());
            if (null != oldUser) {
                return "用户名已经存在!";
            }
        }
        user.getRoleList().clear();
        for (Long roleId : checkedRoleList) {
            Role role = new Role();
            role.setId(roleId);
            user.getRoleList().add(role);
        }
        if (StringUtils.isNotBlank(user.getPlainPassword())) {
            entryptPassword(user);
        }
        user.setTypeStatus(User.TYPE_MANGAER);
        userDao.save(user);

        return "添加用户成功！";
    }

    /**
     * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
     */
    private void entryptPassword(User user) {
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        user.setSalt(Encodes.encodeHex(salt));

        byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, HASH_INTERATIONS);
        user.setPassword(Encodes.encodeHex(hashPassword));
    }

	public List<User> findUserByType(int typeVipuser) {
		return userDao.findUserByType(typeVipuser);
	}

	@Transactional(readOnly = false)
	public String createVipUser(User user, long recommend) {
		if(null != userDao.findByLoginName(user.getLoginName()))
			return "用户登录名已存在";
		if(StringUtils.isBlank(user.getPlainPassword()))
			return "密码未填写";
		
		user.setTypeStatus(User.TYPE_VIPUSER);
		user.setRegisterDate(new Date());
		entryptPassword(user);
		save(user);
		return "注册成功";
	}

	@Transactional(readOnly = false)
	public void resetPwd(User user) {
		entryptPassword(user);
		save(user);
	}

	public void updateUserStatus(long id, int status) {
		userDao.updateUserStatus(id,status);
	}
	
}
