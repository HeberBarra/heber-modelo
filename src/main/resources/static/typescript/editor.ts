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

btnPainelDireito?.addEventListener("click", () => alternarPainel(painelDireito));

btnPainelEsquerdo?.addEventListener("click", (e) => {
  return alternarTextoBotao(e.target as HTMLButtonElement | null);
});

btnPainelEsquerdo?.addEventListener("click", () => alternarPainel(painelEsquerdo));

const alternarTextoBotao = (btnAlvo: HTMLButtonElement | null) => {
  if (btnAlvo == null) return;

  if (btnAlvo.innerHTML === "&lt;") {
    btnAlvo.innerHTML = "&gt;";
  } else {
    btnAlvo.innerHTML = "&lt;";
  }
};

const alternarPainel = (painelAlvo: HTMLElement | null) => {
  if (painelAlvo == null) return;
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
