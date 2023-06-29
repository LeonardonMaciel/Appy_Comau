package com.example.appy_comau;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ClienteHolder extends RecyclerView.ViewHolder {

    public TextView nomeCliente;
    public ImageButton btnEditar;
    public ImageButton btnExcluir;
    public ImageButton btnEditar1;

    public ClienteHolder(View itemView) {
        super(itemView);
        nomeCliente = (TextView) itemView.findViewById(R.id.nomeCliente);
        btnEditar = (ImageButton) itemView.findViewById(R.id.btnEdit);
        btnExcluir = (ImageButton) itemView.findViewById(R.id.btnDelete);
        btnEditar1 = (ImageButton) itemView.findViewById(R.id.btnEdit1);
    }
}
