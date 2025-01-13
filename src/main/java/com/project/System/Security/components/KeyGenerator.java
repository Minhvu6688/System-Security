package com.project.System.Security.components;

import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

public class KeyGenerator {
    public static void main(String[] args) {
        String secretKey = Encoders.BASE64.encode(Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS512).getEncoded());
        System.out.println("Generated Secret Key: " + secretKey);
    }
}
