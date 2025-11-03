package com.example.huertohogardefinitiveedition.data.database

import androidx.compose.runtime.mutableStateListOf
import com.example.huertohogardefinitiveedition.data.model.Producto

object PedidoHistorial {
    val pedidos = mutableStateListOf<Producto>()

    fun agregar(pedido: Producto) {
        pedidos.add(0, pedido) // agrega arriba para ver el último primero
    }

    fun limpiar() {
        pedidos.clear()
    }
}

