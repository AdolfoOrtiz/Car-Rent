package com.example.carrentv1.Screens

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
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
            errorMessage = context.getString(R.string.error_id_no_enviado)
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
                    marca = documentSnapshot.getString("marca") ?: "N/A",
                    modelo = documentSnapshot.getString("modelo") ?: "N/A",
                    ubicacion = documentSnapshot.getString("ubicacion") ?: "N/A",
                    precio = documentSnapshot.getString("precio") ?: "N/A",
                    tipoPrecio = documentSnapshot.getString("tipoPrecio") ?: "",
                    calif = documentSnapshot.getDouble("calif") ?: 0.0,
                    duenio = documentSnapshot.getString("duenio") ?: "N/A",
                    telefono = documentSnapshot.getString("telefono") ?: "N/A"
                )
            } else {
                errorMessage = context.getString(R.string.error_auto_no_encontrado)
            }
        } catch (e: Exception) {
            errorMessage = "${context.getString(R.string.error_cargar_auto)}: ${e.message}"
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.detalle_del_auto)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.volver),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        } else if (errorMessage != null) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text(
                    text = errorMessage ?: stringResource(R.string.error_cargar_auto),
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        } else if (carDetails == null) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text(
                    text = stringResource(R.string.auto_no_disponible),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            val currentCar = carDetails!!

            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                PortraitLayout(padding, currentCar, navController)
            } else {
                LandscapeLayout(padding, currentCar, navController)
            }
        }
    }
}

@Composable
private fun PortraitLayout(padding: PaddingValues, car: CarDetails, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CarInfoContent(car, Alignment.CenterHorizontally, navController, isLandscape = false)
    }
}

@Composable
private fun LandscapeLayout(padding: PaddingValues, car: CarDetails, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(horizontal = 24.dp, vertical = 8.dp), // Padding vertical reducido
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp) // Espacio reducido
    ) {
        Image(
            painter = rememberAsyncImagePainter(car.imageUrl),
            contentDescription = stringResource(R.string.car_description_prefix, "${car.marca} ${car.modelo}"),
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1.2f)
                .clip(RoundedCornerShape(16.dp))
        )

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CarInfoContent(car, Alignment.CenterHorizontally, navController, isLandscape = true)
        }
    }
}

@Composable
private fun CarInfoContent(
    car: CarDetails, 
    alignment: Alignment.Horizontal, 
    navController: NavController, 
    isLandscape: Boolean
) {
    Column(horizontalAlignment = alignment, verticalArrangement = Arrangement.Center) {
        if (!isLandscape) {
             Image(
                painter = rememberAsyncImagePainter(car.imageUrl),
                contentDescription = stringResource(R.string.car_description_prefix, "${car.marca} ${car.modelo}"),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .aspectRatio(1.5f)
                    .clip(RoundedCornerShape(16.dp))
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        Text(
            text = "${car.marca} ${car.modelo}",
            style = if (isLandscape) MaterialTheme.typography.titleMedium else MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = car.ubicacion,
            style = if (isLandscape) MaterialTheme.typography.bodySmall else MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(if (isLandscape) 4.dp else 12.dp))

        Text(
            text = "$${car.precio} / ${car.tipoPrecio}",
            style = if (isLandscape) MaterialTheme.typography.titleSmall else MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(if (isLandscape) 2.dp else 8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = stringResource(R.string.calificacion_desc),
                tint = Color(0xFFFFC107),
                modifier = Modifier.size(if (isLandscape) 16.dp else 24.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = car.calif.toString(), style = if (isLandscape) MaterialTheme.typography.labelMedium else MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.height(if (isLandscape) 4.dp else 16.dp))
        
        if (!isLandscape) {
            Divider(modifier = Modifier.padding(horizontal = 32.dp))
            Spacer(modifier = Modifier.height(16.dp))
        }

        Text(
            text = "${stringResource(R.string.dueno)}: ${car.duenio}",
            style = if (isLandscape) MaterialTheme.typography.labelMedium else MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "${stringResource(R.string.telefono_label)}: ${car.telefono}",
            style = if (isLandscape) MaterialTheme.typography.labelMedium else MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(if (isLandscape) 8.dp else 24.dp))

        Button(
            onClick = { navController.navigate(AppScreens.buildAlquilarAutoRoute(car.id)) },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier
                .fillMaxWidth(if (isLandscape) 1f else 0.8f)
                .height(if (isLandscape) 36.dp else 50.dp)
        ) {
            Text(
                text = stringResource(R.string.alquilar_auto_titulo),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = if (isLandscape) 12.sp else 16.sp
            )
        }
    }
}