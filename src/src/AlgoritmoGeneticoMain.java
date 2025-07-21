package src;

import java.util.Scanner;

/**
 * Classe principal para executar diferentes versões do algoritmo genético
 */
public class AlgoritmoGeneticoMain {
    
    public static void main(String[] args) {
        System.out.println("🧬 ALGORITMO GENÉTICO PARA AGENDAMENTO UNIVERSITÁRIO");
        System.out.println("===================================================");
        System.out.println("Escolha a versão do algoritmo:");
        System.out.println("1. Versão Simples (15 disciplinas)");
        System.out.println("2. Versão Escalável (150 disciplinas)");
        System.out.println("3. Versão Ultra Escalável (500 disciplinas)");
        System.out.print("Opção: ");
        
        try (Scanner scanner = new Scanner(System.in)) {
            int opcao = scanner.nextInt();
            
            switch (opcao) {
                case 1:
                    AlgoritmoGenetico.mainSimples(args);
                    break;
                case 2:
                    AlgoritmoGenetico.mainCem(args);
                    break;
                case 3:
                    AlgoritmoGenetico.mainQuinhentas(args);
                    break;
                default:
                    System.out.println("Opção inválida. Executando versão simples por padrão.");
                    AlgoritmoGenetico.mainSimples(args);
            }
        } catch (Exception e) {
            System.out.println("Erro na leitura da opção. Executando versão simples por padrão.");
            System.out.println("Erro: " + e.getMessage());
            AlgoritmoGenetico.mainSimples(args);
        }
    }
}
