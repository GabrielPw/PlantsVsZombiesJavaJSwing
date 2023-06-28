package gamePlants;

import gameCards.CardItem;

import java.awt.*;

public class SunFlower extends GameItem {

    public SunFlower(){
        itemRect = new Rectangle(x + 5, y + 5, (int) (width - (0.2 * width)), (int) (height - (0.2 * height))); // multiplicando por 0.2 para diminuir 20%.
        sunPrice = 50;
        loadItemImage("/images/plants/sunflower.gif");
    }

    public void gerarSois(){

    }
}
