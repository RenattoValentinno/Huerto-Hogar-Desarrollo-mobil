package com.example.huertohogardefinitiveedition.view

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.huertohogardefinitiveedition.data.session.SessionManager
import com.example.huertohogardefinitiveedition.viewmodel.ProductoViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ProductoFormScreen(
    navController: NavController,
    nombre: String,
    precio: String,
    descripcion: String,
    vm: ProductoViewModel = viewModel() // Mantenemos el ViewModel por si se usa en el futuro
) {
    val context = LocalContext.current

    // --- ESTADOS DE LA PANTALLA ---
    var cantidad by remember { mutableStateOf("1") }
    var direccion by remember { mutableStateOf("") }
    // ¡NUEVO! Estado para controlar la visibilidad del diálogo de la boleta
    var mostrarDialogoBoleta by remember { mutableStateOf(false) }

    // --- LÓGICA DE CÁLCULO ---
    val precioBase = precio.toIntOrNull() ?: 0
    val cantidadNum = cantidad.toIntOrNull() ?: 0
    val total = precioBase * cantidadNum
    val formatoMoneda = remember { NumberFormat.getCurrencyInstance(Locale("es", "CL")) }

    // Obtenemos el nombre del usuario desde la sesión para la boleta
    val nombreUsuario = SessionManager.currentUser?.nombre ?: "Cliente"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = nombre, style = MaterialTheme.typography.headlineMedium)
        Text(text = "Precio Unitario: ${formatoMoneda.format(precioBase)}", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
        ) {
            Text(
                text = descripcion,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Justify
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = cantidad,
            onValueChange = {
                val newText = it.filter { char -> char.isDigit() }
                cantidad = if (newText.startsWith("0") && newText.length > 1) newText.substring(1) else newText
            },
            label = { Text("Cantidad") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = direccion,
            onValueChange = { direccion = it },
            label = { Text("Dirección de entrega") },
            placeholder = { Text("Ej: Av. Siempreviva 742") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))

        Text("Total a Pagar", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Text(
            text = formatoMoneda.format(total),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.ExtraBold
        )
        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                // Validaciones antes de mostrar la boleta
                if (cantidad.isBlank() || cantidadNum <= 0) {
                    Toast.makeText(context, "La cantidad debe ser mayor a cero", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                if (direccion.isBlank()) {
                    Toast.makeText(context, "La dirección es obligatoria", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                // ¡NUEVO! En lugar de navegar, mostramos el diálogo
                mostrarDialogoBoleta = true
            },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Comprar Ahora", style = MaterialTheme.typography.titleMedium)
        }
    }

    // --- ¡NUEVO! DIÁLOGO DE BOLETA ---
    if (mostrarDialogoBoleta) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoBoleta = false },
            title = { Text("🎉 Compra Realizada 🎉") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("¡Gracias por tu compra, $nombreUsuario!", fontWeight = FontWeight.Bold)
                    Divider(modifier = Modifier.padding(vertical = 4.dp))
                    Text("Resumen del Pedido:")
                    Text(" • Producto: $nombre")
                    Text(" • Cantidad: $cantidadNum")
                    Text(" • Dirección de Entrega: $direccion")
                    Divider(modifier = Modifier.padding(vertical = 4.dp))
                    Text(
                        "Total Pagado: ${formatoMoneda.format(total)}",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarDialogoBoleta = false
                        // Ahora sí, después de aceptar la boleta, volvemos al menú
                        navController.popBackStack()
                    }
                ) {
                    Text("Aceptar")
                }
            }
        )
    }
}
