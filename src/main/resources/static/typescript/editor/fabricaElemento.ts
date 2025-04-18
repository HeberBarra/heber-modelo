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

interface ValoresJsonElemento {
  classesElemento: string[];
  valorHtmlInterno: string;
  propriedades: object;
}

let valorHTML: string;
let valoresClasses: string[];

export class FabricaElemento {
  private async pegarJsonElemento(tipoElemento: string): Promise<ValoresJsonElemento> {
    return await fetch(`/elementos/${tipoElemento.toLowerCase()}.json`).then(
      async (response: Response): Promise<any> => {
        return response.json();
      },
    );
  }

  public async pegarHTMLElemento(tipoElemento: string): Promise<string> {
    await this.pegarJsonElemento(tipoElemento).then(
      (valoresJson: ValoresJsonElemento) => (valorHTML = valoresJson.valorHtmlInterno),
    );

    return valorHTML;
  }

  public async pegarClassesElemento(tipoElemento: string): Promise<string[]> {
    await this.pegarJsonElemento(tipoElemento).then(
      (valoresJson: ValoresJsonElemento): string[] =>
        (valoresClasses = valoresJson.classesElemento),
    );

    return valoresClasses;
  }
}
