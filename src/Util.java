import java.io.IOException;
import java.util.Random;
import java.util.Arrays;

public class Util {
    public static void clearScreen() throws IOException, InterruptedException{
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }

    public static void preencherGrid(String[][] grid){

        for(int linha = 0; linha < 8; linha++)
            for(int coluna = 0; coluna < 8; coluna++)
                grid[linha][coluna] = "@";

    }

    public static void preencherGridMinas(String[][] grid){

        int minaQuantidade = 0;
        Random random = new Random();

        while(true){

            int minaLinha = random.nextInt(8);
            int minaColuna = random.nextInt(8);

            if(grid[minaLinha][minaColuna] != "*"){

                grid[minaLinha][minaColuna] = "*";
                minaQuantidade += 1;

            }

            if(minaQuantidade == 7)
                break;

        }
    }

    public static void imprimirTabela(String[][] grid, boolean jogoPerdido){

        System.out.print("    A   B   C   D   E   F   G   H\n");
        System.out.print("  +-------------------------------+");

        if(jogoPerdido == false){

            for(int linha = 0; linha < 8; linha++) {
                for (int coluna = 0; coluna < 8; coluna++) {

                    if(coluna == 0)
                        System.out.print("\n" + String.valueOf(linha) + " ");

                    if(grid[linha][coluna] == "*")
                        System.out.print("| @ ");

                    else
                        System.out.print("| " + grid[linha][coluna] + " ");
                }

                System.out.print("|\n");
                System.out.print("  +-------------------------------+");

            }
        }

        else{

            for(int linha = 0; linha < 8; linha++) {
                for (int coluna = 0; coluna < 8; coluna++) {

                    if(coluna == 0)
                        System.out.print("\n" + String.valueOf(linha) + " ");

                    if(grid[linha][coluna] == "*")
                        System.out.print("| @ ");

                    else
                        System.out.print("| " + grid[linha][coluna] + " ");
                }

                System.out.print("|\n");
                System.out.print("  +-------------------------------+");
            }
        }

    }

    public static void limparVazio(String[][] grid, int linha, int coluna){

        String[] valoresLiberados = {"1", "2", "3", "4", "5", "6", "7", "8"};

        int bombaVizinhas = 0;

        for(int i = linha - 1; i <= linha + 1; i++) {
            for (int j = coluna - 1; j <= coluna + 1; j++) {

                if ((i >= 0 && i < 8) && (j >= 0 && j < 8)) {

                    if (grid[i][j] == "*") {
                        bombaVizinhas++;
                        grid[linha][coluna] = String.valueOf(bombaVizinhas);
                    }
                }
            }
        }

        for(int i = linha - 1; i <= linha + 1; i++){
            for(int j = coluna - 1; j <= coluna + 1; j++){

                if((i >= 0 && i < 8) && (j >= 0 && j < 8)){

                        if(grid[i][j] == "@"){
                            if(bombaVizinhas == 0){
                                grid[i][j] = " ";
                                limparVazio(grid, i, j);
                            }
                        }
                    }
                }
            }

        if(bombaVizinhas == 0)
            grid[linha][coluna] = " ";

    }

    public static int posicaoEscolhida(String[][] grid, int linha, int coluna){

        String[] valoresLiberados = {" ", "1", "2", "3", "4", "5", "6", "7", "8"};

        if(grid[linha][coluna] == "*") {

            imprimirTabela(grid, true);

            for (int i = 0; i < 3; i++) {

                if (i == 0)
                    System.out.println("\n+-----------+");

                else if (i == 1)
                    System.out.println("| Game Over |");

                else
                    System.out.println("+-----------+");

            }

            return 1;

        }

        else if(Arrays.asList(valoresLiberados).contains(grid[linha][coluna])) {

            System.out.println("Posicao ja liberada");

            return 0;

        }

        else if(grid[linha][coluna] == "@"){
            limparVazio(grid, linha, coluna);
            return 0;
        }

        return 0;

    }

}
