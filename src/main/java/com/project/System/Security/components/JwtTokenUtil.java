/*
package com.project.System.Security.components;


import com.project.System.Security.Exception.InvalidParamException;
import com.project.System.Security.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
    private static final String SECRET_KEY = "qlbv"; // Use a constant secret key
    private static final long EXPIRATION_TIME = 86400000L; // 1 day

    // Method to generate a JWT token for a user
    public static String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .claim("role", user.getRole().getRoleName()) // Assuming the role's name is what you want in the token
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY) // Use SECRET_KEY constant
                .compact();
    }

    // Method to get the signing key from the secret key
    private static Key getSignInKey() {
        byte[] bytes = Decoders.BASE64.decode(SECRET_KEY); // Decode the SECRET_KEY
        return Keys.hmacShaKeyFor(bytes);
    }

    // Method to extract all claims from the token
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSignInKey()) // Use the signing key here
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Method to extract a specific claim from the token
    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Check if the token is expired
    public Boolean isTokenExpired(String token) {
        Date expirationDate = this.extractClaims(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }

    // Extract username from the token
    public String extractUserName(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    // Validate the token by comparing the username and checking expiration
    public boolean validateToken(String token, User userDetails) {
        String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}

//private static final String SECRET_KEY = "qlbv";
//private static final long EXPIRATION_TIME = 86400000L; // 1 day
//    @Value("2592000")
//    private static int expiration;
//
//    @Value("${jwt.secretKey}")
//    private static String secretKey;
//
//    public static String generateToken(com.project.System.Security.model.User user) throws Exception {
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("username", user.getUsername());
//        try {
//            String token = Jwts.builder()
//                    .setClaims(claims)
//                    .setSubject(user.getUsername())
//                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
//                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
//                    .compact();
//            return token;
//        } catch (Exception e) {
//            throw new InvalidParamException(("Cannot create jwt token " + e.getMessage()));
//        }
//    }
//public static String generateToken(User user) {
//    return Jwts.builder()
//            .setSubject(user.getUsername())
//            .setIssuedAt(new Date())
//            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//            .claim("role", user.getRole().getRoleName())
//            .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
//            .compact();
//}
//private static Key getSignInKey() {
//    byte[] bytes = Decoders.BASE64.decode(secretKey);
//    return Keys.hmacShaKeyFor(bytes);
//}
//private String generateSecretKey(){
//    SecureRandom random = new SecureRandom();
//    byte[] keyBytes = new byte[32];
//    random.nextBytes(keyBytes);
//    String secretKey = Encoders.BASE64.encode(keyBytes);
//    return secretKey;
//}
//private Claims extractAllClaims(String token){
//    return Jwts.parser()
//            .setSigningKey(getSignInKey())
//            .build()
//            .parseClaimsJws(token)
//            .getBody();
//}
//public  <T> T extractClaims(String token, Function<Claims, T> claimsResolver){
//    final Claims claims =  this.extractAllClaims(token);
//    return claimsResolver.apply(claims);
//}
////check expiration
//public Boolean isTokenExpired(String token){
//    Date expirationDate = this.extractClaims(token, Claims::getExpiration);
//    return expirationDate.before(new Date()); //muon tra ve Jwt token
//}
//public String extractUserName(String token)
//{
//    return extractClaims(token, Claims::getSubject);
//}
//public boolean validateToken(String token, User userDetails){
//    String userName = extractUserName(token);
//    return (userName.equals(userDetails.getUsername())
//            && !isTokenExpired(token));
//}

*/
