package com.phandroo.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

public class StarSystem {
   public double x, y;   // Position in Lj
   public boolean colonized;
   public boolean inhabitable;
   public boolean visited;
   public Colony colony;

   public StarSystem(double x, double y) {
      this.x = x;
      this.y = y;
      this.colonized = false;
      this.inhabitable = setIsInhabitable(0.1);
      this.visited = false;
      this.colony = new Colony();
   }

   private boolean setIsInhabitable(double probability) {
      if (new Random().nextFloat() < probability) {
         return true;
      }
      return false;
   }

   public void draw(GraphicsContext gc, double scale) {
      int width = (int) gc.getCanvas().getWidth();
      int height = (int) gc.getCanvas().getHeight();

      double screenX = width / 2 + x * scale;
      double screenY = height / 2 + y * scale;

      Color starColor = Color.WHITE;
      if (inhabitable) {
         gc.setFill(Color.YELLOW);
         gc.fillOval(screenX, screenY, 1, 1);
      }

      gc.setFill(starColor);
      gc.fillOval(screenX, screenY, 1, 1);

      if (colonized) {
         // A colony may be extinct with 0.1 % probability
         // In the end, every colony dies.
         if (new Random().nextFloat() < 0.001) {
            colony = null;
            colonized = false;
         } else {
            gc.setFill(Color.rgb(100, 100, 255));
            gc.fillOval(screenX - 3, screenY - 3, 6, 6);
         }
      }
   }
}
