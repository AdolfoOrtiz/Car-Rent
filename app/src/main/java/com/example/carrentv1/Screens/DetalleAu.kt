package com.example.carrentv1.Screens

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.* // Importación para Column, Row, Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.carrentv1.Navegation.AppScreens
import com.example.carrentv1.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

data class CarDetails(
    val id: String = "",
    val imageUrl: String = "",
    val marca: String = "",
    val modelo: String = "",
    val ubicacion: String = "",
    val precio: String = "",
    val tipoPrecio: String = "",
    val calif: Double = 0.0,
    val duenio: String = "",
    val telefono: String = ""
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarDetailScreen(navController: NavController, carId: String?) {
    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation
    val context = LocalContext.current
    val db = Firebase.firestore

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

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = MaterialTheme.colorScheme.onBackground // Adaptado a tema
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent // Transparente para que el fondo del Scaffold sea visible
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background // Adaptado a tema
    ) { padding ->

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary) // Adaptado a tema
            }
        } else if (errorMessage != null) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text(text = errorMessage ?: "Ocurrió un error desconocido", color = MaterialTheme.colorScheme.error, fontSize = 18.sp, textAlign = TextAlign.Center) // Adaptado a tema
            }
        } else if (carDetails == null) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text(text = "Auto no disponible.", color = MaterialTheme.colorScheme.onBackground, fontSize = 18.sp, textAlign = TextAlign.Center) // Adaptado a tema
            }
        } else {
            val currentCar = carDetails!!

            when (orientation) {
                // 🔹 Modo Vertical
                Configuration.ORIENTATION_PORTRAIT -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(currentCar.imageUrl),
                            contentDescription = "Car Image",
                            modifier = Modifier
                                .size(220.dp)
                                .padding(top = 16.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "${currentCar.marca} ${currentCar.modelo}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onBackground // Adaptado a tema
                        )
                        Text(
                            text = "En ${currentCar.ubicacion}",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f) // Adaptado a tema
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "$${currentCar.precio} ${currentCar.tipoPrecio}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onBackground // Adaptado a tema
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "Rating",
                                tint = MaterialTheme.colorScheme.onBackground // Adaptado a tema
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = currentCar.calif.toString(), fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground) // Adaptado a tema
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(text = "Dueño: ${currentCar.duenio}", fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground) // Adaptado a tema
                        Text(text = "Tel: ${currentCar.telefono}", fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground) // Adaptado a tema

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = { navController.navigate(AppScreens.buildAlquilarAutoRoute(currentCar.id)) },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary), // Adaptado a tema
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .height(48.dp)
                        ) {
                            Text(text = "Alquilar", fontSize = 16.sp, color = MaterialTheme.colorScheme.onPrimary) // Adaptado a tema
                        }
                    }
                }

                // 🔹 Modo Horizontal
                Configuration.ORIENTATION_LANDSCAPE -> {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .padding(24.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // Imagen del auto
                        Image(
                            painter = rememberAsyncImagePainter(currentCar.imageUrl),
                            contentDescription = "Car Image",
                            modifier = Modifier.size(220.dp)
                        )

                        // Información del auto + botón
                        Column(
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "${currentCar.marca} ${currentCar.modelo}",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onBackground // Adaptado a tema
                            )
                            Text(
                                text = "En ${currentCar.ubicacion}",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f) // Adaptado a tema
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "$${currentCar.precio} ${currentCar.tipoPrecio}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onBackground // Adaptado a tema
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Star,
                                    contentDescription = "Rating",
                                    tint = MaterialTheme.colorScheme.onBackground // Adaptado a tema
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = currentCar.calif.toString(), fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground) // Adaptado a tema
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(text = "Dueño: ${currentCar.duenio}", fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground) // Adaptado a tema
                            Text(text = "Tel: ${currentCar.telefono}", fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground) // Adaptado a tema

                            Spacer(modifier = Modifier.height(24.dp))

                            Button(
                                onClick = { navController.navigate(AppScreens.buildAlquilarAutoRoute(currentCar.id)) },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary), // Adaptado a tema
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                                    .height(48.dp)
                            ) {
                                Text(text = "Alquilar", fontSize = 16.sp, color = MaterialTheme.colorScheme.onPrimary) // Adaptado a tema
                            }
                        }
                    }
                }
            }
        }
    }
}