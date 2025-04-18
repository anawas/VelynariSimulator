package com.phandroo.model;

public class StarSystem {
   public double x, y;   // Position in Lj
   public boolean colonized;
   public boolean inhabitable;

   public StarSystem(double x, double y) {
      this.x = x;
      this.y = y;
      this.colonized = false;
      this.inhabitable = false;
   }
}
