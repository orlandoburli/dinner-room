package br.com.orlandoburli.dinnerroom.service

import br.com.orlandoburli.dinnerroom.dao.GarcomDao
import br.com.orlandoburli.dinnerroom.model.Garcom
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GarcomServiceTest {

    @Mock
    lateinit var garcomDao: GarcomDao

    @Test
    fun deve_LogarNoSistema_QuandoPassarUsuarioSenhaCorretos() {
//        val garcomService = Mockito.spy(GarcomService(garcomDao))
//
//        Mockito.`when`(garcomDao.byLogin(ArgumentMatchers.anyString()))
//            .thenReturn(Garcom(id = 1, login = "jose", nome = "Jose Silva", senha = "123456"))
//
//        Mockito.`when`(garcomService.byLogin(ArgumentMatchers.anyString()))
//            .thenReturn(Garcom(id = 1, login = "jose", nome = "Jose Silva", senha = "123456"))
//
//        val byLogin = garcomDao.byLogin("")
//
//        val jose = garcomService.login("jose", "123456")
//
//        assertNotNull(jose)
//        assertThat(jose.id, equalTo(1))
//        assertThat(jose.nome, equalTo("Jose Silva"));
//        assertThat(jose.login, equalTo("jose"))
//        assertThat(jose.senha, equalTo("123456"))
    }
}