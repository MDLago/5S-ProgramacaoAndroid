package br.univali.prog.healthcheck.editar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import br.univali.prog.healthcheck.dominio.Consulta;
import br.univali.prog.healthcheck.dominio.Medico;
import br.univali.prog.healthcheck.dominio.Paciente;

public class EditaConsulta extends AppCompatActivity {

    private DB db;
    private Consulta consulta;

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
        setContentView(R.layout.activity_edita_consulta);

        db = new DB(getApplicationContext());
        getConsultaFromIntent();
        setPacientesEMedicos();
        setFields();
        setFieldSValues();
        setDataMask();
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

    private void getConsultaFromIntent(){
        Intent i = getIntent();

        int id = i.getIntExtra("id",-1);

        if(id == -1){
            exibirMensagem("Não encontrado",0);
            this.finish();
            return;
        }

        try{
            consulta = db.buscaConsultaFromID(id);
        }catch (SQLException e) {
            exibirMensagem(e.getMessage(), 1);
        }
    }

    private String getDateFromLong(long dateTime){
        Calendar  calendar = new GregorianCalendar();
        calendar.setTimeInMillis(dateTime);
        StringBuilder str = new StringBuilder();
        str.append(calendar.get(Calendar.DAY_OF_MONTH));
        str.append("-");
        str.append(calendar.get(Calendar.MONTH));
        str.append("-");
        str.append(calendar.get(Calendar.YEAR));

        return str.toString();
    }

    private String getTimeFromLong(long dateTime){
        Calendar  calendar = new GregorianCalendar();
        calendar.setTimeInMillis(dateTime);

        StringBuilder str = new StringBuilder();
        str.append(calendar.get(Calendar.HOUR_OF_DAY));
        str.append(":");
        if(calendar.get(Calendar.MINUTE) == 0){
            str.append("00");
        }else{
            str.append(calendar.get(Calendar.MINUTE));
        }

        return str.toString();
    }

    private int getSpinnerIndexPaciente(Spinner sp, int id){

        Paciente paciente;

        for (int i = 0; i < sp.getCount(); i++){
            paciente = (Paciente)sp.getItemAtPosition(i);
            if(paciente.id == id){
                return i;
            }
        }
        return -1;
    }

    private int getSpinnerIndexMedico(Spinner sp, int id){

        Medico medico;

        for (int i = 0; i < sp.getCount(); i++){
            medico = (Medico) sp.getItemAtPosition(i);
            if(medico.id == id){
                return i;
            }
        }
        return -1;
    }
    //endregion

    //region Metodos de inicialização da tela

    private void setFields(){
        spMedico = findViewById(R.id.sp_Medico);
        spPaciente = findViewById(R.id.sp_Paciente);
        dataInicio = findViewById(R.id.et_DataInicio);
        horaInicio = findViewById(R.id.et_HoraInicio);
        dataFim = findViewById(R.id.et_DataFim);
        horaFim = findViewById(R.id.et_HoraFim);
        observacoes = findViewById(R.id.et_Observacoes);
        spinnerMedico();
        spinnerPaciente();
    }

    private void setFieldSValues(){
        spPaciente.setSelection(getSpinnerIndexPaciente(spPaciente,consulta.idPaciente));
        spMedico.setSelection(getSpinnerIndexMedico(spMedico,consulta.idMedico));
        dataInicio.setText(getDateFromLong(consulta.dataHoraInicio));
        horaInicio.setText(getTimeFromLong(consulta.dataHoraInicio));
        dataFim.setText(getDateFromLong(consulta.dataHoraFim));
        horaFim.setText(getTimeFromLong(consulta.dataHoraFim));
        observacoes.setText(consulta.observacoes);
    }
    //endregion

    //region Botões
    public void btnAtualizarConsulta(View v){
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

            String observacoes = this.observacoes.getText().toString().trim();

            db.atualizarConsulta(idMedico,idPaciente,dataHoraInicio.getTimeInMillis(),dataHoraFim.getTimeInMillis(),observacoes,consulta.id);
        }catch (SQLException e){
            exibirMensagem(e.getMessage(),1);
        }
        exibirMensagem("Atualizado com sucesso",0);
        this.finish();
    }

    public void btnExcluir(View v){
        try{
            db.excluirConsulta(consulta.id);
        }catch (SQLException e){
            exibirMensagem(e.getMessage(),1);
            return;
        }
        exibirMensagem("Excluido com sucesso",0);
        this.finish();
    }

    //endregion

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

    //endregion
}
