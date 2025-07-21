import java.util.ArrayList;
import java.util.List;

/**
 * Representa um cromossomo no algoritmo genético. Contém uma lista de aulas e seu valor de fitness
 */
public class Cromossomo {
	private List<Aula> aulas;
	private double fitness;

	public Cromossomo() {
		this.aulas = new ArrayList<>();
		this.fitness = 0.0;
	}

	public Cromossomo(Cromossomo outro) {
		this.aulas = new ArrayList<>();
		for (Aula aula : outro.aulas) {
			this.aulas.add(new Aula(aula.getDisciplina(), aula.getProfessor(), aula.getSala(), aula.getHorario()));
		}
		this.fitness = outro.fitness;
	}

	public List<Aula> getAulas() {
		return aulas;
	}

	public double getFitness() {
		return fitness;
	}

	public void setAulas(List<Aula> aulas) {
		this.aulas = aulas;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public void adicionarAula(Aula aula) {
		this.aulas.add(aula);
	}

	public void removerAula(int indice) {
		if (indice >= 0 && indice < aulas.size()) {
			aulas.remove(indice);
		}
	}

	public int getNumeroAulas() {
		return aulas.size();
	}

	public boolean isEmpty() {
		return aulas.isEmpty();
	}

	public void limpar() {
		aulas.clear();
		fitness = 0.0;
	}

	@Override
	public String toString() {
		return String.format("Cromossomo[aulas=%d, fitness=%.2f]", aulas.size(), fitness);
	}
}
