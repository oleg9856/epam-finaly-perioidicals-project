package com.gmail.fursovych20.db.dao;

import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.entity.Publication;
import com.gmail.fursovych20.entity.dto.LocalizedPublicationDTO;
import com.gmail.fursovych20.entity.dto.PublicationSearchCriteriaDTO;

import java.util.List;

/**
 * An interface that provides methods for edition and other operation for publication
 *
 * @author O.Fursovych
 */
public interface PublicationDAO {

	/**
	 * A method that searches for a {@code publication} by {@code id} and {@code locale}
	 *
	 * @param id {@code publicationId}
	 * @param locale {@code locale}, which will be searched
	 * @return {@code publication}
	 * @throws DAOException throws exception
	 */
	Publication findPublicationByIdAndLocale(int id, LocaleType locale) throws DAOException;

	/**
	 * A method that searches for publications based on a certain search {@code criteria}
	 *
	 * @param criteria parameter, which will be searched
	 * @return list {@code publication} which found
	 * @throws DAOException throws exception
	 */
	List<Publication> findAllPublicationByCriteria(PublicationSearchCriteriaDTO criteria) throws DAOException;

	/**
	 * A method that searches for publications by a certain search {@code criteria} and {@code name}
	 *
	 * @param criteria param, which will be searched
	 * @param name search {@code name} of {@code publication}
	 * @return list of publication
	 * @throws DAOException throws exception
	 */
	List<Publication> findPublicationsByNameAndCriteria(PublicationSearchCriteriaDTO criteria, String name) throws DAOException;

	/**
	 * A method for creating a new {@code publication}
	 *
	 * @param localizedPublicationDTO parameter is localized publication for creating full {@code publication}
	 * @return boolean true, or false dependency of parameters
	 * @throws DAOException throws exception
	 */
	boolean create(LocalizedPublicationDTO localizedPublicationDTO) throws DAOException;

	/**
	 * A method to {@code update} an existing publication
	 *
	 * @param localizedPublicationDTO parameter is localized publication for update full publication
	 * @return boolean true, or false dependency of parameters
	 * @throws DAOException throws exception
	 */
	boolean update(LocalizedPublicationDTO localizedPublicationDTO) throws DAOException;

	/**
	 * A method to {@code delete} an existing publication
	 *
	 * @param id {@code publicationId} for deleting
	 * @return boolean true, or false dependency of parameters
	 * @throws DAOException throws exception
	 */
	boolean delete(int id) throws DAOException;

	/**
	 * A method that provide read publication
	 *
	 * @param id {@code publicationId} for reading
	 * @return found localized publication
	 * @throws DAOException throws exception
	 */
	LocalizedPublicationDTO readLocalized(int id) throws DAOException;

	/**
	 * A method that provide read publication by localType
	 *
	 * @param id {@code publicationId} for reading
	 * @param localeType locale for reading
	 * @return found localized publication
	 * @throws DAOException throws exception
	 */
	LocalizedPublicationDTO readLocalizedWithLocalization(int id, LocaleType localeType) throws DAOException;

	/**
	 * A method which found count by {@code criteria} in
	 * publication
	 *
	 * @param criteria param, which will be searched
	 * @return {@code totalCount} publication
	 * @throws DAOException throws exception
	 */
	int getTotalCount(PublicationSearchCriteriaDTO criteria) throws DAOException;

}
