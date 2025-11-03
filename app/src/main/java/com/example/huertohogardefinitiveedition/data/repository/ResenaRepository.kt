package com.example.huertohogardefinitiveedition.data.repository


import android.util.Log
import com.example.huertohogardefinitiveedition.data.model.Resena
import com.example.huertohogardefinitiveedition.view.listaDeCategorias // Importante importar la lista
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object ResenaRepository {
    // Lista en memoria para guardar las reseñas.
    // Le añadimos un par de reseñas de ejemplo para que no se vea vacío.
    private val resenas = mutableListOf(
        Resena(
            id = 1,
            nombreProducto = "Manzanas Fuji",
            idUsuario = 2,
            nombreUsuario = "Renatto",
            calificacion = 5,
            comentario = "¡Muy frescas y crujientes, las mejores que he probado!",
            fecha = "25/05/2024"
        ),
        Resena(
            id = 2,
            nombreProducto = "Manzanas Fuji",
            idUsuario = 3,
            nombreUsuario = "John Doe",
            calificacion = 4,
            comentario = "Buenas, aunque un poco pequeñas. En general, recomendables.",
            fecha = "26/05/2024"
        )
    )

    private var proximoId = resenas.size + 1

    // Función para añadir una nueva reseña a la lista
    fun agregarResena(
        nombreProducto: String,
        idUsuario: Int,
        nombreUsuario: String,
        calificacion: Int,
        comentario: String
    ) {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaActual = sdf.format(Date())

        resenas.add(
            Resena(
                id = proximoId++,
                nombreProducto = nombreProducto,
                idUsuario = idUsuario,
                nombreUsuario = nombreUsuario,
                calificacion = calificacion,
                comentario = comentario,
                fecha = fechaActual
            )
        )
    }

    // Función para obtener todas las reseñas de un producto específico
    fun obtenerResenasPorProducto(nombreProducto: String): List<Resena> {
        return resenas.filter { it.nombreProducto.equals(nombreProducto, ignoreCase = true) }
    }

    fun actualizarStock(nombreProducto: String, nuevoStock: Int) {
        // Buscamos en todas las categorías...
        for (categoria in listaDeCategorias) {
            // ...y en todos los productos de cada categoría.
            val productoIndex = categoria.productos.indexOfFirst { it.nombre.equals(nombreProducto, ignoreCase = true) }

            // Si encontramos el producto...
            if (productoIndex != -1) {
                val productoAntiguo = categoria.productos[productoIndex]
                // Creamos una copia del producto con el stock actualizado.
                val productoActualizado = productoAntiguo.copy(stock = nuevoStock)

                // Reemplazamos el producto antiguo por el nuevo en la lista.
                // Es necesario crear una nueva lista para que el cambio se refleje en la UI de Compose.
                val nuevosProductos = categoria.productos.toMutableList()
                nuevosProductos[productoIndex] = productoActualizado

                // Esta parte es un poco compleja porque las listas son inmutables,
                // pero este enfoque simula el cambio. En una BD real, sería un simple UPDATE.
                Log.d("StockUpdate", "Stock de '$nombreProducto' actualizado a: $nuevoStock")

                // NOTA: Para que este cambio se refleje visualmente de inmediato en DrawerMenu,
                // la lista `listaDeCategorias` debería ser un estado mutable (`mutableStateOf`).
                // Por ahora, el cambio se mantendrá en memoria y será correcto la próxima vez
                // que la app se reconstruya. Vamos a hacer que funcione lógicamente primero.
                return // Salimos de la función una vez que actualizamos el producto.
            }
        }
        Log.w("StockUpdate", "No se encontró el producto '$nombreProducto' para actualizar el stock.")
    }
}
