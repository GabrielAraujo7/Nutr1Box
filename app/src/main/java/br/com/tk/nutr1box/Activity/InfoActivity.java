package br.com.tk.nutr1box.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.tk.nutr1box.Config.ConfiguracaoFirebase;
import br.com.tk.nutr1box.Helper.UsuarioFirebase;
import br.com.tk.nutr1box.Model.Usuario;
import br.com.tk.nutr1box.R;

public class InfoActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private DatabaseReference reference;
    private String idUsuario;
    private Usuario usuario;
    private TextView txtInicio, txtRefeicoes, txtCompras, txtInfo;
    private TextView txtName, txtMeta;

    private Button btnSair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        iniciarComponentes();

        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();
        idUsuario = UsuarioFirebase.getIndentificadorUsuario();

        reference = ConfiguracaoFirebase.getFirebaseDatabase().child("Users").child(idUsuario);

        txtInicio.setOnClickListener(view -> {
            chamarInicio();
        });

        txtCompras.setOnClickListener(view -> {
            chamarCompras();
        });

        txtRefeicoes.setOnClickListener(view -> {
            chamarRefeicoes();
        });

        btnSair.setOnClickListener(view -> {
            deslogarUsuario();
        });

    }

    private void iniciarComponentes(){

        txtRefeicoes = this.findViewById(R.id.txtRefeicoes);
        txtCompras = this.findViewById(R.id.txtCompras);
        txtInicio = this.findViewById(R.id.txtInicio);
        txtName = this.findViewById(R.id.txtName);
        txtMeta = this.findViewById(R.id.txtMeta);
        btnSair = this.findViewById(R.id.btnSair);

    }
    // CHAMAR TELA CADASTRO
    public void chamarInicio() {
        startActivity(new Intent(InfoActivity.this, MainActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperarDadosUsuario();
    }

    public void chamarCompras() {
        startActivity(new Intent(InfoActivity.this, ComprasActivity.class));
        finish();
    }
    public void chamarRefeicoes() {
        startActivity(new Intent(InfoActivity.this, RefeicoesActivity.class));
        finish();
    }

    private void recuperarDadosUsuario(){

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                usuario = dataSnapshot.getValue(Usuario.class);

                if(usuario != null){
                    txtName.setText(usuario.getNome());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }

    private void deslogarUsuario(){
        FirebaseUser usuario = UsuarioFirebase.getUsuarioAtual();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Deslogar");
        builder.setMessage("Deseja sair da conta " + usuario.getDisplayName() + "?");
        builder.setCancelable(true);
        builder.setPositiveButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNegativeButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try{
                    autenticacao.signOut();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),R.string.erro + e.getMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}

