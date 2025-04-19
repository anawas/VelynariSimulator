package com.phandroo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StarSystemTest {
   @Test
   void testStaSystemIsInitialized() {
      StarSystem starSystem = new StarSystem(1, 1);
      assertNotNull(starSystem);
      assert starSystem.x == 1;
      assert starSystem.colonized == false;
   }
}