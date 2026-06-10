# CSES Download Speed

Trabalho Prático 3 — Unidade 3
Disciplina: Resolução de Problemas com Grafos
Tema: Fluxo Máximo em Redes

## Problema

**Nome:** Download Speed
**Plataforma:** CSES
**Link:** [https://cses.fi/problemset/task/1694/](https://cses.fi/problemset/task/1694/)

## Link da apresentação

**Link:** [https://docs.google.com/presentation/d/13g4uv11tUUDUB56cpBiGq-3FY7C-gXZ_n9V-hF7Uxwk/edit?userstoinvite=marcelomrfilho%40gmail.com&sharingaction=manageaccess&role=writer&slide=id.p1#slide=id.p1](https://docs.google.com/presentation/d/13g4uv11tUUDUB56cpBiGq-3FY7C-gXZ_n9V-hF7Uxwk/edit?userstoinvite=marcelomrfilho%40gmail.com&sharingaction=manageaccess&role=writer&slide=id.p1#slide=id.p1)

## Integrantes

- João Pedro Mendes
- Marcelo Filho
- Nicolas dos Santos

## Linguagem utilizada

Java

## Como executar a solução

A solução está disponível na pasta `src/`.

Compilação:

```bash
javac src/Main.java
```

Execução:

```bash
java -cp src Main
```

A entrada deve ser fornecida pelo terminal no formato especificado pelo problema.

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

---

## Contexto do problema

O problema descreve uma rede de computadores conectados por canais de transmissão de dados. Cada conexão possui uma velocidade máxima de envio.

O objetivo é calcular a maior velocidade total de download possível entre o computador `1`, que representa o servidor, e o computador `n`, que representa o computador de destino.

Esse cenário é modelado como um problema de **fluxo máximo em rede capacitada**.

---

## Modelagem como rede de fluxo

A conversão do enunciado para rede de fluxo é direta:

- Cada computador é representado como um vértice.
- Cada conexão entre computadores é representada como uma aresta direcionada.
- A velocidade máxima de cada conexão é usada como capacidade da aresta.
- O computador `1` é a origem.
- O computador `n` é o sorvedouro.
- O valor do fluxo máximo representa a maior velocidade total de download.

---

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

---

## Algoritmo utilizado

A implementação final utiliza o algoritmo **Dinic**.

Inicialmente foi considerada a utilização do algoritmo Edmonds-Karp por sua simplicidade na explicação dos conceitos de fluxo máximo. Entretanto, durante os testes realizados na plataforma CSES, observou-se que o Edmonds-Karp apresentou estouro de tempo nos maiores casos de teste.

Após autorização do professor da disciplina, foi adotado o algoritmo Dinic, que mantém a mesma modelagem de rede de fluxo e utiliza os mesmos conceitos fundamentais de fluxo máximo e grafo residual, porém com desempenho significativamente superior.

O algoritmo funciona em duas etapas:

1. Construção de um grafo de níveis utilizando BFS.
2. Busca de fluxos bloqueantes utilizando DFS sobre o grafo de níveis.

O processo é repetido até que não exista mais caminho da origem até o sorvedouro no grafo residual.

---

## Papel do grafo residual

O grafo residual representa quanto fluxo ainda pode passar por cada aresta.

Quando enviamos fluxo por uma aresta, a capacidade residual dessa aresta diminui.

Ao mesmo tempo, uma aresta reversa recebe capacidade residual correspondente ao fluxo enviado, permitindo que o algoritmo reajuste decisões anteriores caso encontre caminhos melhores posteriormente.

O algoritmo Dinic utiliza o grafo residual para construir sucessivos grafos de níveis. Em cada fase:

- O BFS determina os níveis dos vértices.
- O DFS encontra fluxos bloqueantes respeitando esses níveis.

O algoritmo termina quando o sorvedouro não pode mais ser alcançado pela BFS no grafo residual.

Exemplo:

Se uma aresta possui capacidade `5` e enviamos `3` unidades de fluxo:

```text
capacidade residual direta = 2
capacidade residual reversa = 3
```

---

## Conversão do fluxo para a resposta

A resposta do problema é o valor total do fluxo máximo que consegue sair do vértice `1` e chegar ao vértice `n`.

Cada unidade de fluxo representa uma unidade de velocidade de transmissão.

Portanto:

```text
Fluxo máximo = maior velocidade total de download
```

---

## Corte mínimo, emparelhamento ou reconstrução de caminhos

Neste problema, não é necessário recuperar corte mínimo, emparelhamento ou reconstruir caminhos finais.

O problema pede apenas o valor máximo de download.

Assim, basta imprimir o valor total do fluxo máximo encontrado.

---

## Análise de complexidade

Considerando:

- `V` = número de vértices;
- `E` = número de arestas.

O algoritmo de Dinic possui complexidade de tempo, no pior caso geral:

```text
O(V² · E)
```

Para este problema:

```text
V ≤ 500
E ≤ 1000
```

Essa complexidade é adequada para os limites da plataforma CSES.

A complexidade de memória é:

```text
O(V + E)
```

pois a rede é armazenada utilizando listas de adjacência e arestas residuais.

---

## Casos especiais relevantes

Alguns casos que precisam ser considerados:

- Pode não existir caminho entre o computador `1` e o computador `n`; nesse caso, o fluxo máximo será `0`.
- Podem existir múltiplas arestas entre os mesmos vértices, e todas devem ser consideradas.
- As capacidades podem ser grandes, portanto deve-se utilizar o tipo `long`.
- As conexões são direcionadas, portanto uma aresta `a → b` não implica a existência de `b → a`.
- O algoritmo deve utilizar corretamente o grafo residual para atualizar capacidades e arestas reversas.

---

## Exemplo manual utilizando Dinic

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

### Construção do grafo de níveis

Executando uma BFS a partir da origem:

```text
Nível 0: 1
Nível 1: 2, 3
Nível 2: 4
```

Representação:

```text
1
↓ ↓
2 3
↓ ↓
4
```

### Fluxo bloqueante

Primeiro caminho:

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

Fluxo total acumulado:

```text
2 + 4 = 6
```

Após a atualização do grafo residual, não existe mais caminho da origem até o sorvedouro.

Logo:

```text
Fluxo Máximo = 6
```

---

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

---

## Estrutura do repositório

```text
T3/
├── README.md
├── acompanhamento/
│   └── roteiro.md
├── src/
│   └── Main.java
├── evidencias/
│   └── accepted.png
├── apresentacao/
│   └── apresentacao.md
└── dados/
    └── entradas_do_problema.txt
```

---

## Conclusão

O problema CSES Download Speed é resolvido por fluxo máximo porque precisamos maximizar a quantidade total de dados transmitida do servidor ao destino, respeitando os limites de capacidade de cada conexão.

A modelagem utiliza o computador `1` como origem, o computador `n` como sorvedouro, os computadores como vértices e as conexões como arestas direcionadas com capacidade.

A implementação final utiliza o algoritmo Dinic, autorizado pelo professor da disciplina devido às restrições da plataforma CSES. O algoritmo constrói grafos de níveis utilizando BFS e encontra fluxos bloqueantes utilizando DFS sobre o grafo residual.

O valor final acumulado do fluxo corresponde à maior velocidade total de download possível entre o servidor e o computador de destino.
