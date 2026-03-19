package DiceGame;

public class Die {


    private int value;

    public Die(){
        reroll();
    }

    public void reroll(){
        value = (int)((Math.random()*6)+1);
    }

    public int getValue(){
        return value;
    }

}
