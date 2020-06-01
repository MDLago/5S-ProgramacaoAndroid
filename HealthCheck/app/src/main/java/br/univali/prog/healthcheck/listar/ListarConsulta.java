package br.univali.prog.healthcheck.listar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import br.univali.prog.healthcheck.R;
import br.univali.prog.healthcheck.adapters.ConsultaRecyclerAdapter;
import br.univali.prog.healthcheck.db.DB;
import br.univali.prog.healthcheck.dominio.Consulta;
import br.univali.prog.healthcheck.editar.EditaConsulta;
import br.univali.prog.healthcheck.interfaces.RecyclerOnClickListerner;

public class ListarConsulta extends AppCompatActivity implements RecyclerOnClickListerner {

    private RecyclerView rv_ListaConsulta;
    private DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_consulta);

        db = new DB(getApplicationContext());

        rv_ListaConsulta = findViewById(R.id.rv_ListaConsulta);
        rv_ListaConsulta.setHasFixedSize(true);
        rv_ListaConsulta.setLayoutManager(getLinearLayout());
        rv_ListaConsulta.setAdapter(rvAdapter());
    }

    @Override
    protected void onResume() {
        super.onResume();

        rv_ListaConsulta.setAdapter(rvAdapter());
    }

    //tempo, 0 = curto, 1 = longo
    private void exibirMensagem(String msg,int tempo){
        Toast.makeText(getApplicationContext(),msg,tempo).show();
    }

    private void abrirEditarConsulta(int id){
        Intent i = new Intent(getApplicationContext(), EditaConsulta.class);
        i.putExtra("id",id);
        startActivity(i);
    }

    private LinearLayoutManager getLinearLayout(){
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(RecyclerView.VERTICAL);

        return llm;
    }

    private ConsultaRecyclerAdapter rvAdapter(){
        List<Consulta> list;
        ConsultaRecyclerAdapter adapter;

        try{
            list = db.buscarConsulta();
            adapter = new ConsultaRecyclerAdapter(getApplicationContext(),list);
            adapter.setRecyclerOnClickListerner(this);
            return adapter;
        }catch (SQLException e){
            exibirMensagem(e.getMessage(),1);
        }
        return null;
    }

    // Position é o ID do Consulta no banco de dados
    @Override
    public void onClickListener(View v, int position) {
        abrirEditarConsulta(position);

    }
}
