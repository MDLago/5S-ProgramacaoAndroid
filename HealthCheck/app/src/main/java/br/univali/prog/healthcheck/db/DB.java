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

import br.univali.prog.healthcheck.dominio.Medico;
import br.univali.prog.healthcheck.dominio.Paciente;

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
    public void deletarDB() throws SQLException{
        context.deleteDatabase(nomeDB);
    }
    private int grpSangueToInt(String grp_sanguineo){
        switch (grp_sanguineo){
            case "A-":
                return 1;
            case "A+":
                return 2;
            case "B-":
                return 3;
            case "B+":
                return 4;
            case "AB-":
                return 5;
            case "AB+":
                return 6;
            case "O-":
                return 7;
            case "O+":
                return 8;
            default:
                throw new SQLException("Grupo Sanguineo Invalido");
        }
    }
    private String grpSangueToString(int grp_sanguineo){
        switch (grp_sanguineo){
            case 1:
                return "A-";
            case 2:
                return "A+";
            case 3:
                return "B-";
            case 4:
                return "B+";
            case 5:
                return "AB-";
            case 6:
                return "AB+";
            case 7:
                return "O-";
            case 8:
                return "O+";
            default:
                throw new SQLException("Grupo Sanguineo Invalido");
        }
    }
    //endregion

    //region CODIGOS SQLs

            //region Criação
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
            //endregion

            //region INSERTS
    private String SQL_InsertMedico(){

                StringBuilder sql = new StringBuilder();

                sql.append("INSERT INTO medico (nome,crm,logradouro,numero,cidade,uf,celular,fixo) ");
                sql.append("VALUES (?,?,?,?,?,?,?,?)");

                return sql.toString();
            }

    private String SQL_InsertPaciente(){

        StringBuilder sql = new StringBuilder();

        sql.append("INSERT INTO paciente (nome,grp_sanguineo,logradouro,numero,cidade,uf,celular,fixo) ");
        sql.append("VALUES (?,?,?,?,?,?,?,?)");

        return sql.toString();
    }

            //endregion

            //region UPDATES
    private String SQL_UpdateMedico(){
        StringBuilder sql = new StringBuilder();

        sql.append("UPDATE medico SET ");
        sql.append("nome = ?,");
        sql.append("crm = ?,");
        sql.append("logradouro = ?,");
        sql.append("numero = ?,");
        sql.append("cidade = ?,");
        sql.append("uf = ?,");
        sql.append("celular = ?, ");
        sql.append("fixo = ?");

        sql.append("WHERE _id = ?");

        return sql.toString();
    }

    private String SQL_UpdatePaciente(){
        StringBuilder sql = new StringBuilder();

        sql.append("UPDATE paciente SET ");
        sql.append("nome = ?,");
        sql.append("grp_sanguineo = ?,");
        sql.append("logradouro = ?,");
        sql.append("numero = ?,");
        sql.append("cidade = ?,");
        sql.append("uf = ?,");
        sql.append("celular = ?, ");
        sql.append("fixo = ?");

        sql.append("WHERE _id = ?");

        return sql.toString();
    }

            //endregion

            //region SELECTS

    private String SQL_SelectMedico(){
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM medico");

        return sql.toString();
    }

    private String SQL_SelectPacienteFromID(){
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM paciente ");
        sql.append("WHERE _id = ?");

        return sql.toString();
    }

    private String SQL_SelectPaciente(){
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM paciente");

        return sql.toString();
    }

    private String SQL_SelectMedicoFromID(){
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM medico ");
        sql.append("WHERE _id = ?");

        return sql.toString();
    }

            //endregion

            //region DELETES
    private String SQL_DeleteMedicoFromID(){
        StringBuilder sql = new StringBuilder();

        sql.append("DELETE FROM medico ");
        sql.append("WHERE _id = ?");

        return sql.toString();
    }

    private String SQL_DeletePacienteFromID(){
        StringBuilder sql = new StringBuilder();

        sql.append("DELETE FROM paciente ");
        sql.append("WHERE _id = ?");

        return sql.toString();
    }
            //endregion
    //endregion

    //region Funções Inserts
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

    public void inserirPaciente(@Nullable String nome, String grp_Sanguineo, @Nullable String rua,int numero,
                              @Nullable String cidade,@Nullable String uf,@Nullable String celular,
                              @Nullable String fixo) throws SQLException{

        abrirDB();

        SQLiteStatement sqtmt = db.compileStatement(SQL_InsertPaciente());

        sqtmt.bindString(1,nome);
        sqtmt.bindLong(2, grpSangueToInt(grp_Sanguineo));
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

    //region Funções Updates
    public void atualizarMedico(@Nullable String nome, @Nullable String crm, @Nullable String rua, @Nullable int numero,
                                @Nullable String cidade, @Nullable String uf, @Nullable String celular,
                                @Nullable String fixo, int id) throws SQLException {

        abrirDB();

        SQLiteStatement sqtmt = db.compileStatement(SQL_UpdateMedico());

        sqtmt.bindString(1, nome);
        sqtmt.bindString(2, crm);
        sqtmt.bindString(3, rua);
        sqtmt.bindLong(4, numero);
        sqtmt.bindString(5, cidade);
        sqtmt.bindString(6, uf);
        sqtmt.bindString(7, celular);
        sqtmt.bindString(8, fixo);

        sqtmt.bindLong(9, id);

        sqtmt.executeUpdateDelete();
        fecharDB();
    }

    public void atualizarPaciente(@Nullable String nome, @Nullable String grp_Sanguineo, @Nullable String rua, @Nullable int numero,
                                @Nullable String cidade, @Nullable String uf, @Nullable String celular,
                                @Nullable String fixo, int id) throws SQLException {

        abrirDB();

        SQLiteStatement sqtmt = db.compileStatement(SQL_UpdatePaciente());

        sqtmt.bindString(1, nome);
        sqtmt.bindLong(2, grpSangueToInt(grp_Sanguineo));
        sqtmt.bindString(3, rua);
        sqtmt.bindLong(4, numero);
        sqtmt.bindString(5, cidade);
        sqtmt.bindString(6, uf);
        sqtmt.bindString(7, celular);
        sqtmt.bindString(8, fixo);

        sqtmt.bindLong(9, id);

        sqtmt.executeUpdateDelete();
        fecharDB();
    }
    //endregion

    //region Funções Selects
    private Cursor getCursor (String sql) throws  SQLException{
        Cursor cursor = db.rawQuery(sql,null);
        return cursor;
    }

    public Medico buscaMedicoFromID(int idRv) throws SQLException{
        Medico medico;

        abrirDB();

        String sql = SQL_SelectMedicoFromID();
        sql = sql.replace("?",String.valueOf(idRv));
        Cursor cursor = getCursor(sql);

        if(cursor == null || cursor.getCount() == 0){
            return null;
        }

        cursor.moveToFirst();

        int id = cursor.getInt(0);
        String nome = cursor.getString(1);
        String crm = cursor.getString(2);
        String rua = cursor.getString(3);
        int numero = cursor.getInt(4);
        String cidade = cursor.getString(5);
        String uf = cursor.getString(6);
        String celular = cursor.getString(7);
        String fixo = cursor.getString(8);

        db.close();

        medico = new Medico(id,nome,crm,rua,numero,cidade,uf,celular,fixo);
        return medico;
    }

    public List<Medico> buscarMedico() throws SQLException{
        ArrayList<Medico> arrayList = new ArrayList<>();

        abrirDB();
        Cursor cursor = getCursor(SQL_SelectMedico());

        if(cursor == null || cursor.getCount() == 0){
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

    public Paciente buscaPacienteFromID(int idRv) throws SQLException{
        Paciente paciente;

        abrirDB();

        String sql = SQL_SelectPacienteFromID();
        sql = sql.replace("?",String.valueOf(idRv));
        Cursor cursor = getCursor(sql);

        if(cursor == null || cursor.getCount() == 0){
            return null;
        }

        cursor.moveToFirst();

        int id = cursor.getInt(0);
        String nome = cursor.getString(1);
        String grp_Sanguineo = grpSangueToString(cursor.getInt(2));
        String rua = cursor.getString(3);
        int numero = cursor.getInt(4);
        String cidade = cursor.getString(5);
        String uf = cursor.getString(6);
        String celular = cursor.getString(7);
        String fixo = cursor.getString(8);

        db.close();

        paciente = new Paciente(id,nome,grp_Sanguineo,rua,numero,cidade,uf,celular,fixo);
        return paciente;
    }

    public List<Paciente> buscarPaciente() throws SQLException{
        ArrayList<Paciente> arrayList = new ArrayList<>();

        abrirDB();
        Cursor cursor = getCursor(SQL_SelectPaciente());

        if(cursor == null || cursor.getCount() == 0){
            return null;
        }

        cursor.moveToFirst();

        do{
            int id = cursor.getInt(0);
            String nome = cursor.getString(1);
            String grp_Sanguineo = grpSangueToString(cursor.getInt(2));
            String rua = cursor.getString(3);
            int numero = cursor.getInt(4);
            String cidade = cursor.getString(5);
            String uf = cursor.getString(6);
            String celular = cursor.getString(7);
            String fixo = cursor.getString(8);

            Paciente paciente = new Paciente(id,nome,grp_Sanguineo,rua,numero,cidade,uf,celular,fixo);
            arrayList.add(paciente);

        }while(cursor.moveToNext());
        fecharDB();

        return arrayList;
    }

    //endregion

    //region Funções Deletes
    public void excluirMedico(int id) throws SQLException{
        abrirDB();

        SQLiteStatement sqtmt = db.compileStatement(SQL_DeleteMedicoFromID());

        sqtmt.bindLong(1,id);

        sqtmt.executeUpdateDelete();
        fecharDB();
    }

    public void excluirPaciente(int id) throws SQLException{
        abrirDB();

        SQLiteStatement sqtmt = db.compileStatement(SQL_DeletePacienteFromID());

        sqtmt.bindLong(1,id);

        sqtmt.executeUpdateDelete();
        fecharDB();
    }
    //endregion

}
