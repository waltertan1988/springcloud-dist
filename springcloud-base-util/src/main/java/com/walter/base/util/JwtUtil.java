package com.walter.base.util;

import java.util.Date;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {
	
	private JwtUtil() {}

	/**
	 * 生成JWT
	 * @param claims
	 * @param exp 失效时间
	 * @param signatureAlgorithm 加密算法
	 * @param secret 密钥
	 * @return
	 */
	public static String generate(Map<String, Object> claims, Date exp, SignatureAlgorithm signatureAlgorithm, String secret) {
	    return Jwts.builder()
	            .setClaims(claims)
	            .setExpiration(exp)
	            .signWith(signatureAlgorithm, secret)
	            .compact();
	}
	
	
	/**
	 * 获取Jwt的Claim，Jwt过期会返回null
	 * @param jwt
	 * @param secret 密钥
	 * @return
	 */
	public static Claims getClaims(String jwt, String secret) {
	    Claims claims;
	    try {
	        claims = Jwts.parser()
	                .setSigningKey(secret)
	                .parseClaimsJws(jwt)
	                .getBody();
	    } catch (Exception e) {
	        claims = null;
	    }
	    return claims;
	}
}
