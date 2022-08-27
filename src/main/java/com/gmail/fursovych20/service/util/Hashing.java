package com.gmail.fursovych20.service.util;

import java.util.function.Function;

public class Hashing {
    // This should be updated every year or two.
    private static final UpdatableBCrypt bcrypt = new UpdatableBCrypt(11);

    public static String hash(String password) {
        return bcrypt.hash(password);
    }

    public static boolean verifyAndUpdateHash(String password, String hash, Function<String, Boolean> updateFunc) {
        return bcrypt.verifyAndUpdateHash(password, hash, updateFunc);
    }

    public static boolean verifyHash(String password, String hashPassword){
        return bcrypt.verifyHash(password, hashPassword);
    }
}
