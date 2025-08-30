/*
 * Copyright (c) 2025. Heber Ferreira Barra, Matheus de Assis de Paula, Matheus Jun Alves Matuda.
 *
 * Licensed under the Massachusetts Institute of Technology (MIT) License.
 * You may obtain a copy of the license at:
 *
 *   https://choosealicense.com/licenses/mit/
 *
 * A short and simple permissive license with conditions only requiring preservation of copyright and license notices.
 * Licensed works, modifications, and larger works may be distributed under different terms and without source code.
 *
 */

import { ComponenteDiagramaOuvinte } from "./componenteDiagramaOuvinte.js";

export interface ComponenteDiagramaOuvido {
  adicionarOuvinte(ouvinte: ComponenteDiagramaOuvinte): void;

  atualizarOuvintes(): void;

  removerOuvinte(
    ouvinte: ComponenteDiagramaOuvinte,
    alertar: boolean,
  ): ComponenteDiagramaOuvinte | null;

  removerTodosOuvintes(): ComponenteDiagramaOuvinte[];
}
