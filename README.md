# T6 - Identificacao de Isomorfismo em Arvores

Implementacao em **Java** do Trabalho Pratico 6 da disciplina
**Resolucao de Problemas com Grafos**.

O programa recebe dois arquivos `algs4` (cada um descrevendo um grafo nao
direcionado), valida que cada entrada e uma arvore, encontra o(s) centro(s)
por remocao iterativa de folhas, gera a **codificacao canonica** de cada
arvore e decide se as duas arvores sao isomorfas.

## Estrutura

```text
T6/
├── README.md
├── T6.md
├── dados/
│   ├── invalid-ciclo3.txt
│   ├── iso-path4-a.txt
│   ├── iso-path4-b.txt
│   ├── nao-iso-estrela5.txt
│   ├── nao-iso-path5.txt
│   ├── unico-centro-a.txt
│   └── unico-centro-b.txt
├── imgs/
│   └── UNIFOR_logo1b.png
├── refs/
│   └── youtube_videos.md
└── src/
    ├── Bag.java
    ├── Graph.java
    ├── In.java
    ├── Main.java
    ├── Stack.java
    ├── StdIn.java
    ├── StdOut.java
    └── TreeIsomorphism.java
```

## Compilacao

No diretorio `src`, execute:

```bash
javac Main.java TreeIsomorphism.java Graph.java Bag.java Stack.java In.java StdIn.java StdOut.java
```

## Execucao

```bash
java Main ../dados/iso-path4-a.txt ../dados/iso-path4-b.txt
java Main ../dados/nao-iso-path5.txt ../dados/nao-iso-estrela5.txt
java Main ../dados/unico-centro-a.txt ../dados/unico-centro-b.txt
java Main ../dados/invalid-ciclo3.txt ../dados/iso-path4-a.txt
```

## Como funciona a solucao

A entrada de cada arquivo e lida e armazenada com a classe `Graph` da base
oficial `algs4` (lista de adjacencia). A logica de isomorfismo fica isolada
em `TreeIsomorphism.java` e segue tres passos:

1. **Validacao da arvore.** Uma arvore com `V` vertices precisa ter
   exatamente `V - 1` arestas, ser conexa (BFS a partir de `0` deve
   alcancar todos os vertices) e nao conter self-loops. Qualquer falha
   nessas condicoes interrompe a comparacao.

2. **Centros por remocao iterativa de folhas.** O algoritmo coloca em uma
   lista `L` todas as folhas atuais (vertices de grau `<= 1`), decrementa o
   grau de cada vizinho e gera uma nova camada de folhas. O processo
   continua ate restarem 1 ou 2 vertices, que sao os centros da arvore. O
   numero de centros e relevante para a etapa seguinte.

3. **Codificacao canonica.** Enraizando a arvore em um centro, cada subarvore
   e codificada recursivamente: folhas viram `()` e cada no concatena os
   codigos dos filhos **em ordem lexicografica** dentro de parenteses. A
   ordenacao e o que torna a representacao independente da ordem em que as
   arestas aparecem no arquivo.

   - **Centro unico:** a codificacao canonica e `encode(centro)`.
   - **Dois centros:** o algoritmo gera as duas codificacoes possiveis,
     enraizando em cada centro, e adota a menor lexicograficamente como
     forma canonica. Esse passo e necessario porque, com dois centros,
     enraizar em um ou no outro pode produzir codigos textualmente
     diferentes para a mesma arvore.

Duas arvores sao isomorfas se, e somente se, suas codificacoes canonicas
finais coincidem.

## Saida do programa

Para cada execucao, o programa imprime:

- a lista de adjacencia das duas arvores (representacao da `Graph` algs4);
- a mensagem de validacao para cada entrada;
- o(s) centro(s) de cada arvore valida;
- a codificacao canonica de cada arvore valida;
- o veredito final (`SAO` ou `NAO SAO` isomorfas).

Quando ao menos uma das entradas nao e uma arvore valida, a comparacao e
interrompida e a razao da invalidade e exibida.

## Video

Link do video explicativo: PREENCHER
