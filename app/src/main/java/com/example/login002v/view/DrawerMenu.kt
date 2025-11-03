package com.example.login002v.view

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.login002v.data.model.Credential
import com.example.login002v.data.session.SessionManager
import com.example.login002v.ui.gestion.GestionUsuarioScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerMenu(
    username: String,          // respaldo por si no hay sesión cargada aún
    navController: NavController
) {
    
    val HuertoHogarColors = lightColorScheme(
        primary    = Color(0xFF4CAF50),
        onPrimary  = Color.White,
        secondary  = Color(0xFFFF9800),
        onSecondary= Color.White,
        surface    = Color(0xFFFFF8F5),
        onSurface  = Color(0xFF3A3A3A)
    )

    MaterialTheme(colorScheme = HuertoHogarColors) {

        Column(modifier = Modifier.fillMaxSize()) {

            // TopAppBar con menú
            val current = SessionManager.currentUser
            val displayName = current?.nombre ?: current?.usuario ?: username
            val isAdmin =
                (current?.idUsuario == Credential.Admin.idUsuario) ||
                        (current?.usuario?.equals(Credential.Admin.usuario, ignoreCase = true) == true)

            var menuOpen by remember { mutableStateOf(false) }

            TopAppBar(
                title = {
                    Column {
                        Text(text = "Perfil: $displayName")
                        if (!current?.correo.isNullOrBlank()) {
                            Text(
                                text = current!!.correo,
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
                            text = { Text("Actualizar datos") },
                            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                            onClick = {
                                menuOpen = false
                                navController.navigate("gestion_usuario")
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Historial de pedidos") },
                            leadingIcon = { Icon(Icons.Default.History, contentDescription = null) },
                            onClick = {
                                menuOpen = false
                                navController.navigate("historial_pedidos")
                            }
                        )
                        if (isAdmin) {
                            DropdownMenuItem(
                                text = { Text("Gestionar usuarios") },
                                leadingIcon = { Icon(Icons.Default.AdminPanelSettings, contentDescription = null) },

                                onClick = {
                                    menuOpen = false
                                    navController.navigate("Gestion")


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
                                navController.navigate("login") {
                                    popUpTo("login") { inclusive = true }
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
            // FIN HEADER

             LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                item{
                    NavigationDrawerItem(
                        label = { Text("hamburguesa Clasica") },
                        selected = false,
                        onClick = {
                            val nombre = Uri.encode("hamburguesa Clasica")
                            val precio = "5000"
                            navController.navigate("ProductoFormScreen/$nombre/$precio")
                        },
                        icon = { Icon(Icons.Default.Fastfood , contentDescription = "Clasica") }
                    )
                }

                item{
                    NavigationDrawerItem(
                        label = { Text("hamburguesa BBQ") },
                        selected = false,
                        onClick = { /* acción futura */ },
                        icon = { Icon(Icons.Default.LunchDining , contentDescription = "BBQ") }
                    )
                }

                item{
                    NavigationDrawerItem(
                        label = { Text("hamburguesa Veggie") },
                        selected = false,
                        onClick = { /* acción futura */ },
                        icon = { Icon(Icons.Default.Grass, contentDescription = "Veggie") }
                    )
                }

                item{
                    NavigationDrawerItem(
                        label = { Text("hamburguesa Picante") },
                        selected = false,
                        onClick = { /* acción futura */ },
                        icon = { Icon(Icons.Default.LocalFireDepartment, contentDescription = "Picante") }
                    )
                }

                item{
                    NavigationDrawerItem(
                        label = { Text("hamburguesa Doble") },
                        selected = false,
                        onClick = { /* acción futura */ },
                        icon = { Icon(Icons.Default.Star, contentDescription = "Doble") }
                    )
                }
            }
            // ======= FIN CONTENIDO =======

            // Footer
            Text(
                text = "@BurgerApp",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DrawerMenuPreview() {
    val navController = rememberNavController()
    DrawerMenu(username = "Usuario Prueba", navController = navController)
}
