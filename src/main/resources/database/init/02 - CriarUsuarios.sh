#!/bin/bash
set -e

mysql --protocol=socket -uroot -p$MYSQL_ROOT_PASSWORD <<EOSQL
CREATE USER IF NOT EXISTS 'estudante'@'$MYSQL_HOST' IDENTIFIED BY '$SENHA_ESTUDANTE';
GRANT INSERT, SELECT ON Sheepnator.tbAtividade TO 'estudante'@'$MYSQL_HOST';
GRANT INSERT, SELECT ON Sheepnator.tbUsuario TO 'estudante'@'$MYSQL_HOST';
GRANT SELECT ON Sheepnator.tbFeedback TO 'estudante'@'$MYSQL_HOST';

CREATE USER IF NOT EXISTS 'professor'@'$MYSQL_HOST' IDENTIFIED BY '$SENHA_PROFESSOR';
GRANT INSERT, SELECT, UPDATE, DELETE ON Sheepnator.* TO 'professor'@'$MYSQL_HOST';
FLUSH PRIVILEGES;
EOSQL
