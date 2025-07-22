# Algoritmo Genético para Otimização de Cronogramas

## Introdução

Este projeto implementa um algoritmo genético para otimizar cronogramas de aulas universitárias. O objetivo é encontrar uma solução que minimize conflitos e maximize a eficiência do cronograma, considerando restrições específicas como disponibilidade de professores, capacidade das salas e horários.

## Estrutura do Projeto

### Arquivos e Pastas

- **src/**: Contém os arquivos fonte do projeto.
  - `AlgoritmoGeneticoCemDisciplinas.java`: Classe principal que implementa o algoritmo genético.
  - `Aula.java`: Representa uma aula no cronograma.
- **bin/**: Diretório onde os arquivos compilados são armazenados.
- **config.properties**: Arquivo de configuração com parâmetros do algoritmo.
- **cronograma_simples.txt**: Exemplo de cronograma inicial.
- **cronograma_otimizado.txt**: Resultado do cronograma otimizado.

## Funcionamento do Algoritmo Genético

### Passos Principais

1. **Inicialização**: Geração de uma população inicial de cromossomos (soluções).
2. **Avaliação (Fitness)**: Cada cromossomo é avaliado com base em uma função de fitness que mede a qualidade do cronograma.
3. **Seleção**: Cromossomos com melhor fitness são selecionados para reprodução.
4. **Cruzamento (Crossover)**: Combinação de cromossomos selecionados para gerar novos cromossomos.
5. **Mutação**: Alteração aleatória em cromossomos para introduzir diversidade.
6. **Iteração**: Repetição dos passos até atingir um critério de parada (número de gerações ou fitness satisfatório).

## Classes

### `AlgoritmoGeneticoCemDisciplinas`

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

### `CromossomoOtimizado`

- Classe interna de `AlgoritmoGeneticoCemDisciplinas`.
- Representa um cromossomo (solução).
- Contém métodos para:
  - Avaliação de fitness.
  - Aplicação de mutação.
  - Cruzamento com outros cromossomos.

## Detalhes do Algoritmo

### Mutação

A mutação é realizada alterando aleatoriamente elementos do cromossomo, como horários ou disciplinas. Por exemplo:

- Trocar o horário de uma aula.
- Alterar o professor responsável por uma disciplina.
- Modificar a sala atribuída a uma aula.

Essas alterações introduzem diversidade na população e ajudam a evitar que o algoritmo fique preso em mínimos locais.

### Fitness

A função de fitness avalia a qualidade do cronograma com base em critérios como:

- **Redução de Conflitos**: Evitar que professores ou alunos tenham horários conflitantes.
- **Distribuição Equilibrada**: Garantir que as aulas sejam distribuídas de forma eficiente ao longo da semana.
- **Preferências de Professores**: Respeitar a disponibilidade dos professores.
- **Capacidade das Salas**: Garantir que o número de alunos não exceda a capacidade das salas.

A avaliação de fitness utiliza normalização para que os valores fiquem entre 0 e 1, facilitando a comparação entre soluções.

## Como Executar

### Compilar o Projeto

Execute o script `compilar.bat`:

```bash
compilar.bat
```

### Executar o Projeto

Execute o script `executar.bat`:

```bash
executar.bat
```

O cronograma otimizado será salvo no arquivo `cronograma_otimizado.txt`.

## Configuração

Os parâmetros do algoritmo podem ser ajustados no arquivo `config.properties`, como:

- Tamanho da população.
- Taxa de mutação.
- Número de gerações.
- Configurações específicas do problema (número de disciplinas, professores, salas, etc.).

## Contribuição

Sinta-se à vontade para contribuir com melhorias ou novas funcionalidades. Abra uma issue ou envie um pull request.

## Licença

Este projeto está licenciado sob a [MIT License](LICENSE).
