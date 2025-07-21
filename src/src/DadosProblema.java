package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DadosProblema {

    public static final int NUM_DISCIPLINAS = 15;
    public static final int NUM_PROFESSORES = 6;
    public static final int NUM_SALAS = 4;
    public static final int NUM_HORARIOS = 20;
    public static final int NUM_ALUNOS = 100;

    protected static final String[] nomesDisciplinas = {
            "Algoritmos", "Banco de Dados", "Redes", "IA", "Compiladores",
            "Sistemas", "POO", "Cálculo I", "Cálculo II", "Álgebra",
            "Física I", "Inglês", "Administração", "Economia", "Estatística"
    };

    protected static final String[] nomesProfessores = {
            "Prof. Silva", "Prof. Santos", "Prof. Oliveira",
            "Prof. Costa", "Prof. Souza", "Prof. Lima"
    };

    protected static final int[][] disciplinasPorProfessor = {
            { 0, 1, 2 },
            { 3, 4, 5 },
            { 6, 7, 8 },
            { 9, 10, 11 },
            { 12, 13, 14 },
            { 0, 3, 6, 9, 12 }
    };

    protected static final List<List<Integer>> alunosPorDisciplina = new ArrayList<>();
    private static final Random random = new Random();

    private DadosProblema() {
        // Construtor privado para evitar instância
    }

    public static void inicializarAlunosPorDisciplina() {
        alunosPorDisciplina.clear();
        for (int d = 0; d < NUM_DISCIPLINAS; d++) {
            List<Integer> alunosNaDisciplina = new ArrayList<>();
            int numAlunos = 20 + random.nextInt(21);
            for (int i = 0; i < numAlunos; i++) {
                int alunoId = random.nextInt(NUM_ALUNOS);
                if (!alunosNaDisciplina.contains(alunoId)) {
                    alunosNaDisciplina.add(alunoId);
                }
            }
            alunosPorDisciplina.add(alunosNaDisciplina);
        }
    }

    public static Cromossomo gerarCromossomoAleatorio() {
        Cromossomo cromossomo = new Cromossomo();
        for (int i = 0; i < NUM_DISCIPLINAS; i++) {
            int professor = random.nextInt(NUM_PROFESSORES);
            int sala = random.nextInt(NUM_SALAS);
            int horario = random.nextInt(NUM_HORARIOS);
            cromossomo.adicionarAula(new Aula(i, professor, sala, horario));
        }
        return cromossomo;
    }

    public static double calcularFitnessCromossomo(Cromossomo cromossomo) {
        double qualidadeAlojamento = (double) cromossomo.getAulas().size() / NUM_DISCIPLINAS;
        double qualidadeDistribuicao = cromossomo.getAulas().stream()
                .map(Aula::getHorario)
                .distinct()
                .count() / (double) NUM_HORARIOS;

        int conflitosTotal = cromossomo.getAulas().stream()
                .mapToInt(aula -> {
                    int conflitos = 0;
                    if (!DisponibilidadeProfessor.isDisponivel(aula.getProfessor(), aula.getHorario()))
                        conflitos += 4;
                    return conflitos;
                }).sum();

        double penalizacaoConflitos = Math.min(1.0, (double) conflitosTotal / (NUM_DISCIPLINAS * 3));
        return Math.max(0.0, Math.min(1.0, 
                (qualidadeAlojamento * 0.4) + (qualidadeDistribuicao * 0.3) + ((1.0 - penalizacaoConflitos) * 0.3)));
    }

    public static Cromossomo cruzar(Cromossomo pai, Cromossomo mae) {
        Cromossomo filho = new Cromossomo();
        int tamanhoSegmento = Math.max(1, pai.getAulas().size() / 10);
        boolean alternar = true;
        for (int i = 0; i < 10; i++) {
            int inicio = i * tamanhoSegmento;
            int fim = Math.min((i + 1) * tamanhoSegmento, pai.getAulas().size());

            List<Aula> aulasSegmento = alternar ? pai.getAulas().subList(inicio, fim)
                    : mae.getAulas().subList(inicio, fim);
            aulasSegmento.forEach(aula -> filho.adicionarAula(
                    new Aula(aula.getDisciplina(), aula.getProfessor(), aula.getSala(), aula.getHorario())));
            alternar = !alternar;
        }
        return filho;
    }

    public static void mutar(Cromossomo cromossomo) {
        if (cromossomo.isEmpty())
            return;
        Aula aula = cromossomo.getAulas().get(random.nextInt(cromossomo.getAulas().size()));
        if (random.nextBoolean()) {
            aula.setSala(random.nextInt(NUM_SALAS));
        } else {
            aula.setHorario(random.nextInt(NUM_HORARIOS));
        }
    }

    public static void imprimirCronograma(Cromossomo cromossomo) {
        String[] dias = { "Segunda", "Terça", "Quarta", "Quinta" };
        String[] horarios = { "08:00", "10:00", "14:00", "16:00", "18:00" };
        Map<Integer, List<Aula>> aulasPorHorario = new HashMap<>();
        cromossomo.getAulas()
                .forEach(aula -> aulasPorHorario.computeIfAbsent(aula.getHorario(), k -> new ArrayList<>()).add(aula));

        for (int dia = 0; dia < 4; dia++) {
            System.out.println("\n" + dias[dia].toUpperCase() + ":");
            for (int hora = 0; hora < 5; hora++) {
                int horarioId = dia * 5 + hora;
                System.out.print("  " + horarios[hora] + ": ");
                List<Aula> aulas = aulasPorHorario.get(horarioId);
                if (aulas != null && !aulas.isEmpty()) {
                    aulas.forEach(aula -> System.out.print(String.format("%s (%s, Sala %d) | ",
                            nomesDisciplinas[aula.getDisciplina()],
                            nomesProfessores[aula.getProfessor()],
                            aula.getSala() + 1)));
                } else {
                    System.out.print("(livre)");
                }
                System.out.println();
            }
        }
    }
}

