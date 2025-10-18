package com.example.login002v.data.model

data class Credential(val username:String, val password:String){
    // objeto que permite accedr a la instancia de la clase
    companion object{
       //estas son las credenciales para login 
        val Admin =Credential(username="admin", password="123")
    }

}