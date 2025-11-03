package com.example.huertohogardefinitiveedition.navigation

import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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

        // Login
        composable("login") {
            LoginScreen(navController = navController)
        }

        // Drawer (
        composable(
            route = "DrawerMenu/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val usernameArg = backStackEntry.arguments?.getString("username") ?: ""
            DrawerMenu(username = usernameArg, navController = navController)
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

        //RUTAS NUEVAS

        // Registro
        composable("registrarse") {
            RegistrarseScreen(navController = navController)
        }

        // Recuperar contraseña
        composable("recuperar_contrasena") {
            RecuperarContrasenaScreen(navController = navController)
        }

        // Gestión historial pedidos
        composable("historial_pedidos") {

            GestionPerfilScreen(navController = navController)
        }

        // Desde el menú de DrawerMenu (para Admin)
        // La llamada en DrawerMenu usa "gestion_usuarios", así que la ruta debe ser igual.
        composable("gestion_usuarios") {
            GestionUsuarioScreen(navController = navController)
        }

        // 🛒 Historial de pedidos (versión con lista en memoria)
        composable("historial_pedidos") {
            com.example.huertohogardefinitiveedition.view.HistorialPedidosScreen()
        }
        //  block
        composable("block") {
            //remplazar quien tenga esa parte

            SimpleStub("Pantalla: block")
        }



        //  carrito de compras
        composable("carrito") {
            //remplazar quien tenga esa parte

            SimpleStub("Pantalla: Proximamente carrito de compras")
        }
    }
}
@Composable
private fun SimpleStub(texto: String) {
    // Stub para que compile si aún no tienes esas pantallas construidas
    Text(text = texto, modifier = Modifier.padding(24.dp))
}
