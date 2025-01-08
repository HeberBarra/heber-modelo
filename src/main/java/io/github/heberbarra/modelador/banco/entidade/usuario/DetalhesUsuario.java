package io.github.heberbarra.modelador.banco.entidade.usuario;

import java.util.ArrayList;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DetalhesUsuario implements UserDetailsService {

    private final UsuarioRepositorio usuarioRepositorio;

    public DetalhesUsuario(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.getUsuarioByNome(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não foi encontrado");
        }

        return new User(usuario.getEmail(), usuario.getSenha(), new ArrayList<>());
    }
}
