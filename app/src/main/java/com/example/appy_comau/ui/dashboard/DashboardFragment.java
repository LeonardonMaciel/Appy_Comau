package com.example.appy_comau.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.appy_comau.R;
import com.example.appy_comau.databinding.FragmentDashboardBinding;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class DashboardFragment extends Fragment implements View.OnClickListener {

    Button btnScan;
    TextView messageText,messageFormat ;

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View FragmentoUsuario = inflater.inflate(R.layout.fragment_dashboard,container, false);

        Button btnScan = FragmentoUsuario.findViewById(R.id.btnScan);
        messageText = FragmentoUsuario.findViewById(R.id.textContent);
        messageFormat = FragmentoUsuario.findViewById(R.id.textFormat);
        btnScan.setOnClickListener(this);

        return FragmentoUsuario;
    }

    @Override
    public void onClick(View view) {
        //Request camera
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(DashboardFragment.this);
        //integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
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
                    //Log.d("ScanFrag", "Cancelled scan");
                    Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    //Log.d("ScanFrag", "Scanned | " + result.getContents());
                    //Toast.makeText(getContext(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                    messageText.setText(result.getContents());
                    messageFormat.setText(result.getFormatName());

                }
            }
        }
    }
}




