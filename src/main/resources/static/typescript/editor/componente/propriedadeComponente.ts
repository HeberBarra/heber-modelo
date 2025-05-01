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
export abstract class PropriedadeComponente<T> {
  constructor(nome: string, htmlElemento: HTMLElement) {
    this._nome = nome;
    this._htmlElemento = htmlElemento;
  }

  protected _nome: string;
  protected _htmlElemento: HTMLElement;

  get nome(): string {
    return this._nome;
  }

  public abstract definirValorPropriedade(valor: T): void;

  public abstract atualizarValorInput(event: InputEvent): void;
}

export class PropriedadePixel extends PropriedadeComponente<number> {
  definirValorPropriedade(valor: number): void {
    this._htmlElemento.setAttribute(this._nome, `${valor}px`);
  }

  atualizarValorInput(event: InputEvent): void {}
}
