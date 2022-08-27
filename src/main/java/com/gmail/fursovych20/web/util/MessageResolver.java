package com.gmail.fursovych20.web.util;

import com.gmail.fursovych20.entity.LocaleType;

import java.util.ResourceBundle;

public final class MessageResolver {
	
	private MessageResolver() {}
	
	private static final ResourceBundle messagesEn = ResourceBundle.getBundle("localization/messages_en_US");
	private static final ResourceBundle messagesUa = ResourceBundle.getBundle("localization/messages_uk_UA");
	
	public static String getMessage(String messageName, LocaleType locale) {
		if (locale == LocaleType.uk_UA) {
			return messagesUa.getString(messageName);
		}
		return messagesEn.getString(messageName);
	}
			

}
