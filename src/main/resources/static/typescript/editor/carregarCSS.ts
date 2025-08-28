/**
 * Copyright (C) 2025 Heber Ferreira Barra, Matheus de Assis de Paula, Matheus Jun Alves Matuda.
 *
 * Licensed under the Massachusetts Institute of Technology (MIT) License.
 * You may obtain a copy of the license at:
 *
 *     https://choosealicense.com/licenses/mit/
 *
 * A short and simple permissive license with conditions only requiring preservation of copyright and license notices.
 * Licensed works, modifications, and larger works may be distributed under different terms and without source code.
 */

import { CLASSE_LINK_CSS_CARREGADO } from "./classesCSSElementos.js";

const cacheElementos: string[] = [];

export const carregarCSS = (nomeArquivo: string): void => {
  if (!nomeArquivo.endsWith(".css")) {
    nomeArquivo += ".css";
  }

  if (cacheElementos.includes(nomeArquivo)) return;

  let linkTag: HTMLLinkElement = document.createElement("link");
  linkTag.className = CLASSE_LINK_CSS_CARREGADO;
  linkTag.setAttribute("arquivo", nomeArquivo);
  linkTag.rel = "stylesheet";
  linkTag.type = "text/css";
  linkTag.href = `/css/elementos/${nomeArquivo}`;
  document.querySelector("head")?.appendChild(linkTag);

  cacheElementos.push(nomeArquivo);
};
