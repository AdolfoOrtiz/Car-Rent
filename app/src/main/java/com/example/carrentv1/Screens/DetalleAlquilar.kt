package com.example.carrentv1.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.carrentv1.Navegation.AppScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevoAlquilerScreen(navController: NavController) {
    var cantidad by remember { mutableStateOf("") }
    var unidad by remember { mutableStateOf("Hora(s)") }
    val opciones = listOf("Hora(s)", "Día(s)", "Semana(s)")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2A57C9)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Nuevo Alquilamiento",
                fontSize = 22.sp,
                color = Color(0xFF2A57C9),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 16.dp)
            )

            // Información del auto
            Text("Chevy Monza", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            Text("Dueño: Daniel Gutierrez", fontSize = 16.sp, color = Color.Black)
            Text("Kilometraje: 90,000 km", fontSize = 16.sp, color = Color.Black)
            Text("Precio: 150 por hora", fontSize = 16.sp, color = Color.Black)

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Cantidad a contratar:",
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = cantidad,
                    onValueChange = { cantidad = it },
                    modifier = Modifier
                        .weight(1f)
                        .height(55.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.width(8.dp))

                var expanded by remember { mutableStateOf(false) }

                Box {
                    OutlinedButton(
                        onClick = { expanded = true },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.height(55.dp)
                    ) {
                        Text(unidad, color = Color.Black)
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(Color.White)
                    ) {
                        opciones.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    unidad = option
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { navController.navigate(route = AppScreens.ResumenPago.route) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B5E54)),
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Continuar con el pago",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}
