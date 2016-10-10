package com.bftcom.dbtools.entity;

import com.bftcom.dbtools.annotations.OnLineColumnInfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;

/**
 * Created by k.nikitin on 22.09.2016.
 */
@Entity
@Table(name="VIOLATIONTYPE")
public class ViolationType {
    @Id
    @Column(name="ID", nullable=false, precision = 15, scale = 0)
    @OnLineColumnInfo
    private BigInteger id;

    @Column(name="CAPTION", length = 255)
    @OnLineColumnInfo
    private String caption;

    @Column(name="DESCRIPTION", length = 500)
    @OnLineColumnInfo
    private String description;

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

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
