package br.univali.prog.healthcheck.adapters;

import android.content.Context;
import android.database.SQLException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import br.univali.prog.healthcheck.R;
import br.univali.prog.healthcheck.db.DB;
import br.univali.prog.healthcheck.dominio.Consulta;
import br.univali.prog.healthcheck.dominio.Medico;
import br.univali.prog.healthcheck.dominio.Paciente;
import br.univali.prog.healthcheck.interfaces.RecyclerOnClickListerner;

public class ConsultaRecyclerAdapter extends RecyclerView.Adapter<ConsultaRecyclerAdapter.MyViewHolder> {

    private List<Consulta> consultaList;
    private LayoutInflater layoutInflater;
    private RecyclerOnClickListerner recyclerOnClickListerner;
    private DB db;

    public ConsultaRecyclerAdapter(Context context, List<Consulta> consultaList){
        this.consultaList = consultaList;
        db = new DB(context);
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //region Obrigatório
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View v = layoutInflater.inflate(R.layout.recycle_layout_consulta, viewGroup,false);
        MyViewHolder mvh = new MyViewHolder(v);

        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        try{
            String nomePaciente = getNomePaciente(consultaList.get(position).idPaciente);
            String nomeMedico = getNomeMedico(consultaList.get(position).idMedico);
            String dataHoraInicio = getDateTimeFromLong(consultaList.get(position).dataHoraInicio);
            holder.listaConsultaPacienteNome.setText(nomePaciente);
            holder.listaConsultaMedicoNome.setText(nomeMedico);
            holder.listaConsultaDataHoraInicio.setText(dataHoraInicio);
        }catch (SQLException e){

        }

    }

    @Override
    public int getItemCount() {
        if(consultaList == null){
            return 0;
        }
        return consultaList.size();
    }

    //endregion

    //region ViewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView listaConsultaPacienteNome;
        public TextView listaConsultaMedicoNome;
        public TextView listaConsultaDataHoraInicio;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            listaConsultaPacienteNome = itemView.findViewById(R.id.tvListarPacienteConsultaNome);
            listaConsultaMedicoNome = itemView.findViewById(R.id.tvListarMedicoConsultaNome);
            listaConsultaDataHoraInicio = itemView.findViewById(R.id.tvListarDataHoraConsulta);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(recyclerOnClickListerner != null){
                recyclerOnClickListerner.onClickListener(v, consultaList.get(getAdapterPosition()).id);
            }
        }
    }

    //endregion

    public void setRecyclerOnClickListerner (RecyclerOnClickListerner r){
        recyclerOnClickListerner = r;
    }

    private String getNomePaciente(int id) throws SQLException {
        Paciente paciente = db.buscaPacienteFromID(id);
        return paciente.nome;
    }
    private String getNomeMedico(int id) throws SQLException{
        Medico medico = db.buscaMedicoFromID(id);
        return medico.nome;
    }
    private String getDateTimeFromLong(long dateTime){
        Calendar  calendar = new GregorianCalendar();
        calendar.setTimeInMillis(dateTime);

        StringBuilder str = new StringBuilder();
        str.append(calendar.get(Calendar.DAY_OF_MONTH));
        str.append("-");
        str.append(calendar.get(Calendar.MONTH));
        str.append("-");
        str.append(calendar.get(Calendar.YEAR));
        str.append("  ");
        str.append(calendar.get(Calendar.HOUR_OF_DAY));
        str.append(":");
        if(calendar.get(Calendar.MINUTE) == 0){
            str.append("00");
        }else{
            str.append(calendar.get(Calendar.MINUTE));
        }

        return str.toString();
    }
}
