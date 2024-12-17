package io.github.heberbarra.modelador.impressor;

import java.util.Set;

public class ImpressorCodigosSaida {

    private int tamanhoCodigoMaisLongo;
    private final ColetorCodigosSaida coletorCodigosSaida;

    public ImpressorCodigosSaida(ColetorCodigosSaida coletorCodigosSaida) {
        this.coletorCodigosSaida = coletorCodigosSaida;
    }

    public void imprimirCodigos() {
        coletorCodigosSaida.coletarCodigosEnum();
        Set<DadosCodigoSaida> codigosSaida = coletorCodigosSaida.getDadosCodigosSaida();
        tamanhoCodigoMaisLongo = calcularTamanhoNomeMaisLongo(codigosSaida);

        StringBuilder resultadoBuilder = new StringBuilder();
        for (DadosCodigoSaida codigoSaida : codigosSaida.stream().sorted().toList()) {
            resultadoBuilder.append(formatarDado(codigoSaida.nome(), codigoSaida.codigo()));
            resultadoBuilder.append("\n");
        }

        System.out.println(resultadoBuilder);
    }

    private String formatarDado(String nomeCodigo, int codigo) {
        return ajustarNome("%3d".formatted(codigo), tamanhoCodigoMaisLongo) + "|\t "
                + ajustarNome(nomeCodigo, tamanhoCodigoMaisLongo);
    }

    private String ajustarNome(String nome, int tamanho) {
        int tamanhoAjuste = Math.divideExact(tamanho - nome.length(), 2);
        tamanhoAjuste = Math.max(tamanhoAjuste, 0);

        return " ".repeat(tamanhoAjuste) + nome + " ".repeat(tamanhoAjuste);
    }

    private int calcularTamanhoNomeMaisLongo(Set<DadosCodigoSaida> codigosSaida) {
        if (codigosSaida.isEmpty()) {
            return 0;
        }

        DadosCodigoSaida codigoMaisLongo = null;
        for (DadosCodigoSaida codigoSaida : codigosSaida) {
            if (codigoMaisLongo == null
                    || codigoSaida.nome().length() > codigoMaisLongo.nome().length()) {
                codigoMaisLongo = codigoSaida;
            }
        }

        assert codigoMaisLongo != null;
        return codigoMaisLongo.nome().length();
    }
}
