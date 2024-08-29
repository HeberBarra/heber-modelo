#!/usr/bin/env python3

import subprocess
import os


def formatar_arquivos():
    if 'windows' in os.name.lower():
        comando = r'.\gradlew.bat'
    else:
        comando = './gradlew'

    subprocess.run([comando, 'spotlessApply'])


comando_git = 'git diff --staged --name-only'
resultado_comando = subprocess.check_output(comando_git, shell=True)
resultado_comando = resultado_comando.decode('utf-8')
arquivos_modificados = resultado_comando.split('\n')
arquivos_modificados.pop()

if any('java' in arquivo for arquivo in arquivos_modificados):
    formatar_arquivos()

for arquivo in arquivos_modificados:
    subprocess.run(['git', 'add', arquivo])
