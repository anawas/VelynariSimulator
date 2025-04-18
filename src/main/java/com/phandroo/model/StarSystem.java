package com.phandroo.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.Random;

public class StarSystem {
   public double x, y;   // Position in Lj
   public boolean colonized;
   public boolean inhabitable;

   public StarSystem(double x, double y) {
      this.x = x;
      this.y = y;
      this.colonized = false;
      this.inhabitable = setIsInhabitable(0.01);
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
      } else if (colonized) {
         gc.setFill(Color.rgb(100, 100, 255));
         gc.fillOval(screenX - 3, screenY - 3, 6, 6);
      } else {
         gc.setFill(starColor);
         gc.fillOval(screenX, screenY, 1, 1);
      }
   }
}
