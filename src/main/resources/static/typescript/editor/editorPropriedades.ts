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
import { PropriedadeComponente } from "./componente/propriedade/propriedadeComponente.js";

export class InputPropriedade {
  private readonly _elementoInput: HTMLInputElement | null;
  private readonly nomePropriedade: string;

  constructor(elementoInput: HTMLInputElement | null, propriedade: string) {
    this._elementoInput = elementoInput;
    this.nomePropriedade = propriedade;
  }

  public atualizar(elementoSelecionado: HTMLElement | null): void {
    atualizarValorInput(elementoSelecionado, this._elementoInput, this.nomePropriedade);
  }

  get elementoInput(): HTMLInputElement | null {
    return this._elementoInput;
  }
}

const verificarStringNumero = (valor: string): boolean => {
  let regexVerificarNumero: RegExp = /^-?\d+$/g;
  return regexVerificarNumero.test(valor);
};

// NOTE: Não funciona com parênteses.
const calcularExpressao = (expressao: string): number | null => {
  let regexFiltroExpressao: RegExp = /(?<!\S)[0-9]+(?:[-+%~^*\/]+?\d+(?:\.\d+)?)+(?!\S)/g;

  if (expressao.match(regexFiltroExpressao) === null) {
    return null;
  }

  return eval(expressao);
};

const ajustarValorAtributo = (valor: string): string => {
  if (verificarStringNumero(valor)) {
    return `${parseFloat(valor)}px`;
  }

  let resultadoExpressao: number | null = calcularExpressao(valor);

  if (resultadoExpressao === null) {
    return "0px";
  }

  return `${resultadoExpressao}px`;
};

export const modificarPropriedadeElemento = (
  elemento: HTMLElement | null,
  inputAtributo: HTMLInputElement | null,
  nomePropriedade: string,
): void => {
  if (elemento === null || inputAtributo === null) return;

  let novoValorAtributo: string = ajustarValorAtributo(inputAtributo.value);
  elemento.style.setProperty(nomePropriedade, novoValorAtributo);
};

export const atualizarValorInput = (
  elemento: HTMLElement | null,
  inputAtributo: HTMLInputElement | null,
  nomePropriedade: string,
): void => {
  if (elemento === null || elemento === undefined || inputAtributo === null) return;

  let valorPropriedade: string = getComputedStyle(elemento).getPropertyValue(nomePropriedade);
  inputAtributo.value = valorPropriedade.substring(0, valorPropriedade.length - 2);
};

export const atualizarInputs = (
  elementoSelecionado: HTMLElement | null,
  inputs: InputPropriedade[],
): void => {
  if (elementoSelecionado !== null) {
    inputs.forEach((input: InputPropriedade): void => input.atualizar(elementoSelecionado));
    return;
  }

  inputs.forEach((input: InputPropriedade): void => {
    if (input.elementoInput !== null) {
      input.elementoInput.value = "";
    }
  });
};

export const limparPropriedades = (abaPropriedades: HTMLElement | null): void => {
  if (abaPropriedades === null) return;

  let propriedades: NodeListOf<HTMLElement> = abaPropriedades.querySelectorAll(
    `.${PropriedadeComponente.CLASSE_PROPRIEDADE_CUSTOMIZADA}`,
  );
  propriedades.forEach((propriedade) => propriedade.remove());
};
