package src;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import src.AlgoritmoGeneticoCemDisciplinas.CromossomoOtimizado;

/**
 * Algoritmo Genético Otimizado para Agendamento Universitário
 * 
 * OTIMIZAÇÕES PARA 100+ DISCIPLINAS:
 * - Paralelização usando threads
 * - Estruturas de dados mais eficientes
 * - Algoritmos de fitness otimizados
 * - Cache de resultados
 * - Elitismo adaptativo
 */
@SuppressWarnings("java:S106") // Suprime avisos sobre System.out em projeto educacional
public class AlgoritmoGeneticoCemDisciplinas {

    // ========== CONFIGURAÇÕES ESCALÁVEIS ==========
    static final int POPULACAO = 500; // Maior população para maior diversidade
    static final int GERACOES = 200; // Mais gerações para convergência
    static final double TAXA_MUTACAO = 0.05; // Taxa menor para preservar boas soluções
    static final double TAXA_CRUZAMENTO = 0.85; // Taxa maior para exploração
    static final int ELITE_SIZE = 50; // Número de melhores preservados
    static final int TOURNAMENT_SIZE = 5; // Tamanho do torneio para seleção

    // Configurações escaláveis do problema
    static final int NUM_DISCIPLINAS = 150; // 🎯 ESCALADO PARA 150 DISCIPLINAS
    static final int NUM_PROFESSORES = 30; // Mais professores necessários
    static final int NUM_SALAS = 20; // Mais salas necessárias
    static final int NUM_HORARIOS = 50; // 5 dias x 10 horários por dia
    static final int NUM_ALUNOS = 1000; // Mais alunos

    // ========== PARALELIZAÇÃO ==========
    static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    static final ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

    // ========== CACHE E OTIMIZAÇÕES ==========
    static final Map<String, Double> fitnessCache = new ConcurrentHashMap<>();
    static final Random random = ThreadLocalRandom.current();

    // ========== DADOS ESCALÁVEIS ==========
    static String[] nomesDisciplinas;
    static String[] nomesProfessores;
    static boolean[][] disponibilidadeProfessor;
    static int[][] disciplinasPorProfessor;
    static int[] capacidadeSalas;
    static List<Set<Integer>> alunosPorDisciplina;

    // ========== INICIALIZAÇÃO ESCALÁVEL ==========
    static {
        initializarDadosEscalaveis();
    }

    static void initializarDadosEscalaveis() {
        System.out.println("🚀 Inicializando dados escaláveis...");

        // Gerar nomes de disciplinas automaticamente
        nomesDisciplinas = new String[NUM_DISCIPLINAS];
        String[] areas = { "Computação", "Matemática", "Física", "Química", "Biologia",
                "Engenharia", "Administração", "Economia", "Psicologia", "História" };
        for (int i = 0; i < NUM_DISCIPLINAS; i++) {
            nomesDisciplinas[i] = areas[i % areas.length] + " " + (i / areas.length + 1);
        }

        // Gerar nomes de professores automaticamente
        nomesProfessores = new String[NUM_PROFESSORES];
        String[] sobrenomes = { "Silva", "Santos", "Oliveira", "Costa", "Souza", "Lima", "Pereira",
                "Ferreira", "Rodrigues", "Almeida", "Nascimento", "Carvalho" };
        for (int i = 0; i < NUM_PROFESSORES; i++) {
            nomesProfessores[i] = "Prof. " + sobrenomes[i % sobrenomes.length] +
                    (i / sobrenomes.length > 0 ? " " + (i / sobrenomes.length + 1) : "");
        }

        // Capacidades das salas
        capacidadeSalas = new int[NUM_SALAS];
        for (int i = 0; i < NUM_SALAS; i++) {
            capacidadeSalas[i] = 30 + random.nextInt(71); // 30-100 alunos por sala
        }

        // Disponibilidade dos professores (otimizada)
        disponibilidadeProfessor = new boolean[NUM_PROFESSORES][NUM_HORARIOS];
        for (int p = 0; p < NUM_PROFESSORES; p++) {
            for (int h = 0; h < NUM_HORARIOS; h++) {
                disponibilidadeProfessor[p][h] = random.nextDouble() > 0.3; // 70% disponível
            }
        }

        // Distribuição de disciplinas por professor (otimizada)
        disciplinasPorProfessor = new int[NUM_PROFESSORES][];
        int disciplinasPorProf = Math.max(3, NUM_DISCIPLINAS / NUM_PROFESSORES + 2);
        for (int p = 0; p < NUM_PROFESSORES; p++) {
            Set<Integer> disciplinas = new HashSet<>();
            while (disciplinas.size() < disciplinasPorProf && disciplinas.size() < NUM_DISCIPLINAS) {
                disciplinas.add(random.nextInt(NUM_DISCIPLINAS));
            }
            disciplinasPorProfessor[p] = disciplinas.stream().mapToInt(Integer::intValue).toArray();
        }

        // Distribuição de alunos por disciplina (otimizada)
        alunosPorDisciplina = new ArrayList<>(NUM_DISCIPLINAS);
        for (int d = 0; d < NUM_DISCIPLINAS; d++) {
            Set<Integer> alunos = new HashSet<>();
            int numAlunos = 20 + random.nextInt(31); // 20-50 alunos por disciplina
            while (alunos.size() < numAlunos) {
                alunos.add(random.nextInt(NUM_ALUNOS));
            }
            alunosPorDisciplina.add(alunos);
        }

        System.out.println("✅ Dados escaláveis inicializados!");
        System.out.printf("📊 %d disciplinas, %d professores, %d salas, %d horários%n",
                NUM_DISCIPLINAS, NUM_PROFESSORES, NUM_SALAS, NUM_HORARIOS);
    }

    // ========== CLASSE OTIMIZADA PARA CROMOSSOMO ==========
    static class CromossomoOtimizado {
        private final List<Aula> aulas;
        private Double fitness;
        private final String hash;

        public CromossomoOtimizado(List<Aula> aulas) {
            this.aulas = new ArrayList<>(aulas);
            this.hash = calcularHash();
        }

        public CromossomoOtimizado(CromossomoOtimizado outro) {
            this.aulas = new ArrayList<>();
            for (Aula aula : outro.aulas) {
                this.aulas.add(new Aula(aula.getDisciplina(), aula.getProfessor(),
                        aula.getSala(), aula.getHorario()));
            }
            this.hash = calcularHash();
        }

        private String calcularHash() {
            return aulas.stream()
                    .mapToInt(a -> Objects.hash(a.getDisciplina(), a.getProfessor(),
                            a.getSala(), a.getHorario()))
                    .sum() + "";
        }

        public List<Aula> getAulas() {
            return aulas;
        }

        public double getFitness() {
            if (fitness == null) {
                fitness = fitnessCache.computeIfAbsent(hash, k -> calcularFitnessOtimizado(this));
            }
            return fitness;
        }

        public void invalidarFitness() {
            fitness = null;
            fitnessCache.remove(hash);
        }
    }

    // ========== FUNÇÃO DE FITNESS OTIMIZADA ==========
    static double calcularFitnessOtimizado(CromossomoOtimizado cromossomo) {
        // Fitness normalizado para variar entre 0 e 1

        // 1. QUALIDADE DE ALOJAMENTO (40% do fitness)
        double qualidadeAlojamento = (double) cromossomo.getAulas().size() / NUM_DISCIPLINAS;

        // 2. QUALIDADE DE DISTRIBUIÇÃO (30% do fitness)
        Set<Integer> horariosUsados = new HashSet<>();

        for (Aula aula : cromossomo.getAulas()) {
            horariosUsados.add(aula.getHorario());
        }

        double qualidadeDistribuicao = (double) horariosUsados.size() / NUM_HORARIOS;

        // 3. PENALIZAÇÕES (30% do fitness)
        int conflitosTotal = 0;
        int maxConflitos = NUM_DISCIPLINAS * 10; // Aumentado para refletir penalizações reais

        // Estruturas para verificação rápida de conflitos
        Set<Integer>[][] ocupacao = new Set[NUM_HORARIOS][NUM_SALAS];
        Set<Integer>[] professorHorario = new Set[NUM_HORARIOS];

        for (int h = 0; h < NUM_HORARIOS; h++) {
            professorHorario[h] = new HashSet<>();
            for (int s = 0; s < NUM_SALAS; s++) {
                ocupacao[h][s] = new HashSet<>();
            }
        }

        for (Aula aula : cromossomo.getAulas()) {
            int disc = aula.getDisciplina();
            int prof = aula.getProfessor();
            int sala = aula.getSala();
            int hor = aula.getHorario();

            // Verificar se professor pode lecionar a disciplina
            boolean podeEnsinar = false;
            for (int d : disciplinasPorProfessor[prof]) {
                if (d == disc) {
                    podeEnsinar = true;
                    break;
                }
            }
            if (!podeEnsinar) {
                conflitosTotal += 6; // Penalização alta
                // NÃO usar continue - ainda verificar outros conflitos
            }

            // Verificar disponibilidade do professor
            if (!disponibilidadeProfessor[prof][hor]) {
                conflitosTotal += 5;
                // NÃO usar continue - ainda verificar outros conflitos
            }

            // Verificar conflito de professor
            if (!professorHorario[hor].add(prof)) {
                conflitosTotal += 4; // Professor já ocupado
            }

            // Verificar conflitos de alunos
            Set<Integer> alunosNaSala = ocupacao[hor][sala];
            Set<Integer> alunosDisciplina = alunosPorDisciplina.get(disc);

            for (int aluno : alunosDisciplina) {
                if (!alunosNaSala.add(aluno)) {
                    conflitosTotal += 1; // Conflito de aluno
                }
            }

            // Verificar capacidade física da sala
            if (alunosNaSala.size() > capacidadeSalas[sala]) {
                conflitosTotal += 3; // Sala superlotada
            }
        }

        // Normalizar penalizações
        double penalizacaoConflitos = Math.min(1.0, (double) conflitosTotal / maxConflitos);

        // CÁLCULO FINAL DO FITNESS (0 a 1)
        double fitness = (qualidadeAlojamento * 0.4) + (qualidadeDistribuicao * 0.3)
                + ((1.0 - penalizacaoConflitos) * 0.3);

        return Math.max(0.0, Math.min(1.0, fitness));
    }

    // ========== GERAÇÃO PARALELA DE POPULAÇÃO ==========
    static List<CromossomoOtimizado> gerarPopulacaoParalela() {
        System.out.println("🧬 Gerando população paralela...");

        List<CompletableFuture<CromossomoOtimizado>> futures = new ArrayList<>();

        for (int i = 0; i < POPULACAO; i++) {
            futures.add(CompletableFuture.supplyAsync(() -> gerarCromossomoAleatorioOtimizado(), executor));
        }

        List<CromossomoOtimizado> populacao = futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        System.out.printf("✅ %d cromossomos criados em paralelo%n", POPULACAO);
        return populacao;
    }

    static CromossomoOtimizado gerarCromossomoAleatorioOtimizado() {
        List<Aula> aulas = new ArrayList<>();
        Set<Integer> disciplinasUsadas = new HashSet<>();

        // Tentar alocar cada disciplina
        for (int d = 0; d < NUM_DISCIPLINAS; d++) {
            if (disciplinasUsadas.contains(d))
                continue;

            // Encontrar professores que podem ensinar esta disciplina
            List<Integer> professoresDisponiveis = new ArrayList<>();
            for (int p = 0; p < NUM_PROFESSORES; p++) {
                for (int disc : disciplinasPorProfessor[p]) {
                    if (disc == d) {
                        professoresDisponiveis.add(p);
                        break;
                    }
                }
            }

            if (professoresDisponiveis.isEmpty())
                continue;

            // Tentar várias vezes encontrar um horário válido
            for (int tentativa = 0; tentativa < 10; tentativa++) {
                int professor = professoresDisponiveis.get(random.nextInt(professoresDisponiveis.size()));
                int horario = random.nextInt(NUM_HORARIOS);
                int sala = random.nextInt(NUM_SALAS);

                if (disponibilidadeProfessor[professor][horario]) {
                    aulas.add(new Aula(d, professor, sala, horario));
                    disciplinasUsadas.add(d);
                    break;
                }
            }
        }

        return new CromossomoOtimizado(aulas);
    }

    // ========== EVOLUÇÃO PARALELA ==========
    static List<CromossomoOtimizado> evoluirPopulacaoParalela(List<CromossomoOtimizado> populacao) {
        // Calcular fitness em paralelo
        populacao.parallelStream().forEach(CromossomoOtimizado::getFitness);

        // Ordenar por fitness
        populacao.sort((a, b) -> Double.compare(b.getFitness(), a.getFitness()));

        List<CromossomoOtimizado> novaPopulacao = new ArrayList<>();

        // Elitismo - preservar os melhores
        for (int i = 0; i < ELITE_SIZE; i++) {
            novaPopulacao.add(new CromossomoOtimizado(populacao.get(i)));
        }

        // Gerar resto da população em paralelo
        List<CompletableFuture<CromossomoOtimizado>> futures = new ArrayList<>();

        for (int i = ELITE_SIZE; i < POPULACAO; i++) {
            futures.add(CompletableFuture.supplyAsync(() -> {
                CromossomoOtimizado pai1 = selecionarPorTorneio(populacao);
                CromossomoOtimizado pai2 = selecionarPorTorneio(populacao);
                CromossomoOtimizado filho = cruzarOtimizado(pai1, pai2);
                return mutarOtimizado(filho);
            }, executor));
        }

        futures.stream()
                .map(CompletableFuture::join)
                .forEach(novaPopulacao::add);

        return novaPopulacao;
    }

    static CromossomoOtimizado selecionarPorTorneio(List<CromossomoOtimizado> populacao) {
        CromossomoOtimizado melhor = null;
        for (int i = 0; i < TOURNAMENT_SIZE; i++) {
            CromossomoOtimizado candidato = populacao.get(random.nextInt(populacao.size()));
            if (melhor == null || candidato.getFitness() > melhor.getFitness()) {
                melhor = candidato;
            }
        }
        return melhor;
    }

    static CromossomoOtimizado cruzarOtimizado(CromossomoOtimizado pai1, CromossomoOtimizado pai2) {
        if (random.nextDouble() > TAXA_CRUZAMENTO) {
            return new CromossomoOtimizado(pai1);
        }

        List<Aula> aulasFilho = new ArrayList<>();
        Set<Integer> disciplinasUsadas = new HashSet<>();

        // Ponto de corte
        int corte = random.nextInt(Math.min(pai1.getAulas().size(), pai2.getAulas().size()));

        // Primeira parte do pai1
        for (int i = 0; i < corte && i < pai1.getAulas().size(); i++) {
            Aula aula = pai1.getAulas().get(i);
            if (disciplinasUsadas.add(aula.getDisciplina())) {
                aulasFilho.add(new Aula(aula.getDisciplina(), aula.getProfessor(),
                        aula.getSala(), aula.getHorario()));
            }
        }

        // Segunda parte do pai2
        for (Aula aula : pai2.getAulas()) {
            if (disciplinasUsadas.add(aula.getDisciplina())) {
                aulasFilho.add(new Aula(aula.getDisciplina(), aula.getProfessor(),
                        aula.getSala(), aula.getHorario()));
            }
        }

        return new CromossomoOtimizado(aulasFilho);
    }

    static CromossomoOtimizado mutarOtimizado(CromossomoOtimizado cromossomo) {
        if (random.nextDouble() > TAXA_MUTACAO) {
            return cromossomo;
        }

        List<Aula> aulas = new ArrayList<>(cromossomo.getAulas());

        if (!aulas.isEmpty()) {
            int index = random.nextInt(aulas.size());
            Aula aulaOriginal = aulas.get(index);

            // Tentar mutar para um estado válido
            for (int tentativa = 0; tentativa < 5; tentativa++) {
                int novoHorario = random.nextInt(NUM_HORARIOS);
                int novaSala = random.nextInt(NUM_SALAS);

                if (disponibilidadeProfessor[aulaOriginal.getProfessor()][novoHorario]) {
                    aulas.set(index, new Aula(aulaOriginal.getDisciplina(),
                            aulaOriginal.getProfessor(),
                            novaSala, novoHorario));
                    break;
                }
            }
        }

        CromossomoOtimizado mutado = new CromossomoOtimizado(aulas);
        mutado.invalidarFitness();
        return mutado;
    }

    // ========== ALGORITMO PRINCIPAL ==========
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        System.out.println("🎓 ALGORITMO GENÉTICO OTIMIZADO - AGENDAMENTO UNIVERSITÁRIO");
        System.out.println("Versão Escalável para 100+ Disciplinas");
        System.out.println("=========================================================");

        try {
            // Gerar população inicial
            List<CromossomoOtimizado> populacao = gerarPopulacaoParalela();

            // Evolução
            for (int geracao = 0; geracao < GERACOES; geracao++) {
                populacao = evoluirPopulacaoParalela(populacao);

                if (geracao % 20 == 0 || geracao == GERACOES - 1) {
                    double melhorFitness = populacao.get(0).getFitness();
                    double piorFitness = populacao.get(populacao.size() - 1).getFitness();
                    double diversidade = melhorFitness - piorFitness;
                    System.out.printf("Geração %3d - Melhor: %.2f | Pior: %.2f | Diversidade: %.3f%n",
                            geracao, melhorFitness, piorFitness, diversidade);
                }
            }

            // Resultado final
            CromossomoOtimizado melhor = populacao.get(0);
            long endTime = System.currentTimeMillis();

            System.out.println("\n🏆 MELHOR SOLUÇÃO ENCONTRADA:");
            System.out.printf("Fitness: %.2f%n", melhor.getFitness());
            System.out.printf("Tempo de execução: %.2f segundos%n", (endTime - startTime) / 1000.0);
            System.out.printf("Disciplinas alocadas: %d/%d (%.1f%%)%n",
                    melhor.getAulas().size(), NUM_DISCIPLINAS,
                    100.0 * melhor.getAulas().size() / NUM_DISCIPLINAS);

            // Salvar resultado
            salvarCronogramaOtimizado(melhor);

        } finally {
            executor.shutdown();
        }
    }

    static void salvarCronogramaOtimizado(CromossomoOtimizado cromossomo) {
        try (PrintWriter writer = new PrintWriter("cronograma_otimizado.txt")) {
            writer.println("CRONOGRAMA UNIVERSITÁRIO OTIMIZADO - EDUCAÇÃO AVANÇADA");
            writer.println("Gerado por Algoritmo Genético Escalável");
            writer.println("=====================================================");
            writer.println();

            Map<Integer, List<Aula>> aulasPorHorario = cromossomo.getAulas().stream()
                    .collect(Collectors.groupingBy(Aula::getHorario));

            String[] dias = { "Segunda", "Terça", "Quarta", "Quinta", "Sexta" };
            int horariosPerDay = NUM_HORARIOS / 5;

            for (int dia = 0; dia < 5; dia++) {
                writer.printf("\n=== %s ===%n", dias[dia].toUpperCase());

                for (int hora = 0; hora < horariosPerDay; hora++) {
                    int horarioId = dia * horariosPerDay + hora;
                    writer.printf("%02d:00--%02d:00:%n", 8 + hora * 2, 10 + hora * 2);

                    List<Aula> aulas = aulasPorHorario.getOrDefault(horarioId, Collections.emptyList());
                    if (aulas.isEmpty()) {
                        writer.println("  (sem aulas)");
                    } else {
                        for (Aula aula : aulas) {
                            writer.printf("  %s - %s (Sala %d)%n",
                                    nomesDisciplinas[aula.getDisciplina()],
                                    nomesProfessores[aula.getProfessor()],
                                    aula.getSala() + 1);
                        }
                    }
                }
            }

            System.out.println("💾 Cronograma otimizado salvo em 'cronograma_otimizado.txt'");

        } catch (IOException e) {
            System.err.println("Erro ao salvar arquivo: " + e.getMessage());
        }
    }
}
