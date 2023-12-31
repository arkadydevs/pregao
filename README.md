<img width=100% src="https://capsule-render.vercel.app/api?type=waving&color=f2b0de&height=120&section=header"/>

[![Typing SVG](https://readme-typing-svg.herokuapp.com/?color=bbaefc&size=35&center=true&vCenter=true&width=1000&lines=Olá!,+Seja+Bem+Vindo+ao+Pregão+Arkady!;Simulamos+um+pregão+de+bolsa+de+valores;desenvolvido+em+Java!+:%29)](https://git.io/typing-svg)


<h1 align="center"> Pregão Bolsa de Valores </h1>

<p align="center">
  <img src="https://cdn.discordapp.com/attachments/1143004037780877324/1165121635364384818/Untitled813_20231020235700.png?ex=65616294&is=654eed94&hm=ad59672086cd31733d6ae48f110eac638794f2dbbb5510de7ac7f7a83474ed8f&" width="330" height="400">
</p>
    


### <p align="center">  Pregão feito para avalição da disciplina de LG2. </p>



### <p align="center"> Integrantes do grupo: </p>
<p align="center"> Ana Luísa de Oliveira Camardella </p>
<p align="center"> Carlos Alberto Pereira Lustosa Júnior </p>
<p align="center"> João Augusto Haupt Fonseca Oliveira </p>


## <span style= "color: pink;"> Visão geral </span>
   Este é um projeto Java que implementa uma simulação de um pregão simplificado, utilizando bancos de dados em formato de arquivo de texto (TXT) para guardar os dados das classes. Utilizamos a linguagem Java, com uma lógica fortemente orientada a objetos. Feito para aprender conceitos báiscos de investimentos.

## Como baixar?
Primeiro é preciso clonar o respositório
```gitexclude
git clone https://github.com/arkadydevs/pregao.git
```
### 1° Método
Depois é preciso ter o JavaFX instalado na máquina para rodar usando esse comando dentro do out/artifacts/pregao_jar
```gitexclude
java --module-path "/caminho/para/javafx/lib" --add-modules javafx.controls,javafx.fxml -jar pregao.jar
```
### 2° Método
Ir em alguma IDEA (IntelliJ, NetBeans, Eclipse, Node++) e rodar a classe MainApp do programa




## Objetivo
O objetivo deste projeto é criar um sistema de pregão em Java, com interface gráfica em JavaFX, como parte da disciplina de lógica. Buscamos aplicar os conceitos aprendidos ao longo do ano, como programação orientada a objetos, algoritmos de estruturas de dados e algoritmos de ordenação. Durante o desenvolvimento do código, contamos com as IDEs IntelliJ e Visual Studio, além do Maven para facilitar o gerenciamento de dependências e compilação. Essas ferramentas foram escolhidas para proporcionar um ambiente de desenvolvimento eficiente. Em resumo, este projeto é uma oportunidade prática de testar e consolidar os conhecimentos adquiridos em sala de aula.

## Outros
### Funcionalidades
- Cadastrar-se como empresa ou investidor físico
- Fazer ativos
- Vender e Comprar ativos
- Ver histórico de ativos (Gráfico)
- Ver histórico de vendas e compras
- Fazer diversas carteiras e adicionar cada ativo em uma carteira
- Ver os ativos com maiores e menores quantidades e menores e maiores preços na aba em Alta
  

### <span style= "color: orange;">Entidades (Obs: todas possuem um banco de dados asssociado)</span>
- Ativos (Ordinária, preferencial e FII)
- Investidores (Físico e jurídico)
- Carteira
- CarteiraAcoes
- Histórico
- Mudança de Preço
- Corretora
- Custodiante

### <span style= "color: orange;"> Estruturas de dados: </span>
<span style ="color: pink;">**- Pilha:**</span> uma coleção de elementos organizados seguindo o princípio "last in, first out" (LIFO), que significa que o último elemento adicionado à pilha é o primeiro a ser removido.

<span style ="color: pink;"> **- Lista encadeada:**</span> estrutura de dados usada para armazenar e organizar uma coleção de elementos. Ela consiste em uma série de nós, onde cada nó contém dois componentes principais: um valor (ou dado) e uma referência ao próximo nó na sequência.

<span style ="color: pink;">**- Árvore:**</span> estrutura de dados em que cada nó tem, no máximo, dois filhos: um filho à esquerda e um filho à direita. Ela é composta por nós interconectados, onde cada nó pode ter zero, um ou dois filhos. O primeiro nó, chamado de nó raiz, é o ponto de partida da árvore, e os nós sem filhos são chamados de folhas.

<span style ="color: pink;">**- Fila:**</span> uma coleção ordenada de elementos onde a inserção de novos elementos ocorre no final (ou "calda") e a remoção ocorre no início (ou "cabeça"). A fila segue o princípio "first in, first out" (FIFO), o que significa que o primeiro elemento a ser inserido é o primeiro a ser removido.

### <span style= "color: orange;"> Algoritimo de ordenação: </span>
<span style ="color: pink;">**- ShellShort:**</span> Algoritmo de ordenação que melhora o desempenho do algoritmo de ordenação por inserção ao dividir a lista em subgrupos menores e ordená-los separadamente usando a ordenação por inserção.

### <span style= "color: orange;"> Método de busca: </span>
<span style ="color: pink;">**- Sequencial:**</span> Método de busca em que cada elemento de uma lista é verificado sequencialmente até encontrar o elemento desejado ou percorrer toda a lista. É um algoritmo simples, e como o programa não tem listas de larga escala não será tão ineficiente. 



### <span style="color: orange;"> FERRAMENTAS/EXTRAS:</span>
- Maven (Para verificar cpfs, cnpjs, deploy e JavaFX)
- JavaFX (Para toda interface gráfica)
- Arquivos TXT (Usados para armazenamentos de dados)
- IntelliJ (IDEA usada)

<img width=100% src="https://capsule-render.vercel.app/api?type=waving&color=f2b0de&height=120&section=header"/>


