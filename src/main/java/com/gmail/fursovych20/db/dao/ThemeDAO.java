package com.gmail.fursovych20.db.dao;

import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.entity.Theme;
import com.gmail.fursovych20.entity.dto.LocalizedThemeDTO;

import java.util.List;

/**
 * An interface that provides methods for edition and other operation
 * for theme
 *
 * @author O.Fursovych
 */
public interface ThemeDAO {

	/**
	 * The method which provide found all themes by {@code local}
	 *
	 * @param locale parameter locale in search
	 * @return List themes which found with this parameters
	 * @throws DAOException throws exception
	 */
	List<Theme> findAll(LocaleType locale) throws DAOException;

	/**
	 * The method which provide found theme by {@code id} and {@code local}
	 *
	 * @param id param, by which the search is performed
	 * @param locale the locale for which sampling takes place
	 * @return theme which found with this parameters
	 * @throws DAOException throws exception
	 */
	Theme findThemeById(Integer id, LocaleType locale) throws DAOException;

	/**
	 * The method which find all themes with locales
	 *
	 * @return all locale themes with database
	 * @throws DAOException throws exception
	 */
	List<LocalizedThemeDTO> findAllLocalized() throws DAOException;

	/**
	 * The method that {@code update} {@code localizedThemesDTO} using other object theme localized
	 *
	 * @param localizedThemeDTO parameter entity which using for update
	 * @return boolean true, or false dependency of logic
	 * @throws DAOException throws exception
	 */
	boolean update(LocalizedThemeDTO localizedThemeDTO) throws DAOException;

	/**
	 * The method that {@code create} {@code localizedThemesDTO} using object theme localized
	 *
	 * @param localizedThemeDTO parameter entity which using for create
	 * @return boolean true, or false dependency of logic
	 * @throws DAOException throws exception
	 */
	boolean create(LocalizedThemeDTO localizedThemeDTO)  throws DAOException;

}
