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

import { esconderSecoesMenosPrimeira, mudarSecao } from "./editor/mudarSecao.js";
import { esconderPainel, mostrarPainel } from "./editor/alternarPainel.js";
import {
  atualizarInputs,
  atualizarValorInput,
  InputPropriedade,
  modificarPropriedadeElemento,
} from "./editor/editorPropriedades.js";
import { removerSelecao, selecionarElemento } from "./editor/selecionarElemento.js";
import { DirecoesMovimento, moverElemento } from "./editor/moverElemento.js";

// Variáveis compartilhadas
let componentes: NodeListOf<HTMLDivElement> = document.querySelectorAll(".componente");

// Trocar aba painel lateral
let secoesPainelDireito: NodeListOf<HTMLElement> =
  document.querySelectorAll("#painel-direito .pagina");
let secoesPainelEsquerdo: NodeListOf<HTMLElement> = document.querySelectorAll(
  "#painel-esquerdo .pagina",
);
let btnProximaSecaoPainelDireito: HTMLButtonElement | null = document.querySelector(
  "aside#painel-direito button.btn-proxima-secao",
);
let btnVoltarSecaoPainelDireito: HTMLButtonElement | null = document.querySelector(
  "aside#painel-direito button.btn-voltar-secao",
);
let btnProximaSecaoPainelEsquerdo: HTMLButtonElement | null = document.querySelector(
  "aside#painel-esquerdo button.btn-proxima-secao",
);
let btnVoltarSecaoPainelEsquerdo: HTMLButtonElement | null = document.querySelector(
  "aside#painel-esquerdo button.btn-voltar-secao",
);

btnProximaSecaoPainelDireito?.addEventListener("click", () => {
  mudarSecao(secoesPainelDireito, true);
});

btnVoltarSecaoPainelDireito?.addEventListener("click", () => {
  mudarSecao(secoesPainelDireito, false);
});

btnProximaSecaoPainelEsquerdo?.addEventListener("click", () => {
  mudarSecao(secoesPainelEsquerdo, true);
});

btnVoltarSecaoPainelEsquerdo?.addEventListener("click", () => {
  mudarSecao(secoesPainelEsquerdo, false);
});

esconderSecoesMenosPrimeira(secoesPainelDireito);
esconderSecoesMenosPrimeira(secoesPainelEsquerdo);

// Esconder painel
let painelDireito: HTMLElement | null = document.querySelector("#painel-direito");
let painelEsquerdo: HTMLElement | null = document.querySelector("#painel-esquerdo");
let btnEsconderPainelDireito: HTMLButtonElement | null = document.querySelector(
  ".btn-painel-direito.btn-esconder",
);
let btnEsconderPainelEsquerdo: HTMLButtonElement | null = document.querySelector(
  ".btn-painel-esquerdo.btn-esconder",
);
let btnMostrarPainelDireito: HTMLButtonElement | null = document.querySelector(
  ".btn-painel-direito.btn-mostrar",
);
let btnMostrarPainelEsquerdo: HTMLButtonElement | null = document.querySelector(
  ".btn-painel-esquerdo.btn-mostrar",
);

btnEsconderPainelDireito?.addEventListener("click", () => {
  esconderPainel(painelDireito);
});

btnEsconderPainelEsquerdo?.addEventListener("click", () => {
  esconderPainel(painelEsquerdo);
});

btnMostrarPainelDireito?.addEventListener("click", () => {
  mostrarPainel(painelDireito);
});

btnMostrarPainelEsquerdo?.addEventListener("click", () => {
  mostrarPainel(painelEsquerdo);
});

// Movimentação dos componentes
let componenteAtual: HTMLDivElement;
let offsetX: number;
let offsetY: number;

componentes.forEach((componente) => {
  componente.addEventListener("mousedown", (e) => {
    e.preventDefault();
    offsetX = e.clientX - componente.getBoundingClientRect().left;
    offsetY = e.clientY - componente.getBoundingClientRect().top;
    componente.classList.add("dragging");
    document.addEventListener("mousemove", dragElement);
    componenteAtual = componente;
  });

  componente.addEventListener("mouseup", () => {
    componente.classList.remove("dragging");
    document.removeEventListener("mousemove", dragElement);
  });
});

const dragElement = (event: MouseEvent) => {
  event.preventDefault();
  let x: number = event.pageX - offsetX;
  let y: number = event.pageY - offsetY;
  // TODO: Calibrar o scroll automático
  window.scrollTo(x, y);
  componenteAtual.style.left = `${x}px`;
  componenteAtual.style.top = `${y}px`;
  atualizarValorInput(elementoSelecionado, editorEixoY, "top");
  atualizarValorInput(elementoSelecionado, editorEixoX, "left");
};

// Editor Propriedades
let elementoSelecionado: HTMLElement | null;
let inputs: InputPropriedade[] = [];

componentes.forEach((componente) => {
  componente.addEventListener("mousedown", () => {
    elementoSelecionado = selecionarElemento(componente);
    atualizarInputs(elementoSelecionado, inputs);
  });
});

let editorEixoX: HTMLInputElement | null = document.querySelector(
  "#propriedades input[name='componente-x']",
);

let editorEixoY: HTMLInputElement | null = document.querySelector(
  "#propriedades input[name='componente-y']",
);

let editorAltura: HTMLInputElement | null = document.querySelector(
  "#propriedades input[name='componente-altura']",
);

let editorLargura: HTMLInputElement | null = document.querySelector(
  "#propriedades input[name='componente-largura']",
);

let editorTamanhoFonte: HTMLInputElement | null = document.querySelector(
  "#propriedades input[name='componente-tamanho-fonte']",
);

inputs.push(new InputPropriedade(editorEixoX, "left"));
inputs.push(new InputPropriedade(editorEixoY, "top"));
inputs.push(new InputPropriedade(editorAltura, "height"));
inputs.push(new InputPropriedade(editorLargura, "width"));
inputs.push(new InputPropriedade(editorTamanhoFonte, "font-size"));

editorEixoX?.addEventListener("input", () => {
  modificarPropriedadeElemento(elementoSelecionado, editorEixoX, "left");
});

editorEixoY?.addEventListener("input", () => {
  modificarPropriedadeElemento(elementoSelecionado, editorEixoY, "top");
});

editorAltura?.addEventListener("input", () => {
  modificarPropriedadeElemento(elementoSelecionado, editorAltura, "height");
});

editorLargura?.addEventListener("input", () => {
  modificarPropriedadeElemento(elementoSelecionado, editorLargura, "width");
});

editorTamanhoFonte?.addEventListener("input", () => {
  modificarPropriedadeElemento(elementoSelecionado, editorTamanhoFonte, "font-size");
});

editorEixoX?.addEventListener("focusout", () => {
  atualizarValorInput(elementoSelecionado, editorEixoX, "left");
});

editorEixoY?.addEventListener("focusout", () => {
  atualizarValorInput(elementoSelecionado, editorEixoY, "top");
});

editorAltura?.addEventListener("focusout", () => {
  atualizarValorInput(elementoSelecionado, editorAltura, "height");
});

editorLargura?.addEventListener("focusout", () => {
  atualizarValorInput(elementoSelecionado, editorLargura, "width");
});

editorTamanhoFonte?.addEventListener("focusout", () => {
  atualizarValorInput(elementoSelecionado, editorTamanhoFonte, "font-size");
});

document.addEventListener("keydown", () => {
  atualizarValorInput(elementoSelecionado, editorEixoY, "top");
  atualizarValorInput(elementoSelecionado, editorEixoX, "left");
});

document.addEventListener("keydown", (event: KeyboardEvent) => {
  if (event.key === bindings.get("removerSelecao")) {
    elementoSelecionado = removerSelecao();
    atualizarInputs(elementoSelecionado, inputs);
  }
});

// Mover elemento com bindings
document.addEventListener("keydown", (event: KeyboardEvent) => {
  switch (event.key) {
    case bindings.get("moverElementoParaCima"):
      moverElemento(elementoSelecionado, DirecoesMovimento.CIMA, incrementoMovimentacaoElemento);
      break;

    case bindings.get("moverElementoParaBaixo"):
      moverElemento(elementoSelecionado, DirecoesMovimento.BAIXO, incrementoMovimentacaoElemento);
      break;

    case bindings.get("moverElementoParaDireita"):
      moverElemento(elementoSelecionado, DirecoesMovimento.DIREITA, incrementoMovimentacaoElemento);
      break;

    case bindings.get("moverElementoParaEsquerda"):
      moverElemento(
        elementoSelecionado,
        DirecoesMovimento.ESQUERDA,
        incrementoMovimentacaoElemento,
      );
      break;
  }
});
