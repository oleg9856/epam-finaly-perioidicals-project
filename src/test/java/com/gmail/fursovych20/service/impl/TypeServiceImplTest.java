package com.gmail.fursovych20.service.impl;

import com.gmail.fursovych20.db.dao.TypeDAO;
import com.gmail.fursovych20.db.dao.exception.DAOException;
import com.gmail.fursovych20.entity.LocaleType;
import com.gmail.fursovych20.entity.Type;
import com.gmail.fursovych20.entity.dto.LocalizedTypeDTO;
import com.gmail.fursovych20.service.exception.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TypeServiceImplTest {

    @Mock
    private TypeDAO typeDAO;

    @InjectMocks
    private TypeServiceImpl typeService;
    private static final String NAME = "typesName";
    private static final String DEFAULT_NAME = "defaultName";
    private static final short ID = 1;
    private static final LocaleType LOCAL = LocaleType.uk_UA;

    private static final Type TYPE = getType();

    private static final LocalizedTypeDTO LOCALIZED_TYPE = getLocalizedTypeDTO();

    private static final List<Type> TYPES = new ArrayList<>();
    private static final List<LocalizedTypeDTO> LOCALIZED_TYPES  = new ArrayList<>(List.of(LOCALIZED_TYPE));
    @Test
    public void testSuccessFindAllTypeServiceByLocaleType() throws DAOException, ServiceException {
        when(typeDAO.findAllType(LOCAL)).thenReturn(TYPES);
        assertEquals(TYPES, typeService.findAllTypeServiceByLocaleType(LOCAL));
    }

    @Test
    public void testSuccessFindTypeServiceById() throws DAOException, ServiceException {
        when(typeDAO.findTypeById((int) ID, LOCAL)).thenReturn(TYPE);
        assertEquals(TYPE, typeService.findTypeServiceById(ID, LOCAL));
    }

    @Test
    public void testSuccessFindAllLocalized() throws DAOException, ServiceException {
        when(typeDAO.findAllLocalizedTypes()).thenReturn(LOCALIZED_TYPES);
        assertEquals(LOCALIZED_TYPES, typeService.findAllLocalized());
    }

    @Test
    public void testSuccessCreate() throws DAOException, ServiceException {
        when(typeDAO.create(LOCALIZED_TYPE)).thenReturn(true);
        assertTrue(typeService.create(LOCALIZED_TYPE));
    }

    @Test
    public void testSuccessUpdate() throws DAOException, ServiceException {
        when(typeDAO.update(LOCALIZED_TYPE)).thenReturn(true);
        assertTrue(typeService.update(LOCALIZED_TYPE));
    }


    private static Type getType() {
        return new Type.Builder()
                .setId(ID)
                .setName(NAME)
                .build();
    }

    private static LocalizedTypeDTO getLocalizedTypeDTO() {
        return new LocalizedTypeDTO.Builder()
                .setId(ID)
                .setDefaultName(DEFAULT_NAME)
                .setLocalizedNames(new EnumMap<>(Map.of(LOCAL, NAME)))
                .build();
    }
}