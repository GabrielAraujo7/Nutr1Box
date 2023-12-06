package br.com.tk.nutr1box.Model;

public class Usuario {

    private String id;
    private String nome;
    private String email;
    private String senha;
    private String sexo;
    private long numero;
    private Double peso;
    private Double altura;

    private String meta;

    public Usuario() {
    }

    public Usuario(String id, String nome, String email, String senha, String sexo, long numero, Double peso, Double altura, String meta) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.sexo = sexo;
        this.numero = numero;
        this.peso = peso;
        this.altura = altura;
        this.meta = meta;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public long getNumero() {
        return numero;
    }

    public void setNumero(long numero) {
        this.numero = numero;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", sexo='" + sexo + '\'' +
                ", numero=" + numero +
                ", peso=" + peso +
                ", altura=" + altura +
                ", meta='" + meta + '\'' +
                '}';
    }
}
