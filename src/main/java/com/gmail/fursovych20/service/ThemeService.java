package com.gmail.fursovych20.service;

import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.entity.Theme;
import com.gmail.fursovych20.entity.dto.LocalizedThemeDTO;
import com.gmail.fursovych20.service.exception.ServiceException;

import java.util.List;

public interface ThemeService {
	
	List<Theme> findAllThemeByLocaleType(LocaleType locale) throws ServiceException;
	
	Theme findThemeById(int id, LocaleType locale) throws ServiceException;
	
	List<LocalizedThemeDTO> findAllLocalized() throws ServiceException;
	
	boolean create(LocalizedThemeDTO theme) throws ServiceException;
	
	boolean update(LocalizedThemeDTO theme) throws ServiceException;

}
