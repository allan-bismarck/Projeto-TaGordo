/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco;

import java.util.ArrayList;
import javafx.util.Pair;

/**
 *
 * @author AAB
 */
public class Estudante {
    private String nome;
    private String login;
    private String senha;
    private ArrayList<Disciplina> disciplinas;
    private ArrayList<AtividadeExtra> atividades_extras;
    
    public Estudante(String nome,String login,String senha) {
        this.nome=nome;
        this.login=login;
        this.senha=senha;
        salvar_banco(nome,login,senha);
    }
      
    
   public void salvar_banco (String nome,String login,String senha) { 
       System.out.println(nome);
       System.out.println(login);
       System.out.println(senha); 
   }

    public String getNome() {
        return nome;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public ArrayList<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public ArrayList<AtividadeExtra> getAtividades_extras() {
        return atividades_extras;
    }
  
    
   
    
}
