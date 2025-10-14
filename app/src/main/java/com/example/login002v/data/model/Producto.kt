package com.example.login002v.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Productos")
data class Producto(
    @PrimaryKey(autoGenerate = true)
    val  id: Int=0,
    val noombre:String,
    val precio:String,
    val cantidad:String,
    val conPapas:Boolean,
    val agreandarBebida: Boolean


)

