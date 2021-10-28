
package Banco;

import java.util.ArrayList;
import javafx.util.Pair;


public class Disciplina {
    private String nome;
    private String sala;
    private String professor;
    private ArrayList <Pair <String,String>> dia_hora;

    public Disciplina(String nome, String sala,  String professor, ArrayList <Pair <String,String>> dia_hora) {
        this.nome=nome;
        this.sala=sala;
        this.professor=professor;
        this.dia_hora=dia_hora;
//      salvar_banco(nome,sala,professor,dia_hora);
    }

   public void salvar_banco(String nome, String sala, String professor, ArrayList <Pair <String,String>> dia_hora) { 
         
   }

    public String getNome() {
        return nome;
    }

    public String getSala() {
        return sala;
    }

    public String getProfessor() {
        return professor;
    }

    public ArrayList <Pair <String,String>> getDia_hora() {
        return dia_hora;
    }
   
   
       
}


   