package com.example.appy_comau;

import java.io.Serializable;

public class Cliente implements Serializable {

    private int id;
    private String nome;
    private String garantia;
    private String uf;
    private boolean vip;
    private String modelorobo;
    private String serialrobo;
    private String modelocontrolador;
    private String serialcontrolador;
    private String aplicação;

    public Cliente(int id, String nome, String garantia, String uf, boolean vip, String modelorobo, String serialrobo, String modelocontrolador, String serialcontrolador, String aplicação){
        this.id = id;
        this.nome = nome;
        this.garantia = garantia;
        this.uf = uf;
        this.vip = vip;
        this.modelorobo = modelorobo;
        this.serialrobo = serialrobo;
        this.modelocontrolador = modelocontrolador;
        this.serialcontrolador = serialcontrolador;
        this.aplicação = aplicação;
    }
    public int getId(){ return this.id; }
    public String getNome(){ return this.nome; }
    public String getGarantia(){ return this.garantia; }
    public boolean getVip(){ return this.vip; }
    public String getUf(){ return this.uf; }
    public String getModelorobo(){ return this.modelorobo; }
    public String getSerialrobo(){ return this.serialrobo; }
    public String getModelocontrolador(){ return this.modelocontrolador; }
    public String getSerialcontrolador(){ return this.serialcontrolador; }
    public String getAplicação(){ return this.aplicação; }

    @Override
    public boolean equals(Object o){
        return this.id == ((Cliente)o).id;
    }

    @Override
    public int hashCode(){
        return this.id;
    }
}
