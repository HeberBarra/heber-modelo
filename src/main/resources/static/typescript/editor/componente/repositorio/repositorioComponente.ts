/*
 * Copyright (c) 2025. Heber Ferreira Barra, Matheus de Assis de Paula, Matheus Jun Alves Matuda.
 *
 * Licensed under the Massachusetts Institute of Technology (MIT) License.
 * You may obtain a copy of the license at:
 *
 *   https://choosealicense.com/licenses/mit/
 *
 * A short and simple permissive license with conditions only requiring preservation of copyright and license notices.
 * Licensed works, modifications, and larger works may be distributed under different terms and without source code.
 *
 */

import { ComponenteDiagrama } from "../componenteDiagrama.js";
import { ComponenteDiagramaOuvinte } from "../componenteDiagramaOuvinte.js";
import { AbstractComponenteConexao } from "../conexao/abstractComponenteConexao.js";

export class RepositorioComponente {
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

  public removerComponente(componente: ComponenteDiagrama): void {
    let id: number = componente.pegarIDElemento();
    this.removerComponentePorID(id);
  }

  private removerComponenteArray(componente: ComponenteDiagrama): void {
    let indexComponente: number = this._componentesDiagrama.indexOf(componente);

    if (indexComponente > -1) {
      this._componentesDiagrama.splice(indexComponente, 1);
    }
  }

  public removerComponentePorID(id: number): void {
    let componenteAlvo: ComponenteDiagrama | null = this.pegarComponentePorID(id);

    if (componenteAlvo === null) {
      return;
    }

    componenteAlvo.htmlComponente.remove();
    let componentes: ComponenteDiagramaOuvinte[] = componenteAlvo.removerTodosOuvintes();
    this.removerComponenteArray(componenteAlvo);

    componentes.forEach((componente: ComponenteDiagramaOuvinte): void => {
      if (componente.isDependente()) {
        if (componente instanceof AbstractComponenteConexao) {
          this.removerComponenteArray(componente);
        }
      }
    });
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
