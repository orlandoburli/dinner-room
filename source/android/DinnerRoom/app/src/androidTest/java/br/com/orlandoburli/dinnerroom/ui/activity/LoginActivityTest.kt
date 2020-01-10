package br.com.orlandoburli.dinnerroom.ui.activity

import android.content.Intent
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import br.com.orlandoburli.dinnerroom.BuildConfig
import br.com.orlandoburli.dinnerroom.R
import br.com.orlandoburli.dinnerroom.dao.GarcomDao
import br.com.orlandoburli.dinnerroom.database.DbHelper
import br.com.orlandoburli.dinnerroom.matchers.ToastMatcher
import br.com.orlandoburli.dinnerroom.model.Garcom
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginActivityTest {

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(LoginActivity::class.java, true, false)

    @Before
    fun setup() {
        limpaDados()
        prepararDados()
    }

    @Test
    fun deve_EntrarNaTelaPrincipal_QuandoInformarUsuarioSenhaCorretamente() {

        activityTestRule.launchActivity(Intent())

        preencheUsuarioSenha("jose", "123456")

        clicarBotaoEntrar()

        verificaSeEntrouNaActivityPrincipal()
    }

    @Test
    fun deve_EntrarNaTelaPrincipal_QuandoInformarUsuarioSenhaCorretamenteEmOrdemInversa() {

        activityTestRule.launchActivity(Intent())

        preencherSenha("123456")

        preencherLogin("jose")

        clicarBotaoEntrar()

        verificaSeEntrouNaActivityPrincipal()
    }

    @Test
    fun deve_ExibirMensagemDeErro_QuandoInformadoUsuarioIncorreto() {

        activityTestRule.launchActivity(Intent())

        preencheUsuarioSenha("josex", "123456")

        clicarBotaoEntrar()

        verificaSeFoiApresentadaMensagemUsuarioSenhaIncorretos()
    }

    @Test
    fun deve_ExibirMensagemDeErro_QuandoInformadaSenhaIncorreta() {

        activityTestRule.launchActivity(Intent())

        preencheUsuarioSenha("jose", "1234567")

        clicarBotaoEntrar()

        verificaSeFoiApresentadaMensagemUsuarioSenhaIncorretos()
    }

    @Test
    fun deve_ExibirMensagemDeErro_QuandoNaoInformadoUsuario() {

        activityTestRule.launchActivity(Intent())

        preencheUsuarioSenha("", "1234567")

        clicarBotaoEntrar()

        verificaSeFoiApresentadaMensagemUsuarioSenhaIncorretos()
    }

    @Test
    fun deve_ExibirMensagemDeErro_QuandoNaoInformadaSenha() {

        activityTestRule.launchActivity(Intent())

        preencheUsuarioSenha("jose", "")

        clicarBotaoEntrar()

        verificaSeFoiApresentadaMensagemUsuarioSenhaIncorretos()
    }

    @Test
    fun deve_ExibirMensagemDeErro_QuandoNaoInformadoUsuarioESenha() {

        activityTestRule.launchActivity(Intent())

        preencheUsuarioSenha("", "")

        clicarBotaoEntrar()

        verificaSeFoiApresentadaMensagemUsuarioSenhaIncorretos()
    }

    @Test
    fun deve_ExibirMensagemDeErro_QuandoClicarDiretoEmEntrar() {

        activityTestRule.launchActivity(Intent())

        clicarBotaoEntrar()

        verificaSeFoiApresentadaMensagemUsuarioSenhaIncorretos()
    }

    private fun preencheUsuarioSenha(login: String, senha: String) {

        preencherLogin(login)

        preencherSenha(senha)
    }

    private fun verificaSeEntrouNaActivityPrincipal() {

        // Checa se exibe o título
        onView(withText("Dinner Room - José da Silva"))
            .check(matches(isDisplayed()))

        // Checa se exibe o label "mesas"
        onView(
            allOf(
                withId(R.id.layout_principal_screen_label_mesas),
                withText("Mesas")
            )
        ).check(matches(isDisplayed()))

        // Checa se existe a logo dinner room
        onView(withId(R.id.layout_principal_screen_logo))
            .check(matches(isDisplayed()))
    }

    private fun verificaSeFoiApresentadaMensagemUsuarioSenhaIncorretos() {
        onView(withText(R.string.mensagem_usuario_senha_invalidos))
            .inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
    }

    private fun prepararDados() {
        val garcomDao = garcomDao()

        criaGarcomJose(garcomDao)
        criaGarcomAlex(garcomDao)
        criaGarcomCarlos(garcomDao)
    }

    private fun garcomDao(): GarcomDao {
        val db = DbHelper.db(InstrumentationRegistry.getTargetContext())
        val garcomDao = db.garcomDao()
        return garcomDao
    }

    private fun preencherLogin(login: String) {
        onView(withId(R.id.layout_login_screen_login))
            .check(matches(isDisplayed()))
            .perform(click())
            .perform(typeText(login), closeSoftKeyboard())
    }

    private fun preencherSenha(senha: String) {
        onView(withId(R.id.layout_login_screen_password))
            .check(matches(isDisplayed()))
            .perform(click())
            .perform(typeText(senha), closeSoftKeyboard())
    }

    private fun clicarBotaoEntrar() {
        onView(withId(R.id.layout_login_screen_botao_entrar))
            .check(matches(isDisplayed()))
            .perform(click())
    }

    private fun criaGarcomJose(garcomDao: GarcomDao) {
        garcomDao.add(Garcom(nome = "José da Silva", login = "jose", senha = "123456"))
    }

    private fun criaGarcomAlex(garcomDao: GarcomDao) {
        garcomDao.add(Garcom(nome = "Alex dos Santos", login = "alex", senha = "abcdef"))
    }

    private fun criaGarcomCarlos(garcomDao: GarcomDao) {
        garcomDao.add(Garcom(nome = "Carlos Bragança", login = "carlosb", senha = "poilkj"))
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
