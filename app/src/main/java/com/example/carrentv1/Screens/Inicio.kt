package com.example.carrentv1.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.carrentv1.Navegation.AppScreens
import com.example.carrentv1.R

@Composable
fun InicioScreen(navController: NavController) {
    // Fondo blano y contenido centrado
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Imagen del logo
            Image(
                painter = painterResource(id = R.drawable.car_rent),
                contentDescription = "Logo Car-Rent",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón "Ingresar"
            Button(
                onClick = { navController.navigate(route = AppScreens.Login.route) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7EB6FF)),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .width(200.dp)
                    .height(48.dp)
            ) {
                Text(text = "Ingresar", color = Color.White)
            }

            // Botón "Registrarse"
            Button(
                onClick = { navController.navigate(route = AppScreens.Registro.route) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7EB6FF)),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .width(200.dp)
                    .height(48.dp)
            ) {
                Text(text = "Registrarse", color = Color.White)
            }
        }
    }
}

