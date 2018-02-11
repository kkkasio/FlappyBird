package com.kasioeduardo.flapybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FlappyBird extends ApplicationAdapter {
	private SpriteBatch batch; //CLASSE PARA CRIAR AS ANIMAÇÕES
    private Texture[] passaros;
    private Texture fundo;
    private int larguraDispositivo,alturaDispositivo;


    private float variacao = 0;
    private float velocidadeQueda =0;
    private float posicaoInicialVertical = 0;
	
	@Override
	public void create () {

	    batch   = new SpriteBatch();
        fundo   = new Texture("fundo.png");

        // imagens de animação do passaro.
	    passaros = new Texture[3];
	    passaros[0] = new Texture("passaro1.png");
	    passaros[1] = new Texture("passaro2.png");
	    passaros[2] = new Texture("passaro2.png");


	    larguraDispositivo = Gdx.graphics.getWidth();
	    alturaDispositivo  = Gdx.graphics.getHeight();
        posicaoInicialVertical = alturaDispositivo /2;
	}

	@Override
	public void render () {
	    //CHAMADO DE TEMPO EM TEMPO PARA CRIAR ANIMAÇÕES DENTRO DO JOGO

        variacao += Gdx.graphics.getDeltaTime() * 8; //getDeltaTime -> VARIAÇÃO ENTRE AS CHAMADAS DO RENDER
        velocidadeQueda++;

        if (posicaoInicialVertical > 0 ) posicaoInicialVertical -= velocidadeQueda; // o passaro não cai d[a tea~sd,LÇ


        if (variacao > 2) variacao = 0; // resetando o VETOR

        batch.begin(); //iniciando exibição das imgs
            /*
                ENTRE O BEGIN E O END, AS IMGS SÃO RENDERIZADAS PARA EXIBIÇAO DENTRO DA TELA DO APP.
                a ordem que desenhamos as imagens é importante, pois define quem vem 'por cima' de quem.
             */
        batch.draw(fundo,0,0, larguraDispositivo,alturaDispositivo); // ocupando o tamanho da tela com a imagem, idependente do tamanho do dispositivo.
        batch.draw(passaros[(int)variacao],200,posicaoInicialVertical);//configuração de onde o pássaro vai aparecer na tela, eixo X,Y


        batch.end();// finalizando a exibicao das imgs

	}
	
	/*@Override
	public void dispose () {
	}*/
}
