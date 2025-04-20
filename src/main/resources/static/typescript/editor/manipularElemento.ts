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
import { CLASSE_COMUM_ELEMENTOS } from "./classesCSSElementos.js";
import { FabricaElemento } from "./fabricaElemento.js";
import { GeradorIdElemento } from "./geradorIdElemento.js";

/***********************************/
/* CRIAÇÃO E EXCLUSÃO DE ELEMENTOS */
/***********************************/

let fabricaElemento: FabricaElemento = new FabricaElemento();
let geradorIdElemento: GeradorIdElemento = new GeradorIdElemento(0);

export const criarElemento = (
  elementoPai: HTMLElement | null,
  nomeElemento: string,
): HTMLDivElement | null => {
  if (elementoPai === null) return null;

  let nomeElementoFormatado = nomeElemento.toLowerCase();
  let promiseHtml: Promise<string> = fabricaElemento.pegarHTMLElemento(nomeElementoFormatado);
  let promiseClasses: Promise<string[]> =
    fabricaElemento.pegarClassesElemento(nomeElementoFormatado);

  let novoElemento: HTMLDivElement = document.createElement("div");
  novoElemento.classList.add(CLASSE_COMUM_ELEMENTOS);
  promiseClasses.then((classes) => classes.forEach((classe) => novoElemento.classList.add(classe)));
  promiseHtml.then((valor) => {
    novoElemento.innerHTML = valor;
    novoElemento.setAttribute("data-id", `${geradorIdElemento.gerarProximoId()}`);
  });
  elementoPai.appendChild(novoElemento);

  return novoElemento;
};

export const apagarElemento = (
  elementoPai: HTMLElement | null,
  elemento: HTMLElement | null,
): void => {
  if (elementoPai === null || elemento === null) return;

  elementoPai.removeChild(elemento);
};

/********************************/
/* MOVIMENTAÇÃO DOS COMPONENTES */

/********************************/

export enum DirecoesMovimento {
  CIMA,
  BAIXO,
  DIREITA,
  ESQUERDA,
}

const incrementarValor = (valorPixels: string, incremento: number): string => {
  let valorInicial: number = Number(valorPixels.substring(0, valorPixels.length - 2));

  return valorInicial + incremento + "px";
};

export const moverElemento = (
  elemento: HTMLElement | null,
  direcao: DirecoesMovimento,
  incremento: number,
) => {
  if (elemento === null) return;

  switch (direcao) {
    case DirecoesMovimento.CIMA:
      elemento.style.top = incrementarValor(getComputedStyle(elemento).top, -incremento);
      break;

    case DirecoesMovimento.BAIXO:
      elemento.style.top = incrementarValor(getComputedStyle(elemento).top, incremento);
      break;

    case DirecoesMovimento.ESQUERDA:
      elemento.style.left = incrementarValor(getComputedStyle(elemento).left, -incremento);
      break;

    case DirecoesMovimento.DIREITA:
      elemento.style.left = incrementarValor(getComputedStyle(elemento).left, incremento);
  }
};
