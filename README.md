# Quiz.java

## Criando as janelas do quiz
No método `public void main()`,  
janela.adicionarTela(String): Adiciona nova tela com título  
janela.adicionarPergunta(String): Adiciona pergunta à última tela criada  
janela.adicionarOpcoes(...String): Recebe o array de perguntas para expor na última tela criada


## Validando as respostas

No método `public static int respostas(int[] resposta)`,  
resposta é o array que irá conter as opções selecionadas, o int retornado é a soma das respostas que estão corretas.



(resposta[0, 2, 1, 3, -1])


No caso resposta[1] == 2
Na segunda tela [1] (afinal arrays começam em 0), espera-se que a terceira resposta (novamente pois o array começa em 0) seja a correta.
```
        Tela
         ↓
resposta[1] == 2
               ↑ 
            Resposta
```

# IMPORTANTE!
* No método `public void main()`, o método `janela.adicionarRespostas()` cria a tela de resultado final, levando em consideração o retorno do método `public static int respostas(int[] resposta)`.
  * Lembrando que a ordem das telas e do adicionar resposta impacta na ordem exibida.
* Ao final do método `public void main()` deve existir o método `janela.start()`, qual desenha as telas.

# Exemplo

```
public void main() {
    janela.adicionarTela("Tela 0");  
    janela.adicionarPergunta("Quem foi o pai da computação?");
    janela.adicionarOpcoes("Ian Sommerville", "Alan Turing", "Fábio Akita", "Silvia Wanderley");

    janela.adicionarTela("Tela 1");  
    janela.adicionarPergunta("Qual era o nome da ");
    janela.adicionarOpcoes("Christopher", "Máquina de Turing", "Calculadora", "\"SmartPhone\"");

    janela.adicionarRespostas();
    janela.start();
}

public static int respostas(int[] resposta) {
    if(resposta[0] == 1)
    
    return 0;
}
```