package mx.edu.utez.pruebasharedpreferences.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import mx.edu.utez.pruebasharedpreferences.models.AppDatabase
import mx.edu.utez.pruebasharedpreferences.models.Usuario

@Composable
fun PruebaRoomScreen() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Instancia de la BD y DAO
    val db = remember { AppDatabase.getDatabase(context) }
    val usuarioDao = db.usuarioDao()

    // Escuchamos los cambios en Room de forma reactiva mediante Flow
    val usuarioState by usuarioDao.getUsuario().collectAsState(initial = null)

    var textoIngresado by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Muestra el nombre guardado en Room
        Text(
            text = "Valor en Room: ${usuarioState?.nombre ?: "Ningún nombre guardado"}",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = textoIngresado,
            onValueChange = { textoIngresado = it },
            label = { Text("Escribe tu nombre") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            // Guardar en Room (Operación asíncrona mediante Coroutines)
            Button(onClick = {
                if (textoIngresado.isNotBlank()) {
                    coroutineScope.launch {
                        usuarioDao.insertUsuario(Usuario(nombre = textoIngresado))
                        textoIngresado = ""
                    }
                }
            }) {
                Text("Guardar")
            }

            // Borrar de Room
            OutlinedButton(onClick = {
                coroutineScope.launch {
                    usuarioDao.deleteUsuario()
                }
            }) {
                Text("Borrar")
            }
        }
    }
}