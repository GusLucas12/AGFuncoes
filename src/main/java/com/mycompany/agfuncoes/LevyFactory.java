/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.agfuncoes;

/**
 *
 * @author gusta
 */
public class LevyFactory implements IndividuoFactory {
      private int numGenes;

    public LevyFactory(int numGenes) {
        this.numGenes = numGenes;
    }

    @Override
    public Individuo getIndividuo() {
        return new LevyIndividuo(numGenes);
    }
}
