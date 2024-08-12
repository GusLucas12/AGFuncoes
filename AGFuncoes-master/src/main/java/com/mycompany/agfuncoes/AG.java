package com.mycompany.agfuncoes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AG {

    public Individuo executar(IndividuoFactory indFact, int nPop, int nElite, int numGer) {
        List<Individuo> popIni = new ArrayList<>();

        // Inicializa a população
        for (int i = 0; i < nPop; i++) {
            popIni.add(indFact.getIndividuo());
        }

        for (int g = 0; g < numGer; g++) {
            // Gera nova população usando recombinação e mutação
            List<Individuo> popAux = new ArrayList<>(popIni);
            List<Individuo> filhos = new ArrayList<>();

            // Recombinação
            for (int i = 0; i < nPop / 2; i++) {
                Individuo pai1 = popAux.remove((int) (Math.random() * popAux.size()));
                Individuo pai2 = popAux.remove((int) (Math.random() * popAux.size()));
                filhos.addAll(pai1.recombinar(pai2));
            }

            // Mutação
            List<Individuo> mutantes = new ArrayList<>();
            for (Individuo ind : popIni) {
                mutantes.add(ind.mutar());
            }

            // Combina populações
            List<Individuo> joinList = new ArrayList<>();
            joinList.addAll(popIni);
            joinList.addAll(filhos);
            joinList.addAll(mutantes);

            // Nova população
            List<Individuo> newPop = new ArrayList<>();
            Elite(joinList, nElite, true, newPop); // Seleção dos melhores (elitismo)
            Roleta(joinList, nPop - nElite, true, newPop); // Seleção via roleta

            popIni = new ArrayList<>(newPop);

            // Avalia o melhor indivíduo da geração atual
            Individuo melhorIndividuo = getMelhorIndividuo(popIni, true);

              // Imprime o número da geração, os genes e a avaliação do melhor indivíduo
            System.out.print("Geração " + g + ": ");
            //Levy
            //((LevyIndividuo) melhorIndividuo).imprimirGenes();
            //Langermann
            //((LangermannIndividuo) melhorIndividuo).imprimirGenes();
            //Dixon-Price
            ((DixonPriceIndividuo) melhorIndividuo).imprimirGenes();
            System.out.println(" Avaliação: " + melhorIndividuo.getAvaliacao());
            
          
          
        }

        // Retorna o melhor indivíduo após todas as gerações
        return getMelhorIndividuo(popIni, true);
    }

    private boolean NaoExisteEmNewPop(List<Individuo> newPop, Individuo ind) {
        for (Individuo individuo : newPop) {
            if (individuo.equals(ind)) {
                return false;
            }
        }
        return true;
    }

    public void Roleta(List<Individuo> joinList, int nSelecao, boolean isMinimizacao, List<Individuo> newPop) {
        int qtd = 0;
        Random random = new Random();

        // Calcular a soma das avaliações
        double somaAvaliacoes = 0;
        for (Individuo ind : joinList) {
            if (isMinimizacao) {
                somaAvaliacoes += (1.0 / ((Math.pow(0.1, 4)) + ind.getAvaliacao())); // Inverter avaliação para minimização
            } else {
                somaAvaliacoes += ind.getAvaliacao();
            }
        }

        while (qtd < nSelecao) {
            //b. gerar um numero aleatorio entre 0 e somaAvaliações
            double valorAleatorio = random.nextDouble() * somaAvaliacoes;
            double somaParcial = 0;
            Individuo IndSelecionado = null;

            //c. percorrer a lista de individuos somando suas avaliações até soma ser >= ao numero aleatorio
            for (Individuo ind : joinList) {
                if (isMinimizacao) {
                    somaParcial += (1.0 / (Math.pow(0.1, 4) + ind.getAvaliacao()));
                } else {
                    somaParcial += ind.getAvaliacao();
                }

                //d. O indice em que o loop anterior parar e o indice do individuo selecionado
                if (somaParcial >= valorAleatorio) {
                    IndSelecionado = ind;
                    break;
                }
            }

            if (IndSelecionado != null) {
                joinList.remove(IndSelecionado);
                if (NaoExisteEmNewPop(newPop, IndSelecionado)) {
                    newPop.add(IndSelecionado);
                    qtd++;
                }

                // Recalcular a soma das avaliações
                somaAvaliacoes = 0;
                for (Individuo ind : joinList) {
                    if (isMinimizacao) {
                        somaAvaliacoes += (1.0 / (0.1 + ind.getAvaliacao()));
                    } else {
                        somaAvaliacoes += ind.getAvaliacao();
                    }
                }
            }
        }
    }

    public void Elite(List<Individuo> joinList, int nElite, boolean isMinimizacao, List<Individuo> newPop) {
        int qtd = 0;

        // Ordenar a lista de indivíduos com base na avaliação
        if (isMinimizacao) {
            joinList.sort((ind1, ind2) -> Double.compare(ind1.getAvaliacao(), ind2.getAvaliacao()));
        } else {
            joinList.sort((ind1, ind2) -> Double.compare(ind2.getAvaliacao(), ind1.getAvaliacao()));
        }

        while (qtd < nElite && !joinList.isEmpty()) {
            Individuo IndSelecionado = joinList.remove(0); // Pega o melhor indivíduo

            if (NaoExisteEmNewPop(newPop, IndSelecionado)) {
                newPop.add(IndSelecionado);
                qtd++;
            }
        }
    }

    private Individuo getMelhorIndividuo(List<Individuo> pop, boolean isMinimizacao) {
        Individuo melhorIndividuo = null;
        for (Individuo ind : pop) {
            if (melhorIndividuo == null || (isMinimizacao ? ind.getAvaliacao() < melhorIndividuo.getAvaliacao() : ind.getAvaliacao() > melhorIndividuo.getAvaliacao())) {
                melhorIndividuo = ind;
            }
        }
        return melhorIndividuo;
    }
}
