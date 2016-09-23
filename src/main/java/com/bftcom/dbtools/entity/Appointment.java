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
@Table(name="APPOINMENT")
public class Appointment {
    @Id
    @Column(name="ID", nullable=false, precision = 15, scale = 0)
    private BigInteger id;

    @Column(name="CAPTION", length = 255)
    private String caption;

    public void setId(BigInteger id){
        this.id = id;
    }

    public BigInteger getId(){
        return id;
    }

    public void setCaption(String caption){
        this.caption = caption;
    }

    public String getCaption(){
        return caption;
    }






}
