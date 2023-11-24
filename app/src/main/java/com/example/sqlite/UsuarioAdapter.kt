package com.example.sqlite
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UsuarioAdapter(private val usuarios: List<Usuario>) : RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder>() {

    class UsuarioViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvRut: TextView = view.findViewById(R.id.tvRut)
        val tvNombre: TextView = view.findViewById(R.id.tvNombre)
        val tvCorreo: TextView = view.findViewById(R.id.tvCorreo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_usuario, parent, false)
        return UsuarioViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val usuario = usuarios[position]
        holder.tvRut.text = usuario.rut
        holder.tvNombre.text = usuario.nombre
        holder.tvCorreo.text = usuario.correo
    }

    override fun getItemCount() = usuarios.size
}

