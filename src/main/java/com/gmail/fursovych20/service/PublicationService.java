package com.gmail.fursovych20.service;

import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.entity.Publication;
import com.gmail.fursovych20.entity.dto.LocalizedPublicationDTO;
import com.gmail.fursovych20.entity.dto.PublicationSearchCriteriaDTO;
import com.gmail.fursovych20.service.exception.ServiceException;

import java.util.List;

public interface PublicationService {
	
	List<Publication> findAllPublicationByCriteria(PublicationSearchCriteriaDTO criteria) throws ServiceException;
	
	Publication findPublicationByIdAndLocale(int id, LocaleType locale) throws ServiceException;

    List<Publication> findPublicationsByNameAndCriteria(PublicationSearchCriteriaDTO criteria, String name) throws ServiceException;

    void addPublication(LocalizedPublicationDTO localizedPublicationDTO) throws ServiceException;

	boolean deletePublication(int id) throws ServiceException;
	
	LocalizedPublicationDTO readLocalized(int id) throws ServiceException;

	LocalizedPublicationDTO readLocalizedWithLocalized(int id, LocaleType local) throws ServiceException;
	
	boolean update(LocalizedPublicationDTO localizedPublicationDTO) throws ServiceException;

}
