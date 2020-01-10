package br.com.orlandoburli.dinnerroom.exception

/**
 * Classe exception base para as exceções do projeto
 * @author Orlando Burli
 */
abstract class DinnerRoomException(override val message: String) : Exception(message) {
}