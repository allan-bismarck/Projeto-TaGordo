
package Interface_gordo;

import Banco.ConexaoBd;
import com.mongodb.BasicDBObject;
import java.io.File;
import java.io.IOException;
import javafx.application.Application; 
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage; 
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image; 
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;

public class TaGordo extends Application {

    public static Stage  primaryStage;
    public static Class thisClass;       
    public static BasicDBObject login;  
    static double xOffset = 0;
    static double yOffset = 0;
    static ConexaoBd c ;
    
    public TaGordo() throws IOException {
        thisClass = getClass(); 
        try{
            c = new ConexaoBd();
            if(!new File("C:\\data").exists()) {
                File data = new File("C:\\data");
                data.mkdir();  
                File db = new File("C:\\data\\db");
                db.mkdir();
            }
            Runtime.getRuntime().exec("Mongod");
        }catch(Exception e){
                System.out.println("Erro:"+e); 
        } 
    }
     
    public static ConexaoBd getC() {
        return c;
    }
    
    public static BasicDBObject getLogin() {
        return login;
    }
    
    public static void setLogin(BasicDBObject login) {
        TaGordo.login = login;
    }
            
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Tela1.fxml"));
            Scene scene = new Scene(root);
            root.requestFocus(); 
            
             primaryStage.initStyle(StageStyle.DECORATED.UNDECORATED); 
            
            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });
            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    primaryStage.setX(event.getScreenX() - xOffset);
                    primaryStage.setY(event.getScreenY() - yOffset);
                }
            }); 
            primaryStage.setTitle("Bem Vindo");
            primaryStage.setResizable(false);
            primaryStage.getIcons().add(new Image("Icones/school.png"));
            primaryStage.setScene(scene);
            primaryStage.setMaximized(false);
            primaryStage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void showAlert(String infoMessage, String titleBar, String headerMessage)
    {
        Alert alert = new Alert(AlertType.ERROR);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public static void loadScene(String nameFile, String titlePage) {
        Parent root;
        try {
            root = FXMLLoader.load(thisClass.getResource(nameFile));
            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });
            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    primaryStage.setX(event.getScreenX() - xOffset);
                    primaryStage.setY(event.getScreenY() - yOffset);
                }
            }); 
            Scene scene = new Scene(root);
            root.requestFocus();
            primaryStage.setResizable(false);
            primaryStage.setTitle(titlePage);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("Erro ao Abrir a Tela");
            e.printStackTrace();
        }
    }    
}
