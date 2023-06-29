package com.example.appy_comau;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

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

public class TelaCadastro extends AppCompatActivity {

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
        setContentView(R.layout.tela_cadastro);

        //verifica se começou agora ou se veio de uma edição
        Intent intent = getIntent();
        if(intent.hasExtra("cliente")){
            findViewById(R.id.includemain).setVisibility(View.INVISIBLE);
            findViewById(R.id.includecadastro).setVisibility(View.VISIBLE);
            findViewById(R.id.fab).setVisibility(View.INVISIBLE);
            clienteEditado = (Cliente) intent.getSerializableExtra("cliente");
            EditText txtNome = (EditText)findViewById(R.id.txtNome);
            Spinner spnEstado = (Spinner)findViewById(R.id.spnEstado);
            CheckBox chkVip = (CheckBox)findViewById(R.id.chkVip);
            EditText txtModeloRobo = (EditText)findViewById(R.id.txtModeloRobo);
            EditText txtSerialRobo = (EditText)findViewById(R.id.txtSerialRobo);
            EditText txtModeloControlador = (EditText)findViewById(R.id.txtModeloControlador);
            EditText txtSerialControlador = (EditText)findViewById(R.id.txtSerialControlador);
            EditText txtAplicação = (EditText)findViewById(R.id.txtAplicação);

            txtNome.setText(clienteEditado.getNome());
            chkVip.setChecked(clienteEditado.getVip());
            spnEstado.setSelection(getIndex(spnEstado, clienteEditado.getUf()));
            txtModeloRobo.setText(clienteEditado.getModelorobo());
            txtSerialRobo.setText(clienteEditado.getSerialrobo());
            txtModeloControlador.setText(clienteEditado.getModelocontrolador());
            txtSerialControlador.setText(clienteEditado.getSerialcontrolador());
            txtAplicação.setText(clienteEditado.getAplicação());

            if(clienteEditado.getGarantia() != null){
                RadioButton rb;
                if(clienteEditado.getGarantia().equals("S"))
                    rb = (RadioButton)findViewById(R.id.rbSim);
                else
                    rb = (RadioButton)findViewById(R.id.rbNão);
                rb.setChecked(true);
            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.includemain).setVisibility(View.INVISIBLE);
                findViewById(R.id.includecadastro).setVisibility(View.VISIBLE);
                findViewById(R.id.fab).setVisibility(View.INVISIBLE);
            }
        });

        Button btnCancelar = (Button)findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                findViewById(R.id.includemain).setVisibility(View.VISIBLE);
                findViewById(R.id.includecadastro).setVisibility(View.INVISIBLE);
                findViewById(R.id.fab).setVisibility(View.VISIBLE);
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
                RadioGroup rgGarantia = (RadioGroup)findViewById(R.id.rgGarantia);
                CheckBox chkVip = (CheckBox)findViewById(R.id.chkVip);
                EditText txtModeloRobo = (EditText)findViewById(R.id.txtModeloRobo);
                EditText txtSerialRobo = (EditText)findViewById(R.id.txtSerialRobo);
                EditText txtModeloControlador = (EditText)findViewById(R.id.txtModeloControlador);
                EditText txtSerialControlador = (EditText)findViewById(R.id.txtSerialControlador);
                EditText txtAplicação = (EditText)findViewById(R.id.txtAplicação);

                //pegando os valores
                String nome = txtNome.getText().toString();
                String uf = spnEstado.getSelectedItem().toString();
                boolean vip = chkVip.isChecked();
                String garantia = rgGarantia.getCheckedRadioButtonId() == R.id.rbSim ? "S" : "N";
                String modelorobo = txtModeloRobo.getText().toString();
                String serialrobo = txtSerialRobo.getText().toString();
                String modelocontrolador= txtModeloControlador.getText().toString();
                String serialcontrolador = txtSerialControlador.getText().toString();
                String aplicação = txtAplicação.getText().toString();


                //salvando os dados
                ClienteDAO dao = new ClienteDAO(getBaseContext());
                boolean sucesso;

                if(clienteEditado != null)
                    sucesso = dao.salvar(clienteEditado.getId(),nome, garantia, uf, vip, modelorobo, serialrobo, modelocontrolador, serialcontrolador, aplicação);
                else
                    sucesso = dao.salvar(nome, garantia, uf, vip, modelorobo, serialrobo, modelocontrolador, serialcontrolador, aplicação);

                if(sucesso) {

                    Cliente cliente = dao.retornarUltimo();

                    if(clienteEditado != null){
                        adapter.atualizarCliente(cliente);
                        clienteEditado = null;
                    }else

                        adapter.adicionarCliente(cliente);

                    //limpa os campos
                    txtNome.setText("");
                    rgGarantia.setSelected(false);
                    spnEstado.setSelection(0);
                    chkVip.setChecked(false);

                    Snackbar.make(view, "Salvou!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    findViewById(R.id.includemain).setVisibility(View.VISIBLE);
                    findViewById(R.id.includecadastro).setVisibility(View.INVISIBLE);
                    findViewById(R.id.fab).setVisibility(View.VISIBLE);
                }else{
                    Snackbar.make(view, "Erro ao salvar, consulte os logs!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                configurarRecycler();
            }
        });

        configurarRecycler();
    }

    RecyclerView recyclerView;
    ClienteAdapter adapter;

    private void configurarRecycler() {
        // Configurando o gerenciador de layout para ser uma lista.
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.
        ClienteDAO dao = new ClienteDAO(this);
        adapter = new ClienteAdapter(dao.retornarTodos());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}