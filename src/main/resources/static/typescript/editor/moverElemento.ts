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

enum DirecoesMovimento {
  CIMA,
  BAIXO,
  DIREITA,
  ESQUERDA,
}

const incrementarValor = (valorPixels: string, incremento: number): string => {
  let valorInicial: number = Number(valorPixels.substring(0, valorPixels.length - 2));

  return valorInicial + incremento + "px";
};

const moverElemento = (
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

export { DirecoesMovimento, moverElemento };
