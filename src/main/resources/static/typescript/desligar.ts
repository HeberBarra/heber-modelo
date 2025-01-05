let btnDesligar: HTMLButtonElement | null = document.querySelector("#btn-desligar");
let formDesligar: HTMLFormElement | null = document.querySelector("#form-desligar");

btnDesligar?.addEventListener("click", () => {
  formDesligar?.submit();
});
