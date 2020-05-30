package br.univali.prog.healthcheck.dominio;

public class Paciente {
    public final int id;
    public final String nome;
    public final String grp_Sanguineo;
    public final String rua;
    public final int numero;
    public final String cidade;
    public final String uf;
    public final String celular;
    public final String fixo;

    public Paciente(int id, String nome, String grp_Sanguineo, String rua, int numero, String cidade, String uf, String celular, String fixo) {
        this.id = id;
        this.nome = nome;
        this.grp_Sanguineo = grp_Sanguineo;
        this.rua = rua;
        this.numero = numero;
        this.cidade = cidade;
        this.uf = uf;
        this.celular = celular;
        this.fixo = fixo;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
