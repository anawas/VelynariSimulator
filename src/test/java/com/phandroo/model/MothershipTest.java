package com.phandroo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MothershipTest {

   @Test
   void distanceToStarSystem() {
      Mothership ship = new Mothership(0,0);
      StarSystem starSystem = new StarSystem(1,1);

      double distance = ship.distanceToStarSystem(starSystem);
      assertEquals(distance,1.4142,0.001);
   }
}