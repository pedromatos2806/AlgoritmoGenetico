# Algoritmo Genético para Agendamento Universitário

## Estrutura do Projeto

### Classes Principais

1. **AlgoritmoGenetico**: Contém o algoritmo genético.
2. **DadosProblema**: Define os dados do problema, como disciplinas e professores.
3. **DisponibilidadeProfessor**: Gerencia a disponibilidade dos professores.
4. **AlunosPorDisciplina**: Gerencia os alunos matriculados em cada disciplina.
5. **Cromossomo**: Representa uma solução (cronograma).
6. **Aula**: Representa uma aula específica.

### Como Modificar os Dados

- Para alterar os dados do problema, edite as classes `DadosProblema`, `DisponibilidadeProfessor` ou `AlunosPorDisciplina`.
- O algoritmo genético na classe `AlgoritmoGenetico` permanece inalterado.

## Como Executar

1. Compile o projeto:
   ```bash
   javac -d bin src/*.java
   ```
2. Execute o programa:
   ```bash
   java -cp bin AlgoritmoGenetico
   ```
