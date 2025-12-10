package com.example.carrentv1.Navegation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.example.carrentv1.Screens.ConfiguracionScreen
import com.example.carrentv1.Screens.ContactoScreen
import com.example.carrentv1.Screens.InicioScreen
import com.example.carrentv1.Screens.MasInformacionScreen
import com.example.carrentv1.Screens.NuevoAlquilerScreen
import com.example.carrentv1.Screens.PerfilUsuarioScreen
import com.example.carrentv1.Screens.PreguntasFrecuentesScreen
import com.example.carrentv1.Screens.RentCarForm
import com.example.carrentv1.Screens.RestablecerContrasenaScreen
import com.example.carrentv1.Screens.ResumenPagoScreen
import com.example.carrentv1.Screens.SoporteTecnicoScreen
import com.example.carrentv1.Screens.PantallaAutos // <<--- CORRECCIÓN AQUÍ
import com.example.carrentv1.ui.screens.TerminosYCondicionesScreenResponsive
import androidx.navigation.navArgument // Importar navArgument
import androidx.navigation.NavType // Importar NavType
import com.example.carrentv1.Screens.CarDetailScreen // Importación CORREGIDA

import com.example.carrentv1.Navegation.AppScreens
import com.example.carrentv1.Screens.RegistroScreen
import com.example.carrentv1.Screens.PantallaLoginScreen


@Composable
fun  AppNavegation (){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.Inicio.route){
        composable(route = AppScreens.Inicio.route){
            InicioScreen(navController)
        }
        composable(route = AppScreens.Registro.route){
            RegistroScreen(navController)
        }
        composable(route = AppScreens.Login.route){
            PantallaLoginScreen(navController)
        }
        composable(route = AppScreens.Terminos.route){
            TerminosYCondicionesScreenResponsive(navController)
        }
        composable(route = AppScreens.dashboard.route){
            PantallaAutos(navController)
        }
        composable(
            route = AppScreens.detalleAuto.route, // Usar la ruta del object directamente con el placeholder
            arguments = listOf(navArgument("carId") { type = NavType.StringType })
        ) { backStackEntry ->
            val carId = backStackEntry.arguments?.getString("carId")
            CarDetailScreen(navController, carId = carId) // ¡Pasar el carId a CarDetailScreen!
        }
        composable(route = AppScreens.RentaAuto.route){
            RentCarForm(navController)
        }
        composable(
            route = AppScreens.AlquilarAuto.route,
            arguments = listOf(navArgument("carId") { type = NavType.StringType })
        ) { backStackEntry ->
            val carId = backStackEntry.arguments?.getString("carId")
            NuevoAlquilerScreen(navController, carId = carId) // ¡Pasar el carId a NuevoAlquilerScreen!
        }
        // **CORRECCIÓN AQUÍ: `ResumenPago` ahora espera carId y totalPrice**
        composable(
            route = AppScreens.ResumenPago.route,
            arguments = listOf(
                navArgument("carId") { type = NavType.StringType },
                navArgument("totalPrice") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val carId = backStackEntry.arguments?.getString("carId")
            val totalPrice = backStackEntry.arguments?.getString("totalPrice")
            ResumenPagoScreen(navController, carId = carId, totalPrice = totalPrice)
        }
        composable(route = AppScreens.Ajustes.route){
            ConfiguracionScreen(navController)
        }
        composable(route = AppScreens.Contacto.route){
            ContactoScreen(navController)
        }
        composable(route = AppScreens.Soporte.route){
            SoporteTecnicoScreen(navController)
        }
        composable(route = AppScreens.Perfil.route){
            PerfilUsuarioScreen(navController)
        }
        composable(route = AppScreens.MasInfo.route){
            MasInformacionScreen(navController)
        }
        composable(route = AppScreens.PreguntasFre.route){
            PreguntasFrecuentesScreen(navController)
        }
        composable(route = AppScreens.RecuperarContra.route){
            RestablecerContrasenaScreen(navController)
        }


    }
}