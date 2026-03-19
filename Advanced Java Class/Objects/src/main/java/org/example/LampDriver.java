package org.example;

public class LampDriver {
    public static void main(String [] args){
         Lamp stainedGlassLamp = new Lamp(20);
         Lamp floorLamp = new Lamp(0);

         stainedGlassLamp.turnOff();
         stainedGlassLamp.setBrightness(50);
         System.out.println(stainedGlassLamp.getBrightness());


    }
}
