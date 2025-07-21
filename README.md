# 🧬 Algoritmo Genético para Montagem de Horários Universitários

## 🤔 O que este programa faz e por que é "genético"?

Este programa **cria automaticamente horários para uma universidade**, resolvendo um quebra-cabeça complexo:

- Combinar professores, disciplinas, salas e horários
- Evitar conflitos (como um professor dando duas aulas ao mesmo tempo)
- Criar um cronograma que funcione bem para todos

Imagine tentar montar manualmente um quebra-cabeça com milhares de peças, onde cada peça precisa se encaixar perfeitamente com várias outras - seria praticamente impossível! É exatamente esse problema que este software resolve.

## 🧠 Como funciona um Algoritmo Genético?

### 🌱 Por que "Genético"? A incrível inspiração na natureza

O nome **"Algoritmo Genético"** não é por acaso. Este programa funciona imitando exatamente como a **evolução natural** trabalha há bilhões de anos na Terra:

1. **Na Natureza**: Diferentes organismos existem em uma população
   **No Programa**: Criamos centenas de versões diferentes do horário

2. **Na Natureza**: Organismos mais adaptados têm maior chance de sobreviver
   **No Programa**: Horários com menos conflitos recebem notas mais altas

3. **Na Natureza**: Organismos bem-sucedidos se reproduzem, combinando seus genes
   **No Programa**: Combinamos partes de bons horários para criar novos horários

4. **Na Natureza**: Mutações aleatórias introduzem novidades na espécie
   **No Programa**: Mudanças aleatórias nos horários descobrem novas possibilidades

5. **Na Natureza**: Após muitas gerações, a espécie está mais adaptada ao ambiente
   **No Programa**: Após muitas iterações, temos um horário quase perfeito

### 🔍 Os conceitos genéticos aplicados aos horários:

#### 1. **"População"** - Como uma espécie com indivíduos diversos

- **Na Natureza**: Uma população de girafas com diferentes alturas de pescoço
- **No Programa**: Centenas de horários completos diferentes sendo testados simultaneamente
- **Por que é genético?**: Assim como na natureza, trabalhamos com muitas variações ao mesmo tempo, não apenas uma solução única

#### 2. **"Cromossomo"** - A identidade completa de um indivíduo

- **Na Natureza**: O DNA completo que define todas as características de um ser vivo
- **No Programa**: Um horário completo com todas as aulas, professores e salas alocadas
- **Por que é genético?**: Como o DNA determina todas as características de um ser vivo, o "cromossomo" do programa contém todas as decisões que formam um horário

#### 3. **"Gene"** - As características individuais

- **Na Natureza**: Genes que definem cor dos olhos, altura, etc.
- **No Programa**: Uma aula específica como "Cálculo 1, Prof. Silva, Sala 101, Segunda 8h"
- **Por que é genético?**: Assim como genes controlam características específicas, cada aula é uma decisão individual que compõe o horário maior

#### 4. **"Fitness"** - A adaptação ao ambiente

- **Na Natureza**: Quão bem um animal está adaptado para sobreviver em seu ambiente
- **No Programa**: Nota de 0 a 10 que avalia quão bom é um horário (sem conflitos)
- **Por que é genético?**: Na natureza, indivíduos mais adaptados têm mais chance de reprodução; no nosso programa, horários melhores têm mais chance de contribuir para a próxima geração

## 🧩 Como o programa é organizado e sua analogia genética

### 📁 As peças principais do programa e seus equivalentes biológicos

#### 1. **Aula** - O Gene do sistema

```
Uma aula (GENE) contém:
- Qual disciplina será dada
- Qual professor vai ensinar
- Em qual sala acontecerá
- Em qual horário acontecerá
```

**Analogia genética**: Assim como um gene controla uma característica específica do organismo (como cor dos olhos), cada aula é uma unidade básica de informação que define uma parte específica do horário. Se um gene estiver "mal codificado" (ex: um professor alocado em horário indisponível), isso causa um "defeito" no organismo final.

#### 2. **Horário Completo** - O Cromossomo completo

```
Um horário completo (CROMOSSOMO) contém:
- Todas as aulas organizadas (genes)
- Uma nota que diz quão bom é este horário (fitness)
```

**Analogia genética**: Assim como um cromossomo contém a sequência completa de genes que define um ser vivo, cada horário completo é uma "solução candidata" com todas as decisões (aulas) organizadas. Alguns cromossomos produzem organismos mais adaptados, assim como alguns horários funcionam melhor que outros.

#### 3. **Motor do Programa** - O Ambiente de Seleção Natural

Temos três versões com diferentes pressões evolutivas:

- **Versão Simples**: Para pequenas escolas (15 disciplinas) - como um ambiente simples com pouca pressão evolutiva
- **Versão Média**: Para faculdades médias (até 150 disciplinas) - um ambiente mais complexo
- **Versão Avançada**: Para grandes universidades (500+ disciplinas) - ambiente altamente competitivo que exige adaptações sofisticadas

**Analogia genética**: O motor do programa representa o ambiente que exerce pressão evolutiva. Quanto maior e mais complexa a universidade, mais "pressão" existe para encontrar soluções altamente otimizadas.

## 🔄 Como funciona passo a passo (com analogias à evolução)

### **Passo 1: Preparando o habitat natural**

```
O programa começa configurando o "ambiente":
- Quantas disciplinas, professores e salas existem
- Quais horários os professores estão disponíveis
- Quais salas comportam quais disciplinas
- Quais alunos estão matriculados em quais disciplinas
```

**Analogia evolutiva**: Este é como o estabelecimento das condições ambientais de um ecossistema - o clima, recursos, predadores, etc. - que determinarão quais características serão mais vantajosas para os organismos que viverão ali.

### **Passo 2: Criando a primeira geração (diversidade genética inicial)**

```
O programa cria vários horários iniciais de forma aleatória:
1. Para cada disciplina que precisa ser oferecida:
   - Escolhe um professor que pode ensinar aquela matéria
   - Escolhe uma sala adequada
   - Escolhe um horário disponível
   - Adiciona esta aula ao horário completo
2. Repete isso para criar centenas de horários diferentes
```

**Analogia evolutiva**: Este é como o "pool genético inicial" de uma espécie recém-surgida, com grande diversidade. Na natureza, a primeira geração de uma nova espécie apresenta grande variabilidade genética, assim como nossos primeiros horários são muito diversos, alguns bons, outros ruins.

### **Passo 3: Avaliando a aptidão de cada indivíduo**

```
Para cada horário criado, o programa dá uma nota baseada em:

1. DISCIPLINAS ALOCADAS (40% da nota)
   - Como um animal conseguir obter alimento suficiente

2. USO DOS HORÁRIOS (30% da nota)
   - Como um animal usar eficientemente sua energia

3. PROBLEMAS E CONFLITOS (30% da nota)
   - Como um animal evitar predadores e doenças
```

**Analogia evolutiva**: Na natureza, alguns animais são mais aptos que outros. Um guepardo mais rápido caça melhor; um camelo que armazena mais água sobrevive mais tempo no deserto. Nosso programa avalia cada horário para ver quais são "mais aptos" para o ambiente universitário.

### **Passo 4: A seleção natural em ação**

```
O programa vai melhorando os horários através de gerações sucessivas:

1. AVALIAÇÃO DE APTIDÃO
   - Como a natureza "avalia" quais organismos estão mais adaptados

2. SELEÇÃO NATURAL
   - Os horários mais bem adaptados têm maior probabilidade de passar seus "genes"
   - Exatamente como animais mais fortes/saudáveis têm mais chance de reprodução

3. REPRODUÇÃO SEXUAL
   - Combina partes de dois bons horários, assim como a reprodução sexual
   - Exatamente como filhos herdam características de ambos os pais

4. MUTAÇÃO GENÉTICA
   - Pequenas mudanças aleatórias, como mutações no DNA
   - Na natureza, mutações ocasionais trazem novidades para as espécies

5. SUCESSÃO DE GERAÇÕES
   - O processo continua por muitas gerações, cada vez com indivíduos mais adaptados
```

**Analogia evolutiva**: Este é o verdadeiro coração do processo evolutivo! Geração após geração, as características mais vantajosas vão se acumulando na população, enquanto as desvantajosas vão desaparecendo. É exatamente assim que surgiram todas as espécies que conhecemos hoje.

### **Passo 5: O "organismo" final - perfeitamente adaptado**

```
1. Escolhe o melhor horário evoluído após centenas de gerações
2. Verifica o grau de adaptação ao ambiente:
   - Nota > 8.5: Como uma espécie perfeitamente adaptada ao seu habitat
   - Nota > 6.5: Como uma espécie que sobrevive bem, mas não é dominante
   - Nota < 6.5: Como uma espécie em risco de extinção - precisa de ajustes

3. Produz o horário final - o "organismo" mais adaptado ao ambiente universitário
```

**Analogia evolutiva**: Assim como a evolução natural produziu espécies incrivelmente bem adaptadas aos seus nichos (pense em um beija-flor ou um tubarão), nosso algoritmo produz um horário perfeitamente adaptado às necessidades da universidade.

## 🧪 Como o programa "evolui" os horários

### � **Escolhendo os melhores horários**

```
É como um mini-torneio onde os melhores horários têm mais chance
de serem escolhidos para "passar seus genes" para a próxima geração.
```

### 🔄 **Misturando os bons horários**

```
Pegamos dois horários bons e criamos um novo, misturando suas aulas.
Por exemplo: 50% das aulas vêm do primeiro horário e 50% do segundo.
```

### 🎲 **Fazendo pequenas mudanças aleatórias**

```
Para não ficar preso em soluções semelhantes, às vezes o programa
faz pequenas alterações aleatórias, como mudar uma sala ou horário.
```

## 📊 Como sabemos se o horário é bom?

### **O que significa a nota do horário:**

- **9 a 10**: Excelente! Quase perfeito, sem conflitos
- **8 a 8.9**: Muito bom, pouquíssimos problemas
- **7 a 7.9**: Bom, com alguns conflitos aceitáveis
- **6 a 6.9**: Razoável, precisa de alguns ajustes
- **Abaixo de 6**: Problemático, muitos conflitos

### **O que é avaliado:**

1. **Disciplinas encaixadas (40%)**: Quantas matérias conseguimos encaixar no horário
2. **Uso equilibrado dos horários (30%)**: Se as aulas estão bem distribuídas na semana
3. **Ausência de conflitos (30%)**: Se não há professores ou salas com horários duplos

## 🚀 Como usar este programa

### **Versão Pequena (15 disciplinas):**

```
javac -d bin src/*.java
java -cp bin AlgoritmoGenetico
```

### **Versão Média (150+ disciplinas):**

```
java -cp bin AlgoritmoGeneticoCemDisciplinas
```

### **Versão Grande (500+ disciplinas):**

```
java -cp bin AlgoritmoGeneticoQuinhentas
```

## 📈 O que esperar dos resultados

- **Versão Pequena**: Nota ~9.3/10, todas as 15 disciplinas alocadas corretamente
- **Versão Média**: Nota ~9.0/10, 70-85% das disciplinas sem conflitos
- **Versão Grande**: Nota ~9.5/10, 85-99% das disciplinas sem conflitos

## � Curiosidades técnicas (para os interessados)

### **Como o programa consegue ser rápido**

- Usa processamento em paralelo (vários cálculos ao mesmo tempo)
- Tem técnicas especiais para não repetir cálculos já feitos
- Usa "atalhos inteligentes" para focar nas soluções mais promissoras

### **Por que este é um problema difícil**

O problema de agendamento é matematicamente classificado como "NP-difícil", o que significa que:

- Não existe fórmula direta para resolvê-lo
- O número de possibilidades cresce exponencialmente
- Para apenas 15 disciplinas, 5 professores, 4 salas e 20 horários, existem mais de 6 trilhões de combinações possíveis!

## � Aplicações práticas

Este programa pode ser usado em:

- **Universidades**: Para criar horários semestrais
- **Escolas**: Para organizar aulas de professores e turmas
- **Cursos livres**: Para otimizar uso de salas e instrutores

---

_Este programa demonstra como os computadores podem resolver problemas complexos inspirando-se na natureza - especificamente, no processo de evolução natural._
