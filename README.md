# CSES Download Speed

Trabalho Prático 3 — Unidade 3
Disciplina: Resolução de Problemas com Grafos
Tema: Fluxo Máximo em Redes

## Problema

**Nome:** Download Speed
**Plataforma:** CSES
**Link:** [https://cses.fi/problemset/task/1694/](https://cses.fi/problemset/task/1694/)

## Integrantes

- João Pedro Mendes
- Marcelo Filho
- Nicolas dos Santos

## Linguagem utilizada

Java

## Como executar a solução

A solução estará disponível na pasta `src/`.

Para executar:

```bash
java src/Main.java
```

A entrada deve ser fornecida pelo terminal no formato do problema.

Exemplo:

```text
4 5
1 2 3
2 4 2
1 3 4
3 4 5
4 1 3
```

Saída esperada:

```text
6
```

## Contexto do problema

O problema descreve uma rede de computadores conectados por canais de transmissão de dados. Cada conexão possui uma velocidade máxima de envio.

O objetivo é calcular a maior velocidade total de download possível entre o computador `1`, que representa o servidor, e o computador `n`, que representa o computador de destino.

Esse cenário é modelado como um problema de **fluxo máximo em rede capacitada**.

## Modelagem como rede de fluxo

A conversão do enunciado para rede de fluxo é direta:

- Cada computador é representado como um vértice.
- Cada conexão entre computadores é representada como uma aresta direcionada.
- A velocidade máxima de cada conexão é usada como capacidade da aresta.
- O computador `1` é a origem.
- O computador `n` é o sorvedouro.
- O valor do fluxo máximo representa a maior velocidade total de download.

## Origem, sorvedouro, vértices, arestas e capacidades

### Origem

A origem da rede é o vértice:

```text
1
```

Esse vértice representa o servidor de onde os dados saem.

### Sorvedouro

O sorvedouro da rede é o vértice:

```text
n
```

Esse vértice representa o computador final que deve receber os dados.

### Vértices

Os vértices são os computadores da rede:

```text
1, 2, 3, ..., n
```

Cada vértice representa um computador.

### Arestas

Cada linha da entrada:

```text
a b c
```

é convertida em uma aresta direcionada:

```text
a → b
```

Isso significa que o computador `a` pode enviar dados para o computador `b`.

### Capacidades

A capacidade da aresta é o valor `c`.

Esse valor representa a velocidade máxima de transmissão naquela conexão.

Exemplo:

```text
1 2 3
```

gera:

```text
1 → 2 com capacidade 3
```

Ou seja, no máximo 3 unidades de fluxo podem passar de `1` para `2`.

## Algoritmo utilizado

O algoritmo utilizado será o **Edmonds-Karp**, uma variação do método de Ford-Fulkerson.

A diferença principal é que o Edmonds-Karp usa **BFS** para encontrar caminhos aumentantes no grafo residual.

A escolha do Edmonds-Karp é adequada porque:

- o problema é de fluxo máximo;
- as capacidades podem ser grandes;
- a BFS torna a escolha dos caminhos aumentantes mais previsível;
- a implementação com grafo residual é direta e segura para esse tipo de problema.

## Papel do grafo residual

O grafo residual representa quanto fluxo ainda pode passar por cada aresta.

Quando enviamos fluxo por uma aresta, a capacidade residual dessa aresta diminui.

Ao mesmo tempo, é criada ou atualizada uma aresta reversa, permitindo que o algoritmo corrija escolhas anteriores, caso encontre uma combinação melhor de caminhos depois.

Exemplo:

Se uma aresta possui capacidade `5` e enviamos `3` unidades de fluxo:

```text
capacidade residual direta = 2
capacidade residual reversa = 3
```

O algoritmo continua procurando caminhos aumentantes no grafo residual até não existir mais caminho da origem ao sorvedouro.

Quando isso acontece, o fluxo máximo foi encontrado.

## Conversão do fluxo para a resposta

A resposta do problema é o valor total do fluxo máximo que consegue sair do vértice `1` e chegar ao vértice `n`.

Cada unidade de fluxo representa uma unidade de velocidade de transmissão.

Portanto:

```text
Fluxo máximo = maior velocidade total de download
```

## Corte mínimo, emparelhamento ou reconstrução de caminhos

Neste problema, não é necessário recuperar corte mínimo, emparelhamento ou reconstruir caminhos finais.

O problema pede apenas o valor máximo de download.

Assim, basta imprimir o valor total do fluxo máximo encontrado.

## Análise de complexidade

Considerando:

- `V` = número de vértices;
- `E` = número de arestas.

O Edmonds-Karp possui complexidade de tempo:

```text
O(V * E²)
```

A busca por caminhos aumentantes é feita com BFS, e cada BFS percorre as arestas do grafo residual.

A complexidade de memória é:

```text
O(V + E)
```

pois a rede é armazenada usando lista de adjacência com arestas residuais e arestas reversas.

## Casos especiais relevantes

Alguns casos que precisam ser considerados:

- Pode não existir caminho entre o computador `1` e o computador `n`; nesse caso, o fluxo máximo será `0`.
- Podem existir múltiplas arestas entre os mesmos vértices, e todas devem ser consideradas.
- As capacidades podem ser grandes, então é importante usar um tipo numérico que suporte valores altos.
- As conexões são direcionadas, portanto uma aresta `a → b` não significa que também existe `b → a`.
- O algoritmo deve trabalhar com grafo residual para permitir atualização das capacidades e uso de arestas reversas.

## Exemplo manual

Entrada:

```text
4 5
1 2 3
2 4 2
1 3 4
3 4 5
4 1 3
```

Rede modelada:

```text
1 → 2 capacidade 3
2 → 4 capacidade 2
1 → 3 capacidade 4
3 → 4 capacidade 5
4 → 1 capacidade 3
```

Origem:

```text
1
```

Sorvedouro:

```text
4
```

Caminhos aumentantes principais:

```text
1 → 2 → 4
```

Gargalo:

```text
min(3, 2) = 2
```

Fluxo enviado:

```text
2
```

Segundo caminho:

```text
1 → 3 → 4
```

Gargalo:

```text
min(4, 5) = 4
```

Fluxo enviado:

```text
4
```

Fluxo total:

```text
2 + 4 = 6
```

Resposta:

```text
6
```

## Evidência de Accepted

A evidência da submissão aceita será adicionada na pasta:

```text
evidencias/
```

Arquivo esperado:

```text
evidencias/accepted.png
```

ou

```text
evidencias/accepted.pdf
```

## Estrutura do repositório

```text
T3/
├── README.md
├── acompanhamento/
│   └── roteiro.md
├── src/
│   └── main.py
├── evidencias/
│   └── accepted.png
├── apresentacao/
│   └── apresentacao.md
└── dados/
    └── entradas_do_problema.txt
```

## Conclusão

O problema CSES Download Speed é resolvido por fluxo máximo porque precisamos maximizar a quantidade total de dados transmitida do servidor ao destino, respeitando os limites de capacidade de cada conexão.

A modelagem utiliza o computador `1` como origem, o computador `n` como sorvedouro, os computadores como vértices e as conexões como arestas direcionadas com capacidade.

O algoritmo Edmonds-Karp encontra caminhos aumentantes no grafo residual até não existir mais caminho possível. O valor final acumulado do fluxo corresponde à maior velocidade de download possível.
