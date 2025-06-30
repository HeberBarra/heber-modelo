const callbackMarcador = (event: Event): void => {
  let classeChavePrimaria: string = "primary-key";
  let elementoAlvo: HTMLElement = event.target as HTMLElement;

  if (elementoAlvo.classList.contains(classeChavePrimaria)) {
    elementoAlvo.classList.remove(classeChavePrimaria);
  } else {
    elementoAlvo.classList.add(classeChavePrimaria);
  }
};

const callbackInverterAtributo = (event: MouseEvent): void => {
  if (event.button == 1) {
    let elementoAlvo: HTMLElement = (event.target as HTMLElement).parentElement as HTMLElement;
    elementoAlvo.append(...Array.from(elementoAlvo.childNodes).reverse());
  }
};
