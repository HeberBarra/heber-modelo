const email: HTMLInputElement | null = document.querySelector("input[name='email']");
const senha: HTMLInputElement | null = document.querySelector("input[name='senha']");
const continuar: HTMLInputElement | null = document.querySelector("#continuar");

const checkInputs = () => {
  if (continuar == null) {
    return;
  }

  continuar.disabled = senha?.value.trim().length == 0 && email?.value.trim().length == 0;

};

checkInputs();
