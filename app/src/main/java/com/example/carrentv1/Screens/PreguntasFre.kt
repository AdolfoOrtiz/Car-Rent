package com.example.carrentv1.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun PreguntasFrecuentesScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo en la parte superior
        Image(
            painter = painterResource(id = com.example.carrentv1.R.drawable.car_rent),
            contentDescription = "Car-Rent Logo",
            modifier = Modifier
                .size(120.dp)
                .padding(top = 40.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Título principal
        Text(
            text = "Preguntas Frecuentes",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1565C0),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Subtítulo
        Text(
            text = "Car-Rent",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Lista de preguntas
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Pregunta 1
            Text(
                text = "¿Qué necesito para alquilar un coche?",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF333333),
                modifier = Modifier.fillMaxWidth()
            )

            // Pregunta 2
            Text(
                text = "¿Qué incluye el precio del alquiler?",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF333333),
                modifier = Modifier.fillMaxWidth()
            )

            // Pregunta 3
            Text(
                text = "¿Puedo alquilar un coche sin tarjeta de crédito?",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF333333),
                modifier = Modifier.fillMaxWidth()
            )

            // Pregunta 4
            Text(
                text = "¿El seguro está incluido en el alquiler?",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF333333),
                modifier = Modifier.fillMaxWidth()
            )

            // Pregunta 5
            Text(
                text = "¿Puedo devolver el coche en otra oficina diferente?",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF333333),
                modifier = Modifier.fillMaxWidth()
            )

            // Pregunta 6
            Text(
                text = "¿Qué pasa si entrego el coche tarde?",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF333333),
                modifier = Modifier.fillMaxWidth()
            )

            // Pregunta 7
            Text(
                text = "¿Puedo cancelar o modificar mi reserva?",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF333333),
                modifier = Modifier.fillMaxWidth()
            )

            // Pregunta 8
            Text(
                text = "¿Qué documentos debo presentar al recoger el coche?",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF333333),
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

