package com.phandroo.model;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Mothership {
   public double x, y;
   public StarSystem target;

   public Mothership(double x, double y) {
      this.x = x;
      this.y = y;
      this.target = null;
   }

   public void moveTowardsTarget(double stepSize) {
      if (target == null) return;

      double distance = distanceToStarSystem(target);
      if (distance < stepSize) {
         x = target.x;
         y = target.y;
         target.visited = true;
         if (target.inhabitable)
            target.colonized = true;
         target = null;  // Zielsytem erreicht
      } else {
         x += ((target.x - x) / distance) * stepSize;
         y += ((target.y - y) / distance) * stepSize;
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

   public double distanceToStarSystem(StarSystem target) {
      double dx = target.x - x;
      double dy = target.y - y;
      return Math.sqrt(dx * dx + dy * dy);
   }
}
