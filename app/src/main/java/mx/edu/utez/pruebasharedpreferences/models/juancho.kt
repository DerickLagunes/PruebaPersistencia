package mx.edu.utez.pruebasharedpreferences.models

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

// 1. ENTIDAD (Representa la tabla en la BD)
@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey val id: Int = 1, // Usamos ID fijo 1 para sobrescribir siempre el mismo usuario
    val nombre: String
)

// 2. DAO (Data Access Object - Las consultas SQL)
@Dao
interface UsuarioDao {
    @Query("SELECT * FROM usuarios WHERE id = 1")
    fun getUsuario(): Flow<Usuario?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsuario(usuario: Usuario)

    @Query("DELETE FROM usuarios WHERE id = 1")
    suspend fun deleteUsuario()
}

// 3. DATABASE (La base de datos en sí)
@Database(entities = [Usuario::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mi_base_de_datos"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}