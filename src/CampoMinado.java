import java.sql.Array;
import java.util.Scanner;

public class CampoMinado extends Game{

    String[][] gridJogo = new String[8][8];

    @Override
    public void criaNovo() {

        Util.preencherGrid(gridJogo);
        Util.preencherGridMinas(gridJogo);
        Util.imprimirTabela(gridJogo, false);

    }

    @Override
    public void executa() {

        Scanner teclado = new Scanner(System.in);

        int linha;
        int coluna;

        while(true){
            System.out.print("\nEscolha uma linha: ");
            linha = teclado.nextInt();

            System.out.print("Escolha uma coluna: ");
            coluna = teclado.nextInt();

            if(Util.posicaoEscolhida(gridJogo, linha, coluna) == 1)
                break;

            Util.imprimirTabela(gridJogo,false);
        }

    }

    @Override
    public void carrega() {

        System.out.println("aa");

    }
}
