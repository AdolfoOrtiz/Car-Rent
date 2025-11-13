package com.example.carrentv1.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RentCarForm(navController: NavController) {
    // Estados de los campos
    var marca by remember { mutableStateOf(TextFieldValue("")) }
    var modelo by remember { mutableStateOf(TextFieldValue("")) }
    var aireAcondicionado by remember { mutableStateOf(false) }
    var anio by remember { mutableStateOf("2025") }
    var puertas by remember { mutableStateOf("1") }
    var precio by remember { mutableStateOf(TextFieldValue("")) }
    var tipoPrecio by remember { mutableStateOf("Hora") }

    var color by remember { mutableStateOf(TextFieldValue("")) }
    var kilometraje by remember { mutableStateOf(TextFieldValue("")) }
    var version by remember { mutableStateOf(TextFieldValue("")) }
    var transmision by remember { mutableStateOf(TextFieldValue("")) }
    var formatoImagen by remember { mutableStateOf("JPG") }

    Scaffold(
        containerColor = Color(0xFFF2F2F2)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Renta tu\nVehículo",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B4F9C),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFFBD2A8))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Ingresa los datos del vehículo",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = marca,
                    onValueChange = { marca = it },
                    label = { Text("Marca") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = modelo,
                    onValueChange = { modelo = it },
                    label = { Text("Modelo") },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Aire Acondicionado")
                    Checkbox(
                        checked = aireAcondicionado,
                        onCheckedChange = { aireAcondicionado = it }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedTextField(
                        value = anio,
                        onValueChange = { anio = it },
                        label = { Text("Año") },
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedTextField(
                        value = puertas,
                        onValueChange = { puertas = it },
                        label = { Text("Puertas") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedTextField(
                        value = precio,
                        onValueChange = { precio = it },
                        label = { Text("Precio") },
                        modifier = Modifier.weight(2f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedTextField(
                        value = tipoPrecio,
                        onValueChange = { tipoPrecio = it },
                        label = { Text("Hora") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Especificaciones técnicas",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = color,
                    onValueChange = { color = it },
                    label = { Text("Color") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = kilometraje,
                    onValueChange = { kilometraje = it },
                    label = { Text("Kilometraje") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = version,
                    onValueChange = { version = it },
                    label = { Text("Versión") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = transmision,
                    onValueChange = { transmision = it },
                    label = { Text("Transmisión") },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedTextField(
                        value = "Subir imagen",
                        onValueChange = {},
                        enabled = false,
                        modifier = Modifier.weight(2f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedTextField(
                        value = formatoImagen,
                        onValueChange = { formatoImagen = it },
                        label = { Text("JPG") },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}


