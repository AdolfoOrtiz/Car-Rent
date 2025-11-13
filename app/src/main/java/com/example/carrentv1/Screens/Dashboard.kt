package com.example.carrentv1.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.carrentv1.Navegation.AppScreens
import com.example.carrentv1.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAutos(navController: NavController) {

    val autos = listOf(
        mapOf("nombre" to "Nissan Sentra", "ubicacion" to "En Aguascalientes", "precio" to "$200 por hora", "calif" to "4.8"),
        mapOf("nombre" to "VW Jetta", "ubicacion" to "En Calvillo", "precio" to "$150 por hora", "calif" to "3.8"),
        mapOf("nombre" to "Nissan Versa", "ubicacion" to "En Jesús María", "precio" to "$200 por hora", "calif" to "4.8"),
        mapOf("nombre" to "Chevy Monza", "ubicacion" to "En Aguascalientes", "precio" to "$150 por hora", "calif" to "3.8")
    )

    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var expanded by remember { mutableStateOf(false) }

    val opcionesMenu = listOf("Añadir Auto", "Soporte", "Contacto", "Perfil", "Más Información", "Cerrar Sesión")

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(Color(0xFFF5CBA7))
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                opcionesMenu.forEach { item ->
                    Button(
                        onClick = {
                            scope.launch { drawerState.close() }
                            // Aquí puedes navegar según el botón
                            when (item) {
                                "Añadir Auto" -> navController.navigate(AppScreens.RentaAuto.route)
                                "Perfil" ->{navController.navigate(route = AppScreens.Perfil.route)}
                                "Cerrar Sesión" -> { navController.navigate(route = AppScreens.Inicio.route) }
                                "Contacto" -> { navController.navigate(route = AppScreens.Contacto.route) }
                                "Soporte" -> { navController.navigate(route = AppScreens.Soporte.route) }
                                "Más Información" -> { navController.navigate(route = AppScreens.MasInfo.route) }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    ) {
                        Text(
                            text = item,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Car-Rent",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.Black)
                        }
                    },
                    actions = {
                        Box {
                            IconButton(onClick = { expanded = !expanded }) {
                                Icon(Icons.Default.MoreVert, contentDescription = "Más", tint = Color.Black)
                            }
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier.background(Color.White)
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Compartir", color = Color.Black) },
                                    onClick = { expanded = false }
                                )
                                DropdownMenuItem(
                                    text = { Text("Ajustes", color = Color.Black) },
                                    onClick = { navController.navigate(route = AppScreens.Ajustes.route)}
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
                )
            }
        ) { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues)
                    .padding(horizontal = 12.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    placeholder = { Text("Busca tu Auto Favorito", color = Color.Gray, fontSize = 14.sp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(20.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))
                Divider(color = Color.LightGray)
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Autos en Renta",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2A57C9),
                    modifier = Modifier.padding(start = 4.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(autos) { auto ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.car_red),
                                contentDescription = "Auto ${auto["nombre"]}",
                                modifier = Modifier
                                    .size(120.dp)
                                    .padding(4.dp)
                                    .clickable { navController.navigate(route = AppScreens.detalleAuto.route) },
                                contentScale = ContentScale.Fit
                            )

                            Text(auto["nombre"].toString(), fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = Color.Black)
                            Text(auto["ubicacion"].toString(), fontSize = 13.sp, color = Color.Gray)
                            Text(auto["precio"].toString(), fontSize = 13.sp, color = Color.Black)
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(Icons.Default.Star, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
                                Text(text = auto["calif"].toString(), fontSize = 13.sp, color = Color.Black)
                            }
                        }
                    }
                }
            }
        }
    }
}
