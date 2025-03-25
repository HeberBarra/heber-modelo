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

export const esconderSecoesMenosPrimeira = (secoes: NodeListOf<HTMLElement>): void => {
  for (let i: number = 0; i < secoes.length; i++) {
    if (i === 0) continue;

    secoes[i].style.display = "none";
  }
};

let indexPainelDireito: number = 0;
let indexPainelEsquerdo: number = 0;

export const mudarSecao = (secoes: NodeListOf<HTMLElement>, avancar: boolean): void => {
  let indexSecaoAtual: number =
    secoes[0].parentElement?.id === "painel-direito" ? indexPainelDireito : indexPainelEsquerdo;

  if (avancar) {
    indexSecaoAtual++;
  } else {
    indexSecaoAtual--;
  }

  if (indexSecaoAtual < 0) {
    indexSecaoAtual = secoes.length - 1;
  }

  if (indexSecaoAtual === secoes.length) {
    indexSecaoAtual = 0;
  }

  for (let i: number = 0; i < secoes.length; i++) {
    if (i === indexSecaoAtual) {
      secoes[i].style.removeProperty("display");
      continue;
    }

    secoes[i].style.display = "none";
  }

  if (secoes[0].parentElement?.id === "painel-direito") {
    indexPainelDireito = indexSecaoAtual;
  } else {
    indexPainelEsquerdo = indexSecaoAtual;
  }
};
