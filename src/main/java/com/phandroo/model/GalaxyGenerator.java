package com.phandroo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GalaxyGenerator {

   public static List<StarSystem> generateGalaxy(int numStars, int numArms, double maxRadius) {
      List<StarSystem> stars = new ArrayList<StarSystem>();
      Random random = new Random();

      for (int i = 0; i < numStars; i++) {
         double radius = Math.pow(random.nextDouble(), 0.5) * maxRadius;  // Dichtezentrum
         double angle = radius * 0.3 + (6.27 * random.nextInt(numArms) / numArms);
         double armOffset = (random.nextDouble() - 0.5) * (Math.PI / numArms);  // Verrauschung

         double finalAngle = angle + armOffset;
         double x = radius * Math.cos(finalAngle);
         double y = radius * Math.sin(finalAngle);

         // Zufällige Streuung der Systeme
         x += random.nextGaussian() * 500;
         y += random.nextGaussian() * 500;

         stars.add(new StarSystem(x, y));
      }

      return stars;
   }
}
