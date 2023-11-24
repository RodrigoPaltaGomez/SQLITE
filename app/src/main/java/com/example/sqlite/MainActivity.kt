package com.example.sqlite
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var editTextRut: EditText
    private lateinit var editTextNombre: EditText
    private lateinit var editTextCorreo: EditText
    private lateinit var btnGuardar: Button
    private lateinit var btnListar: Button
    private lateinit var btnBuscar: Button
    private lateinit var btnActualizar: Button
    private lateinit var btnEliminar: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextRut = findViewById(R.id.editTextRut)
        editTextNombre = findViewById(R.id.editTextNombre)
        editTextCorreo = findViewById(R.id.editTextCorreo)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnListar = findViewById(R.id.btnListar)
        btnBuscar = findViewById(R.id.btnBuscar)
        btnActualizar = findViewById(R.id.btnActualizar)
        btnEliminar = findViewById(R.id.btnEliminar)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        databaseHelper = DatabaseHelper(this)

        btnGuardar.setOnClickListener { guardarUsuario() }
        btnListar.setOnClickListener { listarUsuarios() }
        btnBuscar.setOnClickListener { buscarUsuario() }
        btnActualizar.setOnClickListener { actualizarUsuario() }
        btnEliminar.setOnClickListener { eliminarUsuario() }
    }

    private fun guardarUsuario() {
        val rut = editTextRut.text.toString()
        val nombre = editTextNombre.text.toString()
        val correo = editTextCorreo.text.toString()
        val usuario = Usuario(rut, nombre, correo)

        if (databaseHelper.insertarUsuario(usuario)) {
            Toast.makeText(this, "Usuario guardado", Toast.LENGTH_SHORT).show()
            limpiarCampos()
        } else {
            Toast.makeText(this, "Error al guardar usuario", Toast.LENGTH_SHORT).show()
        }
    }

    private fun listarUsuarios() {
        val usuarios = databaseHelper.obtenerTodosLosUsuarios()
        val adapter = UsuarioAdapter(usuarios)
        recyclerView.adapter = adapter
    }

    private fun buscarUsuario() {
        val rut = editTextRut.text.toString()
        val usuario = databaseHelper.buscarUsuario(rut)

        if (usuario != null) {
            editTextNombre.setText(usuario.nombre)
            editTextCorreo.setText(usuario.correo)
        } else {
            Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun actualizarUsuario() {
        val rut = editTextRut.text.toString()
        val nombre = editTextNombre.text.toString()
        val correo = editTextCorreo.text.toString()
        val usuario = Usuario(rut, nombre, correo)

        if (databaseHelper.actualizarUsuario(usuario) > 0) {
            Toast.makeText(this, "Usuario actualizado", Toast.LENGTH_SHORT).show()
            limpiarCampos()
        } else {
            Toast.makeText(this, "Error al actualizar usuario", Toast.LENGTH_SHORT).show()
        }
    }

    private fun eliminarUsuario() {
        val rut = editTextRut.text.toString()

        if (databaseHelper.eliminarUsuario(rut) > 0) {
            Toast.makeText(this, "Usuario eliminado", Toast.LENGTH_SHORT).show()
            limpiarCampos()
        } else {
            Toast.makeText(this, "Error al eliminar usuario", Toast.LENGTH_SHORT).show()
        }
    }

    private fun limpiarCampos() {
        editTextRut.setText("")
        editTextNombre.setText("")
        editTextCorreo.setText("")
    }
}
