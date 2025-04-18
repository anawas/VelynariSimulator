package com.phandroo.model;

public class StarSystem {
   public double x, y;   // Position in Lj
   public boolean colonized;

   public StarSystem(double x, double y) {
      this.x = x;
      this.y = y;
      this.colonized = false;
   }
}
