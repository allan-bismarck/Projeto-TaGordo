
package Interface_gordo;

import Banco.ConexaoBd;
import Banco.Disciplina;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import java.util.*; 
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label; 
import javafx.stage.Stage;
import javafx.util.Pair;

public class DisciplinasController extends Adicionar_horariosController implements Initializable {
    
    @FXML
    private TextField nome_disciplina;
    @FXML
    private TextField professor_disciplina;
    @FXML
    private TextField sala_disciplina;
    @FXML
    private Label aviso_add_hora;
    
    static ArrayList <Pair <String,String> > dia_hora = new ArrayList <Pair <String,String> > (); 
    
   
    @FXML
    public void salvar_disciplina() throws UnknownHostException{        
        if (nome_disciplina.getText().isEmpty()){
            nome_disciplina.setPromptText("Informe o Nome da Disciplina ");
            nome_disciplina.setStyle("-fx-prompt-text-fill: red");
        }
        if (professor_disciplina.getText().isEmpty()){
            professor_disciplina.setPromptText("Informe o Nome do(a) Professor(a)");
            professor_disciplina.setStyle("-fx-prompt-text-fill: red");
        }
        if (sala_disciplina.getText().isEmpty()){
            sala_disciplina.setPromptText("Informe o Nome da Sala");
            sala_disciplina.setStyle("-fx-prompt-text-fill: red"); 
        }
        if (dia_hora.isEmpty()){
            aviso_add_hora.setText("Adicione os Horários");
        } 
        if (!sala_disciplina.getText().isEmpty() && 
            !professor_disciplina.getText().isEmpty() && 
            !nome_disciplina.getText().isEmpty() && 
            !dia_hora.isEmpty()){ 
            if(TaGordo.getC().validar_disc_e_ativ(dia_hora, nome_disciplina.getText())) {
                Disciplina d = new Disciplina(nome_disciplina.getText(),sala_disciplina.getText(),professor_disciplina.getText(),dia_hora);
//                System.out.println(TaGordo.getC().validar_disc_e_ativ(dia_hora, nome_disciplina.getText()));
                TaGordo.getC().Cadastrar_Disciplina(d);
                TaGordo.loadScene("grade.fxml", "Grade"); 
            }
        }   
    }
    
    @FXML
    private void Close() {
        Stage stage = (Stage) sala_disciplina.getScene().getWindow(); 
        stage.close();
        System.exit(0);
    }
    
    @FXML
    private void Minimizar (ActionEvent event){
        Stage stage = (Stage) sala_disciplina.getScene().getWindow();
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    
    @FXML
    public void adicionar_hora_disc(){ 
        Stage primaryStage1 = new Stage();
        Adicionar_horariosController ad = new Adicionar_horariosController();
        ad.adicionar_hora(primaryStage1,"diciplina");      
     }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dia_hora.clear();
    }   
    
    @FXML
    public void ir_grade() {
        dia_hora.clear();
        TaGordo.loadScene("grade.fxml", "Grade");
    }    
    
    public static ArrayList<Pair<String, String>> getDia_hora() {
        return dia_hora;
    } 
    
}
