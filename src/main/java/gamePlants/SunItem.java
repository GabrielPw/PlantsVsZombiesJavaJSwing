package gamePlants;

import java.awt.*;


public class SunItem extends GameItem{

    private int speed = 3;

    public SunItem(){

        loadItemImage("/images/sun.png");
    }


    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void cair(){
        this.y+=this.speed;
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
