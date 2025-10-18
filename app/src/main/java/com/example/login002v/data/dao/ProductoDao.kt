package com.example.login002v.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.login002v.data.model.Producto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {
    //funciones que se usaran en tabla o producto
    @Insert
    suspend fun insertarProducto(producto: Producto)

    @Query("SELECT * FROM productos")
    fun obtenerProductos(): Flow<List<Producto>>

}