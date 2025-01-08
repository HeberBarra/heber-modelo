package io.github.heberbarra.modelador.banco.entidade.usuario;

import java.util.List;

public interface IUsuarioServices {

    void saveUsuario(UsuarioDTO usuarioDTO);

    Usuario findUserByMatricula(long matricula);

    Usuario findUserByEmail(String email);

    Usuario findUserByNome(String nome);

    List<UsuarioDTO> findAllUsers();
}
