package com.gmail.fursovych20.service.util;

import java.util.function.Function;


/**
 * Class for hashing password on 11 months
 */
public class Hashing {


    // This should be updated every year or two.
    private static final UpdatableBCrypt bcrypt = new UpdatableBCrypt(11);

    private Hashing() {
    }

    /**
     * This method hashing password in hash
     *
     * @param password param which hashing
     * @return hash password
     */
    public static String hash(String password) {
        return bcrypt.hash(password);
    }


    /**
     * A static method for checking and updating a user's password
     *
     * @param password parameter for hashing and verify password
     * @param hash parameter for check password
     * @param updateFunc Function for success update
     * @return true or false dependency of parameters
     */
    public static boolean verifyAndUpdateHash(String password, String hash, Function<String, Boolean> updateFunc) {
        return bcrypt.verifyAndUpdateHash(password, hash, updateFunc);
    }

    /**
     * A static method for checking a user's password
     *
     * @param password parameter which hashing
     * @param hashPassword hash for check
     * @return true or false dependency of parameters
     */
    public static boolean verifyHash(String password, String hashPassword){
        return bcrypt.verifyHash(password, hashPassword);
    }
}
