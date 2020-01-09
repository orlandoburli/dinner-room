![Logo Projeto Dinner Room](https://raw.githubusercontent.com/orlandoburli/dinner-room/master/documentation/prototype/dinner_room_logo.png)

# Modelagem

## Entidades

* Garcom
  * Id (Int)
  * Nome (Char(50))
  * Login (Char(20))
  * Senha (Char(50))

* Produto
  * Id (Int)
  * Nome (Char(50))
  * Preco (Double(10, 2))

* Mesa
  * Id (Int)
  * Numero (Char(10))

* Comanda
  * Id (Int)
  * IdMesa (Int)
  * QuantidadePessoas (Int)
  * Status (Aberta(1), Fechada(2))
  * _**Itens**_
    * Id (Int)
    * IdProduto (Int)
    * Quantidade (Int)
    * PrecoUnitario (Double(10, 2))
    * ValorTotal (Double(10, 2))