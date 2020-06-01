package br.univali.prog.healthcheck.dominio;

public class Consulta {

    public final int id;
    public final int idPaciente;
    public final int idMedico;
    public final long dataHoraInicio;
    public final long dataHoraFim;
    public final String observacoes;


    public Consulta(int id, int idPaciente, int idMedico, long dataHoraInicio, long dataHoraFim, String observacoes) {
        this.id = id;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
        this.observacoes = observacoes;
    }
}
