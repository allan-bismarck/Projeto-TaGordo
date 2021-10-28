
package Interface_gordo;

import static Interface_gordo.TaGordo.xOffset;
import java.net.URL; 
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene; 
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;

public class Adicionar_horariosController implements Initializable {

    @FXML
    private RadioButton oito;
    @FXML
    private RadioButton dez;
    @FXML
    private RadioButton dezesseis;
    @FXML
    private RadioButton doze;
    @FXML
    private RadioButton quatorze;
    @FXML
    private RadioButton vinte;
    @FXML
    private RadioButton dezoito;
    @FXML
    private RadioButton vinteedois;
    @FXML
    private RadioButton dom;
    @FXML
    private RadioButton seg;
    @FXML
    private RadioButton qui;
    @FXML
    private RadioButton ter;
    @FXML
    private RadioButton qua;
    @FXML
    private RadioButton sab;
    @FXML
    private RadioButton sex;
    @FXML
    private Label error;
    static String type;
    static double xOffset = 0;
    static double yOffset = 0;
    
    public void adicionar_hora(Stage primaryStage1, String teste){
        try {  
            type = teste;
            Parent root1 = FXMLLoader.load(getClass().getResource("adicionar_horarios.fxml"));
            Scene scene1 = new Scene(root1);
            
            primaryStage1.initStyle(StageStyle.DECORATED.UNDECORATED); 
            root1.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });
            root1.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    primaryStage1.setX(event.getScreenX() - xOffset);
                    primaryStage1.setY(event.getScreenY() - yOffset);
                }
            }); 
            root1.requestFocus();
            primaryStage1.setTitle("Adicionar Horários");
            primaryStage1.setAlwaysOnTop(true);
            primaryStage1.setResizable(false);
            primaryStage1.getIcons().add(new Image("Icones/school.png"));
            primaryStage1.setScene(scene1);
            primaryStage1.setMaximized(false);
            primaryStage1.show(); 
        }catch (Exception e) {
            System.out.println("Erro ao Abrir a Tela");
            e.printStackTrace();
        }  
    }
    
    @FXML
    public void seleciona_dia(){ 
        String dia = null, hora = null;
        
        if (dom.isSelected()){
            dia = "Domingo";
             
        }else if (seg.isSelected()){
            dia = "Segunda";
            
        }else if (ter.isSelected()){
            dia = "Terça";
            
        }else if (qua.isSelected()){
            dia = "Quarta";
            
        }else if (qui.isSelected()){
            dia = "Quinta";
            
        }else if (sex.isSelected()){
            dia = "Sexta";
            
        }else if (sab.isSelected()){
            dia = "Sábado"; 
        }else{
            error.setText("Selecione uma opção!");
        }
        
        if (oito.isSelected()){
            hora = "08:00 às 10:00";
            
        }else if (dez.isSelected()){
            hora = "10:00 às 12:00";
            
        }else if (doze.isSelected()){
           hora = "12:00 às 14:00";
            
        }else if (quatorze.isSelected()){
            hora = "14:00 às 16:00";
            
        }else if (dezesseis.isSelected()){
           hora = "16:00 às 18:00";
            
        }else if (dezoito.isSelected()){
            hora = "18:00 às 20:00";
            
        }else if (vinte.isSelected()){
           hora = "20:00 às 22:00";
            
        }else if (vinteedois.isSelected()){
           hora = "22:00 às 00:00";   
        }else{
            error.setText("Selecione uma opção!");
        } 
        
        if(dia != null && hora != null){
            switch(type){
                case "diciplina":{ 
                    DisciplinasController.dia_hora.add(new Pair <String,String> (dia, hora)); 
                    break;
                }
                case "evento":{ 
                    EventoController.dia_hora.add(new Pair <String,String> (dia, hora)); 
                    break;
                }
                case "trabalho":{ 
                    TrabalhoController.dia_hora.add(new Pair <String,String> (dia, hora)); 
                    break;
                }
                case "estudo":{ 
                    Hora_estudoController.dia_hora.add(new Pair <String,String> (dia, hora)); 
                    break;
                }
            } 
            
            Stage stage = (Stage) botao.getScene().getWindow(); 
            stage.close();
        }   
    }
    
    @FXML 
    private javafx.scene.control.Button botao;
    
    @FXML
    private void closeButtonAction(){ 
        Stage stage = (Stage) botao.getScene().getWindow(); 
        stage.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ToggleGroup answer = new ToggleGroup();
        ToggleGroup answer1 = new ToggleGroup();
        
        dom.setToggleGroup(answer);
        seg.setToggleGroup(answer);
        ter.setToggleGroup(answer);
        qua.setToggleGroup(answer);
        qui.setToggleGroup(answer);
        sex.setToggleGroup(answer);
        sab.setToggleGroup(answer);
        
        oito.setToggleGroup(answer1);
        dez.setToggleGroup(answer1);
        doze.setToggleGroup(answer1);
        quatorze.setToggleGroup(answer1);
        dezesseis.setToggleGroup(answer1);
        dezoito.setToggleGroup(answer1);
        vinte.setToggleGroup(answer1);
        vinteedois.setToggleGroup(answer1); 
    }    
}
