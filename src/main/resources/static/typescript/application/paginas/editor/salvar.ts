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

const divNomeDiagrama: HTMLDivElement | null = document.querySelector("#nome-diagrama");
const divAutoresDiagrama: HTMLDivElement | null = document.querySelector("#autor-diagrama");
const divEmailsDiagrama: HTMLDivElement | null = document.querySelector("#email-diagrama");
const divTiposDiagrama: HTMLDivElement | null = document.querySelector("#tipos-diagrama");
const mainDiagrama: HTMLElement | null = document.querySelector("main");

function separarStringLista(texto: string | undefined): string[] {
  if (texto === undefined) {
    return [];
  }

  return texto
    .substring(1, texto.length - 1)
    .split(",")
    .map((s: string): string => s.trim());
}

async function salvar(): Promise<void> {
  let nomeDiagrama: string = divNomeDiagrama?.innerText ?? "";
  let autoresDiagrama: string[] = separarStringLista(divAutoresDiagrama?.innerText);
  let emailsDiagrama: string[] = separarStringLista(divEmailsDiagrama?.innerText);
  let tiposDiagrama: string[] = separarStringLista(divTiposDiagrama?.innerText);
  let dadosDiagrama: string | undefined = mainDiagrama?.innerText;

  await fetch("/salvar", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      nome: nomeDiagrama,
      autores: autoresDiagrama,
      emails: emailsDiagrama,
      tipos: tiposDiagrama,
      dados: dadosDiagrama,
    }),
  });
}
