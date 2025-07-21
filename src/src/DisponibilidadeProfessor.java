package src;

import java.util.Random;

public class DisponibilidadeProfessor {

    protected static boolean[][] disponibilidade;
    private static final Random random = new Random();

    private DisponibilidadeProfessor() {
        // Construtor privado para evitar instância
    }

    public static void inicializarDisponibilidade(int numProfessores, int numHorarios) {
        disponibilidade = new boolean[numProfessores][numHorarios];
        for (int p = 0; p < numProfessores; p++) {
            for (int h = 0; h < numHorarios; h++) {
                disponibilidade[p][h] = random.nextInt(100) < 80;
            }
        }
    }

    public static boolean isDisponivel(int professor, int horario) {
        return disponibilidade[professor][horario];
    }
}
