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
  constructor(nome: string, htmlElemento: HTMLElement, sufixo: string, label: string) {
    this._nome = nome;
    this._htmlElemento = htmlElemento;
    this._sufixo = sufixo;
    this._label = label;
  }

  private static _CLASSE_PROPRIEDADE_CUSTOMIZADA: string = "custom";
  protected _nome: string;
  protected _htmlElemento: HTMLElement;
  protected _sufixo: string;
  protected _label: string;

  static get CLASSE_PROPRIEDADE_CUSTOMIZADA(): string {
    return this._CLASSE_PROPRIEDADE_CUSTOMIZADA;
  }

  get nome(): string {
    return this._nome;
  }

  protected pegarValorPropriedade(): string {
    return this._htmlElemento.getAttribute(this._nome) ?? "";
  }

  public definirValorPropriedade(valor: string): void {
    this._htmlElemento.setAttribute(this._nome, `${valor}${this._sufixo}`);
  }

  private formatarLabel(): string {
    return this._label.substring(0, 1).toUpperCase() + this._label.substring(1) + ":";
  }

  public criarElementoInputPropriedade(): HTMLLabelElement {
    let labelInput: HTMLLabelElement = document.createElement("label");
    let elementoInput: HTMLInputElement = document.createElement("input");

    elementoInput.addEventListener("input", (): void => {
      this.definirValorPropriedade(elementoInput.value);
    });

    elementoInput.value = this.pegarValorPropriedade();
    labelInput.innerText = this.formatarLabel();
    labelInput.appendChild(elementoInput);
    labelInput.classList.add(PropriedadeComponente.CLASSE_PROPRIEDADE_CUSTOMIZADA);

    return labelInput;
  }
}

export class PropriedadeInnerText extends PropriedadeComponente {
  constructor(htmlElemento: HTMLElement, sufixo: string, label: string) {
    super("innerText", htmlElemento, sufixo, label);
  }

  definirValorPropriedade(valor: string) {
    this._htmlElemento.innerText = `${valor}${this._sufixo}`;
  }

  protected pegarValorPropriedade(): string {
    return this._htmlElemento.innerText;
  }
}
