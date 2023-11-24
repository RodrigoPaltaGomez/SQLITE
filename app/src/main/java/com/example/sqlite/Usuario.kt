package com.example.sqlite
class Usuario(val rut: String, val nombre: String, val correo: String) {
    companion object {
        const val CREATE_TABLE = """
            CREATE TABLE usuarios (
            rut TEXT PRIMARY KEY,
            nombre TEXT,
            correo TEXT)
        """
    }
}
