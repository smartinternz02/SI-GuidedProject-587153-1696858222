package com.example.chatu.nav

import androidx.navigation.NavHostController
import com.example.chatu.nav.Destination.Home
import com.example.chatu.nav.Destination.Login
import com.example.chatu.nav.Destination.Register

/**
 * A set of destination used in the whole application
 */

// it is basically a sealed class i mean the object destination class

object Destination {
    const val Welcome = "welcome"
    const val Register = "register"
    const val Login = "login"
    const val Home = "home"
}

/**
 * Set of routes which will be passed to different composable so that
 * the routes which are required can be taken.
 */

// This is just a class to for easy navigation.
//We can do it separately in composable function

class Action(navController: NavHostController) {
    val home: () -> Unit = {
        navController.navigate(Home) {
            popUpTo(Login) {
                inclusive = true
            }
            popUpTo(Register) {
                inclusive = true
            }
        }
    }
    val login: () -> Unit = { navController.navigate(Login) }
    val register: () -> Unit = { navController.navigate(Register) }
    val navigateBack: () -> Unit = { navController.popBackStack()}
}