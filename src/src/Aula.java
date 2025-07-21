package src;

/**
 * Representa uma aula no cronograma universitário. Contém informações sobre
 * disciplina, professor, sala e horário.
 */
public class Aula {
	private int disciplina;
	private int professor;
	private int sala;
	private int horario;

	public Aula(int disciplina, int professor, int sala, int horario) {
		this.disciplina = disciplina;
		this.professor = professor;
		this.sala = sala;
		this.horario = horario;
	}

	public int getDisciplina() {
		return disciplina;
	}

	public int getProfessor() {
		return professor;
	}

	public int getSala() {
		return sala;
	}

	public int getHorario() {
		return horario;
	}

	public void setDisciplina(int disciplina) {
		this.disciplina = disciplina;
	}

	public void setProfessor(int professor) {
		this.professor = professor;
	}

	public void setSala(int sala) {
		this.sala = sala;
	}

	public void setHorario(int horario) {
		this.horario = horario;
	}

	@Override
	public String toString() {
		return String.format("D%d P%d S%d H%d", disciplina, professor, sala, horario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		Aula aula = (Aula) obj;
		return disciplina == aula.disciplina && professor == aula.professor && sala == aula.sala
				&& horario == aula.horario;
	}

	@Override
	public int hashCode() {
		return disciplina * 1000 + professor * 100 + sala * 10 + horario;
	}
}
