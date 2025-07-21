package src;

import java.util.ArrayList;
import java.util.List;

/**
 * Cromossomo ultra otimizado para o algoritmo genético com 500+ disciplinas
 */
public class CromossomoUltra {
    private final List<Aula> aulas;
    private Double fitnessSample;
    private Double fitnessCompleto;
    private final String hash;

    /**
     * Construtor com lista de aulas
     */
    public CromossomoUltra(List<Aula> aulas) {
        this.aulas = new ArrayList<>(aulas);
        this.hash = calcularHashRapido();
    }
    
    /**
     * Construtor para cópia de outro cromossomo
     */
    public CromossomoUltra(CromossomoUltra outro) {
        this.aulas = new ArrayList<>();
        for (Aula aula : outro.aulas) {
            this.aulas.add(new Aula(aula.getDisciplina(), aula.getProfessor(),
                    aula.getSala(), aula.getHorario()));
        }
        this.fitnessSample = outro.fitnessSample;
        this.fitnessCompleto = outro.fitnessCompleto;
        this.hash = calcularHashRapido();
    }

    /**
     * Calcula um hash simplificado para melhor performance
     */
    private String calcularHashRapido() {
        return String.valueOf(aulas.size() + aulas.hashCode());
    }

    public List<Aula> getAulas() {
        return aulas;
    }

    /**
     * Retorna o fitness calculado por amostragem (rápido)
     * 
     * @return valor de fitness por amostragem
     */
    public double getFitnessSample() {
        return fitnessSample != null ? fitnessSample : 0.0;
    }
    
    /**
     * Define o fitness calculado por amostragem
     * 
     * @param fitness valor de fitness
     */
    public void setFitnessSample(double fitness) {
        this.fitnessSample = fitness;
    }
    
    /**
     * Retorna o fitness calculado de forma completa (preciso mas lento)
     * 
     * @return valor de fitness completo
     */
    public double getFitnessCompleto() {
        return fitnessCompleto != null ? fitnessCompleto : 0.0;
    }
    
    /**
     * Define o fitness completo
     * 
     * @param fitness valor de fitness completo
     */
    public void setFitnessCompleto(double fitness) {
        this.fitnessCompleto = fitness;
    }
    
    /**
     * Retorna o hash do cromossomo
     * 
     * @return hash do cromossomo
     */
    public String getHash() {
        return hash;
    }
    
    /**
     * Verifica se o cromossomo está vazio
     * 
     * @return true se não tem aulas, false caso contrário
     */
    public boolean isEmpty() {
        return aulas.isEmpty();
    }
    
    @Override
    public String toString() {
        return String.format("CromossomoUltra[aulas=%d, fitnessSample=%.2f]", 
                aulas.size(), 
                fitnessSample != null ? fitnessSample : 0.0);
    }
}
