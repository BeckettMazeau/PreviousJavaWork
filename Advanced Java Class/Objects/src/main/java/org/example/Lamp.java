package org.example;

public class Lamp {
    //MAKING A CLASS PT.1: INSTANCE VARIABLES
        //What are the values that the class needs to work?
    private boolean on;
    private int height;
    private double brightness;
    public Lamp(int startingHeight)
    {
        on = false;
        height = startingHeight;

    }
    //PART 3 OF WRITING A CLASS: METHODS
    public void turnOn(){

        on = true;
    }
    public void turnOff(){
        on = false;
    }
    public void setBrightness(double newBrightness){
        if (newBrightness >= 0 && newBrightness <= 100) {
            brightness = newBrightness;
        }
        else {
            System.out.println("Bad Brightness");
        }
    }
    public double getBrightness(){
        return brightness;
    }

    }


