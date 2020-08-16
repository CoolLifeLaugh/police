package com.lhsj.police.dingding.request;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

import static java.util.Objects.isNull;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

public class MultiActionCard implements Serializable {

    private String    title;
    private String    text;
    private String    btnOrientation;
    private List<Btn> btns;

    public MultiActionCard() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getBtnOrientation() {
        return btnOrientation;
    }

    public void setBtnOrientation(String btnOrientation) {
        this.btnOrientation = btnOrientation;
    }

    public List<Btn> getBtns() {
        return btns;
    }

    public void setBtns(List<Btn> btns) {
        this.btns = btns;
    }

    // --------- flow api ----------

    public static MultiActionCard of() {
        return new MultiActionCard();
    }

    public MultiActionCard title(String title) {
        this.title = title;
        return this;
    }

    public MultiActionCard text(String text) {
        this.text = text;
        return this;
    }

    public MultiActionCard btnOrientation(String btnOrientation) {
        this.btnOrientation = btnOrientation;
        return this;
    }

    public MultiActionCard btn(Btn btn) {
        if (isNull(btn)) {
            return this;
        }

        if (isNull(btns)) {
            btns = Lists.newArrayList(btn);
        } else {
            btns.add(btn);
        }

        return this;
    }

    public MultiActionCard btns(List<Btn> btns) {
        if (isEmpty(btns)) {
            return this;
        }

        if (isNull(this.btns)) {
            this.btns = Lists.newArrayList(btns);
        } else {
            this.btns.addAll(btns);
        }

        return this;
    }
}
