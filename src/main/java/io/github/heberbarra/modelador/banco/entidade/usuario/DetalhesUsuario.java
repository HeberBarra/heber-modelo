package io.github.heberbarra.modelador.banco.entidade.usuario;

import io.github.heberbarra.modelador.banco.UsuarioBanco;
import io.github.heberbarra.modelador.tradutor.TradutorWrapper;
import java.util.Collection;
import java.util.List;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class DetalhesUsuario implements UserDetailsService {

    private final IUsuarioRepositorio IUsuarioRepositorio;

    public DetalhesUsuario(IUsuarioRepositorio IUsuarioRepositorio) {
        this.IUsuarioRepositorio = IUsuarioRepositorio;
    }

    @Override
    public UserDetails loadUserByUsername(String nome) throws UsernameNotFoundException {
        Usuario usuario = IUsuarioRepositorio.getUsuarioByNome(nome);

        if (usuario == null) {
            usuario = IUsuarioRepositorio.getUsuarioByEmail(nome);
        }

        if (usuario == null) {
            usuario = pegarUsuarioPelaMatricula(nome);
        }

        if (usuario == null) {
            throw new UsernameNotFoundException(TradutorWrapper.tradutor.traduzirMensagem("error.user.notfound"));
        }

        return new User(usuario.getNome(), usuario.getSenha(), getAuthorities(usuario.getTipo()));
    }

    private @Nullable Usuario pegarUsuarioPelaMatricula(String matricula) {
        Usuario usuario;
        try {
            long numeroMatricula = Long.parseLong(matricula);
            usuario = IUsuarioRepositorio.getUsuarioByMatricula(numeroMatricula);
        } catch (NumberFormatException e) {
            return null;
        }

        return usuario;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String tipoUsuario) {
        GrantedAuthority authority;
        if (tipoUsuario.startsWith("P")) {
            authority = new SimpleGrantedAuthority(UsuarioBanco.PROFESSOR.getNomeUsuario());
        } else {
            authority = new SimpleGrantedAuthority(UsuarioBanco.ESTUDANTE.getNomeUsuario());
        }

        return List.of(authority);
    }
}
