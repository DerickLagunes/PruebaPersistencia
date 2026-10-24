package mx.edu.utez.pruebasharedpreferences.ui.screens

import android.R
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun SPScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val SP = remember {
        context.getSharedPreferences("Juanito", Context.MODE_PRIVATE)
    }

    var textoIngresado by remember {mutableStateOf("")}
    var nombreGuardado by remember {
        mutableStateOf(SP.getString("usuario_key", "No existe") ?: "")
    }

    Column(){
        Text(text = "Valor guardado: $nombreGuardado", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(20.dp))
        OutlinedTextField(textoIngresado,{textoIngresado = it})
        Spacer(Modifier.height(20.dp))
        Row(){
            Button({
                if (textoIngresado.isNotBlank()){
                    SP.edit().putString("usuario_key", textoIngresado).apply()
                    nombreGuardado = textoIngresado
                    textoIngresado = ""
                }
            }){
                Text("Guardar en SP")
            }
            OutlinedTextField("",{})
        }
    }
}