package com.bftcom.gui.tableViewStoreObj;

import com.bftcom.dbtools.entity.ViolationGroupKBK;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by k.nikitin on 14.11.2016.
 */
public class ViolationKBKTVObject {
    private final SimpleLongProperty id;
    private final SimpleLongProperty kbkdetail_id;
    private final SimpleStringProperty fsr_caption;
    private final SimpleDoubleProperty amount;
    private final SimpleDoubleProperty cash;
    private final SimpleDoubleProperty account;
    private final SimpleDoubleProperty grbs;
    private final SimpleDoubleProperty budget;

    public ViolationKBKTVObject(ViolationGroupKBK violKBK){
        this.id = new SimpleLongProperty(violKBK.getId().longValue());
        this.kbkdetail_id = new SimpleLongProperty(violKBK.getKbkDetail().getId().longValue());
        this.fsr_caption = new SimpleStringProperty(violKBK.getKbkDetail().getFsr_caption());
        this.amount = new SimpleDoubleProperty(violKBK.getAmount().doubleValue());
        this.cash = new SimpleDoubleProperty(violKBK.getCompensatedToAuditOrgCash().doubleValue());
        this.account = new SimpleDoubleProperty(violKBK.getCompensatedToAuditOrgAccount().doubleValue());
        this.grbs = new SimpleDoubleProperty(violKBK.getCompensatedToGRBSAccount().doubleValue());
        this.budget = new SimpleDoubleProperty(violKBK.getCompensatedToBudget().doubleValue());
    }

    public ViolationKBKTVObject(Long kbkdetail_id, String fsr_caption, Double amout, Double cash, Double account, Double grbs, Double budget){
        this.id = new SimpleLongProperty();
        this.kbkdetail_id = new SimpleLongProperty(kbkdetail_id);
        this.fsr_caption = new SimpleStringProperty(fsr_caption);
        this.amount = new SimpleDoubleProperty(amout);
        this.cash = new SimpleDoubleProperty(cash);
        this.account = new SimpleDoubleProperty(account);
        this.grbs = new SimpleDoubleProperty(grbs);
        this.budget = new SimpleDoubleProperty(budget);
    }

    public long getId() {
        return id.get();
    }

    public SimpleLongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public long getKbkdetail_id() {
        return kbkdetail_id.get();
    }

    public SimpleLongProperty kbkdetail_idProperty() {
        return kbkdetail_id;
    }

    public void setKbkdetail_id(long kbkdetail_id) {
        this.kbkdetail_id.set(kbkdetail_id);
    }

    public String getFsr_caption() {
        return fsr_caption.get();
    }

    public SimpleStringProperty fsr_captionProperty() {
        return fsr_caption;
    }

    public void setFsr_caption(String fsr_caption) {
        this.fsr_caption.set(fsr_caption);
    }

    public double getAmount() {
        return amount.get();
    }

    public SimpleDoubleProperty amountProperty() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public double getCash() {
        return cash.get();
    }

    public SimpleDoubleProperty cashProperty() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash.set(cash);
    }

    public double getAccount() {
        return account.get();
    }

    public SimpleDoubleProperty accountProperty() {
        return account;
    }

    public void setAccount(double account) {
        this.account.set(account);
    }

    public double getGrbs() {
        return grbs.get();
    }

    public SimpleDoubleProperty grbsProperty() {
        return grbs;
    }

    public void setGrbs(double grbs) {
        this.grbs.set(grbs);
    }

    public double getBudget() {
        return budget.get();
    }

    public SimpleDoubleProperty budgetProperty() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget.set(budget);
    }
}
