package mx.edu.utez.pruebasharedpreferences.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import mx.edu.utez.pruebasharedpreferences.models.AppDatabase
import mx.edu.utez.pruebasharedpreferences.models.Usuario

@Composable
fun PruebaRoomScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val db = remember { AppDatabase.getDatabase(context) }
    val usuarioDao = db.usuarioDao()

    val usuarios by usuarioDao.getUsuarios().collectAsState(initial = emptyList())
    var textoIngresado by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
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
            text = "Ejemplo Room (Lista)",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = textoIngresado,
            onValueChange = { textoIngresado = it },
            label = { Text("Escribe un nombre") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = {
                    if (textoIngresado.isNotBlank()) {
                        coroutineScope.launch {
                            usuarioDao.insertUsuario(Usuario(nombre = textoIngresado))
                            textoIngresado = ""
                        }
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Guardar")
            }

            OutlinedButton(
                onClick = {
                    coroutineScope.launch {
                        usuarioDao.deleteAllUsuarios()
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Borrar Todo")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Registros en la BD (${usuarios.size}):",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(usuarios) { usuario ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Text(
                        text = "• ${usuario.nombre}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}
