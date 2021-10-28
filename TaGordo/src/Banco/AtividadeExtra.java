

package Banco;

import java.util.ArrayList;
import javafx.util.Pair;

public class AtividadeExtra {
    private String nome;
    private String local;
    private String descricao;
    private ArrayList <Pair <String,String>> dia_hora;
    
    public AtividadeExtra(String nome, String local,  String descricao, ArrayList <Pair <String,String> > dia_hora) {
        this.nome=nome;
        this.local=local;
        this.descricao=descricao;
        this.dia_hora=dia_hora;
        salvar_banco(nome,local,descricao,dia_hora);
    }
    
    public AtividadeExtra(String nome, String local, ArrayList <Pair <String,String> > dia_hora) {
        this.nome=nome;
        this.local=local;
        this.descricao=null;
        this.dia_hora=dia_hora;
        salvar_banco(nome,local,dia_hora);
    }

   public void salvar_banco(String nome, String local, String descricao, ArrayList <Pair <String,String> > dia_hora) { 
//       System.out.println(nome);
//       System.out.println(local);
//       System.out.println(descricao);
//       System.out.println(dia_hora);   
   }
   
   public void salvar_banco(String nome, String local, ArrayList <Pair <String,String> > dia_hora) { 
//       System.out.println(nome);
//       System.out.println(local);
//       System.out.println(dia_hora);   
   }

    public String getNome() {
        return nome;
    }

    public String getLocal() {
        return local;
    }

    public String getDescricao() {
        return descricao;
    }

    public ArrayList<Pair<String, String>> getDia_hora() {
        return dia_hora;
    }    
}
