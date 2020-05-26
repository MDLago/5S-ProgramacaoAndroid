package br.univali.prog.healthcheck.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.univali.prog.healthcheck.R;
import br.univali.prog.healthcheck.dominio.Paciente;
import br.univali.prog.healthcheck.interfaces.RecyclerOnClickListerner;

public class PacienteRecyclerAdapter extends RecyclerView.Adapter<PacienteRecyclerAdapter.MyViewHolder> {

    private List<Paciente> pacienteList;
    private LayoutInflater layoutInflater;
    private RecyclerOnClickListerner recyclerOnClickListerner;

    public PacienteRecyclerAdapter(Context context, List<Paciente> pacienteList){
        this.pacienteList = pacienteList;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //region Obrigatório
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View v = layoutInflater.inflate(R.layout.recycle_layout_paciente, viewGroup,false);
        MyViewHolder mvh = new MyViewHolder(v);

        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.listaPacienteNome.setText(pacienteList.get(position).nome);
        holder.listaPacienteTS.setText(String.valueOf(pacienteList.get(position).grp_Sanguineo));
    }

    @Override
    public int getItemCount() {
        if(pacienteList == null){
            return 0;
        }
        return pacienteList.size();
    }

    //endregion

    //region ViewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView listaPacienteNome;
        public TextView listaPacienteTS;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            listaPacienteNome = itemView.findViewById(R.id.tvListarPacienteNome);
            listaPacienteTS = itemView.findViewById(R.id.tvListarGRPSPaciente);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(recyclerOnClickListerner != null){
                recyclerOnClickListerner.onClickListener(v, pacienteList.get(getAdapterPosition()).id);
            }
        }
    }

    //endregion

    public void setRecyclerOnClickListerner (RecyclerOnClickListerner r){
        recyclerOnClickListerner = r;
    }
}
