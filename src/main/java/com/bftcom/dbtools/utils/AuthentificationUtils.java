package com.bftcom.dbtools.utils;


import com.bftcom.context.Context;
import com.bftcom.dbtools.entity.SysUser;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;

/**
 * Created by k.nikitin on 06.11.2016.
 */
public class AuthentificationUtils {

    public static boolean confirmAthorizate(String login, String passwd){
        try(Session session = HibernateUtils.getCurrentSession()){
            Query<SysUser> query = session.createQuery("from SysUser where login = :login",SysUser.class).setParameter("login",login);
            try{
                SysUser user = query.getSingleResult();
                String storedPswd = user.getPsswd();
                String version = PasswordUtils.getVersion(storedPswd);
                Context.getCurrentContext().setIsAdmin(user.getIs_admin());
                Context.getCurrentContext().setUser_id(user.getId());
                storedPswd = storedPswd.substring(5,storedPswd.length());
                PasswordUtils.Algorithm algorithm = PasswordUtils.getAlgorithm(version);
                String pswdForCheck = algorithm.encodePassword(login,passwd);
                return storedPswd.equals(pswdForCheck);
            } catch (NoResultException e){
                return  false;
            }
        }
    }
}
