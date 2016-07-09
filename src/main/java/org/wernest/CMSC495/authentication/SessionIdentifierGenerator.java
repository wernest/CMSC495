package org.wernest.CMSC495.authentication;

import java.security.SecureRandom;
import java.math.BigInteger;

/**
 * Utility class to generate session ID's randomly, and securely.
 */
public final class SessionIdentifierGenerator {
    private SecureRandom random = new SecureRandom();

    public String nextSessionId() {
        return new BigInteger(130, random).toString(32);
    }
}