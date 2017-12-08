package com.bftcom.dbtools.entity;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * Created by k.nikitin on 29.11.2017.
 */
@Entity
@Table(name = "SYSPARAM")
public class SystemParameters {
    @Id
    @GeneratedValue
    @Column(name="ID", nullable=false, precision = 15, scale = 0)
    private BigInteger id;

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @Column(name="PARAMVALUE", columnDefinition = "clob")
    @Lob
    private String value;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
