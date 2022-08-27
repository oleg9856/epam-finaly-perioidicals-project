package com.gmail.fursovych20.service.util;

import com.gmail.fursovych20.entity.Issue;
import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.entity.User;
import com.gmail.fursovych20.entity.dto.LocalizedPublicationDTO;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import static javax.mail.Message.RecipientType.TO;

public class MailSender {

    private static final Logger logger = LogManager.getLogger(MailSender.class);

    private static final String AUTH_EMAIL = "auth.email";
    private static final String AUTH_PASSWORD = "auth.password";
    private static final String AUTH_PROPERTIES = "mail_authentication";
    private static final String FROM_HEADER = "periodicials.site@gmail.com";
    private static final String SUBJECT_HEADER = "New issue on the Periodicals site";

    private static final String MESSAGE_TEXT = " Hello, %s %s!\n\n"
            + "We have a new issue of the %s - %s\n\n";

    private MailSender() {
    }

    private static final Properties props;

    static {
        props = System.getProperties();
        ResourceBundle bundle = ResourceBundle.getBundle("mail");
        for (String key : bundle.keySet()) {
            props.put(key, bundle.getString(key));
        }
    }

    public static void sendNotifications(List<User> users, Issue issue, LocalizedPublicationDTO localizedPublicationDTO) {
        ResourceBundle authData = ResourceBundle.getBundle(AUTH_PROPERTIES);
        // Rest of the imports

        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(authData.getString(AUTH_EMAIL), authData.getString(AUTH_PASSWORD));
            }
        };

        try {
            for (User user : users) {
                MimeMessage message = formMessage(authenticator, user, issue, localizedPublicationDTO);
                Transport.send(message);
            }
        } catch (MessagingException e) {
            logger.warn("Exception sending email", e);
        }

    }

    private static MimeMessage formMessage(Authenticator authenticator, User user, Issue issue, LocalizedPublicationDTO localizedPublicationDTO) throws MessagingException {
        Session session = Session.getDefaultInstance(props, authenticator);
        MimeMessage message = new MimeMessage(session);

        message.setFrom(FROM_HEADER);
        message.setRecipient(TO, new InternetAddress(user.getEmail()));
        message.setSubject(SUBJECT_HEADER);
        message.setText(String.format(MESSAGE_TEXT, user.getName(),
                user.getSurName(),
                localizedPublicationDTO.getNames().get(LocaleType.en_US),
                issue.getDescription()));

        return message;
    }

}
