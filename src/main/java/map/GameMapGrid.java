package map;

import gamePlants.GameItem;
import gamePlants.PeaShooter;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class GameMapGrid {

    //List<Rectangle> rectGridList = new ArrayList<>();

    private Rectangle rectMatrixGrid[][];
    private GameItem itemMatrixGrid[][];
    Rectangle rect;
    private int numberOfRows, numberOfCols;

    public GameMapGrid(int x, int y, int width, int height, int numberOfRows, int numberOfCols){

        this.numberOfRows = numberOfRows;
        this.numberOfCols = numberOfCols;


        // Declarando matriz de retângulos.
        rectMatrixGrid = new Rectangle[numberOfRows][numberOfCols];
        itemMatrixGrid = new GameItem[numberOfRows][numberOfCols];

        for (int i = 0; i < numberOfRows; i++) {
            // Cria Colunas.
            for (int j = 0; j < numberOfCols; j++) {
                Rectangle newRect = new Rectangle();
                newRect.x = x + (width * j);
                newRect.y = y + (height * i);

                if (i >1) {

                    newRect.height = height + 15;
                }else {
                    newRect.height = height;
                }

                if (i > 2){
                    if (i > 3){
                        newRect.y = y + (height * i) + 30;
                    }else {
                        newRect.y = y + (height * i) + 15;
                    }
                }

                newRect.width = width;

                rectMatrixGrid[i][j] = newRect;
            }
        }

    }

    public void drawRectGrid(Graphics2D graphics, int numberOfRows, int numberOfCols){

        graphics.setStroke(new BasicStroke(4f));
        graphics.setColor(Color.RED);

        //System.out.println("Desenhando GRID.");
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfCols; j++) {
                rect = rectMatrixGrid[i][j];

                //System.out.println("RectX: " + rect.x + " | RectY: " + rect.y);
                graphics.draw(rect); //rect.x, rect.y, rect.width, rect.height
            }
        }
    }

    // Checa se cursor do mouse está sobre algum retangulo do grid. caso positivo, desenha a área clicável.
    public void checkIfMouseIsOverSomeRectangle(Point mousePointInfo, Graphics2D graphics){
        //System.out.println("MouseX: " + mousePointInfo.x + " | MouseY: " + mousePointInfo.y);
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfCols; j++) {
                Rectangle rect = rectMatrixGrid[i][j];

                if(rect.contains(mousePointInfo.x, mousePointInfo.y)) {
                    //System.out.println("Contem");
                    graphics.setColor(new Color(255, 255, 255, 105));
                    graphics.fillRect(rect.x, rect.y, rect.width, rect.height);
                }
            }
        }
    }

    public Point checkIfSomeRectangleWasClickedAndReturnCoordsInMatrix(Point mousePointInfo, Graphics2D graphics){
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfCols; j++) {

                if(rectMatrixGrid[i][j].contains(mousePointInfo.getX(), mousePointInfo.getY())) {
                    return new Point(i, j);
                }

            }
        }
        return null;
    }

    public void plantItemAtGridSpace(Point rectCoordsInGridSpace, GameItem itemToBePlanted){

        int rect_row_coord = rectCoordsInGridSpace.x;
        int rect_col_coord = rectCoordsInGridSpace.y;

        Rectangle clickedRectangle = rectMatrixGrid[rect_row_coord][rect_col_coord];

        itemToBePlanted.setX(clickedRectangle.x + (clickedRectangle.width / 2) - (itemToBePlanted.getWidth() / 2));
        itemToBePlanted.setY(clickedRectangle.y + (clickedRectangle.height / 2) - (itemToBePlanted.getHeight() / 2));

        itemToBePlanted.setPlantEnabled(true);

        // adicionar planta á matriz de itens.
        itemMatrixGrid[rect_row_coord][rect_col_coord] = itemToBePlanted;

        System.out.println("Item plantado com sucesso");
    }

    public void drawPlantedPlants(Graphics2D graphics) {

        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfCols; j++) {

                if (itemMatrixGrid[i][j] != null){

                    if(itemMatrixGrid[i][j] instanceof PeaShooter){
                        System.out.println("PeaShooter");
                        ((PeaShooter) itemMatrixGrid[i][j]).drawGif(graphics);

                    }else
                    {
                        //System.out.println("PlantedPant X: " +itemMatrixGrid[i][j].getX() + "PlantedPant Y: " + itemMatrixGrid[i][j].getY());
                        itemMatrixGrid[i][j].drawItem(graphics);
                    }
                }
            }
        }
    }
}
