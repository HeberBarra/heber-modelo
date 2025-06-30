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
import { DiagramaJSON, TipoComponenteJSON } from "./diagramaJSON.js";

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
        const diagramaJSON: DiagramaJSON = await response.json();

        let fieldset: HTMLFieldSetElement = document.createElement("fieldset");
        let nomeDiagrama: HTMLLegendElement = document.createElement("legend");
        nomeDiagrama.innerText = diagramaJSON.nome;
        fieldset.append(nomeDiagrama);
        fieldset.classList.add("componentes-diagrama");

        diagramaJSON.elementos.forEach((tipoElemento: TipoComponenteJSON): void => {
          fieldset.append(
            this.gerarBotaoCriarElemento(
              callbackCriarComponente,
              tipoElemento.nome,
              tipoElemento.tipo,
            ),
          );
        });

        return fieldset;
      },
    );
  }
}
