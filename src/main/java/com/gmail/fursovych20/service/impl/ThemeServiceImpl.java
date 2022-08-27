package com.gmail.fursovych20.service.impl;

import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.db.dao.ThemeDAO;
import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.entity.Theme;
import com.gmail.fursovych20.entity.dto.LocalizedThemeDTO;
import com.gmail.fursovych20.service.ThemeService;
import com.gmail.fursovych20.service.exception.ServiceException;
import com.gmail.fursovych20.service.exception.ValidationException;
import com.gmail.fursovych20.service.util.Validator;

import java.util.List;

public class ThemeServiceImpl implements ThemeService {
	
	private final ThemeDAO themeDao;

	public ThemeServiceImpl(ThemeDAO themeDao) {
		this.themeDao = themeDao;
	}

	public List<Theme> findAllThemeByLocaleType(LocaleType locale) throws ServiceException {
		try {
			return themeDao.findAll(locale);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Theme findThemeById(int id, LocaleType locale) throws ServiceException {
		try {
			return themeDao.findThemeById(id, locale);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<LocalizedThemeDTO> findAllLocalized() throws ServiceException {
		try {
			return themeDao.findAllLocalized();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean create(LocalizedThemeDTO theme) throws ServiceException{
		if (Validator.localizedThemeIsValid(theme)) {
			throw new ValidationException("Localized theme data is not valid");
		}
		try {
			return themeDao.create(theme);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean update(LocalizedThemeDTO theme) throws ServiceException {
		if (Validator.localizedThemeIsValid(theme)) {
			throw new ValidationException("Localized theme data is not valid");
		}
		try {
			return themeDao.update(theme);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

}
