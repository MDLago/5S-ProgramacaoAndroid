package br.univali.prog.healthcheck.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import br.univali.prog.healthcheck.objetos.Medico;

public class DB extends SQLiteOpenHelper{

    private static final String nomeDB = "consulta.db";
    private static final int versaoDB = 1;
    private Context context;
    private SQLiteDatabase db;

    //region Implementação Obrigatória
    public DB(@Nullable Context context) {
        super(context, nomeDB, null, versaoDB);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //endregion

    //region Metodos
    private void abrirDB() throws  SQLException{
        db = context.openOrCreateDatabase(nomeDB,Context.MODE_PRIVATE,null);
    }
    private void fecharDB() throws  SQLException{
        db.close();
    }
    public void criarDB() throws SQLException {
        abrirDB();

        String sql = SQL_TabelaMedico();
        db.execSQL(sql);

        sql = SQL_TabelaPaciente();
        db.execSQL(sql);

        sql = SQL_TabelaConsulta();
        db.execSQL(sql);

        fecharDB();
    }
    //endregion

    //region SQLs
    private String SQL_TabelaMedico() {
        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE IF NOT EXISTS medico (");
        sql.append("_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sql.append("nome VARCHAR(50), ");
        sql.append("crm VARCHAR(20), ");
        sql.append("logradouro VARCHAR(100), ");
        sql.append("numero MEDIUMINT(8), ");
        sql.append("cidade VARCHAR(30), ");
        sql.append("uf VARCHAR(2), ");
        sql.append("celular VARCHAR(20), ");
        sql.append("fixo VARCHAR(20)");
        sql.append(");");

        return sql.toString();
    }

    private String SQL_TabelaPaciente() {
        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE IF NOT EXISTS paciente (");
        sql.append("_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sql.append("nome VARCHAR(50), ");
        sql.append("grp_sanguineo TINYINT(1), ");
        sql.append("logradouro VARCHAR(100), ");
        sql.append("numero MEDIUMINT(8), ");
        sql.append("cidade VARCHAR(30), ");
        sql.append("uf VARCHAR(2), ");
        sql.append("celular VARCHAR(20), ");
        sql.append("fixo VARCHAR(20)");
        sql.append(");");

        return sql.toString();
    }

    private String SQL_TabelaConsulta() {
        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE IF NOT EXISTS paciente (");
        sql.append("_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sql.append("paciente_id INTEGER NOT NULL, ");
        sql.append("medico_id INTEGER NOT NULL, ");
        sql.append("data_hora_inicio DATETIME, ");
        sql.append("data_hora_fim DATETIME, ");
        sql.append("observacao VARCHAR(200), ");
        sql.append("FOREIGN KEY(paciente_id) REFERENCES paciente(id), ");
        sql.append("FOREIGN KEY(medico_id) REFERENCES medico(id)");
        sql.append(");");

        return sql.toString();
    }

    private String SQL_InsertMedico(){

        StringBuilder sql = new StringBuilder();

        sql.append("INSERT INTO medico (nome,crm,logradouro,numero,cidade,uf,celular,fixo) ");
        sql.append("VALUES (?,?,?,?,?,?,?,?)");

        return sql.toString();
    }

    private String SQL_SelectMedico(){
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM medico");

        return sql.toString();
    }
    //endregion

    //region Inserts
    public void inserirMedico(@Nullable String nome, @Nullable String crm, @Nullable String rua,@Nullable int numero,
                              @Nullable String cidade,@Nullable String uf,@Nullable String celular,
                              @Nullable String fixo) throws SQLException{

        abrirDB();

        SQLiteStatement sqtmt = db.compileStatement(SQL_InsertMedico());

        sqtmt.bindString(1,nome);
        sqtmt.bindString(2,crm);
        sqtmt.bindString(3,rua);
        sqtmt.bindLong(4,numero);
        sqtmt.bindString(5,cidade);
        sqtmt.bindString(6,uf);
        sqtmt.bindString(7,celular);
        sqtmt.bindString(8,fixo);

        sqtmt.executeInsert();
        fecharDB();
    }

    //endregion

    //region Selects
    public List<Medico> buscarMedico() throws SQLException{
        ArrayList<Medico> arrayList = new ArrayList<>();
        abrirDB();

        Cursor cursor = db.rawQuery(SQL_SelectMedico(),null);
        if(cursor == null){
            return null;
        }

        cursor.moveToFirst();
        do{
            int id = cursor.getInt(0);
            String nome = cursor.getString(1);
            String crm = cursor.getString(2);
            String rua = cursor.getString(3);
            int numero = cursor.getInt(4);
            String cidade = cursor.getString(5);
            String uf = cursor.getString(6);
            String celular = cursor.getString(7);
            String fixo = cursor.getString(8);

            Medico medico = new Medico(id,nome,crm,rua,numero,cidade,uf,celular,fixo);
            arrayList.add(medico);

        }while(cursor.moveToNext());
        fecharDB();
        return arrayList;
    }

    //endregion

}