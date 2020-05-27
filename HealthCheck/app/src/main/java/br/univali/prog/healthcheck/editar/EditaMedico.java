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

import br.univali.prog.healthcheck.MainActivity;
import br.univali.prog.healthcheck.R;
import br.univali.prog.healthcheck.db.DB;
import br.univali.prog.healthcheck.dominio.Medico;
import br.univali.prog.healthcheck.listar.ListarMedico;

public class EditaMedico extends AppCompatActivity {

    private DB db;
    private Medico medico;

    private EditText etNome;
    private EditText etCRM;
    private EditText etRua;
    private EditText etNumero;
    private EditText etCidade;
    private Spinner spUF;
    private EditText etCelular;
    private EditText etFixo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edita_medico);

        db = new DB(getApplicationContext());

        getMedicoFromIntent();
        setFields();
        setFieldSValues();
    }

    //region Outros
    //tempo, 0 = curto, 1 = longo
    private void exibirMensagem(String msg,int tempo){
        Toast.makeText(getApplicationContext(),msg,tempo).show();
    }

    private void spinnerUF(){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.UF));
        spUF.setAdapter(arrayAdapter);
    }

    //endregion

    //region Metodos de inicialização da tela

    private void setFields(){

        etNome = findViewById(R.id.et_MedicoNome);
        etCRM = findViewById(R.id.et_MedicoCRM);
        etRua = findViewById(R.id.et_MedicoRua);
        etNumero = findViewById(R.id.ev_MedicoNumero);
        etCidade = findViewById(R.id.et_MedicoCidade);
        spUF = findViewById(R.id.sp_MedicoUF);
        etCelular = findViewById(R.id.et_MedicoCelular);
        etFixo = findViewById(R.id.et_MedicoFixo);
        spinnerUF();
    }



    private void getMedicoFromIntent(){
        Intent i = getIntent();

        int id = i.getIntExtra("id",-1);

        if(id == -1){
            exibirMensagem("Não encontrado",0);
            this.finish();
            return;
        }

        try{
            medico = db.buscaMedicoFromID(id);
        }catch (SQLException e) {
            exibirMensagem(e.getMessage(), 1);
        }
    }

    private void setFieldSValues(){
        etNome.setText(medico.nome);
        etCRM.setText(medico.crm);
        etRua.setText(medico.rua);
        etNumero.setText(String.valueOf(medico.numero));
        etCidade.setText(medico.cidade);
        spUF.setSelection(getSpinnerIndexFromString(medico.uf));
        etCelular.setText(medico.celular);
        etFixo.setText(medico.fixo);
    }

    private int getSpinnerIndexFromString(String str){
        for (int i = 0; i < spUF.getCount(); i++){
            if(spUF.getItemAtPosition(i).toString().equals(str)){
                return i;
            }
        }
        return -1;
    }
    //endregion

    //region Botões
    public void btnAtualizarMedico(View v){
        if(campoVazio()){
            exibirMensagem("Favor preencher todos os campos.",0);
            return;
        }

        String nome = etNome.getText().toString().trim();
        String crm = etCRM.getText().toString().trim();
        String rua= etRua.getText().toString().trim();
        int numero= Integer.parseInt(etNumero.getText().toString());
        String cidade= etCidade.getText().toString().trim();
        String uf= spUF.getSelectedItem().toString();
        String celular= etCelular.getText().toString().trim();
        String fixo= etFixo.getText().toString().trim();

        try{
            db.atualizarMedico(nome,crm,rua,numero,cidade,uf,celular,fixo,medico.id);
        }catch (SQLException e){
            exibirMensagem(e.getMessage(),1);
            return;
        }
        exibirMensagem("Atualizado com sucesso",0);
        this.finish();
    }

    public void btnExcluir(View v){
        try{
            db.excluirMedico(medico.id);
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
                etCRM.getText().toString().trim().isEmpty() ||
                etRua.getText().toString().trim().isEmpty() ||
                etNumero.getText().toString().trim().isEmpty() ||
                etCidade.getText().toString().trim().isEmpty() ||
                spUF.getSelectedItem().toString().trim().isEmpty() ||
                etCelular.getText().toString().trim().isEmpty() ||
                etFixo.getText().toString().trim().isEmpty();
    }

    //endregion
}
