package com.gmail.fursovych20.service;

import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.entity.Publication;
import com.gmail.fursovych20.entity.dto.LocalizedPublicationDTO;
import com.gmail.fursovych20.entity.dto.PublicationSearchCriteriaDTO;
import com.gmail.fursovych20.service.exception.ServiceException;

import java.util.List;

/**
 * An interface that provides methods for working with publications at the dao level
 */
public interface PublicationService {

	/**
	 * A method which have access to dao operation for find publications by criteria balance operation
	 * and using method create
	 *
	 * @param criteria param, which using for searching
	 * @return {@code List<Publication>}
	 * @throws ServiceException throws exception
	 */
	List<Publication> findAllPublicationByCriteria(PublicationSearchCriteriaDTO criteria) throws ServiceException;

	/**
	 * A method which have access to dao operation for find publications by criteria balance operation and locale<br/>
	 * and using method create
	 *
	 * @param id param which using for searching publication
	 * @param locale locale which using for searching
	 * @return publication
	 * @throws ServiceException throws exception
	 */
	Publication findPublicationByIdAndLocale(int id, LocaleType locale) throws ServiceException;

	/**
	 * A method which have access to dao operation for find publications by criteria balance operation
	 * and using method create
	 *
	 * @param criteria criteria for searching in method
	 * @param name param name publication
	 * @return {@code List<Publication>}
	 * @throws ServiceException throws exception
	 */
    List<Publication> findPublicationsByNameAndCriteria(PublicationSearchCriteriaDTO criteria, String name) throws ServiceException;

	/**
	 * A method that creates a localized publication using the dao layer
	 *
	 * @param localizedPublicationDTO param, which using for creating publication
	 * @throws ServiceException throws exception
	 */
    void addPublication(LocalizedPublicationDTO localizedPublicationDTO) throws ServiceException;

	/**
	 * A method that delete a localized publication using the dao layer
	 *
	 * @param id param which using for delete
	 * @return true or false, dependency of id
	 * @throws ServiceException throws exception
	 */
	boolean deletePublication(int id) throws ServiceException;

	/**
	 * A method that read a localized publication using the dao layer
	 *
	 * @param id param which using for read publication
	 * @return {@code LocalizedPublicationDTO}
	 * @throws ServiceException throws exception
	 */
	LocalizedPublicationDTO readLocalized(int id) throws ServiceException;

	/**
	 * A method is read publication
	 *
	 * @param id publication id
	 * @param local local
	 * @return localized publication
	 * @throws ServiceException throws exception
	 */
	LocalizedPublicationDTO readLocalizedWithLocalized(int id, LocaleType local) throws ServiceException;

	/**
	 * A method that delete a localized publication using the dao layer
	 *
	 * @param localizedPublicationDTO  param, which using for updating publication
	 * @return true or false dependency of {@code localizedPublicationDTO}
	 * @throws ServiceException throws exception
	 */
	boolean update(LocalizedPublicationDTO localizedPublicationDTO) throws ServiceException;

}
