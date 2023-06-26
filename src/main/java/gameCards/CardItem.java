package gameCards;

import lombok.Getter;
import lombok.Setter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

@Getter
@Setter
public class CardItem {
    private Rectangle itemRect;
    private PlantCardEnum cardType;
    private int cardSunPrice; // preço do card (medido em sois).
    private int width = 100;
    private int height = 90;
    private int x, y;

    BufferedImage itemImage;

    public CardItem(PlantCardEnum cardType){
        this.cardType = cardType;
        loadItemImage(cardType.getImagePath());
    }

    public void loadItemImage(String imagePath){
        try {
            // Carrega a imagem de fundo do arquivo localizado na pasta "resources"
            itemImage = ImageIO.read(new File(getClass().getResource(imagePath).toURI()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void drawItem(Graphics2D graphics){

        graphics.setStroke(new BasicStroke(2f));
        graphics.setColor(Color.RED);
        itemRect = new Rectangle(x + 5, y + 5, width, height); // multiplicando por 0.2 para diminuir 20%.
        graphics.drawImage(itemImage, x, y, width, height, null);
        graphics.draw(itemRect);
    }

    public boolean ItemWasClicked(Point mousePoint){

        if (itemRect.contains(mousePoint.getX(), mousePoint.getY())){
            //System.out.println("clicou");
            return true;
        }
        return false;
    }
}
