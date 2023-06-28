package gameUi;

import gameCards.CardItem;
import gameCards.PlantCardEnum;
import gamePlants.GameItem;
import gamePlants.PeaShooter;
import gamePlants.SunFlower;
import gamePlants.SunItem;
import gameZombies.Zombie;
import gameZombies.ZombieAnimationThread;
import map.GameMapGrid;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GamePanel extends JPanel {
    Graphics2D graphics2D;
    GameMapGrid gameMapGrid = new GameMapGrid(40, 115, 102, 120, 5, 9);

    BufferedImage mainBg;
    List<SunItem> listaDeSois = new ArrayList<SunItem>();
    Rectangle clickedRect; // armazena retangulo da grid que foi clicado para plantar uma planta selecionada.
    Iterator<SunItem> solItemIterator;
    Timer timer;
    Point mousePoint;
    Double mouseX, mouseY;
    List<CardItem> avaliablePlants; // board de plantas disponíveis para uso.
    int qntSois = 0; // Quantidade de sóis coletados (o sol é a moeda do jogo para obter plantas.)
    CardItem selected_plant;
    boolean isPlantSelected = false;
    PeaShooter floatingSelectedPeashooter = new PeaShooter(); // Quando o usuário selecionar um card, uma pré-visualização da planta seguindo o mouse.
    SunFlower floatingSelectedSunFlower = new SunFlower();
    Zombie regularZombie = new Zombie();

    public GamePanel(int windowWidth, int windowHeight){


        setPreferredSize(new Dimension(1012, 785));
        setLayout(null); // Opcional, dependendo do layout que você pretende usar no painel

        setBounds(0, 0, windowWidth, windowHeight);
        setVisible(true);

        // inicia o timer
        timer = new Timer(3000, e -> gerarSois()); // 8000 --> 8s.

        avaliablePlants = new ArrayList<>(List.of(
                new CardItem(PlantCardEnum.CARD_SUNFLOWER, 50),
                new CardItem(PlantCardEnum.CARD_PEASHOOTER, 100)
        ));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                mousePoint = e.getPoint();
                SwingUtilities.convertPointToScreen(mousePoint, GamePanel.this); // Converte as coordenadas para a tela

                // verifica se usuário clicou em um sol (SunItem).
                solItemIterator = listaDeSois.iterator();
                while(solItemIterator.hasNext()){
                    SunItem sol = solItemIterator.next();
                    if(sol.ItemWasClicked(e.getPoint())){
                        //System.out.println("Clicou");
                        qntSois+=25;
                        solItemIterator.remove();
                        return;
                    }
                }

                // Verifica se usuário clicou para selecionar uma planta.
                for (CardItem plantItem : avaliablePlants){
                    // verificar se usuário tem sois o suficiente para selecionar planta.
                    if (qntSois >= plantItem.getCardSunPrice()) {
                        if (plantItem.ItemWasClicked(e.getPoint())) {
                            // verifica se usuário clicou para selecionar uma planta sendo que ele já estava com outra selecionada.
                            if (isPlantSelected){ // caso positivo, a operação de selecionar será cancelada.
                                isPlantSelected = false;
                            }else {
                                isPlantSelected = true;
                                selected_plant = plantItem;
                                System.out.println("Selecionou planta.");
                                break;              // sair do loop.
                            }
                            return;
                        }
                    }
                }

                // Verifica se usuário está plantando uma planta selecionada.
                if (isPlantSelected){
                    // coordenadas do retangulo clicado dentro da matriz de retangulos.
                    Point coordsOfClickedRectInMatrix = gameMapGrid.checkIfSomeRectangleWasClickedAndReturnCoordsInMatrix(e.getPoint(), graphics2D);
                    if (coordsOfClickedRectInMatrix != null){
                        GameItem itemToBePlanted = null;
                        switch (selected_plant.getCardType()){
                            case CARD_PEASHOOTER:
                                itemToBePlanted = new PeaShooter();
                                break;
                            case CARD_SUNFLOWER:
                                itemToBePlanted = new SunFlower();
                                
                        }
                        if (itemToBePlanted != null) {
                            gameMapGrid.plantItemAtGridSpace(coordsOfClickedRectInMatrix, itemToBePlanted);
                            qntSois-=itemToBePlanted.getSunPrice();
                            isPlantSelected = false;
                        }
                    }
                }
            }
        });


        regularZombie.setX(gameMapGrid.getRectMatrixGrid()[2][8].x); // linha 2 do grid.
        regularZombie.setY(gameMapGrid.getRectMatrixGrid()[2][2].y); // linha 2 do grid.
        regularZombie.ajustEachBodyPartCoordinates();

        ZombieAnimationThread zombieAnimationThread = new ZombieAnimationThread(regularZombie, this);
        zombieAnimationThread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        //System.out.println("Exibindo Game Principal");

        startGame(g2);
        repaint();
    }

    public void startGame(Graphics2D graphics){

        this.graphics2D = graphics;
        try {
            // Carrega a imagem de fundo do arquivo localizado na pasta "resources"
            mainBg = ImageIO.read(new File(getClass().getResource("/images/mainBG.png").toURI()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        graphics.drawImage(mainBg, 0, 0, getWidth(), getHeight(), this);
        drawPlantsCardBoard(graphics);
        drawNumberOfCollectedSuns(graphics);

        if(isPlantSelected){
            drawSelectedCard(graphics);
        }

        gameMapGrid.drawPlantedPlants(graphics);

        regularZombie.drawZombie(graphics);
        regularZombie.walk();
        regularZombie.drawZombieRect(graphics);
        if (gameMapGrid.getItemMatrixGrid()[2][0] != null) {
            System.out.println("Tem planta no [2][0].");
            regularZombie.checkZombieColissionWithSomePlant(gameMapGrid.getItemMatrixGrid()[2][0]);
        }
        drawSuns(graphics);
    }

    // Desenha a planta seguindo o cursor do mouse caso o usuário selecionar uma planta.
    private void drawSelectedCard(Graphics2D graphics) {

        Point mousePointInfo = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(mousePointInfo, GamePanel.this); // Convertendo as coordenadas para o painel

        switch (selected_plant.getCardType()){
            case CARD_PEASHOOTER:
                //System.out.println("Desenhar Planta: " + selected_plant.getCardType().name());

                floatingSelectedPeashooter.setX(mousePointInfo.x - (floatingSelectedPeashooter.getWidth() / 2));
                floatingSelectedPeashooter.setY(mousePointInfo.y - (floatingSelectedPeashooter.getHeight() / 2));
                gameMapGrid.checkIfMouseIsOverSomeRectangle(mousePointInfo, graphics);
                floatingSelectedPeashooter.drawItemWithBrightnessIncreased(graphics);
                //gameMapGrid.drawRectGrid(graphics, gameMapGrid.getNumberOfRows(), gameMapGrid.getNumberOfCols());
                break;
            case CARD_SUNFLOWER:
                //System.out.println("Desenhar Planta: " + selected_plant.getCardType().name());
                floatingSelectedSunFlower.setX(mousePointInfo.x - (floatingSelectedSunFlower.getWidth() / 2));
                floatingSelectedSunFlower.setY(mousePointInfo.y - (floatingSelectedSunFlower.getHeight() / 2));
                gameMapGrid.checkIfMouseIsOverSomeRectangle(mousePointInfo, graphics);
                floatingSelectedSunFlower.drawItemWithBrightnessIncreased(graphics);
                //gameMapGrid.drawRectGrid(graphics, gameMapGrid.getNumberOfRows(), gameMapGrid.getNumberOfCols());
                break;
        }
    }

    private void drawNumberOfCollectedSuns(Graphics2D graphics) {

        //System.out.println("Desenhando string: " + qntSois);

        graphics.setColor(Color.BLACK);
        graphics.setStroke(new BasicStroke(2.5f));
        Font font = graphics.getFont().deriveFont( 20.0f );
        graphics.setFont( font );
        graphics.drawString(""+ qntSois, 45, 102);
    }

    public void gerarSois(){

        System.out.println("Gerando sois...");

        SunItem sunItem = new SunItem();
        int x = (int) (Math.random() * getWidth() - sunItem.getWidth()); // Posição X aleatória dentro da largura do painel
        int y = -20; // Posição Y no topo do painel

        System.out.println("X: " + x + " - Y: " + y);
        sunItem.setPosition(x, y);
        listaDeSois.add(sunItem);
    }

    public void drawSuns(Graphics2D graphics){
        for (SunItem item: listaDeSois){
            item.drawItem(graphics);
            item.cair();
        }

        mousePoint = mousePoint = MouseInfo.getPointerInfo().getLocation();

        // Convertendo as coordenadas do mouse para coordenadas relativas ao componente MenuGame
        SwingUtilities.convertPointFromScreen(mousePoint, this);
        mouseX = mousePoint.getX();
        mouseY = mousePoint.getY();

        solItemIterator = listaDeSois.iterator();
        while (solItemIterator.hasNext()) {

            // Verificar se sol saiu da tela
            SunItem item = solItemIterator.next(); // must be called before you can call i.remove()
            if (item.getY() > getHeight() + item.getWidth()) {
                solItemIterator.remove();
            }
        }

        //System.out.println("Qnt sois: " + listaDeSois.size());
    }

    public void drawPlantsCardBoard(Graphics2D graphics2D){

        for (CardItem plantCard : avaliablePlants){

            plantCard.setX(120 + avaliablePlants.indexOf(plantCard) * plantCard.getWidth() + 5); // Definindo posição X de cada card de acordo com seu index na lista de cards.
            plantCard.setY(10);

            if (qntSois >= plantCard.getCardSunPrice()) {
                plantCard.drawItem(graphics2D);
            } else {
                plantCard.drawItemInGrayscaleMode(graphics2D);
            }
        }
    }

    public void SelectAPlant(CardItem plantCard, Graphics2D graphics){

        switch (plantCard.getCardType()){
            case CARD_PEASHOOTER:

                PeaShooter peaShooter = new PeaShooter();
                //peaShooter.setX();
                peaShooter.drawItem(graphics);
                break;
            case CARD_SUNFLOWER:

                break;
        }
    }

    public void startSunGeneratorTimer() {
        timer.start();
    }

}
