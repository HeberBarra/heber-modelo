#!/usr/bin/env python3

import subprocess
import os


def formatar_java():
    if 'windows' in os.name.lower():
        comando = r'.\gradlew.bat'
    else:
        comando = './gradlew'

    subprocess.run([comando, 'spotlessApply'])


def formatar_prettier():
    comando_formatar_html = ['npx', 'prettier', '--write', 'src/main/resources/templates']
    comando_formatar_scss = ['npx', 'prettier', '--write', 'src/main/resources/static/scss']
    comando_formatar_type = ['npx', 'prettier', '--write', 'src/main/resources/static/typescript']

    subprocess.run(comando_formatar_html)
    subprocess.run(comando_formatar_scss)
    subprocess.run(comando_formatar_type)


comando_git = 'git diff --staged --name-only'
resultado_comando = subprocess.check_output(comando_git, shell=True)
resultado_comando = resultado_comando.decode('utf-8')
arquivos_modificados = resultado_comando.split('\n')
arquivos_modificados.pop()

if any('java' in arquivo for arquivo in arquivos_modificados):
    formatar_java()

if any(tipo_arquivo for tipo_arquivo in ['html', 'scss', 'ts'] if any(tipo_arquivo in arquivo for arquivo in arquivos_modificados)):
    formatar_prettier()

for arquivo in arquivos_modificados:
    subprocess.run(['git', 'add', arquivo])
