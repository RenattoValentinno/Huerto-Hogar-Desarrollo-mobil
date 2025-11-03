package com.example.huertohogardefinitiveedition.view

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.huertohogardefinitiveedition.R
import com.example.huertohogardefinitiveedition.data.model.Categoria
import com.example.huertohogardefinitiveedition.data.model.ProductoItem
import com.example.huertohogardefinitiveedition.data.session.SessionManager

// --- TU LISTA DE PRODUCTOS (SE MANTIENE INTACTA) ---
val listaDeCategorias = listOf(
    Categoria(
        nombre = "Frutas",
        icono = Icons.Default.Agriculture,
        productos = listOf(
            ProductoItem(
                nombre = "Manzanas Fuji",
                precio = "1200",
                descripcion = "Crujientes y dulces, perfectas para cualquier momento del día.",
                imagenResId = R.drawable.logoduoc
            ),
            ProductoItem(
                nombre = "Naranjas Valencia",
                precio = "1000",
                descripcion = "Jugo abundante y sabor cítrico ideal para zumos frescos.",
                imagenResId = R.drawable.logoduoc
            ),
            ProductoItem(
                nombre = "Plátanos Cavendish",
                precio = "800",
                descripcion = "Fuente natural de potasio y energía rápida.",
                imagenResId = R.drawable.logoduoc
            )
        )
    ),
    Categoria(
        nombre = "Verduras",
        icono = Icons.Default.Grass,
        productos = listOf(
            ProductoItem(
                nombre = "Zanahorias Orgánicas",
                precio = "900",
                descripcion = "Ricas en betacaroteno, ideales para ensaladas y guisos.",
                imagenResId = R.drawable.logoduoc
            ),
            ProductoItem(
                nombre = "Espinacas Frescas",
                precio = "700",
                descripcion = "Llenas de hierro y nutrientes, perfectas para batidos y salteados.",
                imagenResId = R.drawable.logoduoc
            ),
            ProductoItem(
                nombre = "Pimientos Tricolores",
                precio = "1500",
                descripcion = "Un paquete con pimientos rojos, amarillos y verdes.",
                imagenResId = R.drawable.logoduoc
            )
        )
    ),
    Categoria(
        nombre = "Orgánicos",
        icono = Icons.Default.Eco,
        productos = listOf(
            ProductoItem(
                nombre = "Miel Orgánica",
                precio = "5000",
                descripcion = "Pura y sin filtrar, de flores silvestres de la patagonia.",
                imagenResId = R.drawable.logoduoc
            ),
            ProductoItem(
                nombre = "Quinua Orgánica",
                precio = "5050",
                descripcion = "Superalimento rico en proteínas y fibra, libre de gluten.",
                imagenResId = R.drawable.logoduoc
            )
        )
    ),
    Categoria(
        nombre = "Lácteos",
        icono = Icons.Default.Icecream,
        productos = listOf(
            ProductoItem(
                nombre = "Leche Entera",
                precio = "1500",
                descripcion = "Leche fresca y cremosa de vacas de pastoreo libre.",
                imagenResId = R.drawable.logoduoc
            )
        )
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerMenu(
    username: String,
    navController: NavController
) {
    // Definimos el esquema de colores para usarlo en toda la pantalla
    val huertoHogarColors = lightColorScheme(
        primary = Color(0xFF4CAF50),
        onPrimary = Color.White,
        secondary = Color(0xFFFF9800),
        onSecondary = Color.White,
        surface = Color(0xFFFFF8F5),
        onSurface = Color(0xFF3A3A3A)
    )

    // --- LÓGICA DE SESIÓN (DE TU COMPAÑERA) ---
    val currentUser = SessionManager.currentUser
    val displayName = currentUser?.nombre ?: username
    val isAdmin = currentUser?.usuario?.equals("admin", ignoreCase = true) ?: false
    var menuOpen by remember { mutableStateOf(false) }

    // --- TU ESTADO PARA LAS CATEGORÍAS (SE MANTIENE) ---
    var categoriaSeleccionada by remember { mutableStateOf(listaDeCategorias.first()) }

    MaterialTheme(colorScheme = huertoHogarColors) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Huerto Hogar") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    actions = {
                        // Mostramos el nombre del usuario y el menú desplegable
                        Text(
                            text = displayName,
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(end = 8.dp)
                        )
                        IconButton(onClick = { menuOpen = true }) {
                            Icon(Icons.Default.MoreVert, "Menú", tint = MaterialTheme.colorScheme.onPrimary)
                        }

                        // Menú desplegable con las opciones de sesión y perfil
                        DropdownMenu(expanded = menuOpen, onDismissRequest = { menuOpen = false }) {
                            DropdownMenuItem(
                                text = { Text("Mi Perfil") },
                                leadingIcon = { Icon(Icons.Default.Person, null) },
                                onClick = {
                                    menuOpen = false
                                    navController.navigate("gestion_perfil")
                                }
                            )
                            if (isAdmin) {
                                DropdownMenuItem(
                                    text = { Text("Gestionar Usuarios") },
                                    leadingIcon = { Icon(Icons.Default.AdminPanelSettings, null) },
                                    onClick = {
                                        menuOpen = false
                                        navController.navigate("gestion_usuarios")
                                    }
                                )
                            }
                            Divider()
                            DropdownMenuItem(
                                text = { Text("Cerrar Sesión") },
                                leadingIcon = { Icon(Icons.Default.Logout, null) },
                                onClick = {
                                    menuOpen = false
                                    SessionManager.logout()
                                    // Navega al login y limpia todo el historial de navegación
                                    navController.navigate("login") { popUpTo(0) { inclusive = true } }
                                }
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            // --- TU INTERFAZ DE CATÁLOGO (SE MANTIENE, CON EL PADDING DEL SCAFFOLD) ---
            Column(modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()) {
                // Tu LazyRow con las categorías (sin cambios)
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    item {
                        FilterChip(
                            selected = false,
                            onClick = { navController.navigate("QRScannerScreen") },
                            label = { Text("Escanear") },
                            leadingIcon = { Icon(Icons.Default.QrCodeScanner, contentDescription = "QR") }
                        )
                    }
                    items(listaDeCategorias) { categoria ->
                        FilterChip(
                            selected = (categoria.nombre == categoriaSeleccionada.nombre),
                            onClick = { categoriaSeleccionada = categoria },
                            label = { Text(categoria.nombre) },
                            leadingIcon = { Icon(categoria.icono, contentDescription = categoria.nombre) }
                        )
                    }
                }

                // Tu LazyColumn con los productos (sin cambios)
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(categoriaSeleccionada.productos) { producto ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            onClick = {
                                val nombreNav = Uri.encode(producto.nombre)
                                val precioNav = producto.precio
                                val descripcionNav = Uri.encode(producto.descripcion)
                                navController.navigate("ProductoFormScreen/$nombreNav/$precioNav/$descripcionNav")
                            }
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = producto.imagenResId),
                                    contentDescription = producto.nombre,
                                    modifier = Modifier
                                        .size(100.dp)
                                        .padding(8.dp),
                                    contentScale = ContentScale.Crop
                                )
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(vertical = 8.dp)
                                ) {
                                    Text(
                                        text = producto.nombre,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = producto.descripcion,
                                        style = MaterialTheme.typography.bodySmall,
                                        maxLines = 2
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "$${producto.precio}",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.align(Alignment.End)
                                    )
                                }
                            }
                        }
                    }
                }

                // Tu Footer (sin cambios)
                Text(
                    text = "@ 2025 Huerto Hogar App",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DrawerMenuPreview() {
    val navController = rememberNavController()
    DrawerMenu(username = "Usuario Prueba", navController = navController)
}
