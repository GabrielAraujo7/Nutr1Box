package br.com.tk.nutr1box.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.tk.nutr1box.Model.Alimentos;
import br.com.tk.nutr1box.R;

public class AdapterHistorico extends RecyclerView.Adapter<AdapterHistorico.MyViewHolder>{

    private List<Alimentos> alimentos;
    private Context context;

    public AdapterHistorico(List<Alimentos> alimentos, Context c ) {
        this.alimentos = alimentos;
        this.context = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemLista = null;

        try{
            itemLista = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_historico, viewGroup, false);
        }catch (Exception e){
            Toast.makeText(context, R.string.erro + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        try{
            Alimentos hist = alimentos.get(i);

            myViewHolder.nome.setText(hist.getNome());

            if(hist.getMarca() != null){

                myViewHolder.marca.setText(context.getString(R.string.marca) + hist.getMarca());

            }
            if(hist.getCalorias() != null){
                myViewHolder.calorias.setText(context.getString(R.string.calorias) + hist.getCalorias());
            }
            if(hist.getAlergenicos() != null){
                myViewHolder.alergenicos.setText(context.getString(R.string.alergenico) + hist.getAlergenicos());
            }
            if(hist.getComposicao() != null){
                myViewHolder.composicao.setText(context.getString(R.string.composicao) + hist.getComposicao());
            }

        }catch (Exception e){
            Toast.makeText(context, R.string.erro + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return alimentos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nome, marca, calorias, alergenicos, composicao;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.txtNomeAlimento);
            marca = itemView.findViewById(R.id.txtMarcaAlimento);
            calorias = itemView.findViewById(R.id.txtCalorias);
            alergenicos = itemView.findViewById(R.id.txtAlergenicos);
            composicao = itemView.findViewById(R.id.txtComposicao);

        }
    }

}