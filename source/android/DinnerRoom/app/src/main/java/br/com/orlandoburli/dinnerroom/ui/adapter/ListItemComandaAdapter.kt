package br.com.orlandoburli.dinnerroom.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.orlandoburli.dinnerroom.R
import br.com.orlandoburli.dinnerroom.extensions.formataInteiro
import br.com.orlandoburli.dinnerroom.extensions.formataMoeda
import br.com.orlandoburli.dinnerroom.model.ItemComanda

class ListItemComandaAdapter(private val context: Context) :
    RecyclerView.Adapter<ListItemComandaAdapter.ItemComandaViewHolder>() {

    val itensComanda: MutableList<ItemComanda> = mutableListOf()

    lateinit var onItemClick: (Int, ItemComanda) -> Unit

    fun atualiza(itensComanda: List<ItemComanda>) {
        this.itensComanda.clear()
        this.itensComanda.addAll(itensComanda)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemComandaViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_comanda, parent, false)
        return ItemComandaViewHolder(view)
    }

    override fun getItemCount(): Int = itensComanda.size

    override fun onBindViewHolder(holder: ItemComandaViewHolder, position: Int) {
        holder.bind(this.itensComanda.get(position))
    }

    inner class ItemComandaViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        var itemComanda: ItemComanda? = null

        fun bind(itemComanda: ItemComanda) {
            Log.i("itemComandaAdapter", "Configurar itemComanda ${itemComanda.id}")

            configuraDados(itemComanda)

//            val botaoMesa = itemView.findViewById<Button>(R.id.item_mesa_numero)
//
//            botaoMesa.setOnClickListener {
//                val posicaoItemComanda = adapterPosition
//
//                val itemSelecionado = this@ListItemComandaAdapter.itensComanda[posicaoItemComanda]
//
//                Log.i(
//                    "itemComandaAdapter",
//                    "Clique itemComanda ${itemSelecionado.id} posição $posicaoItemComanda"
//                )
//                this@ListItemComandaAdapter.onItemClick(posicaoItemComanda, itemSelecionado)
//            }
        }

        private fun configuraDados(itemComanda: ItemComanda) {
            this.itemComanda = itemComanda

            Log.i(
                "itemComandaAdapter",
                "Configurando itemComanda Dados ${itemComanda.id}"
            )

            val campoProduto = this.itemView.findViewById<TextView>(R.id.item_comanda_produto)
            val campoQuantidade = itemView.findViewById<TextView>(R.id.item_comanda_quantidade)
            val campoPrecoUnitario =
                itemView.findViewById<TextView>(R.id.item_comanda_preco_unitario)
            val campoPrecoTotal = itemView.findViewById<TextView>(R.id.item_comanda_preco_total)

            campoProduto.text = itemComanda.produto?.nome
            campoQuantidade.text = itemComanda.quantidade.formataInteiro()
            campoPrecoUnitario.text = itemComanda.precoUnitario.formataMoeda()
            campoPrecoTotal.text = itemComanda.precoTotal.formataMoeda()

            Log.i(
                "itemComandaAdapter",
                "Configurado itemComanda Dados ${itemComanda.id}"
            )
        }
    }
}
