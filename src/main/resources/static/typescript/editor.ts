import { esconderSecoesMenosPrimeira, mudarSecao } from "./editor/mudarSecao.js";
import { esconderPainel, mostrarPainel } from "./editor/alternarPainel.js";
import { atualizarValorInput, modificarPropriedadeElemento } from "./editor/editorPropriedades.js";

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
  let x = event.clientX - offsetX;
  let y = event.clientY - offsetY;
  componenteAtual.style.left = `${x}px`;
  componenteAtual.style.top = `${y}px`;
};

// Editor Propriedades
let elementoSelecionado: HTMLElement | null;

componentes.forEach((componente) =>
  componente.addEventListener("click", () => {
    elementoSelecionado = componente;
  }),
);

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
