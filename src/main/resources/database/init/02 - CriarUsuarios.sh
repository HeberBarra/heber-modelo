#!/bin/bash
set -e

mysql --protocol=socket -uroot -p"$MYSQL_ROOT_PASSWORD" <<EOSQL
CREATE USER IF NOT EXISTS 'estudante'@'%' IDENTIFIED BY '$SENHA_ESTUDANTE';
GRANT INSERT, SELECT ON db_HeberModelo.tbAtividade TO 'estudante'@'%';
GRANT INSERT, SELECT ON db_HeberModelo.tbUsuario TO 'estudante'@'%';
GRANT SELECT ON db_HeberModelo.tbFeedback TO 'estudante'@'%';

CREATE USER IF NOT EXISTS 'estudante'@localhost IDENTIFIED BY '$SENHA_ESTUDANTE';
GRANT INSERT, SELECT ON db_HeberModelo.tbAtividade TO 'estudante'@localhost;
GRANT INSERT, SELECT ON db_HeberModelo.tbUsuario TO 'estudante'@localhost;
GRANT SELECT ON db_HeberModelo.tbFeedback TO 'estudante'@localhost;

CREATE USER IF NOT EXISTS 'professor'@'%' IDENTIFIED BY '$SENHA_PROFESSOR';
GRANT INSERT, SELECT, UPDATE, DELETE ON db_HeberModelo.* TO 'professor'@'%';

CREATE USER IF NOT EXISTS 'professor'@localhost IDENTIFIED BY '$SENHA_PROFESSOR';
GRANT INSERT, SELECT, UPDATE, DELETE ON db_HeberModelo.* TO 'professor'@localhost;

FLUSH PRIVILEGES;
EOSQL
