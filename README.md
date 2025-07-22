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

O fitness é calculado com base em critérios como:

- **Distribuição de aulas**: Minimizar conflitos de horários e superlotação de salas.
- **Preferências de professores**: Garantir que professores estejam disponíveis nos horários atribuídos.
- **Eficiência do cronograma**: Maximizar o uso de recursos como salas e horários.

A função de fitness utiliza componentes ponderados, como:

- **Alojamento**: 40% do fitness.
- **Distribuição**: 30% do fitness.
- **Qualidade**: 30% do fitness.

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