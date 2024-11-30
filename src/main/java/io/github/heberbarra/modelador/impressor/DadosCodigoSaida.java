package io.github.heberbarra.modelador.impressor;

public record DadosCodigoSaida(String nome, int codigo) implements Comparable<DadosCodigoSaida> {

    @Override
    public int compareTo(DadosCodigoSaida dadosCodigoSaida) {
        return Integer.compare(codigo, dadosCodigoSaida.codigo());
    }
}
