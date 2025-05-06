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
import { ComponenteDiagrama } from "./componenteDiagrama";

export class RepositorioComponenteDiagrama {
  private _componentesDiagrama: ComponenteDiagrama[] = [];

  public adicionarComponente(componenteDiagrama: ComponenteDiagrama): void {
    this._componentesDiagrama.push(componenteDiagrama);
  }

  public atualizarComponente(componenteDiagrama: ComponenteDiagrama): void {
    for (let i: number = 0; i < this._componentesDiagrama.length; i++) {
      if (this._componentesDiagrama[i] === componenteDiagrama) {
        this._componentesDiagrama[i] = componenteDiagrama;
      }
    }
  }

  public removerComponentePorID(id: number): void {
    let novaListaComponentes: ComponenteDiagrama[] = [];

    for (let i: number = 0; i < this._componentesDiagrama.length; i++) {
      if (this._componentesDiagrama[i].pegarIDElemento() === id) {
        novaListaComponentes = this._componentesDiagrama.slice(0, i);

        if (i + 1 <= this._componentesDiagrama.length - 1) {
          novaListaComponentes.concat(this._componentesDiagrama.slice(i + 1));
        }
      }
    }

    this._componentesDiagrama = novaListaComponentes;
  }

  public pegarComponentePorID(id: number): ComponenteDiagrama | null {
    for (const componente of this._componentesDiagrama) {
      if (componente.pegarIDElemento() === id) {
        return componente;
      }
    }

    return null;
  }

  public pegarComponentePorHTML(htmlElemento: HTMLElement): ComponenteDiagrama | null {
    for (const componente of this._componentesDiagrama) {
      if (componente.htmlComponente === htmlElemento) {
        return componente;
      }
    }

    return null;
  }

  get componentesDiagrama(): ComponenteDiagrama[] {
    return this._componentesDiagrama;
  }
}
