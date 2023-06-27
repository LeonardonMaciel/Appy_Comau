package com.example.appy_comau.ui.dashboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appy_comau.ClienteDAO;
import com.example.appy_comau.R;
import com.example.appy_comau.SearchCliente;
import com.example.appy_comau.SearchClienteAdapter;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class DashboardFragment extends Fragment implements View.OnClickListener {

    View FragmentoUsuario;
    TextView messageText,messageFormat ;

    RecyclerView recyclerView;

    SearchClienteAdapter adapter;

    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FragmentoUsuario = inflater.inflate(R.layout.fragment_dashboard,container, false);

        recyclerView = (RecyclerView) FragmentoUsuario.findViewById(R.id.recyclerView);
        Button btnScan = FragmentoUsuario.findViewById(R.id.btnScan);
        messageText = FragmentoUsuario.findViewById(R.id.textContent);
        messageFormat = FragmentoUsuario.findViewById(R.id.textFormat);
        btnScan.setOnClickListener(this);

        return FragmentoUsuario;
    }

    @Override
    public void onClick(View view) {
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(DashboardFragment.this);
        integrator.setPrompt("Scan QR code");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(requestCode == IntentIntegrator.REQUEST_CODE) {
            if (result != null) {
                if (result.getContents() == null) {
                    Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    messageText.setText(result.getContents());
                    messageFormat.setText(result.getFormatName());
                    String myname = messageText.getText().toString();
                    SearchCliente nomecliente = new SearchCliente();
                    nomecliente.setTxtnome(myname);
                    configurarRecycler();
                }
            }
        }
    }

    public void configurarRecycler() {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        ClienteDAO dao = new ClienteDAO(getActivity());
        adapter = new SearchClienteAdapter(dao.retornarDados());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        if (dao.retornarDados() != null){
            FragmentoUsuario.findViewById(R.id.msg).setVisibility(View.INVISIBLE);
        }else {
            FragmentoUsuario.findViewById(R.id.msg).setVisibility(View.VISIBLE);
        }
    }
}


