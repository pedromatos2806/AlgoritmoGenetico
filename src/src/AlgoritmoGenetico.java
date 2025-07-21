package src;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Algoritmo Genético para Agendamento Universitário
 * 
 * Classe principal com três versões do algoritmo:
 * 1. Versão Simples: para 15 disciplinas
 * 2. Versão Escalável: para 100+ disciplinas
 * 3. Versão Ultra Escalável: para 500+ disciplinas
 */
public class AlgoritmoGenetico {

    // ========== ALGORITMO PARA 15 DISCIPLINAS ==========
    
    public static void main(String[] args) {
        mainSimples(args);
    }
    
    public static void mainSimples(String[] args) {
        long startTime = System.currentTimeMillis();
        Random random = new Random();
        
        System.out.println("🎓 ALGORITMO GENÉTICO PARA AGENDAMENTO UNIVERSITÁRIO");
        System.out.println("Versão Simples: 15 Disciplinas");
        System.out.println("=================================================");

        // Inicializar dados
        int numDisciplinas = ConfigSimples.NUM_DISCIPLINAS;
        int numProfessores = ConfigSimples.NUM_PROFESSORES;
        int numHorarios = ConfigSimples.NUM_HORARIOS;
        int numAlunos = ConfigSimples.NUM_ALUNOS;
        int populacao = ConfigSimples.POPULACAO;
        int geracoes = ConfigSimples.GERACOES;
        double taxaMutacao = ConfigSimples.TAXA_MUTACAO;
        double taxaCruzamento = ConfigSimples.TAXA_CRUZAMENTO;
        
        DadosProblema.inicializarAlunosPorDisciplina();
        DisponibilidadeProfessor.inicializarDisponibilidade(numProfessores, numHorarios);

        // Gerar população inicial
        List<Cromossomo> populacaoInicial = new ArrayList<>();
        for (int i = 0; i < populacao; i++) {
            populacaoInicial.add(DadosProblema.gerarCromossomoAleatorio());
        }
        System.out.printf("✓ %d cromossomos criados%n", populacao);

        // Usar uma nova variável em cada iteração para torná-la efetivamente final
        List<Cromossomo> populacaoAtual = populacaoInicial;

        // Evoluir por várias gerações
        for (int geracao = 0; geracao < geracoes; geracao++) {
            // Criar uma nova referência para a população atual, tornando-a efetivamente final nesta iteração
            final List<Cromossomo> populacaoAtualFinal = populacaoAtual;
            
            // Calcular fitness de todos
            for (Cromossomo cromossomo : populacaoAtualFinal) {
                cromossomo.setFitness(DadosProblema.calcularFitnessCromossomo(cromossomo));
            }

            // Ordenar por fitness (melhor primeiro)
            populacaoAtualFinal.sort((c1, c2) -> Double.compare(c2.getFitness(), c1.getFitness()));

            // Mostrar progresso
            if (geracao % 20 == 0 || geracao == geracoes - 1) {
                double melhorFitness = populacaoAtualFinal.get(0).getFitness();
                double piorFitness = populacaoAtualFinal.get(populacaoAtualFinal.size() - 1).getFitness();
                System.out.printf("Geração %3d - Melhor fitness: %.2f | Pior fitness: %.2f%n",
                        geracao, melhorFitness, piorFitness);
            }

            // Criar nova população
            List<Cromossomo> novaPopulacao = new ArrayList<>();

            // Manter os 10% melhores (elitismo)
            int elites = populacao / 10;
            for (int i = 0; i < elites; i++) {
                novaPopulacao.add(new Cromossomo(populacaoAtualFinal.get(i)));
            }

            // Gerar o resto por cruzamento e mutação
            while (novaPopulacao.size() < populacao) {
                Cromossomo pai = selecionarPorTorneio(populacaoAtualFinal, 3, random);
                Cromossomo mae = selecionarPorTorneio(populacaoAtualFinal, 3, random);

                Cromossomo filho;
                if (random.nextDouble() < taxaCruzamento) {
                    filho = DadosProblema.cruzar(pai, mae);
                } else {
                    filho = new Cromossomo(pai);
                }

                if (random.nextDouble() < taxaMutacao) {
                    DadosProblema.mutar(filho);
                }

                novaPopulacao.add(filho);
            }

            // Atualizar para a próxima iteração
            populacaoAtual = novaPopulacao;
        }

        // Mostrar melhor solução - usar a versão final da população
        for (Cromossomo c : populacaoAtual) {
            c.setFitness(DadosProblema.calcularFitnessCromossomo(c));
        }
        populacaoAtual.sort((c1, c2) -> Double.compare(c2.getFitness(), c1.getFitness()));

        long endTime = System.currentTimeMillis();
        double tempoExecucao = (endTime - startTime) / 1000.0;
        
        System.out.println("\n🏆 MELHOR SOLUÇÃO ENCONTRADA:");
        System.out.println("Fitness: " + String.format("%.2f", populacaoAtual.get(0).getFitness()));
        System.out.printf("Tempo de execução: %.2f segundos%n", tempoExecucao);
        DadosProblema.imprimirCronograma(populacaoAtual.get(0));

        // Salvar resultado
        salvarResultado(populacaoAtual.get(0), "cronograma_simples.txt", DadosProblema.nomesDisciplinas, 
                DadosProblema.nomesProfessores);
    }

    // ========== ALGORITMO PARA 100+ DISCIPLINAS ==========
    
    public static void mainCem(String[] args) {
        long startTime = System.currentTimeMillis();
        
        System.out.println("🎓 ALGORITMO GENÉTICO OTIMIZADO - AGENDAMENTO UNIVERSITÁRIO");
        System.out.println("Versão Escalável para 100+ Disciplinas");
        System.out.println("=========================================================");

        // Carregar configurações para 100+ disciplinas
        int numDisciplinas = Config100.NUM_DISCIPLINAS;
        int numProfessores = Config100.NUM_PROFESSORES;
        int numSalas = Config100.NUM_SALAS;
        int numHorarios = Config100.NUM_HORARIOS;
        int numAlunos = Config100.NUM_ALUNOS;
        int populacao = Config100.POPULACAO;
        int geracoes = Config100.GERACOES;
        double taxaMutacao = Config100.TAXA_MUTACAO;
        double taxaCruzamento = Config100.TAXA_CRUZAMENTO;
        int eliteSize = Config100.ELITE_SIZE;
        int tournamentSize = Config100.TOURNAMENT_SIZE;

        // Inicializar dados escaláveis
        String[] nomesDisciplinas = gerarNomesDisciplinas(numDisciplinas);
        String[] nomesProfessores = gerarNomesProfessores(numProfessores);
        int[] capacidadeSalas = gerarCapacidadeSalas(numSalas);
        boolean[][] disponibilidadeProfessor = gerarDisponibilidadeProfessores(numProfessores, numHorarios);
        int[][] disciplinasPorProfessor = gerarDisciplinasPorProfessor(numProfessores, numDisciplinas);
        List<Set<Integer>> alunosPorDisciplina = gerarAlunosPorDisciplina(numDisciplinas, numAlunos);
        
        // Inicializar execução paralela
        final int numThreads = Runtime.getRuntime().availableProcessors();
        final ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        final Map<String, Double> fitnessCache = new ConcurrentHashMap<>();
        
        try {
            // Gerar população inicial em paralelo
            System.out.println("🧬 Gerando população paralela...");
            List<CompletableFuture<CromossomoOtimizado>> futures = new ArrayList<>();
            
            for (int i = 0; i < populacao; i++) {
                futures.add(CompletableFuture.supplyAsync(() -> 
                    gerarCromossomoAleatorioOtimizado(numDisciplinas, numProfessores, numSalas, 
                        numHorarios, disciplinasPorProfessor, disponibilidadeProfessor), 
                    executor));
            }
            
            List<CromossomoOtimizado> populacaoInicial = futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
            
            System.out.printf("✅ %d cromossomos criados em paralelo%n", populacao);
            
            // Evolução
            List<CromossomoOtimizado> populacaoAtual = populacaoInicial;
            
            for (int geracao = 0; geracao < geracoes; geracao++) {
                // Criar uma referência final para uso em lambda
                final List<CromossomoOtimizado> populacaoAtualFinal = populacaoAtual;
                
                // Calcular fitness em paralelo
                populacaoAtualFinal.parallelStream().forEach(c -> 
                    calcularFitnessOtimizado(c, numDisciplinas, numHorarios, capacidadeSalas, 
                        disciplinasPorProfessor, disponibilidadeProfessor, 
                        alunosPorDisciplina, fitnessCache));
                
                // Ordenar por fitness
                populacaoAtualFinal.sort((a, b) -> Double.compare(b.getFitness(), a.getFitness()));
                
                if (geracao % 20 == 0 || geracao == geracoes - 1) {
                    double melhorFitness = populacaoAtualFinal.get(0).getFitness();
                    double piorFitness = populacaoAtualFinal.get(populacaoAtualFinal.size() - 1).getFitness();
                    double diversidade = melhorFitness - piorFitness;
                    System.out.printf("Geração %3d - Melhor: %.2f | Pior: %.2f | Diversidade: %.3f%n",
                            geracao, melhorFitness, piorFitness, diversidade);
                }
                
                List<CromossomoOtimizado> novaPopulacao = new ArrayList<>();
                
                // Elitismo - preservar os melhores
                for (int i = 0; i < eliteSize; i++) {
                    novaPopulacao.add(new CromossomoOtimizado(populacaoAtualFinal.get(i)));
                }
                
                // Gerar resto da população em paralelo
                List<CompletableFuture<CromossomoOtimizado>> futuresNovos = new ArrayList<>();
                
                for (int i = eliteSize; i < populacao; i++) {
                    futuresNovos.add(CompletableFuture.supplyAsync(() -> {
                        CromossomoOtimizado pai1 = selecionarPorTorneioOtimizado(populacaoAtualFinal, tournamentSize);
                        CromossomoOtimizado pai2 = selecionarPorTorneioOtimizado(populacaoAtualFinal, tournamentSize);
                        CromossomoOtimizado filho = cruzarOtimizado(pai1, pai2, taxaCruzamento);
                        return mutarOtimizado(filho, taxaMutacao, numHorarios, numSalas, disponibilidadeProfessor);
                    }, executor));
                }
                
                futuresNovos.stream()
                    .map(CompletableFuture::join)
                    .forEach(novaPopulacao::add);
                
                populacaoAtual = novaPopulacao;
            }
            
            // Resultado final
            CromossomoOtimizado melhor = populacaoAtual.get(0);
            long endTime = System.currentTimeMillis();
            
            System.out.println("\n🏆 MELHOR SOLUÇÃO ENCONTRADA:");
            System.out.printf("Fitness: %.2f%n", melhor.getFitness());
            System.out.printf("Tempo de execução: %.2f segundos%n", (endTime - startTime) / 1000.0);
            System.out.printf("Disciplinas alocadas: %d/%d (%.1f%%)%n",
                    melhor.getAulas().size(), numDisciplinas,
                    100.0 * melhor.getAulas().size() / numDisciplinas);
            
            // Salvar resultado
            salvarCronogramaOtimizado(melhor, nomesDisciplinas, nomesProfessores, numHorarios);
            
        } finally {
            executor.shutdown();
        }
    }
    
    // ========== ALGORITMO PARA 500+ DISCIPLINAS ==========
    
    public static void mainQuinhentas(String[] args) {
        long startTime = System.currentTimeMillis();
        
        System.out.println("🚀 ALGORITMO GENÉTICO ULTRA ESCALÁVEL");
        System.out.println("Versão para 500+ Disciplinas");
        System.out.println("============================");
        
        // Carregar configurações para 500+ disciplinas
        int numDisciplinas = Config500.NUM_DISCIPLINAS;
        int numProfessores = Config500.NUM_PROFESSORES;
        int numSalas = Config500.NUM_SALAS;
        int numHorarios = Config500.NUM_HORARIOS;
        int populacaoSize = Config500.POPULACAO;
        int geracoes = Config500.GERACOES;
        int sampleSize = Config500.SAMPLE_SIZE;
        int eliteSize = Config500.ELITE_SIZE;
        
        // Pool de threads para paralelismo extremo
        final ForkJoinPool FORK_JOIN_POOL = new ForkJoinPool(Runtime.getRuntime().availableProcessors() * 2);
        
        // Cache otimizado para fitness
        final Map<String, Double> fitnessCache = Collections.synchronizedMap(new LinkedHashMap<String, Double>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Double> eldest) {
                return size() > Config500.MAX_CACHE_SIZE;
            }
        });
        
        try {
            // Inicialização inteligente
            System.out.println("🧠 Inicializando com heurísticas inteligentes...");
            System.out.printf("✅ %d disciplinas configuradas%n", numDisciplinas);
            
            // População inicial com heurística
            System.out.println("🎯 Gerando população com heurísticas...");
            List<CromossomoUltra> populacao = FORK_JOIN_POOL.submit(() -> 
                IntStream.range(0, populacaoSize)
                    .parallel()
                    .mapToObj(i -> gerarCromossomoHeuristico(numDisciplinas, numProfessores, numSalas, numHorarios))
                    .collect(Collectors.toList())).join();
                    
            // Evolução com otimizações extremas
            for (int geracao = 0; geracao < geracoes; geracao++) {
                // Criando uma cópia efetivamente final da população para uso no lambda
                final List<CromossomoUltra> populacaoFinal = populacao;
                
                // Avaliar fitness em paralelo (por amostragem)
                populacaoFinal.parallelStream().forEach(c -> calcularFitnessPorAmostragem(c, numDisciplinas, 
                    numHorarios, sampleSize));
                
                // Ordenar
                populacaoFinal.sort((a, b) -> Double.compare(b.getFitnessSample(), a.getFitnessSample()));
                
                if (geracao % 50 == 0 || geracao == geracoes - 1) {
                    double melhorFitness = populacaoFinal.get(0).getFitnessSample();
                    double piorFitness = populacaoFinal.get(populacaoFinal.size() - 1).getFitnessSample();
                    System.out.printf("Geração %3d - Melhor fitness: %.2f | Pior fitness: %.2f%n",
                            geracao, melhorFitness, piorFitness);
                }
                
                List<CromossomoUltra> novaPopulacao = new ArrayList<>();
                
                // Elitismo
                for (int i = 0; i < eliteSize; i++) {
                    novaPopulacao.add(new CromossomoUltra(populacaoFinal.get(i)));
                }
                
                // Gerar resto em paralelo
                final int finalEliteSize = eliteSize;  // Tornar efetivamente final para uso no lambda
                List<CromossomoUltra> novos = FORK_JOIN_POOL.submit(() -> 
                    IntStream.range(0, populacaoSize - finalEliteSize)
                        .parallel()
                        .mapToObj(i -> {
                            CromossomoUltra pai1 = selecionarRapido(populacaoFinal);
                            CromossomoUltra pai2 = selecionarRapido(populacaoFinal);
                            return cruzarRapido(pai1, pai2);
                        })
                        .collect(Collectors.toList())).join();
                        
                novaPopulacao.addAll(novos);
                populacao = novaPopulacao;
            }
            
            // Resultado final com fitness completo
            CromossomoUltra melhor = populacao.get(0);
            double fitnessCompleto = calcularFitnessCompleto(melhor, numDisciplinas, numHorarios);
            melhor.setFitnessCompleto(fitnessCompleto);
            
            long endTime = System.currentTimeMillis();
            
            System.out.println("\n🏆 RESULTADO FINAL:");
            System.out.printf("Fitness completo: %.2f%n", fitnessCompleto);
            System.out.printf("Tempo total: %.2f segundos%n", (endTime - startTime) / 1000.0);
            System.out.printf("Disciplinas alocadas: %d/%d (%.1f%%)%n",
                    melhor.getAulas().size(), numDisciplinas,
                    100.0 * melhor.getAulas().size() / numDisciplinas);
            
            // Salvar resultado
            salvarCronogramaUltra(melhor, gerarNomesDisciplinas(numDisciplinas), 
                    gerarNomesProfessores(numProfessores), numHorarios);
                    
        } finally {
            FORK_JOIN_POOL.shutdown();
        }
    }
    
    // ========== MÉTODOS AUXILIARES VERSÃO SIMPLES ==========
    
    private static Cromossomo selecionarPorTorneio(List<Cromossomo> populacao, int tamanhoTorneio, Random random) {
        Cromossomo melhor = null;
        for (int i = 0; i < tamanhoTorneio; i++) {
            Cromossomo candidato = populacao.get(random.nextInt(populacao.size()));
            if (melhor == null || candidato.getFitness() > melhor.getFitness()) {
                melhor = candidato;
            }
        }
        return melhor;
    }
    
    private static void salvarResultado(Cromossomo melhor, String arquivo, String[] nomesDisciplinas, String[] nomesProfessores) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(arquivo));
            writer.println("CRONOGRAMA UNIVERSITÁRIO - EDUCAÇÃO AVANÇADA");
            writer.println("Gerado por Algoritmo Genético Simplificado");
            writer.println("==========================================");
            writer.println();

            for (Aula aula : melhor.getAulas()) {
                int dia = aula.getHorario() / 5;
                int hora = aula.getHorario() % 5;
                String[] dias = { "Segunda", "Terça", "Quarta", "Quinta" };
                String[] horarios = { "08:00-10:00", "10:00-12:00", "14:00-16:00", "16:00-18:00", "18:00-20:00" };

                writer.printf("%s %s: %s - %s (Sala %d)%n",
                        dias[dia], horarios[hora],
                        nomesDisciplinas[aula.getDisciplina()],
                        nomesProfessores[aula.getProfessor()],
                        aula.getSala() + 1);
            }

            writer.close();
            System.out.println("\n💾 Resultado salvo em '" + arquivo + "'");

        } catch (IOException e) {
            System.out.println("❌ Erro ao salvar arquivo: " + e.getMessage());
        }
    }
    
    // ========== MÉTODOS AUXILIARES VERSÃO 100+ DISCIPLINAS ==========
    
    private static String[] gerarNomesDisciplinas(int numDisciplinas) {
        String[] nomesDisciplinas = new String[numDisciplinas];
        String[] areas = { "Computação", "Matemática", "Física", "Química", "Biologia",
                "Engenharia", "Administração", "Economia", "Psicologia", "História" };
        for (int i = 0; i < numDisciplinas; i++) {
            nomesDisciplinas[i] = areas[i % areas.length] + " " + (i / areas.length + 1);
        }
        return nomesDisciplinas;
    }
    
    private static String[] gerarNomesProfessores(int numProfessores) {
        String[] nomesProfessores = new String[numProfessores];
        String[] sobrenomes = { "Silva", "Santos", "Oliveira", "Costa", "Souza", "Lima", "Pereira",
                "Ferreira", "Rodrigues", "Almeida", "Nascimento", "Carvalho" };
        for (int i = 0; i < numProfessores; i++) {
            nomesProfessores[i] = "Prof. " + sobrenomes[i % sobrenomes.length] +
                    (i / sobrenomes.length > 0 ? " " + (i / sobrenomes.length + 1) : "");
        }
        return nomesProfessores;
    }
    
    private static int[] gerarCapacidadeSalas(int numSalas) {
        int[] capacidadeSalas = new int[numSalas];
        Random random = ThreadLocalRandom.current();
        for (int i = 0; i < numSalas; i++) {
            capacidadeSalas[i] = 30 + random.nextInt(71); // 30-100 alunos por sala
        }
        return capacidadeSalas;
    }
    
    private static boolean[][] gerarDisponibilidadeProfessores(int numProfessores, int numHorarios) {
        boolean[][] disponibilidadeProfessor = new boolean[numProfessores][numHorarios];
        Random random = ThreadLocalRandom.current();
        for (int p = 0; p < numProfessores; p++) {
            for (int h = 0; h < numHorarios; h++) {
                disponibilidadeProfessor[p][h] = random.nextDouble() > 0.3; // 70% disponível
            }
        }
        return disponibilidadeProfessor;
    }
    
    private static int[][] gerarDisciplinasPorProfessor(int numProfessores, int numDisciplinas) {
        int[][] disciplinasPorProfessor = new int[numProfessores][];
        int disciplinasPorProf = Math.max(3, numDisciplinas / numProfessores + 2);
        Random random = ThreadLocalRandom.current();
        for (int p = 0; p < numProfessores; p++) {
            Set<Integer> disciplinas = new HashSet<>();
            while (disciplinas.size() < disciplinasPorProf && disciplinas.size() < numDisciplinas) {
                disciplinas.add(random.nextInt(numDisciplinas));
            }
            disciplinasPorProfessor[p] = disciplinas.stream().mapToInt(Integer::intValue).toArray();
        }
        return disciplinasPorProfessor;
    }
    
    private static List<Set<Integer>> gerarAlunosPorDisciplina(int numDisciplinas, int numAlunos) {
        List<Set<Integer>> alunosPorDisciplina = new ArrayList<>(numDisciplinas);
        Random random = ThreadLocalRandom.current();
        for (int d = 0; d < numDisciplinas; d++) {
            Set<Integer> alunos = new HashSet<>();
            int numAlunosDisciplina = 20 + random.nextInt(31); // 20-50 alunos por disciplina
            while (alunos.size() < numAlunosDisciplina) {
                alunos.add(random.nextInt(numAlunos));
            }
            alunosPorDisciplina.add(alunos);
        }
        return alunosPorDisciplina;
    }
    
    // Classe otimizada para algoritmo de 100+ disciplinas
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
            return String.valueOf(aulas.hashCode());
        }

        public List<Aula> getAulas() {
            return aulas;
        }

        public double getFitness() {
            return fitness;
        }
        
        public void setFitness(double fitness) {
            this.fitness = fitness;
        }

        public void invalidarFitness() {
            this.fitness = null;
        }
        
        public String getHash() {
            return hash;
        }
    }
    
    private static CromossomoOtimizado gerarCromossomoAleatorioOtimizado(
            int numDisciplinas, int numProfessores, int numSalas, int numHorarios,
            int[][] disciplinasPorProfessor, boolean[][] disponibilidadeProfessor) {
        List<Aula> aulas = new ArrayList<>();
        Set<Integer> disciplinasUsadas = new HashSet<>();
        Random random = ThreadLocalRandom.current();

        // Tentar alocar cada disciplina
        for (int d = 0; d < numDisciplinas; d++) {
            if (disciplinasUsadas.contains(d))
                continue;

            // Encontrar professores que podem ensinar esta disciplina
            List<Integer> professoresDisponiveis = new ArrayList<>();
            for (int p = 0; p < numProfessores; p++) {
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
                int horario = random.nextInt(numHorarios);
                int sala = random.nextInt(numSalas);

                if (disponibilidadeProfessor[professor][horario]) {
                    aulas.add(new Aula(d, professor, sala, horario));
                    disciplinasUsadas.add(d);
                    break;
                }
            }
        }

        return new CromossomoOtimizado(aulas);
    }
    
    private static void calcularFitnessOtimizado(
            CromossomoOtimizado cromossomo, int numDisciplinas, int numHorarios, 
            int[] capacidadeSalas, int[][] disciplinasPorProfessor, 
            boolean[][] disponibilidadeProfessor, List<Set<Integer>> alunosPorDisciplina,
            Map<String, Double> fitnessCache) {
        
        String hash = cromossomo.getHash();
        Double cachedFitness = fitnessCache.get(hash);
        if (cachedFitness != null) {
            cromossomo.setFitness(cachedFitness);
            return;
        }
        
        // Fitness normalizado para variar entre 0 e 1

        // 1. QUALIDADE DE ALOJAMENTO (40% do fitness)
        double qualidadeAlojamento = (double) cromossomo.getAulas().size() / numDisciplinas;

        // 2. QUALIDADE DE DISTRIBUIÇÃO (30% do fitness)
        Set<Integer> horariosUsados = new HashSet<>();

        for (Aula aula : cromossomo.getAulas()) {
            horariosUsados.add(aula.getHorario());
        }

        double qualidadeDistribuicao = (double) horariosUsados.size() / numHorarios;

        // 3. PENALIZAÇÕES (30% do fitness)
        int conflitosTotal = 0;
        int maxConflitos = numDisciplinas * 10; // Aumentado para refletir penalizações reais

        // Estruturas para verificação rápida de conflitos
        Set<Integer>[][] ocupacao = new HashSet[numHorarios][capacidadeSalas.length];
        Set<Integer>[] professorHorario = new HashSet[numHorarios];

        for (int h = 0; h < numHorarios; h++) {
            professorHorario[h] = new HashSet<>();
            for (int s = 0; s < capacidadeSalas.length; s++) {
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
            }

            // Verificar disponibilidade do professor
            if (!disponibilidadeProfessor[prof][hor]) {
                conflitosTotal += 5;
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

        double finalFitness = Math.max(0.0, Math.min(1.0, fitness));
        fitnessCache.put(hash, finalFitness);
        cromossomo.setFitness(finalFitness);
    }
    
    private static CromossomoOtimizado selecionarPorTorneioOtimizado(
            List<CromossomoOtimizado> populacao, int tamanhoTorneio) {
        Random random = ThreadLocalRandom.current();
        CromossomoOtimizado melhor = null;
        for (int i = 0; i < tamanhoTorneio; i++) {
            CromossomoOtimizado candidato = populacao.get(random.nextInt(populacao.size()));
            if (melhor == null || candidato.getFitness() > melhor.getFitness()) {
                melhor = candidato;
            }
        }
        return melhor;
    }
    
    private static CromossomoOtimizado cruzarOtimizado(
            CromossomoOtimizado pai1, CromossomoOtimizado pai2, double taxaCruzamento) {
        Random random = ThreadLocalRandom.current();
        if (random.nextDouble() > taxaCruzamento) {
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
    
    private static CromossomoOtimizado mutarOtimizado(
            CromossomoOtimizado cromossomo, double taxaMutacao, 
            int numHorarios, int numSalas, boolean[][] disponibilidadeProfessor) {
        Random random = ThreadLocalRandom.current();
        if (random.nextDouble() > taxaMutacao) {
            return cromossomo;
        }

        List<Aula> aulas = new ArrayList<>(cromossomo.getAulas());

        if (!aulas.isEmpty()) {
            int index = random.nextInt(aulas.size());
            Aula aulaOriginal = aulas.get(index);

            // Tentar mutar para um estado válido
            for (int tentativa = 0; tentativa < 5; tentativa++) {
                int novoHorario = random.nextInt(numHorarios);
                int novaSala = random.nextInt(numSalas);

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
    
    private static void salvarCronogramaOtimizado(
            CromossomoOtimizado cromossomo, String[] nomesDisciplinas, 
            String[] nomesProfessores, int numHorarios) {
        try (PrintWriter writer = new PrintWriter("cronograma_otimizado.txt")) {
            writer.println("CRONOGRAMA UNIVERSITÁRIO OTIMIZADO - EDUCAÇÃO AVANÇADA");
            writer.println("Gerado por Algoritmo Genético Escalável");
            writer.println("=====================================================");
            writer.println();

            Map<Integer, List<Aula>> aulasPorHorario = cromossomo.getAulas().stream()
                    .collect(Collectors.groupingBy(Aula::getHorario));

            String[] dias = { "Segunda", "Terça", "Quarta", "Quinta", "Sexta" };
            int horariosPerDay = numHorarios / 5;

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
    
    // ========== MÉTODOS AUXILIARES VERSÃO 500+ DISCIPLINAS ==========
    
    private static CromossomoUltra gerarCromossomoHeuristico(
            int numDisciplinas, int numProfessores, int numSalas, int numHorarios) {
        List<Aula> aulas = new ArrayList<>();
        Random rand = ThreadLocalRandom.current();

        // Gerar aulas com heurística simples
        int numAulas = Math.min(numDisciplinas, rand.nextInt(numDisciplinas / 2) + numDisciplinas / 2);

        for (int i = 0; i < numAulas; i++) {
            aulas.add(new Aula(
                    i % numDisciplinas,
                    rand.nextInt(numProfessores),
                    rand.nextInt(numSalas),
                    rand.nextInt(numHorarios)));
        }

        return new CromossomoUltra(aulas);
    }
    
    private static void calcularFitnessPorAmostragem(
            CromossomoUltra cromossomo, int numDisciplinas, int numHorarios, int sampleSize) {
        // Fitness rápido normalizado entre 0 e 1 com verificação realista de conflitos
        List<Aula> amostra = cromossomo.getAulas().stream()
                .limit(Math.min(sampleSize, cromossomo.getAulas().size()))
                .collect(Collectors.toList());

        // 1. Qualidade de alojamento (proporção real)
        double qualidadeAlojamento = (double) cromossomo.getAulas().size() / numDisciplinas;

        // 2. Qualidade de distribuição (horários únicos na amostra)
        Set<Integer> horariosUsados = amostra.stream()
                .map(Aula::getHorario)
                .collect(Collectors.toSet());
        double qualidadeDistribuicao = (double) horariosUsados.size() / numHorarios;

        // 3. Penalizações realistas (conflitos na amostra extrapolados)
        Set<String> ocupadosProfessor = new HashSet<>();
        Set<String> ocupadosSala = new HashSet<>();
        int conflitosTotal = 0;

        for (Aula aula : amostra) {
            String keyProf = aula.getProfessor() + "_" + aula.getHorario();
            String keySala = aula.getSala() + "_" + aula.getHorario();

            if (!ocupadosProfessor.add(keyProf)) {
                conflitosTotal += 3; // Professor ocupado
            }
            if (!ocupadosSala.add(keySala)) {
                conflitosTotal += 2; // Sala ocupada
            }

            // Simular outros conflitos típicos
            if (aula.getHorario() < 0 || aula.getHorario() >= numHorarios) {
                conflitosTotal += 4; // Horário inválido
            }
        }

        // Extrapolação dos conflitos para população total
        double fatorExtrapolacao = (double) cromossomo.getAulas().size() / Math.max(1, amostra.size());
        conflitosTotal = (int) (conflitosTotal * fatorExtrapolacao);
        int maxConflitos = numDisciplinas * 3; // Máximo realista para escala

        double penalizacaoConflitos = Math.min(1.0, (double) conflitosTotal / maxConflitos);

        // Fitness final normalizado com pesos realistas
        double fitness = (qualidadeAlojamento * 0.4) + (qualidadeDistribuicao * 0.3)
                + ((1.0 - penalizacaoConflitos) * 0.3);

        cromossomo.setFitnessSample(Math.max(0.0, Math.min(1.0, fitness)));
    }
    
    private static double calcularFitnessCompleto(
            CromossomoUltra cromossomo, int numDisciplinas, int numHorarios) {
        // Qualidade de alojamento
        double qualidadeAlojamento = (double) cromossomo.getAulas().size() / numDisciplinas;

        // Qualidade de distribuição
        Set<Integer> horariosUsados = cromossomo.getAulas().stream()
                .map(Aula::getHorario)
                .collect(Collectors.toSet());
        double qualidadeDistribuicao = (double) horariosUsados.size() / numHorarios;

        // Verificação de conflitos
        Map<String, Integer> ocupacaoProfessor = new HashMap<>();
        for (Aula aula : cromossomo.getAulas()) {
            String key = aula.getProfessor() + "_" + aula.getHorario();
            ocupacaoProfessor.merge(key, 1, Integer::sum);
        }

        // Contar conflitos
        int conflitosTotal = 0;
        for (int conflitos : ocupacaoProfessor.values()) {
            if (conflitos > 1) {
                conflitosTotal += (conflitos - 1);
            }
        }

        // Normalizar penalizações
        int maxConflitos = Math.max(1, cromossomo.getAulas().size() / 4);
        double penalizacaoConflitos = Math.min(1.0, (double) conflitosTotal / maxConflitos);

        // Cálculo final
        double fitness = (qualidadeAlojamento * 0.4) + (qualidadeDistribuicao * 0.3)
                + ((1.0 - penalizacaoConflitos) * 0.3);

        return Math.max(0.0, Math.min(1.0, fitness));
    }
    
    private static CromossomoUltra selecionarRapido(List<CromossomoUltra> populacao) {
        Random rand = ThreadLocalRandom.current();
        int eliteSize = Math.min(populacao.size(), Config500.ELITE_SIZE * 2);
        return populacao.get(rand.nextInt(eliteSize));
    }
    
    private static CromossomoUltra cruzarRapido(CromossomoUltra pai1, CromossomoUltra pai2) {
        Random rand = ThreadLocalRandom.current();

        if (rand.nextDouble() > Config500.TAXA_CRUZAMENTO) {
            return new CromossomoUltra(pai1);
        }

        // Cruzamento simplificado
        List<Aula> aulasFilho = new ArrayList<>();
        Set<Integer> disciplinasUsadas = new HashSet<>();

        // Metade de cada pai
        int meio1 = pai1.getAulas().size() / 2;
        for (int i = 0; i < meio1; i++) {
            Aula aula = pai1.getAulas().get(i);
            if (disciplinasUsadas.add(aula.getDisciplina())) {
                aulasFilho.add(new Aula(aula.getDisciplina(), aula.getProfessor(),
                        aula.getSala(), aula.getHorario()));
            }
        }

        for (Aula aula : pai2.getAulas()) {
            if (disciplinasUsadas.add(aula.getDisciplina())) {
                aulasFilho.add(new Aula(aula.getDisciplina(), aula.getProfessor(),
                        aula.getSala(), aula.getHorario()));
            }
        }

        return new CromossomoUltra(aulasFilho);
    }
    
    private static void salvarCronogramaUltra(
            CromossomoUltra cromossomo, String[] nomesDisciplinas, 
            String[] nomesProfessores, int numHorarios) {
        try (PrintWriter writer = new PrintWriter("cronograma_ultra.txt")) {
            writer.println("CRONOGRAMA UNIVERSITÁRIO ULTRA ESCALÁVEL - EDUCAÇÃO AVANÇADA");
            writer.println("Gerado por Algoritmo Genético Ultra Escalável");
            writer.println("=====================================================");
            writer.println();

            Map<Integer, List<Aula>> aulasPorHorario = cromossomo.getAulas().stream()
                    .collect(Collectors.groupingBy(Aula::getHorario));

            String[] dias = { "Segunda", "Terça", "Quarta", "Quinta", "Sexta" };
            int horariosPerDay = numHorarios / 5;

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

            System.out.println("💾 Cronograma ultra salvo em 'cronograma_ultra.txt'");

        } catch (IOException e) {
            System.err.println("Erro ao salvar arquivo: " + e.getMessage());
        }
    }
}
