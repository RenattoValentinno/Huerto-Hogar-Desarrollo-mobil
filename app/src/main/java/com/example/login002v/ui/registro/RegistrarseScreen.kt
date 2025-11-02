package com.example.login002v.ui.registro

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults.outlinedButtonColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material3.lightColorScheme

@Composable
fun RegistrarseScreen(navController: NavController) {


    // -------------------------------------------------------------------------
    val HuertoHogarColors = lightColorScheme(
        primary    = Color(0xFF4CAF50),   //  Verde principal
        onPrimary  = Color.White,                // Texto blanco sobre el verde
        secondary  = Color(0xFFFF9800),    //  Naranjo secundario
        onSecondary= Color.White,                 // Texto blanco sobre naranjo
        surface    = Color(0xFFFFF8F5),   //  Fondo claro cálido
        onSurface  = Color(0xFF3A3A3A)    // Texto gris oscuro sobre el fondo
    )

    MaterialTheme(colorScheme = HuertoHogarColors) { // inicio Aplicar Material (tema)

        var nombre    by remember { mutableStateOf("") }
        var correo    by remember { mutableStateOf("") }
        var usuario   by remember { mutableStateOf("") }
        var clave     by remember { mutableStateOf("") }
        var confirmar by remember { mutableStateOf("") }

        // Mostrar/ocultar contraseñas (ícono del ojo)
        var verClave     by remember { mutableStateOf(false) }
        var verConfirmar by remember { mutableStateOf(false) }


        Scaffold { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),                 // hace que la columna ocupe toda la pantalla
                verticalArrangement = Arrangement.Center,          // centra verticalmente
                horizontalAlignment = Alignment.CenterHorizontally // centra horizontalmente
            ) {


                Text(
                    text = "Crear cuenta",                      // título
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(Modifier.height(16.dp)) // espacio visual entre título y formulario


                Column(
                    modifier = Modifier.fillMaxWidth(0.9f),           // ocupa el 90% del ancho
                    verticalArrangement = Arrangement.spacedBy(8.dp)  // espacio entre campos
                ) {
                    // NOMBRE
                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre completo") },
                        singleLine = true, // evita saltos de línea
                        modifier = Modifier.fillMaxWidth(),

                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor   = MaterialTheme.colorScheme.secondary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.25f),
                            cursorColor          = MaterialTheme.colorScheme.secondary
                        ),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(6.dp) // esquinas suaves
                    )


                    OutlinedTextField(
                        value = correo,
                        onValueChange = { correo = it },
                        label = { Text("Correo electrónico") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor   = MaterialTheme.colorScheme.secondary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.25f),
                            cursorColor          = MaterialTheme.colorScheme.secondary
                        ),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(6.dp)
                    )


                    OutlinedTextField(
                        value = usuario,
                        onValueChange = { usuario = it },
                        label = { Text("Usuario") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        supportingText = { Text("Puedes escribirlo o generarlo automáticamente después") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor   = MaterialTheme.colorScheme.secondary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.25f),
                            cursorColor          = MaterialTheme.colorScheme.secondary
                        ),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(6.dp)
                    )

                    // CONTRASEÑA
                    OutlinedTextField(
                        value = clave,
                        onValueChange = { clave = it },
                        label = { Text("Contraseña") },
                        singleLine = true,

                        visualTransformation = if (verClave) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            // Ícono para ver/ocultar
                            IconButton(onClick = { verClave = !verClave }) {
                                Icon(
                                    imageVector = if (verClave) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                    contentDescription = null // opcional (solo visual)
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor   = MaterialTheme.colorScheme.secondary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.25f),
                            cursorColor          = MaterialTheme.colorScheme.secondary
                        ),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(6.dp)
                    )

                    // CONFIRMAR CONTRASEÑA
                    OutlinedTextField(
                        value = confirmar,
                        onValueChange = { confirmar = it },
                        label = { Text("Confirmar contraseña") },
                        singleLine = true,
                        visualTransformation = if (verConfirmar) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { verConfirmar = !verConfirmar }) {
                                Icon(
                                    imageVector = if (verConfirmar) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                    contentDescription = null
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor   = MaterialTheme.colorScheme.secondary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.25f),
                            cursorColor          = MaterialTheme.colorScheme.secondary
                        ),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(6.dp)
                    )
                }


                Spacer(Modifier.height(16.dp)) // espacio entre formulario y botón


                Button(
                    onClick = {
                        //  agregar validaciones/registro real
                        navController.navigate("login")
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.6f)  // ancho 60% del contenedor
                        .height(44.dp),      // altura cómoda
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(50), // ovalado
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor   = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text("Crear cuenta")
                }

                Spacer(Modifier.height(8.dp))


                OutlinedButton(
                    onClick = { navController.navigate("login") },
                    colors = outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Ya tengo cuenta (volver)")
                }
            }
        }
    }
}
