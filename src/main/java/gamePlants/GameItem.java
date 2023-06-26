package gamePlants;

import lombok.Getter;
import lombok.Setter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

@Getter
@Setter
public abstract class GameItem {
    protected Rectangle itemRect;
    protected int width = 72;
    protected int height = 72;
    protected int x, y;

    BufferedImage itemImage;
    BufferedImage itemImage_brightness_increased;

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
        itemRect = new Rectangle(x + 5, y + 5, (int) (width - (0.2 * width)), (int) (height - (0.2 * height))); // multiplicando por 0.2 para diminuir 20%.
        graphics.drawImage(itemImage, x, y, width, height, null);
        graphics.draw(itemRect);
    }


    public void drawItemWithBrightnessIncreased(Graphics2D graphics2D){

        // Cria uma cópia da imagem original para aplicar os efeitos
        BufferedImage transformedImage = new BufferedImage(this.itemImage.getWidth(), this.itemImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2dTransformed = transformedImage.createGraphics();

        // Transforma a imagem em preto e branco
        //ColorConvertOp grayscaleOp = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        //grayscaleOp.filter(originalImage, transformedImage);

        // Desenha a imagem original na imagem transformada
        g2dTransformed.drawImage(itemImage, 0, 0, null);

        // Aumenta o brilho da imagem
        RescaleOp brightnessOp = new RescaleOp(1.4f, 0, null);
        brightnessOp.filter(transformedImage, transformedImage);

        // Desenha a imagem transformada
        graphics2D.drawImage(transformedImage, x, y, width, height, null);

    }
}
