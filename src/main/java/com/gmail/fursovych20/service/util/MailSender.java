package com.gmail.fursovych20.service.util;

import com.gmail.fursovych20.entity.Issue;
import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.entity.User;
import com.gmail.fursovych20.entity.dto.LocalizedPublicationDTO;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.gmail.fursovych20.web.util.MessageResolver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import static javax.mail.Message.RecipientType.TO;

/**
 * Class for sending message to email
 *
 * @author O.Fursovych
 */
public class MailSender {

    private static final Logger logger = LogManager.getLogger(MailSender.class);

    private static final String AUTH_EMAIL = "auth.email";
    private static final String AUTH_PASSWORD = "auth.password";
    private static final String AUTH_PROPERTIES = "mail_authentication";
    private static final String FROM_HEADER = "periodicials.site@gmail.com";
    private static final String SUBJECT_HEADER = "message.subject_header";
    private static final String GREETING = "message.hello";
    private static final String NEW_PUBLICATION = "message.new_issue";
    private static final String ADDED = "message.added";
    private static final String VISIT_SITE = "message.visit";
    private static final String PUBLICATION = "message.new_publication";

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

    /**
     * A method for send message to email
     *
     * @param users param which using for send message to email and for show information in message
     * @param issue issue for show information in message
     * @param localizedPublicationDTO localized publication using for show information in message
     * @param locale locale message
     */
    public static void sendNotifications(List<User> users, Issue issue, LocalizedPublicationDTO localizedPublicationDTO, LocaleType locale) {
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
                MimeMessage message = formMessage(authenticator, user, issue, localizedPublicationDTO, locale);
                Transport.send(message);
            }
        } catch (MessagingException e) {
            logger.warn("Exception sending email", e);
        }

    }

    private static MimeMessage formMessage(Authenticator authenticator, User user, Issue issue, LocalizedPublicationDTO localizedPublicationDTO, LocaleType locale) throws MessagingException {
        Session session = Session.getDefaultInstance(props, authenticator);
        MimeMessage message = new MimeMessage(session);

        message.setFrom(FROM_HEADER);
        message.setRecipient(TO, new InternetAddress(user.getEmail()));
        message.setSubject(MessageResolver.getMessage(SUBJECT_HEADER, locale));

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(
                "<div style=\"background: rgba(220,240,246,0.37) \">\n" +
                        "<div style=\"text-align: center;font-style: italic; text-decoration: dashed\">\n" +
                        "    "+MessageResolver.getMessage(GREETING, locale)+" "+user.getName()+" "+user.getSurName()+"\n" +
                        "</div>\n" +
                        "    <div>\n" +
                        "        <div>\n" +
                        "            <h5 style=\"text-align: center;font-style: oblique\" >"+MessageResolver.getMessage(PUBLICATION, locale)+"</h5>\n" +
                        "            <p>"+MessageResolver.getMessage(NEW_PUBLICATION, locale)+" "+localizedPublicationDTO.getNames().get(locale)
                        +" "+MessageResolver.getMessage(ADDED, locale)+" "+localizedPublicationDTO.getNames().get(locale)+" - "+issue.getDescription()+"</p>\n" +
                        "            <a href=\"http://127.0.0.1:8080/periodicals_epam_war_exploded/\">"+MessageResolver.getMessage(VISIT_SITE, locale)+"</a>\n" +
                        "        </div>\n" +
                        "    </div>\n" +
                        "</div>"
                , "text/html; charset=utf-8");


        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        return message;
    }

}
