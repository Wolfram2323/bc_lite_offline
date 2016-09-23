package com.bftcom.dbtools.entity;

import javax.persistence.*;
import javax.persistence.metamodel.ListAttribute;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by k.nikitin on 23.09.2016.
 */
@Entity
@Table(name="VIOLATION")
public class Violation {
    @Id
    @Column(name = "ID", nullable = false, precision = 15, scale = 0)
    private BigInteger id;

    @Column(name = "CAPTION", length = 2000)
    private String caption;

    @Column(name="CODE", length = 100)
    private String code;

    @Column(name="DESCRIPTION", length = 2000)
    private String description;


    @OneToMany
    @JoinColumn(name="PARENT_ID", foreignKey = @ForeignKey(name="FK_VIOLATION_VIOLATION"))
    private List<Violation> violation = new LinkedList<>();

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Violation> getViolation() {
        return violation;
    }

    public void setViolation(List<Violation> violation) {
        this.violation = violation;
    }
}
