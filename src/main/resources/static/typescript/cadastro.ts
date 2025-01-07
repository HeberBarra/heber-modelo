let inputSenha: HTMLInputElement | null = document.querySelector("input[name='senha']");
let inputConfirmarSenha: HTMLInputElement | null = document.querySelector(
  "input[name='confirmar-senha']",
);
let btnEnviar: HTMLButtonElement | null = document.querySelector("form button#btn-enviar");
let btnEnviarVerdadeiro: HTMLButtonElement | null = document.querySelector(
  "form button#btn-enviar-verdadeiro",
);

btnEnviar?.addEventListener("click", () => {
  if (inputSenha == null || inputConfirmarSenha == null) return;

  if (inputSenha.value === inputConfirmarSenha.value) {
    btnEnviarVerdadeiro?.click();
  }
});
