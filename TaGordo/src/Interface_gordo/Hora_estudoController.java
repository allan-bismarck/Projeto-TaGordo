

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


public class Hora_estudoController implements Initializable {

    @FXML
    private TextField nome_hora;

    @FXML
    private TextField descricao_hora;

    @FXML
    private TextField local_hora;
    
    static ArrayList <Pair <String,String> > dia_hora = new ArrayList <Pair <String,String> > (); 
    
    @FXML
    private Label aviso_add_hora;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dia_hora.clear();
    }    
    
    @FXML
    private void ir_grade() {
        dia_hora.clear();
        TaGordo.loadScene("grade.fxml", "Grade");
    }
    
    @FXML
    public void salvar_horario_estudos() throws UnknownHostException{     
        if (nome_hora.getText().isEmpty()){
            nome_hora.setPromptText("Informe o Nome");
            nome_hora.setStyle("-fx-prompt-text-fill: red");
        }
        
        if (local_hora.getText().isEmpty()){
            local_hora.setPromptText("Informe o Local");
            local_hora.setStyle("-fx-prompt-text-fill: red");
        }
        if (dia_hora.isEmpty()){
            aviso_add_hora.setText("Adicione os Horários");
        } 
        
        if (!local_hora.getText().isEmpty() && !nome_hora.getText().isEmpty() && !dia_hora.isEmpty()){
            if(TaGordo.getC().validar_disc_e_ativ(dia_hora, nome_hora.getText())) {
//                System.out.println(TaGordo.getC().validar_disc_e_ativ(dia_hora, nome_hora.getText()));
                AtividadeExtra AE;
                if (!descricao_hora.getText().isEmpty()){
                    AE = new AtividadeExtra(nome_hora.getText(),local_hora.getText(),descricao_hora.getText(),dia_hora);
                }else{
                    AE = new AtividadeExtra(nome_hora.getText(),local_hora.getText(),dia_hora);
                }
                TaGordo.getC().Cadastrar_Atividades(AE);
                TaGordo.loadScene("grade.fxml", "Grade");  
            }
        }
    }
    
    
    @FXML
    private void Close() {
        Stage stage = (Stage) descricao_hora.getScene().getWindow(); 
        stage.close();
        System.exit(0);
    }
    
    @FXML
    private void Minimizar (ActionEvent event){
        Stage stage = (Stage) descricao_hora.getScene().getWindow();
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    
    
    @FXML
    public void adicionar_hora_estudo(){ 
        Stage primaryStage1 = new Stage();
        Adicionar_horariosController ad = new Adicionar_horariosController();
        ad.adicionar_hora(primaryStage1,"estudo");     
    }

    public static ArrayList<Pair<String, String>> getDia_hora() {
        return dia_hora;
    }

}
