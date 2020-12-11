
package com.book.store.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.expiry}")
	private String expireDays;

	private static final String AUTHORITY = "https://login.microsoftonline.com/common/";

	@Value("${jwt.client-id}")
	private String clientId;

	

	

	public String retriveUsernameFromJsonString(Map<String, Object> mapAzureObject) {
		String userPrincipalName = null;
		if (null != mapAzureObject && mapAzureObject.containsKey("userPrincipalName")) {
			userPrincipalName = (String) mapAzureObject.get("userPrincipalName");
		}
		return userPrincipalName;

	}

	public String getJwt(HttpServletRequest request) {
		final String authorizationHeader = request.getHeader("Authorization");
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			return authorizationHeader.substring(7);
		}
		return null;
	}

	/*public boolean validateToken(String token, Map<String, Object> mapAzureObject,UserDetails userDetails) {
		final String username = retriveUsernameFromJsonString(mapAzureObject);
		return (username.equalsIgnoreCase(userDetails.getUsername()));
	}
	

	public Date extractExpiration(String token) {
		try {
			return extractClaim(token, Claims::getExpiration);
		} catch (ExpiredJwtException ex) {
			throw new JwtException("JWT token/API key has been expired, please request again !");
		}
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		try {
			final Claims claims = extractAllClaims(token);
			return claimsResolver.apply(claims);
		} catch (JwtException ex) {
			throw new JwtException(ex.getMessage());
		}
	}

	private Claims extractAllClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		} catch (Exception ex) {
			log.debug("extractAllClaims - {}", ex.getCause() + " :- " + ex.getMessage());
			throw new JwtException(ex.getMessage());
		}
	}

	
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userDetails.getUsername());
	}

	private String createToken(Map<String, Object> claims, String subject) {
		try {
			LocalDateTime localDateTime = LocalDateTime.now();
			Date today = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
			Date expireDate = Date
					.from(localDateTime.plusDays(Long.valueOf(expireDays)).atZone(ZoneId.systemDefault()).toInstant());
			return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(today).setExpiration(expireDate)
					.signWith(SignatureAlgorithm.HS512, secretKey).compact();
		} catch (JwtException ex) {
			throw new JwtException("JWT token creation failed !");
		}

	}*/
}