package com.mycompany.agfuncoes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LevyIndividuo implements Individuo {
    private double[] genes;
    private double avaliacao = -1;
    private static final double X_MIN = -10; // Limite inferior do domínio da função
    private static final double X_MAX = 10;  // Limite superior do domínio da função
    private static Random random = new Random();

    public LevyIndividuo(int numGenes) {
        this.genes = new double[numGenes];
        for (int i = 0; i < numGenes; i++) {
            this.genes[i] = X_MIN + (X_MAX - X_MIN) * random.nextDouble();
        }
    }

    private LevyIndividuo(double[] genes) {
        this.genes = genes;
    }

    @Override
    public List<Individuo> recombinar(Individuo ind) {
        List<Individuo> filhos = new ArrayList<>();
        if (ind instanceof LevyIndividuo) {
            LevyIndividuo outroInd = (LevyIndividuo) ind;

            // CrossOver Aritmético
            double alpha = random.nextDouble();
            double[] genesFilho1 = new double[genes.length];
            double[] genesFilho2 = new double[genes.length];
            for (int i = 0; i < genes.length; i++) {
                genesFilho1[i] = alpha * this.genes[i] + (1 - alpha) * outroInd.genes[i];
                genesFilho2[i] = alpha * outroInd.genes[i] + (1 - alpha) * this.genes[i];
            }
            
               // CrossOver BLX-α
            double alphaBLX = 0.5; // Parâmetro de controle
            for (int i = 0; i < genes.length; i++) {
                double cMin = Math.min(this.genes[i], outroInd.genes[i]);
                double cMax = Math.max(this.genes[i], outroInd.genes[i]);
                double range = cMax - cMin;
                genesFilho1[i] = cMin - alphaBLX * range + random.nextDouble() * (1 + 2 * alphaBLX) * range;
                genesFilho2[i] = cMin - alphaBLX * range + random.nextDouble() * (1 + 2 * alphaBLX) * range;

                // Garantir que os novos genes estejam dentro dos limites
                genesFilho1[i] = Math.max(X_MIN, Math.min(X_MAX, genesFilho1[i]));
                genesFilho2[i] = Math.max(X_MIN, Math.min(X_MAX, genesFilho2[i]));
            }
            filhos.add(new LevyIndividuo(genesFilho1));
            filhos.add(new LevyIndividuo(genesFilho2));
        }
        return filhos;
    }

    @Override
    public Individuo mutar() {
        double sigma = 0.1; // Desvio padrão da mutação gaussiana
        double[] novosGenes = new double[genes.length];
        for (int i = 0; i < genes.length; i++) {
            novosGenes[i] = this.genes[i] + random.nextGaussian() * sigma;
            // Garantir que o novo gene esteja dentro dos limites
            novosGenes[i] = Math.max(X_MIN, Math.min(X_MAX, novosGenes[i]));
        }
        return new LevyIndividuo(novosGenes);
    }

    @Override
    public double getAvaliacao() {
        if (this.avaliacao < 0) {
            this.avaliacao = avaliar();
        }
        return this.avaliacao;
    }

    private double avaliar() {
        double w0 = 1 + (genes[0] - 1) / 4;
        double sum = Math.pow(Math.sin(Math.PI * w0), 2);
        for (int i = 0; i < genes.length - 1; i++) {
            double wi = 1 + (genes[i] - 1) / 4;
            sum += Math.pow(wi - 1, 2) * (1 + 10 * Math.pow(Math.sin(Math.PI * wi + 1), 2));
        }
        double wn = 1 + (genes[genes.length - 1] - 1) / 4;
        sum += Math.pow(wn - 1, 2) * (1 + Math.pow(Math.sin(2 * Math.PI * wn), 2));
        return sum;
    }

    public void imprimirGenes() {
        for (double gene : genes) {
            System.out.printf("%.4f ", gene);
        }
        System.out.println();
    }
}
