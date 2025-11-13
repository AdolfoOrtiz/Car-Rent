package com.example.carrentv1.Screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.carrentv1.Navegation.AppScreens
import com.example.carrentv1.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarDetailScreen(navController: NavController) {
    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        containerColor = Color(0xFFF2F2F2)
    ) { padding ->

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
                        painter = painterResource(id = R.drawable.car_red),
                        contentDescription = "Car Image",
                        modifier = Modifier
                            .size(220.dp)
                            .padding(top = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Chevy Monza",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "En Aguascalientes",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "$150 por hora",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Rating",
                            tint = Color.Black
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "3.8", fontSize = 14.sp)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(text = "Dueño: Daniel Gutierrez", fontSize = 14.sp)
                    Text(text = "Tel: 4490000000z", fontSize = 14.sp)

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { navController.navigate(route = AppScreens.AlquilarAuto.route) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B5E54)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(48.dp)
                    ) {
                        Text(text = "Alquilar", fontSize = 16.sp, color = Color.White)
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
                        painter = painterResource(id = R.drawable.car_red),
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
                            text = "Chevy Monza",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "En Aguascalientes",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "$150 por hora",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "Rating",
                                tint = Color.Black
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "3.8", fontSize = 14.sp)
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(text = "Dueño: Daniel Gutierrez", fontSize = 14.sp)
                        Text(text = "Tel: 4490000000z", fontSize = 14.sp)

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = {  navController.navigate(route = AppScreens.AlquilarAuto.route)},
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B5E54)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .height(48.dp)
                        ) {
                            Text(text = "Alquilar", fontSize = 16.sp, color = Color.White)
                        }
                    }
                }
            }
        }
    }
}
