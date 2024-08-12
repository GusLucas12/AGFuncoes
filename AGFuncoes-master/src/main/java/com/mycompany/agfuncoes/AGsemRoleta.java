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

        IndividuoFactory levy = new LevyFactory(numGenes);
        IndividuoFactory langer = new LangermannFactory(numGenes);
        IndividuoFactory dixon= new DixonPriceIndividuoFactory(numGenes);
        AG ag = new AG();
        //Levy
        //Individuo ind = ag.executar(levy, n, elite, nGer);
        //Langermann
         //Individuo ind = ag.executar(langer, n, elite, nGer);
        //Dixon-Price
        Individuo ind=ag.executar(dixon, nGer, elite, nGer);
        System.out.println("Melhor solução encontrada após " + nGer + " gerações:");
        //Levy
        //((LevyIndividuo) ind).imprimirGenes();
        //Langermann
        //((LangermannIndividuo) ind).imprimirGenes();
        //Dixon-Price
        ((DixonPriceIndividuo) ind).imprimirGenes();
        System.out.println("Avaliação da melhor solução: " + ind.getAvaliacao());
     
        
    }
}
