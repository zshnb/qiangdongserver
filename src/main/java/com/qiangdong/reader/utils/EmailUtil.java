package com.qiangdong.reader.utils;

import com.qiangdong.reader.config.EmailConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class EmailUtil {

    @Autowired
    private EmailConfig emailConfig;

    public void sendEmail(String receiver, String subject, String content) throws MessagingException {
        int port = 465;
        boolean isSSL = true;
        boolean isAuth = true;
        Properties props = new Properties();
        props.put("mail.smtp.ssl.enable", isSSL);
        props.put("mail.smtp.host", emailConfig.getHost());
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", isAuth);
        Authenticator authenticator = new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailConfig.getAccount(), emailConfig.getPassword());
            }
        };
        Session session = Session.getDefaultInstance(props, authenticator);
        session.setDebug(true);
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(emailConfig.getAccount()));
        message.setRecipients(RecipientType.TO, InternetAddress.parse(receiver));
        message.setSubject(subject);
        message.setContent(content, "text/html;charset=UTF-8");
        Transport.send(message);
    }

    public boolean isValidEmail(String email){
        String emailRegex= "^[A-Za-z0-9]+([_\\.][A-Za-z0-9]+)*@([A-Za-z0-9\\-]+\\.)+[A-Za-z]{2,6}$";
        Pattern p = Pattern.compile(emailRegex);
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
