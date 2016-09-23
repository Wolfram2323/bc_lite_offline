package com.bftcom.dbtools.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;

/**
 * Created by k.nikitin on 22.09.2016.
 */
@Entity
@Table(name = "SYSUSER")
public class SysUser {

    @Id
    @Column(name = "ID", nullable=false, precision = 15, scale = 0)
    private BigInteger id;

    @Column(name="USERNAME", unique = true, length = 30)
    private String login;

    @Column(name="PSSWD", length = 50)
    private String psswd;  //todo шифрование

    @Column(name="IS_ADMIN")
    private Boolean is_admin;


    public void setId(BigInteger id){
        this.id = id;
    }
    public BigInteger getId(){
        return id;
    }

    public void setLogin(String login){
        this.login = login;
    }

    public String getLogin(){
        return login;
    }

    public void setPsswd(String psswd){
        this.psswd = psswd;
    }

    public String getPsswd(){
        return psswd;
    }

    public void setIs_admin(Boolean is_admin){
        this.is_admin = is_admin;
    }

    public Boolean getIs_admin(){
        return is_admin;
    }

}
