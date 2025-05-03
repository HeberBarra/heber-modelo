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

export const converterPixeisParaNumero = (valorPixeis: string): number => {
  return Number(valorPixeis.substring(0, valorPixeis.length - 2));
};

export const converterRadianosParaGraus = (valorRadianos: number) => {
  return (valorRadianos * 180) / Math.PI;
};
