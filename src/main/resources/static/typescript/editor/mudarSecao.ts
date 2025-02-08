const esconderSecoesMenosPrimeira = (secoes: NodeListOf<HTMLElement>): void => {
  for (let i: number = 0; i < secoes.length; i++) {
    if (i === 0) continue;

    secoes[i].style.display = "none";
  }
};

let indexPainelDireito: number = 0;
let indexPainelEsquerdo: number = 0;

const mudarSecao = (secoes: NodeListOf<HTMLElement>, avancar: boolean): void => {
  let indexSecaoAtual: number =
    secoes[0].parentElement?.id === "painel-direito" ? indexPainelDireito : indexPainelEsquerdo;

  if (avancar) {
    indexSecaoAtual++;
  } else {
    indexSecaoAtual--;
  }

  if (indexSecaoAtual < 0) {
    indexSecaoAtual = secoes.length - 1;
  }

  if (indexSecaoAtual === secoes.length) {
    indexSecaoAtual = 0;
  }

  for (let i: number = 0; i < secoes.length; i++) {
    if (i === indexSecaoAtual) {
      secoes[i].style.removeProperty("display");
      continue;
    }

    secoes[i].style.display = "none";
  }

  if (secoes[0].parentElement?.id === "painel-direito") {
    indexPainelDireito = indexSecaoAtual;
  } else {
    indexPainelEsquerdo = indexSecaoAtual;
  }
};

export { esconderSecoesMenosPrimeira, mudarSecao };
