package com.example.huertohogardefinitiveedition.view

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
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Grass
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.LunchDining
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
import androidx.compose.animation.AnimatedVisibility // Importa esto
import androidx.compose.foundation.clickable // Importa esto
import androidx.compose.foundation.layout.Row // Importa esto
import androidx.compose.material.icons.filled.ExpandLess // Importa esto
import androidx.compose.material.icons.filled.ExpandMore // Importa esto
import androidx.compose.runtime.getValue // Importa esto
import androidx.compose.runtime.mutableStateOf // Importa esto
import androidx.compose.runtime.remember // Importa esto
import androidx.compose.runtime.setValue // Importa esto
import androidx.compose.material.icons.filled.QrCodeScanner

@Composable

fun DrawerMenu(
    username:String,
    navController: NavController
){ // inicio

    Column(modifier =Modifier.fillMaxSize())
    { //inicio columna
        Box(
            modifier =Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(MaterialTheme.colorScheme.primary)
        ) // fin box
        { //inicio contenido
            Text(
                text="Categorias user:$username",
                style=MaterialTheme.typography.headlineSmall,
                color= MaterialTheme.colorScheme.onPrimary,
                modifier =Modifier
                    .align(Alignment.BottomStart)
            )
        } //fin contenido

// LazyColumn: Crear la lista de delemnetos que se pueden desplazar verticalmente

        LazyColumn( modifier =Modifier.weight(1f)){
            item {
                NavigationDrawerItem(
                    label = { Text("Escanear QR de Producto") },
                    selected = false,
                    onClick = {
                        // Navega a la pantalla que activa la cámara para escanear
                        // Asegúrate de que "QRScannerScreen" es el nombre correcto de tu ruta.
                        navController.navigate("QRScannerScreen")
                    },
                    icon = { Icon(Icons.Default.QrCodeScanner, contentDescription = "Escanear QR") }
                )
            }

            item {
                // Estado para saber si la categoría de frutas está expandida
                var frutasExpanded by remember { mutableStateOf(false) }

                // El título de la categoría que se puede clickear
                NavigationDrawerItem(
                    label = { Text("Frutas Frescas") },
                    selected = false,
                    onClick = { frutasExpanded = !frutasExpanded }, // Cambia el estado
                    icon = { Icon(Icons.Default.Agriculture, contentDescription = "Frutas") },
                    // Añadimos un ícono para indicar si está expandido o no
                    badge = {
                        Icon(
                            imageVector = if (frutasExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = "Expandir"
                        )
                    }
                )

                // Contenido que se mostrará solo si está expandido
                AnimatedVisibility(visible = frutasExpanded) {
                    Column(modifier = Modifier.padding(start = 24.dp)) { // Padding para anidar
                        NavigationDrawerItem(
                            label = { Text("Manzanas Fuji") },
                            selected = false,
                            onClick = {
                                val nombre = Uri.encode("Manzanas Fuji")
                                val precio = "1200"
                                navController.navigate("ProductoFormScreen/$nombre/$precio")
                            },
                            icon = { /* Puedes dejarlo vacío o poner un ícono pequeño */ }
                        )

                        NavigationDrawerItem(
                            label = { Text("Naranjas Valencia") },
                            selected = false,
                            onClick = {
                                val nombre = Uri.encode("Naranjas Valencia")
                                val precio = "1000"
                                navController.navigate("ProductoFormScreen/$nombre/$precio")
                            },
                            icon = { /* Puedes dejarlo vacío o poner un ícono pequeño */ }
                        )

                        NavigationDrawerItem(
                            label = { Text("Plátanos Cavendish") },
                            selected = false,
                            onClick = {
                                val nombre = Uri.encode("Plátanos Cavendish")
                                val precio = "800"
                                navController.navigate("ProductoFormScreen/$nombre/$precio")
                            },
                            icon = { /* Puedes dejarlo vacío o poner un ícono pequeño */ }
                        )
                        // Aquí podrías añadir más frutas...
                    }
                }
            }// fin item 1

            item {
                // Estado para saber si la categoría de frutas está expandida
                var verdurasExpanded by remember { mutableStateOf(false) }

                // El título de la categoría que se puede clickear
                NavigationDrawerItem(
                    label = { Text("Verduras Orgánicas") },
                    selected = false,
                    onClick = { verdurasExpanded = !verdurasExpanded }, // Cambia el estado
                    icon = { Icon(Icons.Default.Agriculture, contentDescription = "Verduras") },
                    // Añadimos un ícono para indicar si está expandido o no
                    badge = {
                        Icon(
                            imageVector = if (verdurasExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = "Expandir"
                        )
                    }
                )

                // Contenido que se mostrará solo si está expandido
                AnimatedVisibility(visible = verdurasExpanded) {
                    Column(modifier = Modifier.padding(start = 24.dp)) { // Padding para anidar
                        NavigationDrawerItem(
                            label = { Text("Zanahorias Orgánicas") },
                            selected = false,
                            onClick = {
                                val nombre = Uri.encode("Zanahorias Orgánicas")
                                val precio = "900"
                                navController.navigate("ProductoFormScreen/$nombre/$precio")
                            },
                            icon = { /* Puedes dejarlo vacío o poner un ícono pequeño */ }
                        )

                        NavigationDrawerItem(
                            label = { Text("Espinacas Frescas") },
                            selected = false,
                            onClick = {
                                val nombre = Uri.encode("Espinacas Frescas")
                                val precio = "700"
                                navController.navigate("ProductoFormScreen/$nombre/$precio")
                            },
                            icon = { /* Puedes dejarlo vacío o poner un ícono pequeño */ }
                        )

                        NavigationDrawerItem(
                            label = { Text("Pimientos Tricolores") },
                            selected = false,
                            onClick = {
                                val nombre = Uri.encode("Pimientos Tricolores")
                                val precio = "1500"
                                navController.navigate("ProductoFormScreen/$nombre/$precio")
                            },
                            icon = { /* Puedes dejarlo vacío o poner un ícono pequeño */ }
                        )
                        // Aquí podrías añadir más frutas...
                    }
                }
            }// fin item 2

            item {
                // Estado para saber si la categoría de Productos Orgánicos está expandida
                var organicosExpanded by remember { mutableStateOf(false) }

                // El título de la categoría que se puede clickear
                NavigationDrawerItem(
                    label = { Text("Productos Orgánicos") },
                    selected = false,
                    onClick = { organicosExpanded = !organicosExpanded }, // Cambia el estado
                    icon = { Icon(Icons.Default.Agriculture, contentDescription = "Productos Orgánicos") },
                    // Añadimos un ícono para indicar si está expandido o no
                    badge = {
                        Icon(
                            imageVector = if (organicosExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = "Expandir"
                        )
                    }
                )

                // Contenido que se mostrará solo si está expandido
                AnimatedVisibility(visible = organicosExpanded) {
                    Column(modifier = Modifier.padding(start = 24.dp)) { // Padding para anidar
                        NavigationDrawerItem(
                            label = { Text("Miel Orgánica") },
                            selected = false,
                            onClick = {
                                val nombre = Uri.encode("Miel Orgánica")
                                val precio = "5000"
                                navController.navigate("ProductoFormScreen/$nombre/$precio")
                            },
                            icon = { /* Puedes dejarlo vacío o poner un ícono pequeño */ }
                        )

                        NavigationDrawerItem(
                            label = { Text("Quinua Orgánica") },
                            selected = false,
                            onClick = {
                                val nombre = Uri.encode("Quinua Orgánica")
                                val precio = "5050"
                                navController.navigate("ProductoFormScreen/$nombre/$precio")
                            },
                            icon = { /* Puedes dejarlo vacío o poner un ícono pequeño */ }
                        )
                        // Aquí podrías añadir más Productos Orgánicos...
                    }
                }
            }// fin item 3

            item {
                // Estado para saber si la categoría de lacteos está expandida
                var lacteosExpanded by remember { mutableStateOf(false) }

                // El título de la categoría que se puede clickear
                NavigationDrawerItem(
                    label = { Text("Productos Lácteos") },
                    selected = false,
                    onClick = { lacteosExpanded = !lacteosExpanded }, // Cambia el estado
                    icon = { Icon(Icons.Default.Agriculture, contentDescription = "Lácteos") },
                    // Añadimos un ícono para indicar si está expandido o no
                    badge = {
                        Icon(
                            imageVector = if (lacteosExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = "Expandir"
                        )
                    }
                )

                // Contenido que se mostrará solo si está expandido
                AnimatedVisibility(visible = lacteosExpanded) {
                    Column(modifier = Modifier.padding(start = 24.dp)) { // Padding para anidar
                        NavigationDrawerItem(
                            label = { Text("Leche Entera") },
                            selected = false,
                            onClick = {
                                val nombre = Uri.encode("Leche Entera")
                                val precio = "1500"
                                navController.navigate("ProductoFormScreen/$nombre/$precio")
                            },
                            icon = { /* Puedes dejarlo vacío o poner un ícono pequeño */ }
                        )
                        // Aquí podrías añadir más lacteos...
                    }
                }
            }// fin item 4


        } // fin Lazy

//Footer

        Text(
            text="@ 2025 Huerto Hogar App",
            style=MaterialTheme.typography.bodySmall,
            modifier =Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center

        )


    } //fin columna


}// fin DrawerMenu


@Preview(showBackground = true)
@Composable


fun DrawerMenuPreview(){
    val navController = androidx.navigation.compose.rememberNavController()
    DrawerMenu(username = "Usuario Prueba", navController = navController)
}