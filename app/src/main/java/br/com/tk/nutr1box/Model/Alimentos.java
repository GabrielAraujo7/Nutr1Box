package br.com.tk.nutr1box.Model;

public class Alimentos {

    private String nome, marca, calorias, alergenicos, composicao;

    public Alimentos() {
    }

    public Alimentos(String nome, String marca, String calorias, String alergenicos, String composicao) {
        this.nome = nome;
        this.marca = marca;
        this.calorias = calorias;
        this.alergenicos = alergenicos;
        this.composicao = composicao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCalorias() {
        return calorias;
    }

    public void setCalorias(String calorias) {
        this.calorias = calorias;
    }

    public String getAlergenicos() {
        return alergenicos;
    }

    public void setAlergenicos(String alergenicos) {
        this.alergenicos = alergenicos;
    }

    public String getComposicao() {
        return composicao;
    }

    public void setComposicao(String composicao) {
        this.composicao = composicao;
    }

    @Override
    public String toString() {
        return "Historico{" +
                "nome='" + nome + '\'' +
                ", marca='" + marca + '\'' +
                ", calorias='" + calorias + '\'' +
                ", alergenicos='" + alergenicos + '\'' +
                ", composicao='" + composicao + '\'' +
                '}';
    }
}
