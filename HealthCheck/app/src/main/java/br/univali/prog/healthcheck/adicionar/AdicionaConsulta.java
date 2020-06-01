package br.univali.prog.healthcheck.adicionar;

import androidx.appcompat.app.AppCompatActivity;

import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import br.univali.prog.healthcheck.MaskEditText;
import br.univali.prog.healthcheck.R;
import br.univali.prog.healthcheck.db.DB;
import br.univali.prog.healthcheck.dominio.Medico;
import br.univali.prog.healthcheck.dominio.Paciente;

public class AdicionaConsulta extends AppCompatActivity {

    private DB db;

    List<Medico> medicos;
    List<Paciente> pacientes;

    private Spinner spMedico;
    private Spinner spPaciente;
    private EditText dataInicio;
    private EditText horaInicio;
    private EditText dataFim;
    private EditText horaFim;
    private EditText observacoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adiciona_consulta);

        spMedico = findViewById(R.id.sp_Medico);
        spPaciente = findViewById(R.id.sp_Paciente);
        dataInicio = findViewById(R.id.et_DataInicio);
        horaInicio = findViewById(R.id.et_HoraInicio);
        dataFim = findViewById(R.id.et_DataFim);
        horaFim = findViewById(R.id.et_HoraFim);
        observacoes = findViewById(R.id.et_Observacoes);

        db = new DB(getApplicationContext());
        setPacientesEMedicos();
        setDataMask();
        spinnerMedico();
        spinnerPaciente();
    }

    //region Outros
    //tempo, 0 = curto, 1 = longo
    private void exibirMensagem(String msg,int tempo){
        Toast.makeText(getApplicationContext(),msg,tempo).show();
    }

    private void setPacientesEMedicos(){
        medicos = db.buscarMedico();
        pacientes = db.buscarPaciente();
    }

    private void spinnerMedico(){
        ArrayAdapter<Medico> arrayAdapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,medicos);
        spMedico.setAdapter(arrayAdapter);
    }

    private void spinnerPaciente(){
        ArrayAdapter<Paciente> arrayAdapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,pacientes);
        spPaciente.setAdapter(arrayAdapter);
    }

    private void setDataMask(){
        dataInicio.addTextChangedListener(MaskEditText.mask(dataInicio,MaskEditText.FORMAT_DATE));
        dataFim.addTextChangedListener(MaskEditText.mask(dataFim,MaskEditText.FORMAT_DATE));
        horaInicio.addTextChangedListener(MaskEditText.mask(horaInicio,MaskEditText.FORMAT_HOUR));
        horaFim.addTextChangedListener(MaskEditText.mask(horaFim,MaskEditText.FORMAT_HOUR));
    }
    private Calendar criarCalendar(String[] data, String[] tempo){

        int dia = Integer.parseInt(data[0]);
        int mes = Integer.parseInt(data[1]);
        int ano = Integer.parseInt(data[2]);
        int hora = Integer.parseInt(tempo[0]);
        int minuto = Integer.parseInt(tempo[1]);

        return new GregorianCalendar(ano, mes, dia, hora, minuto);
    }
    //endregion

    //region Botões
    public void btnAdicionarConsulta(View v){
        if(campoVazio()){
            exibirMensagem("Favor preencher todos os campos.",0);
            return;
        }

        try {
            Medico medico = (Medico)spMedico.getSelectedItem();
            Paciente paciente = (Paciente)spPaciente.getSelectedItem();

            String[] tDataI = dataInicio.getText().toString().trim().split("-");
            String[] tHoraI = horaInicio.getText().toString().trim().split(":");
            String[] tDataF = dataFim.getText().toString().trim().split("-");
            String[] tHoraF = horaFim.getText().toString().trim().split(":");

            int idMedico = medico.id;
            int idPaciente = paciente.id;
            Calendar dataHoraInicio = criarCalendar(tDataI,tHoraI);
            Calendar dataHoraFim = criarCalendar(tDataF, tHoraF);

            if(dataHoraIncoerente(dataHoraInicio,dataHoraFim)){
                exibirMensagem("Data ou Hora fim é menor que o inicio", 0);
                return;
            }

            if(foraDeDia(dataHoraInicio)){
                exibirMensagem("Não há expediente nessa data de inicio",0);
                return;
            }

            if(foraDeDia(dataHoraFim)){
                exibirMensagem("Não há expediente nessa data fim",0);
                return;
            }

            if(foraDeHorario(dataHoraInicio) || foraDeHorario(dataHoraFim)){
                exibirMensagem("Fora do horário do expediente 8:00 12:00 e 13:30 as 17:30",0);
                return;
            }

            String observacoes = this.observacoes.getText().toString().trim();

            db.inserirConsulta(idMedico,idPaciente,dataHoraInicio.getTimeInMillis(),dataHoraFim.getTimeInMillis(),observacoes);
        }catch (SQLException e){
            exibirMensagem(e.getMessage(),1);
        }

        exibirMensagem("Adicionado com sucesso",0);
        this.finish();

    }
    public void btnVoltar(View v){
        this.finish();
    }

    //region VALIDAÇÕES

    private boolean campoVazio(){
        return spMedico.getSelectedItem().toString().trim().isEmpty() ||
                spPaciente.getSelectedItem().toString().trim().isEmpty() ||
                dataInicio.getText().toString().trim().isEmpty() ||
                horaInicio.getText().toString().trim().isEmpty() ||
                dataFim.getText().toString().trim().isEmpty() ||
                horaFim.getText().toString().trim().isEmpty();
    }

    private boolean dataHoraIncoerente(Calendar inicio, Calendar fim){
        return inicio.getTimeInMillis() >= fim.getTimeInMillis();
    }

    private boolean foraDeHorario(Calendar cal){
        return cal.get(Calendar.HOUR_OF_DAY) < 8 ||
                ((cal.get(Calendar.HOUR_OF_DAY) == 12 && cal.get(Calendar.MINUTE) > 0) && (cal.get(Calendar.HOUR_OF_DAY) == 13 && cal.get(Calendar.MINUTE) < 30)) ||
                (cal.get(Calendar.HOUR_OF_DAY) > 17 && cal.get(Calendar.MINUTE) > 30);
    }

    private boolean foraDeDia(Calendar cal){
        return cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    //endregion


}
