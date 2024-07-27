package com.codewithprojects.service.impl;

import com.codewithprojects.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JWTServiceImpl implements JWTService {

    //add jwt secret
    //the secret key which is used for signing and verifying jwts
    //private static final String SECRET = "6hlaWJaPZp0ep59E/pfLJb31NTSKjiEL4GTBkXtl1dE";

    //generates a jwt token for the given user details
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())//sets the subject(usually the username)
                .setIssuedAt(new Date(System.currentTimeMillis()))//sets the current time as the issue time
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))//sets the expiration time(30 minutes)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)//signs the token with the secret key and HS256 algorithm
                .compact();//builds the token and return it as a compact string
    }
    //This method creates and generates a JWT token with the given userDetails.
    //It sets the claims, subject, issue time, and expiration time, and then signs the token using the secret key and the HS256 algorithm.

    //now create a getSignKey method which will return a key
    //returns the signing key used to sign the jwt
    private Key getSignKey() {

        try {
            //create a byte array and name it as keyBytes
            //Decode the secret key from Base64 format
            //add jwt secret
            //the secret key which is used for signing and verifying jwts
            byte[] keyBytes = Decoders.BASE64.decode("6hlaWJaPZp0ep59E/pfLJb31NTSKjiEL4GTBkXtl1dE");
            //after getting the keyBytes, lets format this key and return from here
            //generate and return the signing key
            return Keys.hmacShaKeyFor(keyBytes);
        }
        catch(Exception e)
        {
            throw new RuntimeException("Failed to decode the JWT secret key, e");
        }
    }
    //This method returns the signing key used to sign the JWT.
    //It decodes the secret key from Base64 format and then generates the signing key.

    //a method to extract the username from our jwt token
    //return the username
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    //This method extracts the username (subject) from the JWT token by calling extractClaim with the token and Claims::getSubject as the claims resolver.

    //extracts a specific claim from the jwt token using the given claims resolver function
    //return the extracted claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);//extract all claims from the token
        return claimsResolver.apply(claims);//apply the claims resolver to get the specific claim
    }
    //This method extracts a specific claim from the JWT token using the given claims resolver function.
    // It first extracts all claims from the token and then applies the claims resolver to get the specific claim.


    //extracts all claims from jwt token
    //return the claims
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey()) //sets the signing key for parsing the token
                .build()
                .parseClaimsJws(token)
                .getBody();//parses the token and returns the claims(body)
    }
    //This method extracts all claims from the JWT token.
    //It sets the signing key, parses the token, and returns the claims (body).


    //checks if the jwt token is valid by comparing the username and cehcking expiration
    //returns true if the token is valid, false otherwise
    public boolean isTokenValid(String token, UserDetails userDetails)
    {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    //checks if the jwt token is expired
    //a method to check the expiration of our jwt token
    //return true if the token is expired, false otherwise
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    //it extracts the expiration date from the token and compares it with the current date

    //extracts the expiration date from the jwt token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    //This method extracts the expiration date from the JWT token by calling extractClaim with the token and Claims::getExpiration as the claims resolver.


}
/*SECRET: The secret key for signing JWTs.
generateToken: Generates a JWT token for a given username.
createToken: Creates a JWT token with the given claims and username.
getSignKey: Returns the signing key used to sign the JWT.
extractUsername: Extracts the username from the JWT token.
extractClaim: Extracts a specific claim from the JWT token.
extractAllClaims: Extracts all claims from the JWT token.
isTokenExpired: Checks if the JWT token is expired.
extractExpiration: Extracts the expiration date from the JWT token.*/

