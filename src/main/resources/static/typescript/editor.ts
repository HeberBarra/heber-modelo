const converterPXParaNumero = (tamanho: string): number => {
  return Number(tamanho.substring(0, tamanho.length - 2));
};

// Trocar página painel lateral
let paginasPainelDireito: NodeListOf<HTMLElement> =
  document.querySelectorAll("#painel-direito .pagina");
let paginasPainelEsquerdo: NodeListOf<HTMLElement> = document.querySelectorAll(
  "#painel-esquerdo .pagina",
);

// Esconder painel
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

  if (painelAlvo.classList.contains(".hidden")) {
    painelAlvo.classList.remove(".hidden");
    painelAlvo.style.width = "auto";
    painelAlvo.style.height = "auto";
    painelAlvo.style.removeProperty("border");
    btnPainel.style.removeProperty("left");
  } else {
    painelAlvo.classList.add(".hidden");
    painelAlvo.style.width = "0";
    painelAlvo.style.height = "0";
    painelAlvo.style.border = "none";

    if (btnPainel.id === btnPainelEsquerdo?.id) {
      btnPainel.style.left = "16px";
    } else if (btnPainel.id == btnPainelDireito?.id) {
      btnPainel.style.left = "-32px";
    }
  }

  definirEstiloGrade(document.body, painelDireito, painelEsquerdo);
};

let colunasGrade: string[] | null = null;
// TODO: Ajustar a grade quando a página for redimensionada
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

  if (painelDireito.classList.contains(".hidden")) {
    elementoAlvo.style.gridTemplateColumns =
      colunasGrade[0] + " " + ajustarTamanhoColuna(colunasGrade[1], colunasGrade[2]).join(" ");
    colunasGrade = elementoAlvo.style.gridTemplateColumns.split(" ");
  }

  if (painelEsquerdo.classList.contains(".hidden")) {
    elementoAlvo.style.gridTemplateColumns =
      ajustarTamanhoColuna(colunasGrade[1], colunasGrade[0]).reverse().join(" ") +
      " " +
      colunasGrade[2];
    colunasGrade = elementoAlvo.style.gridTemplateColumns.split(" ");
  }
};

const ajustarTamanhoColuna = (
  tamanhoColunaCentral: string,
  tamanhoColunaAdjacente: string,
): string[] => {
  if (btnPainelEsquerdo == null) {
    return ["", ""];
  }

  let tamanhoCentro: number = converterPXParaNumero(tamanhoColunaCentral);
  let tamanhoAdjacente: number = converterPXParaNumero(tamanhoColunaAdjacente);
  let estiloBotao = getComputedStyle(btnPainelEsquerdo);
  let tamanhoBotao: number = converterPXParaNumero(estiloBotao.width);

  let tamanhoAjustado: number = tamanhoCentro + tamanhoAdjacente - tamanhoBotao;
  return [tamanhoAjustado + "px", tamanhoBotao + "px"];
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
