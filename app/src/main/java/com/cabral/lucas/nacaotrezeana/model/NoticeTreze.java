package com.cabral.lucas.nacaotrezeana.model;

/**
 * Created by Lucas on 11/01/2017.
 */
public class NoticeTreze {
    private String description, linkImage, publishedBy, linkNotice;
    private Boolean errorImage = false;
    private int type = 1;

    public NoticeTreze(String description, String linkImage, String publishedBy, String linkNotice) {
        this.description = description;
        this.linkImage = linkImage;
        this.publishedBy = publishedBy;
        this.linkNotice = linkNotice;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }

    public String getDescription() {
        return description;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public String getPublishedBy() {
        return publishedBy;
    }

    public Boolean getErrorImage() {
        return errorImage;
    }

    public void setErrorImage(Boolean errorImage) {
        this.errorImage = errorImage;
    }

    public String getLinkNotice() {
        return linkNotice;
    }

    @Override
    public String toString() {
        return this.getLinkImage() + " - " + this.getDescription();
    }

    public int getType() {
        return type;
    }
}
