# <p align="center"> API de Serviços de Fiscalização da DSBRS - AGEMS </p>

<p align="center">
        <img src="/.assets/logo_nes2.png" height="60px", width=55px />
<img src="/.assets/java2.png" height="60px", width=55px  />
<img src="/.assets/sring2.png" height="60px", width=55px />
<img src="/.assets/Maven2.png" height="60px", width=55px />
<img src="/.assets/sringBoot2.png" height="60px", width=55px />
<img src="/.assets/Swagger2.png" height="60px", width=55px />
<img src="/.assets/jwt2.png" height="60px" , width=55px/>
<img src="/.assets/docker2.png" height="60px", width=55px />
<img src="/.assets/postgres2.png" height="60px", width=55px />


</p>

Este repositório reúne os artefatos de códigos do Back-End produzidos no ano de 2023, 1° semestre, durante o processo de desenvolvimento do projeto do App de Fiscalização da AGEMS no escopo da disciplinas de Prática em Desenvolvimento de Software I da Faculdade de Computação (FACOM) da Universidade Federal do Mato Grosso do Sul (UFMS).

## Descrição do Projeto: 

A Diretoria de Regulação e Fiscalização - Saneamento Básico e Resíduos Sólidos (DSBRS) regula e fiscaliza os serviços de fornecimento de água potável, esgotamento sanitário e manejo de resíduos sólidos nos municípios conveniados. 

Atualmente, as fiscalizações são feitas por meio de formulários físicos e registro de fotografias com smartphone. 
Cada fiscalização gera um relatório complexo, em que os  fiscalizadores separam as centenas de imagens registradas por unidade, inserem as informações do município no Word, conferem o formulário e incluem manualmente todas as imagens, constatações, não conformidades, recomendações e determinações. 

Os serviços disponibilizados pela API REST desenvolvida apoiam o App Mobile de Fiscalização para a DSBRS-AGEMS, com o objetivo de permitir o cadastro, confecção e manipulação de formulários utilizados nas fiscalizações da DSBRS-AGEMS. Possui também serviços que facilitam o retorno dos formulários, bem como imagens e não conformidades relacionadas para a confecção dos relatórios.

## Funcionalidades 
Os serviços disponibilizam as seguintes funcionalidades:

- [x]  Cadastro de formulários;
- [x]  Edição de formulários;
- [x]  Deleção de formulários;
- [x]  Listagem de formulários;
- [x]  Visualização de um formulário específico;
- [x]  Cadastro de modelo de formulários (formulários são os preenchidos e personalizados por unidade);
- [x]  Edição de modelo;
- [x]  Deleção de modelo;
- [x]  Listagem de modelo;
- [x]  Visualização de um modelo específico;
- [x]  Inserção de imagens junto as respostas do formulário;
- [x]  Edição e deleção de imagens do formulário;
- [x]  Edição de respostas do formulário;
- [x]  Cadastro de questões e portarias vinculadas a um modelo;
- [x]  Edição de questões e portarias vinculadas a um modelo;
- [x]  Cadastro de constatações, não conformidades, recomendações e determinações conforme as respostas do formulário;
- [x]  Edição de constatações, não conformidades, recomendações e determinações conforme as respostas do formulário;
- [x]  Cadastro de usuários;
- [x]  Autenticação de usuários;
- [x]  Edição de informações de usuário;
- [x]  Visualização de informações de usuário;
- [x]  Cadastro de unidade;
- [x]  Edição de unidade;
- [x]  Deleção de unidade;
- [x]  Visualização de unidade específica;
- [x]  Listagem de unidades;

## Requisitos

- [Docker](https://www.docker.com/) (versão 23 ou superior).

Este sistema foi desenvolvido e amplamente testado em ambientes Unix e Windows.

## Instalação / Implantação

A API REST foi desenvolvida na linguagem [Java](https://www.java.com/pt-BR/) (Versão 20), com uso do framework [Spring](https://spring.io/) e a ferramenta de gerenciamento, construção e implantação de projetos [Maven](https://maven.apache.org/) (Versão 3.8.7). Foi documentada com o apoio do [Swagger](https://swagger.io/), encriptada com Tokens [JWT](https://jwt.io/) e conteinerizada com Docker. O armazenamento dos dados é feito em um banco [PostgreSQL](https://www.postgresql.org/), também conteinerizado em um Docker.

### Versão hospedada em nuvem:

Há uma versão disponível para testes hospedada em nuvem. O hub de documentação da API pode ser acessado via:
[http://54.210.245.243:8080/swagger-ui/index.html#/](http://54.210.245.243:8080/swagger-ui/index.html#/)

### Versão local:
Para ter uma versão local da API:

1. Instale o Docker em sua máquina.
2. Clone esse repositório.
3. Acesse o diretório `/apis` do projeto e execute a ativação dos contêineres:
```
cd apis
```
```
docker compose build
```
```
docker compose up -d
```
        
A API estará disponível na porta 8080, com o hub de documentação acessível pelo endereço:
[http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)


## Autores e histórico

Os serviços foram desenvolvido pela seguinte equipe:

- [Júlia Alves Corazza](https://github.com/j-alves-c) (j.alves@ufms.br)
- [Luiz Alexandre Felipe de Oliveira](https://github.com/luizAlexandre-ops) (felipe_luiz@ufms.br) 

Orientado pelo professor [Hudson Silva Borges](https://github.com/hsborges) (hudson.borges@ufms.br) e proposto por João Lucas Alves da Silva.


