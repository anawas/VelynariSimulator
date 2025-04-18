package com.phandroo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GalaxyGenerator {

   public static List<StarSystem> generateGalaxy(int numStars, int numArms, double maxRadius) {
      List<StarSystem> stars = new ArrayList<StarSystem>();
      Random random = new Random();

      for (int i = 0; i < numStars; i++) {
         double radius = maxRadius * Math.pow(random.nextDouble(), 1.5); // steilere Kurve als sqrt()

         double baseAngle = (2 * Math.PI * random.nextInt(numArms)) / numArms;
         double spiralTwist = radius * 0.00015;
         double angle = baseAngle + spiralTwist;

         // Verrauschung (Offset) für Natürlichkeit
         double armOffset = (random.nextDouble() - 0.5) * (Math.PI / numArms);

         double finalAngle = angle + armOffset;
         double x = radius * Math.cos(finalAngle);
         double y = radius * Math.sin(finalAngle);

         // Zufällige Streuung der Systeme
         x += random.nextGaussian() * 2000;
         y += random.nextGaussian() * 2000;

         stars.add(new StarSystem(x, y));
      }
      return stars;
   }
}
