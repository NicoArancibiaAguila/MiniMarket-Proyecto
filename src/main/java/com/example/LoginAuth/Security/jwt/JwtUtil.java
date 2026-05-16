package com.example.loginauth.security.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JwtUtil {
    
    //llave super maestra, identica a la de inventario, si no, gg
    private final String SECRET_KEY = "clave_secreta_super_segura";

    public String generateToken(String username, String rol){
        Map<String, Object> claims = new HashMap<>();
        //se guarda el rol en el token para filtrar
        claims.put("rol", rol);

        return Jwts.builder()       
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                //token expira en 10 hrs
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10 ))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
        
    }

}
