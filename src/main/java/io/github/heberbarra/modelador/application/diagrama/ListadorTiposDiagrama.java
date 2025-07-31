/*
 * Copyright (C) 2025 Heber Ferreira Barra, Matheus de Assis de Paula, Matheus Jun Alves Matuda.
 *
 * Licensed under the Massachusetts Institute of Technology (MIT) License.
 * You may obtain a copy of the license at:
 *
 *   https://choosealicense.com/licenses/mit/
 *
 * A short and simple permissive license with conditions only requiring preservation of copyright and license notices.
 * Licensed works, modifications, and larger works may be distributed under different terms and without source code.
 *
 */

package io.github.heberbarra.modelador.application.diagrama;

import io.github.heberbarra.modelador.domain.model.TipoDiagramaDTO;
import java.util.List;

public class ListadorTiposDiagrama {

    public static List<TipoDiagramaDTO> pegarDiagramasUML() {
        return List.of(
                new TipoDiagramaDTO("DDA", "DDA", "Diagrama de Atividades"),
                new TipoDiagramaDTO("DCU", "DCU", "Diagrama de Caso de Uso"),
                new TipoDiagramaDTO("DCL", "DCL", "Diagrama de Classes"),
                new TipoDiagramaDTO("DCM", "DCM", "Diagrama de Comunicação"),
                new TipoDiagramaDTO("DCP", "DCP", "Diagrama de Componentes"),
                new TipoDiagramaDTO("DEC", "DEC", "Diagrama de Estrutura Composta"),
                new TipoDiagramaDTO("DDI", "DDI", "Diagrama de Implantação"),
                new TipoDiagramaDTO("DVI", "DVI", "Diagrama de Visão Geral da Interação"),
                new TipoDiagramaDTO("DDO", "DDO", "Diagrama de Objetos"),
                new TipoDiagramaDTO("DDP", "DDP", "Diagrama de Pacotes"),
                new TipoDiagramaDTO("DPF", "DPF", "Diagrama de Perfil"),
                new TipoDiagramaDTO("DTE", "DTE", "Diagrama de Transição de Estados"),
                new TipoDiagramaDTO("DDT", "DDT", "Diagrama de Tempo"),
                new TipoDiagramaDTO("DDS", "DDS", "Diagrama de Sequência"));
    }

    public static List<TipoDiagramaDTO> pegarDiagramasBancoDados() {
        return List.of(
                new TipoDiagramaDTO("DDD", "DDD", "Dicionário de Dados"),
                new TipoDiagramaDTO("DER", "DER", "Diagrama de Entidade Relacionamento"),
                new TipoDiagramaDTO("DRL", "DRL", "Diagrama do Modelo Relacional"),
                new TipoDiagramaDTO("MLR", "MLR", "Modelo Relacional"));
    }

    public static List<TipoDiagramaDTO> pegarDiagramasOutros() {
        return List.of(
                new TipoDiagramaDTO("FLX", "FLX", "Fluxograma"), new TipoDiagramaDTO("ORG", "ORG", "Organograma"));
    }
}
