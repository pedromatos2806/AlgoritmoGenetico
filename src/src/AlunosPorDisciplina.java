package src;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AlunosPorDisciplina {

    private static List<List<Integer>> alunosPorDisciplina = new ArrayList<>();
    private static final Random random = new Random();

    private AlunosPorDisciplina() {
        // Construtor privado para evitar instância
    }

    public static void inicializar(int numDisciplinas, int numAlunos) {
        alunosPorDisciplina.clear();
        for (int d = 0; d < numDisciplinas; d++) {
            List<Integer> alunosNaDisciplina = new ArrayList<>();
            int numAlunosDisciplina = 20 + random.nextInt(21);
            for (int i = 0; i < numAlunosDisciplina; i++) {
                int alunoId = random.nextInt(numAlunos);
                if (!alunosNaDisciplina.contains(alunoId)) {
                    alunosNaDisciplina.add(alunoId);
                }
            }
            alunosPorDisciplina.add(alunosNaDisciplina);
        }
    }

    public static List<List<Integer>> getAlunosPorDisciplina() {
        return alunosPorDisciplina;
    }
}
