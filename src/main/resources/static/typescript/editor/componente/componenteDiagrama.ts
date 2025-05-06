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
import { ComponenteDiagramaOuvido } from "./componenteDiagramaOuvido.js";
import { ComponenteDiagramaOuvinte } from "./componenteDiagramaOuvinte.js";
import { PropriedadeComponente } from "./propriedadeComponente.js";
import { converterPixeisParaNumero } from "../../conversor/conversor.js";

export enum LateralComponente {
  NORTE,
  SUL,
  LESTE,
  OESTE,
}

export class ComponenteDiagrama implements ComponenteDiagramaOuvido {
  constructor(htmlComponente: HTMLDivElement, propriedades: PropriedadeComponente<any>[] | null) {
    this._htmlComponente = htmlComponente;
    this._propriedades = propriedades ?? [];
  }

  private readonly _propriedades: PropriedadeComponente<any>[];
  private _ouvintes: ComponenteDiagramaOuvinte[] = [];
  protected readonly _htmlComponente: HTMLDivElement;
  protected _recebeSetas: boolean = true;

  get htmlComponente(): HTMLDivElement {
    return this._htmlComponente;
  }

  get recebeSetas(): boolean {
    return this._recebeSetas;
  }

  public definirValorPropriedade<T>(nomePropriedade: string, valorPropriedade: T): void {
    this._propriedades.forEach((propriedade) => {
      if (propriedade.nome === nomePropriedade) {
        propriedade.definirValorPropriedade(valorPropriedade);
        return;
      }
    });

    if (this._htmlComponente.hasAttribute(nomePropriedade)) {
      this._htmlComponente.setAttribute(nomePropriedade, String(valorPropriedade));
      return;
    }

    throw `Propriedade ${nomePropriedade} não existe no elemento: ${this._htmlComponente}`;
  }

  public pegarEstiloElemento(): CSSStyleDeclaration {
    return getComputedStyle(this._htmlComponente);
  }

  public pegarIDElemento(): number {
    return Number(this._htmlComponente.getAttribute("data-id"));
  }

  public calcularPontoLateralComponente(lateralComponente: LateralComponente): number[] {
    let estiloComponente: CSSStyleDeclaration = this.pegarEstiloElemento();
    let x: number = 0;
    let y: number = 0;

    switch (lateralComponente) {
      case LateralComponente.NORTE:
        x =
          converterPixeisParaNumero(estiloComponente.left) +
          converterPixeisParaNumero(estiloComponente.width) / 2;
        y = converterPixeisParaNumero(estiloComponente.top);
        break;

      case LateralComponente.SUL:
        x =
          converterPixeisParaNumero(estiloComponente.left) +
          converterPixeisParaNumero(estiloComponente.width) / 2;
        y =
          converterPixeisParaNumero(estiloComponente.top) +
          converterPixeisParaNumero(estiloComponente.height);
        break;

      case LateralComponente.OESTE:
        x = converterPixeisParaNumero(estiloComponente.left);
        y =
          converterPixeisParaNumero(estiloComponente.top) +
          converterPixeisParaNumero(estiloComponente.height) / 2;
        break;

      case LateralComponente.LESTE:
        x =
          converterPixeisParaNumero(estiloComponente.left) +
          converterPixeisParaNumero(estiloComponente.width);
        y =
          converterPixeisParaNumero(estiloComponente.top) +
          converterPixeisParaNumero(estiloComponente.height) / 2;
        break;
    }

    return [x, y];
  }

  adicionarOuvinte(ouvinte: ComponenteDiagramaOuvinte): void {
    this._ouvintes.push(ouvinte);
  }

  atualizarOuvintes(): void {
    this._ouvintes.forEach((ouvinte) => {
      ouvinte.atualizar(this._htmlComponente);
    });
  }
}
