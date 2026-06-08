# Atividade de Acompanhamento — CSES Download Speed

## 1. Resumo do problema

O problema **Download Speed**, da plataforma CSES, apresenta uma rede de computadores conectados por canais direcionados de transmissão de dados. Cada conexão possui uma capacidade máxima, que representa a velocidade máxima que pode passar por ela.

O objetivo é calcular a **maior velocidade total de download** possível do computador `1` até o computador `n`.

Esse problema é resolvido como um problema de **fluxo máximo em rede capacitada**.

---

## 2. Interpretação da entrada e da saída

A primeira linha da entrada contém dois valores:

```text
n m
```

Onde:

* `n` representa a quantidade de computadores;
* `m` representa a quantidade de conexões entre os computadores.

Em seguida, são lidas `m` linhas no formato:

```text
a b c
```

Onde:

* `a` é o computador de origem da conexão;
* `b` é o computador de destino da conexão;
* `c` é a capacidade máxima dessa conexão.

A saída esperada é um único número inteiro, representando o **fluxo máximo** do computador `1` até o computador `n`.

---

## 3. Modelagem da rede de fluxo

A modelagem é feita da seguinte forma:

* cada computador é um vértice da rede;
* cada conexão é uma aresta direcionada;
* a capacidade da aresta é igual à velocidade máxima informada na entrada;
* o computador `1` é a origem;
* o computador `n` é o sorvedouro.

### Vértices

Os vértices representam os computadores:

```text
1, 2, 3, ..., n
```

### Origem

A origem é o vértice:

```text
1
```

Ele representa o servidor de onde os dados saem.

### Sorvedouro

O sorvedouro é o vértice:

```text
n
```

Ele representa o computador final que deve receber os dados.

### Arestas e capacidades

Cada linha:

```text
a b c
```

gera uma aresta direcionada:

```text
a → b
```

com capacidade:

```text
c
```

Essa capacidade limita a quantidade máxima de fluxo que pode passar por aquela conexão.

---

## 4. Justificativa da modelagem

A modelagem representa corretamente o problema porque o enunciado descreve uma rede em que dados saem de um servidor, passam por conexões com limites de velocidade e chegam a um computador final.

Como cada conexão possui uma capacidade máxima, o fluxo em cada aresta não pode ultrapassar esse limite.

Assim, maximizar o fluxo entre o computador `1` e o computador `n` equivale a maximizar a velocidade total de download.

---

## 5. Algoritmo escolhido

O algoritmo escolhido é o **Dinic**.

Inicialmente foi considerada a utilização do algoritmo Edmonds-Karp, por ser uma implementação clássica de fluxo máximo baseada em caminhos aumentantes encontrados por BFS. Entretanto, durante os testes realizados para o problema CSES Download Speed, observou-se que o Edmonds-Karp apresentou desempenho insuficiente para os maiores casos de teste da plataforma.

Após autorização do professor da disciplina, foi adotado o algoritmo **Dinic**, que mantém a mesma modelagem de rede de fluxo e utiliza os mesmos conceitos fundamentais de fluxo máximo e grafo residual, porém com desempenho superior.

O algoritmo funciona em duas etapas principais:

1. Construção de um grafo de níveis utilizando BFS.
2. Busca de fluxos bloqueantes utilizando DFS sobre o grafo de níveis.

O processo é repetido até que o sorvedouro não possa mais ser alcançado no grafo residual.

### Complexidade

Considerando:

```text
V = número de vértices
E = número de arestas
```

## 6. Instância pequena

Usaremos o exemplo do enunciado:

```text
4 5
1 2 3
2 4 2
1 3 4
3 4 5
4 1 3
```

A rede fica:

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

---

## 7. Execução manual passo a passo

### Estado inicial

Fluxo total inicial:

```text
0
```

Todas as capacidades residuais começam iguais às capacidades originais.

---

### Primeiro caminho aumentante

A BFS encontra o caminho:

```text
1 → 2 → 4
```

Capacidades disponíveis no caminho:

```text
1 → 2 = 3
2 → 4 = 2
```

O gargalo é a menor capacidade do caminho:

```text
min(3, 2) = 2
```

Então enviamos `2` unidades de fluxo.

Atualização das capacidades residuais:

```text
1 → 2: 3 - 2 = 1
2 → 4: 2 - 2 = 0
```

Arestas reversas criadas ou atualizadas:

```text
2 → 1: 2
4 → 2: 2
```

Fluxo total:

```text
2
```

---

### Segundo caminho aumentante

A BFS encontra o caminho:

```text
1 → 3 → 4
```

Capacidades disponíveis no caminho:

```text
1 → 3 = 4
3 → 4 = 5
```

O gargalo é:

```text
min(4, 5) = 4
```

Então enviamos `4` unidades de fluxo.

Atualização das capacidades residuais:

```text
1 → 3: 4 - 4 = 0
3 → 4: 5 - 4 = 1
```

Arestas reversas criadas ou atualizadas:

```text
3 → 1: 4
4 → 3: 4
```

Fluxo total:

```text
2 + 4 = 6
```

---

### Condição de parada

Após essas atualizações, não existe mais caminho com capacidade residual positiva saindo da origem `1` até o sorvedouro `4`.

Portanto, o algoritmo termina.

---

## 8. Verificação da resposta final

O fluxo máximo encontrado foi:

```text
6
```

Logo, a maior velocidade possível de download é:

```text
6
```

Esse valor é a resposta final porque representa a maior quantidade de dados que consegue sair do servidor e chegar ao computador final respeitando todas as capacidades das conexões.

---

## 9. Recuperação da resposta

Neste problema, não é necessário recuperar corte mínimo, emparelhamento ou reconstruir caminhos finais.

A resposta é apenas o valor total acumulado do fluxo máximo.

Portanto, ao final do algoritmo, imprimimos:

```text
6
```

---

## 10. Conclusão

O problema foi modelado como fluxo máximo porque precisamos maximizar a quantidade total de dados enviada de uma origem até um destino.

Cada computador é representado por um vértice, cada conexão por uma aresta direcionada com capacidade, o computador `1` representa a origem e o computador `n` representa o sorvedouro.

A implementação final utiliza o algoritmo de Dinic, que constrói grafos de níveis e encontra fluxos bloqueantes no grafo residual até atingir o fluxo máximo.

O valor final obtido corresponde à maior velocidade total de download possível entre o servidor e o computador de destino.