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
import { CLASSE_ELEMENTO_SELECIONADO } from "./classesCSSElementos.js";

export const copiarElemento = (elementoSelecionado: HTMLElement | null): void => {
  if (elementoSelecionado == null) {
    return;
  }

  elementoSelecionado.classList.remove(CLASSE_ELEMENTO_SELECIONADO);
  navigator.clipboard.writeText(elementoSelecionado.outerHTML).then(
    () => {},
    () => {
      window.alert("Erro ao tentar copiar elemento");
    },
  );
  elementoSelecionado.classList.add(CLASSE_ELEMENTO_SELECIONADO);
};

export const cortarElemento = (elementoSelecionado: HTMLElement | null): void => {
  if (elementoSelecionado == null) {
    return;
  }

  copiarElemento(elementoSelecionado);

  elementoSelecionado.parentNode?.removeChild(elementoSelecionado);
};

export const colarElemento = (elementoPai: HTMLElement | null): void => {
  if (elementoPai == null) {
    return;
  }

  navigator.clipboard.readText().then((conteudo: string) => {
    let novoElemento: HTMLElement | null = document.createElement("div");
    elementoPai.appendChild(novoElemento);
    novoElemento.outerHTML = conteudo;
  });
};
