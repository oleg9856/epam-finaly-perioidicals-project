package com.gmail.fursovych20.service.impl;

import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.db.dao.TypeDAO;
import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.entity.Type;
import com.gmail.fursovych20.entity.dto.LocalizedTypeDTO;
import com.gmail.fursovych20.service.TypeService;
import com.gmail.fursovych20.service.exception.ServiceException;
import com.gmail.fursovych20.service.exception.ValidationException;
import com.gmail.fursovych20.service.util.Validator;

import java.util.List;

/**
 * TypeServiceImpl implementation for manipulating dao layer
 *
 * @author O. Fursovych
 */
public class TypeServiceImpl implements TypeService {
	
	private final TypeDAO typeDao;

	public TypeServiceImpl(TypeDAO typeDao) {
		this.typeDao = typeDao;
	}

	public List<Type> findAllTypeServiceByLocaleType(LocaleType locale) throws ServiceException {
		try {
			return typeDao.findAllType(locale);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Type findTypeServiceById(int id, LocaleType locale) throws ServiceException {
		try {
			return typeDao.findTypeById(id, locale);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<LocalizedTypeDTO> findAllLocalized() throws ServiceException {
		try {
			return typeDao.findAllLocalizedTypes();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean create(LocalizedTypeDTO type) throws ServiceException {
		if (Validator.localizedTypeIsValid(type)) {
			throw new ValidationException("Localized type data is not valid");
		}
		try {
			return typeDao.create(type);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean update(LocalizedTypeDTO type) throws ServiceException {
		if (Validator.localizedTypeIsValid(type)) {
			throw new ValidationException("Localized type data is not valid");
		}
		try {
			return typeDao.update(type);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

}
