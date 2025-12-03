package com.example.carrentv1.Screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.* 
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.carrentv1.Navegation.AppScreens
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import androidx.compose.ui.graphics.SolidColor // <--- Añadir esta importación

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun  NuevoAlquilerScreen(navController: NavController, carId: String?) {
    val context = LocalContext.current
    val db = Firebase.firestore

    var cantidad by remember { mutableStateOf("") }
    var unidad by remember { mutableStateOf("Hora(s)") }
    val opciones = listOf("Hora(s)", "Día(s)", "Semana(s)")

    var carDetails by remember { mutableStateOf<CarDetails?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(carId) {
        if (carId == null) {
            errorMessage = "ID del auto no proporcionado para alquiler."
            isLoading = false
            return@LaunchedEffect
        }

        isLoading = true
        errorMessage = null
        try {
            val documentSnapshot = db.collection("coches").document(carId).get().await()
            if (documentSnapshot.exists()) {
                carDetails = CarDetails(
                    id = documentSnapshot.id,
                    imageUrl = documentSnapshot.getString("imageUrl") ?: "",
                    marca = documentSnapshot.getString("marca") ?: "",
                    modelo = documentSnapshot.getString("modelo") ?: "",
                    ubicacion = documentSnapshot.getString("ubicacion") ?: "",
                    precio = documentSnapshot.getString("precio") ?: "",
                    tipoPrecio = documentSnapshot.getString("tipoPrecio") ?: "por hora",
                    calif = documentSnapshot.getDouble("calif") ?: 0.0,
                    duenio = documentSnapshot.getString("duenio") ?: "N/A",
                    telefono = documentSnapshot.getString("telefono") ?: "N/A"
                )
            } else {
                errorMessage = "Auto no encontrado para alquiler."
            }
        } catch (e: Exception) {
            errorMessage = "Error al cargar el auto para alquiler: ${e.message}"
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Alquilar Auto", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp)
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            } else if (errorMessage != null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = errorMessage ?: "Ocurrió un error desconocido", color = MaterialTheme.colorScheme.error, fontSize = 18.sp, textAlign = TextAlign.Center)
                }
            } else if (carDetails == null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Auto no disponible para alquiler.", color = MaterialTheme.colorScheme.onBackground, fontSize = 18.sp, textAlign = TextAlign.Center)
                }
            } else {
                val currentCar = carDetails!!

                Text(
                    text = "Nuevo Alquilamiento",
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(bottom = 16.dp)
                )

                // Información del auto (ahora dinámica)
                Image(
                    painter = rememberAsyncImagePainter(currentCar.imageUrl),
                    contentDescription = "Auto a Alquilar",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("${currentCar.marca} ${currentCar.modelo}", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                Text("Dueño: ${currentCar.duenio}", fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground)
                Text("Precio: $${currentCar.precio} ${currentCar.tipoPrecio}", fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground)

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Cantidad a contratar:",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground,
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
                        onValueChange = { newValue ->
                            if (newValue.all { it.isDigit() }) {
                                cantidad = newValue
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(55.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors( // Usar colores del tema
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
                            cursorColor = MaterialTheme.colorScheme.primary,
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                        )
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    var expanded by remember { mutableStateOf(false) }

                    Box {
                        OutlinedButton(
                            onClick = { expanded = true },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.height(55.dp),
                            colors = ButtonDefaults.outlinedButtonColors( // Usar colores del tema
                                contentColor = MaterialTheme.colorScheme.onSurface
                            ),
                            border = ButtonDefaults.outlinedButtonBorder.copy( // Ajustar borde
                                brush = SolidColor(MaterialTheme.colorScheme.onSurface)
                            )
                        ) {
                            Text(unidad, color = MaterialTheme.colorScheme.onSurface)
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                        ) {
                            opciones.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option, color = MaterialTheme.colorScheme.onSurface) },
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
                    onClick = { 
                        val numericCantidad = cantidad.toIntOrNull()
                        val numericPrecio = currentCar.precio.toDoubleOrNull()

                        if (numericCantidad == null || numericCantidad <= 0) {
                            Toast.makeText(context, "Por favor, ingrese una cantidad válida", Toast.LENGTH_SHORT).show()
                        } else if (numericPrecio == null) {
                            Toast.makeText(context, "El precio del auto no es válido", Toast.LENGTH_SHORT).show()
                        } else {
                            val totalPrice = numericCantidad * numericPrecio
                            Toast.makeText(context, "Confirmar alquiler de ${currentCar.marca} ${currentCar.marca}. Total: $${totalPrice}", Toast.LENGTH_LONG).show()
                            navController.navigate(AppScreens.buildResumenPagoRoute(currentCar.id, totalPrice.toString()))
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(
                        text = "Continuar con el pago",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}