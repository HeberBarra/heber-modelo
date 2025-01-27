let componentes: NodeListOf<HTMLDivElement> = document.querySelectorAll(".componente");
let componenteAtual: HTMLDivElement;
let offsetX: number;
let offsetY: number;
let xInicial: number;
let redimensionando: boolean = false;

componentes.forEach((componente) => {
  componente.addEventListener("mousedown", (e) => {
    e.preventDefault();
    if (verificarDentroBorda(componente, e.clientX, e.clientY)) {
      redimensionando = true;
      xInicial = e.clientX;
      return;
    }
    offsetX = e.clientX - componente.getBoundingClientRect().left;
    offsetY = e.clientY - componente.getBoundingClientRect().top;
    componente.classList.add("dragging");
    document.addEventListener("mousemove", dragElement);
    componenteAtual = componente;
  });

  componente.addEventListener("mousemove", (e) => {
    if (!redimensionando) return;

    componente.style.width = `${componente.getBoundingClientRect().width + (e.clientX - xInicial)}px`;
    xInicial = e.clientX;
  });

  componente.addEventListener("mouseup", () => {
    componente.classList.remove("dragging");
    document.removeEventListener("mousemove", dragElement);
  });
});

document.addEventListener("mouseup", () => {
  redimensionando = false;
});

const verificarDentroBorda = (componente: HTMLElement, x: number, y: number): boolean => {
  let estiloComponente = getComputedStyle(componente);
  let larguraBorda: number = converterTamanhoParaNumero(estiloComponente.borderWidth);
  let alturaElemento: number = converterTamanhoParaNumero(estiloComponente.height);
  let larguraElemento: number = converterTamanhoParaNumero(estiloComponente.width);
  let posicaoX = x - componente.getBoundingClientRect().left;
  let posicaoY = y - componente.getBoundingClientRect().top;

  return (
    (posicaoX <= larguraBorda || posicaoX >= larguraElemento - larguraBorda) &&
    (posicaoY <= larguraBorda || posicaoY >= alturaElemento - larguraElemento)
  );
};

const converterTamanhoParaNumero = (tamanho: string): number => {
  return Number(tamanho.substring(0, tamanho.length - 2));
};

const dragElement = (event: MouseEvent) => {
  event.preventDefault();
  let x = event.clientX - offsetX;
  let y = event.clientY - offsetY;
  componenteAtual.style.left = `${x}px`;
  componenteAtual.style.top = `${y}px`;
};
