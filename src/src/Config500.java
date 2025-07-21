package src;

/**
 * Configurações para o algoritmo genético ultra escalável (500+ disciplinas)
 */
public class Config500 {
    // Configurações do algoritmo genético
    public static final int POPULACAO = 1000;
    public static final int GERACOES = 300;
    public static final double TAXA_MUTACAO = 0.03;
    public static final double TAXA_CRUZAMENTO = 0.90;
    public static final int ELITE_SIZE = 100;
    public static final int SAMPLE_SIZE = 100;
    public static final int MAX_CACHE_SIZE = 10000;
    
    // Configurações do problema
    public static final int NUM_DISCIPLINAS = 500;
    public static final int NUM_PROFESSORES = 100;
    public static final int NUM_SALAS = 50;
    public static final int NUM_HORARIOS = 100;
    public static final int NUM_ALUNOS = 5000;
    
    private Config500() {
        // Construtor privado para evitar instanciação
    }
}
