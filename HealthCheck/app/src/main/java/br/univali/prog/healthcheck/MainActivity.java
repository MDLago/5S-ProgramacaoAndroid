package br.univali.prog.healthcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG);
    }

    public void abrirAdicionarMedico(View v){
        Intent i = new Intent(getApplicationContext(),AdicionaMedico.class);
        startActivity(i);
    }
}
