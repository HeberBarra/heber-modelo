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
export class PropriedadeComponente {
  constructor(nome: string, htmlElemento: HTMLElement, sufixo: string) {
    this._nome = nome;
    this._htmlElemento = htmlElemento;
    this._sufixo = sufixo;
  }

  protected _nome: string;
  protected _htmlElemento: HTMLElement;
  protected _sufixo: string;

  get nome(): string {
    return this._nome;
  }

  public definirValorPropriedade(valor: string): void {
    this._htmlElemento.setAttribute(this._nome, `${valor}${this._sufixo}`);
  }

  public atualizarValorInput(event: InputEvent): void {
    let elementoInput: HTMLInputElement = event.target as HTMLInputElement;
    elementoInput.innerText = `${this._htmlElemento.getAttribute(this._nome)}`;
  }

  public criarElementoInputPropriedade(): HTMLInputElement {
    let elementoInput: HTMLInputElement = document.createElement("input");

    elementoInput.addEventListener("input", (): void => {
      this.definirValorPropriedade(elementoInput.value);
    });

    return elementoInput;
  }
}

export class PropriedadeInnerText extends PropriedadeComponente {
  constructor(htmlElemento: HTMLElement, sufixo: string) {
    super("innerText", htmlElemento, sufixo);
  }

  definirValorPropriedade(valor: string) {
    this._htmlElemento.innerText = `${valor}${this._sufixo}`;
  }

  atualizarValorInput(event: InputEvent) {
    let elementoInput: HTMLInputElement = event.target as HTMLInputElement;
    elementoInput.innerText = this._htmlElemento.innerText;
  }
}
