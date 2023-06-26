package com.example.appy_comau;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class SuporteSearch extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;

    Cliente clienteEditado = null;

    private int getIndex(Spinner spinner, String myString) {
        int index = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                index = i;
                break;
            }
        }
        return index;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_register);

        findViewById(R.id.includebusca).setVisibility(View.VISIBLE);

        //verifica se começou agora ou se veio de uma edição
        Intent intent = getIntent();
        if(intent.hasExtra("cliente")){
            findViewById(R.id.includemain).setVisibility(View.INVISIBLE);
            findViewById(R.id.includecadastro).setVisibility(View.VISIBLE);
            findViewById(R.id.fab2).setVisibility(View.INVISIBLE);
            clienteEditado = (Cliente) intent.getSerializableExtra("cliente");
            EditText txtNome = (EditText)findViewById(R.id.txtNome);
            Spinner spnEstado = (Spinner)findViewById(R.id.spnEstado);
            CheckBox chkVip = (CheckBox)findViewById(R.id.chkVip);

            txtNome.setText(clienteEditado.getNome());
            chkVip.setChecked(clienteEditado.getVip());
            spnEstado.setSelection(getIndex(spnEstado, clienteEditado.getUf()));
            if(clienteEditado.getSexo() != null){
                RadioButton rb;
                if(clienteEditado.getSexo().equals("M"))
                    rb = (RadioButton)findViewById(R.id.rbMasculino);
                else
                    rb = (RadioButton)findViewById(R.id.rbFeminino);
                rb.setChecked(true);
            }
        }

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.includemain).setVisibility(View.INVISIBLE);
                findViewById(R.id.includecadastro).setVisibility(View.VISIBLE);
                findViewById(R.id.fab2).setVisibility(View.INVISIBLE);
            }
        });

        Button btnCancelar = (Button)findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                findViewById(R.id.includemain).setVisibility(View.VISIBLE);
                findViewById(R.id.includecadastro).setVisibility(View.INVISIBLE);
            }
        });

        Button btnSalvar = (Button)findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onClick(View view) {
                //carregando os campos
                EditText txtNome = (EditText)findViewById(R.id.txtNome);
                Spinner spnEstado = (Spinner)findViewById(R.id.spnEstado);
                RadioGroup rgSexo = (RadioGroup)findViewById(R.id.rgSexo);
                CheckBox chkVip = (CheckBox)findViewById(R.id.chkVip);

                //pegando os valores
                String nome = txtNome.getText().toString();
                String uf = spnEstado.getSelectedItem().toString();
                boolean vip = chkVip.isChecked();
                String sexo = rgSexo.getCheckedRadioButtonId() == R.id.rbMasculino ? "M" : "F";

                //salvando os dados
                ClienteDAO dao = new ClienteDAO(getBaseContext());
                boolean sucesso;

                if(clienteEditado != null)
                    sucesso = dao.salvar(clienteEditado.getId(), nome, sexo, uf, vip);
                else
                    sucesso = dao.salvar(nome, sexo, uf, vip);

                if(sucesso) {

                    Cliente cliente = dao.retornarUltimo();

                    if(clienteEditado != null){
                        adapter.atualizarCliente(cliente);
                        clienteEditado = null;
                    }else

                        adapter.adicionarCliente(cliente);

                    //limpa os campos
                    txtNome.setText("");
                    rgSexo.setSelected(false);
                    spnEstado.setSelection(0);
                    chkVip.setChecked(false);

                    Snackbar.make(view, "Salvou!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    findViewById(R.id.includemain).setVisibility(View.VISIBLE);
                    findViewById(R.id.includecadastro).setVisibility(View.INVISIBLE);
                    findViewById(R.id.fab2).setVisibility(View.VISIBLE);
                }else{
                    Snackbar.make(view, "Erro ao salvar, consulte os logs!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        Button btnBuscar = (Button)findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(new Button.OnClickListener(){
        @Override
        public void onClick(View view) {

            EditText textnome = (EditText)findViewById(R.id.txtnomeCliente);
            String nometxt = textnome.getText().toString();

            SearchCliente nomecliente = new SearchCliente();
            nomecliente.setTxtnome(nometxt);
            configurarRecycler();
        }
    });

        Button btnvisualizar = (Button)findViewById(R.id.btnvisualizar);
        btnvisualizar.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {


                SearchCliente nomeid = new SearchCliente();
                int testeid = nomeid.getR_id();

                System.out.println(testeid);

                SearchCliente nomeclienteok = new SearchCliente();
                String nomeok = nomeclienteok.getR_nome();


                setContentView(R.layout.search_cadastro);
                TextView txt1 = (TextView)findViewById(R.id.idcliente);
                TextView txt2 = (TextView)findViewById(R.id.nomeclientecd);

                txt1.setText(String.valueOf(testeid));
                txt2.setText(nomeok);
            }
        });
    }
    RecyclerView recyclerView;
    SearchClienteAdapter adapter;

    private void configurarRecycler() {

         // Configurando o gerenciador de layout para ser uma lista
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.
        ClienteDAO dao = new ClienteDAO(this);
        adapter = new SearchClienteAdapter(dao.retornarDados());

        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        if (dao.retornarDados() != null){
            findViewById(R.id.includebusca).setVisibility(View.INVISIBLE);
            findViewById(R.id.includemain).setVisibility(View.VISIBLE);
            findViewById(R.id.txtnomeCliente).setVisibility(View.INVISIBLE);
            findViewById(R.id.btnBuscar).setVisibility(View.INVISIBLE);
            findViewById(R.id.msg1).setVisibility(View.INVISIBLE);
        }else {
            findViewById(R.id.txtnomeCliente).setVisibility(View.VISIBLE);
            findViewById(R.id.btnBuscar).setVisibility(View.VISIBLE);
            findViewById(R.id.msg1).setVisibility(View.VISIBLE);
        }
    }

    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}



