package com.bftcom.gui.tableViewStoreObj;

import com.bftcom.dbtools.entity.BrokenNPA;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by k.nikitin on 14.11.2016.
 */
public class BrokenNpaTVObject {
    private final SimpleLongProperty id;
    private final SimpleStringProperty caption;
    private final SimpleStringProperty look;
    private final SimpleStringProperty num;
    private final SimpleStringProperty date;
    private final SimpleStringProperty chapter;
    private final SimpleStringProperty clause;
    private final SimpleStringProperty parag;
    private final SimpleStringProperty subParag;
    private final SimpleStringProperty rubric;

    public BrokenNpaTVObject (BrokenNPA bnpa) {
        this.id = new SimpleLongProperty(bnpa.getId().longValue());
        this.caption = new SimpleStringProperty(bnpa.getCaption());
        this.look = new SimpleStringProperty(bnpa.getLook());
        this.num = new SimpleStringProperty(bnpa.getNum());
        this.date = new SimpleStringProperty(bnpa.getDate_npa().toLocalDate().toString());
        this.chapter = new SimpleStringProperty(bnpa.getChapter());
        this.clause = new SimpleStringProperty(bnpa.getClause());
        this.parag = new SimpleStringProperty(bnpa.getParagraph());
        this.subParag = new SimpleStringProperty(bnpa.getSubparagraph());
        this.rubric = new SimpleStringProperty(bnpa.getRubric());
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

    public String getCaption() {
        return caption.get();
    }

    public SimpleStringProperty captionProperty() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption.set(caption);
    }

    public String getLook() {
        return look.get();
    }

    public SimpleStringProperty lookProperty() {
        return look;
    }

    public void setLook(String look) {
        this.look.set(look);
    }

    public String getNum() {
        return num.get();
    }

    public SimpleStringProperty numProperty() {
        return num;
    }

    public void setNum(String num) {
        this.num.set(num);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getChapter() {
        return chapter.get();
    }

    public SimpleStringProperty chapterProperty() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter.set(chapter);
    }

    public String getClause() {
        return clause.get();
    }

    public SimpleStringProperty clauseProperty() {
        return clause;
    }

    public void setClause(String clause) {
        this.clause.set(clause);
    }

    public String getParag() {
        return parag.get();
    }

    public SimpleStringProperty paragProperty() {
        return parag;
    }

    public void setParag(String parag) {
        this.parag.set(parag);
    }

    public String getSubParag() {
        return subParag.get();
    }

    public SimpleStringProperty subParagProperty() {
        return subParag;
    }

    public void setSubParag(String subParag) {
        this.subParag.set(subParag);
    }

    public String getRubric() {
        return rubric.get();
    }

    public SimpleStringProperty rubricProperty() {
        return rubric;
    }

    public void setRubric(String rubric) {
        this.rubric.set(rubric);
    }

    public static List<TableColumn> createColumns() {
        List<TableColumn> result = new LinkedList<>();
        TableColumn id = new TableColumn();
        id.setCellValueFactory(new PropertyValueFactory<BrokenNpaTVObject, Long>("id"));
        id.setEditable(false);
        id.setVisible(false);
        result.add(id);
        TableColumn caption =  new TableColumn ("Наименование");
        caption.setCellValueFactory(new PropertyValueFactory<BrokenNpaTVObject,String>("caption"));
        caption.setEditable(false);
        result.add(caption);
        TableColumn look =  new TableColumn ("Вид");
        look.setCellValueFactory(new PropertyValueFactory<BrokenNpaTVObject,String>("look"));
        look.setEditable(false);
        result.add(look);
        TableColumn num =  new TableColumn ("Номер");
        num.setCellValueFactory(new PropertyValueFactory<BrokenNpaTVObject,String>("num"));
        num.setEditable(false);
        result.add(num);
        TableColumn date =  new TableColumn ("Дата");
        date.setCellValueFactory(new PropertyValueFactory<BrokenNpaTVObject,String>("date"));
        date.setEditable(false);
        result.add(date);
        TableColumn chapter =  new TableColumn ("Раздел/Глава");
        chapter.setCellValueFactory(new PropertyValueFactory<BrokenNpaTVObject,String>("chapter"));
        chapter.setEditable(false);
        result.add(chapter);
        TableColumn clause =  new TableColumn ("Статья");
        clause.setCellValueFactory(new PropertyValueFactory<BrokenNpaTVObject,String>("clause"));
        clause.setEditable(false);
        result.add(clause);
        TableColumn parag =  new TableColumn ("Часть/Пункт");
        parag.setCellValueFactory(new PropertyValueFactory<BrokenNpaTVObject,String>("parag"));
        parag.setEditable(false);
        result.add(parag);
        TableColumn subParag =  new TableColumn ("Подпункт");
        subParag.setCellValueFactory(new PropertyValueFactory<BrokenNpaTVObject,String>("subParag"));
        subParag.setEditable(false);
        result.add(subParag);
        TableColumn rubric =  new TableColumn ("Абзац");
        rubric.setCellValueFactory(new PropertyValueFactory<BrokenNpaTVObject,String>("rubric"));
        rubric.setEditable(false);
        result.add(rubric);


        return result;
    }
}
