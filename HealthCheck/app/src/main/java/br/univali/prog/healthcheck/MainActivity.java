package br.univali.prog.healthcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import br.univali.prog.healthcheck.adicionar.AdicionaConsulta;
import br.univali.prog.healthcheck.adicionar.AdicionaMedico;
import br.univali.prog.healthcheck.adicionar.AdicionaPaciente;
import br.univali.prog.healthcheck.db.DB;
import br.univali.prog.healthcheck.listar.ListarConsulta;
import br.univali.prog.healthcheck.listar.ListarMedico;
import br.univali.prog.healthcheck.listar.ListarPaciente;

public class MainActivity extends AppCompatActivity {

    DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DB(getApplicationContext());

        try {
            db.criarDB();
        }catch(SQLException e){
            exibirMensagem(e.toString());
        }
    }

    private void exibirMensagem(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }

    public void abrirAdicionarMedico(View v){
        Intent i = new Intent(getApplicationContext(), AdicionaMedico.class);
        startActivity(i);
    }

    public void abrirAdicionarPaciente(View v){
        Intent i = new Intent(getApplicationContext(), AdicionaPaciente.class);
        startActivity(i);
    }

    public void abrirAdicionarConsulta(View v){
        Intent i = new Intent(getApplicationContext(), AdicionaConsulta.class);
        startActivity(i);
    }

    public void abrirListarMedico(View v){
        Intent i = new Intent(getApplicationContext(), ListarMedico.class);
        startActivity(i);
    }

    public void abrirListarPaciente(View v){
        Intent i = new Intent(getApplicationContext(), ListarPaciente.class);
        startActivity(i);
    }

    public void abrirListarConsulta(View v){
        Intent i = new Intent(getApplicationContext(),ListarConsulta.class);
        startActivity(i);
    }
}
