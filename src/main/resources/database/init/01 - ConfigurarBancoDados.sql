CREATE DATABASE IF NOT EXISTS db_HeberModelo DEFAULT CHARACTER SET = utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;

USE db_HeberModelo;

CREATE TABLE IF NOT EXISTS tbUsuario (
    matricula_usuario NUMERIC(11),
    email_usuario VARCHAR(50) NOT NULL,
    nome_usuario VARCHAR(50) NOT NULL,
    senha_usuario TEXT NOT NULL,
    tipo_usuario ENUM('P', 'E') NOT NULL,
    UNIQUE (email_usuario),
    UNIQUE (nome_usuario),
    PRIMARY KEY (matricula_usuario)
) DEFAULT CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS tbAtividade (
    codigo_atividade TINYINT AUTO_INCREMENT,
    nome_atividade VARCHAR(100) NOT NULL,
    matricula_usuario NUMERIC(11),
    PRIMARY KEY (codigo_atividade),
    FOREIGN KEY (matricula_usuario)
    REFERENCES tbUsuario(matricula_usuario)
) DEFAULT CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS tbFeedback(
    codigo_feedback TINYINT AUTO_INCREMENT,
    descricao_feedback TEXT NOT NULL,
    codigo_atividade TINYINT,
    matricula_professor NUMERIC(11),
    PRIMARY KEY (codigo_feedback),
    FOREIGN KEY (codigo_atividade)
    REFERENCES tbAtividade(codigo_atividade),
    FOREIGN KEY (matricula_professor)
    REFERENCES tbUsuario(matricula_usuario)
) DEFAULT CHARSET = utf8mb4;
