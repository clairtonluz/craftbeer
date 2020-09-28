# Cadastro de cervejas artesanais

O objetivo deste projeto é avaliar o desenvolvimento de uma aplicação java utilizando Spring e APIs.


### Como usar

#### Maven

Não precisa instalar o maven na sua máquina . O Comando abaixo vai baixar a versão do mavem para o projeto e executa-lo
 ```shell script
./mvnw spring-boot:run
```

#### Docker
Primeiramente é preciso ter o Docker e Docker compose instalado:

Para instalar o docker e docker compose siga os passos da documentação oficial: 

docker: https://docs.docker.com/engine/install/

docker compose: https://docs.docker.com/compose/install/

para executar a aplicação basta entrar no diretorio onde está a aplicação e executar o seguinte comando:

```shell
docker-composer up
```

Agora a aplicação está rodando em: http://localhost:9000/beerhouse/

#### Acessando banco de dados
h2 console (acesso ao banco): http://localhost:9000/beerhouse/h2-console
 - Driver Class: org.h2.Driver
 - JDBC URL: jdbc:h2:mem:craftbeerdb
 - username: sa
 - password: 


### Baseado no [Building an Application with Spring Boot](https://spring.io/guides/gs/spring-boot/).


## Set up environment

Para iniciar o projeto é necessário realizar o clone deste repositório

```bash
    $ git clone https://github.com/Sensedia/craftbeer.git
```

Você deverá compartilhar no seu repositório do github as alterações solicitadas para o projeto. 
O endereço deste repositório deverá ser enviado para rh@sensedia.com

## Especificação do projeto

A beer house é uma empresa possui um catálogo de cervejas artesanais. Esta empresa está buscando entrar no mundo digital.
Para entrar no mundo digital a beer house dicidiu começar pelas APIs. As APIs serão utilizadas para compartilhar dados com os parceiros e também para o seu sistema web.

Pra atender a esta demanda será necessário que a você implemente as APIs do projeto beer house.

Para implementar estas APIs você dever seguir a especificação do swagger que está neste projeto.

    craftbeer
    |
    |docs
    |    |___swagger-craftbeer


## Requisitos do projeto

1. Administrar cervejas: 

- O sistema deverá ter um cadastro de cervejas artesanais por API.<br/>
- O sistema deverá ser capaz de criar, excluir e alterar as cervejas.
   
2. Sistema deverá armazenar os dados em banco de dados. 
 
- Poderá ser utilizado MYSQL ou qualquer banco de dados embbeded.<br/>
  Caso seja utilizado o MYSQL, adicionar o script para criação do banco.
- A comunicação com o sistema deverá ser feita através de JPA.

3. O sistema deve conter testes unitários com JUnit

4. O sistema deve conter uma forma de validar o funcionamento.
   
- Deverá ser diponibilizado uma coleção do postman ou soapui para testar todos os recursos

## O que será avaliado no projeto

- Qualidade de código
- Design patterns utilizados
- O sistema tem que estar completo e possuir todos os scripts necessários para a execução
- A utilização do JPA de forma correta
- A criação de testes unitários

## O que você deve fazer:

- Utilizar java ao máximo e mostrar todo o seu conhecimento.
- Entregar o projeto completo
- Usar Java 8 e deixar a gente bem feliz com isso!

## O que você pode fazer:

- Utilizar frameworks
- Utilizar Spring Data ou qualquer outro framework para JPA.
- Alterar e criar o código à vontade
- Consultar tutoriais, consultar fóruns e tirar dúvidas
- Você pode aprender com código de outras pessoas, utilizar trechos, mas não usar tudo igual.

## O que você não pode fazer:

- Copiar de outros candidatos
- Pedir alguém para fazer o projeto para você

## Links de sugestão:

### [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/).
### [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/).
