package com.gmail.fursovych20.web.util;

import com.gmail.fursovych20.entity.LocaleType;

import java.util.ResourceBundle;

/**
 * Bundle message resolver
 *
 * @author O.Fursovych
 */
public final class MessageResolver {
	
	private MessageResolver() {}
	
	private static final ResourceBundle messagesEn = ResourceBundle.getBundle("localization/messages_en_US");
	private static final ResourceBundle messagesUa = ResourceBundle.getBundle("localization/messages_uk_UA");

	/**
	 * Method for receiving messages from resource bundle
	 *
	 * @param messageName the name of the message
	 * @param locale the locale message
	 * @return Message content
	 */
	public static String getMessage(String messageName, LocaleType locale) {
		if (locale == LocaleType.uk_UA) {
			return messagesUa.getString(messageName);
		}
		return messagesEn.getString(messageName);
	}
			

}
