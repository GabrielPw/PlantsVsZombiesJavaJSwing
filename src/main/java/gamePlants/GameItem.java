package gamePlants;

import lombok.Getter;
import lombok.Setter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

@Getter
@Setter
public abstract class GameItem {
    protected int sunPrice;
    protected Rectangle itemRect;
    protected int width = 72;
    protected int height = 72;
    protected int x, y;

    BufferedImage itemImage;
    BufferedImage itemImage_brightness_increased;
    private boolean isPlantEnabled = false; // verifica se planta foi plantada.


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
        graphics.drawImage(itemImage, x, y, width, height, null);
        //graphics.draw(itemRect);
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

    public void drawItemInGrayscaleMode(Graphics2D graphics2D){
        // Cria uma cópia da imagem original para aplicar os efeitos
        BufferedImage transformedImage = new BufferedImage(this.itemImage.getWidth(), this.itemImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2dTransformed = transformedImage.createGraphics();

         //Transforma a imagem em preto e branco
        ColorConvertOp grayscaleOp = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        grayscaleOp.filter(itemImage, transformedImage);

        // Desenha a imagem original na imagem transformada
        g2dTransformed.drawImage(itemImage, 0, 0, null);
        // Desenha a imagem transformada
        graphics2D.drawImage(transformedImage, x, y, width, height, null);

    }

    public void setX(int x) {
        this.x = x;
        this.itemRect.x = x + 5;
    }

    public void setY(int y) {
        this.y = y;
        this.itemRect.y = y + 5;
    }


}
