package br.com.orlandoburli.dinnerroom.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.orlandoburli.dinnerroom.R
import br.com.orlandoburli.dinnerroom.model.Mesa

class ListMesaAdapter(val context: Context) : RecyclerView.Adapter<ListMesaAdapter.MyViewHolder>() {

    val mesas: MutableList<Mesa> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_mesa, parent, false)

        Log.i("mesaAdapter", "Criando View Holder")
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.i("mesaAdapter", "Bind View Holder in position ${position}")
        holder.bind(this.mesas.get(position))
    }

    override fun getItemCount(): Int {
        Log.i("mesaAdapter", "Mesas: ${this.mesas.size}")
        return this.mesas.size
    }

    fun atualiza(mesas: List<Mesa>) {
        this.mesas.clear()
        this.mesas.addAll(mesas)

        Log.i("mesaAdapter", "Mesas atualizadas: ${this.mesas.size}")

        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mesa: Mesa? = null

        fun bind(mesa: Mesa) {
            this.mesa = mesa

            val campoMesaNumero = this.itemView.findViewById<TextView>(R.id.item_mesa_numero)

            campoMesaNumero.text = mesa.numero
        }
    }
}
