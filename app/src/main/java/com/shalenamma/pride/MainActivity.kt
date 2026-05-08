// Shale-Namma Pride - Main Activity
package com.shalenamma.pride

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.core.spring
import com.shalenamma.pride.ui.screens.SplashScreen
import com.shalenamma.pride.ui.screens.RoleSelectScreen
import com.shalenamma.pride.ui.screens.LoginScreen
import com.shalenamma.pride.ui.screens.HomeScreen
import com.shalenamma.pride.ui.screens.MealsScreen
import com.shalenamma.pride.ui.screens.GalleryScreen
import com.shalenamma.pride.ui.screens.AchievementsScreen
import com.shalenamma.pride.ui.screens.ProfileScreen
import com.shalenamma.pride.ui.screens.FeedbackScreen
import com.shalenamma.pride.ui.screens.NotificationsScreen
import com.shalenamma.pride.ui.screens.AdminDashboardScreen
import com.shalenamma.pride.ui.screens.FeedbackListScreen
import com.shalenamma.pride.ui.screens.GalleryDetailScreen
import com.shalenamma.pride.ui.theme.ShaleNammaPrideTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun attachBaseContext(newBase: Context) {
        val prefs = newBase.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val language = prefs.getString("language", "en") ?: "en"
        val locale = if (language == "kn") java.util.Locale("kn", "IN") else java.util.Locale("en")
        java.util.Locale.setDefault(locale)
        val config = Configuration(newBase.resources.configuration)
        config.setLocale(locale)
        val wrappedContext = newBase.createConfigurationContext(config)
        super.attachBaseContext(wrappedContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShaleNammaPrideTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ShaleNammaNavGraph()
                }
            }
        }
    }
}

@Composable
fun ShaleNammaNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash",
        modifier = Modifier.fillMaxSize(),
        enterTransition = { slideInHorizontally(animationSpec = spring()) { it } },
        exitTransition = { slideOutHorizontally(animationSpec = spring()) { -it } },
        popEnterTransition = { slideInHorizontally(animationSpec = spring()) { -it } },
        popExitTransition = { slideOutHorizontally(animationSpec = spring()) { it } }
    ) {
        composable("splash") {
            SplashScreen(navController = navController)
        }
        composable("role_select") {
            RoleSelectScreen(navController = navController)
        }
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable("meals") {
            MealsScreen(navController = navController)
        }
        composable("gallery") {
            GalleryScreen(navController = navController)
        }
        composable("image_detail/{imageIndex}") { backStackEntry ->
            val imageIndex = backStackEntry.arguments?.getString("imageIndex")?.toIntOrNull() ?: 0
            GalleryDetailScreen(navController = navController, imageIndex = imageIndex)
        }
        composable("achievements") {
            AchievementsScreen(navController = navController)
        }
        composable("profile") {
            ProfileScreen(navController = navController)
        }
        composable("feedback") {
            FeedbackScreen(navController = navController)
        }
        composable("notifications") {
            NotificationsScreen(navController = navController)
        }
        composable("feedback_list") {
            FeedbackListScreen(navController = navController)
        }
        composable("admin_dashboard") {
            AdminDashboardScreen(navController = navController)
        }
    }
}
