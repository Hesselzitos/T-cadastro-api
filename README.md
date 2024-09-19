# T-cadastro-api
Com o objetivo de treinamento, este projeto deve criar uma api em JAVA 17 utilizando o spring boot como framework que fornece um crud do cadastro de clientes inserindo e alterando uma de dados noSQL dynamodb.


# How to call?

- Consulta: curl -X GET http://localhost:8080/api/v1/data/1?port=63413
  - Sendo 1 o id e 63413 o valor da porta que um node qualquer da dht iniciou.
- curl -X POST http://localhost:8080/api/v1/data -d "id=1&value=Hello&port=63413"
    - Sendo 1 o id, Hello o valor a ser guardado e 63413 o valor da porta que um node qualquer da dht iniciou.