package SpinnerGame;

public class SpinnerGame {
    public int spin(int min, int max){
        return (int)(Math.random()*(max+1))+min;
    }
    public void playRound(){
        int playerValue = spin(1,10);
        int computerValue = spin(2,8);
        if(computerValue == playerValue){
            playerValue += spin(1,10);
            computerValue += spin(1,10);
        }
        if (computerValue==playerValue){
            System.out.println("Tie. 0 points");
        }
        if (playerValue>computerValue){
            System.out.println("You win! " + (playerValue-computerValue) + " points");
        } else {
            System.out.println("You lose. " +(playerValue-computerValue) + " points");
        }

    }
}
