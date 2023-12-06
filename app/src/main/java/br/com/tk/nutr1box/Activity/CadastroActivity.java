package br.com.tk.nutr1box.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import br.com.tk.nutr1box.Config.ConfiguracaoFirebase;
import br.com.tk.nutr1box.Helper.UsuarioFirebase;
import br.com.tk.nutr1box.R;
import br.com.tk.nutr1box.Model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
    private StorageReference storageReference;

    private Button btCadastrar;
    private TextInputLayout etNome, etEmail, etSenha1, etSenha2;
    private RadioButton rbM, rbF, rbPerderPeso, rbGanharPeso, rbManter;
    private EditText etDataNascimento, etPeso, etAltura;

    private Usuario usuario = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //Configurações Iniciais
        storageReference = ConfiguracaoFirebase.getFirebaseStorage();
        iniciarComponentes();

        btCadastrar.setOnClickListener(e -> {
            validarCadastroUsuario();
        });

    }

    private void iniciarComponentes(){
        btCadastrar = findViewById(R.id.btCadastrar);
        etNome = findViewById(R.id.etNome);
        etEmail = findViewById(R.id.etEmail);
        etSenha1 = findViewById(R.id.etSenha1);
        etSenha2 = findViewById(R.id.etSenha2);
        rbM = findViewById(R.id.radioButtonCadMasculino);
        rbF = findViewById(R.id.radioButtonCadFeminino);
        rbPerderPeso = findViewById(R.id.radioPerderPeso);
        rbGanharPeso = findViewById(R.id.radioGanharPeso);
        rbManter = findViewById(R.id.radioManterPeso);
        etDataNascimento = findViewById(R.id.txtDataNascimento);
        etPeso = findViewById(R.id.txtPeso);
        etAltura = findViewById(R.id.txtAltura);

    }

    public void validarCadastroUsuario(){

        boolean erro = false;

        usuario = new Usuario();

        if(etNome.getEditText().getText().toString().equals("")){
            this.etNome.setError("Digite o Nome!");
            erro = true;
        }else{
            usuario.setNome(etNome.getEditText().getText().toString());
        }
        if(etEmail.getEditText().getText().toString().equals("")){
            this.etEmail.setError("Digite o Email!");
            erro = true;
        }else{
            usuario.setEmail(etEmail.getEditText().getText().toString());
        }

        if(!etAltura.getText().toString().equals("")){
            if(Float.parseFloat(etAltura.getText().toString()) >= 50){
                etAltura.setText(String.valueOf(Float.parseFloat(etAltura.getText().toString()) * 0.01));
            }
            else if(Float.parseFloat(etAltura.getText().toString()) > 4 || Float.parseFloat(etAltura.getText().toString()) < 0){
                etAltura.setError("Altura inválida!");
                erro = true;
            }
        }
        if(!etPeso.getText().toString().equals("")){
            if(Float.parseFloat(etPeso.getText().toString()) > 600 || Float.parseFloat(etPeso.getText().toString()) < 0){
                etPeso.setError("Peso inválido!");
                erro = true;
            }
        }

        if(rbM.isChecked()){
            usuario.setSexo("M");
        }else if(rbF.isChecked()){
            usuario.setSexo("F");
        }else{
            erro = true;
        }

        if(rbPerderPeso.isChecked()){
            usuario.setMeta("Perder Peso");
        }else if(rbGanharPeso.isChecked()){
            usuario.setMeta("Ganhar Peso");
        }else if(rbManter.isChecked()){
            usuario.setMeta("Manter Peso");
        }else{
            erro = true;
        }

        if(etSenha1.getEditText().getText().toString().isEmpty()){
            erro = true;
            etSenha1.setError("Insira uma Senha!");
        }else if(!etSenha2.getEditText().getText().toString().equals(etSenha1.getEditText().getText().toString())){
            erro = true;
            etSenha2.setError("As Senhas não conferem!");
        }

        if(!erro){
            if(!etPeso.getText().toString().equals("")){
                usuario.setPeso(Double.parseDouble(etPeso.getText().toString()));
            }

            String altura = etAltura.getText().toString().replace(",", ".");

            usuario.setAltura(Double.parseDouble(altura));
            usuario.setSenha(etSenha1.getEditText().getText().toString());

            try {
                cadastrarUsuario();
            }catch (Exception e){
                Toast.makeText(CadastroActivity.this, "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }

        }
    }

    public void cadastrarUsuario(){

        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();
        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(this, task -> {

                    if(task.isSuccessful()){

                        Toast.makeText(getApplicationContext(), "Bem vindo " + usuario.getNome() + "!", Toast.LENGTH_LONG).show();
                        UsuarioFirebase.atualizarNomeUsuario(usuario.getNome());

                        try{

                            usuario.setId(UsuarioFirebase.getIndentificadorUsuario());

                            databaseReference.child(usuario.getId()).child("Email").setValue(usuario.getEmail());
                            databaseReference.child(usuario.getId()).child("Nome").setValue(usuario.getNome());
                            databaseReference.child(usuario.getId()).child("Meta").setValue(usuario.getMeta());

                            if(!usuario.getSexo().equals("")){
                                databaseReference.child(usuario.getId()).child("Sexo").setValue(usuario.getSexo());
                            }
                            if(!etAltura.getText().toString().equals("")){
                                databaseReference.child(usuario.getId()).child("Altura").setValue(usuario.getAltura());
                            }
                            if(!etPeso.getText().toString().equals("")){
                                databaseReference.child(usuario.getId()).child("Peso").setValue(usuario.getPeso());
                            }

                            chamarMain();

                        }catch (Exception e){
                            Toast.makeText(CadastroActivity.this, "Erro ao salvar informações: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }else {
                        String excessao = "";
                        try{
                            throw task.getException();
                        }catch (FirebaseAuthWeakPasswordException e){
                            excessao = "Digite uma senha mais forte!";
                            etSenha1.setError(excessao);
                        }catch (FirebaseAuthInvalidCredentialsException e){
                            excessao = "Por favor, digite um e-mail válido!";
                            etEmail.setError(excessao);
                        }catch (FirebaseAuthUserCollisionException e){
                            excessao = "Esta conta já foi cadastrada!";
                            etEmail.setError(excessao);
                        }catch (FirebaseNetworkException e){
                            excessao = "Verifique sua conexão!";
                        }catch (Exception e){
                            excessao = "Erro ao cadastrar usuário: " + e.getMessage();
                            e.printStackTrace();
                        }

                        Toast.makeText(CadastroActivity.this, excessao, Toast.LENGTH_LONG).show();
                    }

                });

    }

    private void chamarMain(){
        Intent intent = new Intent(CadastroActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}