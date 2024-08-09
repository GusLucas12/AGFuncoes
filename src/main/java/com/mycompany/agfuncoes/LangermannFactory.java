/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.agfuncoes;

/**
 *
 * @author gusta
 */
public class LangermannFactory implements IndividuoFactory{
    private int numGenes;

    public LangermannFactory(int numGenes) {
        this.numGenes = numGenes;
    }

    @Override
    public Individuo getIndividuo() {
        return new LangermannIndividuo(numGenes);
    }
}
