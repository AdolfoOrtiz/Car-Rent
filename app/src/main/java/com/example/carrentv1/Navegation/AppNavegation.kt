package com.example.carrentv1.Navegation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.carrentv1.Screens.CarDetailScreen
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
import com.example.carrentv1.ui.screens.PantallaAutos
import com.example.carrentv1.ui.screens.TerminosYCondicionesScreenResponsive
import com.example.loginui.PantallaLoginScreen
import com.example.registroapp.ui.screens.RegistroScreen


@Composable
fun AppNavegation (){
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
        composable(route = AppScreens.detalleAuto.route){
            CarDetailScreen(navController)
        }
        composable(route = AppScreens.RentaAuto.route){
            RentCarForm(navController)
        }
        composable(route = AppScreens.AlquilarAuto.route){
            NuevoAlquilerScreen(navController)
        }
        composable(route = AppScreens.ResumenPago.route){
            ResumenPagoScreen(navController)
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