package gamePlants;

import java.awt.*;


public class SunItem extends GameItem{

    private int speed = 2;
    public SunItem(){

        loadItemImage("/images/sun.png");
        itemRect = new Rectangle(x + 5, y + 5, (int) (width - (0.2 * width)), (int) (height - (0.2 * height))); // multiplicando por 0.2 para diminuir 20%.
    }


    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void cair(){
        this.y+=this.speed;
        updateRectCoords();
    }

    public boolean ItemWasClicked(Point mousePoint){

        if (itemRect.contains(mousePoint.getX(), mousePoint.getY())){
            System.out.println("clicou");
            return true;
        }
        return false;
    }

    public void updateRectCoords(){

        this.itemRect.x = x + 5;
        this.itemRect.y = y + 5;
    }

}
