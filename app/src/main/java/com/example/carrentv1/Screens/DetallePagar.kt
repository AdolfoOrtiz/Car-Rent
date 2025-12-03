package com.example.carrentv1.Screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.carrentv1.Navegation.AppScreens // Importar AppScreens
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch // Importar launch para coroutines
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResumenPagoScreen(navController: NavController, carId: String?, totalPrice: String?) {
    val context = LocalContext.current
    val db = Firebase.firestore
    val scope = rememberCoroutineScope() // Crear un scope para coroutines

    var carDetails by remember { mutableStateOf<CarDetails?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(carId) {
        if (carId == null) {
            errorMessage = "ID del auto no proporcionado."
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
                errorMessage = "Auto no encontrado."
            }
        } catch (e: Exception) {
            errorMessage = "Error al cargar el auto: ${e.message}"
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        } finally {
            isLoading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        } else if (errorMessage != null || carDetails == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = errorMessage ?: "No se pudieron cargar los detalles.",
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            val currentCar = carDetails!!
            // Título
            Text(
                text = "Resumen",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Información del vehículo
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                     Image(
                        painter = rememberAsyncImagePainter(currentCar.imageUrl),
                        contentDescription = "Imagen del Auto",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = "${currentCar.marca} ${currentCar.modelo}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Precio base:",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "$${currentCar.precio} ${currentCar.tipoPrecio}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            // Ubicación
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Ubicación:",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = currentCar.ubicacion,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Total a pagar
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Total a pagar:",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Text(
                        text = "$${totalPrice ?: "0.00"}",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón de PayPal
            Button(
                onClick = { 
                    if (carId != null) {
                        scope.launch {
                            try {
                                db.collection("coches").document(carId).delete().await()
                                Toast.makeText(context, "¡Pago exitoso y auto eliminado de la base de datos!", Toast.LENGTH_LONG).show()
                                navController.navigate(AppScreens.dashboard.route) {
                                    popUpTo(AppScreens.dashboard.route) { inclusive = true }
                                }
                            } catch (e: Exception) {
                                Toast.makeText(context, "Error al procesar el pago o eliminar el auto: ${e.message}", Toast.LENGTH_LONG).show()
                            }
                        }
                    } else {
                        Toast.makeText(context, "Error: ID del auto no disponible para procesar el pago.", Toast.LENGTH_LONG).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = "Pay with PayPal",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}
