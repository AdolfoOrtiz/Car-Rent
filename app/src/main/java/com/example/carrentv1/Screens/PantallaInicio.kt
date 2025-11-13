package com.example.carrentv1.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.carrentv1.R

@Preview
@Composable
fun PantallaInicio() {
    Box(
        modifier = Modifier
            .fillMaxSize().background(Color.White), // Ocupa toda la pantalla
        contentAlignment = Alignment.Center,


        ) {
        Image(
            painter = painterResource(id = R.drawable.car_rent),
            contentDescription = "Imagen del logo",
            modifier = Modifier.size(150.dp) // Tamaño opcional
        )
    }
}

