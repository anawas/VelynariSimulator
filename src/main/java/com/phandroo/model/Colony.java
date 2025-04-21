package com.phandroo.model;

public class Colony {
   private long generations;
   private boolean abandonned = false;
   private long inhabitants;


   public void setAbandonned() {
      this.abandonned = true;
      this.inhabitants = 0;
   }
}
