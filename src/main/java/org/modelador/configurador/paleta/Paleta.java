package org.modelador.configurador.paleta;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import org.tomlj.TomlTable;

public class Paleta {

    protected static TomlTable templatePaleta;
    protected static TomlTable informacoesPaleta;
    protected static Map<String, Color> cacheCores = new HashMap<>();

    protected static boolean verificarFormato(String hexCode) {
        // Fonte regex:
        // https://stackoverflow.com/questions/1636350/how-to-identify-a-given-string-is-hex-color-format
        return hexCode.matches("^#(?:[0-9a-fA-F]{3}){1,2}$");
    }

    protected static int converterStringParaHexadecimal(String hexCode) {
        return Integer.parseInt(hexCode.substring(1), 16);
    }

    protected static String pegarHexCode(TomlTable tabelaPaleta, String chaveCor) {
        TomlTable tabelaTomlPaleta = templatePaleta.getTable("paleta");

        if (tabelaTomlPaleta != null) {
            return (String) tabelaTomlPaleta.get(chaveCor);
        }

        return (String) tabelaPaleta.get(chaveCor);
    }

    public static void setTemplatePaleta(TomlTable templatePaleta) {
        Paleta.templatePaleta = templatePaleta;
    }

    public static void setInformacoesPaleta(TomlTable informacoesPaleta) {
        Paleta.informacoesPaleta = informacoesPaleta;
    }

    public static void limparCache() {
        cacheCores.clear();
    }

    public static Color pegarCor(String chaveCor) {

        if (cacheCores.containsKey(chaveCor)) {
            return cacheCores.get(chaveCor);
        }

        String hexCode = pegarHexCode(informacoesPaleta, chaveCor);
        if (hexCode == null || !verificarFormato(hexCode)) {
            hexCode = pegarHexCode(templatePaleta, chaveCor);
        }

        Color cor = new Color(converterStringParaHexadecimal(hexCode));
        cacheCores.put(chaveCor, cor);

        return cor;
    }
}
