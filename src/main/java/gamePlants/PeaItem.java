package gamePlants;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class PeaItem {

    int x, y;
    int speed;
    BufferedImage itemImage;

    public PeaItem(){
        speed = 4;
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

    public void updatePeaPosition(){
        this.x += speed;
    }
}
