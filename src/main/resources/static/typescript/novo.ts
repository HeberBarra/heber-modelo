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

const autoresDiv: HTMLDivElement | null = document.querySelector(".autores");
const btnAdicionarAutor: HTMLButtonElement | null = document.querySelector("#btn-criar-autor");
const btnRemoverAutor: HTMLButtonElement | null = document.querySelector("#btn-remover-autor");
const textoLabelAutor: string = document.querySelector("#label-author")?.innerHTML ?? "";
const textoLabelEmail: string = document.querySelector("#label-e-mail")?.innerHTML ?? "";
const textoLabelNome: string = document.querySelector("#label-name")?.innerHTML ?? "";

btnAdicionarAutor?.addEventListener("click", (): void => {
  let novoAutorFieldset: HTMLFieldSetElement = document.createElement("fieldset");
  let legendAutor: HTMLLegendElement = document.createElement("legend");
  legendAutor.innerHTML = textoLabelAutor;

  let labelNome: HTMLLabelElement = document.createElement("label");
  let inputNome: HTMLInputElement = document.createElement("input");

  labelNome.innerHTML = textoLabelNome;
  inputNome.name = "autor[]";
  inputNome.required = true;
  inputNome.type = "text";

  labelNome.append(inputNome);

  let labelEmail: HTMLLabelElement = document.createElement("label");
  let inputEmail: HTMLInputElement = document.createElement("input");

  labelEmail.innerHTML = textoLabelEmail;
  inputEmail.name = "email[]";
  inputEmail.required = false;
  inputEmail.type = "email";

  labelEmail.append(inputEmail);

  novoAutorFieldset.append(legendAutor);
  novoAutorFieldset.append(labelNome);
  novoAutorFieldset.append(labelEmail);
  autoresDiv?.append(novoAutorFieldset);
  btnRemoverAutor?.removeAttribute("disabled");
});

btnRemoverAutor?.addEventListener("click", (): void => {
  let numeroAutores: number | undefined = autoresDiv?.childElementCount;

  if (numeroAutores === undefined || numeroAutores === 1) {
    return;
  }

  autoresDiv?.removeChild(autoresDiv?.children[numeroAutores - 1]);

  numeroAutores--;
  if (numeroAutores == 1) {
    btnRemoverAutor?.setAttribute("disabled", "true");
  }
});
