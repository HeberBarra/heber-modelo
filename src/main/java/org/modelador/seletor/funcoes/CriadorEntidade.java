package org.modelador.seletor.funcoes;

import java.awt.MouseInfo;
import java.awt.Point;
import org.modelador.Principal;
import org.modelador.diagrama.Entidade;
import org.modelador.programa.JanelaPrincipal;

public class CriadorEntidade implements FuncaoSeletor {

    @Override
    public void executar() {
        Entidade novaEntidade = new Entidade();

        Point posicaoJanela = Principal.janelaPrincipal.getLocation();
        Point posicaoGrade = Principal.janelaPrincipal.getGrade().getLocation();
        Point posicaoMouse = MouseInfo.getPointerInfo().getLocation();
        int entidadeX = posicaoMouse.x - posicaoJanela.x - posicaoGrade.x;
        int entidadeY = posicaoMouse.y - posicaoJanela.y - posicaoGrade.y;

        novaEntidade.setLocation(entidadeX, entidadeY);
        JanelaPrincipal.diagrama.adicionarComponente(novaEntidade);
    }
}
