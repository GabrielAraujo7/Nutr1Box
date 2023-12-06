package br.com.tk.nutr1box.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import br.com.tk.nutr1box.R;

public class RefeicoesActivity extends AppCompatActivity {

    private TextView txtInicio, txtRefeicoes, txtCompras, txtInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refeicoes);

        iniciarComponentes();

        txtInicio.setOnClickListener(view -> {
            chamarInicio();
        });
        txtCompras.setOnClickListener(view -> {
            chamarCompras();
        });
        txtInfo.setOnClickListener(view -> {
            chamarInfo();
        });

    }

    private void iniciarComponentes(){

        txtCompras = this.findViewById(R.id.txtCompras);
        txtInfo = this.findViewById(R.id.txtInfo);
        txtInicio = this.findViewById(R.id.txtInicio);
    }

    // CHAMAR TELA CADASTRO
    public void chamarInicio() {
        startActivity(new Intent(RefeicoesActivity.this, MainActivity.class));
        finish();
    }
    public void chamarCompras() {
        startActivity(new Intent(RefeicoesActivity.this, ComprasActivity.class));
        finish();
    }
    public void chamarInfo() {
        startActivity(new Intent(RefeicoesActivity.this, InfoActivity.class));
        finish();
    }

}