package com.phandroo;

import com.phandroo.model.StarSystem;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
   public static void main(String[] args) {
      StarSystem starSystem = new StarSystem(1, 1);
      System.out.println(starSystem.toString());
   }

   @Override
   public void start(Stage primaryStage) throws Exception {

   }
}
