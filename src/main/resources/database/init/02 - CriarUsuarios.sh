#!/bin/bash
set -e

mysql --protocol=socket -uroot -p"$MYSQL_ROOT_PASSWORD" <<EOSQL
CREATE USER IF NOT EXISTS 'estudante'@'$MYSQL_HOST' IDENTIFIED BY '$SENHA_ESTUDANTE';
GRANT INSERT, SELECT ON db_HeberModelo.tbAtividade TO 'estudante'@'$MYSQL_HOST';
GRANT INSERT, SELECT ON db_HeberModelo.tbUsuario TO 'estudante'@'$MYSQL_HOST';
GRANT SELECT ON db_HeberModelo.tbFeedback TO 'estudante'@'$MYSQL_HOST';

CREATE USER IF NOT EXISTS 'professor'@'$MYSQL_HOST' IDENTIFIED BY '$SENHA_PROFESSOR';
GRANT INSERT, SELECT, UPDATE, DELETE ON db_HeberModelo.* TO 'professor'@'$MYSQL_HOST';
FLUSH PRIVILEGES;
EOSQL
