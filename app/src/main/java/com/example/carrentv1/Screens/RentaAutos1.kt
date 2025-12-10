package com.example.carrentv1.Screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.carrentv1.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RentCarForm(navController: NavController) {

    var marca by remember { mutableStateOf(TextFieldValue("")) }
    var modelo by remember { mutableStateOf(TextFieldValue("")) }
    var aireAcondicionado by remember { mutableStateOf(false) }
    var anio by remember { mutableStateOf("") }
    var puertas by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf(TextFieldValue("")) }
    var tipoPrecio by remember { mutableStateOf("Hora") }

    var color by remember { mutableStateOf(TextFieldValue("")) }
    var kilometraje by remember { mutableStateOf(TextFieldValue("")) }
    var version by remember { mutableStateOf(TextFieldValue("")) }
    var transmision by remember { mutableStateOf(TextFieldValue("")) }

    var imageUrlInput by remember { mutableStateOf(TextFieldValue("")) }

    val context = LocalContext.current
    val db = Firebase.firestore

    Scaffold(containerColor = MaterialTheme.colorScheme.background) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(R.string.renta_tu_vehiculo),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // CARD 1 – DATOS DEL VEHÍCULO
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = stringResource(R.string.ingresa_datos_vehiculo),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = marca,
                        onValueChange = { marca = it },
                        label = { Text(stringResource(R.string.marca)) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = modelo,
                        onValueChange = { modelo = it },
                        label = { Text(stringResource(R.string.modelo)) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(stringResource(R.string.aire_acondicionado))
                        Checkbox(
                            checked = aireAcondicionado,
                            onCheckedChange = { aireAcondicionado = it }
                        )
                    }

                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = anio,
                            onValueChange = { anio = it },
                            label = { Text(stringResource(R.string.anio)) },
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedTextField(
                            value = puertas,
                            onValueChange = { puertas = it },
                            label = { Text(stringResource(R.string.puertas)) },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = precio,
                            onValueChange = { precio = it },
                            label = { Text(stringResource(R.string.precio)) },
                            modifier = Modifier.weight(2f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedTextField(
                            value = tipoPrecio,
                            onValueChange = { tipoPrecio = it },
                            label = { Text(stringResource(R.string.tipo_precio)) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // CARD 2 – ESPECIFICACIONES TÉCNICAS
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.especificaciones_tecnicas),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = color,
                        onValueChange = { color = it },
                        label = { Text(stringResource(R.string.color)) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = kilometraje,
                        onValueChange = { kilometraje = it },
                        label = { Text(stringResource(R.string.kilometraje)) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = version,
                        onValueChange = { version = it },
                        label = { Text(stringResource(R.string.version)) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = transmision,
                        onValueChange = { transmision = it },
                        label = { Text(stringResource(R.string.transmision)) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // CARD 3 – URL IMAGEN
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.url_imagen),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )

                    OutlinedTextField(
                        value = imageUrlInput,
                        onValueChange = { imageUrlInput = it },
                        label = { Text(stringResource(R.string.pegar_url_imagen)) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    if (imageUrlInput.text.isNotEmpty()) {
                        Image(
                            painter = rememberImagePainter(imageUrlInput.text),
                            contentDescription = stringResource(R.string.imagen_vehiculo),
                            modifier = Modifier
                                .size(200.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(200.dp)
                                .background(MaterialTheme.colorScheme.surfaceVariant),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(stringResource(R.string.no_hay_imagen))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // BOTÓN GUARDAR
            Button(
                onClick = {
                    if (marca.text.isNotEmpty() && modelo.text.isNotEmpty() &&
                        anio.isNotEmpty() && puertas.isNotEmpty() && precio.text.isNotEmpty() &&
                        imageUrlInput.text.isNotEmpty()
                    ) {

                        val carData = hashMapOf(
                            "marca" to marca.text,
                            "modelo" to modelo.text,
                            "aireAcondicionado" to aireAcondicionado,
                            "anio" to anio,
                            "puertas" to puertas,
                            "precio" to precio.text,
                            "tipoPrecio" to tipoPrecio,
                            "color" to color.text,
                            "kilometraje" to kilometraje.text,
                            "version" to version.text,
                            "transmision" to transmision.text,
                            "imageUrl" to imageUrlInput.text
                        )

                        db.collection("coches")
                            .add(carData)
                            .addOnSuccessListener {
                                Toast.makeText(context, R.string.vehiculo_guardado, Toast.LENGTH_LONG).show()
                                navController.popBackStack()
                            }
                            .addOnFailureListener {
                                Toast.makeText(context, R.string.error_guardar, Toast.LENGTH_LONG).show()
                            }

                    } else {
                        Toast.makeText(context, R.string.faltan_campos, Toast.LENGTH_LONG).show()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(stringResource(R.string.guardar_vehiculo))
            }
        }
    }
}
