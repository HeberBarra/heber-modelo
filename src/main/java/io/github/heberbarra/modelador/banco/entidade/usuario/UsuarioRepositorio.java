package io.github.heberbarra.modelador.banco.entidade.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    Usuario getUsuarioByMatricula(long matricula);

    Usuario getUsuarioByNome(String name);
}
