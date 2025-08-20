/**
 * Copyright (C) 2025 Heber Ferreira Barra, João Gabriel de Cristo, Matheus Jun Alves Matuda.
 *
 * Licensed under the Massachusetts Institute of Technology (MIT) License.
 * You may obtain a copy of the license at:
 *
 *     https://choosealicense.com/licenses/mit/
 *
 * A short and simple permissive license with conditions only requiring preservation of copyright and license notices.
 * Licensed works, modifications, and larger works may be distributed under different terms and without source code.
 */
import { ResponseTraducaoJSON } from "../../model/response/responseTraducaoJSON.js";
import {
  ResponseDiagramaJSON,
  TipoComponenteJSON,
} from "../../model/response/responseDiagramaJSON";

export class CarregadorDiagrama {
  private gerarBotaoCriarElemento(
    callbackCriarComponente: (event: Event) => void,
    nomeElemento: string,
    tipoElemento: string,
  ): HTMLButtonElement {
    let botao: HTMLButtonElement = document.createElement("button");
    botao.classList.add("btn-criar-elemento");
    botao.setAttribute("data-nome-elemento", tipoElemento);
    botao.title = nomeElemento;
    botao.innerText = nomeElemento.toUpperCase();
    botao.addEventListener("click", callbackCriarComponente);

    return botao;
  }

  public async carregarDiagrama(
    nomeDiagrama: string,
    callbackCriarComponente: (event: Event) => void,
  ): Promise<HTMLFieldSetElement> {
    return await fetch(`diagramas/${nomeDiagrama}.json`).then(
      async (response: Response): Promise<HTMLFieldSetElement> => {
        const diagramaJSON: ResponseDiagramaJSON = await response.json();

        let valorNomeDiagrama: string = diagramaJSON.nome;
        if (diagramaJSON.chaveI18N !== null && diagramaJSON.chaveI18N !== undefined) {
          valorNomeDiagrama = await fetch(`/traducao/${diagramaJSON.chaveI18N}`).then(
            async (response: Response): Promise<string> => {
              let responseTraducao: ResponseTraducaoJSON = await response.json();
              return responseTraducao.mensagem;
            },
          );
        }

        let fieldset: HTMLFieldSetElement = document.createElement("fieldset");
        let nomeDiagrama: HTMLLegendElement = document.createElement("legend");
        nomeDiagrama.innerText = valorNomeDiagrama;
        fieldset.append(nomeDiagrama);
        fieldset.classList.add("componentes-diagrama");

        for (const tipoElemento of diagramaJSON.elementos) {
          let nomeElemento: string = tipoElemento.nome;

          if (tipoElemento.chaveI18N !== null && tipoElemento.chaveI18N !== undefined) {
            nomeElemento = await fetch(`/traducao/${tipoElemento.chaveI18N}`).then(
              async (response: Response): Promise<string> => {
                let responseTraducao: ResponseTraducaoJSON = await response.json();
                return responseTraducao.mensagem;
              },
            );
          }

          fieldset.append(
            this.gerarBotaoCriarElemento(callbackCriarComponente, nomeElemento, tipoElemento.tipo),
          );
        }

        return fieldset;
      },
    );
  }
}
