package net.twitter.infra;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public class JwtParser {

    private io.jsonwebtoken.JwtParser parser;

    public JwtParser(String singingKey) {
        parser = Jwts.parserBuilder()
                .setSigningKey(singingKey.getBytes())
                .build();
    }

    public Jws<Claims> parseClaims(String token) {
        return parser.parseClaimsJws(token);
    }
}
