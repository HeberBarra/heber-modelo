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
import { CLASSE_COMUM_ELEMENTOS } from "../classesCSSElementos.js";

export class FabricaComponente {
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
        let propriedades: PropriedadeComponente[] = [];

        elementoHTML.innerHTML = valores.valorHtmlInterno;
        elementoHTML.classList.add(CLASSE_COMUM_ELEMENTOS);
        elementoHTML.classList.add(valores.classesElemento.join(" "));
        valores.propriedades.forEach((propriedade: JSONPropriedade): void => {
          let propriedadeComponente: PropriedadeComponente | null =
            fabricaPropriedade.criarPropriedade(
              propriedade.nomePropriedade,
              propriedade.sufixo,
              elementoHTML.querySelector(`.${propriedade.classeElemento}`) as HTMLElement,
            );
          if (propriedadeComponente !== null) {
            propriedades.push(propriedadeComponente);
          }
        });

        return new ComponenteDiagrama(elementoHTML, propriedades);
      },
    );
  }
}
