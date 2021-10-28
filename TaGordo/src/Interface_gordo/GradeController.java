package Interface_gordo;

import Banco.ConexaoBd;
import java.net.URL;
import java.util.ResourceBundle; 
import javafx.fxml.Initializable; 
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.ObjectId;
import java.net.UnknownHostException;
import java.util.HashMap; 
import java.util.logging.Level;
import java.util.logging.Logger; 
import javafx.collections.ObservableList; 
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane; 
import javafx.stage.Stage;

public class GradeController implements Initializable {
    HashMap  < String, HashMap <String, Integer> > tabela = new HashMap  <String, HashMap <String, Integer> >(); 
    HashMap<Integer, Integer> colortable = new HashMap<Integer, Integer>();
    
    @FXML
    private GridPane grade;
    
    private AnchorPane anchorPane;
    
    @FXML
    private AnchorPane detalhes;
      
    private ObservableList <Node> ListaAnchor;
    @FXML
    private Button remove;
    
    @FXML
    private Label primeiro_campo;
    @FXML
    private Label segundo_campo;
    @FXML
    private Label terceiro_campo;
    
    @FXML
    private Label primeiro_label;
    @FXML
    private Label segundo_label;
    @FXML
    private Label terceiro_label;
    
    @FXML
    private Label msg;
    @FXML
    private Button b2;
    @FXML
    private Button b1;
     
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
        setInicio(tabela, colortable, detalhes);    
        String mensagem;  
        mensagem = "Olá, "+TaGordo.getLogin().get("Nome");
        msg.setText(mensagem);
        try {
            this.teste();
        } catch (UnknownHostException ex) {
            Logger.getLogger(GradeController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }      
    
    @FXML
    private void disciplinas() {
       TaGordo.loadScene("disciplinas.fxml", "Adicionar Disciplina");;
    }
    
    @FXML
    private void ir_evento() {
       TaGordo.loadScene("evento.fxml", "Adicionar Evento");
    }
    
    @FXML
    private void ir_trabalho() {
       TaGordo.loadScene("trabalho.fxml", "Adicionar Trabalho");
    }
    
    @FXML
    private void ir_horario_de_estudo() {
       TaGordo.loadScene("hora_estudo.fxml", "Grade");
    }
    
    @FXML
    private void ir_telainicial() {
       TaGordo.setLogin(null);
       TaGordo.loadScene("Tela1.fxml", "Tela Inicial");
    }
    
    private void remover_disc(String dia, String hora, String id,String type) throws UnknownHostException { 
        
            TaGordo.getC().remover_disciplina(id,type);   
            ListaAnchor = grade.getChildren();
            anchorPane =(AnchorPane) ListaAnchor.get(tabela.get(dia).get(hora));
            anchorPane.getChildren().clear();
            detalhes.setOpacity(0); 
            TaGordo.loadScene("grade.fxml", "Grade");
    }
    
    public void selectDisc(String nome,String dia,String hora,String id){ 
        BasicDBObject evento;
        
        try {
            evento = TaGordo.getC().getDisc(id);
            String type = (String) evento.get("type");
            BasicDBObject obj = (BasicDBObject) evento.get("obj");
//            System.out.println("teste: " + evento);
            if(type.equals("Disciplina")){
                primeiro_label.setText("Nome");
                segundo_label.setText("Sala");
                terceiro_label.setText("Professor");
                primeiro_campo.setText((String) obj.get("Nome"));
                segundo_campo.setText((String) obj.get("Sala"));
                terceiro_campo.setText((String) obj.get("Professor"));
                remove.setOnAction(e->{
                    try {
                        remover_disc(dia,hora,id,"Disciplina");
                    } catch (UnknownHostException ex) {
                        Logger.getLogger(GradeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }else{
                if(type.equals("Atividade")){
                    primeiro_label.setText("Nome");
                    segundo_label.setText("Local");
                    terceiro_label.setText("Descrição");
                    primeiro_campo.setText((String) obj.get("Nome"));
                    segundo_campo.setText((String) obj.get("Local"));
                    terceiro_campo.setText((String) obj.get("Descricao"));
                    remove.setOnAction(e->{
                        try {
                            remover_disc(dia,hora,id,"Atividade");
                        } catch (UnknownHostException ex) {
                            Logger.getLogger(GradeController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                }   
            }
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(GradeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        detalhes.setOpacity(1);      
        b1.setStyle("-fx-background-color:  #E16740");
        b2.setStyle("-fx-background-color:  #E16740");
    } 
    
    private void select(String dia,String hora, String nome, String id){
        
        ListaAnchor = grade.getChildren(); 
        TextField newTF = new TextField(nome); 
        newTF.setOnMouseClicked(e->{
            selectDisc(nome,dia,hora,id);
        });
        newTF.setCursor(Cursor.HAND);
        
        int cor_textfield = tabela.get(dia).get(hora);
        anchorPane =(AnchorPane) ListaAnchor.get(cor_textfield);  
        if (colortable.containsKey(cor_textfield)){ 
            newTF.setEditable(false);
            newTF.setPrefSize(92, 50);
            newTF.setStyle("-fx-background-color: #FFF7F2;-fx-alignment:center;-fx-text-inner-color: black;");
            anchorPane.getChildren().add(newTF);
        }else{ 
            newTF.setEditable(false);
            newTF.setPrefSize(92, 50);
            newTF.setStyle("-fx-background-color: #ffdbbe;-fx-alignment:center;-fx-text-inner-color: black;");
            anchorPane.getChildren().add(newTF);
        } 
    }
    
    @FXML
    private void Close() {
        Stage stage = (Stage) primeiro_label.getScene().getWindow(); 
        stage.close();
        System.exit(0);
    }
    
    @FXML
    private void Minimizar (ActionEvent event){
        Stage stage = (Stage) primeiro_label.getScene().getWindow();
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    
    @FXML
    private void fechar_painel_lateral(){ 
          detalhes.setOpacity(0);
          b1.setStyle("-fx-background-color: white");
          b2.setStyle("-fx-background-color: white");
    }
    
    private void teste() throws UnknownHostException{
         
        BasicDBList disciplinas = new BasicDBList();
        BasicDBList atividades = new BasicDBList();
        disciplinas = (BasicDBList) TaGordo.getC().getUser().get("Disciplinas");
        atividades = (BasicDBList) TaGordo.getC().getUser().get("Atividades Extras");
        
        disciplinas.forEach(e->{
            BasicDBObject aux = new BasicDBObject();
            aux = (BasicDBObject) e;
            String nome = (String) aux.get("Nome");
            String id = aux.get("_id").toString();
            
            BasicDBList listaAux = (BasicDBList) aux.get("Horarios");            
            listaAux.forEach(h->{
                BasicDBObject horario = new BasicDBObject();
                horario = (BasicDBObject) h;
                String dia,hora;
                dia = (String) horario.get("Dia");
                hora = (String) horario.get("hora");
                this.select(dia, hora,nome,id);
            });
        });
        
        
        atividades.forEach(e->{
            BasicDBObject aux = new BasicDBObject();
            aux = (BasicDBObject) e;
            String nome = (String) aux.get("Nome");
            String id = aux.get("_id").toString();
            
            
            BasicDBList listaAux = (BasicDBList) aux.get("Horarios");            
            listaAux.forEach(h->{
                BasicDBObject horario = new BasicDBObject();
                horario = (BasicDBObject) h;
                String dia,hora;
                dia = (String) horario.get("Dia");
                hora = (String) horario.get("hora");
                this.select(dia, hora,nome,id);
            });
        });
    } 
     
    private void setInicio(HashMap  < String, HashMap <String, Integer> > tabela, HashMap<Integer, Integer> colortable, AnchorPane detalhes){
        
        colortable.put(9, 1);
        colortable.put(10, 1);
        colortable.put(11, 1);
        colortable.put(12, 1);
        colortable.put(13, 1);
        colortable.put(14, 1);
        colortable.put(15, 1);
        colortable.put(25, 1);
        colortable.put(26, 1);
        colortable.put(27, 1);
        colortable.put(28, 1);
        colortable.put(29, 1);
        colortable.put(30, 1);
        colortable.put(31, 1); 
        colortable.put(41, 1); 
        colortable.put(42, 1); 
        colortable.put(43, 1); 
        colortable.put(44, 1); 
        colortable.put(45, 1); 
        colortable.put(46, 1); 
        colortable.put(47, 1); 
        colortable.put(57, 1); 
        colortable.put(58, 1); 
        colortable.put(59, 1); 
        colortable.put(60, 1); 
        colortable.put(61, 1); 
        colortable.put(62, 1); 
        colortable.put(63, 1); 
        
        detalhes.setOpacity(0.0);
        
        HashMap <String, Integer> aux = new HashMap <String, Integer>();
        aux.put("08:00 às 10:00", 9); 
        aux.put("10:00 às 12:00", 17); 
        aux.put("12:00 às 14:00", 25); 
        aux.put("14:00 às 16:00", 33); 
        aux.put("16:00 às 18:00", 41); 
        aux.put("18:00 às 20:00", 49); 
        aux.put("20:00 às 22:00", 57); 
        aux.put("22:00 às 00:00", 65); 
        tabela.put("Domingo", aux); 
        
        HashMap <String, Integer> aux1 = new HashMap <String, Integer>();
        aux1.put("08:00 às 10:00", 10); 
        aux1.put("10:00 às 12:00", 18); 
        aux1.put("12:00 às 14:00", 26); 
        aux1.put("14:00 às 16:00", 34); 
        aux1.put("16:00 às 18:00", 42); 
        aux1.put("18:00 às 20:00", 50); 
        aux1.put("20:00 às 22:00", 58); 
        aux1.put("22:00 às 00:00", 66); 
        tabela.put("Segunda", aux1);  
        
        HashMap <String, Integer> aux2 = new HashMap <String, Integer>();
        aux2.put("08:00 às 10:00", 11); 
        aux2.put("10:00 às 12:00", 19); 
        aux2.put("12:00 às 14:00", 27); 
        aux2.put("14:00 às 16:00", 35); 
        aux2.put("16:00 às 18:00", 43); 
        aux2.put("18:00 às 20:00", 51); 
        aux2.put("20:00 às 22:00", 59); 
        aux2.put("22:00 às 00:00", 67); 
        tabela.put("Terça", aux2);  
        
        HashMap <String, Integer> aux3 = new HashMap <String, Integer>();
        aux3.put("08:00 às 10:00", 12); 
        aux3.put("10:00 às 12:00", 20); 
        aux3.put("12:00 às 14:00", 28); 
        aux3.put("14:00 às 16:00", 36); 
        aux3.put("16:00 às 18:00", 44); 
        aux3.put("18:00 às 20:00", 52); 
        aux3.put("20:00 às 22:00", 60); 
        aux3.put("22:00 às 00:00", 68); 
        tabela.put("Quarta", aux3); 
          
        HashMap <String, Integer> aux4 = new HashMap <String, Integer>();
        aux4.put("08:00 às 10:00", 13); 
        aux4.put("10:00 às 12:00", 21); 
        aux4.put("12:00 às 14:00", 29); 
        aux4.put("14:00 às 16:00", 37); 
        aux4.put("16:00 às 18:00", 45); 
        aux4.put("18:00 às 20:00", 53); 
        aux4.put("20:00 às 22:00", 61); 
        aux4.put("22:00 às 00:00", 69); 
        tabela.put("Quinta", aux4);  
        
        HashMap <String, Integer> aux5 = new HashMap <String, Integer>();
        aux5.put("08:00 às 10:00", 14); 
        aux5.put("10:00 às 12:00", 22); 
        aux5.put("12:00 às 14:00", 30); 
        aux5.put("14:00 às 16:00", 38); 
        aux5.put("16:00 às 18:00", 46); 
        aux5.put("18:00 às 20:00", 54); 
        aux5.put("20:00 às 22:00", 62); 
        aux5.put("22:00 às 00:00", 70);  
        tabela.put("Sexta", aux5);  
        
        HashMap <String, Integer> aux6 = new HashMap <String, Integer>();
        aux6.put("08:00 às 10:00", 15); 
        aux6.put("10:00 às 12:00", 23); 
        aux6.put("12:00 às 14:00", 31); 
        aux6.put("14:00 às 16:00", 39); 
        aux6.put("16:00 às 18:00", 47); 
        aux6.put("18:00 às 20:00", 55); 
        aux6.put("20:00 às 22:00", 63); 
        aux6.put("22:00 às 00:00", 71); 
        tabela.put("Sábado", aux6);    
        
    }
}
