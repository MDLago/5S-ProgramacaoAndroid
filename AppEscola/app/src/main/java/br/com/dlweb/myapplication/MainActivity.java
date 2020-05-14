package br.com.dlweb.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        criarBD();

        Button clickAdicionar = findViewById(R.id.btnAdicionar);
        clickAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AdicionarAlunoActivity.class);
                startActivity(i);
            }
        });

        Button clickListar = findViewById(R.id.btnListar);
        clickListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ListarAlunoActivity.class);
                startActivity(i);
            }
        });
    }

    private void criarBD () {
        db = openOrCreateDatabase("escolas.db", Context.MODE_PRIVATE, null);
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT EXISTS aluno (");
        sql.append("_id INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sql.append("nome VARCHAR(100), ");
        sql.append("idade TINYINT(3), ");
        sql.append("curso VARCHAR(40)");
        sql.append(");");
        try {
            db.execSQL(sql.toString());
        } catch (SQLException e) {
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        db.close();
    }
}
