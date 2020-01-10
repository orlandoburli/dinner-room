package br.com.orlandoburli.dinnerroom.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.orlandoburli.dinnerroom.R
import br.com.orlandoburli.dinnerroom.model.Garcom
import br.com.orlandoburli.dinnerroom.utils.GARCOM_INTENT_PARAMETER

class TelaPrincipalActivity() : AppCompatActivity() {

    lateinit var garcom: Garcom

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
}