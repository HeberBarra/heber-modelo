package io.github.heberbarra.modelador.banco.entidade.usuario;

import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsuarioServices implements IUsuarioServices {

    private final UsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServices(UsuarioRepositorio usuarioRepositorio, PasswordEncoder passwordEncoder) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();

        usuario.setMatricula(usuarioDTO.getMatricula());
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        usuario.setTipo(usuarioDTO.getTipo());

        usuarioRepositorio.save(usuario);
    }

    @Override
    public Usuario findUserByMatricula(long matricula) {
        return usuarioRepositorio.getUsuarioByMatricula(matricula);
    }

    @Override
    public Usuario findUserByEmail(String email) {
        return null;
    }

    @Override
    public Usuario findUserByNome(String nome) {
        return usuarioRepositorio.getUsuarioByNome(nome);
    }

    @Override
    public List<UsuarioDTO> findAllUsers() {
        return usuarioRepositorio.findAll().stream().map(this::convertToDTO).toList();
    }

    private UsuarioDTO convertToDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();

        usuarioDTO.setMatricula(usuario.getMatricula());
        usuarioDTO.setNome(usuario.getNome());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setTipo(usuario.getTipo());

        return usuarioDTO;
    }
}
