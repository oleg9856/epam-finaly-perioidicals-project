package com.gmail.fursovych20.db.dao;

import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.entity.Theme;
import com.gmail.fursovych20.entity.dto.LocalizedThemeDTO;

import java.util.List;

public interface ThemeDAO {
	
	List<Theme> findAll(LocaleType locale) throws DAOException;
	
	Theme findThemeById(Integer id, LocaleType locale) throws DAOException;
	
	List<LocalizedThemeDTO> findAllLocalized() throws DAOException;
	
	boolean update(LocalizedThemeDTO localizedThemeDTO) throws DAOException;
	
	boolean create(LocalizedThemeDTO localizedThemeDTO)  throws DAOException;

}
