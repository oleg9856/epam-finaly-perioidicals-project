package com.gmail.fursovych20.service;

import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.entity.Theme;
import com.gmail.fursovych20.entity.dto.LocalizedThemeDTO;
import com.gmail.fursovych20.service.exception.ServiceException;

import java.util.List;

/**
 * An interface that provides methods for working with themes at the dao level
 */
public interface ThemeService {

	/**
	 * This method find all theme by locale using dao layer
	 *
	 * @param locale param for using dao layer
	 * @return {@code List<Theme>}
	 * @throws ServiceException throws exception
	 */
	List<Theme> findAllThemeByLocaleType(LocaleType locale) throws ServiceException;

	/**
	 * The method which using for find theme by id using dao layer
	 *
	 * @param id param id for using dao layer
	 * @param locale param for using dao layer
	 * @return theme which searching
	 * @throws ServiceException throws exception
	 */
	Theme findThemeById(int id, LocaleType locale) throws ServiceException;

	/**
	 * A method which find all localized theme using dao layer
	 *
	 * @return all {@code List<LocalizedThemeDTO>}
	 * @throws ServiceException throws exception
	 */
	List<LocalizedThemeDTO> findAllLocalized() throws ServiceException;

	/**
	 * This method creating new localized theme using dao layer
	 *
	 * @param theme param using for creating
	 * @return true or false, dependency of logic
	 * @throws ServiceException throws exception
	 */
	boolean create(LocalizedThemeDTO theme) throws ServiceException;

	/**
	 * This method updating localized theme using dao layer
	 *
	 * @param theme param using for updating localized theme
	 * @return true or false, dependency of logic
	 * @throws ServiceException throws exception
	 */
	boolean update(LocalizedThemeDTO theme) throws ServiceException;

}
