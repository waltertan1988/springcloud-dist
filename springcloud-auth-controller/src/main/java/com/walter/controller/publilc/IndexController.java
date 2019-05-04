package com.walter.controller.publilc;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/public")
public class IndexController {
	
	private final String JWT_SECRET = "JWT-SECRET";
	
	@GetMapping("/index")
	public String index(@RequestParam String helloTo) {
		return "Hello " + helloTo;
	}
	
	@GetMapping("/genJwt/{username}")
	public String generateToken(@PathVariable("username") String username) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(Claims.SUBJECT, username);
	    return Jwts.builder()
	            .setClaims(claims)
	            .setExpiration(DateUtils.addMinutes(new Date(), 10))
	            .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
	            .compact();
	}
	
	@GetMapping("/getJwtClaims")
	public Claims getClaimsFromToken(@RequestParam("token") String token) {
	    Claims claims;
	    try {
	        claims = Jwts.parser()
	                .setSigningKey(JWT_SECRET)
	                .parseClaimsJws(token)
	                .getBody();
	    } catch (Exception e) {
	        claims = null;
	    }
	    return claims;
	}
}
