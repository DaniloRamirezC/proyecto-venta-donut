package com.donutin.service_autenticacion.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService 
{
    @Value("${jwt.secret}")
    private String claveSecreta;
    public String generarToken(String username, List<String> roles)
    {
        //Calculo: 1000 ms * 60s * 60 m * 1h = 3.600 milisegundos
        long unaHoraEnMilisegundos = 1000L * 60 * 60 * 1;
        return Jwts.builder()
            .setSubject(username)
            .claim("roles", roles)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis()+unaHoraEnMilisegundos))
            .signWith(Keys.hmacShaKeyFor(claveSecreta.getBytes()),SignatureAlgorithm.HS256)
            .compact();
    
    }
}
