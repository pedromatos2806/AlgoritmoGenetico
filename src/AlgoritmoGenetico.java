import java.io.*;
import java.util.*;

/**
 * Algoritmo Genético para Agendamento Universitário
 * 
 * PROBLEMA: Alocar disciplinas em horários e salas, respeitando:
 * - Disponibilidade dos professores
 * - Capacidade das salas
 * - Evitar conflitos de horário para alunos
 * 
 * SOLUÇÃO: Cada cromossomo representa um cronograma completo
 */
@SuppressWarnings("java:S106") // Suprime avisos sobre System.out em projeto educacional
public class AlgoritmoGenetico {
    
    // ========== CONFIGURAÇÕES ==========
    static final int POPULACAO = 200;           // Tamanho da população
    static final int GERACOES = 100;           // Número de gerações
    static final double TAXA_MUTACAO = 0.1;    // 10% de chance de mutação
    static final double TAXA_CRUZAMENTO = 0.8; // 80% de chance de cruzamento
    
    // Dados do problema
    static final int NUM_DISCIPLINAS = 15;
    static final int NUM_PROFESSORES = 6;
    static final int NUM_SALAS = 4;
    static final int NUM_HORARIOS = 20; // 4 dias x 5 horários por dia
    static final int NUM_ALUNOS = 100;
    
    // ========== DADOS DO PROBLEMA ==========
    static String[] nomesDisciplinas = {
        "Algoritmos", "Banco de Dados", "Redes", "IA", "Compiladores",
        "Sistemas", "POO", "Cálculo I", "Cálculo II", "Álgebra",
        "Física I", "Inglês", "Administração", "Economia", "Estatística"
    };
    
    static String[] nomesProfessores = {
        "Prof. Silva", "Prof. Santos", "Prof. Oliveira", 
        "Prof. Costa", "Prof. Souza", "Prof. Lima"
    };
    
    // Disponibilidade dos professores (true = disponível)
    static boolean[][] disponibilidadeProfessor;
    
    // Disciplinas que cada professor pode lecionar
    static int[][] disciplinasPorProfessor = {
        {0, 1, 2},        // Prof. Silva: Algoritmos, BD, Redes
        {3, 4, 5},        // Prof. Santos: IA, Compiladores, Sistemas
        {6, 7, 8},        // Prof. Oliveira: POO, Cálculo I, Cálculo II
        {9, 10, 11},      // Prof. Costa: Álgebra, Física, Inglês
        {12, 13, 14},     // Prof. Souza: Administração, Economia, Estatística
        {0, 3, 6, 9, 12}  // Prof. Lima: pode lecionar uma de cada área
    };
    
    // Alunos matriculados em cada disciplina
    static List<List<Integer>> alunosPorDisciplina = new ArrayList<>();
    
    static Random random = new Random();
    
    // ========== ALGORITMO GENÉTICO ==========
    
    public static void main(String[] args) {
        System.out.println("🎓 ALGORITMO GENÉTICO PARA AGENDAMENTO UNIVERSITÁRIO");
        System.out.println("Universidade: Educação Avançada");
        System.out.println("=================================================");
        
        // Inicializar dados
        inicializarDados();
        
        // Gerar população inicial
        List<Cromossomo> populacao = gerarPopulacaoInicial();
        
        // Evoluir por várias gerações
        for (int geracao = 0; geracao < GERACOES; geracao++) {
            
            // Calcular fitness de todos
            calcularFitness(populacao);
            
            // Ordenar por fitness (melhor primeiro)
            populacao.sort((c1, c2) -> Double.compare(c2.getFitness(), c1.getFitness()));
            
            // Mostrar progresso
            if (geracao % 20 == 0 || geracao == GERACOES - 1) {
                double melhorFitness = populacao.get(0).getFitness();
                double piorFitness = populacao.get(populacao.size() - 1).getFitness();
                System.out.printf("Geração %3d - Melhor fitness: %.2f | Pior fitness: %.2f\n", 
                    geracao, melhorFitness, piorFitness);
            }
            
            // Criar nova população
            List<Cromossomo> novaPopulacao = new ArrayList<>();
            
            // Manter os 10% melhores (elitismo)
            int elites = POPULACAO / 10;
            for (int i = 0; i < elites; i++) {
                novaPopulacao.add(new Cromossomo(populacao.get(i)));
            }
            
            // Gerar o resto por cruzamento e mutação
            while (novaPopulacao.size() < POPULACAO) {
                Cromossomo pai = selecionarPai(populacao);
                Cromossomo mae = selecionarPai(populacao);
                
                Cromossomo filho;
                if (random.nextDouble() < TAXA_CRUZAMENTO) {
                    filho = cruzar(pai, mae);
                } else {
                    filho = new Cromossomo(pai);
                }
                
                if (random.nextDouble() < TAXA_MUTACAO) {
                    mutar(filho);
                }
                
                novaPopulacao.add(filho);
            }
            
            populacao = novaPopulacao;
        }
        
        // Mostrar melhor solução
        calcularFitness(populacao);
        populacao.sort((c1, c2) -> Double.compare(c2.getFitness(), c1.getFitness()));
        
        System.out.println("\n🏆 MELHOR SOLUÇÃO ENCONTRADA:");
        System.out.println("Fitness: " + String.format("%.2f", populacao.get(0).getFitness()));
        imprimirCronograma(populacao.get(0));
        
        // Salvar resultado
        salvarResultado(populacao.get(0));
    }
    
    // ========== INICIALIZAÇÃO ==========
    
    static void inicializarDados() {
        System.out.println("📊 Inicializando dados...");
        
        // Inicializar disponibilidade dos professores
        disponibilidadeProfessor = new boolean[NUM_PROFESSORES][NUM_HORARIOS];
        for (int p = 0; p < NUM_PROFESSORES; p++) {
            for (int h = 0; h < NUM_HORARIOS; h++) {
                // 80% de chance de estar disponível
                disponibilidadeProfessor[p][h] = random.nextDouble() < 0.8;
            }
        }
        
        // Inicializar alunos por disciplina
        alunosPorDisciplina.clear();
        for (int d = 0; d < NUM_DISCIPLINAS; d++) {
            List<Integer> alunosNaDisciplina = new ArrayList<>();
            
            // Cada disciplina tem entre 20-40 alunos
            int numAlunos = 20 + random.nextInt(21);
            
            for (int i = 0; i < numAlunos; i++) {
                int alunoId = random.nextInt(NUM_ALUNOS);
                if (!alunosNaDisciplina.contains(alunoId)) {
                    alunosNaDisciplina.add(alunoId);
                }
            }
            
            alunosPorDisciplina.add(alunosNaDisciplina);
        }
        
        System.out.printf("✓ %d disciplinas, %d professores, %d salas, %d horários\n", 
            NUM_DISCIPLINAS, NUM_PROFESSORES, NUM_SALAS, NUM_HORARIOS);
    }
    
    // ========== GERAÇÃO DE POPULAÇÃO ==========
    
    static List<Cromossomo> gerarPopulacaoInicial() {
        System.out.println("🧬 Gerando população inicial...");
        
        List<Cromossomo> populacao = new ArrayList<>();
        
        for (int i = 0; i < POPULACAO; i++) {
            Cromossomo cromossomo = gerarCromossomoAleatorio();
            populacao.add(cromossomo);
        }
        
        System.out.printf("✓ %d cromossomos criados\n", POPULACAO);
        return populacao;
    }
    
    static Cromossomo gerarCromossomoAleatorio() {
        Cromossomo cromossomo = new Cromossomo();
        
        // Para cada disciplina, alocar um horário
        for (int d = 0; d < NUM_DISCIPLINAS; d++) {
            
            // Encontrar professores que podem lecionar esta disciplina
            List<Integer> professoresValidos = new ArrayList<>();
            for (int p = 0; p < NUM_PROFESSORES; p++) {
                for (int disc : disciplinasPorProfessor[p]) {
                    if (disc == d) {
                        professoresValidos.add(p);
                        break;
                    }
                }
            }
            
            if (professoresValidos.isEmpty()) continue;
            
            // Tentar alocar em um horário válido
            int tentativas = 0;
            while (tentativas < 50) {
                int professor = professoresValidos.get(random.nextInt(professoresValidos.size()));
                int sala = random.nextInt(NUM_SALAS);
                int horario = random.nextInt(NUM_HORARIOS);
                
                // Verificar se professor está disponível
                if (disponibilidadeProfessor[professor][horario]) {
                    
                    // Verificar se não há conflito
                    boolean conflito = false;
                    for (Aula aula : cromossomo.getAulas()) {
                        if ((aula.getProfessor() == professor || aula.getSala() == sala) && 
                            aula.getHorario() == horario) {
                            conflito = true;
                            break;
                        }
                    }
                    
                    if (!conflito) {
                        cromossomo.adicionarAula(new Aula(d, professor, sala, horario));
                        break;
                    }
                }
                tentativas++;
            }
        }
        
        return cromossomo;
    }
    
    // ========== CÁLCULO DE FITNESS ==========
    
    static void calcularFitness(List<Cromossomo> populacao) {
        for (Cromossomo cromossomo : populacao) {
            cromossomo.setFitness(calcularFitnessCromossomo(cromossomo));
        }
    }
    
    static double calcularFitnessCromossomo(Cromossomo cromossomo) {
        // Fitness normalizado para variar entre 0 e 1
        
        // 1. QUALIDADE DE ALOJAMENTO (40% do fitness)
        // Baseado na proporção de disciplinas alocadas
        double qualidadeAlojamento = (double) cromossomo.getAulas().size() / NUM_DISCIPLINAS;
        
        // 2. QUALIDADE DE DISTRIBUIÇÃO (30% do fitness)
        // Baseado na distribuição uniforme pelos horários
        Set<Integer> horariosUsados = new HashSet<>();
        for (Aula aula : cromossomo.getAulas()) {
            horariosUsados.add(aula.getHorario());
        }
        double qualidadeDistribuicao = (double) horariosUsados.size() / NUM_HORARIOS;
        
        // 3. PENALIZAÇÕES (30% do fitness - subtraído)
        int conflitosTotal = 0;
        int maxConflitos = NUM_DISCIPLINAS * 3; // Estimativa máxima de conflitos possíveis
        
        // 3.1. Conflitos de horário (professor ou sala ocupados)
        Set<String> ocupados = new HashSet<>();
        for (Aula aula : cromossomo.getAulas()) {
            String chaveProf = "P" + aula.getProfessor() + "H" + aula.getHorario();
            String chaveSala = "S" + aula.getSala() + "H" + aula.getHorario();
            
            if (ocupados.contains(chaveProf)) conflitosTotal += 3; // Professor ocupado
            if (ocupados.contains(chaveSala)) conflitosTotal += 2; // Sala ocupada
            
            ocupados.add(chaveProf);
            ocupados.add(chaveSala);
        }
        
        // 3.2. Professor indisponível
        for (Aula aula : cromossomo.getAulas()) {
            if (!disponibilidadeProfessor[aula.getProfessor()][aula.getHorario()]) {
                conflitosTotal += 4;
            }
        }
        
        // 3.3. Conflitos de alunos (aluno com 2 aulas no mesmo horário)
        Map<Integer, Set<Integer>> aulasPorHorario = new HashMap<>();
        for (Aula aula : cromossomo.getAulas()) {
            aulasPorHorario.computeIfAbsent(aula.getHorario(), k -> new HashSet<>()).add(aula.getDisciplina());
        }
        
        for (Set<Integer> disciplinasNoHorario : aulasPorHorario.values()) {
            if (disciplinasNoHorario.size() > 1) {
                // Verificar se há alunos em comum
                for (int d1 : disciplinasNoHorario) {
                    for (int d2 : disciplinasNoHorario) {
                        if (d1 < d2) {
                            Set<Integer> comum = new HashSet<>(alunosPorDisciplina.get(d1));
                            comum.retainAll(alunosPorDisciplina.get(d2));
                            conflitosTotal += comum.size(); // 1 ponto por aluno em conflito
                        }
                    }
                }
            }
        }
        
        // Normalizar penalizações
        double penalizacaoConflitos = Math.min(1.0, (double) conflitosTotal / maxConflitos);
        
        // CÁLCULO FINAL DO FITNESS (0 a 1)
        double fitness = (qualidadeAlojamento * 0.4) + (qualidadeDistribuicao * 0.3) + ((1.0 - penalizacaoConflitos) * 0.3);
        
        return Math.max(0.0, Math.min(1.0, fitness));
    }
    
    // ========== OPERADORES GENÉTICOS ==========
    
    static Cromossomo selecionarPai(List<Cromossomo> populacao) {
        // Seleção por torneio (tamanho 3)
        Cromossomo melhor = populacao.get(random.nextInt(populacao.size()));
        for (int i = 0; i < 2; i++) {
            Cromossomo candidato = populacao.get(random.nextInt(populacao.size()));
            if (candidato.getFitness() > melhor.getFitness()) {
                melhor = candidato;
            }
        }
        return melhor;
    }
    
    static Cromossomo cruzar(Cromossomo pai, Cromossomo mae) {
        Cromossomo filho = new Cromossomo();
        
        // Cruzamento por disciplina: para cada disciplina, escolher do pai ou da mãe
        Set<Integer> disciplinasUsadas = new HashSet<>();
        
        for (Aula aulaPai : pai.getAulas()) {
            if (random.nextBoolean() && !disciplinasUsadas.contains(aulaPai.getDisciplina())) {
                filho.adicionarAula(new Aula(aulaPai.getDisciplina(), aulaPai.getProfessor(), 
                                           aulaPai.getSala(), aulaPai.getHorario()));
                disciplinasUsadas.add(aulaPai.getDisciplina());
            }
        }
        
        for (Aula aulaMae : mae.getAulas()) {
            if (!disciplinasUsadas.contains(aulaMae.getDisciplina())) {
                filho.adicionarAula(new Aula(aulaMae.getDisciplina(), aulaMae.getProfessor(), 
                                           aulaMae.getSala(), aulaMae.getHorario()));
                disciplinasUsadas.add(aulaMae.getDisciplina());
            }
        }
        
        return filho;
    }
    
    static void mutar(Cromossomo cromossomo) {
        if (cromossomo.isEmpty()) return;
        
        // Escolher uma aula aleatória para mutar
        Aula aula = cromossomo.getAulas().get(random.nextInt(cromossomo.getAulas().size()));
        
        // Mutar sala ou horário
        if (random.nextBoolean()) {
            aula.setSala(random.nextInt(NUM_SALAS));
        } else {
            aula.setHorario(random.nextInt(NUM_HORARIOS));
        }
    }
    
    // ========== EXIBIÇÃO DE RESULTADOS ==========
    
    static void imprimirCronograma(Cromossomo cromossomo) {
        System.out.println("\n📅 CRONOGRAMA OTIMIZADO:");
        System.out.println("========================");
        
        String[] dias = {"Segunda", "Terça", "Quarta", "Quinta"};
        String[] horarios = {"08:00", "10:00", "14:00", "16:00", "18:00"};
        
        // Agrupar aulas por horário
        Map<Integer, List<Aula>> aulasPorHorario = new HashMap<>();
        for (Aula aula : cromossomo.getAulas()) {
            aulasPorHorario.computeIfAbsent(aula.getHorario(), k -> new ArrayList<>()).add(aula);
        }
        
        for (int dia = 0; dia < 4; dia++) {
            System.out.println("\n" + dias[dia].toUpperCase() + ":");
            
            for (int hora = 0; hora < 5; hora++) {
                int horarioId = dia * 5 + hora;
                System.out.printf("  %s: ", horarios[hora]);
                
                List<Aula> aulas = aulasPorHorario.get(horarioId);
                if (aulas != null && !aulas.isEmpty()) {
                    for (int i = 0; i < aulas.size(); i++) {
                        if (i > 0) System.out.print(" | ");
                        Aula aula = aulas.get(i);
                        System.out.printf("%s (%s, Sala %d)", 
                            nomesDisciplinas[aula.getDisciplina()],
                            nomesProfessores[aula.getProfessor()],
                            aula.getSala() + 1);
                    }
                } else {
                    System.out.print("(livre)");
                }
                System.out.println();
            }
        }
        
        // Estatísticas
        System.out.println("\n📊 ESTATÍSTICAS:");
        System.out.printf("• Disciplinas alocadas: %d/%d\n", cromossomo.getAulas().size(), NUM_DISCIPLINAS);
        System.out.printf("• Fitness: %.2f\n", cromossomo.getFitness());
    }
    
    static void salvarResultado(Cromossomo melhor) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("cronograma_simples.txt"));
            writer.println("CRONOGRAMA UNIVERSITÁRIO - EDUCAÇÃO AVANÇADA");
            writer.println("Gerado por Algoritmo Genético Simplificado");
            writer.println("==========================================");
            writer.println();
            
            for (Aula aula : melhor.getAulas()) {
                int dia = aula.getHorario() / 5;
                int hora = aula.getHorario() % 5;
                String[] dias = {"Segunda", "Terça", "Quarta", "Quinta"};
                String[] horarios = {"08:00-10:00", "10:00-12:00", "14:00-16:00", "16:00-18:00", "18:00-20:00"};
                
                writer.printf("%s %s: %s - %s (Sala %d)\n",
                    dias[dia], horarios[hora],
                    nomesDisciplinas[aula.getDisciplina()],
                    nomesProfessores[aula.getProfessor()],
                    aula.getSala() + 1);
            }
            
            writer.close();
            System.out.println("\n💾 Resultado salvo em 'cronograma_simples.txt'");
            
        } catch (IOException e) {
            System.out.println("❌ Erro ao salvar arquivo: " + e.getMessage());
        }
    }
}
