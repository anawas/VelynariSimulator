package com.phandroo;

import com.phandroo.model.GalaxyGenerator;
import com.phandroo.model.StarSystem;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {

   private static final int WIDTH = 1000;
   private static final int HEIGHT = 1000;
   private static final double SCALE = 0.01;

   public static void main(String[] args) {
      launch(args);
   }

   @Override
   public void start(Stage primaryStage) throws Exception {
      List<StarSystem> stars = GalaxyGenerator.generateGalaxy(
          100_000,
          5,
          50_000
      );

      Canvas canvas = new Canvas(WIDTH, HEIGHT);
      GraphicsContext gc = canvas.getGraphicsContext2D();

      gc.setFill(Color.BLACK);
      gc.fillRect(0, 0, WIDTH, HEIGHT);

      gc.setFill(Color.WHITE);
      for (StarSystem star : stars) {
         double screenX = WIDTH / 2 + star.x * SCALE;
         double screenY = HEIGHT / 2 + star.y * SCALE;

         gc.fillRect(screenX, screenY, 1, 1);
      }

      StackPane root = new StackPane(canvas);
      Scene scene = new Scene(root, WIDTH, HEIGHT, Color.BLACK);

      primaryStage.setScene(scene);
      primaryStage.setTitle("Velynari Galaxie");
      primaryStage.show();
   }
}
