const verificarStringNumero = (valor: string): boolean => {
  let regexVerificarNumero: RegExp = /^-?\d+$/g;
  return regexVerificarNumero.test(valor);
};

// Infelizmente não funciona com parenteses.
const calcularExpressao = (expressao: string): number | null => {
  let regexFiltroExpressao: RegExp = /(?<!\S)[0-9]+(?:[-+%~^*\/]+?\d+(?:\.\d+)?)+(?!\S)/g;

  if (expressao.match(regexFiltroExpressao) === null) {
    return null;
  }

  return eval(expressao);
};

const ajustarValorAtributo = (valor: string): number => {
  if (verificarStringNumero(valor)) {
    return parseFloat(valor);
  }

  let resultadoExpressao = calcularExpressao(valor);

  if (resultadoExpressao === null) {
    return 0;
  }

  return resultadoExpressao;
};

const modificarPropriedadeElemento = (
  elemento: HTMLElement | null,
  inputAtributo: HTMLInputElement | null,
  nomePropriedade: string,
): void => {
  if (elemento === null || inputAtributo === null) return;

  let novoValorAtributo: string = ajustarValorAtributo(inputAtributo.value) + "px";
  elemento.style.setProperty(nomePropriedade, novoValorAtributo);
};

const atualizarValorInput = (
  elemento: HTMLElement | null,
  inputAtributo: HTMLInputElement | null,
  nomePropriedade: string,
): void => {
  if (elemento === null || inputAtributo === null) return;

  let valorPropriedade = elemento.style.getPropertyValue(nomePropriedade);
  inputAtributo.value = valorPropriedade.substring(0, valorPropriedade.length - 2);
};

export { atualizarValorInput, modificarPropriedadeElemento };
