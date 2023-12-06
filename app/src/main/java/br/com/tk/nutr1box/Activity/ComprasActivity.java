package br.com.tk.nutr1box.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.tk.nutr1box.Adapter.AdapterHistorico;
import br.com.tk.nutr1box.Config.ConfiguracaoFirebase;
import br.com.tk.nutr1box.Helper.UsuarioFirebase;
import br.com.tk.nutr1box.Model.Alimentos;
import br.com.tk.nutr1box.R;

public class ComprasActivity extends AppCompatActivity {
    private TextView txtInicio, txtRefeicoes, txtCompras, txtInfo;

    // TODO - Variavel SearchView
    private SearchView svPesquisa;

    private RecyclerView recyclerViewListaAlimentos;
    private AdapterHistorico adapter;
    private ArrayList<Alimentos> alimentos;
    private DatabaseReference reference;
    private DatabaseReference alimentosRef;
    private ValueEventListener valueEventListenerHistorico;

    private FirebaseAuth autenticacao;
    private String idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compras);

        iniciarComponentes();

        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();
        idUsuario = UsuarioFirebase.getIndentificadorUsuario();

        reference = ConfiguracaoFirebase.getFirebaseDatabase();

        //Configurações Iniciais
        alimentos = new ArrayList<>();
        alimentosRef = reference.child("Alimentos").child(idUsuario);
        alimentosRef.keepSynced(true);

        //Configurar Adapter
        adapter = new AdapterHistorico(alimentos, this);

        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewListaAlimentos.setLayoutManager(layoutManager);
        recyclerViewListaAlimentos.setHasFixedSize(true);
        recyclerViewListaAlimentos.setAdapter( adapter );

        txtRefeicoes.setOnClickListener(view -> {
            chamarRefeicoes();
        });
        txtInicio.setOnClickListener(view -> {
            chamarInicio();
        });
        txtInfo.setOnClickListener(view -> {
            chamarInfo();
        });

    }

    private void iniciarComponentes(){

        svPesquisa = this.findViewById(R.id.svPesquisa);

        recyclerViewListaAlimentos = this.findViewById(R.id.recyclerCompras);

        txtRefeicoes = this.findViewById(R.id.txtRefeicoes);

        txtInfo = this.findViewById(R.id.txtInfo);
        txtInicio = this.findViewById(R.id.txtInicio);
    }

    // CHAMAR TELA CADASTRO
    public void chamarRefeicoes() {
        startActivity(new Intent(ComprasActivity.this, RefeicoesActivity.class));
        finish();
    }
    public void chamarInicio() {
        startActivity(new Intent(ComprasActivity.this, MainActivity.class));
        finish();
    }
    public void chamarInfo() {
        startActivity(new Intent(ComprasActivity.this, InfoActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        recuperarHistorico();
    }

    @Override
    protected void onStop() {
        try{

            alimentosRef.removeEventListener(valueEventListenerHistorico);

        }catch (Exception e){
            Toast.makeText(this, getString(R.string.erro) + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        super.onStop();
    }

    private void recuperarHistorico(){

        alimentos.clear();

        valueEventListenerHistorico = alimentosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                alimentos.clear();

                for( DataSnapshot dados: dataSnapshot.getChildren() ){

                    System.out.println(dados.getValue());

                    Alimentos con = dados.getValue(Alimentos.class);
                    alimentos.add(con);

                }

                if(alimentos.isEmpty()){
                    Toast.makeText(ComprasActivity.this, "Você não tem mensagens!", Toast.LENGTH_SHORT).show();
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}