package com.example.huertohogardefinitiveedition.data.model

// Representa a un usuario del sistema
data class Credential(
    val idUsuario: Int = 0,
    val nombre: String,
    val correo: String,
    val usuario: String,
    val telefono: String,
    val direccion: String,
    val password: String
) {
    companion object {
        // Usuario administrador por defecto
        val Admin = Credential(
            idUsuario = 1,
            nombre = "Administrador del Sistema",
            correo = "admin@duoc.cl",
            usuario = "admin",
            telefono = "000000000",
            direccion = "Sede Central DuocUC",
            password = "123"
        )
    }
}

