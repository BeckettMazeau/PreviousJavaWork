package DiceGame;

public class HighDiceGame {


    private Die[] dice;	// array of dice values used in the game
    private int numTurns;	// number of turns remaining in the game

    public HighDiceGame(int numDice, int numberOfTurns){
        dice = new Die[numDice];
        numTurns = numberOfTurns;
        for (int i = 0; i < dice.length; i++) {
            dice[i]=new Die();
        }
    }

    public void reroll(boolean[] rolls){
        if(numTurns>0) {
            numTurns--;
            for (int i = 0; i < dice.length ; i++) {
                if (rolls[i]) {
                    dice[i].reroll();
                }
            }
        }
    }


    public int getScore(){
        int temp = 0;
        for (int z = 0; z < dice.length; z++) {
            temp += dice[z].getValue();
        }
        return temp;
    }
}
