package com.example.login002v.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.login002v.data.dao.ProductoDao
import com.example.login002v.data.model.Producto

@Database(
    entities = [Producto::class],
    version=1,
    exportSchema = false // evite warning
)

abstract class ProductoDatabase: RoomDatabase(){

    abstract fun productoDao(): ProductoDao

    companion object {
        @Volatile //cualquier cambio en el valor de la  INSTANCE  va a ser visible
        private var INSTANCE: ProductoDatabase ? = null

        fun getDatabase(context: Context):ProductoDatabase {
            return INSTANCE ?: synchronized(this){
                var instance= Room.databaseBuilder(
                    context.applicationContext,
                    ProductoDatabase::class.java,
                    name = "producto_database"
                ).build()
                INSTANCE = instance
                instance

            }// fin return
        }// fin  fun getDatabase

    }// fin  companion object


}// fin abstract class ProductoDatabase