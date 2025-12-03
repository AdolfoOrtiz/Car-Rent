package com.example.carrentv1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.carrentv1.Navegation.AppScreens
import com.example.carrentv1.Screens.*
import com.example.carrentv1.ui.screens.PantallaAutos
import com.example.carrentv1.ui.screens.TerminosYCondicionesScreenResponsive
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.delay
import androidx.navigation.navArgument
import androidx.navigation.NavType

import com.example.carrentv1.ui.theme.CarRentV1Theme // Importar el tema
import com.example.carrentv1.data.ThemePreference // Importar ThemePreference
import androidx.compose.runtime.collectAsState // Importar collectAsState
import androidx.compose.runtime.getValue // Importar getValue
import androidx.compose.ui.platform.LocalContext // Importar LocalContext
import androidx.compose.runtime.remember // <--- Añadir esta línea

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current // Obtener el contexto
            val themePreference = remember { ThemePreference(context) } // Crear instancia de ThemePreference
            val darkTheme by themePreference.themePreference.collectAsState(initial = false) // Leer la preferencia del tema

            CarRentV1Theme(darkTheme = darkTheme) { // Envolver con tu tema
                AppNavigation()
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = "splash",
        enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn() },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut() }
    ) {
        // Pantalla Splash
        composable("splash") {
            SplashScreen(navController)
        }
        
        // Todas tus pantallas (corregidas según AppNavegation.kt)
        composable(route = AppScreens.Inicio.route){
            InicioScreen(navController)
        }
        composable(route = AppScreens.Registro.route){
            RegistroScreen(navController)
        }
        composable(route = AppScreens.Login.route){
            PantallaLoginScreen(navController)
        }
        composable(route = AppScreens.dashboard.route){
            PantallaAutos(navController)
        }
        composable(route = AppScreens.RentaAuto.route){
            RentCarForm(navController)
        }
        composable(
            route = AppScreens.detalleAuto.route, 
            arguments = listOf(navArgument("carId") { type = NavType.StringType })
        ) { backStackEntry ->
            val carId = backStackEntry.arguments?.getString("carId")
            CarDetailScreen(navController, carId = carId)
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
        composable(route = AppScreens.Terminos.route){
            TerminosYCondicionesScreenResponsive(navController)
        }
        composable(
            route = AppScreens.AlquilarAuto.route,
            arguments = listOf(navArgument("carId") { type = NavType.StringType })
        ) { backStackEntry ->
            val carId = backStackEntry.arguments?.getString("carId")
            NuevoAlquilerScreen(navController, carId = carId)
        }
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
    }
}

@Composable
fun SplashScreen(navController: NavHostController) {
    LaunchedEffect(Unit) {
        delay(3000) // Espera 3 segundos
        navController.navigate(AppScreens.Inicio.route) {
            popUpTo("splash") { inclusive = true } // Elimina el splash del historial
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Cargando...")
    }
}