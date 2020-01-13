package br.com.orlandoburli.dinnerroom.ui.activity

import android.content.Intent
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import br.com.orlandoburli.dinnerroom.BuildConfig
import br.com.orlandoburli.dinnerroom.R
import br.com.orlandoburli.dinnerroom.dao.GarcomDao
import br.com.orlandoburli.dinnerroom.dao.MesaDao
import br.com.orlandoburli.dinnerroom.database.DbHelper
import br.com.orlandoburli.dinnerroom.model.Garcom
import br.com.orlandoburli.dinnerroom.model.Mesa
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TelaPrincipalActivityTest {

    private val MAXIMO_MESAS = 12

    @Rule
    @JvmField
    val loginTestRule = ActivityTestRule(LoginActivity::class.java, true, false)

    @Before
    fun setup() {
        limpaDados()

        prepararDados()

        loginTestRule.launchActivity(Intent())

        entrar("jose", "123456")
    }

    @Test
    fun deve_ApresentarListaDeMesas_QuandoLogar() {
        for (i in 1..MAXIMO_MESAS) {
            val numero = i.toString().padStart(3, '0')

            onView(withText(numero))
                .check(matches(isDisplayed()))
        }
    }

    private fun entrar(login: String, senha: String) {
        preencheUsuarioSenha(login, senha)
        clicarBotaoEntrar()
    }

    private fun preencheUsuarioSenha(login: String, senha: String) {

        preencherLogin(login)

        preencherSenha(senha)
    }

    private fun preencherLogin(login: String) {
        onView(ViewMatchers.withId(R.id.layout_login_screen_login))
            .check(matches(isDisplayed()))
            .perform(ViewActions.click())
            .perform(ViewActions.typeText(login), ViewActions.closeSoftKeyboard())
    }

    private fun preencherSenha(senha: String) {
        onView(ViewMatchers.withId(R.id.layout_login_screen_password))
            .check(matches(isDisplayed()))
            .perform(ViewActions.click())
            .perform(ViewActions.typeText(senha), ViewActions.closeSoftKeyboard())
    }

    private fun clicarBotaoEntrar() {
        onView(ViewMatchers.withId(R.id.layout_login_screen_botao_entrar))
            .check(matches(isDisplayed()))
            .perform(ViewActions.click())
    }

    private fun mesaDao(): MesaDao {
        val db = DbHelper.db(InstrumentationRegistry.getTargetContext())
        val mesaDao = db.mesaDao()
        return mesaDao
    }

    private fun garcomDao(): GarcomDao {
        val db = DbHelper.db(InstrumentationRegistry.getTargetContext())
        val garcomDao = db.garcomDao()
        return garcomDao
    }

    private fun prepararDados() {
        criaGarcomJose(garcomDao())
        criaMesas(mesaDao())
    }

    private fun criaGarcomJose(garcomDao: GarcomDao) {
        garcomDao.add(Garcom(nome = "José da Silva", login = "jose", senha = "123456"))
    }

    private fun criaMesas(mesaDao: MesaDao) {
        for (i in 1..MAXIMO_MESAS) {
            mesaDao.add(Mesa(numero = i.toString().padStart(3, '0')))
        }
    }

    @After
    fun tearDown() {
        this.limpaDados()
    }

    fun limpaDados() {
        val context = InstrumentationRegistry.getTargetContext()
        context.deleteDatabase(BuildConfig.DATABASE_NAME)
    }
}