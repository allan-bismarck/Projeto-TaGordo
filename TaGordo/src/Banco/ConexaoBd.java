package Banco;

import Interface_gordo.DisciplinasController;
import Interface_gordo.EventoController;
import Interface_gordo.GradeController;
import Interface_gordo.Hora_estudoController;
import Interface_gordo.TaGordo;
import Interface_gordo.TrabalhoController;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.ObjectId;
import com.mongodb.util.*;
import java.io.IOException;
import static java.lang.System.exit;
import java.net.UnknownHostException;
import java.util.ArrayList;
import javafx.util.Pair;


public class ConexaoBd {
	private Mongo conexao;
	private DB db;
        
        public ConexaoBd() throws UnknownHostException{
            try{
                conexao = this.conexao = new Mongo("127.0.0.1");
            }catch(UnknownHostException | MongoException e){
//                System.out.println("Erro: "+e);
//                TaGordo.showAlert("", "Erro", "Erro ao conectar ao banco.");
            }
            
        }
        
        public BasicDBObject getUser () throws UnknownHostException{
            this.conexao = new Mongo("127.0.0.1");
            this.db = conexao.getDB("Lista_de_estudantes");
            BasicDBObject user = new BasicDBObject();
            
            try {
                
                DBCursor cursor = db.getCollection("Lista_de_estudantes").find();
                DBCollection estudantes = db.getCollection("Lista_de_estudantes");
                while(cursor.hasNext()) {
                    BasicDBObject pessoa = (BasicDBObject) cursor.next();
                    if(pessoa.getString("Login").equals(TaGordo.getLogin().get("Login"))) {
                        user = pessoa;
                    }                    
                }
                return user;
                
            } catch(Exception e){
                System.out.println("Erro: "+e);
                return null;
            }         
            
        }
        
        public BasicDBObject getDisc (String id) throws UnknownHostException{
            this.conexao = new Mongo("127.0.0.1");
            this.db = conexao.getDB("Lista_de_estudantes");
            BasicDBObject user = new BasicDBObject();
            BasicDBList disciplina = new BasicDBList();
            
            try {
                
                DBCursor cursor = db.getCollection("Lista_de_estudantes").find();
                DBCollection estudantes = db.getCollection("Lista_de_estudantes");
                while(cursor.hasNext()) {
                    BasicDBObject pessoa = (BasicDBObject) cursor.next();
                    if(pessoa.getString("Login").equals(TaGordo.getLogin().get("Login"))) {
                        BasicDBList disciplinas = (BasicDBList) pessoa.get("Disciplinas");
                        
                        disciplinas.forEach(d->{
                            BasicDBObject aux = (BasicDBObject) d;
                            if(aux.get("_id").equals(id)){
                                disciplina.add(new BasicDBObject().append("type","Disciplina").append("obj",aux));
                            }                                
                                
                        });
                        
                        if(disciplina.isEmpty()){
                            BasicDBList atividades = (BasicDBList) pessoa.get("Atividades Extras");
                            atividades.forEach(d->{
                                BasicDBObject aux = (BasicDBObject) d;
                                if(aux.get("_id").equals(id)){
                                    disciplina.add(new BasicDBObject().append("type","Atividade").append("obj",aux));
                                }


                            });
                        }
                            
                    }                    
                }
                return (BasicDBObject) disciplina.get(0);
                
                
            } catch(Exception e){
                System.out.println("Erro: "+e);
                return null;
            }         
            
        }
        
        public boolean Cadastrar_Estudante(Estudante E) throws UnknownHostException, MongoException {
		this.conexao = new Mongo("127.0.0.1");
		this.db = conexao.getDB("Lista_de_estudantes");
                
		try {
                        DBCollection estudantes = db.getCollection("Lista_de_estudantes");
                        BasicDBObject pessoa = new BasicDBObject();
                        BasicDBList disciplinas = new BasicDBList();
                        BasicDBList atividades = new BasicDBList();
			pessoa.put("Nome", E.getNome());
			pessoa.put("Login", E.getLogin());
			pessoa.put("Senha", E.getSenha());
                        pessoa.put("Disciplinas", disciplinas);
                        pessoa.put("Atividades Extras", atividades);
                        estudantes.insert(pessoa);
                        TaGordo.setLogin(pessoa);
                        return true;
            	} catch(Exception e){
			System.err.println(e.getClass().getName() + ", " + e.getMessage());	
                        return false;
		}
	}
        
        public void Cadastrar_Disciplina(Disciplina d) throws UnknownHostException, MongoException {
		this.conexao = new Mongo("127.0.0.1");
		this.db = conexao.getDB("Lista_de_estudantes");
		
		try {
                    DBCursor cursor = db.getCollection("Lista_de_estudantes").find();
                    DBCollection estudantes = db.getCollection("Lista_de_estudantes");
                    DBCollection lista_disc = db.getCollection("disciplinas");
                    while(cursor.hasNext()) {
                        BasicDBObject pessoa = (BasicDBObject) cursor.next();
                        BasicDBObject aux = new BasicDBObject();
                        if(pessoa.getString("Login").equals(TaGordo.getLogin().get("Login"))) {
                            
                            BasicDBObject disciplina = new BasicDBObject();
                            BasicDBList lista = new BasicDBList();
                            BasicDBList disciplinas = new BasicDBList();
                            BasicDBList horarios = new BasicDBList();
                            
                            d.getDia_hora().forEach(e->{
                                BasicDBObject horario = new BasicDBObject ();
                                horario.put("Dia", e.getKey());
                                horario.put("hora", e.getValue());
                                horarios.add(horario);
                            });
                            
                            disciplina.put("Nome", d.getNome());
                            disciplina.put("Sala", d.getSala());
                            disciplina.put("Professor", d.getProfessor());
                            disciplina.put("Horarios", horarios);
                            disciplina.put("_id", new ObjectId());
                                    
                            
                            disciplinas = (BasicDBList) pessoa.get("Disciplinas");
                            
                            
                            disciplinas.forEach((e)->{
                                lista.add(e);  
                            });
                            
                            lista.add(disciplina);

                            
                            aux.append("$set", new BasicDBObject().append("Disciplinas", lista));                            
                            estudantes.update(pessoa,aux);
                            TaGordo.getLogin().append("Disciplinas", lista);
//                            System.out.println(TaGordo.getLogin());
                        }
                    }
                        
		} catch(Exception e){
			System.err.println(e.getClass().getName() + ", " + e.getMessage());
			
		}
	}
        
        public void Cadastrar_Atividades(AtividadeExtra a) throws UnknownHostException, MongoException {
		this.conexao = new Mongo("127.0.0.1");
		this.db = conexao.getDB("Lista_de_estudantes");
		
		try {
                    DBCursor cursor = db.getCollection("Lista_de_estudantes").find();
                    DBCollection estudantes = db.getCollection("Lista_de_estudantes");
                    while(cursor.hasNext()) {
                        BasicDBObject pessoa = (BasicDBObject) cursor.next();
                        BasicDBObject aux = new BasicDBObject();
                        if(pessoa.getString("Login").equals(TaGordo.getLogin().get("Login"))) {
                            
                            BasicDBObject atividade = new BasicDBObject();
                            BasicDBList lista = new BasicDBList();
                            BasicDBList atividades = new BasicDBList();
                            BasicDBList horarios = new BasicDBList();
                            
                            a.getDia_hora().forEach(e->{
//                                System.out.println(e);
                                BasicDBObject horario = new BasicDBObject ();
                                horario.put("Dia", e.getKey());
                                horario.put("hora", e.getValue());
                                horarios.add(horario);
                            });
                            
                            atividade.put("Nome", a.getNome());
//                            System.out.println("aqui");
                            atividade.put("Local", a.getLocal());
                            atividade.put("Descricao", a.getDescricao());
                            atividade.put("Horarios", horarios);
                            atividade.put("_id", new ObjectId());
                            
                            atividades = (BasicDBList) pessoa.get("Atividades Extras");
                            
                            
                            atividades.forEach((e)->{
                                lista.add(e);  
                            });
                            
                            lista.add(atividade);
//                            System.out.println("Atividades" + lista);
                            
                            aux.append("$set", new BasicDBObject().append("Atividades Extras", lista));
//                            System.out.println("aux: "+aux);
//                            System.out.println("pessoa: "+pessoa);
//                            System.out.println("atividade: "+atividade);
                            
                            estudantes.update(pessoa,aux);
                            TaGordo.getLogin().append("Atividades Extras", lista);
//                            System.out.println(TaGordo.getLogin());
                        }
                    }
                        
		} catch(Exception e){
			System.err.println(e.getClass().getName() + ", " + e.getMessage());
			
		}
	}
        
       public void remover_disciplina(String id,String type) throws UnknownHostException {
           this.conexao = new Mongo("127.0.0.1");
           this.db = conexao.getDB("Lista_de_estudantes");
           
           BasicDBObject user = getUser();
           BasicDBList disciplinas = (BasicDBList) user.get("Disciplinas");
           BasicDBList atividades = (BasicDBList) user.get("Atividades Extras");
           BasicDBList aux = new BasicDBList();
           
           if(type.equals("Disciplina")){
               disciplinas.forEach((e)->{
               BasicDBObject disciplina = new BasicDBObject();
               disciplina = (BasicDBObject) e;
               String nome = disciplina.get("_id").toString();
                    if(!nome.equals(id)){
                        aux.add(disciplina);
                    }
               });
               user.append("Disciplinas", aux);
           }else{
               if(type.equals("Atividade")){
                    atividades.forEach((e)->{
                    BasicDBObject atividade = new BasicDBObject();
                    atividade = (BasicDBObject) e;
                    String nome = atividade.get("_id").toString();
                         if(!nome.equals(id)){
                             aux.add(atividade);
                         }
                    });
               }
               user.append("Atividades Extras", aux);
           }
               
           try {
		DBCursor cursor = db.getCollection("Lista_de_estudantes").find();
                DBCollection estudantes = db.getCollection("Lista_de_estudantes");
                while(cursor.hasNext()) {
                    
                    BasicDBObject pessoa = (BasicDBObject) cursor.next();
                    if(pessoa.getString("Login").equals(TaGordo.getLogin().getString("Login"))) {
                        estudantes.update(pessoa,user);
                        TaGordo.setLogin(user);
                    }
                }                
                
           } catch(Exception e){
			System.err.println(e.getClass().getName() + ", " + e.getMessage());
           }
           
       }
        
	public boolean validar_usuario_senha(String login, String senha) throws UnknownHostException, MongoException {
		this.conexao = new Mongo("127.0.0.1");
		this.db = conexao.getDB("Lista_de_estudantes");
//                System.out.println("Conexão: "+this.conexao);
//                System.out.println("DB: "+this.db);
		
		try {
			DBCursor cursor = db.getCollection("Lista_de_estudantes").find();
			while(cursor.hasNext()) {
                            BasicDBObject pessoa = (BasicDBObject) cursor.next();
                            if(pessoa.getString("Login").equals(login)&&pessoa.getString("Senha").equals(senha)) {
                                TaGordo.setLogin(pessoa);
                                return true;
                            }	
			}
                        
		} catch(Exception e){
			System.err.println("Erro: "+e.getClass().getName() + ", " + e.getMessage());
			return false;
		}
                return false;
	}
        
        public boolean possui_cadastro(String login) throws UnknownHostException, MongoException {
            try{
                this.db = conexao.getDB("Lista_de_estudantes"); 
            }catch(Exception e){
                TaGordo.showAlert("", "Erro", "Erro ao conectar ao banco.");
            }
		
                
		
		try {
			DBCursor cursor = db.getCollection("Lista_de_estudantes").find();
			while(cursor.hasNext()) {
				BasicDBObject pessoa = (BasicDBObject) cursor.next();
                                if(pessoa.getString("Login").equals(login)) {
                                    return true; 
                                }
			}
		} catch(Exception e){
			System.err.println("Erro: "+e.getClass().getName() + ", " + e.getMessage());
//                        TaGordo.showAlert("", "Erro", "Erro ao conectar ao banco.");
			return false;
		}
            return false;
	}
    
        public boolean validar_disc_e_ativ(ArrayList< javafx.util.Pair <String,String> > dia_hora, String nome) {
            BasicDBList aux = (BasicDBList) TaGordo.getLogin().get("Disciplinas");
            BasicDBList aux2 = (BasicDBList) TaGordo.getLogin().get("Atividades Extras");
            BasicDBObject y = new BasicDBObject("Valor","1");
            aux.forEach((Object e)->{
                BasicDBObject disciplina = new BasicDBObject();
                disciplina = (BasicDBObject )e;
                BasicDBList hora_dia_aux = (BasicDBList) disciplina.get("Horarios");
                
                hora_dia_aux.forEach((d)->{
                    BasicDBObject horario = new BasicDBObject();
                    horario = (BasicDBObject) d;
                    for (int x=0;x<dia_hora.size();x++) {
                        if(dia_hora.get(x).getKey().equals(horario.get("Dia"))&&dia_hora.get(x).getValue().equals(horario.get("hora"))) {
                            TaGordo.showAlert("Conflito de horário", "Erro", null);
                            y.append("Valor","0");
                            DisciplinasController.getDia_hora().clear();
                            EventoController.getDia_hora().clear();
                            Hora_estudoController.getDia_hora().clear();
                            TrabalhoController.getDia_hora().clear();
                            break;
                        }
                    }
                });
                        
            });
            
            aux2.forEach((Object a)->{
                BasicDBObject atividade = new BasicDBObject();
                atividade = (BasicDBObject )a;
                BasicDBList hora_dia_aux = (BasicDBList) atividade.get("Horarios");
                
                hora_dia_aux.forEach((b)->{
                    BasicDBObject horario = new BasicDBObject();
                    horario = (BasicDBObject) b;
                    for (int x=0;x<dia_hora.size();x++) {
                        if(dia_hora.get(x).getKey().equals(horario.get("Dia"))&&dia_hora.get(x).getValue().equals(horario.get("hora"))) {
                            TaGordo.showAlert("Choque de horário", "Erro", null);
                            y.append("Valor","0");
                            DisciplinasController.getDia_hora().clear();
                            EventoController.getDia_hora().clear();
                            Hora_estudoController.getDia_hora().clear();
                            TrabalhoController.getDia_hora().clear();
                            break;
                        }
                    }
                });
                        
            });
            
            if(y.getString("Valor").equals("1")) {
                return true;
            } else {
                return false;
            }
        }    

}
