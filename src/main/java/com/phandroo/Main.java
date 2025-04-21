package com.phandroo;

import com.phandroo.model.GalaxyGenerator;
import com.phandroo.model.Mothership;
import com.phandroo.model.StarSystem;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.*;
import java.util.List;

public class Main extends Application {

   private static final int WIDTH = 1000;
   private static final int HEIGHT = 1000;
   private static final double SCALE = 0.01;

   private long simulationTime = 0;
   private final long timePerStep = 500;

   List<StarSystem> stars = null;

   public static void main(String[] args) {
      launch(args);
   }

   @Override
   public void start(Stage primaryStage) throws Exception {
    stars = GalaxyGenerator.generateGalaxy(
          10_000,
          2,
          50_000
      );

      List<Mothership> motherships = new ArrayList<>();
      for (int i = 0; i < 3; i++) {
         Mothership ship = new Mothership(1, 1);  // Start 1 Lj von Zentrum
         StarSystem targetSystem = findClosestStarSystem(ship);
         ship.target = targetSystem;
         motherships.add(ship);
      }
      Canvas canvas = new Canvas(WIDTH, HEIGHT);
      GraphicsContext gc = canvas.getGraphicsContext2D();

      redrawCanvas(gc, stars, motherships);

      StackPane root = new StackPane(canvas);
      Scene scene = new Scene(root, WIDTH, HEIGHT, Color.BLACK);
      scene.setOnKeyPressed(event -> {
         switch (event.getCode()) {
            case Q -> primaryStage.close();
            case S -> saveCanvasAsPng(canvas);
         }
      });

      Timeline timeline = new Timeline(
          new KeyFrame(Duration.millis(100), e -> {
             simulationTime += timePerStep;
             for (Mothership s : motherships) {
                s.moveTowardsTarget(100);  // 100 Lj pro Schritt
             }
             redrawCanvas(gc, stars, motherships);
          })
      );
      timeline.setCycleCount(Timeline.INDEFINITE);
      timeline.play();
      primaryStage.setScene(scene);
      primaryStage.setTitle("Velynari Galaxie");
      primaryStage.show();
   }

   private void saveCanvasAsPng(Canvas canvas) {
      WritableImage image = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
      canvas.snapshot(null, image);

      File file = new File("target.png");
      try {
         ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   private StarSystem findClosestStarSystem(Mothership mothership) {
      List<StarSystem> closestStarSystems = stars.stream()
          .sorted(Comparator.comparingDouble(s -> mothership.distanceToStarSystem(s)))
          .filter(s -> s.colonized==false)
          .filter(s -> s.visited == false)
          .limit(5)
          .toList();

      // Need to make sure that the system is visited by only one ship. We set ths status
      // to be visited.
      StarSystem system = closestStarSystems.get(new Random().nextInt(closestStarSystems.size()));
      system.visited = true;
      return system;
   }
   private void redrawCanvas(GraphicsContext gc, List<StarSystem> stars, List<Mothership> motherships) {
      gc.setFill(Color.BLACK);
      //gc.fillRect(0, 0, WIDTH, HEIGHT);

      stars.forEach(star -> {star.draw(gc, SCALE);});

      motherships.stream().filter(mothership -> mothership.target != null)
          .forEach(mothership -> {mothership.draw(gc, SCALE);});

      motherships.stream().filter(mothership -> mothership.target == null)
          .forEach(mothership -> {mothership.target = findClosestStarSystem(mothership);});

      String infoMessage = "Zeit: "
          + formatYears(simulationTime)
          + "  /  Kolonien: "
          + countColonializedSystems();

      Text text = new Text(infoMessage + "  ");  // Add additional spaces
      double width = text.getLayoutBounds().getWidth();
      gc.setFill(Color.BLACK);
      gc.fillRect(WIDTH-220, HEIGHT-35, width, 20);
      gc.setFill(Color.LIGHTGREY);
      gc.fillText(infoMessage, WIDTH-220, HEIGHT-20);
   }

   private long countColonializedSystems() {
      return stars.stream().filter(s -> s.colonized).count();
   }

   private String formatYears(long years) {
      if (years >= 1_000_000) {
         double mio = years / 1_000_000.0;
         NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMANY);
         nf.setMinimumFractionDigits(3);
         nf.setMaximumFractionDigits(3);
         return nf.format(mio) + " Mio. Jahre";
      } else if (years >= 1_000) {
         return years / 1_000 + " Tsd. Jahre";
      } else {
         return years + " Jahre";
      }
   }
}
