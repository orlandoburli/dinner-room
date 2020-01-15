package br.com.orlandoburli.dinnerroom.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.orlandoburli.dinnerroom.R
import br.com.orlandoburli.dinnerroom.model.Garcom
import br.com.orlandoburli.dinnerroom.model.Mesa
import br.com.orlandoburli.dinnerroom.service.MesaService
import br.com.orlandoburli.dinnerroom.ui.adapter.ListMesaAdapter
import br.com.orlandoburli.dinnerroom.utils.GARCOM_INTENT_PARAMETER
import br.com.orlandoburli.dinnerroom.utils.MESA_INTENT_PARAMETER
import kotlinx.android.synthetic.main.layout_principal_screen.*

class TelaPrincipalActivity() : AppCompatActivity() {

    lateinit var garcom: Garcom

    private val mesaService: MesaService by lazy {
        MesaService(this)
    }

    private val mesas: List<Mesa> by lazy {
        mesaService.all()
    }

    private val listMesaAdapter: ListMesaAdapter by lazy {
        ListMesaAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_principal_screen)

        if (intent.hasExtra(GARCOM_INTENT_PARAMETER)) {
            garcom = intent.getSerializableExtra(GARCOM_INTENT_PARAMETER) as Garcom

            title = "Dinner Room - ${garcom.nome}"

            configuraLista()
        } else {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()

        atualizaDados()
    }

    private fun configuraLista() {
        lista_mesas.adapter = this.listMesaAdapter

        this.listMesaAdapter.onItemClick = { posicao: Int, mesa: Mesa ->
            val intent = Intent(this, ComandaActivity::class.java)
            intent.putExtra(MESA_INTENT_PARAMETER, mesa)
            startActivity(intent)
        }
    }

    private fun atualizaDados() {
        this.listMesaAdapter.atualiza(mesas)
    }
}