package com.example.login002v.view

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Grass
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.LunchDining
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun DrawerMenu(
    username: String,
    navController: NavController
) {
    Column(modifier = Modifier.fillMaxSize())
    {//inicio contenido interior de columna

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp) //densidad de pixel
                .background(MaterialTheme.colorScheme.primary) //material theme permite pintar - definido en ui/theme/type.kt
        ) //fin box
        {//inicio de aplicacion contenido

            Text(
                text = "Categorias user: $username",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .align(Alignment.BottomStart)
            ) //fin text

        }//fin de contenido
        //lazyColumn crea lista elementos que se pueden desplazar verticalmente
        LazyColumn(
            modifier = Modifier.weight(1f)//una fracción
        ) {  //contenido lazyColumn
            //siempre va en navcontroller NavigationDrawerItem para este caso

            item{
                NavigationDrawerItem(
                    label = {Text("hamburguesa Clasica")},
                    selected = false,
                    onClick = {

                        val nombre = Uri.encode("hamburguesa Clasica")
                        val precio = "5000"
                        navController.navigate("ProductoFormScreen/$nombre/$precio")
                    }//fin onClick

                    ,//agregar adicional se pone , a nav
                    icon ={Icon(Icons.Default.Fastfood , contentDescription = "Clasica")}
                )
            }//fin item 1

            item{
                NavigationDrawerItem(
                    label = {Text("hamburguesa BBQ")},
                    selected = false,
                    onClick = {

                        /* accion futura */
                    }//fin onClick

                    ,//agregar adicional se pone , a nav
                    icon ={Icon(Icons.Default.LunchDining , contentDescription = "BBQ")}
                )
            }//fin item 2

            item{
                NavigationDrawerItem(
                    label = {Text("hamburguesa Veggie")},
                    selected = false,
                    onClick = {

                        /* accion futura */
                    }//fin onClick

                    ,//agregar adicional se pone , a nav
                    icon ={Icon(Icons.Default.Grass, contentDescription = "Veggie")}
                )
            }//fin item 3

            item{
                NavigationDrawerItem(
                    label = {Text("hamburguesa Picante")},
                    selected = false,
                    onClick = {

                        /* accion futura */
                    }//fin onClick

                    ,//agregar adicional se pone , a nav
                    icon ={Icon(Icons.Default.LocalFireDepartment, contentDescription = "Picante")}
                )
            }//fin item 4

            item{
                NavigationDrawerItem(
                    label = {Text("hamburguesa Doble")},
                    selected = false,
                    onClick = {

                        /* accion futura */
                    }//fin onClick

                    ,//agregar adicional se pone , a nav
                    icon ={Icon(Icons.Default.Star, contentDescription = "Doble")}
                )
            }//fin item 5









        }// fin contenido lazyColumn

        //footer
        Text(
            text = "@BurgerApp",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),//densidad de pixel
            textAlign = TextAlign.Center
            //

        ) //fin text


    }//fin column

}//fin DrawerMenu

@Preview(showBackground = true)
@Composable
fun DrawerMenuPreview() {
    val navController = rememberNavController()
    DrawerMenu(username = "Usuario Prueba", navController = navController)
}//fin DrawerMenuPreview
