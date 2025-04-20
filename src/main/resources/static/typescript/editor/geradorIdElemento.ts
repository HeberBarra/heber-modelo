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
export class GeradorIdElemento {
  private _idAtual: number = 0;

  constructor(idInicial: number) {
    this._idAtual = idInicial;
  }

  // Incrementa o ID atual e retorna o novo valor
  public gerarProximoId(): number {
    this._idAtual++;
    return this._idAtual;
  }

  set idAtual(value: number) {
    this._idAtual = value;
  }
}
