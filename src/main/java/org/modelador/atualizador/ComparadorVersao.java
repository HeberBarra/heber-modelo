package org.modelador.atualizador;

import java.util.ArrayList;
import java.util.List;

public class ComparadorVersao {

    public static final int MAIOR = 1;
    public static final int IGUAL = 0;
    public static final int MENOR = -1;
    private static final String PREFIXO_VERSAO = "v";
    private String versaoPrograma;
    private String versaoRemota;

    private List<String> pegarPartesVersao(String versao) {
        List<String> partesVersao = new ArrayList<>(List.of(versao.split("\\.")));

        if (partesVersao.getLast().contains("-")) {
            List<String> ultimasPartesVersao = List.of(partesVersao.getLast().split("-"));
            partesVersao.removeLast();
            partesVersao.addAll(ultimasPartesVersao);
        }

        return partesVersao;
    }

    private SufixoVersao pegarSufixoVersao(List<String> partesVersao) {
        if (partesVersao.size() > 3) {
            return SufixoVersao.valueOf(partesVersao.getLast());
        }

        return SufixoVersao.RELEASE;
    }

    public int compararVersoes() {
        System.out.println(versaoPrograma);
        System.out.println(versaoRemota);

        if (versaoPrograma.equals(versaoRemota)) {
            return IGUAL;
        }

        List<String> partesVersaoPrograma = pegarPartesVersao(versaoPrograma);
        List<String> partesVersaoRemota = pegarPartesVersao(versaoRemota);
        SufixoVersao sufixoVersaoPrograma = pegarSufixoVersao(partesVersaoPrograma);
        SufixoVersao sufixoVersaoRemota = pegarSufixoVersao(partesVersaoRemota);
        if (sufixoVersaoRemota.valor > sufixoVersaoPrograma.valor) {
            return MAIOR;
        } else if (sufixoVersaoRemota.valor < sufixoVersaoPrograma.valor) {
            return MENOR;
        }

        for (int i = 0; i < 3; i++) {
            if (partesVersaoRemota.get(i).compareToIgnoreCase(partesVersaoPrograma.get(i)) == 1) {
                return MAIOR;
            }

            if (partesVersaoRemota.get(i).compareToIgnoreCase(partesVersaoPrograma.get(i)) == -1) {
                return MENOR;
            }
        }

        return IGUAL;
    }

    private String removerPrefixo(String versao) {
        if (versao.toLowerCase().startsWith(PREFIXO_VERSAO)) {
            return versao.substring(PREFIXO_VERSAO.length());
        }

        return versao;
    }

    public ComparadorVersao(String versaoPrograma, String versaoRemota) {
        this.versaoPrograma = removerPrefixo(versaoPrograma);
        this.versaoRemota = removerPrefixo(versaoRemota);
    }

    public void setVersaoPrograma(String versaoPrograma) {
        this.versaoPrograma = removerPrefixo(versaoPrograma);
    }

    public void setVersaoRemota(String versaoRemota) {
        this.versaoRemota = removerPrefixo(versaoRemota);
    }
}
