package gameZombies;

import gamePlants.GameItem;
import lombok.Getter;
import lombok.Setter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Getter
@Setter
public class Zombie {

    float imageScale = 1.f; // will be use to increase or decrease image dimension.
    private int x, y;
    private int width, height;
    private Rectangle rect;
    int speed;
    private int health;
    private BufferedImage zombieSpriteSheetImage;
    private ZombieBodyPart bodyParts[] = new ZombieBodyPart[8];

    public Zombie(){

        loadZombieSpriteSheetImage("/images/spritesheets/zombies/zombie.png");

        BufferedImage zombieHeadPart = zombieSpriteSheetImage.getSubimage(1, 9, 53, 48);
        BufferedImage zombieJawPart = zombieSpriteSheetImage.getSubimage(1, 86, 32,15);
        BufferedImage zombieBodyPart = zombieSpriteSheetImage.getSubimage(1, 162, 53,63);
        BufferedImage zombieUpRightLegPart = zombieSpriteSheetImage.getSubimage(91, 340, 15, 26);
        BufferedImage zombieBottomRightLegPart = zombieSpriteSheetImage.getSubimage(107, 340, 32, 36);
        BufferedImage zombieUpLeftLegPart = zombieSpriteSheetImage.getSubimage(1, 340, 21, 39);
        BufferedImage zombieBottomLeftLegPart = zombieSpriteSheetImage.getSubimage(23, 340, 24, 30);
        BufferedImage zombieBottomLeftShoePart = zombieSpriteSheetImage.getSubimage(48, 340, 42,21);

        bodyParts[0] = new ZombieBodyPart(zombieHeadPart);
        bodyParts[1] = new ZombieBodyPart(zombieJawPart);
        bodyParts[2] = new ZombieBodyPart(zombieBodyPart);
        bodyParts[3] = new ZombieBodyPart(zombieUpRightLegPart);
        bodyParts[4] = new ZombieBodyPart(zombieBottomRightLegPart);
        bodyParts[5] = new ZombieBodyPart(zombieUpLeftLegPart);
        bodyParts[6] = new ZombieBodyPart(zombieBottomLeftLegPart);
        bodyParts[7] = new ZombieBodyPart(zombieBottomLeftShoePart);

        reScaleZombieSize(0.88f);
        health = 100;
        speed = 3;
        rect = new Rectangle(x, y, 0, 0);
    }

    public void walk(){
        this.x -= speed;
        ajustEachBodyPartCoordinates();

        this.rect.x = bodyParts[0].getX() + ((int) (bodyParts[0].getWidth() * 0.2));
        this.rect.y = y;
    }
    public void attack(){}

    public void drawZombie(Graphics2D graphics){

        //System.out.println("Desenhando zombie X: " + x + " | Y: " + y);
        // Desenhando cada parte do corpo do zumbi.
        graphics.drawImage(bodyParts[0].getImageBodyPart(), bodyParts[0].getX(), bodyParts[0].getY(), bodyParts[0].getWidth(), bodyParts[0].getHeight(), null);
        graphics.drawImage(bodyParts[1].getImageBodyPart(), bodyParts[1].getX(), bodyParts[1].getY(), bodyParts[1].getWidth(), bodyParts[1].getHeight(), null);
        graphics.drawImage(bodyParts[3].getImageBodyPart(), bodyParts[3].getX(), bodyParts[3].getY(), bodyParts[3].getWidth(), bodyParts[3].getHeight(), null);
        graphics.drawImage(bodyParts[5].getImageBodyPart(), bodyParts[5].getX(), bodyParts[5].getY(), bodyParts[5].getWidth(), bodyParts[5].getHeight(), null);
        graphics.drawImage(bodyParts[2].getImageBodyPart(), bodyParts[2].getX(), bodyParts[2].getY(), bodyParts[2].getWidth(), bodyParts[2].getHeight(), null); // desenhando o index 2 depois de desenhar o 3 para que o corpo fique na frente da perna.
        graphics.drawImage(bodyParts[4].getImageBodyPart(), bodyParts[4].getX(), bodyParts[4].getY(), bodyParts[4].getWidth(), bodyParts[4].getHeight(), null);
        graphics.drawImage(bodyParts[6].getImageBodyPart(), bodyParts[6].getX(), bodyParts[6].getY(), bodyParts[6].getWidth(), bodyParts[6].getHeight(), null);
        graphics.drawImage(bodyParts[7].getImageBodyPart(), bodyParts[7].getX(), bodyParts[7].getY(), bodyParts[7].getWidth(), bodyParts[7].getHeight(), null);


    }

    public void loadZombieSpriteSheetImage(String imagePath){
        try {
            // Carrega a imagem de fundo do arquivo localizado na pasta "resources"
            zombieSpriteSheetImage = ImageIO.read(new File(getClass().getResource(imagePath).toURI()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void ajustEachBodyPartCoordinates(){

        bodyParts[0].setX(x);
        bodyParts[0].setY(y);

        bodyParts[1].setX(x + 10);
        bodyParts[1].setY(y + bodyParts[0].getImageBodyPart().getHeight() - 12);

        bodyParts[2].setX(bodyParts[1].getX() + 16);
        bodyParts[2].setY(bodyParts[1].getY() + bodyParts[1].getHeight() - 20);

        bodyParts[3].setX(bodyParts[2].getX() + bodyParts[3].getWidth() + 5);
        bodyParts[3].setY(bodyParts[2].getY() + bodyParts[2].getHeight() - 5);

        bodyParts[4].setX(bodyParts[3].getX() - bodyParts[3].getWidth());
        bodyParts[4].setY(bodyParts[3].getY() + bodyParts[3].getHeight() - 10);

        bodyParts[5].setX(bodyParts[2].getX() + bodyParts[2].getWidth() - bodyParts[5].getWidth());
        bodyParts[5].setY(bodyParts[2].getY() + bodyParts[2].getHeight() - 20);

        bodyParts[6].setX(bodyParts[5].getX() + bodyParts[5].getWidth() - (bodyParts[6].getWidth() / 2) - 2);
        bodyParts[6].setY(bodyParts[5].getY() + bodyParts[5].getHeight() - 8);

        bodyParts[7].setX(bodyParts[6].getX() - (bodyParts[7].getWidth() / 2) + 2);
        bodyParts[7].setY(bodyParts[6].getY() + bodyParts[6].getHeight() - 5);

        // ajustando retangulo de colisão do zombie.
        rect.height = (bodyParts[7].getY() + bodyParts[7].getHeight()) - bodyParts[0].getY(); // altura definida do espaço entre a cabeça[0] e os pés[7].
        rect.width = (bodyParts[7].getX() + bodyParts[7].getWidth()) - bodyParts[0].getX() - ((int) (bodyParts[0].getWidth() * 0.2)); // multipliquei por 0.2 para diminuir a largura em 20%.
    }

    public void reScaleZombieSize(float imageScale){

        int size = bodyParts.length;
        for (int i = 0; i < size; i++){
            bodyParts[i].setScaleDimension(imageScale);
        }
    }

    public void checkZombieColissionWithSomePlant(GameItem plant){

        if (rect.intersects(plant.getItemRect())){

            speed = 0;
        }
    }

    public void drawZombieRect(Graphics2D graphics){

        graphics.setStroke(new BasicStroke(2f));
        graphics.setColor(Color.RED);
        graphics.draw(rect);
    }
    public void animateBodyParts(List<Integer> bodyPartIndices, int amplitude, int period) {
        int offsetX = 0;

        while (true) {
            for (int i = 0; i < bodyPartIndices.size(); i++) {
                int bodyPartIndex = bodyPartIndices.get(i);
                ZombieBodyPart bodyPart = bodyParts[bodyPartIndex];
                int startY = bodyPart.getY();

                // Calcula a posição Y com base no tempo e na amplitude
                int newY = startY + (int) (amplitude * Math.sin(offsetX * 2 * Math.PI / period));

                // Atualiza a posição Y da parte do corpo
                bodyPart.setY(newY);
            }

            // Atualiza as coordenadas de todas as partes do corpo
            //ajustEachBodyPartCoordinates();

            // Incrementa o deslocamento horizontal
            offsetX++;

            // Aguarda um pequeno intervalo de tempo para suavizar a animação
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
