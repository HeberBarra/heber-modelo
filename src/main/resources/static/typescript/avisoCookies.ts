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

let avisoCookies: HTMLDivElement | null = document.querySelector("#aviso-cookies");
let btnAviso: HTMLButtonElement | null = document.querySelector("#aviso-cookies button");

btnAviso?.addEventListener("click", () => {
  if (avisoCookies == null) {
    return;
  }

  avisoCookies.style.display = "none";
  window.localStorage.setItem("hm-esconder-cookies", "true");
});

if (avisoCookies != null && window.localStorage.getItem("hm-esconder-cookies") != null) {
  avisoCookies.style.display = "none";
}
