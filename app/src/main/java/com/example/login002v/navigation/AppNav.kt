package com.example.login002v.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import androidx.navigation.navArgument

import com.example.login002v.ui.login.LoginScreen
import com.example.login002v.ui.registro.RegistrarseScreen
import com.example.login002v.view.DrawerMenu
import com.example.login002v.ui.gestion.GestionUsuarioScreen
import com.example.login002v.ui.gestion.RecuperarContrasenaScreen
import com.example.login002v.ui.gestion.GestionPerfilScreen

@Composable
fun AppNav() {
    val navController = rememberNavController()

    // (Opcional) paleta rapidita para todo el nav si quieres
    val colors = lightColorScheme()
    MaterialTheme(colorScheme = colors) {

        NavHost(
            navController = navController,
            startDestination = "login"
        ) {
            // Login
            composable("login") {
                LoginScreen(navController = navController)
            }

            // Registro
            composable("registrarse") {
                RegistrarseScreen(navController = navController)
            }

            // Drawer (
            composable(
                route = "DrawerMenu/{username}",
                arguments = listOf(navArgument("username") { type = NavType.StringType })
            ) { backStackEntry ->
                val usernameArg = backStackEntry.arguments?.getString("username") ?: ""
                DrawerMenu(username = usernameArg, navController = navController)
            }

            // Gestión de datos personales
            composable("gestion_usuario") {

                GestionPerfilScreen(navController = navController)
            }

            //  Historial de pedidos
            composable("historial_pedidos") {
                //remplazar quien tenga esa parte

                SimpleStub("Pantalla: Historial de pedidos")
            }

            // Gestión de usuarios (SOLO admin)
            composable("Gestion") {
                GestionUsuarioScreen(navController = navController)
            }

            // Recuperar contraseña
            composable("recuperar_contrasena") {
                RecuperarContrasenaScreen(navController = navController)
            }
        }
    }
}

@Composable
private fun SimpleStub(texto: String) {
    // Stub para que compile si aún no tienes esas pantallas construidas
    Text(text = texto, modifier = Modifier.padding(24.dp))
}
