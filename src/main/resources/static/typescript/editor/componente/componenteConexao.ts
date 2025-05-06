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
import { ComponenteDiagrama, LateralComponente } from "./componenteDiagrama.js";
import { PropriedadeComponente } from "./propriedadeComponente.js";
import { ComponenteDiagramaOuvinte } from "./componenteDiagramaOuvinte.js";

export class ComponenteConexao extends ComponenteDiagrama implements ComponenteDiagramaOuvinte {
  private _x1: number;
  private _y1: number;
  private _x2: number;
  private _y2: number;
  private readonly _lateralPrimeiroPonto: LateralComponente;
  private readonly _lateralSegundoPonto: LateralComponente;
  private readonly _primeiroComponente: ComponenteDiagrama;
  private readonly _segundoComponente: ComponenteDiagrama;

  constructor(
    htmlConexao: HTMLDivElement,
    propriedades: PropriedadeComponente<any>[],
    x1: number,
    y1: number,
    x2: number,
    y2: number,
    lateralPrimeiroPonto: LateralComponente,
    lateralSegundoPonto: LateralComponente,
    primeiroComponente: ComponenteDiagrama,
    segundoComponente: ComponenteDiagrama,
  ) {
    super(htmlConexao, propriedades);
    this._x1 = x1;
    this._y1 = y1;
    this._x2 = x2;
    this._y2 = y2;
    this._lateralPrimeiroPonto = lateralPrimeiroPonto;
    this._lateralSegundoPonto = lateralSegundoPonto;
    this._primeiroComponente = primeiroComponente;
    this._segundoComponente = segundoComponente;
    this._primeiroComponente.adicionarOuvinte(this);
    this._segundoComponente.adicionarOuvinte(this);
    this._recebeSetas = false;

    this.ajustarConexao();
  }

  private ajustarConexao(): void {
    let angulo: number = this.calcularAnguloConexao();
    let distancia: number = this.calcularDistanciaConexao();

    this._htmlComponente.style.width = `${distancia}px`;
    this._htmlComponente.style.rotate = `${angulo}rad`;
    this._htmlComponente.style.top = `${this._y1}px`;
    this._htmlComponente.style.left = `${this._x1}px`;
  }

  private calcularAnguloConexao(): number {
    let deltaX: number = this._x2 - this._x1;
    let deltaY: number = this._y2 - this._y1;

    return Math.atan2(deltaY, deltaX);
  }

  private calcularDistanciaConexao(): number {
    let deltaX: number = this._x2 - this._x1;
    let deltaY: number = this._y2 - this._y1;

    return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
  }

  atualizar(htmlElemento: HTMLDivElement): void {
    let ponto: number[];

    if (this._primeiroComponente.htmlComponente === htmlElemento) {
      ponto = this._primeiroComponente.calcularPontoLateralComponente(this._lateralPrimeiroPonto);
      this._x1 = ponto[0];
      this._y1 = ponto[1];
    } else {
      ponto = this._segundoComponente.calcularPontoLateralComponente(this._lateralSegundoPonto);
      this._x2 = ponto[0];
      this._y2 = ponto[1];
    }

    this.ajustarConexao();
  }
}
