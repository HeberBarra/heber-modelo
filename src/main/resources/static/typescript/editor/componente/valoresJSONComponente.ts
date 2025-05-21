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
export interface ValoresJSONComponente {
  classesElemento: string[];
  valorHtmlInterno: string;
  propriedades: JSONPropriedade[];
}

export interface JSONPropriedade {
  classeElemento: string;
  nomePropriedade: string;
  sufixo: string;
}
