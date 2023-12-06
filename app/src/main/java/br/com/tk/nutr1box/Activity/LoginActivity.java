package br.com.tk.nutr1box.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import br.com.tk.nutr1box.Config.ConfiguracaoFirebase;
import br.com.tk.nutr1box.Helper.UsuarioFirebase;
import br.com.tk.nutr1box.R;
import br.com.tk.nutr1box.Model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private Button btEntrar;
    private TextInputLayout etUsuario, etSenha;
    private TextView txtCadastro;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        iniciarComponentes();

        btEntrar.setOnClickListener(e -> {
            validarAutenticacaoUsuario();

        });

        txtCadastro.setOnClickListener(e -> {
            chamarCadastro();
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = autenticacao.getCurrentUser();
        if(currentUser != null){
            Toast.makeText(LoginActivity.this, "Bem vindo " + UsuarioFirebase.getUsuarioAtual().getDisplayName(), Toast.LENGTH_LONG).show();
            abrirTelaPrincipal();
        }
    }

    private void iniciarComponentes() {

        try {

            autenticacao = ConfiguracaoFirebase.getFirebaseAuth();
        }catch (Exception e){
            Log.e("AUTENTICACAO", e.getMessage());
            e.printStackTrace();
        }

        etUsuario = this.findViewById(R.id.etUsuario);
        etSenha = this.findViewById(R.id.etSenha);
        txtCadastro = this.findViewById(R.id.txtCadastro);
        btEntrar = this.findViewById(R.id.btEntrar);
    }

    // CHAMAR TELA CADASTRO
    public void chamarCadastro() {
        startActivity(new Intent(LoginActivity.this, CadastroActivity.class));
    }

    //Entrar
    public void logarUsuario(Usuario usuario) {

        autenticacao.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        Toast.makeText(LoginActivity.this, "Bem vindo " + autenticacao.getCurrentUser().getDisplayName(), Toast.LENGTH_LONG).show();

                        abrirTelaPrincipal();
                    } else {
                        String excessao = "";
                        try {
                            throw task.getException();
                        } catch (FirebaseAuthInvalidUserException e) {
                            excessao = getString(R.string.user_not_found);
                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            excessao = "E-mail e senha não correspondem a um usuário cadastrado!";
                        } catch (FirebaseNetworkException e) {
                            excessao = "Verifique sua conexão!";
                        } catch (Exception e) {
                            excessao = "Erro ao cadastrar usário: " + e.getMessage();
                            e.printStackTrace();
                        }
                        Toast.makeText(LoginActivity.this, excessao, Toast.LENGTH_LONG).show();
                    }

                });
    }

    public void validarAutenticacaoUsuario() {

        if (!validarCampos()) {
            Usuario usuario = new Usuario();
            usuario.setEmail(etUsuario.getEditText().getText().toString());
            usuario.setSenha(etSenha.getEditText().getText().toString());

            logarUsuario(usuario);
        }

    }

    public boolean validarCampos() {
        boolean erro = false;

        if (etUsuario.getEditText().getText().toString().equals("")) {
            etUsuario.setError("Digite um e-mail!");
            erro = true;
        }
        if (etSenha.getEditText().getText().toString().equals("")) {
            etSenha.setError("Digite uma senha!");
            erro = true;
        }
        return erro;
    }

    public void abrirTelaPrincipal() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

}
