package com.example.login002v.ui.login


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.example.login002v.R
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.lightColorScheme


@OptIn(ExperimentalMaterial3Api::class)
// Permite usar funciones Material 3 qe son experimentales
@Composable  // Genera Interfz Garfica

fun LoginScreen(
    navController: NavController,
    vm: LoginViewModel= viewModel()

){

    val state =vm.uiState
    var showPass by remember { mutableStateOf(false) }

    // es una funcion de material3 que define un color claro
    val ColorScheme = lightColorScheme(
        primary   = Color(0xFF388E3C),  // verde bosque (botón)
        onPrimary = Color.White,        // texto en botones
        surface   = Color(0xFFF9F9FB),  // fondo claro general
        onSurface = Color(0xFF3A3A3A)   // textos gris oscuro
    )

    MaterialTheme(
        colorScheme = ColorScheme
    ){ // inicio Aplicar Material

        Scaffold (
            //  Se define topBar, BottomBar (no necesaria)
        ) // fin Scaff

        {// Inicio Inner
            innerPadding -> // Representa el espacio interno para que no choque con el topBar
            Column (  //   Colaca los elementos de la Ui
                modifier = Modifier
                    .padding( innerPadding) // Evita que quede oculto
                    .fillMaxSize() // Hace que la columnna tome el pantalla completa
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.surface), // fondo del tema claro
                verticalArrangement = Arrangement.Center,           // centra verticalmente
                horizontalAlignment = Alignment.CenterHorizontally  // centra horizontalmente
            )// fin column

            {// inicio Contenido
                Text(text="Bienvenido a Huerto Hogar!",
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize, // tamaño moderado
                    fontWeight = FontWeight.SemiBold,                            // semi negrita elegante
                    color = MaterialTheme.colorScheme.onSurface,                 // gris oscuro
                    modifier = Modifier.padding(bottom = 24.dp)                  // espacio debajo
                ) // Muestra un texto simple en la pantalla

                Image(  // insertar una imagen en la interfaz
                    painter = painterResource(id = R.drawable.logo_huerto_hogar),
                    contentDescription = "Logo App",
                    modifier = Modifier
                        .fillMaxWidth() // Hace que la columnna tome el pantalla completa
                        .height(150.dp) //alto
                        .padding(bottom = 5.dp),  // deja espacio debajo del logo
                    contentScale = ContentScale.Fit
                    // Ajusta la imagen para que encaje dentro del espacio
                )

                // agregar un espacio
                Spacer(modifier = Modifier.height(30.dp))

                OutlinedTextField(
                    value=state.username,
                    onValueChange = vm::onUsernameChange,
                    label={Text("Usuario")},
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)                 // un poco más angosto para verse más limpio
                        .padding(vertical = 4.dp),          // aire arriba/abajo
                    colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                        focusedBorderColor   = MaterialTheme.colorScheme.primary,      // borde al enfocar
                        unfocusedBorderColor = Color(0xFFBDBDBD),               // borde suave sin foco
                        cursorColor          = MaterialTheme.colorScheme.primary,      // color del cursor
                        focusedLabelColor    = MaterialTheme.colorScheme.primary,      // label cuando hay foco
                        unfocusedLabelColor  = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    ),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(6.dp) // esquinas suaves
                )

                OutlinedTextField(
                    value = state.password,
                    onValueChange = vm::onPasswordChange,
                    label = { Text("Contraseña") },
                    singleLine = true,
                    visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        // ícono más compacto que el TextButton
                        androidx.compose.material3.IconButton(onClick = { showPass = !showPass }) {
                            val icon = if (showPass)
                                androidx.compose.material.icons.Icons.Default.VisibilityOff
                            else
                                androidx.compose.material.icons.Icons.Default.Visibility
                            androidx.compose.material3.Icon(icon, contentDescription = null)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)                 // un poco más angosto para verse más limpio
                        .padding(vertical = 4.dp),          // aire arriba/abajo
                    colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                        focusedBorderColor   = MaterialTheme.colorScheme.primary,      // borde al enfocar
                        unfocusedBorderColor = Color(0xFFBDBDBD),               // borde suave sin foco
                        cursorColor          = MaterialTheme.colorScheme.primary,      // color del cursor
                        focusedLabelColor    = MaterialTheme.colorScheme.primary,      // label cuando hay foco
                        unfocusedLabelColor  = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    ),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(6.dp)
                )//password

                // agregar un espacio
                Spacer(modifier = Modifier.height(30.dp))


                if (state.error !=null){
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text=state.error ?:"",
                        color= MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )

                }


                Button(onClick = {
                    vm.submit { user -> navController.navigate("DrawerMenu/user")//navController.navigate("muestraDatos/user")
                        { // inicio navegar
                            popUpTo("login"){ inclusive = true }  // No puede volver al login con Back
                            launchSingleTop = true // Evita crear una nueva instancia
                        } // termino navegar
                    } // fin submit
                },
                    enabled = !state.isLoading,
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary, // color del botón
                        contentColor = Color.White                          // color del texto
                    ),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(50), // ovalado
                    modifier = Modifier
                        .fillMaxWidth(0.6f) // ancho 60% de la pantalla
                        .height(44.dp)      // altura cómoda para el botón
                ) // fin button
                {
                    // Texto del botón
                    Text(
                        if (state.isLoading) "Validando..." else "Iniciar Sesión"
                    )
                } // fin botón

                // agregar un espacio
                Spacer(modifier = Modifier.height(30.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    //  Crear cuenta
                    Text(
                        text = "Crear cuenta",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold, //tipo de letra
                        modifier = Modifier.clickable {
                            // Acción te lleva a
                            navController.navigate("registrarse") // o la ruta que tengas
                        }
                    )

                    // Recuperar contraseña
                    Text(
                        text = "¿Olvidaste tu contraseña?",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold, //tipo de letra
                        modifier = Modifier.clickable {
                            // Acción te lleva a
                            navController.navigate("recuperar_contrasena") // o tu pantalla de recuperación
                        }
                    )
                }




            }// fin Contenido

        } // Fin inner


    } // fin Aplicar Material
}// Fin HomeScreen


@Preview(showBackground = true) // Genera la vista
@Composable  // Genera Interfz Garfica

fun LoginScreenPreview(){

 //   Crear un navController de manera ficticia para fines de la vista previa
    val navController = rememberNavController()

    // Puedes usar un ViewModel simulado aquí si no tienes acceso a uno real
    val vm = LoginViewModel() // Suponiendo que LoginViewModel está correctamente configurado para la vista previa

    LoginScreen(navController = navController, vm = vm)


}// Fin LoginScreen
