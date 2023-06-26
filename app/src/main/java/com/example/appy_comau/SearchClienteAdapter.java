package com.example.appy_comau;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class SearchClienteAdapter extends RecyclerView.Adapter<ClienteHolder>{


    private final List<Cliente> clientes;

    private Activity getActivity(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

    public SearchClienteAdapter(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    @Override
    public ClienteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ClienteHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_lista, parent, false));
    }


    @Override
    public void onBindViewHolder(ClienteHolder holder, int position) {

        final Cliente cliente = clientes.get(position);

        holder.nomeCliente.setText(clientes.get(position).getNome());

        holder.btnEditar1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity(v);
                Intent intent = new Intent(v.getContext(), SuporteSearchCadastro.class);
                //Intent intent = activity.getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("cliente", cliente);
                activity.finish();
                activity.startActivity(intent);
            }
        } );

        holder.btnExcluir.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                final View view = v;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Confirmação")
                        .setMessage("Tem certeza que deseja excluir este cliente?")
                        .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int index = 1;
                                Cliente cliente = clientes.get(index);
                                ClienteDAO dao = new ClienteDAO(view.getContext());
                                boolean sucesso = dao.excluir(cliente.getId());
                                if(sucesso) {
                                    removerCliente(cliente);
                                    Snackbar.make(view, "Excluiu!", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }else{
                                    Snackbar.make(view, "Erro ao excluir o cliente!", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .create()
                        .show();
            }
        });
    }

    public void atualizarCliente(Cliente cliente){
        clientes.set(clientes.indexOf(cliente), cliente);
        notifyItemChanged(clientes.indexOf(cliente));
    }

    public void adicionarCliente(Cliente cliente){
        clientes.add(cliente);
        notifyItemInserted(getItemCount());
    }

    public void removerCliente(Cliente cliente){
        int position = clientes.indexOf(cliente);
        clientes.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return clientes != null ? clientes.size() : 0;
    }
}
