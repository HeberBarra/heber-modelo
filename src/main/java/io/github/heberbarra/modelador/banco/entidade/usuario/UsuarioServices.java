package io.github.heberbarra.modelador.banco.entidade.usuario;

import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsuarioServices implements IUsuarioServices {

    private final IUsuarioRepositorio IUsuarioRepositorio;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServices(IUsuarioRepositorio IUsuarioRepositorio, PasswordEncoder passwordEncoder) {
        this.IUsuarioRepositorio = IUsuarioRepositorio;
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

        IUsuarioRepositorio.save(usuario);
    }

    @Override
    public Usuario findUserByMatricula(long matricula) {
        return IUsuarioRepositorio.getUsuarioByMatricula(matricula);
    }

    @Override
    public Usuario findUserByEmail(String email) {
        return IUsuarioRepositorio.getUsuarioByEmail(email);
    }

    @Override
    public Usuario findUserByNome(String nome) {
        return IUsuarioRepositorio.getUsuarioByNome(nome);
    }

    @Override
    public List<UsuarioDTO> findAllUsers() {
        return IUsuarioRepositorio.findAll().stream().map(this::convertToDTO).toList();
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
