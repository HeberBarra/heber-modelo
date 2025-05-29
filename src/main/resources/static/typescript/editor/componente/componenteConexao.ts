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
import { PropriedadeComponente } from "./propriedade/propriedadeComponente.js";
import { ComponenteDiagramaOuvinte } from "./componenteDiagramaOuvinte.js";
import { Ponto } from "../ponto.js";

export class ComponenteConexao extends ComponenteDiagrama implements ComponenteDiagramaOuvinte {
  private _ponto1: Ponto;
  private _ponto2: Ponto;
  private readonly _lateralPrimeiroPonto: LateralComponente;
  private readonly _lateralSegundoPonto: LateralComponente;
  private readonly _primeiroComponente: ComponenteDiagrama;
  private readonly _segundoComponente: ComponenteDiagrama;

  constructor(
    htmlConexao: HTMLDivElement,
    propriedades: PropriedadeComponente[],
    ponto1: Ponto,
    ponto2: Ponto,
    lateralPrimeiroPonto: LateralComponente,
    lateralSegundoPonto: LateralComponente,
    primeiroComponente: ComponenteDiagrama,
    segundoComponente: ComponenteDiagrama,
  ) {
    super(htmlConexao, propriedades);
    this._ponto1 = ponto1;
    this._ponto2 = ponto2;
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
    this._htmlComponente.style.top = `${this._ponto1.y}px`;
    this._htmlComponente.style.left = `${this._ponto1.x}px`;
  }

  private calcularAnguloConexao(): number {
    let deltaX: number = this._ponto2.x - this._ponto1.x;
    let deltaY: number = this._ponto2.y - this._ponto1.y;

    return Math.atan2(deltaY, deltaX);
  }

  private calcularDistanciaConexao(): number {
    let deltaX: number = this._ponto2.x - this._ponto1.x;
    let deltaY: number = this._ponto2.y - this._ponto1.y;

    return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
  }

  atualizar(htmlElemento: HTMLDivElement): void {
    let ponto: number[];

    if (this._primeiroComponente.htmlComponente === htmlElemento) {
      ponto = this._primeiroComponente.calcularPontoLateralComponente(this._lateralPrimeiroPonto);
      this._ponto1 = new Ponto(ponto[0], ponto[1]);
    } else {
      ponto = this._segundoComponente.calcularPontoLateralComponente(this._lateralSegundoPonto);
      this._ponto2 = new Ponto(ponto[0], ponto[1]);
    }

    this.ajustarConexao();
  }
}
