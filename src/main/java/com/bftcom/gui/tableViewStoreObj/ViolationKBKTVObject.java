package com.bftcom.gui.tableViewStoreObj;

import com.bftcom.dbtools.entity.ViolationGroupKBK;
import com.bftcom.gui.custom.combobox.StoreObject;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.math.BigInteger;

/**
 * Created by k.nikitin on 14.11.2016.
 */
public class ViolationKBKTVObject {
    private final SimpleLongProperty id;
    private final SimpleLongProperty kbkdetail_id;
    private final SimpleObjectProperty fsr_caption;
    private final SimpleStringProperty amount;
    private final SimpleStringProperty cash;
    private final SimpleStringProperty account;
    private final SimpleStringProperty grbs;
    private final SimpleStringProperty budget;

    public ViolationKBKTVObject(ViolationGroupKBK violKBK){
        this.id = new SimpleLongProperty(violKBK.getId().longValue());
        this.kbkdetail_id = violKBK.getKbkDetail() != null ? new SimpleLongProperty(violKBK.getKbkDetail().getId().longValue()) : new SimpleLongProperty();
        this.fsr_caption = violKBK.getKbkDetail() != null ? new SimpleObjectProperty(new StoreObject(violKBK.getKbkDetail().getId(),violKBK.getKbkDetail().getFsr_caption())) : new SimpleObjectProperty(new StoreObject(null,""));
        this.amount = new SimpleStringProperty(violKBK.getAmount().toString());
        this.cash = new SimpleStringProperty(violKBK.getCompensatedToAuditOrgCash().toString());
        this.account = new SimpleStringProperty(violKBK.getCompensatedToAuditOrgAccount().toString());
        this.grbs = new SimpleStringProperty(violKBK.getCompensatedToGRBSAccount().toString());
        this.budget = new SimpleStringProperty(violKBK.getCompensatedToBudget().toString());
    }

    public ViolationKBKTVObject(Long kbkdetail_id, String fsr_caption, String amount, String cash, String account, String grbs, String budget){
        this.id = new SimpleLongProperty();
        this.kbkdetail_id = kbkdetail_id != null ? new SimpleLongProperty(kbkdetail_id) : new SimpleLongProperty();
        this.fsr_caption = kbkdetail_id != null ? new SimpleObjectProperty(new StoreObject(BigInteger.valueOf(kbkdetail_id),fsr_caption)) : new SimpleObjectProperty(new StoreObject(null,""));
        this.amount =  amount == null || amount.isEmpty() ? new SimpleStringProperty("0.0") : new SimpleStringProperty(amount);
        this.cash = cash == null || cash.isEmpty() ? new SimpleStringProperty("0.0") :  new SimpleStringProperty(cash);
        this.account = account == null || account.isEmpty() ? new SimpleStringProperty("0.0") : new SimpleStringProperty(account);
        this.grbs = grbs == null || grbs.isEmpty() ? new SimpleStringProperty("0.0") : new SimpleStringProperty(grbs);
        this.budget =  budget == null || budget.isEmpty() ? new SimpleStringProperty("0.0") : new SimpleStringProperty(budget);
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

    public Object getFsr_caption() {
        return fsr_caption.get();
    }

    public SimpleObjectProperty fsr_captionProperty() {
        return fsr_caption;
    }

    public void setFsr_caption(Object fsr_caption) {
        this.fsr_caption.set(fsr_caption);
    }

    public String getAmount() {
        return amount.get();
    }

    public SimpleStringProperty amountProperty() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount.set(amount);
    }

    public String getCash() {
        return cash.get();
    }

    public SimpleStringProperty cashProperty() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash.set(cash);
    }

    public String getAccount() {
        return account.get();
    }

    public SimpleStringProperty accountProperty() {
        return account;
    }

    public void setAccount(String account) {
        this.account.set(account);
    }

    public String getGrbs() {
        return grbs.get();
    }

    public SimpleStringProperty grbsProperty() {
        return grbs;
    }

    public void setGrbs(String grbs) {
        this.grbs.set(grbs);
    }

    public String getBudget() {
        return budget.get();
    }

    public SimpleStringProperty budgetProperty() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget.set(budget);
    }
}
