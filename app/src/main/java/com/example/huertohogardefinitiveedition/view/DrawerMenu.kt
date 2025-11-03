package com.example.huertohogardefinitiveedition.view

import android.net.Uri
import androidx.compose.foundation.Image
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
import com.example.huertohogardefinitiveedition.data.model.Credential
import com.example.huertohogardefinitiveedition.data.model.ProductoItem
import com.example.huertohogardefinitiveedition.data.session.SessionManager
private object Routes {
    const val Login = "login"
    const val GestionPerfil = "gestion"          // o "gestion_usuario"
    const val GestionUsuarios = "gestion_usuarios"      // o "Gestion"
    const val HistorialPedidos = "historial_pedidos"
    const val QRScanner = "QRScannerScreen"
    const val ProductoFormBase = "ProductoFormScreen"   // define tu destino con 3 args
    const val Carrito = "carrito"

    const val Block = "block"

    const val Resena = "resena"
}

//LISTA DE CATEGORÍAS Y PRODUCTOS
val listaDeCategorias = listOf(
    Categoria(
        nombre = "Frutas",
        icono = Icons.Default.Agriculture,
        productos = listOf(
            ProductoItem(
                nombre = "Manzanas Fuji",
                precio = "1200",
                descripcion = "Manzanas Fuji crujientes y dulces, cultivadas en el Valle del Maule. \n" +
                        "Perfectas para meriendas saludables o como ingrediente en postres. Estas manzanas \n" +
                        "son conocidas por su textura firme y su sabor equilibrado entre dulce y ácido.",
                imagenResId = R.drawable.manzana_fuji
            ),
            ProductoItem(
                nombre = "Naranjas Valencia",
                precio = "1000",
                descripcion = "Jugosas y ricas en vitamina C, estas naranjas Valencia son ideales para \n" +
                        "zumos frescos y refrescantes. Cultivadas en condiciones climáticas óptimas que \n" +
                        "aseguran su dulzura y jugosidad.",
                imagenResId = R.drawable.naranja_valencia
            ),
            ProductoItem(
                nombre = "Plátanos Cavendish",
                precio = "800",
                descripcion = "Plátanos maduros y dulces, perfectos para el desayuno o como snack \n" +
                        "energético. Estos plátanos son ricos en potasio y vitaminas, ideales para mantener una \n" +
                        "dieta equilibrada.",
                imagenResId = R.drawable.platano_cavendish
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
                descripcion = "Zanahorias crujientes cultivadas sin pesticidas en la Región de O'Higgins. \n" +
                        "Excelente fuente de vitamina A y fibra, ideales para ensaladas, jugos o como snack \n" +
                        "saludable.",
                imagenResId = R.drawable.zanahorias
            ),
            ProductoItem(
                nombre = "Espinacas Frescas",
                precio = "700",
                descripcion = "Espinacas frescas y nutritivas, perfectas para ensaladas y batidos verdes. \n" +
                        "Estas espinacas son cultivadas bajo prácticas orgánicas que garantizan su calidad y valor \n" +
                        "nutricional.",
                imagenResId = R.drawable.espinaca
            ),
            ProductoItem(
                nombre = "Pimientos Tricolores",
                precio = "1500",
                descripcion = "Pimientos rojos, amarillos y verdes, ideales para salteados y platos \n" +
                        "coloridos. Ricos en antioxidantes y vitaminas, estos pimientos añaden un toque vibrante \n" +
                        "y saludable a cualquier receta.",
                imagenResId = R.drawable.pimientos
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
                descripcion = "Miel pura y orgánica producida por apicultores locales. Rica en \n" +
                        "antioxidantes y con un sabor inigualable, perfecta para endulzar de manera natural tus \n" +
                        "comidas y bebidas.",
                imagenResId = R.drawable.miel_organica
            ),
            ProductoItem(
                nombre = "Quinua Orgánica",
                precio = "5050",
                descripcion = "Superalimento rico en proteínas y fibra, libre de gluten.",
                imagenResId = R.drawable.quinua_organica
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
                imagenResId = R.drawable.leche_entera
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
    // Paleta de colores local
    val huertoHogarColors = lightColorScheme(
        primary     = Color(0xFF4CAF50),
        onPrimary   = Color.White,
        secondary   = Color(0xFFFF9800),
        onSecondary = Color.White,
        surface     = Color(0xFFFFF8F5),
        onSurface   = Color(0xFF3A3A3A)
    )

    // Sesión
    val current = SessionManager.currentUser
    val displayName = when {
        !current?.nombre.isNullOrBlank()  -> current!!.nombre
        !current?.usuario.isNullOrBlank() -> current!!.usuario
        else -> username
    }
    // Admin
    val isAdmin = (current?.idUsuario == Credential.Admin.idUsuario) || (current?.usuario?.equals(Credential.Admin.usuario, ignoreCase = true) == true)

    // UI state
    var menuOpen by remember { mutableStateOf(false) }
    var categoriaSeleccionada by remember { mutableStateOf(listaDeCategorias.first()) }

    MaterialTheme(colorScheme = huertoHogarColors) {
        Scaffold(
            // TOP APP BAR
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                text = "Perfil: $displayName",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            val correo = current?.correo
                            if (!correo.isNullOrBlank()) {
                                Text(
                                    text = correo,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
                                )
                            }
                        }
                    },
                    actions = {
                        IconButton(onClick = { menuOpen = true }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "Menú de perfil",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        DropdownMenu(
                            expanded = menuOpen,
                            onDismissRequest = { menuOpen = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Mi Perfil") },
                                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                                onClick = {
                                    menuOpen = false
                                    navController.navigate(Routes.GestionPerfil)
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Historial de pedidos") },
                                leadingIcon = { Icon(Icons.Default.History, contentDescription = null) },
                                onClick = {
                                    menuOpen = false
                                    navController.navigate(Routes.HistorialPedidos)
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Block") },
                                leadingIcon = { Icon(Icons.Default.Apps, contentDescription = null) },
                                onClick = {
                                    menuOpen = false
                                    navController.navigate(Routes.Block)
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Reseñas") },
                                leadingIcon = { Icon(Icons.Default.AccountBalanceWallet, contentDescription = null) },
                                onClick = {
                                    menuOpen = false
                                    navController.navigate(Routes.Resena)
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Carrito (proximamente)") },
                                leadingIcon = { Icon(Icons.Default.History, contentDescription = null) },
                                onClick = {
                                    menuOpen = false
                                    navController.navigate(Routes.Carrito)
                                }
                            )
                            if (isAdmin) {
                                DropdownMenuItem(
                                    text = { Text("Gestionar usuarios") },
                                    leadingIcon = { Icon(Icons.Default.AdminPanelSettings, contentDescription = null) },
                                    onClick = {
                                        menuOpen = false
                                        navController.navigate(Routes.GestionUsuarios)
                                    }
                                )
                            }
                            Divider()
                            DropdownMenuItem(
                                text = { Text("Cerrar sesión") },
                                leadingIcon = { Icon(Icons.Default.Logout, contentDescription = null) },
                                onClick = {
                                    menuOpen = false
                                    SessionManager.logout()
                                    navController.navigate(Routes.Login) {
                                        popUpTo(0) { inclusive = true }
                                        launchSingleTop = true
                                    }
                                }
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        ) { innerPadding ->

            //categorías + productos
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                // categorías + botón QR
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
                            onClick = { navController.navigate(Routes.QRScanner) },
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

                // Lista de productos por categoría
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
                                navController.navigate(
                                    "${Routes.ProductoFormBase}/$nombreNav/$precioNav/$descripcionNav"
                                )
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

                // Footer
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
