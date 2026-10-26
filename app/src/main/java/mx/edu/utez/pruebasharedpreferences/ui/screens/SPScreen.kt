package mx.edu.utez.pruebasharedpreferences.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun SPScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val sp = remember {
        context.getSharedPreferences("Juanito", Context.MODE_PRIVATE)
    }

    var textoIngresado by remember { mutableStateOf("") }
    var nombreGuardado by remember {
        mutableStateOf(sp.getString("usuario_key", "No existe") ?: "")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            OutlinedButton(onClick = onBack) {
                Text("← Volver al Menú")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Ejemplo SharedPreferences",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Valor guardado: $nombreGuardado",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = textoIngresado,
            onValueChange = { textoIngresado = it },
            label = { Text("Escribe un valor") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (textoIngresado.isNotBlank()) {
                    sp.edit().putString("usuario_key", textoIngresado).apply()
                    nombreGuardado = textoIngresado
                    textoIngresado = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar en SharedPreferences")
        }
    }
}
