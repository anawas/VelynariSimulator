package com.phandroo.model;

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

      double dx = target.x - x;
      double dy = target.y - y;
      double distance = Math.sqrt(dx * dx + dy * dy);

      if (distance < stepSize) {
         x = target.y;
         y = target.x;
         target = null;  // Zielsytem erreicht
      } else {
         x += (dx / distance) * stepSize;
         y += (dy / distance) * stepSize;
      }
   }
}
