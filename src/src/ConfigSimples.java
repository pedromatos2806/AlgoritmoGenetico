package src;

/**
 * Configurações para o algoritmo genético simples (15 disciplinas)
 */
public class ConfigSimples {
    // Configurações do algoritmo genético
    public static final int POPULACAO = 200;
    public static final int GERACOES = 100;
    public static final double TAXA_MUTACAO = 0.1;
    public static final double TAXA_CRUZAMENTO = 0.8;
    
    // Configurações do problema
    public static final int NUM_DISCIPLINAS = 15;
    public static final int NUM_PROFESSORES = 6;
    public static final int NUM_SALAS = 4;
    public static final int NUM_HORARIOS = 20;
    public static final int NUM_ALUNOS = 100;
    
    private ConfigSimples() {
        // Construtor privado para evitar instanciação
    }
}
