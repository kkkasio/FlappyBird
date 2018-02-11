package com.kasioeduardo.flapybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
	private SpriteBatch batch; //CLASSE PARA CRIAR AS ANIMAÇÕES
    private Texture[] passaros;
    private Texture fundo;
    private Texture canoBaixo;
    private Texture canoTopo;
    private Random numeroRandomico;
    private BitmapFont fonte;
    private Circle  passaroCirculo;
    private Rectangle recCanoTopo;
    private Rectangle recCanoBaixo;
    private ShapeRenderer shape;

    //variáveis de configuração
    private float variacao = 0;
    private float velocidadeQueda =0;
    private float posicaoInicialVertical = 0;
    private float espacoEntreCanos;
    private float posicaoMovimentoCanoHorizontal;
    private float deltaTime;
    private float alturaEntreCanosRandomica;
    private int larguraDispositivo,alturaDispositivo;
    private int pontuacao =7;
    private boolean marcouPonto = false;
    private int estadoJogo =0;// estado 0 jogo nao iniciado ; estado 1 jogo inicado.

    @Override
	public void create () {

        batch   = new SpriteBatch();
        fundo   = new Texture("fundo.png");

        passaroCirculo = new Circle();
        recCanoBaixo   = new Rectangle();
        recCanoTopo    = new Rectangle();
        shape          = new ShapeRenderer();

        // configurações fonte
        fonte = new BitmapFont();
        fonte.setColor(Color.WHITE); // cor fonte
        fonte.getData().setScale(8); // tamanho fonte


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


        if (estadoJogo== 0){// não iniciado.
            if (Gdx.input.justTouched()){
                estadoJogo = 1; // jogo iniciado.
            }
        }else{
            velocidadeQueda++;//velocidade de queda deve aumentar para que o passaro tenha o efeito de queda.
            if(Gdx.input.justTouched()){
                // quando a tela for tocada o pássaro deve SALTAR! (SUBIR)
                velocidadeQueda = -18; // como estamos aumentando a veloc de queda, quando a tela for pressionada esta velocidade tem que ser subtraida, assim o passaro irá saltar.
            }
            if (posicaoInicialVertical > 0 || velocidadeQueda < 0){
                posicaoInicialVertical -= velocidadeQueda; // quando passaro cai ele fica no fim e náo passa para baixo da tela.
            }

            posicaoMovimentoCanoHorizontal -= deltaTime * 200; // fazendo os canos andar para a esquerda na tela

            //verifica se o cano saiu inteiramente da tela para aparecer outro cano.
            if (posicaoMovimentoCanoHorizontal < -canoTopo.getWidth()) {
                posicaoMovimentoCanoHorizontal = larguraDispositivo;//volta o cano para o inicio da tela na direita.
                // cada vez que o cano sai da tela ele deve ter uma NOVA ALTURA;
                alturaEntreCanosRandomica = numeroRandomico.nextInt(400)-200; // gerando a altura POSITIVA e NEGATIVA
                marcouPonto = false;
            }

            //verificar se o cano passou do passaro
            if (posicaoMovimentoCanoHorizontal < 120){
                if (!marcouPonto) {
                    marcouPonto = true;
                    pontuacao++;
                }
            }
        }

        batch.begin(); //iniciando exibição das imgs
            /*
                ENTRE O BEGIN E O END, AS IMGS SÃO RENDERIZADAS PARA EXIBIÇAO DENTRO DA TELA DO APP.
                a ordem que desenhamos as imagens é importante, pois define quem vem 'por cima' de quem.
             */
        batch.draw(fundo,0,0, larguraDispositivo,alturaDispositivo); // ocupando o tamanho da tela com a imagem, idependente do tamanho do dispositivo.
        batch.draw(canoTopo, posicaoMovimentoCanoHorizontal ,alturaDispositivo /2 + espacoEntreCanos /2 + alturaEntreCanosRandomica); //
        batch.draw(canoBaixo, posicaoMovimentoCanoHorizontal , alturaDispositivo /2 - canoBaixo.getHeight() - espacoEntreCanos /2 + alturaEntreCanosRandomica); //
        batch.draw(passaros[(int)variacao],200,posicaoInicialVertical);//configuração de onde o pássaro vai aparecer na tela, eixo X,Y
        fonte.draw(batch,String.valueOf(pontuacao), (pontuacao < 10 ) ? (larguraDispositivo/2)-10 : (larguraDispositivo/2)-70 , alturaDispositivo - 50); // exibindo a pontuação com Bitmap
        batch.end();// finalizando a exibicao das imgs

        passaroCirculo.set(200 + passaros[0].getWidth() / 2, posicaoInicialVertical + passaros[0].getHeight() /2, passaros[0].getWidth()/2); // circulo de colisão do pássaro
        recCanoBaixo = new Rectangle(posicaoMovimentoCanoHorizontal,alturaDispositivo/2 - canoBaixo.getHeight() - espacoEntreCanos / 2 + alturaEntreCanosRandomica,canoBaixo.getWidth(),canoBaixo.getHeight()); // retangulo do cano de baixo.
        recCanoTopo  = new Rectangle(posicaoMovimentoCanoHorizontal, alturaDispositivo /2 + espacoEntreCanos /2 + alturaEntreCanosRandomica , canoTopo.getWidth(), canoTopo.getHeight()); //retangulo do cano de cima.

        // DESENHANDO AS FORMAS
        shape.begin(ShapeRenderer.ShapeType.Filled);

        shape.circle(passaroCirculo.x,passaroCirculo.y,passaroCirculo.radius);
        shape.rect(recCanoBaixo.x,recCanoBaixo.y,recCanoBaixo.width,recCanoBaixo.height );
        shape.rect(recCanoTopo.x,recCanoTopo.y,recCanoTopo.width,recCanoTopo.height);
        shape.setColor(Color.RED);
        shape.end();


	}
	
	/*@Override
	public void dispose () {
	}*/
}
