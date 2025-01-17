package io.github.heberbarra.modelador.banco.entidade.atividade;

import io.github.heberbarra.modelador.banco.entidade.usuario.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAtividadeRepositorio extends JpaRepository<Atividade, Integer> {

    Atividade getAtividadeByCodigo(int codigo);

    Atividade getAtividadeByNome(String nome);

    List<Atividade> searchAtividadesByNomeContaining(String nome);

    List<Atividade> getAtividadesByUsuario(Usuario usuario);
}
