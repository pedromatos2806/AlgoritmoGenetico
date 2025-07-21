# Algoritmo Genético para Agendamento Universitário

## Estrutura do Projeto

### Classes Principais

1. **AlgoritmoGenetico**: Contém as três versões do algoritmo genético.
   - `mainSimples()`: Versão para 15 disciplinas
   - `mainCem()`: Versão para 150 disciplinas
   - `mainQuinhentas()`: Versão para 500 disciplinas
2. **AlgoritmoGeneticoMain**: Classe principal para escolher qual versão executar.
3. **ConfigSimples**, **Config100**, **Config500**: Configurações específicas para cada versão.
4. **DadosProblema**: Define os dados do problema, como disciplinas e professores.
5. **DisponibilidadeProfessor**: Gerencia a disponibilidade dos professores.
6. **AlunosPorDisciplina**: Gerencia os alunos matriculados em cada disciplina.
7. **Cromossomo**: Representa uma solução (cronograma).
8. **Aula**: Representa uma aula específica.

### Versões do Algoritmo

1. **Versão Simples**: Para 15 disciplinas - Ideal para testes iniciais.
2. **Versão Escalável**: Para 150 disciplinas - Com otimizações de paralelismo.
3. **Versão Ultra Escalável**: Para 500+ disciplinas - Com otimizações extremas.

### Como Modificar os Dados

- Para alterar os dados do problema, edite as respectivas classes de configuração.
- O algoritmo genético na classe `AlgoritmoGenetico` permanece utilizando essas configurações.

## Como Executar

1. Compile o projeto:
   ```bash
   javac -d bin src/src/*.java
   ```
2. Execute a classe principal:
   ```bash
   java -cp bin src.AlgoritmoGeneticoMain
   ```
3. Selecione a versão desejada do algoritmo quando solicitado.

## Requisitos do Sistema

- Java 11 ou superior
- Pelo menos 4GB de RAM para a versão simples
- Pelo menos 8GB de RAM para a versão escalável
- Pelo menos 16GB de RAM para a versão ultra escalável
