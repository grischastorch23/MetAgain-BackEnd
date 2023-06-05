package com.metagain.backend.helper;

import java.util.Base64;

public class AuthorizationStringSplitter {

    public static String[] splitAuthorization(String authorizationString) {
        String credentialsEncoded = authorizationString.substring(6);
        String credentials = new String(Base64.getDecoder().decode(credentialsEncoded));
        return credentials.split(":");
    }

}
