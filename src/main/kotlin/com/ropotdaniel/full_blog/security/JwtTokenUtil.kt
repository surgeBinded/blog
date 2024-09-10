package com.ropotdaniel.full_blog.security

import com.ropotdaniel.full_blog.dataaccessobject.JwtSecretRepository
import com.ropotdaniel.full_blog.domainobject.JwtSecretEntity
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import javax.crypto.SecretKey
import org.springframework.beans.factory.annotation.Autowired
import java.util.Base64

@Component
class JwtTokenUtil @Autowired constructor(
    private val jwtSecretRepository: JwtSecretRepository
) {

    private val key: SecretKey = getOrGenerateSecretKey()

    // Fetch the secret key from the database or generate a new one if not found
    private fun getOrGenerateSecretKey(): SecretKey {
        val storedSecret = jwtSecretRepository.findFirstByOrderByIdAsc()

        return if (storedSecret != null) {
            // If a secret is found, decode it and use it
            Keys.hmacShaKeyFor(Base64.getDecoder().decode(storedSecret.secretKey))
        } else {
            // Generate a new secret key, save it in the database, and return it
            val newSecretKey = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256)
            val encodedSecretKey = Base64.getEncoder().encodeToString(newSecretKey.encoded)

            // Save the new secret key to the database
            val jwtSecret = JwtSecretEntity(secretKey = encodedSecretKey)
            jwtSecretRepository.save(jwtSecret)

            newSecretKey
        }
    }

    fun extractUsername(token: String): String = extractClaim(token, Claims::getSubject)

    fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver.invoke(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
    }

    fun generateToken(username: String, claims: Map<String, Any>): String {
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(java.util.Date())
            .setExpiration(java.util.Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
            .signWith(key)
            .compact()
    }

    fun validateToken(token: String, username: String): Boolean {
        val extractedUsername = extractUsername(token)
        return extractedUsername == username && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(java.util.Date())
    }

    private fun extractExpiration(token: String): java.util.Date {
        return extractClaim(token, Claims::getExpiration)
    }
}
