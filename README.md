# 🧬 Algoritmo Genético - Agendamento Universitário

## 🎯 **O Problema**

Este projeto resolve o **problema de agendamento universitário**, uma tarefa computacionalmente complexa que envolve:

- **15 disciplinas** que precisam ser alocadas
- **6 professores** com especialidades específicas
- **4 salas** de aula disponíveis
- **20 horários** semanais (Segunda a Quinta, 5 horários/dia)
- **100 alunos** matriculados em múltiplas disciplinas

### **Desafios:**

- ❌ **Conflitos de professor:** Um professor não pode estar em dois lugares ao mesmo tempo
- ❌ **Conflitos de sala:** Uma sala não pode ter duas aulas simultâneas
- ❌ **Conflitos de alunos:** Alunos não podem ter aulas conflitantes
- ❌ **Restrições de especialidade:** Professores só podem lecionar disciplinas de sua área
- ❌ **Disponibilidade:** Professores têm horários preferenciais

---

## 🧬 **Por Que É Um Algoritmo Genético?**

Este algoritmo é **genético** porque segue os princípios da **evolução biológica**:

### **1. População de Soluções**

```
30 cronogramas diferentes evoluindo simultaneamente
(não apenas uma solução sendo melhorada)
```

### **2. Representação Cromossômica**

```
Cada cronograma = Cromossomo
Cada aula = Gene
Cada parâmetro da aula = Alelo
```

### **3. Operadores Evolutivos**

```
SELEÇÃO → Melhores cronogramas têm mais chance de "reproduzir"
CRUZAMENTO → Combina características de 2 cronogramas "pais"
MUTAÇÃO → Alterações aleatórias para manter diversidade
ELITISMO → Preserva os melhores cronogramas
```

### **4. Evolução Por Gerações**

```
Geração 0 → Geração 1 → Geração 2 → ... → Geração 99
Qualidade média melhora ao longo do tempo
```

### **5. Fitness (Aptidão)**

```
Cada cronograma recebe uma "nota" baseada em:
- Quantas disciplinas conseguiu alocar
- Quantos conflitos tem
- Quão bem distribuído está
```

---

## 📚 **Documentação das Classes**

### **1. `AlgoritmoGenetico.java` - Classe Principal**

**O que faz:** Coordena todo o processo evolutivo

**Principais métodos:**

```java
main() → Executa o algoritmo completo
gerarPopulacaoInicial() → Cria 30 cronogramas aleatórios
calcularFitness() → Avalia qualidade de cada cronograma
selecionarPai() → Escolhe cronogramas para reprodução
cruzar() → Combina dois cronogramas pais
mutar() → Faz alterações aleatórias
```

**Por que é importante:** É o "motor evolutivo" que simula a seleção natural

### **2. `Cromossomo.java` - Solução Completa**

**O que representa:** Um cronograma universitário completo

**Estrutura:**

```java
class Cromossomo {
    List<Aula> aulas;    // Lista de todas as aulas alocadas
    double fitness;      // Qualidade desta solução (0-150)
}
```

**Analogia biológica:**

```
Organismo vivo = Cromossomo (cronograma)
DNA = Lista de aulas
Aptidão = Fitness (quão bem sobrevive no ambiente)
```

**Por que é importante:** Representa uma solução candidata que pode evoluir

### **3. `Aula.java` - Gene Individual**

**O que representa:** Uma única aula alocada

**Estrutura:**

```java
class Aula {
    int disciplina;  // Qual disciplina (0-14)
    int professor;   // Qual professor (0-5)
    int sala;        // Qual sala (0-3)
    int horario;     // Qual horário (0-19)
}
```

**Analogia biológica:**

```
Gene = Aula individual
Alelos = Valores específicos (disciplina=5, professor=2, etc.)
Expressão do gene = Aula concreta no cronograma
```

**Por que é importante:** É a unidade básica de informação genética que pode ser herdada e mutada

### **4. `TesteSimples.java` - Verificação**

**O que faz:** Testa se o ambiente Java está funcionando

**Por que é importante:** Garante que o projeto pode ser executado antes de rodar o algoritmo principal

---

## 🔄 **Como o Algoritmo Resolve o Problema**

### **Passo 1: Inicialização**

```
1. Define dados do problema (disciplinas, professores, salas, horários)
2. Gera 30 cronogramas aleatórios (população inicial)
3. Calcula fitness de cada cronograma
```

### **Passo 2: Evolução (100 gerações)**

```
Para cada geração:
├── 1. Avalia fitness de todos os cronogramas
├── 2. Ordena por qualidade (melhor primeiro)
├── 3. Mantém os 3 melhores (elitismo)
├── 4. Gera nova população:
│   ├── Seleciona "pais" por torneio
│   ├── Cruza pais para gerar "filhos"
│   ├── Muta alguns filhos aleatoriamente
│   └── Substitui população antiga
└── 5. Repete até convergir
```

### **Passo 3: Resultado**

```
1. Seleciona o melhor cronograma da última geração
2. Exibe cronograma otimizado
3. Salva resultado em arquivo
```

---

## 📊 **Função de Fitness (Como Avalia Qualidade)**

Cada cronograma recebe uma pontuação baseada em:

### **Base Inicial:**

```
+100 pontos → Valor base inicial
```

### **Bonificações (+pontos):**

```
+2 pontos → Por cada disciplina alocada (máximo +30)
+0.5 pontos → Por cada horário utilizado (máximo +10)
```

### **Penalizações (-pontos):**

```
-20 pontos → Por conflito de professor
-15 pontos → Por conflito de sala
-25 pontos → Por professor indisponível
-0.5 pontos → Por cada aluno em conflito
```

### **Range de Fitness:**

#### **💯 MÁXIMO TEÓRICO: ~140 pontos**

```
Base inicial:           +100.00
15 disciplinas:         + 30.00  (15 × 2)
20 horários usados:     + 10.00  (20 × 0.5)
Sem conflitos:          +  0.00

TOTAL MÁXIMO:           140.00
```

#### **❌ MÍNIMO TEÓRICO: 0 pontos**

```
(Fitness nunca fica negativo - limitado a 0)

Cenário extremo:
Base:                   +100.00
Nenhuma disciplina:     +  0.00
Muitos conflitos:       -150.00+
→ Resultado limitado a 0.00
```

#### **📊 RANGES TÍPICOS OBSERVADOS:**

```
🔴 RUIM (50-90):      Muitos conflitos, poucas disciplinas
🟡 REGULAR (90-120):  Algumas disciplinas, conflitos moderados
🟢 BOM (120-135):     Maioria disciplinas, poucos conflitos
🏆 EXCELENTE (135+):  Todas disciplinas, conflitos mínimos
```

### **Exemplo de Cálculo:**

```
Cronograma com 15 disciplinas, alguns conflitos:

Base:                     +100.00
Disciplinas alocadas:     + 30.00  (15 × 2)
Horários utilizados:      + 10.00  (20 × 0.5)
Conflitos professor:      - 40.00  (2 × 20)
Conflitos sala:           - 15.00  (1 × 15)
Professor indisponível:   - 25.00  (1 × 25)
Conflitos alunos:         - 10.00  (20 × 0.5)

FITNESS FINAL:            50.00
```

---

## 🎯 **Vantagens do Algoritmo Genético**

### **1. Robustez**

- Funciona mesmo com dados imperfeitos
- Não fica "preso" em soluções ruins
- Adapta-se a mudanças nos dados

### **2. Qualidade das Soluções**

- Encontra soluções muito boas rapidamente
- Equilibra múltiplos objetivos simultâneos
- Evita ótimos locais através da população

### **3. Flexibilidade**

- Fácil adicionar novas restrições
- Parâmetros ajustáveis para diferentes problemas
- Pode ser paralelizado naturalmente

### **4. Inspiração Natural**

- Usa princípios evolutivos testados por milhões de anos
- Comportamento emergente (soluções surgem naturalmente)
- Balança exploração (diversidade) com exploitation (melhoria)

---

## 📁 **Estrutura do Projeto**

```
AlgoritmoGenetico_02/
├── src/
│   ├── AlgoritmoGenetico.java ← MOTOR EVOLUTIVO 🧬
│   ├── Aula.java             ← GENE (unidade básica)
│   ├── Cromossomo.java       ← ORGANISMO (solução completa)
│   └── TesteSimples.java     ← VERIFICAÇÃO
├── bin/                      ← Classes compiladas
├── .project                  ← Configuração Eclipse
├── .classpath                ← Configuração Eclipse
├── .settings/                ← Configuração Eclipse
├── cronograma_simples.txt    ← RESULTADO final
├── DOCUMENTACAO_COMPLETA.md  ← Documentação técnica detalhada
└── README.md                 ← Este arquivo
```

---

## 🚀 **Como Executar**

### **Opção 1: Eclipse IDE**

1. **File** → **Import** → **Existing Projects into Workspace**
2. Selecione a pasta `AlgoritmoGenetico_02`
3. Clique direito em `AlgoritmoGenetico.java` → **Run As** → **Java Application**

### **Opção 2: Terminal**

```bash
cd AlgoritmoGenetico_02
javac -d bin src/*.java
java -cp bin AlgoritmoGenetico
```

---

## 📊 **Resultado Esperado**

```
🎓 ALGORITMO GENÉTICO PARA AGENDAMENTO UNIVERSITÁRIO
Universidade: Educação Avançada
=================================================
📊 Inicializando dados...
✓ 15 disciplinas, 6 professores, 4 salas, 20 horários
🧬 Gerando população inicial...
✓ 30 cromossomos criados

Geração   0 - Melhor fitness: 133,50
Geração  20 - Melhor fitness: 137,50
Geração  40 - Melhor fitness: 137,50
Geração  60 - Melhor fitness: 137,50
Geração  80 - Melhor fitness: 137,50
Geração  99 - Melhor fitness: 137,50

🏆 MELHOR SOLUÇÃO ENCONTRADA:
Fitness: 137,50

📅 CRONOGRAMA OTIMIZADO:
========================
SEGUNDA:
  08:00: IA (Prof. Santos, Sala 4)
  10:00: Algoritmos (Prof. Silva, Sala 3)
  14:00: Administração (Prof. Souza, Sala 2)
  16:00: Economia (Prof. Souza, Sala 1)

TERÇA:
  08:00: Física I (Prof. Costa, Sala 2)
  10:00: Inglês (Prof. Costa, Sala 4)
  14:00: Cálculo I (Prof. Oliveira, Sala 1)
  18:00: Banco de Dados (Prof. Silva, Sala 3)

... (cronograma completo)

📊 ESTATÍSTICAS:
• Disciplinas alocadas: 15/15 (100%)
• Fitness: 137,50
• Conflitos: Nenhum grave

💾 Resultado salvo em 'cronograma_simples.txt'
```

---

## ✅ **Verificação de Funcionamento**

Execute `TesteSimples.java` para verificar se tudo está funcionando:

```
✅ Teste simples funcionando!
Java version: 22.0.2
OS: Windows 11
Projeto: Algoritmo Genético - Agendamento Universitário
Status: Pronto para executar no Eclipse! 🎉
```

---

## 🎓 **Conclusão**

Este projeto demonstra como **Algoritmos Genéticos** podem resolver problemas complexos de otimização combinatória de forma eficiente e elegante, usando princípios da evolução biológica para encontrar soluções de alta qualidade em espaços de busca imensos.

**O resultado é um cronograma universitário otimizado, gerado automaticamente em segundos, respeitando todas as restrições e minimizando conflitos.** 🎯
#   A l g o r i t m o G e n e t i c o  
 