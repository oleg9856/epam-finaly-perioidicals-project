package com.gmail.fursovych20.service;

import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.entity.Type;
import com.gmail.fursovych20.entity.dto.LocalizedTypeDTO;
import com.gmail.fursovych20.service.exception.ServiceException;

import java.util.List;
/**
 * An interface that provides methods for working with types at the dao level
 */
public interface TypeService {

	/**
	 * The method which find all types by locale using dao layer
	 *
	 * @param locale param locale using for searching
	 * @return {@code List<Type>}
	 * @throws ServiceException throws exception
	 */
	List<Type> findAllTypeServiceByLocaleType(LocaleType locale) throws ServiceException;

	/**
	 * The method which find type by locale and id using dao layer
	 *
	 * @param id param, using for searching type
	 * @param locale param, using for searching type
	 * @return {@code Type}
	 * @throws ServiceException throws exception
	 */
	Type findTypeServiceById(int id, LocaleType locale) throws ServiceException;

	/**
	 * This method find all localized using dao layer
	 *
	 * @return {@code List<LocalizedTypeDTO>}
	 * @throws ServiceException throws exception
	 */
	List<LocalizedTypeDTO> findAllLocalized() throws ServiceException;

	/**
	 * This method creating new localized type using dao layer
	 *
	 * @param type param using for creating
	 * @return true or false, dependency of logic
	 * @throws ServiceException throws exception
	 */
	boolean create(LocalizedTypeDTO type) throws ServiceException;

	/**
	 * This method updating localized type using dao layer
	 *
	 * @param type param using for updating localized theme
	 * @return true or false, dependency of logic
	 * @throws ServiceException throws exception
	 */
	boolean update(LocalizedTypeDTO type) throws ServiceException;

}
