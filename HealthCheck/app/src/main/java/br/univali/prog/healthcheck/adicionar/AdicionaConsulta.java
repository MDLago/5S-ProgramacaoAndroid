package br.univali.prog.healthcheck.adicionar;

import androidx.appcompat.app.AppCompatActivity;

import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

            int idMedico = medico.id;
            int idPaciente = paciente.id;
            Date dataHoraInicio = getDateFromString(dataInicio+" "+horaInicio);
            Date dataHoraFim = getDateFromString(dataFim+" "+horaFim);
            String observacoes = this.observacoes.getText().toString().trim();

            db.inserirConsulta(idMedico,idPaciente,dataHoraInicio,dataHoraFim,observacoes);
        }catch (ParseException e){
            exibirMensagem(e.getMessage(),1);
        }

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

    //endregion


}
