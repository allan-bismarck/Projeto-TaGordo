

package Interface_gordo;

import Banco.AtividadeExtra;
import Banco.ConexaoBd; 
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Pair;


public class TrabalhoController implements Initializable {
    
    @FXML
    private TextField nome_trabalho;

    @FXML
    private TextField descricao_trabalho;

    @FXML
    private TextField local_trabalho;
    
    static ArrayList <Pair <String,String> > dia_hora = new ArrayList <Pair <String,String> > (); 
   
    
    @FXML
    private Label aviso_add_hora;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dia_hora.clear();
    }    
    
    @FXML
    public void salvar_trabalho() throws UnknownHostException{       
        if (nome_trabalho.getText().isEmpty()){
            nome_trabalho.setPromptText("Informe o Nome");
            nome_trabalho.setStyle("-fx-prompt-text-fill: red");
        }
        
        if (local_trabalho.getText().isEmpty()){
            local_trabalho.setPromptText("Informe o Local");
            local_trabalho.setStyle("-fx-prompt-text-fill: red");
        }
        if (dia_hora.isEmpty()){
            aviso_add_hora.setText("Adicione os Horários");
        } 
        
        if (!local_trabalho.getText().isEmpty() && !nome_trabalho.getText().isEmpty() && !dia_hora.isEmpty()){
            if(TaGordo.getC().validar_disc_e_ativ(dia_hora, nome_trabalho.getText())) { 
                AtividadeExtra AE;  
                if (!descricao_trabalho.getText().isEmpty()){
                    AE = new AtividadeExtra(nome_trabalho.getText(),local_trabalho.getText(),descricao_trabalho.getText(),dia_hora);
                }else{
                    AE = new AtividadeExtra(nome_trabalho.getText(),local_trabalho.getText(),dia_hora);
                }
                TaGordo.getC().Cadastrar_Atividades(AE);
                TaGordo.loadScene("grade.fxml", "Grade");  
            }
        }
    }
    
    
    @FXML
    private void Close() {
        Stage stage = (Stage) descricao_trabalho.getScene().getWindow(); 
        stage.close();
        System.exit(0);
    }
    
    @FXML
    private void Minimizar (ActionEvent event){
        Stage stage = (Stage) descricao_trabalho.getScene().getWindow();
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    
     @FXML
    private void ir_grade() {
        dia_hora.clear();
        TaGordo.loadScene("grade.fxml", "Grade");
    }
    
    @FXML
    public void adicionar_hora_trabalho(){ 
        Stage primaryStage1 = new Stage();
        Adicionar_horariosController ad = new Adicionar_horariosController();
        ad.adicionar_hora(primaryStage1,"trabalho");     
    }

    public static ArrayList<Pair<String, String>> getDia_hora() {
        return dia_hora;
    }
    
}
