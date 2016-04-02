package net.github.rtc.app.model.dto.message;

import java.util.Date;

public class MessageDto {

    private String code;

    private String userName;

    private String subject;

    private String description;

    private Date sendingDate;

    public MessageDto() {
    }

    public MessageDto(String userName, String subject, String description, Date sendingDate) {
        this.userName = userName;
        this.subject = subject;
        this.description = description;
        if (sendingDate != null) {
            this.sendingDate = new Date(sendingDate.getTime());
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getSendingDate() {
        return sendingDate == null ? null : new Date(sendingDate.getTime());
    }

    public void setSendingDate(Date sendingDate) {
        if (sendingDate != null) {
            this.sendingDate = new Date(sendingDate.getTime());
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
