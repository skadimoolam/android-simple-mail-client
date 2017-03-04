package dev.adi.poc.simplemailserver.model;

import com.afollestad.ason.AsonName;

public class MailItem {

    public String messageID;
    public String subject;
    public String message;
    public @AsonName(name = "useremail") String userEmail;
    public @AsonName(name = "create_date") String createDate;

    public MailItem() {}

    public MailItem(String messageID, String subject, String message, String userEmail, String createDate) {
        this.messageID = messageID;
        this.subject = subject;
        this.message = message;
        this.userEmail = userEmail;
        this.createDate = createDate;
    }

}
