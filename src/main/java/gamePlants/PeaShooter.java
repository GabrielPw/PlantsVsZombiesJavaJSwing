package gamePlants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class PeaShooter extends GameItem{

    Image imageGif;
    List<PeaItem> peaShootList;

    public PeaShooter() {
        itemRect = new Rectangle(x + 5, y + 5, (int) (width - (0.2 * width)), (int) (height - (0.2 * height))); // multiplicando por 0.2 para diminuir 20%.
        sunPrice = 100;

        imageGif = new javax.swing.ImageIcon(this.getClass().getResource("/images/plants/peashooter.gif")).getImage();

        loadItemImage("/images/plants/peashooter_hd.gif");
        peaShootList = new ArrayList<>();
    }
    // Cria 'PeaItem' (tiro).

    public void atirar(){

        PeaItem peaItem = new PeaItem();
        peaItem.x = this.x;
        peaItem.y = this.y;

        peaShootList.add(peaItem);
    }

    public void drawPeaShoot(Graphics2D graphics){
        for (PeaItem pea : peaShootList){
            graphics.drawImage(pea.itemImage, x, y, width, height, null);
            pea.updatePeaPosition();
        }
    }

    @Override
    public void drawItem(Graphics2D graphics) {

        graphics.setStroke(new BasicStroke(2f));
        graphics.setColor(Color.RED);
        graphics.drawImage(imageGif, x, y, width, height, null);
    }

    public void drawGif(Graphics2D graphics){
        graphics.setStroke(new BasicStroke(2f));
        graphics.setColor(Color.RED);
        graphics.drawImage(imageGif, x, y, width, height, null);
    }
}
