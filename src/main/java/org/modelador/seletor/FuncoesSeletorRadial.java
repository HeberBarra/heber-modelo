package org.modelador.seletor;

import javax.swing.JLabel;
import org.modelador.base.componente.Icone;
import org.modelador.seletor.funcoes.CriadorEntidade;

public class FuncoesSeletorRadial {

    private static final int LARGURA_ICONE = 12;
    private static final int ALTURA_ICONE = 9;

    private static final JLabel LABEL_ENTIDADE =
            Icone.criarLabelImagem("assets/entidade.png", "Criar entidade", LARGURA_ICONE, ALTURA_ICONE);

    static {
        SeletorRadial.adicionarFuncao(LABEL_ENTIDADE, new CriadorEntidade());
    }
}
