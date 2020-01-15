package br.com.orlandoburli.dinnerroom.ui.activity

import android.content.Intent
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import br.com.orlandoburli.dinnerroom.BuildConfig
import br.com.orlandoburli.dinnerroom.R
import br.com.orlandoburli.dinnerroom.dao.GarcomDao
import br.com.orlandoburli.dinnerroom.dao.MesaDao
import br.com.orlandoburli.dinnerroom.dao.ProdutoDao
import br.com.orlandoburli.dinnerroom.database.AppDatabase
import br.com.orlandoburli.dinnerroom.database.DbHelper
import br.com.orlandoburli.dinnerroom.model.Garcom
import br.com.orlandoburli.dinnerroom.model.ItemComanda
import br.com.orlandoburli.dinnerroom.model.Mesa
import br.com.orlandoburli.dinnerroom.model.Produto
import br.com.orlandoburli.dinnerroom.service.ComandaService
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.math.BigDecimal

class TelaPrincipalActivityTest {

    private val MAXIMO_MESAS = 12

    private val db: AppDatabase by lazy { DbHelper.db(InstrumentationRegistry.getTargetContext()) }

    private val mesaDao: MesaDao by lazy { db.mesaDao() }

    private val garcomDao: GarcomDao by lazy { db.garcomDao() }

    private val produtoDao: ProdutoDao by lazy { db.produtoDao() }

    private val comandaService: ComandaService by lazy { ComandaService(db) }

    @Rule
    @JvmField
    val loginTestRule = ActivityTestRule(LoginActivity::class.java, true, false)
    private val cocaCola = Produto(nome = "Coca cola Lata", preco = BigDecimal(7.00))
    private val fanta = Produto(nome = "Fanta Laranja", preco = BigDecimal(7.10))
    private val guaranaKuat = Produto(nome = "Guarana Kuat", preco = BigDecimal(7.20))
    private val sprite = Produto(nome = "Sprite", preco = BigDecimal(7.30))
    private val fantaUva = Produto(nome = "Fanta Uva", preco = BigDecimal(7.40))
    private val espetinhoAlcatra = Produto(nome = "Espetinho de alcatra", preco = BigDecimal(14.00))

    private val espetinhoFile = Produto(nome = "Espetinho de filé", preco = BigDecimal(25.00))

    private val espetinhoPicanha = Produto(nome = "Espetinho de picanha", preco = BigDecimal(30.00))

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

    @Test
    fun deve_AbrirTelaDeComanda_QuandoClicarNaMesa001ENaoTiverComandaCriada() {
        clicarMesa("001")

        onView(withText("Mesa 001 Comanda 1"))
            .check(matches(isDisplayed()))

        pressBack()
    }

    @Test
    fun deve_AbrirTelaDeComanda_QuandoClicarNaMesa001EJaTiverComandaCriada() {
        clicarMesa("001")

        pressBack()

        clicarMesa("002")

        pressBack()

        clicarMesa("001")

        onView(withText("Mesa 001 Comanda 1"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun deve_AbrirTelaDeComandaComProdutos_QuandoClicarNaMesa001() {
        clicarMesa("001")

        onView(withText("Mesa 001 Comanda 1"))
            .check(matches(isDisplayed()))

        pressBack()

        comandaService.adicionaItem(
            ItemComanda(
                idProduto = cocaCola.id,
                precoUnitario = cocaCola.preco,
                quantidade = BigDecimal(1),
                idComanda = 1,
                produto = cocaCola
            )
        )

        comandaService.adicionaItem(
            ItemComanda(
                idProduto = fanta.id,
                precoUnitario = fanta.preco,
                quantidade = BigDecimal(1),
                idComanda = 1,
                produto = fanta
            )
        )

        comandaService.adicionaItem(
            ItemComanda(
                idProduto = guaranaKuat.id,
                precoUnitario = guaranaKuat.preco,
                quantidade = BigDecimal(2),
                idComanda = 1,
                produto = guaranaKuat
            )
        )

        comandaService.adicionaItem(
            ItemComanda(
                idProduto = espetinhoAlcatra.id,
                precoUnitario = espetinhoAlcatra.preco,
                quantidade = BigDecimal(2),
                idComanda = 1,
                produto = espetinhoAlcatra
            )
        )

        comandaService.adicionaItem(
            ItemComanda(
                idProduto = espetinhoFile.id,
                precoUnitario = espetinhoFile.preco,
                quantidade = BigDecimal(2),
                idComanda = 1,
                produto = espetinhoFile
            )
        )

        comandaService.adicionaItem(
            ItemComanda(
                idProduto = espetinhoPicanha.id,
                precoUnitario = espetinhoPicanha.preco,
                quantidade = BigDecimal(1),
                idComanda = 1,
                produto = espetinhoPicanha
            )
        )

        clicarMesa("001")
    }

    private fun clicarMesa(numeroMesa: String) {
        onView(withText(numeroMesa))
            .check(matches(isDisplayed()))
            .perform(click())
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
            .perform(click())
            .perform(ViewActions.typeText(login), ViewActions.closeSoftKeyboard())
    }

    private fun preencherSenha(senha: String) {
        onView(ViewMatchers.withId(R.id.layout_login_screen_password))
            .check(matches(isDisplayed()))
            .perform(click())
            .perform(ViewActions.typeText(senha), ViewActions.closeSoftKeyboard())
    }

    private fun clicarBotaoEntrar() {
        onView(ViewMatchers.withId(R.id.layout_login_screen_botao_entrar))
            .check(matches(isDisplayed()))
            .perform(click())
    }

    private fun prepararDados() {
        criaGarcomJose()
        criaMesas()
        criaProdutos()
    }

    private fun criaGarcomJose() {
        garcomDao.add(Garcom(nome = "José da Silva", login = "jose", senha = "123456"))
    }

    private fun criaMesas() {
        for (i in 1..MAXIMO_MESAS) {
            mesaDao.add(Mesa(numero = i.toString().padStart(3, '0')))
        }
    }

    private fun criaProdutos() {
        cocaCola.id = produtoDao.add(cocaCola)
        fanta.id = produtoDao.add(fanta)
        guaranaKuat.id = produtoDao.add(guaranaKuat)
        sprite.id = produtoDao.add(sprite)
        fantaUva.id = produtoDao.add(fantaUva)
        espetinhoAlcatra.id = produtoDao.add(espetinhoAlcatra)
        espetinhoFile.id = produtoDao.add(espetinhoFile)
        espetinhoPicanha.id = produtoDao.add(espetinhoPicanha)
    }

    @After
    fun tearDown() {
        this.limpaDados()
        this.db.close()
    }

    fun limpaDados() {
        val context = InstrumentationRegistry.getTargetContext()
        context.deleteDatabase(BuildConfig.DATABASE_NAME)
    }
}