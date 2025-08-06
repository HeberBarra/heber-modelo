const callbackMarcador = (event: Event): void => {
  let classeChavePrimaria: string = "primary-key";
  let elementoAlvo: HTMLElement = event.target as HTMLElement;

  if (elementoAlvo.classList.contains(classeChavePrimaria)) {
    elementoAlvo.classList.remove(classeChavePrimaria);
  } else {
    elementoAlvo.classList.add(classeChavePrimaria);
  }
};

const callbackInverterAtributo = (event: MouseEvent): void => {
  if (event.button == 1) {
    let elementoAlvo: HTMLElement = (event.target as HTMLElement).parentElement as HTMLElement;
    elementoAlvo.append(...Array.from(elementoAlvo.childNodes).reverse());
  }
};

/*********************************/
/* Diagrama do Modelo Relacional */
/*********************************/

const callbackAlterarAtributoRelacional = (event: MouseEvent): void => {
  if (event.button == 1) {
    (event.target as HTMLElement).remove();
    return;
  }

  let elementoChave: HTMLElement | null = (event.target as HTMLElement).querySelector(".chave");
  let svgChave: string =
    "<svg width='10px' height='20px' viewBox='0 0 90 221' xmlns='http://www.w3.org/2000/svg'>" +
    "<rect x='30' y='71' width='30' height='125' fill='currentColor'/>" +
    "<path d='M45 221L60.2169 195.779L54.4046 154.971H35.5954L29.7831 195.779L45 221Z' fill='currentColor'/>" +
    "<rect x='30' y='194' width='30' height='1' fill='currentColor'/>" +
    "<path d='M41 175.5L60.05 164.675V186.325L41 175.5Z' fill='currentColor' class='svg-cor-fill'/>" +
    "<path d='M41 151.5L60.05 140.675V162.325L41 151.5Z' fill='currentColor' class='svg-cor-fill'/>" +
    "<path d='M41 127.5L60.05 116.675V138.325L41 127.5Z' fill='currentColor' class='svg-cor-fill'/>" +
    "<circle cx='45' cy='45' r='45' fill='currentColor'/>" +
    "<circle cx='45' cy='45' r='25' fill='currentColor' class='svg-cor-fill'/></svg>";
  elementoChave?.setHTMLUnsafe(svgChave);

  let elementoSvg: SVGSVGElement | null | undefined = elementoChave?.querySelector("svg");
  if (elementoChave === null || elementoSvg === null || elementoSvg === undefined) {
    return;
  }

  let classesChave: string[] = [
    "chave-escondida",
    "chave-primaria",
    "chave-estrangeira",
    "chave-mista",
  ];
  let nomeAtributoIndex: string = "index-fundo";
  let indexAtual: number = Number(
    elementoChave?.hasAttribute(nomeAtributoIndex)
      ? elementoChave?.getAttribute(nomeAtributoIndex)
      : "0",
  );

  if (indexAtual === classesChave.length - 1) {
    indexAtual = -1;
  }

  indexAtual++;

  elementoSvg.classList.value = classesChave[indexAtual];
  elementoChave?.setAttribute(nomeAtributoIndex, String(indexAtual));
};

const callbackCriarAtributoRelacional = (event: MouseEvent): void => {
  let novoAtributo: HTMLDivElement = document.createElement("div");
  (event.target as HTMLElement).parentElement?.append(novoAtributo);
  novoAtributo.outerHTML =
    '<div class="atributo" onmouseup="callbackAlterarAtributoRelacional(event)"><span class="chave"></span><span contenteditable="true" spellcheck="true" class="texto">atributo: tipo</span></div>';
};
