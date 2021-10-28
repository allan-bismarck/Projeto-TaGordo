package Interface_gordo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Banco.Estudante;
import java.net.URL;
import java.util.ResourceBundle; 
import javafx.fxml.Initializable;  
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.UnknownHostException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button; 
import javafx.scene.control.TextField; 
import javafx.stage.Stage;

public class Tela1Controller implements Initializable {
    
     @FXML
    private TextField login_cadastro;
      
    @FXML
    private JFXPasswordField senha_cadastro;

    @FXML
    private TextField nome_cadastro;

    @FXML
    private JFXPasswordField confirmar_cadastro;
    
    @FXML
    private JFXTextField login_login;

    @FXML
    private JFXPasswordField senha_login; 
    
    
    private void ir_grade() {
       TaGordo.loadScene("grade.fxml", "Grade");
    }
    
    @FXML
    public void salvar_usuario() throws UnknownHostException{ 
        final String regex = "[\\w]*";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher;
         
        if (nome_cadastro.getText().isEmpty()){
            nome_cadastro.setPromptText("Informe o Nome");
            nome_cadastro.setStyle("-fx-prompt-text-fill: red");
        }
        if (login_cadastro.getText().isEmpty()){              
            login_cadastro.setPromptText("Informe o Login");
            login_cadastro.setStyle("-fx-prompt-text-fill: red");
        }
        matcher = pattern.matcher(login_cadastro.getText()); 
        if (senha_cadastro.getText().isEmpty()){
            senha_cadastro.setPromptText("Informe a Senha");
            senha_cadastro.setStyle("-fx-prompt-text-fill: red");
        } 
        if (confirmar_cadastro.getText().isEmpty()){
            confirmar_cadastro.setPromptText("Confirme a Senha");    
            confirmar_cadastro.setStyle("-fx-prompt-text-fill: red");
        }    
        
        if (!login_cadastro.getText().isEmpty() && !senha_cadastro.getText().isEmpty() && !nome_cadastro.getText().isEmpty() && !confirmar_cadastro.getText().isEmpty() ){
            if (!confirmar_cadastro.getText().equals(senha_cadastro.getText()) ){  
                confirmar_cadastro.clear();
                confirmar_cadastro.setPromptText("Senha não confere");
                confirmar_cadastro.setStyle("-fx-prompt-text-fill: red");
            }else if (!matcher.matches()){
                TaGordo.showAlert(null, "Erro", "Login Inválido");
                login_cadastro.clear();
                login_cadastro.setPromptText("Apenas letras, números e _ ");
                login_cadastro.setStyle("-fx-prompt-text-fill: red"); 
            }else if(TaGordo.getC().possui_cadastro(login_cadastro.getText())) {
                    TaGordo.showAlert(null, "Erro", "Login já cadastrado");
                    login_cadastro.clear();  
                    login_cadastro.setPromptText("Login já cadastrado");
                    login_cadastro.setStyle("-fx-prompt-text-fill: red");
                }else if (!login_cadastro.getText().isEmpty() && !senha_cadastro.getText().isEmpty() && !nome_cadastro.getText().isEmpty() && !confirmar_cadastro.getText().isEmpty() ){
                    Estudante E = new Estudante(nome_cadastro.getText(), login_cadastro.getText(), senha_cadastro.getText());
                    TaGordo.getC().Cadastrar_Estudante(E);
                    TaGordo.loadScene("grade.fxml", "Grade");
                } 
            } 
    }
    
    @FXML
    public void fazer_login() throws UnknownHostException{         
        
        if (login_login.getText().isEmpty()){           
            login_login.setPromptText("Informe o Login");
            login_login.setStyle("-fx-prompt-text-fill: red");
        }
        if (senha_login.getText().isEmpty()){
            senha_login.setPromptText("Informe a Senha");
            senha_login.setStyle("-fx-prompt-text-fill: red");
        }
        if (!login_login.getText().isEmpty() && !senha_login.getText().isEmpty() ){
            if(TaGordo.getC().possui_cadastro(login_login.getText())) {
                if(TaGordo.getC().validar_usuario_senha(login_login.getText(), senha_login.getText()))
                    TaGordo.loadScene("grade.fxml", "Grade");
                else
                    TaGordo.showAlert(null, "Erro", "Login e/ou Senha Incorretos");
            }else{ 
                TaGordo.showAlert(null, "Erro", "Usuário não cadastrado");
                login_login.clear();
                senha_login.clear();
            }
        }
    }
    
    @FXML
    private void Close() {
        Stage stage = (Stage) senha_login.getScene().getWindow(); 
        stage.close();
        System.exit(0);
    }
    
    @FXML
    private void Minimizar (ActionEvent event){
        Stage stage = (Stage) senha_login.getScene().getWindow();
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }      
}
