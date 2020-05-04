package br.univali.prog.aula8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ListView lvTimes;
    Spinner spSeries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvTimes = findViewById(R.id.lvTimes);
        spSeries = findViewById(R.id.spSeries);
        String[] series = new String[] {
                "Série A",
                "Série B",
                "Série C"
        };
        ArrayAdapter<String> spArrayAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, series);

        spSeries.setAdapter(spArrayAdapter);

        spSeries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        ArrayAdapter tsaArrayAdapter = ArrayAdapter.createFromResource
                                (getApplicationContext(), R.array.SerieA, android.R.layout.simple_list_item_checked);
                        lvTimes.setAdapter(tsaArrayAdapter);
                        break;
                    case 1:
                        ArrayAdapter tsbArrayAdapter = ArrayAdapter.createFromResource
                                (getApplicationContext(), R.array.SerieB, android.R.layout.simple_list_item_checked);
                        lvTimes.setAdapter(tsbArrayAdapter);
                        break;
                    default:
                        String [] serieC = new String []{
                                "Treze", "Vila Nova", "Brusque"
                        };
                        ArrayAdapter<String> tscAdapter = new ArrayAdapter<String>
                                (getApplicationContext(), android.R.layout.simple_list_item_checked, serieC);
                        lvTimes.setAdapter(tscAdapter);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        lvTimes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nomeTime = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Time Selecionado: " + nomeTime, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void visualizarTime(View v){
        String nomeSerie = spSeries.getSelectedItem().toString();

        int indexTime = lvTimes.getCheckedItemPosition();
        String nomeTime = "Nenhum";
        if(indexTime >= 0){ //Selecionou um time (-1) == nenhuma seleção
            nomeTime = lvTimes.getItemAtPosition(indexTime).toString();
        }
        Toast.makeText(getApplicationContext(),
                        "Série informada: " + nomeSerie + " Time selecionado: " + nomeTime,
                        Toast.LENGTH_LONG).show();
    }
}
