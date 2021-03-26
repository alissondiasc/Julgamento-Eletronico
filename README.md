
#  Julgamento Eletrônico
<p align="center">🚀 Desafio proposto pela empresa SouthSystem para simulação de um julgamento eletrônico</p>

Tecnologias usadas:

Kafka

O Apache Kafka é uma plataforma distribuída de transmissão de dados que é capaz de publicar, subscrever, armazenar e processar fluxos de registro em tempo real. E este foi escolhido para informa aos associados resultados de julgamento de diversas pautas.

Swagger

 Aplicação open source que auxilia desenvolvedores nos processos de definir, criar, documentar e consumir APIs REST. 
 
Spring-cloud/Openfeign

Me auxilio de forma prática fazer integração com servicos externos a aplicação e muito mais.

MongoDB - Banco de Dados

O MongoDB é orientado a documentos, ou seja, os dados são armazenados como documentos, ao contrário de bancos de dados de modelo relacional, onde trabalhamos com registros em linhas e colunas. Os documentos podem ser descritos como dados no formato de chave-valor, no caso, utilizando o formato JSON (JavaScript Object Notation). Este foi escolhido para este desafio pela sua praticidade, agilidade e ganho de tempo no desenvolvimento de persistencia de dados em nossa base.

Lombok

O Lombok é uma biblioteca Java focada em produtividade e redução de código boilerplate que, por meio de anotações adicionadas ao nosso código, ensinamos o compilador (maven ou gradle) durante o processo de compilação a criar código Java.

#  Construção e inicialização do projeto.

- mvn instal: para download das dependencias no repositório maven 

- docker compose up: (na raiz do projeto) para rodar os container do kafka

- mvnw spring-boot:run: para startar o servidor da aplicação

Links

- Swagger: http://localhost:8080/api/swagger-ui
- Postamn: https://www.getpostman.com/collections/57144039b3f1518fd03b
