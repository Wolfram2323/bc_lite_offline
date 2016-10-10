package com.bftcom.dbtools.entity;

import com.bftcom.dbtools.annotations.OnLineColumnInfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;
import java.sql.Date;

/**
 * Created by k.nikitin on 22.09.2016.
 */
@Entity
@Table(name="BROKENNPA")
public class BrokenNPA {
    @Id
    @Column(name="ID", nullable=false, precision = 15, scale = 0)
    @OnLineColumnInfo
    private BigInteger id;

    @Column(name="CAPTION", length = 500)
    @OnLineColumnInfo
    private String caption;

    @Column(name="LOOK", length = 500)
    @OnLineColumnInfo
    private String look;

    @Column(name="NUM", length = 500)
    @OnLineColumnInfo
    private String num;

    @Column(name="DATE_BNPA", length = 500)
    @OnLineColumnInfo
    private Date date_npa;

    @Column(name="CHAPTER", length = 500)
    @OnLineColumnInfo
    private String chapter;

    @Column(name="CLAUSE", length = 500)
    @OnLineColumnInfo
    private String clause;

    @Column(name="PARAGRAPH", length = 500)
    @OnLineColumnInfo
    private String paragraph;

    @Column(name="SUBPARAGRAPH", length = 500)
    @OnLineColumnInfo
    private String subparagraph;

    @Column(name="RUBRIC", length = 500)
    @OnLineColumnInfo
    private String rubric;

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

    public String getLook() {
        return look;
    }

    public void setLook(String look) {
        this.look = look;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public Date getDate_npa() {
        return date_npa;
    }

    public void setDate_npa(Date date_npa) {
        this.date_npa = date_npa;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getClause() {
        return clause;
    }

    public void setClause(String clause) {
        this.clause = clause;
    }

    public String getParagraph() {
        return paragraph;
    }

    public void setParagraph(String paragraph) {
        this.paragraph = paragraph;
    }

    public String getSubparagraph() {
        return subparagraph;
    }

    public void setSubparagraph(String subparagraph) {
        this.subparagraph = subparagraph;
    }

    public String getRubric() {
        return rubric;
    }

    public void setRubric(String rubric) {
        this.rubric = rubric;
    }

}
