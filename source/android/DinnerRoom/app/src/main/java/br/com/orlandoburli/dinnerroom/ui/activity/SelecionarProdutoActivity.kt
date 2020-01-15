package br.com.orlandoburli.dinnerroom.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.orlandoburli.dinnerroom.R
import br.com.orlandoburli.dinnerroom.model.Produto
import br.com.orlandoburli.dinnerroom.service.ProdutoService
import br.com.orlandoburli.dinnerroom.ui.adapter.ListProdutosAdapter
import kotlinx.android.synthetic.main.layout_selecionar_produto_screen.*

class SelecionarProdutoActivity : AppCompatActivity() {

    lateinit var produtoAdapter: ListProdutosAdapter

    val produtoService: ProdutoService by lazy {
        ProdutoService(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.layout_selecionar_produto_screen)

        setTitle("Seleção de Produtos")

        configuraLista()

        atualizaDados()

        configuraBotaoCancelar()
    }

    private fun configuraBotaoCancelar() {
        layout_selecionar_produto_botao_cancelar.setOnClickListener {
            finish()
        }
    }

    private fun atualizaDados() {
        produtoAdapter.atualiza(this.produtoService.all())
    }

    private fun configuraLista() {
        produtoAdapter = ListProdutosAdapter(this, this::devolveProdutoActivity)

        lista_itens_comanda.adapter = produtoAdapter
    }

    private fun devolveProdutoActivity(produto: Produto) {
        Log.i("produtoActivity", "Devolvendo o produto ${produto.nome}")

        val intent = Intent()
        intent.putExtra("produto_selecionado", produto)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}