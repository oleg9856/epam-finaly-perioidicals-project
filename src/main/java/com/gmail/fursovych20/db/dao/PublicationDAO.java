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
	 * A method that searches for a publication by id and locale
	 *
	 * @param id publication id
	 * @param locale locale, which will be searched
	 * @return publication
	 * @throws DAOException throws exception in DAO
	 */
	Publication findPublicationByIdAndLocale(int id, LocaleType locale) throws DAOException;

	/**
	 * A method that searches for publications based on a certain search criterion
	 *
	 * @param criteria criteria, which will be searched
	 * @return list publication which found
	 * @throws DAOException throws exception in DAO
	 */
	List<Publication> findAllPublicationByCriteria(PublicationSearchCriteriaDTO criteria) throws DAOException;

	/**
	 *A method that searches for publications by a certain search criteria and name
	 *
	 * @param criteria criteria, which will be searched
	 * @param name search name of publication
	 * @return list of publication
	 * @throws DAOException throws exception in DAO
	 */
	List<Publication> findPublicationsByNameAndCriteria(PublicationSearchCriteriaDTO criteria, String name) throws DAOException;

	/**
	 * Method for creating a new publication
	 *
	 * @param localizedPublicationDTO localizedPublicationDTO is localized publication for creating full publication
	 * @return boolean true, or false dependency of logic
	 * @throws DAOException throws exception in DAO
	 */
	boolean create(LocalizedPublicationDTO localizedPublicationDTO) throws DAOException;

	/**
	 * A method to update an existing post
	 *
	 * @param localizedPublicationDTO localizedPublicationDTO is localized publication for update full publication
	 * @return boolean true, or false dependency of logic
	 * @throws DAOException throws exception in DAO
	 */
	boolean update(LocalizedPublicationDTO localizedPublicationDTO) throws DAOException;

	/**
	 * A method to delete an existing post
	 *
	 * @param id publication id for deleting
	 * @return boolean true, or false dependency of logic
	 * @throws DAOException throws exception in DAO
	 */
	boolean delete(int id) throws DAOException;

	/**
	 * @param id publication id for reading
	 * @return found localized publication
	 * @throws DAOException throws exception in DAO
	 */
	LocalizedPublicationDTO readLocalized(int id) throws DAOException;

	/**
	 * A method which found count by criteria
	 *
	 * @param criteria criteria, which will be searched
	 * @return total count publication
	 * @throws DAOException throws exception in DAO
	 */
	int getTotalCount(PublicationSearchCriteriaDTO criteria) throws DAOException;

}
