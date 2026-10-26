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

// 1. ENTIDAD (Representa la tabla en la BD con ID autoincrementable)
@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val nombre: String
)

// 2. DAO (Data Access Object - Consultas SQL)
@Dao
interface UsuarioDao {
    @Query("SELECT * FROM usuarios")
    fun getUsuarios(): Flow<List<Usuario>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsuario(usuario: Usuario)

    @Query("DELETE FROM usuarios")
    suspend fun deleteAllUsuarios()
}

// 3. DATABASE
@Database(entities = [Usuario::class], version = 2, exportSchema = false)
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
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
