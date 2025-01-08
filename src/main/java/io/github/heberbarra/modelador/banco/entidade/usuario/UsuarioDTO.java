package io.github.heberbarra.modelador.banco.entidade.usuario;

public class UsuarioDTO {

    public UsuarioDTO() {}

    public UsuarioDTO(long matricula, String nome, String email, String senha, String confirmarSenha, String tipo) {
        this.matricula = matricula;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.confirmarSenha = confirmarSenha;
        this.tipo = tipo;
    }

    private long matricula;
    private String nome;
    private String email;
    private String senha;
    private String confirmarSenha;
    private String tipo;

    public long getMatricula() {
        return matricula;
    }

    public void setMatricula(long matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfirmarSenha() {
        return confirmarSenha;
    }

    public void setConfirmarSenha(String confirmarSenha) {
        this.confirmarSenha = confirmarSenha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
