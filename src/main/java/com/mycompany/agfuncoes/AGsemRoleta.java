package com.mycompany.agfuncoes;

/**
 *
 * @author Pichau
 */
public class AGsemRoleta {

    public static void main(String[] args) {

        int n = 20; // Tamanho da população
        int elite = 4; // Número de indivíduos de elite
        int nGer = 2000; // Número de gerações
        int numGenes = 2; // Número de variáveis (dimensões) 

        IndividuoFactory indFact = new LevyFactory(numGenes);
        LangermannFactory factory = new LangermannFactory(numGenes);
        AG ag = new AG();
        //Levy
        Individuo ind = ag.executar(indFact, n, elite, nGer);
        //Langermann
         //Individuo ind = ag.executar(factory, n, elite, nGer);

        System.out.println("Melhor solução encontrada após " + nGer + " gerações:");
        //Levy
        ((LevyIndividuo) ind).imprimirGenes();
        //Langermann
        //((LangermannIndividuo) ind).imprimirGenes();
        System.out.println("Avaliação da melhor solução: " + ind.getAvaliacao());
     
        
    }
}
