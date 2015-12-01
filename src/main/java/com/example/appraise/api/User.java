package com.example.appraise.api;

import com.example.appraise.controller.SessionChecker;
import com.example.appraise.dao.UserDao;
import com.example.appraise.dao.UserSecureDao;
import com.example.appraise.model.ArUser;
import com.example.appraise.model.ArUserSecure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 负责处理/api/user相关的RestApi。
 */
@RestController
@RequestMapping(value = "/api/user")
public class User extends BaseRestApiController {
    @Autowired
    private UserSecureDao userSecureDao;

    @Autowired
    private UserDao userDao;

    /**
     * 处理用户登录请求
     *
     * @param username 用户名
     * @param password 密码(SHA256后)
     * @param session  Session
     * @return 如果登陆成功，则返回ArUser对象，可以获取到用户的信息。
     * @throws RestApiException 如果登陆失败，则抛出异常。
     */
    @RequestMapping(value = "auth")
    public ArUser Auth(@RequestParam("username") String username,
                       @RequestParam("password") String password,
                       HttpSession session) throws RestApiException {
        // 记录下最后一次尝试登陆的用户名
        session.setAttribute("username", username);
        // 用户身份验证
        ArUserSecure user = userSecureDao.findById(username);
        if (user != null) {
            if (password.equals(user.getPassword())) {
                new SessionChecker(session).setUser(user);
                return user.toArUser();
            }
        }
        throw RestApiException.onUnauthorized();
    }

    /**
     * 处理管理员创建新用户的请求
     *
     * @param user    新用户的对象
     * @param session Session
     * @return 如果创建成功，则返回ArUser对象。
     * @throws RestApiException 如果创建失败，则抛出异常。
     */
    @RequestMapping(value = "create")
    public ArUser Create(ArUserSecure user, HttpSession session) throws RestApiException {
        // taken from: http://stackoverflow.com/a/16942352

        // 必须管理员
        new SessionChecker(session).requireAccountAdmin();
        // 验证输入有效
        if (user.getUsername() == null || user.getUsername().isEmpty())
            throw RestApiException.onInvalidParam("username is null/empty");
        if (user.getPassword() == null || user.getPassword().isEmpty())
            throw RestApiException.onInvalidParam("password is null/empty");

        try {
            userSecureDao.save(user);
        } catch (DataIntegrityViolationException e) {
            // 数据库保存失败，原因只有用户名重复/主键冲突
            throw RestApiException.onInvalidParam("duplicated username");
        }

        return user.toArUser();
    }

    /**
     * 更新用户数据
     * <p/>
     * 可以是1)普通用户修改自己的备注/密码；也可以是2)管理员修改任意用户备注/密码/权限。
     *
     * @param newUser 更改后用户的数据
     * @param session Session
     * @return 如果更新成功，则返回更新后的ArUser对象。
     * @throws RestApiException 如果更新失败，则抛出异常。
     */
    @RequestMapping(value = "update")
    public ArUser Modify(ArUserSecure newUser, HttpSession session) throws RestApiException {
        /**
         * 思路：先根据用户名查到数据库中的用户对象oldUser，然后根据newUser更新oldUser，最后update。
         */
        // 首先，用户必须登陆。
        SessionChecker sessionChecker = new SessionChecker(session);
        sessionChecker.requireAuthorized();

        // 验证用户名存在
        ArUserSecure oldUser = userSecureDao.findById(newUser.getUsername());
        if (oldUser == null)
            throw RestApiException.onInvalidParam("username does not exist");

        // 分情况判断
        if (!sessionChecker.hasAccountAdmin()) {
            // 普通用户，只能修改自己的信息。
            if (!oldUser.getUsername().equals(sessionChecker.getUsername())) {
                throw RestApiException.onUnprivileged();
            }
        } else {
            // 管理员用户，不能修改自己的管理员权限。
            if (newUser.getUsername().equals(sessionChecker.getUsername())) {
                if (oldUser.getIsAccountAdmin() != 0 && newUser.getIsAccountAdmin() == 0)
                    throw RestApiException.onInvalidParam("cannot self-revoke account admin privilege");
            }
            // 配置用户权限
            oldUser.setIsAccountAdmin(newUser.getIsAccountAdmin());
            oldUser.setIsAppraisalAdmin(newUser.getIsAppraisalAdmin());
        }
        oldUser.setRemark(newUser.getRemark());
        if (newUser.getPassword() != null && !newUser.getPassword().isEmpty()) {
            // TODO: 11/27/2015 verify hex password
            oldUser.setPassword(newUser.getPassword());
        }

        userSecureDao.update(oldUser);

        return oldUser.toArUser();
    }

    /**
     * 删除用户
     *
     * @param username 待删除的用户
     * @param session  Session
     * @return 如果删除成功，则返回已删除用户的ArUser对象。
     * @throws RestApiException 如果删除失败，则抛出异常。
     */
    @RequestMapping(value = "delete")
    public ArUser Delete(@RequestParam("username") String username,
                         HttpSession session) throws RestApiException {
        // 首先，只有管理员有权限删除用户。
        SessionChecker sessionChecker = new SessionChecker(session);
        sessionChecker.requireAccountAdmin();

        // 检查输入数据
        if (username == null)
            throw RestApiException.onInvalidParam("username is null");
        // 不能删除自己
        if (username.equals(sessionChecker.getUsername()))
            throw RestApiException.onInvalidParam("cannot self delete");
        // 检查用户是否存在
        ArUserSecure fullUser = userSecureDao.findById(username);
        if (fullUser == null)
            throw RestApiException.onInvalidParam("username does not exist");

        userSecureDao.delete(fullUser);

        return fullUser.toArUser();
    }

    /**
     * 列出所有用户
     *
     * @param session Session
     * @return 如果普通用户，则列出所有用户名/备注；如果管理员登陆，则列出所有用户名/备注/权限。
     * @throws RestApiException 如果没有登陆，则抛出异常。
     */
    @RequestMapping(value = "list")
    public List List(HttpSession session) throws RestApiException {
        // 判断当前登录用户
        SessionChecker sessionChecker = new SessionChecker(session);
        sessionChecker.requireAuthorized();

        if (sessionChecker.hasAccountAdmin()) {
            // 管理员
            List<ArUserSecure> result = userSecureDao.findAll();
            // 抹除密码。这段代码不能放在DAO里，因为DAO有@Transactional，在DAO中做的修改，在离开DAO前，会自动commit到数据库中。
            for (ArUserSecure user : result) {
                user.setPassword("");
            }
            return result;
        } else {
            // 普通用户
            return userDao.findAll();
        }
    }

    /**
     * 判断一个用户名是否已使用
     *
     * @param username 用户名
     * @param session  Session
     * @return 如果用户名存在，则返回true；否则返回false。
     * @throws RestApiException 如果没有登陆，则抛出异常。
     */
    @RequestMapping(value = "exist")
    public boolean Exist(@RequestParam("username") String username, HttpSession session) throws RestApiException {
        new SessionChecker(session).requireAuthorized();

        return userDao.findById(username) != null;
    }
}
