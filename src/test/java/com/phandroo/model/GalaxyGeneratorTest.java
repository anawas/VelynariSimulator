package com.phandroo.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GalaxyGeneratorTest {

   int numStars = 100_000;
   int galaxyRadius_Lj = 50_000;

   @Test
   void generateGalaxy() {
      List<StarSystem> galaxy = GalaxyGenerator.generateGalaxy(numStars, 4, galaxyRadius_Lj);
      assert(galaxy.size() == numStars);
   }
}