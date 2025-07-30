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

  // Placeholders para as imagens de chave primária, estrangeira e mista.
  let valoresFundo: string[] = ["", "var(--cor-borda)", "var(--cor-botao)", "var(--cor-cabecalho)"];
  let nomeAtributoIndex: string = "index-fundo";
  let indexAtual: number = Number(
    elementoChave?.hasAttribute(nomeAtributoIndex)
      ? elementoChave?.getAttribute(nomeAtributoIndex)
      : "0",
  );

  if (indexAtual === valoresFundo.length - 1) {
    indexAtual = -1;
  }

  indexAtual++;

  elementoChave?.style.setProperty("background-color", valoresFundo[indexAtual]);
  elementoChave?.setAttribute(nomeAtributoIndex, String(indexAtual));
};

const callbackCriarAtributoRelacional = (event: MouseEvent): void => {
  let novoAtributo: HTMLDivElement = document.createElement("div");
  (event.target as HTMLElement).parentElement?.append(novoAtributo);
  novoAtributo.outerHTML =
    '<div class="atributo" onmouseup="callbackAlterarAtributoRelacional(event)"><span class="chave"></span><span contenteditable="true" spellcheck="true" class="texto">atributo: tipo</span></div>';
};
