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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.huertohogardefinitiveedition.R
import com.example.huertohogardefinitiveedition.data.model.Credential
import com.example.huertohogardefinitiveedition.data.session.SessionManager
import com.example.huertohogardefinitiveedition.viewmodel.DrawerMenuViewModel

// LA LISTA DE CATEGORÍAS YA NO VIVE AQUÍ, SE HA MOVIDO AL VIEWMODEL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerMenu(
    username: String,
    navController: NavController,
    // Se inyecta el ViewModel. Compose se encargará de crear y mantener una única instancia.
    viewModel: DrawerMenuViewModel
) {
    // Leemos el estado del ViewModel. Compose observará cambios en esta variable.
    val categoriasState = viewModel.categorias.value

    val huertoHogarColors = lightColorScheme(
        primary = Color(0xFF4CAF50),
        onPrimary = Color.White,
        secondary = Color(0xFFFF9800),
        onSecondary = Color.White,
        surface = Color(0xFFFFF8F5),
        onSurface = Color(0xFF3A3A3A)
    )

    val current = SessionManager.currentUser
    val displayName = current?.nombre?.takeIf { it.isNotBlank() } ?: current?.usuario ?: username
    val isAdmin = (current?.idUsuario == Credential.Admin.idUsuario) || (current?.usuario?.equals(Credential.Admin.usuario, ignoreCase = true) == true)

    var menuOpen by remember { mutableStateOf(false) }
    // La categoría seleccionada ahora depende del estado que viene del ViewModel.
    // Usamos 'LaunchedEffect' para actualizarla si la lista cambia.
    var categoriaSeleccionada by remember { mutableStateOf(categoriasState.first()) }
    LaunchedEffect(categoriasState) {
        if (!categoriasState.contains(categoriaSeleccionada)) {
            categoriaSeleccionada = categoriasState.first()
        }
    }


    MaterialTheme(colorScheme = huertoHogarColors) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                text = "Perfil: $displayName",
                                style = MaterialTheme.typography.titleMedium
                            )
                            current?.correo?.takeIf { it.isNotBlank() }?.let { correo ->
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
                            Icon(Icons.Default.MoreVert, "Menú de perfil")
                        }
                        DropdownMenu(
                            expanded = menuOpen,
                            onDismissRequest = { menuOpen = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Mi Perfil") },
                                leadingIcon = { Icon(Icons.Default.Person, null) },
                                onClick = {
                                    menuOpen = false
                                    navController.navigate("gestion_perfil")
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Historial de pedidos") },
                                leadingIcon = { Icon(Icons.Default.History, null) },
                                onClick = {
                                    menuOpen = false
                                    navController.navigate("historial_pedidos")
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Block") },
                                leadingIcon = { Icon(Icons.Default.Apps, null) },
                                onClick = {
                                    menuOpen = false
                                    navController.navigate("block")
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Carrito (próximamente)") },
                                leadingIcon = { Icon(Icons.Default.ShoppingCart, null) },
                                onClick = {
                                    menuOpen = false
                                    navController.navigate("carrito")
                                }
                            )
                            if (isAdmin) {
                                DropdownMenuItem(
                                    text = { Text("Gestionar usuarios") },
                                    leadingIcon = { Icon(Icons.Default.AdminPanelSettings, null) },
                                    onClick = {
                                        menuOpen = false
                                        navController.navigate("gestion_usuarios")
                                    }
                                )
                            }
                            Divider()
                            DropdownMenuItem(
                                text = { Text("Cerrar sesión") },
                                leadingIcon = { Icon(Icons.Default.Logout, null) },
                                onClick = {
                                    menuOpen = false
                                    SessionManager.logout()
                                    navController.navigate("login") {
                                        popUpTo(0) { inclusive = true }
                                        launchSingleTop = true
                                    }
                                }
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
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
                            leadingIcon = { Icon(Icons.Default.QrCodeScanner, "QR") }
                        )
                    }
                    // El LazyRow ahora itera sobre el estado que viene del ViewModel
                    items(categoriasState) { categoria ->
                        FilterChip(
                            selected = (categoria.nombre == categoriaSeleccionada.nombre),
                            onClick = { categoriaSeleccionada = categoria },
                            label = { Text(categoria.nombre) },
                            leadingIcon = { Icon(categoria.icono, categoria.nombre) }
                        )
                    }
                }

                // El LazyColumn itera sobre los productos de la categoría seleccionada
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
                                // Pasamos el stock actual al navegar
                                navController.navigate(
                                    "ProductoFormScreen/$nombreNav/$precioNav/$descripcionNav/${producto.stock}"
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
                                    Text(text = producto.nombre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(text = producto.descripcion, style = MaterialTheme.typography.bodySmall, maxLines = 2)
                                    Spacer(modifier = Modifier.height(8.dp))
                                    // Mostramos el stock actual del producto
                                    Text(
                                        text = "Quedan: ${producto.stock}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = if (producto.stock < 10) Color.Red else Color.Gray,
                                        modifier = Modifier.align(Alignment.Start)
                                    )
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
    // ¡AQUÍ ESTÁ LA CORRECCIÓN!
    // Creamos una instancia del ViewModel para poder pasársela a la previsualización.
    val previewViewModel = DrawerMenuViewModel()

    DrawerMenu(
        username = "Usuario Prueba",
        navController = navController,
        viewModel = previewViewModel // Le pasamos la instancia creada
    )
}
