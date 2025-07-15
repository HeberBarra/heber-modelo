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
import { JSONPropriedade, ValoresJSONComponente } from "./valoresJSONComponente.js";
import { ComponenteDiagrama } from "./componenteDiagrama.js";
import { FabricaPropriedade } from "./propriedade/fabricaPropriedade.js";
import { PropriedadeComponente } from "./propriedade/propriedadeComponente.js";

export class FabricaComponente {
  public static PROPRIEDADE_NOME_COMPONENTE: string = "data-nome-componente";
  private static _CLASSE_COMUM_ELEMENTOS: string = "componente";

  static get CLASSE_COMUM_ELEMENTOS(): string {
    return this._CLASSE_COMUM_ELEMENTOS;
  }

  private async pegarJSON(nomeComponente: string): Promise<ValoresJSONComponente> {
    return await fetch(`/elementos/${nomeComponente.toLowerCase()}.json`).then(
      (response: Response): Promise<ValoresJSONComponente> => response.json(),
    );
  }

  public async criarComponente(nomeComponente: string): Promise<ComponenteDiagrama> {
    return this.pegarJSON(nomeComponente).then(
      (valores: ValoresJSONComponente): ComponenteDiagrama => {
        let fabricaPropriedade: FabricaPropriedade = new FabricaPropriedade();
        let elementoHTML: HTMLDivElement = document.createElement("div");
        elementoHTML.innerHTML = valores.valorHtmlInterno;
        elementoHTML.setAttribute(FabricaComponente.PROPRIEDADE_NOME_COMPONENTE, nomeComponente);
        elementoHTML.classList.add(FabricaComponente.CLASSE_COMUM_ELEMENTOS);
        elementoHTML.classList.add(valores.classesElemento.join(" "));
        let componente: ComponenteDiagrama = new ComponenteDiagrama(elementoHTML, []);
        valores.propriedades.forEach((propriedade: JSONPropriedade): void => {
          let propriedadeComponente: PropriedadeComponente | null =
            fabricaPropriedade.criarPropriedade(
              propriedade.nomePropriedade,
              propriedade.sufixo,
              componente,
              propriedade.label,
              propriedade.classeElemento,
            );
          if (propriedadeComponente !== null) {
            componente.propriedades.push(propriedadeComponente);
          }
        });

        return componente;
      },
    );
  }
}
