# language: pt
Funcionalidade: Gerenciamento de pedidos
  Como um usuário da API de pedidos
  Eu quero ser capaz de criar, visualizar, atualizar e deletar pedidos
  Para gerenciar pedidos de forma eficiente

  Cenário: Criar um novo pedido
    Dado que eu tenho um pedido válido para criar
    Quando eu envio uma solicitação
    Então eu recebo uma resposta com status 201
    E o pedido é salvo no sistema

#  Cenário: Atualizar um pedido existente
#    Dado que eu tenho um pedido existente com ID
#    E eu tenho os detalhes atualizados para o pedido
#    Quando eu envio uma solicitação com as informações atualizadas
#    Então eu recebo uma resposta com status 200
#    E os detalhes do pedido são atualizados no sistema

  Cenário: Recuperar todos os pedidos
    Quando eu envio uma solicitação GET para orders
    Então eu recebo uma resposta com status 200
    E uma lista de todos os pedidos é retornada

  Cenário: Recuperar um pedido pelo ID
    Quando eu envio uma solicitação GET para orders passando o id do pedido
    Então eu recebo uma resposta com status 200
    E os detalhes do pedido com ID passado são retornados

  Cenário: Deletar um pedido
    Quando eu envio uma solicitação DELETE para orders passando o id do pedido 2
    Então eu recebo uma resposta com status 200
    E o pedido com ID 2 é removido do sistema

#  Cenário: Tentativa de atualizar um pedido inexistente
#    Dado que não existe um pedido com ID "999"
#    Quando eu envio uma solicitação PUT para "/v1/orders/999" com alguns detalhes
#    Então eu recebo uma resposta com status 404
#    E uma mensagem de erro é retornada indicando que o pedido não foi encontrado
