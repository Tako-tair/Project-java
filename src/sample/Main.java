package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;


public class Main extends Application {

    public static void connect(PackageData pd) {
        String s = "";
        try{
            Socket socket = new Socket("127.0.0.1", 3306);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            if(pd.getOperationType().equals("ADD")){
                outputStream.writeObject(pd);
            }
            else if(pd.getOperationType().equals("LIST")){
                outputStream.writeObject(pd);
                PackageData infoFromServer = (PackageData)inputStream.readObject();
                ArrayList<Order> arrayListFromServer = infoFromServer.getOrders();
                 s = "";

                for(int i=0;i<arrayListFromServer.size();i++){
                    s+=arrayListFromServer.get(i)+"\n";
                }
            }

            MenuList menuList = new MenuList();
            menuList.listText.setText(s);


            inputStream.close();
            outputStream.close();
            socket.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("My first application");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
