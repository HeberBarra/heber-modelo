const CLASSE_PAINEL_OCULTO: string = "hidden";

const esconderPainel = (painelAlvo: HTMLElement | null): void => {
  if (painelAlvo == null) return;

  painelAlvo.classList.add(CLASSE_PAINEL_OCULTO);
  painelAlvo.style.border = "none";
};

const mostrarPainel = (painelAlvo: HTMLElement | null): void => {
  if (painelAlvo == null) return;

  painelAlvo.classList.remove(CLASSE_PAINEL_OCULTO);
  painelAlvo.style.removeProperty("border");
};

export { esconderPainel, mostrarPainel };
