package mx.edu.utez.pruebasharedpreferences

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mx.edu.utez.pruebasharedpreferences.ui.screens.PruebaRoomScreen
import mx.edu.utez.pruebasharedpreferences.ui.screens.SPScreen
import mx.edu.utez.pruebasharedpreferences.ui.theme.PruebaSharedPreferencesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PruebaSharedPreferencesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var currentScreen by remember { mutableStateOf("menu") }

                    Box(modifier = Modifier.padding(innerPadding)) {
                        when (currentScreen) {
                            "menu" -> MainMenuScreen(
                                onNavigateToSP = { currentScreen = "sp" },
                                onNavigateToRoom = { currentScreen = "room" }
                            )
                            "sp" -> SPScreen(
                                onBack = { currentScreen = "menu" }
                            )
                            "room" -> PruebaRoomScreen(
                                onBack = { currentScreen = "menu" }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainMenuScreen(
    onNavigateToSP: () -> Unit,
    onNavigateToRoom: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Práctica: Almacenamiento",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onNavigateToSP,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ir a SharedPreferences")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onNavigateToRoom,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ir a Room (Base de Datos)")
        }
    }
}
