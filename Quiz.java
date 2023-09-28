public class Quiz {

    UI janela;

    public Quiz() {
        janela = UI.getInstance();
        main();
    }

    public void main() {
        janela.adicionarTela("Tela 0");
        janela.adicionarPergunta("QUESTÃO");
        janela.adicionarOpcoes("RESPOSTA 1", "RESPOSTA 2", "RESPOSTA 3", "resposta 4");

        janela.start();
    }

    public static int respostas(int[] resposta) {
        int resultado = 0;

        if (resposta[0] == 1)
            resultado++;

        return resultado;
    }

}
