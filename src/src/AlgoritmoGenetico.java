package src;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    static final int POPULACAO = 200; // Tamanho da população
    static final int GERACOES = 100; // Número de gerações
    static final double TAXA_MUTACAO = 0.1; // 10% de chance de mutação
    static final double TAXA_CRUZAMENTO = 0.8; // 80% de chance de cruzamento

    // ========== ALGORITMO GENÉTICO ==========

    static Random random = new Random();

    public static void main(String[] args) {
        System.out.println("🎓 ALGORITMO GENÉTICO PARA AGENDAMENTO UNIVERSITÁRIO");
        System.out.println("Universidade: Educação Avançada");
        System.out.println("=================================================");

        // Inicializar dados
        DadosProblema.inicializarAlunosPorDisciplina();
        DisponibilidadeProfessor.inicializarDisponibilidade(DadosProblema.NUM_PROFESSORES, DadosProblema.NUM_HORARIOS);

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
                System.out.printf("Geração %3d - Melhor fitness: %.2f | Pior fitness: %.2f%n",
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
        salvarResultado(populacao.get(0), "cronograma_simples.txt");
        salvarResultado(populacao.get(0), "cronograma_alternativo1.txt");
        salvarResultado(populacao.get(0), "cronograma_alternativo2.txt");
    }

    // ========== GERAÇÃO DE POPULAÇÃO ==========

    static List<Cromossomo> gerarPopulacaoInicial() {
        System.out.println("🧬 Gerando população inicial...");

        List<Cromossomo> populacao = new ArrayList<>();

        for (int i = 0; i < POPULACAO; i++) {
            Cromossomo cromossomo = DadosProblema.gerarCromossomoAleatorio();
            populacao.add(cromossomo);
        }

        System.out.printf("✓ %d cromossomos criados%n", POPULACAO);
        return populacao;
    }

    // ========== CÁLCULO DE FITNESS ==========

    static void calcularFitness(List<Cromossomo> populacao) {
        for (Cromossomo cromossomo : populacao) {
            double fitnessBase = DadosProblema.calcularFitnessCromossomo(cromossomo);

            // Introduzir variações no fitness
            double penalidade = 0.0;
            if (cromossomo.temConflitos()) {
                penalidade += 0.2; // Penalidade por conflitos
            }
            if (cromossomo.excedeCapacidade()) {
                penalidade += 0.3; // Penalidade por exceder capacidade
            }

            double fitnessFinal = fitnessBase - penalidade;
            cromossomo.setFitness(Math.max(fitnessFinal, 0)); // Garantir que o fitness não seja negativo
        }
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
        return DadosProblema.cruzar(pai, mae);
    }

    static void mutar(Cromossomo cromossomo) {
        DadosProblema.mutar(cromossomo);
    }

    // ========== EXIBIÇÃO DE RESULTADOS ==========

    static void imprimirCronograma(Cromossomo cromossomo) {
        DadosProblema.imprimirCronograma(cromossomo);
    }

    static void salvarResultado(Cromossomo melhor, String arquivo) {
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
                        DadosProblema.nomesDisciplinas[aula.getDisciplina()],
                        DadosProblema.nomesProfessores[aula.getProfessor()],
                        aula.getSala() + 1);
            }

            writer.close();
            System.out.println("\n💾 Resultado salvo em '" + arquivo + "'");

        } catch (IOException e) {
            System.out.println("❌ Erro ao salvar arquivo: " + e.getMessage());
        }
    }
}
