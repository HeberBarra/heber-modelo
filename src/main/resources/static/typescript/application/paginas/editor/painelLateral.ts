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

class PainelLateral {
  public readonly CLASSE_PAINEL_OCULTO: string = "hidden";
  private _indexSecaoAtual: number;
  private _secoes: NodeListOf<HTMLElement>;

  constructor(
    painel: HTMLElement | null,
    secoes: NodeListOf<HTMLElement>,
    btnAvancarSecao: HTMLButtonElement | null,
    btnVoltarSecao: HTMLButtonElement | null,
    btnEsconderPainel: HTMLButtonElement | null,
    btnMostrarPainel: HTMLButtonElement | null,
  ) {
    this._indexSecaoAtual = 0;
    this._secoes = secoes;

    btnAvancarSecao?.addEventListener("click", (): void => {
      this.mudarSecao(true);
    });

    btnVoltarSecao?.addEventListener("click", (): void => {
      this.mudarSecao(false);
    });

    btnEsconderPainel?.addEventListener("click", () => this.esconderPainel(painel));
    btnMostrarPainel?.addEventListener("click", () => this.mostrarPainel(painel));

    this.ocultarSecoesExcetoPrimeira();
  }

  private ocultarSecoesExcetoPrimeira(): void {
    for (let i: number = 0; i < this._secoes.length; i++) {
      if (i === 0) continue;

      this._secoes[i].style.display = "none";
    }
  }

  private mudarSecao(avancar: boolean): void {
    if (avancar) {
      this._indexSecaoAtual++;
    } else {
      this._indexSecaoAtual--;
    }

    if (this._indexSecaoAtual < 0) {
      this._indexSecaoAtual = this._secoes.length - 1;
    }

    if (this._indexSecaoAtual === this._secoes.length) {
      this._indexSecaoAtual = 0;
    }

    this._secoes.forEach((secao: HTMLElement): void => {
      secao.style.display = "none";
    });

    this._secoes[this._indexSecaoAtual].style.removeProperty("display");
  }

  private esconderPainel(painel: HTMLElement | null): void {
    painel?.classList.add(this.CLASSE_PAINEL_OCULTO);
    painel?.style.setProperty("border", "none");
  }

  private mostrarPainel(painel: HTMLElement | null): void {
    painel?.classList.remove(this.CLASSE_PAINEL_OCULTO);
    painel?.style.removeProperty("border");
  }
}

function configurarPainel(id: string): void {
  let painel: HTMLElement | null = document.querySelector(id);
  let secoes: NodeListOf<HTMLElement> = document.querySelectorAll(`${id} .pagina`);
  let btnAvancar: HTMLButtonElement | null = document.querySelector(`${id} .btn-proxima-secao`);
  let btnVoltar: HTMLButtonElement | null = document.querySelector(`${id} .btn-voltar-secao`);
  let btnEsconder: HTMLButtonElement | null = document.querySelector(`${id} .btn-esconder`);
  let btnMostrar: HTMLButtonElement | null = document.querySelector(`${id} .btn-mostrar`);

  new PainelLateral(painel, secoes, btnAvancar, btnVoltar, btnEsconder, btnMostrar);
}

configurarPainel("#painel-direito");
configurarPainel("#painel-esquerdo");
