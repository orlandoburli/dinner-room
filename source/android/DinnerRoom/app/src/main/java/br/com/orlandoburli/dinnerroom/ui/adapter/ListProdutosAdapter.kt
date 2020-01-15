package br.com.orlandoburli.dinnerroom.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.orlandoburli.dinnerroom.R
import br.com.orlandoburli.dinnerroom.extensions.formataMoeda
import br.com.orlandoburli.dinnerroom.model.Produto

class ListProdutosAdapter(
    private val context: Context,
    val onProdutoSelecionado: (Produto) -> Unit
) :
    RecyclerView.Adapter<ListProdutosAdapter.ProdutoViewHolder>() {

    val produtos: MutableList<Produto> = mutableListOf()

    fun atualiza(produtos: List<Produto>) {
        this.produtos.clear()
        this.produtos.addAll(produtos)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_produto, parent, false)

        return ProdutoViewHolder(view)
    }

    override fun getItemCount(): Int = produtos.size

    override fun onBindViewHolder(holder: ProdutoViewHolder, position: Int) {
        holder.bind(this.produtos.get(position))
    }

    inner class ProdutoViewHolder : RecyclerView.ViewHolder {
        constructor(itemView: View) : super(itemView) {

            Log.i("produtoAdapter", "Configurando click listener ${itemView.isClickable}")

            val card = itemView.findViewById<View>(R.id.item_selecao_produto_card)

            card.setOnClickListener {
                val position = adapterPosition
                val produto = produtos[position]

                Log.i("produtoAdapter", "Clicou no produto ${produto.nome} na posição $position")

                onProdutoSelecionado(produto)
            }
        }

        var produto: Produto? = null

        fun bind(produto: Produto) = configuraDados(produto)

        private fun configuraDados(produto: Produto) {
            this.produto = produto

            val campoDescricaoProduto =
                this.itemView.findViewById<TextView>(R.id.item_selecao_produto_descricao)
            val campoProdutoPrecoUnitario =
                itemView.findViewById<TextView>(R.id.item_selecao_produto_preco)

            campoDescricaoProduto.text = this.produto?.nome
            campoProdutoPrecoUnitario.text = this.produto?.preco?.formataMoeda()
        }
    }
}
