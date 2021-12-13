import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class CampoMinado extends Game{

    String[][] gridJogo = new String[8][8]; // Grid com os valores reais
    String[][] gridTemporario = new String[8][8]; // Grid impresso
    int bandeiras = 10;

    // ------------------------------- Funções ------------------------------- //

    public void imprimirTabela(boolean fimDeJogo){

        if(fimDeJogo == false){

            System.out.print("    A   B   C   D   E   F   G   H\n");
            System.out.print("  +-------------------------------+       +------------------------------+");

            for(int linha = 0; linha < 8; linha++) {
                for (int coluna = 0; coluna < 8; coluna++) {

                    if (coluna == 0)
                        System.out.print("\n" + linha + " ");

                    System.out.print("| " + gridTemporario[linha][coluna] + " ");

                }

                if (linha == 0) {

                    System.out.print("|       |           Instruções         |\n");
                    System.out.print("  +-------------------------------+       | Funçoes: abrir / (des)marcar |");

                }
                else if (linha == 1) {

                    System.out.print("|       | Exemplo: abrir A1            |\n");
                    System.out.print("  +-------------------------------+       +------------------------------+");

                }
                else if (linha == 2) {

                    if (bandeiras == 10) {

                        System.out.print("|       | Bandeiras: " + bandeiras + "                |\n");
                        System.out.print("  +-------------------------------+       +------------------------------+");

                    }
                    else {

                        System.out.print("|       | Bandeiras: " + bandeiras + "                 |\n");
                        System.out.print("  +-------------------------------+       +------------------------------+");
                    }

                }
                else {

                    System.out.print("|\n");
                    System.out.print("  +-------------------------------+");

                }
            }
        }

        else{

            System.out.print("    A   B   C   D   E   F   G   H\n");
            System.out.print("  +-------------------------------+");

            for(int linha = 0; linha < 8; linha++) {
                for (int coluna = 0; coluna < 8; coluna++) {

                    if(coluna == 0)
                        System.out.print("\n" + linha + " ");

                    if(gridJogo[linha][coluna].equals("*"))
                        System.out.print("| * ");

                    else
                        System.out.print("| " + gridTemporario[linha][coluna] + " ");
                }

                System.out.print("|\n");
                System.out.print("  +-------------------------------+");
            }
        }
    }

    public int sistemaSave(int saveLoad){

        switch (saveLoad){

            // Salvar
            case 1:
                try{

                    FileWriter fileWriterInstancia = new FileWriter("GameSave.txt", false);
                    BufferedWriter bufferedWriterInstancia = new BufferedWriter(fileWriterInstancia);

                    for(int linha = 0; linha < 8; linha++){

                        for(int coluna = 0; coluna < 8; coluna++)
                            bufferedWriterInstancia.write(gridJogo[linha][coluna]);

                        bufferedWriterInstancia.write("\n");
                    }

                    for(int linha = 0; linha < 8; linha++){

                        for(int coluna = 0; coluna < 8; coluna++)
                            bufferedWriterInstancia.write(gridTemporario[linha][coluna]);

                        bufferedWriterInstancia.write("\n");
                    }

                    bufferedWriterInstancia.write(Integer.toString(bandeiras));

                    bufferedWriterInstancia.close();

                    return -1;
                }
                catch (IOException ex) {
                    System.err.println("Nao foi possivel criar um novo save");
                }
                break;

            case 2:

                // Carregar
                try{

                    FileReader fileReaderInstancia = new FileReader("GameSave.txt");
                    BufferedReader bufferedReaderInstancia = new BufferedReader(fileReaderInstancia);

                    String fileString;
                    char charTemporario;

                    for(int linha = 0; linha < 8; linha++){

                        fileString = bufferedReaderInstancia.readLine();

                        for(int coluna = 0; coluna < 8; coluna++){

                            charTemporario = fileString.charAt(coluna);
                            gridJogo[linha][coluna] = Character.toString(charTemporario);

                        }
                    }

                    for(int linha = 0; linha < 8; linha++){

                        fileString = bufferedReaderInstancia.readLine();

                        for(int coluna = 0; coluna < 8; coluna++){

                            charTemporario = fileString.charAt(coluna);
                            gridTemporario[linha][coluna] = Character.toString(charTemporario);

                        }
                    }

                    fileString = bufferedReaderInstancia.readLine();
                    bandeiras = Integer.valueOf(fileString);

                    bufferedReaderInstancia.close();

                    return bandeiras;

                }
                catch (IOException ex) {
                    System.err.println("Nao foi possivel carregar o save");
                }
                break;
        }

        return -1;

    }

    public void preencherGrid(){

        for(int linha = 0; linha < 8; linha++)
            for(int coluna = 0; coluna < 8; coluna++){

                gridJogo[linha][coluna] = "@";
                gridTemporario[linha][coluna] = "@";

            }
    }

    public void preencherGridMinas(){

        int minaQuantidade = 0;
        Random random = new Random();

        while(true){

            int minaLinha = random.nextInt(8);
            int minaColuna = random.nextInt(8);

            if(!gridJogo[minaLinha][minaColuna].equals("*")){

                gridJogo[minaLinha][minaColuna] = "*";
                minaQuantidade++;

            }

            if(minaQuantidade == 7)
                break;

        }
    }

    public void limparVazio(int linha, int coluna){

        int minasVizinhas = 0;

        // Checa as casas vizinhas, para cada mina encontrada adiciona-se +1 à 'minasVizinhas'

        for(int i = linha - 1; i <= linha + 1; i++) {
            for (int j = coluna - 1; j <= coluna + 1; j++) {

                if ((i >= 0 && i < 8) && (j >= 0 && j < 8)) {

                    if (gridJogo[i][j].equals("*")) {

                        minasVizinhas++;

                        gridJogo[linha][coluna] = String.valueOf(minasVizinhas);
                        gridTemporario[linha][coluna] = String.valueOf(minasVizinhas);

                    }
                }
            }
        }

        /* Caso a casa vizinha esteja fechada e nenhuma mina seja encontrada, chama recursivamente a função para a
        casa vizinha */

        for(int i = linha - 1; i <= linha + 1; i++){

            for(int j = coluna - 1; j <= coluna + 1; j++){

                if((i >= 0 && i < 8) && (j >= 0 && j < 8)){

                    if(gridJogo[i][j].equals("@")){
                        if(minasVizinhas == 0){

                            gridJogo[i][j] = " ";
                            gridTemporario[i][j] = " ";

                            limparVazio(i, j);

                        }
                    }
                }
            }
        }
    }

    public void marcarBandeira(int linha, String colunaLetra){

        String[] valoresLiberados = {" ", "1", "2", "3", "4", "5", "6", "7", "8"};
        String[] colunaLetraArray = {"A", "B", "C", "D", "E", "F", "G", "H"};

        int coluna = Arrays.asList(colunaLetraArray).indexOf(colunaLetra);

        if(Arrays.asList(valoresLiberados).contains(gridTemporario[linha][coluna]))
            System.out.println("Posição já liberada");

        else if(bandeiras > 0){
            gridTemporario[linha][coluna] = "♦";
            bandeiras--;
        }

        else if(bandeiras == 0)
            System.out.println("\nTodas as bandeiras foram usadas");

    }

    public void desmarcarBandeira(int linha, String colunaLetra){

        String[] valoresLiberados = {" ", "1", "2", "3", "4", "5", "6", "7", "8"};
        String[] colunaLetraArray = {"A", "B", "C", "D", "E", "F", "G", "H"};

        int coluna = Arrays.asList(colunaLetraArray).indexOf(colunaLetra);

        if(Arrays.asList(valoresLiberados).contains(gridTemporario[linha][coluna]))
            System.out.println("Posição já liberada");

        else if(gridTemporario[linha][coluna].equals("@"))
            System.out.println("Posição não marcada");

        else if(bandeiras < 10 && gridTemporario[linha][coluna].equals("♦")){
            gridTemporario[linha][coluna] = "@";
            bandeiras++;
        }

    }

    public int vitoriaCheck() {

        int closedSymbols = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                if (gridJogo[i][j].equals("@"))
                    closedSymbols++;

            }

        }

        if (closedSymbols == 0) {

            imprimirTabela(true);

            for (int i = 0; i < 3; i++) {

                if (i == 0)
                    System.out.println("\n+--------------+");

                else if (i == 1)
                    System.out.println("| Você Venceu! |");

                else
                    System.out.println("+--------------+");

            }

            return 1;

        }

        return 0;

    }

    public int abrirPosicao(int linha, String colunaLetra){

        String[] valoresLiberados = {" ", "1", "2", "3", "4", "5", "6", "7", "8"};
        String[] colunaLetraArray = {"A", "B", "C", "D", "E", "F", "G", "H"};

        int coluna = Arrays.asList(colunaLetraArray).indexOf(colunaLetra);

        // Fim de jogo
        if(gridJogo[linha][coluna].equals("*")) {

            imprimirTabela(true);

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

        // Posicao ja liberada
        else if(Arrays.asList(valoresLiberados).contains(gridTemporario[linha][coluna])) {
            System.out.println("Posicao ja liberada");
            return 0;
        }

        else if(gridJogo[linha][coluna].equals("@")){

            if(gridTemporario[linha][coluna].equals("♦"))
                bandeiras++;

            limparVazio(linha, coluna);

            if(vitoriaCheck() == 1)
                return 1;

            return 0;
        }

        return 0;

    }

    // ------------------------------------------------------------------------ //

    @Override
    public void criaNovo() {

        try {

            Files.deleteIfExists(Path.of("GameSave.txt"));
            File save = new File("GameSave.txt");
            save.createNewFile();

        }
        catch (IOException ex) {
            System.err.println("Save já criado");
        }

        preencherGrid();
        preencherGridMinas();
        imprimirTabela(false);

    }

    @Override
    public void carrega() {

        int bandeiraTemp = sistemaSave(2);

        if(bandeiraTemp != -1)
            bandeiras = bandeiraTemp;

        imprimirTabela(false);

    }

    @Override
    public void executa() {

        Scanner teclado = new Scanner(System.in);

        String[] colunaLetraArray = {"A", "B", "C", "D", "E", "F", "G", "H"};
        String linhaNumeroArray = "01234567";

        String modoPosicao;

        String modo;
        String coluna;
        int linha;

        while(true){

            try{

                System.out.print("\nEscolha uma funcao, coluna e linha: ");
                modoPosicao = teclado.nextLine().toUpperCase(Locale.ROOT);

                if(modoPosicao.length() > 12 || !modoPosicao.contains(" ") || modoPosicao.length() < 8)
                    throw new InputMismatchException("entrada invalida");

                else{

                    int split = modoPosicao.indexOf(' ');
                    char linhaChar;
                    char colunaChar;

                    modo = modoPosicao.substring(0, split);
                    colunaChar = modoPosicao.charAt(split+1);
                    coluna = Character.toString(colunaChar);

                    linhaChar = modoPosicao.charAt(split+2);

                    if(modo.equals("ABRIR")){

                        if(!Arrays.asList(colunaLetraArray).contains(coluna) ||
                                !linhaNumeroArray.contains(Character.toString(linhaChar)))
                            throw new InputMismatchException("entrada invalida");

                        else{

                            linha = Character.getNumericValue(linhaChar);

                            if(abrirPosicao(linha, coluna) == 1)
                                break;

                            else
                                imprimirTabela(false);
                        }

                    }

                    else if(modo.equals("MARCAR")){

                        if(!Arrays.asList(colunaLetraArray).contains(coluna) ||
                                !linhaNumeroArray.contains(Character.toString(linhaChar)))
                            throw new InputMismatchException("entrada invalida");

                        else{

                            linha = Character.getNumericValue(linhaChar);

                            marcarBandeira(linha, coluna);
                            imprimirTabela(false);

                        }

                    }

                    else if(modo.equals("DESMARCAR")){

                        if(!Arrays.asList(colunaLetraArray).contains(coluna) ||
                                !linhaNumeroArray.contains(Character.toString(linhaChar)))
                            throw new InputMismatchException("entrada invalida");

                        else{

                            linha = Character.getNumericValue(linhaChar);

                            desmarcarBandeira(linha, coluna);
                            imprimirTabela(false);

                        }

                    }

                    else
                        throw new InputMismatchException("entrada invalida");

                }

                sistemaSave(1);

            }
            catch (InputMismatchException ex){

                System.err.print("Entrada invalida, aperte ENTER para retornar");
                teclado.nextLine();

            }

        }
    }
}
