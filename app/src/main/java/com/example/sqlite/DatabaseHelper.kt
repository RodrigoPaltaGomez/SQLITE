package com.example.sqlite
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        // Creación de la tabla
        db.execSQL("CREATE TABLE $TABLE_NAME ($COLUMN_RUT TEXT PRIMARY KEY, $COLUMN_NOMBRE TEXT, $COLUMN_CORREO TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Actualiza la base de datos si es necesario
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertarUsuario(usuario: Usuario): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_RUT, usuario.rut)
            put(COLUMN_NOMBRE, usuario.nombre)
            put(COLUMN_CORREO, usuario.correo)
        }

        val resultado = db.insert(TABLE_NAME, null, values)
        db.close()
        return resultado != -1L
    }

    fun obtenerTodosLosUsuarios(): List<Usuario> {
        val usuarios = mutableListOf<Usuario>()
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        if (cursor.moveToFirst()) {
            do {
                val usuario = Usuario(
                    cursor.getString(cursor.getColumnIndex(COLUMN_RUT)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_CORREO))
                )
                usuarios.add(usuario)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return usuarios
    }

    fun actualizarUsuario(usuario: Usuario): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, usuario.nombre)
            put(COLUMN_CORREO, usuario.correo)
        }

        val resultado = db.update(TABLE_NAME, values, "$COLUMN_RUT = ?", arrayOf(usuario.rut))
        db.close()
        return resultado
    }

    fun eliminarUsuario(rut: String): Int {
        val db = this.writableDatabase
        val resultado = db.delete(TABLE_NAME, "$COLUMN_RUT = ?", arrayOf(rut))
        db.close()
        return resultado
    }

    fun buscarUsuario(rut: String): Usuario? {
        val db = this.readableDatabase
        val cursor = db.query(TABLE_NAME, null, "$COLUMN_RUT = ?", arrayOf(rut), null, null, null)

        var usuario: Usuario? = null
        if (cursor.moveToFirst()) {
            usuario = Usuario(
                cursor.getString(cursor.getColumnIndex(COLUMN_RUT)),
                cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE)),
                cursor.getString(cursor.getColumnIndex(COLUMN_CORREO))
            )
        }
        cursor.close()
        db.close()
        return usuario
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "UsuariosDB"
        private const val TABLE_NAME = "usuarios"
        private const val COLUMN_RUT = "rut"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_CORREO = "correo"
    }
}

