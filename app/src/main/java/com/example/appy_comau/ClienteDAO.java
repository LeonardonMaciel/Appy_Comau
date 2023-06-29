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
        @SuppressLint("Range")String garantia = cursor.getString(cursor.getColumnIndex("Garantia"));
        @SuppressLint("Range")String uf = cursor.getString(cursor.getColumnIndex("UF"));
        @SuppressLint("Range")boolean vip = cursor.getInt(cursor.getColumnIndex("Vip")) > 0;
        @SuppressLint("Range") String modelorobo = cursor.getString(cursor.getColumnIndex("Modelorobo"));
        @SuppressLint("Range") String serialrobo = cursor.getString(cursor.getColumnIndex("Serialrobo"));
        @SuppressLint("Range") String modelocontrolador = cursor.getString(cursor.getColumnIndex("Modelocontrolador"));
        @SuppressLint("Range") String serialcontrolador = cursor.getString(cursor.getColumnIndex("Serialcontrolador"));
        @SuppressLint("Range") String aplicação = cursor.getString(cursor.getColumnIndex("Aplicação"));

        if (nome.equals(name)) {
            clientes.add(new Cliente(id, nome, garantia, uf, vip, modelorobo, serialrobo, modelocontrolador, serialcontrolador, aplicação));
            rid.setR_id(id);
            rnome.setR_nome(nome);
            return clientes;

        }

        while (cursor.moveToNext()){
            id = cursor.getInt(cursor.getColumnIndex("ID"));
            nome = cursor.getString(cursor.getColumnIndex("Nome"));
            garantia = cursor.getString(cursor.getColumnIndex("Garantia"));
            uf = cursor.getString(cursor.getColumnIndex("UF"));
            vip = cursor.getInt(cursor.getColumnIndex("Vip")) > 0;
            modelorobo = cursor.getString(cursor.getColumnIndex("Modelorobo"));
            serialrobo = cursor.getString(cursor.getColumnIndex("Serialorobo"));
            modelocontrolador = cursor.getString(cursor.getColumnIndex("Modelocontrolador"));
            serialcontrolador = cursor.getString(cursor.getColumnIndex("Serialcontrolador"));
            aplicação = cursor.getString(cursor.getColumnIndex("Aplicação"));

            if (nome.equals(name)){
                clientes.add(new Cliente(id, nome, garantia, uf, vip, modelorobo, serialrobo, modelocontrolador, serialcontrolador, aplicação));
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
            @SuppressLint("Range")String garantia = cursor.getString(cursor.getColumnIndex("Garantia"));
            @SuppressLint("Range")String uf = cursor.getString(cursor.getColumnIndex("UF"));
            @SuppressLint("Range")boolean vip = cursor.getInt(cursor.getColumnIndex("Vip")) > 0;
            @SuppressLint("Range") String modelorobo = cursor.getString(cursor.getColumnIndex("Modelorobo"));
            @SuppressLint("Range") String serialrobo = cursor.getString(cursor.getColumnIndex("Serialrobo"));
            @SuppressLint("Range") String modelocontrolador = cursor.getString(cursor.getColumnIndex("Modelocontrolador"));
            @SuppressLint("Range") String serialcontrolador = cursor.getString(cursor.getColumnIndex("Serialcontrolador"));
            @SuppressLint("Range") String aplicação = cursor.getString(cursor.getColumnIndex("Aplicação"));
            clientes.add(new Cliente(id, nome, garantia, uf, vip, modelorobo, serialrobo, modelocontrolador, serialcontrolador, aplicação));
        }
        cursor.close();
        return clientes;
    }

    public Cliente retornarUltimo(){
        Cursor cursor = gw.getDatabase().rawQuery("SELECT * FROM Clientes ORDER BY ID DESC", null);
        if(cursor.moveToFirst()){
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("ID"));
            @SuppressLint("Range") String nome = cursor.getString(cursor.getColumnIndex("Nome"));
            @SuppressLint("Range") String garantia = cursor.getString(cursor.getColumnIndex("Garantia"));
            @SuppressLint("Range") String uf = cursor.getString(cursor.getColumnIndex("UF"));
            @SuppressLint("Range") boolean vip = cursor.getInt(cursor.getColumnIndex("Vip")) > 0;
            @SuppressLint("Range") String modelorobo = cursor.getString(cursor.getColumnIndex("Modelorobo"));
            @SuppressLint("Range") String serialrobo = cursor.getString(cursor.getColumnIndex("Serialrobo"));
            @SuppressLint("Range") String modelocontrolador = cursor.getString(cursor.getColumnIndex("Modelocontrolador"));
            @SuppressLint("Range") String serialcontrolador = cursor.getString(cursor.getColumnIndex("Serialcontrolador"));
            @SuppressLint("Range") String aplicação = cursor.getString(cursor.getColumnIndex("Aplicação"));
            cursor.close();
            return new Cliente(id, nome, garantia, uf, vip, modelorobo, serialrobo, modelocontrolador, serialcontrolador, aplicação);
        }
        return null;
    }

    public boolean salvar(String nome, String garantia, String uf, boolean vip, String modelorobo, String serialrobo, String modelocontrolador, String serialcontrolador, String aplicação){
        return salvar(0, nome, garantia, uf, vip, modelorobo, serialrobo, modelocontrolador, serialcontrolador, aplicação);
    }

    public boolean salvar(int id, String nome, String garantia, String uf, boolean vip, String modelorobo, String serialrobo, String modelocontrolador, String serialcontrolador, String aplicação){
        ContentValues cv = new ContentValues();
        cv.put("Nome", nome);
        cv.put("Garantia", garantia);
        cv.put("UF", uf);
        cv.put("Vip", vip ? 1 : 0);
        cv.put("ModeloRobo", modelorobo);
        cv.put("SerialRobo", serialrobo);
        cv.put("ModeloControlador", modelocontrolador);
        cv.put("SerialControlador", serialcontrolador);
        cv.put("aplicação", aplicação);
        if(id > 0)
            return gw.getDatabase().update(TABLE_CLIENTES, cv, "ID=?", new String[]{ id + "" }) > 0;
        else
            return gw.getDatabase().insert(TABLE_CLIENTES, null, cv) > 0;
    }

    public boolean excluir(int id){
        return gw.getDatabase().delete(TABLE_CLIENTES, "ID=?", new String[]{ id + "" }) > 0;
    }
 }

