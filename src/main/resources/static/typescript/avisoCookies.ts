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
