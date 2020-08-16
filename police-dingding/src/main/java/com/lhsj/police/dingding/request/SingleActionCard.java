package com.lhsj.police.dingding.request;

import java.io.Serializable;

public class SingleActionCard implements Serializable {

    private String title;
    private String text;
    private String btnOrientation;
    private String singleTitle;
    private String singleURL;

    public SingleActionCard() {
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

    public String getSingleTitle() {
        return singleTitle;
    }

    public void setSingleTitle(String singleTitle) {
        this.singleTitle = singleTitle;
    }

    public String getSingleURL() {
        return singleURL;
    }

    public void setSingleURL(String singleURL) {
        this.singleURL = singleURL;
    }

    // --------- flow api ----------

    public static SingleActionCard of() {
        return new SingleActionCard();
    }

    public SingleActionCard title(String title) {
        this.title = title;
        return this;
    }

    public SingleActionCard text(String text) {
        this.text = text;
        return this;
    }

    public SingleActionCard btnOrientation(String btnOrientation) {
        this.btnOrientation = btnOrientation;
        return this;
    }

    public SingleActionCard singleTitle(String singleTitle) {
        this.singleTitle = singleTitle;
        return this;
    }

    public SingleActionCard singleURL(String singleURL) {
        this.singleURL = singleURL;
        return this;
    }
}
