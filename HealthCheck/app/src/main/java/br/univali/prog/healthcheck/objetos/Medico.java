package br.univali.prog.healthcheck.objetos;

public class Medico {
    public final int id;
    public final String nome;
    public final String crm;
    public final String rua;
    public final int numero;
    public final String cidade;
    public final String uf;
    public final String celular;
    public final String fixo;


    public Medico(int id, String nome, String crm, String rua, int numero, String cidade, String uf, String celular, String fixo) {
        this.id = id;
        this.nome = nome;
        this.crm = crm;
        this.rua = rua;
        this.numero = numero;
        this.cidade = cidade;
        this.uf = uf;
        this.celular = celular;
        this.fixo = fixo;
    }
}