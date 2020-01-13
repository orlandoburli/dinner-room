package br.com.orlandoburli.dinnerroom.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.orlandoburli.dinnerroom.R
import br.com.orlandoburli.dinnerroom.model.Mesa
import br.com.orlandoburli.dinnerroom.utils.MESA_INTENT_PARAMETER

class ComandaActivity : AppCompatActivity() {

    lateinit var mesa: Mesa

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.layout_comanda_screen)

        if (intent.hasExtra(MESA_INTENT_PARAMETER)) {
            this.mesa = intent.getSerializableExtra(MESA_INTENT_PARAMETER) as Mesa
        } else {
            finish()
        }
    }
}