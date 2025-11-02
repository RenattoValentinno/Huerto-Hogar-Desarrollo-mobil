package com.example.login002v.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.login002v.ui.home.MuestraDatosScreen
import com.example.login002v.ui.login.LoginScreen
import com.example.login002v.view.DrawerMenu
import com.example.login002v.view.ProductoFormScreen
import com.example.login002v.ui.registro.RegistrarseScreen

@Composable


fun AppNav(){

    //veamos controlador
    val navController = rememberNavController()
    NavHost( navController= navController, startDestination = "login")
    {
        composable("login"){
            LoginScreen(navController= navController)
        }//fin composable 1

        //route="muestraDatos/{username}"
        composable(
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
            //(username=username,navController= navController )
            DrawerMenu(username=username,navController= navController )
        }


        composable(
            route="ProductoFormScreen/{nombre}/{precio}",
                arguments = listOf(
                navArgument("nombre"){type= NavType.StringType },
                navArgument("precio"){type= NavType.StringType },
            )//fin List Of
        )//fin composable 3
        {// inicio
                backStackEntry ->
            val nombre = Uri.decode(backStackEntry.arguments?.getString("nombre") ?:"")
            val precio = backStackEntry.arguments?.getString("precio")?:""

            ProductoFormScreen(navController= navController, nombre =nombre,precio = precio )
        }
        //ruta de registro usuario
        composable("registrarse") {
            RegistrarseScreen(navController = navController)
        }





    }



}//fin AppNav