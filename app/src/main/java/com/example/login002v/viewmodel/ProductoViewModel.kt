package com.example.login002v.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.login002v.data.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductoViewModel : ViewModel (){
    // uso _ es para decir que clase y variable es privada

    private val _productos = MutableStateFlow<List<Producto>> (emptyList())
    val producto: StateFlow<List<Producto>> =_productos.asStateFlow()

    //metodo
    fun guardarProducto(producto : Producto){
        viewModelScope.launch {
            // guardar en memoria
            val nuevaLista =_productos.value + producto
            _productos.value = nuevaLista  //asignas valor de contenido en nueva lista
        }

    }// fin  fun guardarProducto

}// fin ProductoViewModel