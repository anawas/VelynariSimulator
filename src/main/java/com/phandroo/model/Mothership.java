package com.phandroo.model;

import com.phandroo.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;

public class Mothership implements Drawable {
   public double x, y;
   public StarSystem target;

   public Mothership(double x, double y) {
      this.x = x;
      this.y = y;
      this.target = null;
   }

   public void moveTowardsTarget(double stepSize) {
      if (target == null) return;

      double dx = target.x - x;
      double dy = target.y - y;
      double distance = Math.sqrt(dx * dx + dy * dy);

      if (distance < stepSize) {
         x = target.x;
         y = target.y;
         target.colonized = true;
         target = null;  // Zielsytem erreicht
      } else {
         x += (dx / distance) * stepSize;
         y += (dy / distance) * stepSize;
      }
   }

   public void draw(GraphicsContext gc, double scale) {
      if (target == null) return;

      int width = (int)gc.getCanvas().getWidth();
      int height = (int)gc.getCanvas().getHeight();

      gc.setFill(Color.GREEN);
      double screenX = width / 2 + target.x * scale;
      double screenY = height / 2 + target.y * scale;
      gc.fillOval(screenX - 3, screenY - 3, 6, 6);

      gc.setFill(Color.RED);
      screenX = width / 2 + x * scale;
      screenY = height / 2 + y * scale;
      gc.fillOval(screenX - 3, screenY - 3, 6, 6);
   }
}
