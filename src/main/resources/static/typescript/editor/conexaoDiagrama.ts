/*
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

export const calcularAnguloConexao = (x1: number, y1: number, x2: number, y2: number): number => {
  let deltaX: number = x2 - x1;
  let deltaY: number = y2 - y1;

  return Math.atan2(deltaY, deltaX);
};

export const calcularDistanciaConexao = (
  x1: number,
  y1: number,
  x2: number,
  y2: number,
): number => {
  let deltaX: number = x2 - x1;
  let deltaY: number = y2 - y1;

  return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
};
