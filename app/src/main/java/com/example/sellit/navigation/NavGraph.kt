package com.example.sellit.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sellit.ui.screens.AddProductScreen
import com.example.sellit.ui.screens.ForgotPasswordScreen
import com.example.sellit.ui.screens.ProductDetailScreen
import com.example.sellit.ui.screens.ProfileScreen
import com.example.sellit.ui.screens.RegisterScreen
import com.example.sellit.ui.screens.HomeScreen
import com.example.sellit.ui.screens.SplashScreen
import com.example.sellit.ui.screens.LoginScreen

sealed class Screen(val route: String) {
    // all screens routes
    data object Splash : Screen("splash")
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object ForgotPassword : Screen("forgot_password")
    data object Home : Screen("home")
    data object AddProduct : Screen("add_product")
    data object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: String) = "product_detail/$productId"
    }
    data object Profile : Screen("profile"
}

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Splash.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // all screen navigation
        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(Screen.Register.route) {
            RegisterScreen(navController)
        }
        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.AddProduct.route) {
            AddProductScreen(navController)
        }
        composable(
            Screen.ProductDetail.route,
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            ProductDetailScreen(
                navController,
                productId = backStackEntry.arguments?.getString("productId") ?: ""
            )
        }
        composable(Screen.Profile.route) {
            ProfileScreen(navController)
        }
    }
}