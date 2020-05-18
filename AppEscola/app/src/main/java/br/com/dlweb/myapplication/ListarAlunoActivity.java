package br.com.dlweb.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ListarAlunoActivity extends AppCompatActivity {

    SQLiteDatabase db;
    ListView lvAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_aluno);

        lvAlunos = findViewById(R.id.lvAlunos);

        listarAlunos();

        lvAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View v = lvAlunos.getChildAt(position);
                TextView tvListId = v.findViewById(R.id.tvListId);
                TextView tvListNome = v.findViewById(R.id.tvListNome);
                TextView tvListIdade = v.findViewById(R.id.tvListIdade);
                TextView tvListCurso = v.findViewById(R.id.tvListCurso);

                Intent i = new Intent(getApplicationContext(), EditarAlunoActivity.class);
                i.putExtra("id", tvListId.getText().toString());
                i.putExtra("nome", tvListNome.getText().toString());
                i.putExtra("idade", tvListIdade.getText().toString());
                i.putExtra("curso", tvListCurso.getText().toString());
                startActivity(i);
            }
        });
    }

    private void listarAlunos () {
        db = openOrCreateDatabase("escolas.db", Context.MODE_PRIVATE, null);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM aluno;");
        Cursor dados = db.rawQuery(sql.toString(), null);
        String[] from = {"_id", "nome", "idade", "curso"};
        int[] to = {R.id.tvListId, R.id.tvListNome, R.id.tvListIdade, R.id.tvListCurso};

        SimpleCursorAdapter scAdapter =
                new SimpleCursorAdapter(getApplicationContext(), R.layout.dados, dados, from, to, 0);

        lvAlunos.setAdapter(scAdapter);
        db.close();
    }
}
