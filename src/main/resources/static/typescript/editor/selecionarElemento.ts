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
const CLASSE_ELEMENTO_SELECIONADO = "selected";
let elementoSelecionado: HTMLElement | null;

const selecionarElemento = (elemento: HTMLElement): HTMLElement => {
  elementoSelecionado?.classList.remove(CLASSE_ELEMENTO_SELECIONADO);
  elementoSelecionado = elemento;
  elementoSelecionado.classList.add(CLASSE_ELEMENTO_SELECIONADO);
  return elementoSelecionado;
};

const removerSelecao = (): null => {
  elementoSelecionado?.classList.remove(CLASSE_ELEMENTO_SELECIONADO);
  elementoSelecionado = null;
  return elementoSelecionado;
};

export { selecionarElemento, removerSelecao };
