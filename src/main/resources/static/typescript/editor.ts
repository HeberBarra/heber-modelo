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

import { converterPixeisParaNumero } from "./conversor/conversor.js";
import { esconderSecoesMenosPrimeira, mudarSecao } from "./editor/mudarSecao.js";
import { esconderPainel, mostrarPainel } from "./editor/alternarPainel.js";
import {
  atualizarInputs,
  atualizarValorInput,
  InputPropriedade,
  limparPropriedades,
  modificarPropriedadeElemento,
} from "./editor/editorPropriedades.js";
import { colarElemento, copiarElemento, cortarElemento } from "./editor/clipboard.js";
import { apagarElemento, DirecoesMovimento, moverElemento } from "./editor/manipularElemento.js";
import { carregarCSS } from "./editor/carregarCSS.js";
import { ComponenteDiagrama, LateralComponente } from "./editor/componente/componenteDiagrama.js";
import { RepositorioComponenteDiagrama } from "./editor/componente/repositorioComponenteDiagrama.js";
import { SelecionadorComponente } from "./editor/componente/selecionadorComponente.js";
import { GeradorIDComponente } from "./editor/componente/geradorIDComponente.js";
import { ComponenteConexao } from "./editor/componente/componenteConexao.js";
import { FabricaComponente } from "./editor/componente/fabricaComponente.js";
import { PropriedadeComponente } from "./editor/componente/propriedade/propriedadeComponente.js";
import { Ponto } from "./editor/ponto.js";

/****************************/
/* VARIÁVEIS COMPARTILHADAS */
/****************************/

let abaPropriedades: HTMLDivElement | null = document.querySelector("section#propriedades");
let diagrama: HTMLElement | null = document.querySelector("main");
let fabricaComponente: FabricaComponente = new FabricaComponente();
let geradorIDComponente: GeradorIDComponente = GeradorIDComponente.pegarInstance();
let repositorioComponentes: RepositorioComponenteDiagrama = new RepositorioComponenteDiagrama();
let componentes: NodeListOf<HTMLDivElement> = document.querySelectorAll(".componente");
let selecionadorComponente: SelecionadorComponente = new SelecionadorComponente();

componentes.forEach((componente: HTMLDivElement): void => {
  repositorioComponentes.adicionarComponente(new ComponenteDiagrama(componente, []));
});

/********************************/
/* ALTERAR SEÇÃO PAINEL LATERAL */
/********************************/

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

/*************************************/
/* ESCONDER/MOSTRAR PAINÉIS LATERAIS */
/*************************************/

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

/*********************************/
/* MOVIMENTAÇÃO DE UM COMPONENTE */
/*********************************/

let componenteAtual: HTMLDivElement;
let offsetX: number;
let offsetY: number;

function mouseDownComecarMoverElemento(event: MouseEvent): void {
  let componente: HTMLDivElement = event.target as HTMLDivElement;

  if (!componente.classList.contains("componente")) {
    return;
  }

  offsetX = event.clientX - componente.getBoundingClientRect().left;
  offsetY = event.clientY - componente.getBoundingClientRect().top;
  componente.classList.add("dragging");
  document.addEventListener("mousemove", dragElement);
  componenteAtual = componente;
}

function mouseUpPararMoverElemento(event: Event): void {
  let componente: HTMLElement = event.target as HTMLElement;
  componente.classList.remove("dragging");
  document.removeEventListener("mousemove", dragElement);
}

function dragElement(event: MouseEvent): void {
  event.preventDefault();
  let x: number = event.pageX - offsetX;
  let y: number = event.pageY - offsetY;
  // TODO: Calibrar o scroll automático
  window.scrollTo(x, y);
  componenteAtual.style.left = `${x}px`;
  componenteAtual.style.top = `${y}px`;
  atualizarValorInput(elementoSelecionado, editorEixoY, "top");
  atualizarValorInput(elementoSelecionado, editorEixoX, "left");

  let componente: ComponenteDiagrama | null = repositorioComponentes.pegarComponentePorHTML(
    event.target as HTMLElement,
  );

  if (componente === null) return;
  selecionadorComponente.moverSetas(componente);
}

/**************************/
/* EDITOR DE PROPRIEDADES */
/**************************/

let elementoSelecionado: HTMLElement | null;
let inputs: InputPropriedade[] = [];

function mouseDownSelecionarElemento(event: Event): void {
  let componente: ComponenteDiagrama | null = repositorioComponentes.pegarComponentePorHTML(
    event.target as HTMLElement,
  );

  if (componente === null) return;

  selecionadorComponente.selecionarElemento(componente);
  limparPropriedades(abaPropriedades);
  elementoSelecionado = selecionadorComponente.componenteSelecionado?.htmlComponente as HTMLElement;
  componente.propriedades.forEach((propriedade: PropriedadeComponente): void => {
    let editorPropriedade: HTMLLabelElement = propriedade.criarElementoInputPropriedade();
    abaPropriedades?.appendChild(editorPropriedade);
  });
  atualizarInputs(elementoSelecionado, inputs);
}

const registrarEventosComponente = (componente: HTMLDivElement): void => {
  componente.addEventListener("click", conectarElementos);
  componente.addEventListener("mousedown", mouseDownSelecionarElemento);
  componente.addEventListener("mousedown", mouseDownComecarMoverElemento);
  componente.addEventListener("mouseup", mouseUpPararMoverElemento);
};

componentes.forEach((componente) => {
  registrarEventosComponente(componente);
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
  selecionadorComponente.moverSetasParaComponenteSelecionado();
});

editorEixoY?.addEventListener("input", () => {
  modificarPropriedadeElemento(elementoSelecionado, editorEixoY, "top");
  selecionadorComponente.moverSetasParaComponenteSelecionado();
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

/******************************/
/* BOTÕES CRIAR NOVO ELEMENTO */
/******************************/

let botoesCriarElemento: NodeListOf<HTMLButtonElement> =
  document.querySelectorAll("button.criar-elemento");

botoesCriarElemento.forEach((btn) => {
  btn.addEventListener("click", () => {
    let nomeElemento: string | null = btn.getAttribute("data-nome-elemento");

    if (nomeElemento === null) return;

    fabricaComponente.criarComponente(nomeElemento).then((componente: ComponenteDiagrama): void => {
      carregarCSS(nomeElemento);
      registrarEventosComponente(componente.htmlComponente);
      componente.htmlComponente.setAttribute(
        "data-id",
        String(geradorIDComponente.pegarProximoID()),
      );
      repositorioComponentes.adicionarComponente(componente);
      diagrama?.appendChild(componente.htmlComponente);
    });
  });
});

/********************/
/* CONEXÕES E SETAS */
/********************/

const setas: NodeListOf<HTMLElement> = document.querySelectorAll(".seta");
let lateralPrimeiroComponente: LateralComponente | null;
let ponto1: Ponto | null = null;

setas.forEach((seta) =>
  seta.addEventListener("click", () => {
    if (elementoSelecionado === null || selecionadorComponente.componenteSelecionado === null)
      return;

    let componenteSelecionado: ComponenteDiagrama = selecionadorComponente.componenteSelecionado;
    let ponto: number[];

    if (seta.classList.contains("seta-direita")) {
      ponto = componenteSelecionado.calcularPontoLateralComponente(LateralComponente.LESTE);
      lateralPrimeiroComponente = LateralComponente.LESTE;
    } else if (seta.classList.contains("seta-esquerda")) {
      ponto = componenteSelecionado.calcularPontoLateralComponente(LateralComponente.OESTE);
      lateralPrimeiroComponente = LateralComponente.OESTE;
    } else if (seta.classList.contains("seta-superior")) {
      ponto = componenteSelecionado.calcularPontoLateralComponente(LateralComponente.NORTE);
      lateralPrimeiroComponente = LateralComponente.NORTE;
    } else {
      ponto = componenteSelecionado.calcularPontoLateralComponente(LateralComponente.SUL);
      lateralPrimeiroComponente = LateralComponente.SUL;
    }

    ponto1 = new Ponto(ponto[0], ponto[1]);
  }),
);

function limparCoordenadaInicial(): void {
  lateralPrimeiroComponente = null;
  ponto1 = null;
}

function conectarElementos(event: MouseEvent): void {
  diagrama?.removeEventListener("click", limparCoordenadaInicial);
  event.stopPropagation();
  event.stopImmediatePropagation();
  let elementoAlvo: HTMLElement = event.target as HTMLElement;
  let primeiroComponente: ComponenteDiagrama | null = selecionadorComponente.componenteSelecionado;
  let segundoComponente: ComponenteDiagrama | null =
    repositorioComponentes.pegarComponentePorHTML(elementoAlvo);
  if (
    ponto1 === null ||
    ponto1 === undefined ||
    lateralPrimeiroComponente === undefined ||
    lateralPrimeiroComponente === null ||
    primeiroComponente === null ||
    segundoComponente === null
  )
    return;

  diagrama?.addEventListener("click", limparCoordenadaInicial);
  let estiloElementoAlvo: CSSStyleDeclaration = getComputedStyle(elementoAlvo);
  let alturaElementoAlvo: number = converterPixeisParaNumero(estiloElementoAlvo.height);
  let larguraElementoAlvo: number = converterPixeisParaNumero(estiloElementoAlvo.width);
  let leftElementoAlvo: number = converterPixeisParaNumero(estiloElementoAlvo.left);
  let topElemento: number = converterPixeisParaNumero(estiloElementoAlvo.top);
  let posX: number = event.pageX - leftElementoAlvo;
  let posY: number = event.pageY - topElemento;

  let centroX: boolean = false;
  let centroY: boolean = false;
  let direita: boolean = false;
  let esquerda: boolean = false;
  let cima: boolean = false;
  let baixo: boolean = false;
  let lateralSegundoComponente: LateralComponente;
  let x2: number;
  let y2: number;

  if (posX > larguraElementoAlvo * 0.2 && posX < larguraElementoAlvo * 0.8) {
    x2 = larguraElementoAlvo / 2 + leftElementoAlvo;
    centroX = true;
  } else if (posX <= larguraElementoAlvo * 0.2) {
    x2 = leftElementoAlvo;
    esquerda = true;
  } else {
    x2 = larguraElementoAlvo + leftElementoAlvo;
    direita = true;
  }

  if (posY > alturaElementoAlvo * 0.4 && posY < alturaElementoAlvo * 0.6) {
    y2 = alturaElementoAlvo / 2 + topElemento;
    centroY = true;
  } else if (posY <= alturaElementoAlvo * 0.4) {
    y2 = topElemento;
    baixo = true;
  } else {
    y2 = alturaElementoAlvo + topElemento;
    cima = true;
  }

  if ((centroY || baixo || cima) && esquerda) {
    lateralSegundoComponente = LateralComponente.OESTE;
  } else if ((centroY || baixo || cima) && direita) {
    lateralSegundoComponente = LateralComponente.LESTE;
  } else if ((centroX && centroX) || cima) {
    lateralSegundoComponente = LateralComponente.NORTE;
  } else {
    lateralSegundoComponente = LateralComponente.SUL;
  }

  const nomeElementoConexao: string = "conexao";
  let ponto2: Ponto = new Ponto(x2, y2);

  fabricaComponente
    .criarComponente(nomeElementoConexao)
    .then((componente: ComponenteDiagrama): void => {
      carregarCSS(nomeElementoConexao);
      registrarEventosComponente(componente.htmlComponente);
      let componenteConexao: ComponenteDiagrama = new ComponenteConexao(
        componente.htmlComponente,
        componente.propriedades,
        ponto1 as Ponto,
        ponto2,
        lateralPrimeiroComponente as LateralComponente,
        lateralSegundoComponente,
        primeiroComponente,
        segundoComponente,
      );
      repositorioComponentes.adicionarComponente(componenteConexao);
      diagrama?.appendChild(componenteConexao.htmlComponente);
      limparCoordenadaInicial();
    });
}

diagrama?.addEventListener("click", limparCoordenadaInicial);

/***********************/
/* BINDINGS DO USUÁRIO */
/***********************/

let teclaAnterior: string | null = null;

document.addEventListener("keydown", (event: KeyboardEvent) => {
  atualizarValorInput(elementoSelecionado, editorEixoY, "top");
  atualizarValorInput(elementoSelecionado, editorEixoX, "left");
  if (teclaAnterior === null) {
    teclaAnterior = event.key;
  }

  // Leader key bindings
  if (teclaAnterior === bindings.get("leaderKey") && event.key === bindings.get("copiarElemento")) {
    copiarElemento(elementoSelecionado);
    return;
  }

  if (teclaAnterior === bindings.get("leaderKey") && event.key === bindings.get("cortarElemento")) {
    cortarElemento(elementoSelecionado);
    return;
  }

  if (teclaAnterior === bindings.get("leaderKey") && event.key === bindings.get("colarElemento")) {
    let ultimoElemento: HTMLDivElement = diagrama?.lastElementChild as HTMLDivElement;
    colarElemento(diagrama);

    setTimeout(() => {
      let novoElemento: HTMLDivElement = diagrama?.lastElementChild as HTMLDivElement;

      if (ultimoElemento !== novoElemento) {
        registrarEventosComponente(novoElemento);
        novoElemento.setAttribute("data-id", String(geradorIDComponente.pegarProximoID()));
        repositorioComponentes.adicionarComponente(new ComponenteDiagrama(novoElemento, []));
      }
    }, 200);
    return;
  }

  switch (event.key) {
    // Limpar selecao
    case bindings.get("removerSelecao"):
      selecionadorComponente.removerSelecao();
      limparPropriedades(abaPropriedades);
      elementoSelecionado = selecionadorComponente.pegarHTMLElementoSelecionado();
      atualizarInputs(elementoSelecionado, inputs);
      break;

    // Apagar elemento
    case bindings.get("apagarElemento"):
      apagarElemento(diagrama, elementoSelecionado);
      selecionadorComponente.removerSelecao();
      limparPropriedades(abaPropriedades);
      elementoSelecionado = selecionadorComponente.pegarHTMLElementoSelecionado();
      atualizarInputs(elementoSelecionado, inputs);
      break;

    // Mover elemento
    case bindings.get("moverElementoParaCima"):
      moverElemento(elementoSelecionado, DirecoesMovimento.CIMA, incrementoMovimentacao);
      selecionadorComponente.moverSetasParaComponenteSelecionado();
      break;

    case bindings.get("moverElementoParaBaixo"):
      moverElemento(elementoSelecionado, DirecoesMovimento.BAIXO, incrementoMovimentacao);
      selecionadorComponente.moverSetasParaComponenteSelecionado();
      break;

    case bindings.get("moverElementoParaDireita"):
      moverElemento(elementoSelecionado, DirecoesMovimento.DIREITA, incrementoMovimentacao);
      selecionadorComponente.moverSetasParaComponenteSelecionado();
      break;

    case bindings.get("moverElementoParaEsquerda"):
      moverElemento(elementoSelecionado, DirecoesMovimento.ESQUERDA, incrementoMovimentacao);
      selecionadorComponente.moverSetasParaComponenteSelecionado();
      break;
  }

  teclaAnterior = event.key;
});
