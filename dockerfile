FROM mysql:9.0.1

COPY ./src/main/resources/database /docker-entrypoint-initdb.d/