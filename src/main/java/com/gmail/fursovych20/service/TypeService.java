package com.gmail.fursovych20.service;

import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.entity.Type;
import com.gmail.fursovych20.entity.dto.LocalizedTypeDTO;
import com.gmail.fursovych20.service.exception.ServiceException;
import com.gmail.fursovych20.service.exception.ValidationException;

import java.util.List;

public interface TypeService {
	
	List<Type> findAllTypeServiceByLocaleType(LocaleType locale) throws ServiceException;
	
	Type findTypeServiceById(int id, LocaleType locale) throws ServiceException;
	
	List<LocalizedTypeDTO> findAllLocalized() throws ServiceException;
	
	boolean create(LocalizedTypeDTO type) throws ServiceException, ValidationException;
	
	boolean update(LocalizedTypeDTO type) throws ServiceException;

}
