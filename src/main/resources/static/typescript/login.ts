let email: HTMLInputElement | null = document.querySelector("input[name='email']");
let senha: HTMLInputElement | null = document.querySelector("input[name='senha']");
let continuar: HTMLInputElement | null = document.querySelector("#continuar");

const checkInputs = () => {
  if (continuar == null) {
    return;
  }

  continuar.disabled = senha?.value.trim().length == 0 && email?.value.trim().length == 0;
};

email?.addEventListener("input", checkInputs);
senha?.addEventListener("input", checkInputs);

checkInputs();
