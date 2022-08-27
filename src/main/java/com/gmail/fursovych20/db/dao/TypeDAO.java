package com.gmail.fursovych20.db.dao;

import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.entity.Type;
import com.gmail.fursovych20.entity.dto.LocalizedTypeDTO;

import java.util.List;

public interface TypeDAO {
	
	List<Type> findAllType(LocaleType locale) throws DAOException;
	
	Type findTypeById(Integer id, LocaleType locale) throws DAOException;
	
	List<LocalizedTypeDTO> findAllLocalizedTypes() throws DAOException;
	
	boolean update(LocalizedTypeDTO localizedTypeDTO) throws DAOException;
	
	boolean create(LocalizedTypeDTO localizedTypeDTO)  throws DAOException;

}
