package com.example.huertohogardefinitiveedition.data.repository

import com.example.huertohogardefinitiveedition.data.model.Credential

class AuthRepository(
    private val validCredential: Credential = Credential.Admin
) {

    fun login(usernameOrEmail: String, password: String): Boolean {
        // Busca en la lista de usuarios del UserRepository
        val userMatch = UserRepository.all().any {
            (it.usuario.equals(usernameOrEmail, ignoreCase = true) ||
                    it.correo.equals(usernameOrEmail, ignoreCase = true)) &&
                    it.password == password
        }

        // Comprueba también si es el admin por defecto (como respaldo)
        val adminMatch = (
                usernameOrEmail.equals(validCredential.usuario, ignoreCase = true) &&
                        password == validCredential.password
                )

        return userMatch || adminMatch
    }
}
