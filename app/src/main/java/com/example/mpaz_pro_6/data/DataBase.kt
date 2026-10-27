package com.example.mpaz_pro_6.data

import android.content.Context
import androidx.room.*

@Entity(tableName = "usuarios_ficticios")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val iniciales: String,
    val nombre: String,
    val descripcion: String,
    val rol: String,
    val usuario: String,
    val clave: String
)

@Dao
interface UsuarioDao {
    @Query("SELECT * FROM usuarios_ficticios")
    suspend fun obtenerTodosLosUsuarios(): List<UsuarioEntity>

    @Query("SELECT * FROM usuarios_ficticios WHERE usuario = :usuario AND clave = :clave LIMIT 1")
    suspend fun validarLogin(usuario: String, clave: String): UsuarioEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarUsuarios(usuarios: List<UsuarioEntity>)
}

@Database(entities = [UsuarioEntity::class], version = 2, exportSchema = false)
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
                    "mpaz_database"
                )
                    // Cambió el esquema (usuario y clave): se recrea la base de prueba.
                    .fallbackToDestructiveMigration(true)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}