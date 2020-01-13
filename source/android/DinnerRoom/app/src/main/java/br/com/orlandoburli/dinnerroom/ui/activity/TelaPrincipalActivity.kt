package br.com.orlandoburli.dinnerroom.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import br.com.orlandoburli.dinnerroom.R
import br.com.orlandoburli.dinnerroom.model.Garcom
import br.com.orlandoburli.dinnerroom.model.Mesa
import br.com.orlandoburli.dinnerroom.service.MesaService
import br.com.orlandoburli.dinnerroom.ui.adapter.ListMesaAdapter
import br.com.orlandoburli.dinnerroom.utils.GARCOM_INTENT_PARAMETER
import kotlinx.android.synthetic.main.layout_principal_screen.*

class TelaPrincipalActivity() : AppCompatActivity() {

    lateinit var garcom: Garcom

    val mesaService: MesaService by lazy {
        MesaService(this)
    }

    val mesas: List<Mesa> by lazy {
        mesaService.all()
    }

    val listMesaAdapter: ListMesaAdapter by lazy {
        Log.i("mesaAdapter", "Inicializando Adapter")
        ListMesaAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_principal_screen)

        if (intent.hasExtra(GARCOM_INTENT_PARAMETER)) {
            garcom = intent.getSerializableExtra(GARCOM_INTENT_PARAMETER) as Garcom

            setTitle("Dinner Room - ${garcom.nome}")

        } else {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()

        configuraLista()

        atualizaDados()
    }

    private fun configuraLista() {
        Log.i("mesaAdapter", "Configurando Lista")
        lista_mesas.adapter = this.listMesaAdapter
    }

    private fun atualizaDados() {
        this.listMesaAdapter.atualiza(mesas)
    }
}