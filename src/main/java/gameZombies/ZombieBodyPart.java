package gameZombies;

import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;

@Getter
@Setter
public class ZombieBodyPart {

    private float scaleDimension;
    private int x, y;
    private int width, height;
    private BufferedImage imageBodyPart;

    public ZombieBodyPart(BufferedImage imageBodyPart) {
        this.imageBodyPart = imageBodyPart;
        this.width = imageBodyPart.getWidth();
        this.height = imageBodyPart.getHeight();
    }

    public void setScaleDimension(float scaleDimension) {

        this.width *= scaleDimension;
        this.height *= scaleDimension;
    }

}
