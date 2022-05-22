import java.util.Scanner;

public abstract class Game {
    
    abstract public void criaNovo();
    abstract public void executa();
    abstract public void carrega();
    public void sai(){
        System.out.println("Obrigado por jogar!");
        new Scanner(System.in).next();
        System.exit(0);
    }
}
