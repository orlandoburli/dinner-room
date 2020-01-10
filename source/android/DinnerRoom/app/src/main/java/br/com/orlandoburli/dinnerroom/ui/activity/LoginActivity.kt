package br.com.orlandoburli.dinnerroom.ui.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import br.com.orlandoburli.dinnerroom.R
import br.com.orlandoburli.dinnerroom.dao.GarcomDao
import br.com.orlandoburli.dinnerroom.database.AppDatabase
import br.com.orlandoburli.dinnerroom.database.DbHelper
import br.com.orlandoburli.dinnerroom.exception.garcom.UsuarioSenhaInvalidosException
import br.com.orlandoburli.dinnerroom.model.Garcom
import br.com.orlandoburli.dinnerroom.service.GarcomService
import br.com.orlandoburli.dinnerroom.utils.GARCOM_INTENT_PARAMETER
import kotlinx.android.synthetic.main.layout_login_screen.*

class LoginActivity : Activity() {

    var contadorCliquesLogo = 0

    val database: AppDatabase by lazy {
        DbHelper.db(this)
    }

    val garcomDao: GarcomDao by lazy {
        database.garcomDao()
    }

    val garcomService: GarcomService by lazy {
        GarcomService(this, garcomDao)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_login_screen)

        inicializaAcoes()
    }

    private fun inicializaAcoes() {
        layout_login_screen_botao_entrar.setOnClickListener {
            entrar()
        }

        Log.i("info", "Clickable ${layout_login_screen_logo.isClickable().toString()}")

        layout_login_screen_logo.setOnClickListener {
            zerarDados()
        }
    }

    private fun entrar() {
        val loginGarcom = layout_login_screen_login.text.toString()
        val senhaGarcom = layout_login_screen_password.text.toString()

        try {
            abreTelaPrincipal(
                garcomService.login(loginGarcom, senhaGarcom)
            )

        } catch (e: UsuarioSenhaInvalidosException) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun abreTelaPrincipal(garcom: Garcom) {
        val intent = Intent(this, TelaPrincipalActivity::class.java)
        intent.putExtra(GARCOM_INTENT_PARAMETER, garcom)
        startActivity(intent)
    }

    private fun zerarDados() {
        contadorCliquesLogo++

        if (contadorCliquesLogo >= 5) {
            configuraDialogConfirmacaoZeraDados { _, _ ->
                garcomService.sample()
            }

            contadorCliquesLogo = 0
        }
    }

    private fun configuraDialogConfirmacaoZeraDados(delegate: (Any, Int) -> Unit) {
        val builder = AlertDialog.Builder(this@LoginActivity)
        builder.setTitle("Confirmação")
        builder.setMessage("Deseja ZERAR os dados do aplicativo? Esta operação não será desfeita!")
        builder.setPositiveButton("Sim", delegate)
        builder.setNegativeButton("Não", null)
        builder.show()
    }
}