package com.gmail.fursovych20.service.impl;

import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.db.dao.PublicationDAO;
import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.entity.Publication;
import com.gmail.fursovych20.entity.dto.LocalizedPublicationDTO;
import com.gmail.fursovych20.entity.dto.PublicationSearchCriteriaDTO;
import com.gmail.fursovych20.service.PublicationService;
import com.gmail.fursovych20.service.exception.ServiceException;
import com.gmail.fursovych20.service.exception.ValidationException;
import com.gmail.fursovych20.service.util.Validator;

import java.util.List;

public class PublicationServiceImpl implements PublicationService {

    private final PublicationDAO publicationDao;

    public PublicationServiceImpl(PublicationDAO publicationDao) {
        this.publicationDao = publicationDao;
    }

    @Override
    public List<Publication> findAllPublicationByCriteria(PublicationSearchCriteriaDTO criteria) throws ServiceException {
        List<Publication> publications;
        try {
            publications = publicationDao.findAllPublicationByCriteria(criteria);
            int totalPageCount = (int) Math.ceil((double) publicationDao.getTotalCount(criteria) / criteria.getItemsPerPage());
            criteria.setPageCount(totalPageCount);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return publications;
    }

    @Override
    public Publication findPublicationByIdAndLocale(int id, LocaleType locale) throws ServiceException {
        try {
            return publicationDao.findPublicationByIdAndLocale(id, locale);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Publication> findPublicationsByNameAndCriteria(PublicationSearchCriteriaDTO criteria, String name) throws ServiceException {
        if(!Validator.validateStrings(name)){
            throw new ValidationException("data is not valid");
        }
        List<Publication> publications;
        try {
            String searchName = "%"+name+"%";
            publications = publicationDao.findPublicationsByNameAndCriteria(criteria, searchName);
            int totalPageCount = (int) Math.ceil((double) publicationDao.getTotalCount(criteria) / criteria.getItemsPerPage());
            criteria.setPageCount(totalPageCount);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return publications;
    }

    @Override
    public void addPublication(LocalizedPublicationDTO localizedPublicationDTO) throws ServiceException {
        if (Validator.localizedPublicationIsValid(localizedPublicationDTO)) {
            throw new ValidationException("Publication data is not valid");
        }
        try {
            publicationDao.create(localizedPublicationDTO);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public LocalizedPublicationDTO readLocalized(int id) throws ServiceException {
        try {
            return publicationDao.readLocalized(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deletePublication(int id) throws ServiceException {
        try {
            if (id != 0) {
                return publicationDao.delete(id);
            }else {
                throw new ValidationException("publication data is not valid");
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean update(LocalizedPublicationDTO localizedPublicationDTO) throws ServiceException {
        if (Validator.localizedPublicationIsValid(localizedPublicationDTO)) {
            throw new ValidationException("publication data is not valid");
        }
        try {
            return publicationDao.update(localizedPublicationDTO);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

}
