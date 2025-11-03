package com.example.huertohogardefinitiveedition.ui.login

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.huertohogardefinitiveedition.R
import com.example.huertohogardefinitiveedition.data.repository.UserRepository
import com.example.huertohogardefinitiveedition.data.session.SessionManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    vm: LoginViewModel = viewModel()
) {
    val state = vm.uiState
    var showPass by remember { mutableStateOf(false) }

    // Usamos el tema de colores de tu compañera que está muy bien definido
    val huertoHogarColors = lightColorScheme(
        primary = Color(0xFF4CAF50),
        onPrimary = Color.White,
        secondary = Color(0xFFFF9800),
        onSecondary = Color.White,
        surface = Color(0xFFFFF8F5),
        onSurface = Color(0xFF3A3A3A)
    )

    MaterialTheme(colorScheme = huertoHogarColors) {
        Scaffold { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Bienvenido a Huerto Hogar!",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Asegúrate de tener una imagen `logo_huerto_hogar` en `res/drawable`
                // o cámbiala por `R.drawable.logoduoc`
                Image(
                    painter = painterResource(id = R.drawable.logo_huerto_hogar),
                    contentDescription = "Logo App",
                    modifier = Modifier.height(150.dp).padding(bottom = 24.dp),
                    contentScale = ContentScale.Fit
                )

                OutlinedTextField(
                    value = state.username,
                    onValueChange = vm::onUsernameChange,
                    label = { Text("Usuario o correo") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.9f)
                )

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = state.password,
                    onValueChange = vm::onPasswordChange,
                    label = { Text("Contraseña") },
                    singleLine = true,
                    visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { showPass = !showPass }) {
                            Icon(if (showPass) Icons.Default.VisibilityOff else Icons.Default.Visibility, null)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(0.9f)
                )

                if (state.error != null) {
                    Text(
                        text = state.error ?: "",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(Modifier.height(24.dp))

                Button(
                    onClick = {
                        if (state.username.isBlank() || state.password.isBlank()) {
                            vm.setError("Debes completar todos los campos")
                            return@Button
                        }
                        // Lógica de login directa con el nuevo repositorio
                        val result = UserRepository.login(state.username, state.password)
                        result.onSuccess { user ->
                            SessionManager.login(user) // Inicia la sesión
                            val uname = Uri.encode(user.usuario)
                            navController.navigate("DrawerMenu/$uname") {
                                popUpTo("login") { inclusive = true }
                            }
                        }.onFailure { e ->
                            vm.setError(e.message ?: "Credenciales inválidas")
                        }
                    },
                    modifier = Modifier.fillMaxWidth(0.6f).height(48.dp),
                    shape = RoundedCornerShape(50)
                ) {
                    Text("Iniciar Sesión")
                }

                Spacer(Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Crear cuenta",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable { navController.navigate("registrarse") }
                    )
                    Text(
                        text = "¿Olvidaste tu contraseña?",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable { navController.navigate("recuperar_contrasena") }
                    )
                }
            }
        }
    }
}

