package br.com.orlandoburli.dinnerroom.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.orlandoburli.dinnerroom.R
import br.com.orlandoburli.dinnerroom.model.Mesa

class ListMesaAdapter(private val context: Context) :
    RecyclerView.Adapter<ListMesaAdapter.MesaViewHolder>() {

    val mesas: MutableList<Mesa> = mutableListOf()

    lateinit var onItemClick: (Int, Mesa) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MesaViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_mesa, parent, false)
        return MesaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MesaViewHolder, position: Int) {
        holder.bind(this.mesas.get(position))
    }

    override fun getItemCount(): Int = this.mesas.size

    fun atualiza(mesas: List<Mesa>) {
        this.mesas.clear()
        this.mesas.addAll(mesas)

        notifyDataSetChanged()
    }

    inner class MesaViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        var mesa: Mesa? = null

        fun bind(mesa: Mesa) {
            configuraDados(mesa)

            Log.i("mesaAdapter", "Configurando mesa ${mesa.numero}")

            val botaoMesa = itemView.findViewById<Button>(R.id.item_mesa_numero)

            botaoMesa.setOnClickListener {
                val posicaoMesa = adapterPosition

                val mesaSelecionada = this@ListMesaAdapter.mesas[posicaoMesa]

                Log.i(
                    "mesaAdapter",
                    "Clique mesa ${mesaSelecionada.numero} posição $posicaoMesa"
                )
                this@ListMesaAdapter.onItemClick(posicaoMesa, mesaSelecionada)
            }
        }

        private fun configuraDados(mesa: Mesa) {
            this.mesa = mesa

            val campoMesaNumero = this.itemView.findViewById<TextView>(R.id.item_mesa_numero)

            campoMesaNumero.text = mesa.numero
        }
    }
}
