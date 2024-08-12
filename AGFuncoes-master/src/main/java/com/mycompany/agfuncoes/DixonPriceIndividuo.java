/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.agfuncoes;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pichau
 */
public class DixonPriceIndividuo implements Individuo {

    private double[] genes;
    private double avaliacao = -1;

    public DixonPriceIndividuo(int numGenes) {
        this.genes = new double[numGenes];
        for (int i = 0; i < numGenes; i++) {
            this.genes[i] = Math.random() * 10 - 5; // Inicializa genes entre -5 e 5
        }
    }

    @Override
    public List<Individuo> recombinar(Individuo ind) {
        List<Individuo> filhos = new ArrayList<>();
        if (ind instanceof DixonPriceIndividuo) {
            DixonPriceIndividuo parceiro = (DixonPriceIndividuo) ind;
            DixonPriceIndividuo filho1 = new DixonPriceIndividuo(this.genes.length);
            DixonPriceIndividuo filho2 = new DixonPriceIndividuo(this.genes.length);

            // CrossOver BLX-α
            double alpha = 0.5; // Parâmetro alfa que define a variação além dos pais
            for (int i = 0; i < this.genes.length; i++) {
                double d = Math.abs(this.genes[i] - parceiro.genes[i]);
                double min = Math.min(this.genes[i], parceiro.genes[i]) - alpha * d;
                double max = Math.max(this.genes[i], parceiro.genes[i]) + alpha * d;

                // Geração de filhos
                filho1.genes[i] = min + Math.random() * (max - min);
                filho2.genes[i] = min + Math.random() * (max - min);
            }

            filhos.add(filho1);
            filhos.add(filho2);
        }
        return filhos;
    }

    @Override
    public Individuo mutar() {
        DixonPriceIndividuo mutante = new DixonPriceIndividuo(this.genes.length);
        double txm = 0.2;
        for (int i = 0; i < this.genes.length; i++) {
            if (Math.random() < txm) {
                mutante.genes[i] = this.genes[i] + Math.random() * 0.1; // Mutação Gaussiana
            } else {
                mutante.genes[i] = this.genes[i];
            }
        }
        return mutante;
    }

    @Override
    public double getAvaliacao() {
        if (this.avaliacao < 0) {
            this.avaliacao = calcularDixonPriceFunction();
        }
        return this.avaliacao;
    }

    private double calcularDixonPriceFunction() {
        double sum = Math.pow(genes[0] - 1, 2);
        for (int i = 1; i < genes.length; i++) {
            sum += i * Math.pow(2 * Math.pow(genes[i], 2) - genes[i - 1], 2);
        }
        return sum; // A função é de minimização
    }

    public void imprimirGenes() {
        for (double gene : this.genes) {
            System.out.print(gene + " ");
        }
        System.out.println();
    }
}


