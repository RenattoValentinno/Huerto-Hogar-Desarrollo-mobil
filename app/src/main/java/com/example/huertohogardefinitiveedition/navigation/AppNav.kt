package com.example.huertohogardefinitiveedition.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.huertohogardefinitiveedition.ui.home.MuestraDatosScreen
import com.example.huertohogardefinitiveedition.ui.login.LoginScreen
import com.example.huertohogardefinitiveedition.view.DrawerMenu
import com.example.huertohogardefinitiveedition.view.ProductoFormScreen
import com.example.huertohogardefinitiveedition.view.QrScannerScreen
import com.example.huertohogardefinitiveedition.viewmodel.QrViewModel

@Composable


fun AppNav(hasCameraPermission: Boolean,
           onRequestPermission: () -> Unit){

    //reamos controlador
    val navController = rememberNavController()

    NavHost( navController= navController, startDestination = "login")
    {
        composable("QRScannerScreen") {
            val qrViewModel: QrViewModel = viewModel()
            QrScannerScreen(
                viewModel = qrViewModel,
                hasCameraPermission = hasCameraPermission,
                onRequestPermission = onRequestPermission
            )
        }

        composable("login"){
            LoginScreen(navController= navController)
        }//fin composable

        composable(
            //route="muestraDatos/{username}",
            route="DrawerMenu/{username}",
            arguments = listOf(
                navArgument("username"){
                    type= NavType.StringType
                }
            )//fin List Of
        )//fin composable 2

        { // inicio
                backStackEntry ->
            val username = backStackEntry.arguments?.getString("username").orEmpty()
            // MuestraDatosScreen(username=username,navController= navController )
            DrawerMenu(username=username,navController= navController )

        }

///  Ruta del Formulario:  ProductoFormScreen

        composable(
            route="ProductoFormScreen/{nombre}/{precio}/{descripcion}",
            arguments = listOf(
                navArgument("nombre"){ type= NavType.StringType },
                navArgument("precio"){ type= NavType.StringType },
                navArgument("descripcion"){ type= NavType.StringType },
            )//fin List Of
        ) // fin composable 3

        { // inicio
                backStackEntry ->
            val nombre = Uri.decode(backStackEntry.arguments?.getString("nombre") ?:"")
            val precio = backStackEntry.arguments?.getString("precio") ?:""
            val descripcion = Uri.decode(backStackEntry.arguments?.getString("descripcion") ?: "")

            ProductoFormScreen( navController= navController,  nombre=nombre,precio= precio, descripcion = descripcion )
        }

    }// Fin NavHost

}//fin AppNav