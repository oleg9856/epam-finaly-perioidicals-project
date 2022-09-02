package com.gmail.fursovych20.db.dao;

import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.entity.Type;
import com.gmail.fursovych20.entity.dto.LocalizedTypeDTO;

import java.util.List;

/**
 * An interface that provides methods for edition and other operation for type
 *
 * @author O.Fursovych
 */
public interface TypeDAO {

	/**
	 * A method which return all type by locale
	 *
	 * @param locale the locale for which sampling takes place
	 * @return all type by locale
	 * @throws DAOException throws exception
	 */
	List<Type> findAllType(LocaleType locale) throws DAOException;

	/**
	 * A method which return type by {@code id} and locale
	 *
	 * @param id param by which the search is performed
	 * @param locale the locale for which sampling takes place
	 * @return Type find
	 * @throws DAOException throws exception
	 */
	Type findTypeById(Integer id, LocaleType locale) throws DAOException;

	/**
	 * A method which return all types
	 *
	 * @return all types with all locales
	 * @throws DAOException throws exception
	 */
	List<LocalizedTypeDTO> findAllLocalizedTypes() throws DAOException;

	/**
	 * A method that {@code update} {@code localizedTypeDTO} using other object type localized
	 *
	 * @param localizedTypeDTO parameter entity which using for update
	 * @return boolean true, or false dependency of parameters
	 * @throws DAOException throws exception
	 */
	boolean update(LocalizedTypeDTO localizedTypeDTO) throws DAOException;

	/**
	 * A method that {@code create} {@code localizedTypeDTO} using object type localized
	 *
	 * @param localizedTypeDTO parameter entity which using for create
	 * @return boolean true, or false dependency of parameters
	 * @throws DAOException throws exception
	 */
	boolean create(LocalizedTypeDTO localizedTypeDTO)  throws DAOException;

}
