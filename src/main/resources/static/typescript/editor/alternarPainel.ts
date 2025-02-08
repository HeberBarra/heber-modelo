const CLASSE_PAINEL_OCULTO: string = "hidden";
const TEXTOS_BOTAO: string[] = ["&lt;", "&gt;"];

let painelDireito: HTMLElement | null = document.querySelector("#painel-direito");
let painelEsquerdo: HTMLElement | null = document.querySelector("#painel-esquerdo");

const alternarTextoBotao = (btnAlvo: HTMLButtonElement | null): void => {
  if (btnAlvo == null) return;

  if (btnAlvo.innerHTML === TEXTOS_BOTAO[0]) {
    btnAlvo.innerHTML = TEXTOS_BOTAO[1];
  } else {
    btnAlvo.innerHTML = TEXTOS_BOTAO[0];
  }
};

const definirEstiloGrade = (
  elementoAlvo: HTMLElement,
  painelDireito: HTMLElement | null,
  painelEsquerdo: HTMLElement | null,
): void => {
  if (elementoAlvo === null || painelDireito === null || painelEsquerdo === null) return;

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

const alternarPainel = (painelAlvo: HTMLElement | null, btnPainel: HTMLElement | null): void => {
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

export { alternarTextoBotao, alternarPainel };
