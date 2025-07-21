# Algoritmo Genético para Agendamento Universitário

Este projeto implementa um algoritmo genético para resolver o complexo problema de agendamento de aulas universitárias. O sistema é capaz de gerar cronogramas otimizados considerando restrições como disponibilidade de professores, capacidade das salas, preferências dos alunos e evitando conflitos de horários.

## O Problema Real

O agendamento de aulas universitárias é um problema de otimização combinatória NP-difícil. Em uma universidade típica, temos:

- **Disciplinas**: Cada curso possui várias disciplinas que precisam ser oferecidas
- **Professores**: Cada professor tem competências específicas e disponibilidade limitada
- **Salas**: Recursos físicos com capacidades diferentes
- **Horários**: Períodos disponíveis durante a semana
- **Alunos**: Que se matriculam em diversas disciplinas e não podem ter aulas conflitantes

Um coordenador acadêmico pode levar semanas para criar manualmente um cronograma que atenda a todas essas restrições. Nosso algoritmo genético consegue gerar soluções de alta qualidade em minutos, atendendo às restrições e maximizando o aproveitamento dos recursos.

## Versões do Algoritmo

O projeto oferece três versões do algoritmo:

1. **Versão Simples**: Otimizada para 15 disciplinas, ideal para testes iniciais
2. **Versão Escalável**: Otimizada para 100-150 disciplinas, com paralelização
3. **Versão Ultra Escalável**: Otimizada para 500+ disciplinas, com heurísticas avançadas

## Estrutura do Projeto

### Classes Principais

- **AlgoritmoGenetico**: Contém as três versões do algoritmo (mainSimples, mainCem, mainQuinhentas)
- **AlgoritmoGeneticoMain**: Interface para escolha da versão a executar
- **Cromossomo**: Representa uma solução completa (um cronograma)
- **CromossomoOtimizado**: Versão otimizada para problemas médios (100+ disciplinas)
- **CromossomoUltra**: Versão altamente otimizada para problemas grandes (500+ disciplinas)
- **Aula**: Representa uma aula específica (disciplina, professor, sala, horário)
- **DadosProblema**: Contém os dados do problema (disciplinas, professores, etc.)
- **ConfigSimples, Config100, Config500**: Configurações para cada versão do algoritmo
- **DisponibilidadeProfessor**: Gerencia a disponibilidade dos professores por horário

## Funcionamento do Algoritmo Genético

O algoritmo genético funciona baseado em princípios evolutivos:

1. **Inicialização**: Gera uma população inicial de soluções aleatórias
2. **Avaliação**: Calcula o fitness (qualidade) de cada solução
3. **Seleção**: Escolhe os melhores indivíduos para reprodução (seleção por torneio)
4. **Cruzamento**: Combina pares de soluções para gerar novas soluções
5. **Mutação**: Introduz pequenas alterações aleatórias nas soluções
6. **Elitismo**: Preserva as melhores soluções para a próxima geração
7. **Repetição**: Repete o processo por várias gerações até atingir um critério de parada

### Representação do Cromossomo

Cada cromossomo representa um cronograma completo, consistindo em uma lista de aulas. Cada aula contém:

- Disciplina
- Professor
- Sala
- Horário

### Cálculo de Fitness

O fitness é calculado considerando três componentes principais:

1. **Qualidade de Alojamento (40%)**: Proporção de disciplinas alocadas com sucesso

   ```java
   double qualidadeAlojamento = (double) cromossomo.getAulas().size() / numDisciplinas;
   ```

2. **Qualidade de Distribuição (30%)**: Distribuição eficiente ao longo dos horários disponíveis

   ```java
   double qualidadeDistribuicao = (double) horariosUsados.size() / numHorarios;
   ```

3. **Penalização de Conflitos (30%)**: Penalizações por violações de restrições
   - Professor não disponível: +5 pontos de penalização
   - Professor em dois lugares ao mesmo tempo: +4 pontos
   - Professor sem competência para a disciplina: +6 pontos
   - Sala superlotada: +3 pontos
   - Aluno em duas disciplinas simultaneamente: +1 ponto

O fitness final é normalizado para um valor entre 0 e 1, sendo 1 a solução perfeita:

```java
double fitness = (qualidadeAlojamento * 0.4) + (qualidadeDistribuicao * 0.3) + ((1.0 - penalizacaoConflitos) * 0.3);
```

Na versão ultra escalável, usamos amostragem para calcular o fitness em grandes populações, melhorando significativamente a performance.

### Operadores Genéticos

#### Cruzamento (Crossover)

O operador de cruzamento combina dois cronogramas para gerar um novo:

- **Versão Simples**: Cruzamento segmentado - alterna segmentos de aulas entre os pais

  ```java
  int tamanhoSegmento = Math.max(1, pai.getAulas().size() / 10);
  // Alterna entre 10 segmentos dos pais
  ```

- **Versões Otimizadas**: Cruzamento de ponto único - divide o cromossomo em duas partes
  ```java
  int corte = random.nextInt(Math.min(pai1.getAulas().size(), pai2.getAulas().size()));
  // Primeira parte do pai1, segunda parte do pai2
  ```

Em todas as versões, evitamos duplicação de disciplinas no filho resultante.

#### Mutação

A mutação introduz diversidade alterando aleatoriamente propriedades das aulas:

```java
// Exemplo de mutação
if (random.nextBoolean()) {
    aula.setSala(random.nextInt(NUM_SALAS));
} else {
    aula.setHorario(random.nextInt(NUM_HORARIOS));
}
```

Na versão otimizada, verificamos a disponibilidade do professor ao mutar para garantir soluções mais válidas.

#### Seleção por Torneio

Selecionamos indivíduos usando torneio, onde o melhor de K candidatos aleatórios é escolhido:

```java
for (int i = 0; i < tamanhoTorneio; i++) {
    Cromossomo candidato = populacao.get(random.nextInt(populacao.size()));
    if (melhor == null || candidato.getFitness() > melhor.getFitness()) {
        melhor = candidato;
    }
}
```

## Exemplo Real: Agendamento de um Departamento Universitário

Imagine um departamento de Ciência da Computação com:

- 150 disciplinas para agendar
- 30 professores com especializações diferentes
- 20 salas de aulas com capacidades variadas
- 1000 alunos matriculados em múltiplas disciplinas
- 50 horários disponíveis na semana

Desafios:

1. O Prof. Silva só pode dar aulas às segundas e quartas
2. A Profa. Oliveira é especialista em IA e não pode lecionar Banco de Dados
3. A disciplina de Programação Avançada precisa de um laboratório específico
4. Alguns alunos fazem disciplinas de diferentes períodos
5. As salas têm capacidades diferentes e algumas disciplinas são muito populares

Manualmente, este cronograma levaria semanas para ser criado. Com nosso algoritmo:

```
$ java -cp bin src.AlgoritmoGeneticoMain
🧬 ALGORITMO GENÉTICO PARA AGENDAMENTO UNIVERSITÁRIO
===================================================
Escolha a versão do algoritmo:
1. Versão Simples (15 disciplinas)
2. Versão Escalável (150 disciplinas)
3. Versão Ultra Escalável (500 disciplinas)
Opção: 2

🎓 ALGORITMO GENÉTICO OTIMIZADO - AGENDAMENTO UNIVERSITÁRIO
Versão Escalável para 100+ Disciplinas
=========================================================
🧬 Gerando população paralela...
✅ 500 cromossomos criados em paralelo
Geração   0 - Melhor: 0.82 | Pior: 0.21 | Diversidade: 0.612
Geração  20 - Melhor: 0.87 | Pior: 0.45 | Diversidade: 0.421
...
Geração 180 - Melhor: 0.96 | Pior: 0.67 | Diversidade: 0.287
Geração 199 - Melhor: 0.97 | Pior: 0.72 | Diversidade: 0.252

🏆 MELHOR SOLUÇÃO ENCONTRADA:
Fitness: 0.97
Tempo de execução: 45.32 segundos
Disciplinas alocadas: 148/150 (98.7%)
```

O algoritmo gera um cronograma de alta qualidade em menos de um minuto, com 98.7% das disciplinas alocadas e poucas violações de restrições.

## Conclusão

Este projeto demonstra como algoritmos genéticos podem resolver eficientemente problemas complexos de agendamento que seriam extremamente difíceis de solucionar manualmente ou com métodos tradicionais. As três versões oferecem flexibilidade para diferentes cenários, desde pequenos departamentos até grandes universidades.
