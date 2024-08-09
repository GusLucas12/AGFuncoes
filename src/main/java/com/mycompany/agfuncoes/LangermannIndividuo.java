/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.agfuncoes;

/**
 *
 * @author gusta
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LangermannIndividuo implements Individuo {

    private double[] genes;
    private double avaliacao = -1;
    private static Random random = new Random();
    private static final int m = 5; // Número de termos na função de Langermann
    private static final double[] c = {1.0, 2.0, 5.0, 2.0, 3.0};
    private static final double[][] a = {
        {3, 5, 2, 1, 7},
        {5, 2, 1, 4, 9},
        {2, 1, 4, 8, 2},
        {1, 3, 2, 6, 4},
        {7, 5, 3, 1, 2}
    };

    public LangermannIndividuo(int numGenes) {
        this.genes = new double[numGenes];
        for (int i = 0; i < numGenes; i++) {
            this.genes[i] = Math.random() * 10; // Inicializa genes entre 0 e 10
        }
    }

    @Override
    public List<Individuo> recombinar(Individuo ind) {
        List<Individuo> filhos = new ArrayList<>();
        if (ind instanceof LangermannIndividuo) {
            LangermannIndividuo parceiro = (LangermannIndividuo) ind;
            LangermannIndividuo filho1 = new LangermannIndividuo(this.genes.length);
            LangermannIndividuo filho2 = new LangermannIndividuo(this.genes.length);

            // CrossOver Aritmético
            for (int i = 0; i < this.genes.length; i++) {
                filho1.genes[i] = 0.5 * (this.genes[i] + parceiro.genes[i]);
                filho2.genes[i] = 0.5 * (this.genes[i] + parceiro.genes[i]);
            }

            // CrossOver BLX-α
            double alphaBLX = 0.5; // Parâmetro de controle
            for (int i = 0; i < genes.length; i++) {
                double cMin = Math.min(this.genes[i], parceiro.genes[i]);
                double cMax = Math.max(this.genes[i], parceiro.genes[i]);
                double range = cMax - cMin;
                filho1.genes[i] = cMin - alphaBLX * range + random.nextDouble() * (1 + 2 * alphaBLX) * range;
                filho2.genes[i] = cMin - alphaBLX * range + random.nextDouble() * (1 + 2 * alphaBLX) * range;

            }

            filhos.add(filho1);
            filhos.add(filho2);
        }
        return filhos;
    }

    @Override
    public Individuo mutar() {
        LangermannIndividuo mutante = new LangermannIndividuo(this.genes.length);
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
            this.avaliacao = calcularLangermannFunction();
        }
        return this.avaliacao;
    }

    private double calcularLangermannFunction() {
        double sum = 0.0;
        for (int i = 0; i < m; i++) {
            double innerSum = 0.0;
            for (int j = 0; j < this.genes.length; j++) {
                innerSum += Math.pow(this.genes[j] - a[i][j], 2);
            }
            sum += c[i] * Math.exp(-innerSum / Math.PI) * Math.cos(Math.PI * innerSum);
        }
        return -sum; // Como a função é de minimização, o valor retornado é invertido
    }

    public void imprimirGenes() {
        for (double gene : this.genes) {
            System.out.print(gene + " ");
        }
    }
}
