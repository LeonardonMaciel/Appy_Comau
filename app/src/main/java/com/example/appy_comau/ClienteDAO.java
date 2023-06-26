package com.example.appy_comau;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO{

    private final String TABLE_CLIENTES = "Clientes";
    private static DbGateway gw;

    public ClienteDAO(Context ctx){
        gw = DbGateway.getInstance(ctx);
    }

    @SuppressLint("Range")
    public List<Cliente> retornarDados(){

        SearchCliente rid = new SearchCliente();
        SearchCliente rnome = new SearchCliente();


        SearchCliente nomecliente = new SearchCliente();
        String name = nomecliente.getTxtnome().toString();

        List<Cliente> clientes = new ArrayList<>();
        Cursor cursor = gw.getDatabase().rawQuery("SELECT * FROM Clientes", null);
        cursor.moveToFirst();
        @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("ID"));
        @SuppressLint("Range") String nome = cursor.getString(cursor.getColumnIndex("Nome"));
        @SuppressLint("Range")String sexo = cursor.getString(cursor.getColumnIndex("Sexo"));
        @SuppressLint("Range")String uf = cursor.getString(cursor.getColumnIndex("UF"));
        @SuppressLint("Range")boolean vip = cursor.getInt(cursor.getColumnIndex("Vip")) > 0;

        if (nome.equals(name)) {
            clientes.add(new Cliente(id, nome, sexo, uf, vip));
            rid.setR_id(id);
            rnome.setR_nome(nome);
            return clientes;

        }

        while (cursor.moveToNext()){
            id = cursor.getInt(cursor.getColumnIndex("ID"));
            nome = cursor.getString(cursor.getColumnIndex("Nome"));
            sexo = cursor.getString(cursor.getColumnIndex("Sexo"));
            uf = cursor.getString(cursor.getColumnIndex("UF"));
            vip = cursor.getInt(cursor.getColumnIndex("Vip")) > 0;

            if (nome.equals(name)){
                clientes.add(new Cliente(id, nome, sexo, uf, vip));
                rid.setR_id(id);
                rnome.setR_nome(nome);
                return clientes;
            }
        }

        if (nome.equals(name)) {
            return clientes;
        } else {
             return null;
        }
    }

    public List<Cliente> retornarTodos(){
        List<Cliente> clientes = new ArrayList<>();
        Cursor cursor = gw.getDatabase().rawQuery("SELECT * FROM Clientes", null);
        while(cursor.moveToNext()){
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("ID"));
            @SuppressLint("Range") String nome = cursor.getString(cursor.getColumnIndex("Nome"));
            @SuppressLint("Range")String sexo = cursor.getString(cursor.getColumnIndex("Sexo"));
            @SuppressLint("Range")String uf = cursor.getString(cursor.getColumnIndex("UF"));
            @SuppressLint("Range")boolean vip = cursor.getInt(cursor.getColumnIndex("Vip")) > 0;
            clientes.add(new Cliente(id, nome, sexo, uf, vip));
        }
        cursor.close();
        return clientes;
    }
    public Cliente retornarUltimo(){
        Cursor cursor = gw.getDatabase().rawQuery("SELECT * FROM Clientes ORDER BY ID DESC", null);
        if(cursor.moveToFirst()){
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("ID"));
            @SuppressLint("Range") String nome = cursor.getString(cursor.getColumnIndex("Nome"));
            @SuppressLint("Range") String sexo = cursor.getString(cursor.getColumnIndex("Sexo"));
            @SuppressLint("Range") String uf = cursor.getString(cursor.getColumnIndex("UF"));
            @SuppressLint("Range") boolean vip = cursor.getInt(cursor.getColumnIndex("Vip")) > 0;
            cursor.close();
            return new Cliente(id, nome, sexo, uf, vip);
        }
        return null;
    }

    public boolean salvar(String nome, String sexo, String uf, boolean vip){
        return salvar(0, nome, sexo, uf, vip);
    }

    public boolean salvar(int id, String nome, String sexo, String uf, boolean vip){
        ContentValues cv = new ContentValues();
        cv.put("Nome", nome);
        cv.put("Sexo", sexo);
        cv.put("UF", uf);
        cv.put("Vip", vip ? 1 : 0);
        if(id > 0)
            return gw.getDatabase().update(TABLE_CLIENTES, cv, "ID=?", new String[]{ id + "" }) > 0;
        else
            return gw.getDatabase().insert(TABLE_CLIENTES, null, cv) > 0;
    }

    public boolean excluir(int id){
        return gw.getDatabase().delete(TABLE_CLIENTES, "ID=?", new String[]{ id + "" }) > 0;
    }
 }

