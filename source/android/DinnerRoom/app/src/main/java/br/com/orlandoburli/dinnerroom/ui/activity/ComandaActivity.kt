package br.com.orlandoburli.dinnerroom.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.orlandoburli.dinnerroom.R
import br.com.orlandoburli.dinnerroom.database.AppDatabase
import br.com.orlandoburli.dinnerroom.database.DbHelper
import br.com.orlandoburli.dinnerroom.extensions.formataMoeda
import br.com.orlandoburli.dinnerroom.extensions.total
import br.com.orlandoburli.dinnerroom.model.Comanda
import br.com.orlandoburli.dinnerroom.model.ItemComanda
import br.com.orlandoburli.dinnerroom.model.Mesa
import br.com.orlandoburli.dinnerroom.model.Produto
import br.com.orlandoburli.dinnerroom.service.ComandaService
import br.com.orlandoburli.dinnerroom.ui.adapter.ListItemComandaAdapter
import br.com.orlandoburli.dinnerroom.utils.MESA_INTENT_PARAMETER
import kotlinx.android.synthetic.main.layout_comanda_screen.*

class ComandaActivity : AppCompatActivity() {

    private lateinit var comanda: Comanda

    lateinit var mesa: Mesa

    val db: AppDatabase by lazy {
        DbHelper.db(this)
    }

    val comandaService: ComandaService by lazy {
        ComandaService(db)
    }

    val itensComandaAdapter: ListItemComandaAdapter by lazy {
        ListItemComandaAdapter(this)
    }

    private val REQUEST_PRODUTO = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.layout_comanda_screen)

        if (intent.hasExtra(MESA_INTENT_PARAMETER)) {
            this.mesa = intent.getSerializableExtra(MESA_INTENT_PARAMETER) as Mesa

            this.comanda = comandaService.getByMesa(mesa)

            title = "Mesa ${mesa.numero} Comanda ${comanda.id}"

            configuraAdapterItensComanda()

            configuraBotoes()
        } else {
            finish()
        }
    }

    private fun adicionarItem() {
        startActivityForResult(Intent(this, SelecionarProdutoActivity::class.java), REQUEST_PRODUTO)
    }

    private fun fecharComanda() {
        Toast.makeText(this, "Fechando comanda...", Toast.LENGTH_SHORT).show()
    }

    private fun configuraBotoes() {
        configuraFecharComanda()

        configuraAdicionarItem()
    }

    private fun configuraAdicionarItem() {
        layout_comanda_screen_botao_adicionar_item.setOnClickListener {
            adicionarItem()
        }
    }

    private fun configuraFecharComanda() {
        layout_comanda_screen_botao_fechar_comanda.setOnClickListener {
            fecharComanda()
        }
    }

    override fun onResume() {
        super.onResume()

        atualizaDados()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        
        if (requestCode == REQUEST_PRODUTO && resultCode == Activity.RESULT_OK && data?.hasExtra("produto_selecionado") == true) {
            val produto = data.getSerializableExtra("produto_selecionado") as Produto

            comandaService.adicionaItem(comanda, produto, 1)

            atualizaDados()
        }
    }

    private fun configuraAdapterItensComanda() {
        lista_itens_comanda.adapter = itensComandaAdapter

        itensComandaAdapter.onItemClick = { posicao: Int, itemComanda: ItemComanda ->
            Toast.makeText(this, "Clicou no item", Toast.LENGTH_SHORT).show()
        }
    }

    private fun atualizaDados() {
        val itensComanda = comandaService.itensComanda(this.comanda.id)

        itensComandaAdapter.atualiza(itensComanda)

        layout_comanda_screen_total.text = itensComanda.total().formataMoeda()
    }
}