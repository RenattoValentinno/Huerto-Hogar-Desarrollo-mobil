package com.example.huertohogardefinitiveedition.view

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.huertohogardefinitiveedition.R // <-- CAMBIO 1: IMPORT AÑADIDO
import com.example.huertohogardefinitiveedition.data.model.Categoria
import com.example.huertohogardefinitiveedition.data.model.ProductoItem

// --- CAMBIO 2: DATOS COMPLETADOS PARA TODOS LOS PRODUCTOS ---
val listaDeCategorias = listOf(
    Categoria(
        nombre = "Frutas",
        icono = Icons.Default.Agriculture,
        productos = listOf(
            ProductoItem(
                nombre = "Manzanas Fuji",
                precio = "1200",
                descripcion = "Crujientes y dulces, perfectas para cualquier momento del día.",
                imagenResId = R.drawable.logoduoc // Asegúrate de tener este archivo
            ),
            ProductoItem(
                nombre = "Naranjas Valencia",
                precio = "1000",
                descripcion = "Jugo abundante y sabor cítrico ideal para zumos frescos.",
                imagenResId = R.drawable.logoduoc // Asegúrate de tener este archivo
            ),
            ProductoItem(
                nombre = "Plátanos Cavendish",
                precio = "800",
                descripcion = "Fuente natural de potasio y energía rápida.",
                imagenResId = R.drawable.logoduoc // Usando una imagen genérica
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
                imagenResId = R.drawable.logoduoc // Asegúrate de tener este archivo
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

@Composable
fun DrawerMenu(
    username: String,
    navController: NavController
) {
    var categoriaSeleccionada by remember { mutableStateOf(listaDeCategorias.first()) }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.BottomStart
        ) {
            Text(
                text = "Bienvenido, $username",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(16.dp)
            )
        }

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

        // --- CAMBIO 3: TARJETA DE PRODUCTO MEJORADA ---
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(categoriaSeleccionada.productos) { producto ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    onClick = {
                        val nombre = Uri.encode(producto.nombre)
                        val precio = producto.precio
                        val descripcion = Uri.encode(producto.descripcion)
                        navController.navigate("ProductoFormScreen/$nombre/$precio/$descripcion")
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

@Preview(showBackground = true)
@Composable
fun DrawerMenuPreview() {
    val navController = androidx.navigation.compose.rememberNavController()
    DrawerMenu(username = "Usuario Prueba", navController = navController)
}
