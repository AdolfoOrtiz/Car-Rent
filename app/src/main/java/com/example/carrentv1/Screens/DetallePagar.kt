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
import androidx.compose.ui.res.stringResource
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResumenPagoScreen(navController: NavController, carId: String?, totalPrice: String?) {
    val context = LocalContext.current
    val db = Firebase.firestore
    val scope = rememberCoroutineScope()

    var carDetails by remember { mutableStateOf<CarDetails?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(carId) {
        if (carId == null) {
            errorMessage = ""
            isLoading = false
            return@LaunchedEffect
        }

        isLoading = true
        errorMessage = null
        try {
            val snapshot = db.collection("coches").document(carId).get().await()
            if (snapshot.exists()) {
                carDetails = CarDetails(
                    id = snapshot.id,
                    imageUrl = snapshot.getString("imageUrl") ?: "",
                    marca = snapshot.getString("marca") ?: "",
                    modelo = snapshot.getString("modelo") ?: "",
                    ubicacion = snapshot.getString("ubicacion") ?: "",
                    precio = snapshot.getString("precio") ?: "",
                    tipoPrecio = snapshot.getString("tipoPrecio") ?: "",
                    calif = snapshot.getDouble("calif") ?: 0.0,
                    duenio = snapshot.getString("duenio") ?: "",
                    telefono = snapshot.getString("telefono") ?: ""
                )
            } else {
                errorMessage = ""
            }
        } catch (e: Exception) {
            errorMessage =""
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
                CircularProgressIndicator()
            }
        } else if (errorMessage != null || carDetails == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = errorMessage ?: stringResource(R.string.no_detalles),
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            val currentCar = carDetails!!

            Text(
                text = stringResource(R.string.resumen_titulo),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(currentCar.imageUrl),
                        contentDescription = stringResource(R.string.desc_auto),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Text(
                        text = "${currentCar.marca} ${currentCar.modelo}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(R.string.precio_base),
                            fontSize = 16.sp
                        )
                        Text(
                            text = "$${currentCar.precio} ${currentCar.tipoPrecio}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(R.string.ubicacion_label),
                        fontSize = 16.sp
                    )
                    Text(
                        text = currentCar.ubicacion,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.total_pagar),
                        fontSize = 18.sp
                    )
                    Text(
                        text = "$${totalPrice ?: "0.00"}",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (carId != null) {
                        scope.launch {
                            try {
                                db.collection("coches").document(carId).delete().await()
                                Toast.makeText(
                                    context,
                                    "",
                                    Toast.LENGTH_LONG
                                ).show()
                                navController.navigate(AppScreens.dashboard.route) {
                                    popUpTo(AppScreens.dashboard.route) { inclusive = true }
                                }
                            } catch (e: Exception) {
                                Toast.makeText(
                                    context,
                                    "",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = stringResource(R.string.paypal_pago),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
