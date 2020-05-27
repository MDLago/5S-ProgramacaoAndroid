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

import br.univali.prog.healthcheck.R;
import br.univali.prog.healthcheck.db.DB;
import br.univali.prog.healthcheck.dominio.Paciente;

public class EditaPaciente extends AppCompatActivity {

    private DB db;
    private Paciente paciente;

    private EditText etNome;
    private Spinner spGRPS;
    private EditText etRua;
    private EditText etNumero;
    private EditText etCidade;
    private Spinner spUF;
    private EditText etCelular;
    private EditText etFixo;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edita_paciente);

        db = new DB(getApplicationContext());

        getPacienteFromIntent();
        setFields();
        setFieldSValues();
    }

    //region Outros
    //tempo, 0 = curto, 1 = longo
    private void exibirMensagem(String msg, int tempo) {
        Toast.makeText(getApplicationContext(), msg, tempo).show();
    }

    private void spinnerUF() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.UF));
        spUF.setAdapter(arrayAdapter);
    }

    private void spinnerGRPS() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.TipoSangue));
        spGRPS.setAdapter(arrayAdapter);
    }

    private void getPacienteFromIntent(){
        Intent i = getIntent();

        int id = i.getIntExtra("id",-1);

        if(id == -1){
            exibirMensagem("Não encontrado",0);
            this.finish();
            return;
        }

        try{
            paciente = db.buscaPacienteFromID(id);
        }catch (SQLException e) {
            exibirMensagem(e.getMessage(), 1);
        }
    }

    private int getSpinnerIndexFromString(Spinner sp, String str){
        for (int i = 0; i < sp.getCount(); i++){
            if(sp.getItemAtPosition(i).toString().equals(str)){
                return i;
            }
        }
        return -1;
    }

    //endregion

    //region Metodos de inicialização da tela

    private void setFields(){
        etNome = findViewById(R.id.et_PacienteNome);
        spGRPS = findViewById(R.id.sp_PacienteTipoSangue);
        etRua = findViewById(R.id.et_PacienteRua);
        etNumero = findViewById(R.id.et_PacienteNumero);
        etCidade = findViewById(R.id.et_PacienteCidade);
        spUF = findViewById(R.id.sp_PacienteUF);
        etCelular = findViewById(R.id.et_PacienteCelular);
        etFixo = findViewById(R.id.et_PacienteFixo);
        spinnerUF();
        spinnerGRPS();
    }

    private void setFieldSValues(){
        etNome.setText(paciente.nome);
        spGRPS.setSelection(getSpinnerIndexFromString(spGRPS, paciente.grp_Sanguineo));
        etRua.setText(paciente.rua);
        etNumero.setText(String.valueOf(paciente.numero));
        etCidade.setText(paciente.cidade);
        spUF.setSelection(getSpinnerIndexFromString(spUF,paciente.uf));
        etCelular.setText(paciente.celular);
        etFixo.setText(paciente.fixo);
    }
    //endregion

    //region Botões
    public void btnAtualizarPaciente(View v){
        if(campoVazio()){
            exibirMensagem("Favor preencher todos os campos.",0);
            return;
        }

        String nome = etNome.getText().toString().trim();
        String grps = spGRPS.getSelectedItem().toString();
        String rua= etRua.getText().toString().trim();
        int numero= Integer.parseInt(etNumero.getText().toString());
        String cidade= etCidade.getText().toString().trim();
        String uf= spUF.getSelectedItem().toString();
        String celular= etCelular.getText().toString().trim();
        String fixo= etFixo.getText().toString().trim();

        try{
            db.atualizarPaciente(nome,grps,rua,numero,cidade,uf,celular,fixo,paciente.id);
        }catch (SQLException e){
            exibirMensagem(e.getMessage(),1);
            return;
        }
        exibirMensagem("Atualizado com sucesso",0);
        this.finish();
    }

    public void btnExcluir(View v){
        try{
            db.excluirPaciente(paciente.id);
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
        return etNome.getText().toString().trim().isEmpty() ||
                spGRPS.getSelectedItem().toString().trim().isEmpty() ||
                etRua.getText().toString().trim().isEmpty() ||
                etNumero.getText().toString().trim().isEmpty() ||
                etCidade.getText().toString().trim().isEmpty() ||
                spUF.getSelectedItem().toString().trim().isEmpty() ||
                etCelular.getText().toString().trim().isEmpty() ||
                etFixo.getText().toString().trim().isEmpty();
    }

    //endregion
}

