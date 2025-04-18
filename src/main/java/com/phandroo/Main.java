package com.phandroo;

import com.phandroo.model.GalaxyGenerator;
import com.phandroo.model.Mothership;
import com.phandroo.model.StarSystem;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends Application {

   private static final int WIDTH = 1000;
   private static final int HEIGHT = 1000;
   private static final double SCALE = 0.01;

   private long simulationTime = 0;
   private final long timePerStep = 500;

   public static void main(String[] args) {
      launch(args);
   }

   @Override
   public void start(Stage primaryStage) throws Exception {
      List<StarSystem> stars = GalaxyGenerator.generateGalaxy(
          10_000,
          2,
          50_000
      );

      List<Mothership> motherships = new ArrayList<>();
      Mothership ship = new Mothership(1, 1);  // Start 1 Lj von Zentrum
      StarSystem targetSystem = stars.get(new Random().nextInt(stars.size()));
      ship.target = targetSystem;
      motherships.add(ship);

      Canvas canvas = new Canvas(WIDTH, HEIGHT);
      GraphicsContext gc = canvas.getGraphicsContext2D();

      redrawCanvas(gc, stars, motherships);
      StackPane root = new StackPane(canvas);
      Scene scene = new Scene(root, WIDTH, HEIGHT, Color.BLACK);
      scene.setOnKeyPressed(event -> {
         switch (event.getCode()) {
            case Q -> primaryStage.close();
            case N -> {
               simulationTime += timePerStep;
               for (Mothership s : motherships) {
                  s.moveTowardsTarget(100);  // 100 Lj pro Schritt
               }
               redrawCanvas(gc, stars, motherships);
            }
         }
      });

      primaryStage.setScene(scene);
      primaryStage.setTitle("Velynari Galaxie");
      primaryStage.show();
   }

   private void redrawCanvas(GraphicsContext gc, List<StarSystem> stars, List<Mothership> motherships) {
      gc.setFill(Color.BLACK);
      gc.fillRect(0, 0, WIDTH, HEIGHT);

      gc.setFill(Color.WHITE);
      for (StarSystem star : stars) {
         double screenX = WIDTH / 2 + star.x * SCALE;
         double screenY = HEIGHT / 2 + star.y * SCALE;
         gc.fillRect(screenX, screenY, 1, 1);
      }

      gc.setFill(Color.GREEN);
      for (Mothership ship : motherships) {
         double screenX = WIDTH / 2 + ship.target.x * SCALE;
         double screenY = HEIGHT / 2 + ship.target.y * SCALE;
         gc.fillOval(screenX - 3, screenY - 3, 6, 6);
      }

      gc.setFill(Color.RED);
      for (Mothership ship : motherships) {
         double screenX = WIDTH / 2 + ship.x * SCALE;
         double screenY = HEIGHT / 2 + ship.y * SCALE;
         gc.fillOval(screenX - 3, screenY - 3, 6, 6);
      }

      gc.setFill(Color.LIGHTGREY);
      gc.fillText("Zeit: " + formatYears(simulationTime), WIDTH - 180, HEIGHT - 20);
   }

   private String formatYears(long years) {
      if (years >= 1_000_000) {
         return years / 1_000_000 + " Mio. Jahre";
      } else if (years >= 1_000) {
         return years / 1_000 + " Tsd. Jahre";
      } else {
         return years + " Jahre";
      }
   }
}
