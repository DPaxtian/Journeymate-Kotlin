package com.example.journeymate.models
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class Encription {
    companion object {
        fun getHashedPassword(password: String): String {
            var passwordHashed = ""
            val key = "\$_jourNeyMateIsTheB35TPlatForMForJourney5&EnJoy-$$"
            val hmac = Mac.getInstance("HmacSHA256")
            val secretKey = SecretKeySpec(key.toByteArray(StandardCharsets.UTF_8), "HmacSHA256")
            hmac.init(secretKey)
            val hashBytes = hmac.doFinal(password.toByteArray(StandardCharsets.UTF_8))
            passwordHashed = hashBytes.joinToString("") { "%02x".format(it) }
            return passwordHashed
        }
    }
}
