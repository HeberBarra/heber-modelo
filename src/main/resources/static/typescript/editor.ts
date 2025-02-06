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
let indexSecoesPainelDireito: number = 0;
let indexSecoesPainelEsquerdo: number = 0;

const esconderSecoesExcetoPrimeira = (secoes: NodeListOf<HTMLElement>) => {
  for (let i: number = 0; i < secoes.length; i++) {
    if (i == 0) continue;

    secoes[i].style.display = "none";
  }
};

const mudarSecao = (secoes: NodeListOf<HTMLElement>, proxima: boolean) => {
  let indexSecoes =
    secoes === secoesPainelDireito ? indexSecoesPainelDireito : indexSecoesPainelEsquerdo;

  if (proxima) {
    indexSecoes++;
  } else {
    indexSecoes--;
  }

  if (indexSecoes < 0) {
    indexSecoes = secoes.length - 1;
  }

  if (indexSecoes == secoes.length) {
    indexSecoes = 0;
  }

  for (let i: number = 0; i < secoes.length; i++) {
    if (i == indexSecoes) {
      secoes[i].style.removeProperty("display");
      continue;
    }

    secoes[i].style.display = "none";
  }

  if (secoes == secoesPainelDireito) {
    indexSecoesPainelDireito = indexSecoes;
  } else {
    indexSecoesPainelEsquerdo = indexSecoes;
  }
};

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

esconderSecoesExcetoPrimeira(secoesPainelDireito);
esconderSecoesExcetoPrimeira(secoesPainelEsquerdo);

// Esconder painel
const CLASSE_PAINEL_OCULTO: string = "hidden";
let painelDireito: HTMLElement | null = document.querySelector("#painel-direito");
let painelEsquerdo: HTMLElement | null = document.querySelector("#painel-esquerdo");
let btnPainelDireito: HTMLButtonElement | null = document.querySelector(
  "#painel-direito button.alternar-painel",
);

let btnPainelEsquerdo: HTMLButtonElement | null = document.querySelector(
  "#painel-esquerdo button.alternar-painel",
);

btnPainelDireito?.addEventListener("click", (e) => {
  return alternarTextoBotao(e.target as HTMLButtonElement | null);
});

btnPainelDireito?.addEventListener("click", () => {
  alternarPainel(painelDireito, btnPainelDireito);
});

btnPainelEsquerdo?.addEventListener("click", (e) => {
  return alternarTextoBotao(e.target as HTMLButtonElement | null);
});

btnPainelEsquerdo?.addEventListener("click", () =>
  alternarPainel(painelEsquerdo, btnPainelEsquerdo),
);

const alternarTextoBotao = (btnAlvo: HTMLButtonElement | null) => {
  if (btnAlvo == null) return;

  if (btnAlvo.innerHTML === "&lt;") {
    btnAlvo.innerHTML = "&gt;";
  } else {
    btnAlvo.innerHTML = "&lt;";
  }
};

const alternarPainel = (painelAlvo: HTMLElement | null, btnPainel: HTMLButtonElement | null) => {
  if (painelAlvo == null || btnPainel == null) return;

  if (painelAlvo.classList.contains(CLASSE_PAINEL_OCULTO)) {
    painelAlvo.classList.remove(CLASSE_PAINEL_OCULTO);
    painelAlvo.style.removeProperty("border");
  } else {
    painelAlvo.classList.add(CLASSE_PAINEL_OCULTO);
    painelAlvo.style.border = "none";
  }

  definirEstiloGrade(document.body, painelDireito, painelEsquerdo);
};

let colunasGrade: string[] | null = null;

const definirEstiloGrade = (
  elementoAlvo: HTMLElement | null,
  painelDireito: HTMLElement | null,
  painelEsquerdo: HTMLElement | null,
) => {
  if (elementoAlvo == null || painelEsquerdo == null || painelDireito == null) return;

  let estiloElementoAlvo = getComputedStyle(elementoAlvo);

  if (colunasGrade == null) {
    colunasGrade = estiloElementoAlvo.gridTemplateColumns.split(" ");
  }

  if (
    painelDireito.classList.contains(CLASSE_PAINEL_OCULTO) &&
    painelEsquerdo.classList.contains(CLASSE_PAINEL_OCULTO)
  ) {
    elementoAlvo.style.gridTemplateColumns = "5% 90% 5%";
  } else if (painelDireito.classList.contains(CLASSE_PAINEL_OCULTO)) {
    elementoAlvo.style.gridTemplateColumns = "20% 75% 5%";
  } else if (painelEsquerdo.classList.contains(CLASSE_PAINEL_OCULTO)) {
    elementoAlvo.style.gridTemplateColumns = "5% 75% 20%";
  } else {
    elementoAlvo.style.gridTemplateColumns = "20% 60% 20%";
  }
};

// Movimentação dos componentes
let componentes: NodeListOf<HTMLDivElement> = document.querySelectorAll(".componente");
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

let editorAltura: HTMLInputElement | null = document.querySelector(
  "#propriedades input[name='componente-altura']",
);

let editorLargura: HTMLInputElement | null = document.querySelector(
  "#propriedades input[name='componente-largura']",
);

editorAltura?.addEventListener("input", () => {
  if (elementoSelecionado === null || editorAltura === null) return;

  elementoSelecionado.style.height = ajustarValorAtributo(editorAltura.value) + "px";
});

editorLargura?.addEventListener("input", () => {
  if (elementoSelecionado === null || editorLargura === null) return;

  elementoSelecionado.style.width = ajustarValorAtributo(editorLargura.value) + "px";
});

editorAltura?.addEventListener("focusout", () => {
  if (elementoSelecionado === null || editorAltura === null) return;

  let alturaElemento: string = elementoSelecionado.style.height;
  editorAltura.value = alturaElemento.substring(0, alturaElemento.length - 2);
});

editorLargura?.addEventListener("focusout", () => {
  if (elementoSelecionado === null || editorLargura === null) return;

  let larguraElemento: string = elementoSelecionado.style.height;
  editorLargura.value = larguraElemento.substring(0, larguraElemento.length - 2);
});

const verificarNumero = (valor: string): boolean => {
  let regexVerificarNumero: RegExp = /^-?\d+$/g;
  return regexVerificarNumero.test(valor);
};

// Verifica uma expressão matemática simples e retorna o resultado, ou caso a expressão seja inválida retorna null.
// Infelizmente, não é possível utilizar parenteses
const calcularExpressao = (expressao: string): number | null => {
  let regexFiltroExpressao: RegExp = /(?<!\S)[0-9]+(?:[-+%~^*\/]+?\d+(?:\.\d+)?)+(?!\S)/g;

  if (expressao.match(regexFiltroExpressao) == null) {
    return null;
  }

  return eval(expressao);
};

const ajustarValorAtributo = (valor: string): number => {
  if (verificarNumero(valor)) {
    return parseFloat(valor);
  }

  let resultadoExpressao = calcularExpressao(valor);

  console.log(valor);
  console.log(resultadoExpressao);

  if (resultadoExpressao == null) {
    return 0;
  }

  return resultadoExpressao;
};
