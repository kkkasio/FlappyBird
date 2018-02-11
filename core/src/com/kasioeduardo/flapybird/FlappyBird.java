package com.kasioeduardo.flapybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
	private SpriteBatch batch; //CLASSE PARA CRIAR AS ANIMAÇÕES
    private Texture[] passaros;
    private Texture fundo;
    private Texture canoBaixo;
    private Texture canoTopo;
    private int larguraDispositivo,alturaDispositivo;

    private Random numeroRandomico;


    private float variacao = 0;
    private float velocidadeQueda =0;
    private float posicaoInicialVertical = 0;
    private float espacoEntreCanos;
    private float posicaoMovimentoCanoHorizontal;
    private float deltaTime;
    private float alturaEntreCanosRandomica;
	
	@Override
	public void create () {

	    batch   = new SpriteBatch();
        fundo   = new Texture("fundo.png");

        //textura dos canos
        canoBaixo = new Texture("cano_baixo.png");
        canoTopo  = new Texture("cano_topo.png");

        // imagens de animação do passaro.
	    passaros = new Texture[3];
	    passaros[0] = new Texture("passaro1.png");
	    passaros[1] = new Texture("passaro2.png");
	    passaros[2] = new Texture("passaro2.png");


	    larguraDispositivo = Gdx.graphics.getWidth();
	    alturaDispositivo  = Gdx.graphics.getHeight();
        posicaoInicialVertical = alturaDispositivo /2;

        numeroRandomico = new Random();

        posicaoMovimentoCanoHorizontal = larguraDispositivo -100;
        espacoEntreCanos = 300;
	}

	@Override
	public void render () {
	    deltaTime = Gdx.graphics.getDeltaTime(); //getDeltaTime -> VARIAÇÃO ENTRE AS CHAMADAS DO RENDER
	    //CHAMADO DE TEMPO EM TEMPO PARA CRIAR ANIMAÇÕES DENTRO DO JOGO

        variacao += deltaTime * 8;
        if (variacao > 2) variacao = 0; // resetando o VETOR da imagem do pássaro.

        velocidadeQueda++;//velocidade de queda deve aumentar para que o passaro tenha o efeito de queda.
        if(Gdx.input.justTouched()){
            // quando a tela for tocada o pássaro deve SALTAR! (SUBIR)
            velocidadeQueda = -20; // como estamos aumentando a veloc de queda, quando a tela for pressionada esta velocidade tem que ser subtraida, assim o passaro irá saltar.
        }
        if (posicaoInicialVertical > 0 ||velocidadeQueda < 0) posicaoInicialVertical -= velocidadeQueda; // quando passaro cai ele fica no fim e náo passa para baixo da tela.

        posicaoMovimentoCanoHorizontal -= deltaTime * 200; // fazendo os canos andar para a esquerda na tela

        //verifica se o cano saiu inteiramente da tela para aparecer outro cano.
        if (posicaoMovimentoCanoHorizontal < -canoTopo.getWidth()+2) {
            posicaoMovimentoCanoHorizontal = larguraDispositivo;//volta o cano para o inicio da tela na direita.
            // cada vez que o cano sai da tela ele deve ter uma NOVA ALTURA;
            alturaEntreCanosRandomica = numeroRandomico.nextInt(400)-200; // gerando a altura POSITIVA e NEGATIVA
            Gdx.app.log("Espaço Randomico: ", "" +alturaEntreCanosRandomica);
        }



        batch.begin(); //iniciando exibição das imgs
            /*
                ENTRE O BEGIN E O END, AS IMGS SÃO RENDERIZADAS PARA EXIBIÇAO DENTRO DA TELA DO APP.
                a ordem que desenhamos as imagens é importante, pois define quem vem 'por cima' de quem.
             */
        batch.draw(fundo,0,0, larguraDispositivo,alturaDispositivo); // ocupando o tamanho da tela com a imagem, idependente do tamanho do dispositivo.
        batch.draw(canoTopo, posicaoMovimentoCanoHorizontal,alturaDispositivo /2 + espacoEntreCanos /2 + alturaEntreCanosRandomica); //
        batch.draw(canoBaixo, posicaoMovimentoCanoHorizontal, alturaDispositivo /2 - canoBaixo.getHeight() - espacoEntreCanos /2 + alturaEntreCanosRandomica); //
        batch.draw(passaros[(int)variacao],200,posicaoInicialVertical);//configuração de onde o pássaro vai aparecer na tela, eixo X,Y


        batch.end();// finalizando a exibicao das imgs

	}
	
	/*@Override
	public void dispose () {
	}*/
}
