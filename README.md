# Algoritmo Genético para Otimização de Cronogramas

## Introdução

Este projeto implementa um algoritmo genético para otimizar cronogramas de aulas universitárias. O objetivo é encontrar uma solução que minimize conflitos e maximize a eficiência do cronograma, considerando restrições específicas como disponibilidade de professores, capacidade das salas e horários.

## Estrutura do Projeto

### Arquivos e Pastas

- **src/**: Contém os arquivos fonte do projeto.
  - `AlgoritmoGenetico.java`: Classe principal que implementa o algoritmo genético.
  - `Aula.java`: Representa uma aula no cronograma.
- **bin/**: Diretório onde os arquivos compilados são armazenados.
- **config.properties**: Arquivo de configuração com parâmetros do algoritmo.
- **cronograma.txt**: Exemplo de cronograma gerado pelo algoritmo.

## Funcionamento do Algoritmo Genético

### Passos Principais

1. **Inicialização**: Geração de uma população inicial de cromossomos (soluções).
2. **Avaliação (Fitness)**: Cada cromossomo é avaliado com base em uma função de fitness que mede a qualidade do cronograma.
3. **Seleção**: Cromossomos com melhor fitness são selecionados para reprodução.
4. **Cruzamento (Crossover)**: Combinação de cromossomos selecionados para gerar novos cromossomos.
5. **Mutação**: Alteração aleatória em cromossomos para introduzir diversidade.
6. **Iteração**: Repetição dos passos até atingir um critério de parada (número de gerações ou fitness satisfatório).

### Cálculo do Fitness

O fitness é uma medida da qualidade do cronograma gerado, normalizada entre 0.0 e 1.0, onde 1.0 representa uma solução perfeita. O cálculo é composto por três componentes principais:

#### 1. Qualidade de Alojamento (40% do fitness)

- Avalia a quantidade de disciplinas efetivamente alocadas.
- Calculado como: `numeroDeAulasAlocadas / numeroDeDisciplinas`
- Penaliza soluções que deixam disciplinas sem horário.
- Peso maior (40%) por ser o objetivo principal do algoritmo.

#### 2. Qualidade de Distribuição (30% do fitness)

- Avalia o aproveitamento dos horários disponíveis.
- Calculado como: `numeroDiferenteDeHorariosUsados / numeroTotalDeHorarios`
- Incentiva o uso uniforme dos horários ao longo da semana.
- Evita concentração excessiva de aulas em poucos horários.

#### 3. Penalizações por Conflitos (30% do fitness)

Reduz o fitness baseado em diversos tipos de conflitos, com pesos diferentes:

- **Conflitos de Professor (Peso 6)**

  - Professor designado para disciplina que não pode lecionar
  - Penalização mais severa por violar uma restrição fundamental

- **Indisponibilidade de Professor (Peso 5)**

  - Professor alocado em horário que não está disponível
  - Alta penalização por comprometer a execução do cronograma

- **Professor em Múltiplas Aulas (Peso 4)**

  - Professor alocado em duas ou mais aulas simultâneas
  - Penalização significativa por impossibilidade física

- **Superlotação de Sala (Peso 3)**

  - Número de alunos excede a capacidade da sala
  - Penalização moderada por questões de infraestrutura

- **Conflitos de Alunos (Peso 1)**
  - Aluno alocado em duas disciplinas no mesmo horário
  - Penalização menor por ser mais flexível

#### Fórmula do Fitness Final

```
fitness = (qualidadeAlojamento * 0.4) +
         (qualidadeDistribuicao * 0.3) +
         ((1.0 - penalizacaoConflitos) * 0.3)
```

O resultado é normalizado entre 0.0 e 1.0, onde:

- **0.0 a 0.3**: Solução muito ruim, com muitos conflitos
- **0.3 a 0.6**: Solução regular, com alguns conflitos
- **0.6 a 0.8**: Solução boa, com poucos conflitos
- **0.8 a 1.0**: Solução excelente, com mínimo de conflitos

O algoritmo utiliza cache para otimizar o cálculo do fitness, evitando recalcular valores para cromossomos idênticos. Cada cromossomo possui um hash único baseado em suas características, permitindo um cache eficiente em um ambiente paralelo.

## Detalhes do Algoritmo

### Fitness

A função de fitness avalia a qualidade do cronograma com base nos seguintes critérios:

- **Redução de Conflitos**: Evitar que professores ou alunos tenham horários conflitantes.
- **Distribuição Equilibrada**: Garantir que as aulas sejam distribuídas de forma eficiente ao longo da semana.
- **Preferências de Professores**: Respeitar a disponibilidade dos professores.
- **Capacidade das Salas**: Garantir que o número de alunos não exceda a capacidade das salas.
- **Qualidade Geral**: Considerar a satisfação geral dos critérios acima.

Os valores de fitness são normalizados entre 0 e 1 para facilitar a comparação entre soluções.

### Cromossomos e Cortes

Um cromossomo representa uma solução completa para o cronograma. Ele é composto por múltiplos genes, onde cada gene representa uma aula específica (disciplina, professor, sala e horário).

- **Número de Genes**: Cada cromossomo contém um total de 150 genes, correspondendo ao número de disciplinas.
- **Cortes no Cromossomo**: Durante o cruzamento, são realizados dois cortes no cromossomo para dividir os genes em segmentos. Esses cortes permitem combinar partes de dois cromossomos diferentes, gerando novos cromossomos com características mistas.

### Mutação

A mutação é realizada alterando aleatoriamente atributos de um cromossomo, como:

- Trocar o horário de uma aula.
- Alterar a sala atribuída.
- Substituir o professor de uma aula.

A taxa de mutação é configurada como 5% para preservar boas soluções enquanto introduz diversidade.

### Cromossomo

Um cromossomo representa uma solução para o problema de agendamento. Ele é composto por uma lista de aulas, onde cada aula contém:

- Disciplina.
- Professor.
- Sala.
- Horário.

## Classes do Projeto

### `AlgoritmoGenetico`

- Classe principal que gerencia o algoritmo genético.
- Responsável por:
  - Inicializar a população.
  - Executar os passos do algoritmo genético.
  - Salvar o cronograma otimizado.
- Implementa paralelização para maior eficiência.

### `Aula`

- Representa uma aula no cronograma.
- Contém informações como:
  - Disciplina.
  - Professor.
  - Sala.
  - Horário.
- Inclui métodos para manipulação e comparação de aulas.

## Execução do Algoritmo Genético

#### Inicialização dos Dados

O método `initializarDadosEscalaveis` é chamado para configurar os dados do problema, como disciplinas, professores, salas e horários.
Os dados são gerados automaticamente com base nas configurações definidas no arquivo `config.properties`.

#### Geração da População Inicial

Uma população inicial de cromossomos é criada. Cada cromossomo representa uma solução para o cronograma.
Os cromossomos são gerados aleatoriamente para garantir diversidade.

#### Avaliação de Fitness

Cada cromossomo é avaliado usando o método `calcularFitness`.
O fitness mede a qualidade do cronograma com base em critérios como redução de conflitos, distribuição equilibrada e preferências de professores.

#### Seleção

Os melhores cromossomos são selecionados para reprodução usando o método de torneio.
A seleção garante que as melhores soluções sejam preservadas.

#### Cruzamento (Crossover)

Cromossomos selecionados são combinados para gerar novos cromossomos.
O método `cruzarOtimizado` realiza o cruzamento, dividindo os cromossomos em segmentos e combinando-os.

#### Mutação

Alterações aleatórias são aplicadas aos cromossomos para introduzir diversidade.
O método `mutarOtimizado` realiza a mutação, modificando genes como horários ou professores.

#### Iteração

Os passos de avaliação, seleção, cruzamento e mutação são repetidos por várias gerações.
O número de gerações é definido no arquivo `config.properties`.

#### Salvar Cronograma Otimizado

O melhor cromossomo da última geração é salvo como o cronograma otimizado.
O método `salvarCronogramaOtimizado` escreve o cronograma em um arquivo de texto, detalhando as aulas por dia e horário.

### Observação

O algoritmo utiliza paralelização para melhorar a eficiência, executando avaliações de fitness e outras operações em múltiplas threads.
