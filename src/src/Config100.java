package src;

/**
 * Configurações para o algoritmo genético escalável (100+ disciplinas)
 */
public class Config100 {
    // Configurações do algoritmo genético
    public static final int POPULACAO = 500;
    public static final int GERACOES = 200;
    public static final double TAXA_MUTACAO = 0.05;
    public static final double TAXA_CRUZAMENTO = 0.85;
    public static final int ELITE_SIZE = 50;
    public static final int TOURNAMENT_SIZE = 5;
    
    // Configurações do problema
    public static final int NUM_DISCIPLINAS = 150;
    public static final int NUM_PROFESSORES = 30;
    public static final int NUM_SALAS = 20;
    public static final int NUM_HORARIOS = 50;
    public static final int NUM_ALUNOS = 1000;
    
    private Config100() {
        // Construtor privado para evitar instanciação
    }
}
