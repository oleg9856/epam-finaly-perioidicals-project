package com.gmail.fursovych20.service.util;

import com.gmail.fursovych20.entity.BalanceOperation;
import com.gmail.fursovych20.entity.Review;
import com.gmail.fursovych20.entity.User;
import com.gmail.fursovych20.entity.dto.LocalizedPublicationDTO;
import com.gmail.fursovych20.entity.dto.LocalizedThemeDTO;
import com.gmail.fursovych20.entity.dto.LocalizedTypeDTO;
import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Class Validator which check different types of data
 */
public class Validator {

    /** Constant Logger */
    private static final Logger LOG = Logger.getLogger(Validator.class);

    /** Constant pattern for login */
    private static final Pattern pLogin = Pattern.compile("([A-Za-zА-Яа-я\\d_-]){3,16}");

    /** Constant pattern for password */
    private static final Pattern pPassword = Pattern.compile("([A-Za-zА-Яа-я\\d_-]){8,20}");

    /** Constant pattern for email */
    private static final Pattern pEmail = Pattern.compile("([A-za-z\\d_.-]+)@([A-za-z\\d_.-]+)\\.([A-za-z.]{2,6})");

    /** Constant pattern for name */
    private static final Pattern pName = Pattern.compile("([A-Za-zА-Яа-я]){2,25}");

    /** Constant pattern for surname */
    private static final Pattern pSurname = Pattern.compile("([A-Za-zА-Яа-я]){2,25}");

    /** Constant pattern for title of edition */
    private static final Pattern pNamePublication = Pattern.compile("(.){3,45}");

    /** Constant pattern for text of edition */
    private static final Pattern pTextPublication = Pattern.compile("(.){10,100}");

    /** Constant min price */
    private static final int PRICE_MIN = 1;

    /** Constant max price */
    private static final int PRICE_MAX = 10000;

    private Validator() {
    }

    /**
     * Validate login.
     *
     * @param enterLogin the enter login
     * @return true, if successful
     */
    public static boolean validateLogin(String enterLogin) {
        Matcher mLogin = pLogin.matcher(enterLogin);
        LOG.debug("Validate Login: " + mLogin.matches());
        return mLogin.matches();
    }

    /**
     * Validate password.
     *
     * @param enterPass the enter pass
     * @return true, if successful
     */
    public static boolean validatePass(String enterPass) {
        Matcher mPassword = pPassword.matcher(enterPass);
        LOG.debug("Validate Password: " + mPassword.matches());
        return mPassword.matches();
    }

    /**
     * Validate email.
     *
     * @param enterEmail the enter email
     * @return true, if successful
     */
    public static boolean validateEmail(String enterEmail) {
        Matcher mEmail = pEmail.matcher(enterEmail);
        LOG.debug("Validate Email: " + mEmail.matches());
        return mEmail.matches();
    }

    /**
     * Validate name.
     *
     * @param enterName the enter name
     * @return true, if successful
     */
    public static boolean validateName(String enterName) {
        Matcher mName = pName.matcher(enterName);
        LOG.debug("Validate Name: " + mName.matches());
        return mName.matches();
    }

    /**
     * Validate surname.
     *
     * @param enterSurname the enter surname
     * @return true, if successful
     */
    public static boolean validateSurname(String enterSurname) {
        Matcher mSurname = pSurname.matcher(enterSurname);
        LOG.debug("Validate Surname: " + mSurname.matches());
        return mSurname.matches();
    }

    /**
     * Validate name tour.
     *
     * @param enterText the enter name tour
     * @return true, if successful
     */
    public static boolean validateTextPublication(String enterText) {
        Matcher mText = pTextPublication.matcher(enterText);
        LOG.debug("Validate TextEdition: " + mText.matches());
        return mText.matches();
    }

    /**
     * Validate country or city name.
     *
     * @param enterTitle the enter name
     * @return true, if successful
     */
    public static boolean validateNamePublication(String enterTitle) {
        Matcher mTitle = pNamePublication.matcher(enterTitle);
        LOG.debug("Validate TitleEdition: " + mTitle.matches());
        return mTitle.matches();
    }

    /**
     * Validate price.
     *
     * @param enterPrice the enter price
     * @return true, if successful
     */
    public static boolean validatePrice(double enterPrice) {
        if (enterPrice == 0) {
            return false;
        } else {
            LOG.debug("Validate Price: " + (enterPrice >= PRICE_MIN && enterPrice <= PRICE_MAX));
            return enterPrice >= PRICE_MIN && enterPrice <= PRICE_MAX;
        }
    }

    /**
     * Validate string.
     *
     * @param enterStrings the enter strings
     * @return true, if successful
     */
    public static boolean validateStrings(String... enterStrings) {
        for (String val: enterStrings) {
            if (val.isEmpty()){
                return false;
            }
        }
        return true;
    }

    public static boolean userIsValid(User user) {
        return validateLogin(user.getLogin()) && validateName(user.getName()) && validateEmail(user.getEmail()) &&
                validateSurname(user.getSurName()) && validatePass(user.getPassword());
    }

    public static boolean balanceOperationIsValid(BalanceOperation balanceOperation) {
        return balanceOperation.getLocalDate() != null && validatePrice(balanceOperation.getSum().doubleValue());
    }

    public static boolean reviewIsValid(Review review) {
        return validateStrings(review.getText()) && review.getDateOfPublication() != null;
    }

    public static boolean localizedPublicationIsValid(LocalizedPublicationDTO publication) {
        for (String description : publication.getDescriptions().values()) {
            if (description == null || description.isEmpty() || !validateTextPublication(description)) {
                return true;
            }
        }
        for (String name : publication.getNames().values()) {
            if (name == null || name.isEmpty() || !validateNamePublication(name)) {
                return true;
            }
        }
        return publication.getPicturePath() == null || publication.getPicturePath().isEmpty();
    }

    public static boolean localizedThemeIsValid(LocalizedThemeDTO theme) {
        if (theme.getDefaultName() == null || theme.getDefaultName().isEmpty()) {
            return true;
        }
        for (String name : theme.getLocalizedNames().values()) {
            if (name == null || name.isEmpty() || !validateNamePublication(name)) {
                return true;
            }
        }
        return false;
    }

    public static boolean localizedTypeIsValid(LocalizedTypeDTO type) {
        if (type.getDefaultName() == null || type.getDefaultName().isEmpty()) {
            return true;
        }
        for (String name : type.getLocalizedNames().values()) {
            if (name == null || name.isEmpty() || !validateNamePublication(name)) {
                return true;
            }
        }
        return false;
    }
}
