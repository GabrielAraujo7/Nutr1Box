package br.com.tk.nutr1box.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.tk.nutr1box.Config.ConfiguracaoFirebase;
import br.com.tk.nutr1box.Helper.UsuarioFirebase;
import br.com.tk.nutr1box.Model.Usuario;
import br.com.tk.nutr1box.R;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private DatabaseReference reference;
    private String idUsuario;
    private Usuario usuario;

    private TextView txtInicio, txtRefeicoes, txtCompras, txtInfo;
    private boolean started = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciarComponentes();

        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();
        idUsuario = UsuarioFirebase.getIndentificadorUsuario();

        reference = ConfiguracaoFirebase.getFirebaseDatabase().child("Users").child(idUsuario);

        txtRefeicoes.setOnClickListener(view -> {
            chamarRefeicoes();
        });
        txtCompras.setOnClickListener(view -> {
            chamarCompras();
        });
        txtInfo.setOnClickListener(view -> {
            chamarInfo();
        });

    }

    private void iniciarComponentes(){
        txtRefeicoes = this.findViewById(R.id.txtRefeicoes);
        txtCompras = this.findViewById(R.id.txtCompras);
        txtInfo = this.findViewById(R.id.txtInfo);
    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperarDadosUsuario();
    }

    // CHAMAR TELA CADASTRO
    public void chamarRefeicoes() {
        startActivity(new Intent(MainActivity.this, RefeicoesActivity.class));
        finish();
    }
    public void chamarCompras() {
        startActivity(new Intent(MainActivity.this, ComprasActivity.class));
        finish();
    }
    public void chamarInfo() {
        startActivity(new Intent(MainActivity.this, InfoActivity.class));
        finish();
    }

    private void recuperarDadosUsuario(){

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                usuario = dataSnapshot.getValue(Usuario.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }

}