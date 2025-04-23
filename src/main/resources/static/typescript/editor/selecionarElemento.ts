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

import { CLASSE_ELEMENTO_SELECIONADO } from "./classesCSSElementos.js";
import { converterPixeisParaNumero } from "../conversor/conversor.js";

const setaSuperior: HTMLElement = document.querySelector(".seta-superior") as HTMLElement;
const setaInferior: HTMLElement = document.querySelector(".seta-inferior") as HTMLElement;
const setaDireita: HTMLElement = document.querySelector(".seta-direita") as HTMLElement;
const setaEsquerda: HTMLElement = document.querySelector(".seta-esquerda") as HTMLElement;
let elementoSelecionado: HTMLElement | null;
let setas: NodeListOf<HTMLElement> = document.querySelectorAll(".seta");

export const selecionarElemento = (elemento: HTMLElement): HTMLElement => {
  elementoSelecionado?.classList.remove(CLASSE_ELEMENTO_SELECIONADO);
  elementoSelecionado = elemento;
  elementoSelecionado.classList.add(CLASSE_ELEMENTO_SELECIONADO);

  moverSetas(elemento);

  return elementoSelecionado;
};

export const moverSetas = (elemento: HTMLElement): void => {
  if (elemento.classList.contains("elemento-conexao") || elemento.classList.length === 0) {
    return;
  }

  let estiloElemento: CSSStyleDeclaration = getComputedStyle(elemento);
  let alturaElemento: number = converterPixeisParaNumero(estiloElemento.height);
  let larguraElemento: number = converterPixeisParaNumero(estiloElemento.width);
  let xElemento: number = converterPixeisParaNumero(estiloElemento.left);
  let yElemento: number = converterPixeisParaNumero(estiloElemento.top);

  let centroVerticalElemento: number = yElemento + alturaElemento / 2;
  let centroHorizontalElemento: number = xElemento + larguraElemento / 2;
  let alturaSeta: number = converterPixeisParaNumero(getComputedStyle(setaSuperior).height);
  let larguraSeta: number = converterPixeisParaNumero(getComputedStyle(setaSuperior).width);

  setas.forEach((seta) => {
    seta.style.removeProperty("display");
  });

  setaSuperior.style.top = `${centroVerticalElemento - alturaSeta * 2.5}px`;
  setaSuperior.style.left = `${centroHorizontalElemento - larguraSeta / 2}px`;

  setaInferior.style.top = `${centroVerticalElemento + alturaSeta * 1.5}px`;
  setaInferior.style.left = `${centroHorizontalElemento - larguraSeta / 2}px`;

  setaDireita.style.top = `${centroVerticalElemento - alturaSeta / 2}px`;
  setaDireita.style.left = `${centroHorizontalElemento + larguraSeta * 2}px`;

  setaEsquerda.style.top = `${centroVerticalElemento - alturaSeta / 2}px`;
  setaEsquerda.style.left = `${centroHorizontalElemento - larguraSeta * 3}px`;
};

export const removerSelecao = (): HTMLElement | null => {
  elementoSelecionado?.classList.remove(CLASSE_ELEMENTO_SELECIONADO);
  elementoSelecionado = null;

  setas.forEach((seta) => {
    seta.setAttribute("display", "none");
  });

  return elementoSelecionado;
};
