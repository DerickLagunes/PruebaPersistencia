package mx.edu.utez.pruebasharedpreferences

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import mx.edu.utez.pruebasharedpreferences.ui.screens.PruebaRoomScreen
import mx.edu.utez.pruebasharedpreferences.ui.screens.SPScreen
import mx.edu.utez.pruebasharedpreferences.ui.theme.PruebaSharedPreferencesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PruebaSharedPreferencesTheme() {
                PruebaRoomScreen()
            }
                }
            }
        }