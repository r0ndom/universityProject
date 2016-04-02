package net.github.rtc.app.model.entity.activity;


import net.github.rtc.app.model.entity.AbstractPersistenceObject;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Activity extends AbstractPersistenceObject implements java.io.Serializable {

    private String username;

    @Enumerated(EnumType.STRING)
    private ActivityEntity entity;

    private String detail;

    @Enumerated(EnumType.STRING)
    private ActivityAction action;

    @Temporal(TemporalType.TIMESTAMP)
    private Date actionDate = new Date();

    public Activity() { }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getActionDate() {
        return actionDate == null ? null : new Date(actionDate.getTime());
    }

    public void setActionDate(final Date actionDate) {
        if (actionDate != null) {
            this.actionDate = new Date(actionDate.getTime());
        }
    }

    public ActivityEntity getEntity() {
        return entity;
    }

    public void setEntity(ActivityEntity entity) {
        this.entity = entity;
    }

    public ActivityAction getAction() {
        return action;
    }

    public void setAction(ActivityAction action) {
        this.action = action;
    }
}