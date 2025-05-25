/**
 *
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
import { PropriedadeComponente, PropriedadeInnerText } from "./propriedadeComponente.js";

export class FabricaPropriedade {
  public criarPropriedade(
    nomePropriedade: string,
    sufixo: string,
    elementoHTML: HTMLElement,
    label: string,
  ): PropriedadeComponente | null {
    if (nomePropriedade === "innerText") {
      return new PropriedadeInnerText(elementoHTML, sufixo, label);
    } else {
      return new PropriedadeComponente(nomePropriedade, elementoHTML, sufixo, label);
    }
  }
}
