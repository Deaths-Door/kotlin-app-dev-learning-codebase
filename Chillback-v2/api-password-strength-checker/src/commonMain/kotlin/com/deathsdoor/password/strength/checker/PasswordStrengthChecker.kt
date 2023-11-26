package com.deathsdoor.password.strength.checker

import com.deathsdoor.request.utilities.RequestHandler

class PasswordStrengthChecker(override val rapidApiKey: String) : RequestHandler() {
    override val rapidApiHost: String = "check-password-strength-with-zxcvbn1.p.rapidapi.com"
    override val baseURL: String = "https://rapidapi.com/daniyal.javani/api/check-password-strength-with-zxcvbn1/"

    suspend fun check(password : String) : Strength = Strength.Strong
}