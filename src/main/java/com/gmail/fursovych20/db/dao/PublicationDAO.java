package com.gmail.fursovych20.db.dao;

import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.entity.Publication;
import com.gmail.fursovych20.entity.dto.LocalizedPublicationDTO;
import com.gmail.fursovych20.entity.dto.PublicationSearchCriteriaDTO;

import java.util.List;

public interface PublicationDAO {
	
	Publication findPublicationByIdAndLocale(int id, LocaleType locale) throws DAOException;

	List<Publication> findAllPublicationByCriteria(PublicationSearchCriteriaDTO criteria) throws DAOException;

	List<Publication> findPublicationsByNameAndCriteria(PublicationSearchCriteriaDTO criteria, String name) throws DAOException;

	boolean create(LocalizedPublicationDTO localizedPublicationDTO) throws DAOException;
	
	boolean update(LocalizedPublicationDTO localizedPublicationDTO) throws DAOException;

	boolean delete(int id) throws DAOException;

	LocalizedPublicationDTO readLocalized(int id) throws DAOException;
	
	int getTotalCount(PublicationSearchCriteriaDTO criteria) throws DAOException;

}
