#!/usr/bin/env python3

import os
import subprocess
from subprocess import CalledProcessError

gradle_wrapper = '.\gradlew.bat' if 'windows' in os.name else './gradlew'


def criar_jar() -> None:
    comando_criar_jar = f'{gradle_wrapper} clean bootJar'.split(' ')
    subprocess.run(comando_criar_jar)


def pegar_versao() -> str:
    comando_pegar_versao = 'java -jar ./build/libs/heber-modelo.jar --version'.split(' ')
    resultado = subprocess.check_output(comando_pegar_versao)
    versao = resultado.decode('utf-8')
    versao = versao.replace('\n', '')
    return f'v{versao}'


def criar_tag(versao: str, mensagem: str) -> None:
    comando_criar_tag = ['git', 'tag', '-a', versao, '-m', f'{mensagem}']

    try:
        resultado = subprocess.check_output(comando_criar_tag).decode('utf-8')
        print(resultado)
    except CalledProcessError:
        print('Falha ao tentar criar tag')


def main() -> None:
    criar_jar()
    versao = pegar_versao()
    mensagem = input('Digite a mensagem da tag:\n> ').strip()

    if mensagem == '':
        print('Operação cancelada')
        return

    criar_tag(versao, mensagem)


if __name__ == '__main__':
    main()
