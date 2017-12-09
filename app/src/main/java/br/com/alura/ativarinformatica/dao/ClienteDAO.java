package br.com.alura.ativarinformatica.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.ativarinformatica.model.Cliente;

/**
 * Created by rafael on 04/12/17.
 */

public class ClienteDAO extends SQLiteOpenHelper {
    public ClienteDAO(Context context) {
        super(context, "Ativar", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Clientes (id INTEGER PRIMARY KEY, nome TEXT NOT NULL, endereco TEXT, telefone TEXT, site TEXT, caminhoFoto TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "";
        switch (oldVersion){
            case 1:
                sql = "ALTER TABLE Clientes ADD COLUMN caminhoFoto TEXT";
                db.execSQL(sql);
        }

    }

    public void insere(Cliente cliente) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosCliente(cliente);

        db.insert("Clientes", null, dados );
    }

    @NonNull
    private ContentValues pegaDadosCliente(Cliente cliente) {
        ContentValues dados = new ContentValues();
        dados.put("id",cliente.getId());
        dados.put("nome", cliente.getNome());
        dados.put("endereco", cliente.getEndereco());
        dados.put("telefone", cliente.getTelefone());
        dados.put("site", cliente.getSite());
        dados.put("caminhoFoto", cliente.getCaminhoFoto());
        return dados;
    }

    public List<Cliente> buscaClientes()  {
        String sql = "SELECT * FROM Clientes;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Cliente> clientes = new ArrayList<Cliente>();
        while (c.moveToNext()) {
            Cliente cliente = new Cliente();
            cliente.setId(c.getLong(c.getColumnIndex("id")));
            cliente.setNome(c.getString(c.getColumnIndex("nome")));
            cliente.setEndereco(c.getString(c.getColumnIndex("endereco")));
            cliente.setTelefone(c.getString(c.getColumnIndex("telefone")));
            cliente.setSite(c.getString(c.getColumnIndex("site")));
            cliente.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));
            clientes.add(cliente);
        }
        c.close();

        return clientes;
    }

    public void deleta(Cliente cliente) {
        SQLiteDatabase db = getWritableDatabase();

        String [] params = {String.valueOf(cliente.getId())};
        db.delete("Clientes", "id = ?", params);
    }

    public void altera(Cliente cliente) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosCliente(cliente);

        String[] params ={cliente.getId().toString()};
        db.update("Clientes", dados, "id = ?", params);
    }
}
