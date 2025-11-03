package com.example.huertohogardefinitiveedition.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.huertohogardefinitiveedition.ui.gestion.GestionPerfilScreen
import com.example.huertohogardefinitiveedition.ui.gestion.GestionUsuarioScreen
import com.example.huertohogardefinitiveedition.ui.gestion.RecuperarContrasenaScreen
import com.example.huertohogardefinitiveedition.ui.login.LoginScreen
import com.example.huertohogardefinitiveedition.ui.registro.RegistrarseScreen
import com.example.huertohogardefinitiveedition.view.DrawerMenu
import com.example.huertohogardefinitiveedition.view.ProductoFormScreen
import com.example.huertohogardefinitiveedition.view.QrScannerScreen
import com.example.huertohogardefinitiveedition.viewmodel.QrViewModel

@Composable
fun AppNav(hasCameraPermission: Boolean, onRequestPermission: () -> Unit) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {

        // --- RUTA PRINCIPAL DE LOGIN ---
        composable("login") {
            LoginScreen(navController = navController)
        }

        // --- RUTAS DEL MENÚ PRINCIPAL (DrawerMenu) ---
        composable(
            route = "DrawerMenu/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username").orEmpty()
            DrawerMenu(username = username, navController = navController)
        }

        composable("QRScannerScreen") {
            val qrViewModel: QrViewModel = viewModel()
            QrScannerScreen(
                viewModel = qrViewModel,
                hasCameraPermission = hasCameraPermission,
                onRequestPermission = onRequestPermission
            )
        }

        composable(
            route = "ProductoFormScreen/{nombre}/{precio}/{descripcion}",
            arguments = listOf(
                navArgument("nombre") { type = NavType.StringType },
                navArgument("precio") { type = NavType.StringType },
                navArgument("descripcion") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val nombre = Uri.decode(backStackEntry.arguments?.getString("nombre") ?: "")
            val precio = backStackEntry.arguments?.getString("precio") ?: ""
            val descripcion = Uri.decode(backStackEntry.arguments?.getString("descripcion") ?: "")
            ProductoFormScreen(
                navController = navController,
                nombre = nombre,
                precio = precio,
                descripcion = descripcion
            )
        }

        // --- RUTAS NUEVAS (¡CORREGIDAS!) ---

        // Desde LoginScreen
        composable("registrarse") {
            RegistrarseScreen(navController = navController)
        }

        // Desde LoginScreen
        composable("recuperar_contrasena") {
            RecuperarContrasenaScreen(navController = navController)
        }

        // Desde el menú de DrawerMenu
        composable("gestion_perfil") {
            GestionPerfilScreen(navController = navController)
        }

        // Desde el menú de DrawerMenu (para Admin)
        // La llamada en DrawerMenu usa "gestion_usuarios", así que la ruta debe ser igual.
        composable("gestion_usuarios") {
            GestionUsuarioScreen(navController = navController)
        }
    }
}
