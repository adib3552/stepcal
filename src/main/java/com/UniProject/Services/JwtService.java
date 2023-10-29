package com.UniProject.Services;

import com.UniProject.Entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    public String generateToken(UserDetails user){
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()*1000*60*24))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public String generateRefreshedToken(Map<String,Object>extraClaims,UserDetails user){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()*1000*60*24))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }
    public <T> T extractClaim(String token, Function<Claims,T>claimsResolver){
        final Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public Key getSignKey(){
        byte[] key= Decoders.BASE64.decode("sdgfhsgfsgfhsgfhgshfgsjhfgshjsdgfhgsjhfggyret");
        return Keys.hmacShaKeyFor(key);
    }
    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }
    public boolean isTokenValid(String token, UserDetails userDetails){
        String username=extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isExpired(token));
    }
    public boolean isExpired(String token){
        return extractClaim(token,Claims::getExpiration).before(new Date());
    }
}
