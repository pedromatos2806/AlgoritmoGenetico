# 🧬 Algoritmo Genético para Agendamento Universitário

## 📋 Visão Geral

Este projeto implementa um **Algoritmo Genético** para resolver o problema de agendamento universitário, criando cronogramas otimizados que alocam disciplinas, professores, salas e horários de forma eficiente.

## 🎯 O que é um Algoritmo Genético?

Um **Algoritmo Genético** é uma técnica de otimização inspirada na evolução natural que utiliza conceitos como seleção, cruzamento e mutação para encontrar soluções ótimas ou próximas do ótimo para problemas complexos.

### 🔬 Conceitos Fundamentais

#### 1. **População**
- Conjunto de **cromossomos** (soluções candidatas)
- Cada cromossomo representa um cronograma completo
- Tamanho típico: 200-500 indivíduos

#### 2. **Cromossomo** 
- Representa uma solução completa do problema
- Contém uma lista de **aulas** com suas alocações
- Cada cromossomo tem um **fitness** (qualidade da solução)

#### 3. **Gene**
- Unidade básica de informação
- No nosso caso: uma **aula** específica
- Contém: disciplina, professor, sala e horário

#### 4. **Fitness (Aptidão)**
- Medida da qualidade de uma solução (0.0 a 1.0)
- **40%** - Qualidade de Alojamento (disciplinas alocadas)
- **30%** - Qualidade de Distribuição (uso equilibrado de horários)
- **30%** - Ausência de Conflitos (penalizações por problemas)

#### 5. **Seleção**
- Escolha dos melhores cromossomos para reprodução
- Método usado: **Seleção por Torneio**
- Favorece soluções com maior fitness

#### 6. **Cruzamento (Crossover)**
- Combinação de dois cromossomos pais
- Gera novos cromossomos filhos
- Taxa típica: 85%

#### 7. **Mutação**
- Alteração aleatória em genes
- Mantém diversidade genética
- Taxa típica: 5%

#### 8. **Elitismo**
- Preservação dos melhores cromossomos
- Garante que boas soluções não sejam perdidas
- Percentual típico: 10% da população

## 🏗️ Arquitetura do Sistema

### 📁 Classes Fundamentais

#### 1. **`Aula.java`** - Gene do Sistema
```java
// Representa uma aula específica (gene)
class Aula {
    int disciplina;  // ID da disciplina
    int professor;   // ID do professor
    int sala;        // ID da sala
    int horario;     // ID do horário
}
```
**Função**: Unidade básica de informação - representa a alocação de uma disciplina específica.

#### 2. **`Cromossomo.java`** - Solução Candidata
```java
// Representa uma solução completa (cromossomo)
class Cromossomo {
    List<Aula> aulas;    // Lista de todas as aulas
    double fitness;      // Qualidade da solução
}
```
**Função**: Contém um cronograma completo e sua avaliação de qualidade.

#### 3. **`AlgoritmoGenetico.java`** - Versão Educacional
- **Propósito**: Demonstração acadêmica (15 disciplinas)
- **Características**: Código simples e didático
- **População**: 200 cromossomos
- **Gerações**: 100 iterações

#### 4. **`AlgoritmoGeneticoCemDisciplinas.java`** - Versão Escalável
- **Propósito**: Universidades médias (150+ disciplinas)
- **Características**: Paralelização, cache de fitness
- **População**: 500 cromossomos
- **Gerações**: 200 iterações
- **Otimizações**: ExecutorService, CompletableFuture

#### 5. **`AlgoritmoGeneticoQuinhentas.java`** - Versão Ultra Escalável
- **Propósito**: Grandes universidades (500+ disciplinas)
- **Características**: ForkJoinPool, fitness por amostragem
- **População**: 1000 cromossomos
- **Gerações**: 300 iterações
- **Otimizações**: Heurísticas inteligentes, paralelização massiva

## 🔄 Funcionamento Passo a Passo

### **Etapa 1: Inicialização**
```
1. Definir parâmetros do problema
   - Número de disciplinas, professores, salas, horários
   - Disponibilidade dos professores
   - Capacidade das salas
   - Alunos por disciplina

2. Criar estruturas de dados
   - Arrays de disponibilidade
   - Mapas de relacionamentos
   - Configurações de restrições
```

### **Etapa 2: Geração da População Inicial**
```
1. Para cada cromossomo na população:
   2. Para cada disciplina:
      3. Escolher professor aleatório (que pode lecionar)
      4. Escolher sala aleatória (com capacidade adequada)
      5. Escolher horário aleatório (professor disponível)
      6. Criar aula e adicionar ao cromossomo
   7. Calcular fitness do cromossomo
```

### **Etapa 3: Cálculo do Fitness**
```
Para cada cromossomo:

1. QUALIDADE DE ALOJAMENTO (40%)
   - Contar disciplinas alocadas
   - Calcular: disciplinas_alocadas / total_disciplinas

2. QUALIDADE DE DISTRIBUIÇÃO (30%)
   - Contar horários únicos utilizados
   - Calcular: horarios_unicos / total_horarios

3. PENALIZAÇÕES (30%)
   - Conflitos de professor (mesmo horário)
   - Conflitos de sala (superlotação)
   - Professor indisponível
   - Disciplina sem professor adequado
   - Calcular: 1.0 - (conflitos / max_conflitos)

4. FITNESS FINAL
   - fitness = (alojamento × 0.4) + (distribuição × 0.3) + (penalizações × 0.3)
   - Normalizar entre 0.0 e 1.0
```

### **Etapa 4: Evolução (Loop Principal)**
```
Para cada geração:

1. AVALIAÇÃO
   - Calcular fitness de todos os cromossomos
   - Ordenar população por fitness (melhor → pior)

2. SELEÇÃO
   - Implementar seleção por torneio
   - Escolher pais com maior probabilidade para melhores fitness

3. REPRODUÇÃO
   - Aplicar elitismo (manter 10% melhores)
   - Cruzamento: combinar pais para gerar filhos
   - Mutação: alterar genes aleatoriamente

4. SUBSTITUIÇÃO
   - Nova população substitui a anterior
   - Manter tamanho populacional constante

5. CRITÉRIO DE PARADA
   - Número máximo de gerações atingido
   - Fitness desejado alcançado
   - Convergência da população
```

### **Etapa 5: Resultado Final**
```
1. Selecionar melhor cromossomo da última geração
2. Verificar qualidade da solução:
   - Fitness > 0.85: Excelente
   - Fitness > 0.65: Aceitável
   - Fitness < 0.65: Necessita ajustes

3. Gerar cronograma formatado
4. Salvar em arquivo
5. Apresentar estatísticas
```

## ⚙️ Operadores Genéticos Detalhados

### 🎯 **Seleção por Torneio**
```java
// Seleciona melhor cromossomo de um grupo aleatório
Cromossomo selecionarPai(List<Cromossomo> populacao) {
    Cromossomo melhor = cromossomo_aleatorio();
    for (i = 0; i < tamanho_torneio; i++) {
        Cromossomo candidato = cromossomo_aleatorio();
        if (candidato.fitness > melhor.fitness) {
            melhor = candidato;
        }
    }
    return melhor;
}
```

### 🧬 **Cruzamento (Crossover)**
```java
// Combina dois pais para gerar filho
Cromossomo cruzar(Cromossomo pai, Cromossomo mae) {
    Cromossomo filho = new Cromossomo();
    
    for (cada aula) {
        if (random() < 0.5) {
            filho.adicionar(pai.getAula(i));
        } else {
            filho.adicionar(mae.getAula(i));
        }
    }
    
    return filho;
}
```

### 🔀 **Mutação**
```java
// Altera genes aleatoriamente
void mutar(Cromossomo cromossomo) {
    for (cada aula in cromossomo) {
        if (random() < taxa_mutacao) {
            // Alterar professor, sala ou horário aleatoriamente
            aula.modificarAleatorio();
        }
    }
}
```

## 📊 Métricas de Qualidade

### **Interpretação do Fitness:**
- **0.90 - 1.00**: Solução excelente (quase perfeita)
- **0.80 - 0.89**: Solução muito boa (poucos conflitos)
- **0.70 - 0.79**: Solução boa (conflitos aceitáveis)
- **0.60 - 0.69**: Solução razoável (necessita melhorias)
- **0.00 - 0.59**: Solução inadequada (muitos problemas)

### **Componentes do Fitness:**
1. **Alojamento (40%)**: Maximizar disciplinas alocadas
2. **Distribuição (30%)**: Equilibrar uso de horários
3. **Conflitos (30%)**: Minimizar violações de restrições

## 🚀 Como Executar

### **Versão Educacional (15 disciplinas):**
```bash
javac -d bin src/*.java
java -cp bin AlgoritmoGenetico
```

### **Versão Escalável (150+ disciplinas):**
```bash
java -cp bin AlgoritmoGeneticoCemDisciplinas
```

### **Versão Ultra Escalável (500+ disciplinas):**
```bash
java -cp bin AlgoritmoGeneticoQuinhentas
```

## 📈 Resultados Esperados

- **Versão Educacional**: Fitness ~0.93, 15/15 disciplinas alocadas
- **Versão 100+ Disciplinas**: Fitness ~0.89-0.93, 70-85% disciplinas alocadas
- **Versão 500 Disciplinas**: Fitness ~0.91-0.97, 85-99% disciplinas alocadas

## 💡 Conceitos Avançados Implementados

### **Paralelização**
- ExecutorService para cálculo paralelo de fitness
- CompletableFuture para geração paralela de população
- ForkJoinPool para processamento massivo

### **Otimizações**
- Cache de fitness para evitar recálculos
- Fitness por amostragem para grandes populações
- Heurísticas inteligentes na geração inicial

### **Normalização**
- Fitness sempre entre 0.0 e 1.0
- Penalizações proporcionais ao tamanho do problema
- Componentes balanceados para interpretação clara

## 🎓 Aplicação Acadêmica

Este projeto demonstra os princípios fundamentais dos Algoritmos Genéticos aplicados a um problema real de otimização combinatória, sendo ideal para:

- **Estudo de Metaheurísticas**
- **Otimização Combinatória**
- **Programação Paralela**
- **Sistemas de Agendamento**
- **Inteligência Artificial**

---

*Desenvolvido para demonstração acadêmica dos conceitos de Algoritmos Genéticos aplicados ao problema de agendamento universitário.*
