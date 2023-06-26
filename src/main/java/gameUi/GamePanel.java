package gameUi;

import gameCards.CardItem;
import gameCards.PlantCardEnum;
import gamePlants.GameItem;
import gamePlants.PeaShooter;
import gamePlants.SunItem;

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

    BufferedImage mainBg;
    List<SunItem> listaDeSois = new ArrayList<SunItem>();
    Iterator<SunItem> solItemIterator;
    Timer timer;
    Point mousePoint;
    Double mouseX, mouseY;
    List<CardItem> avaliablePlants; // board de plantas disponíveis para uso.
    int qntSois = 0; // Quantidade de sóis coletados (o sol é a moeda do jogo para obter plantas.)
    CardItem selected_plant;
    boolean isPlantSelected = false;

    public GamePanel(int windowWidth, int windowHeight){
        setPreferredSize(new Dimension(1012, 785));
        setLayout(null); // Opcional, dependendo do layout que você pretende usar no painel

        setBounds(0, 0, windowWidth, windowHeight);
        setVisible(true);

        // inicia o timer
        timer = new Timer(5000, e -> gerarSois());
        timer.start();

        avaliablePlants = new ArrayList<>(List.of(
                new CardItem(PlantCardEnum.CARD_SUNFLOWER),
                new CardItem(PlantCardEnum.CARD_PEASHOOTER)
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
                        System.out.println("Clicou");
                        qntSois+=25;
                        solItemIterator.remove();
                    }
                }

                // Verifica se usuário clicou para selecionar uma planta.
                for (CardItem plantItem : avaliablePlants){
                    if(plantItem.ItemWasClicked(e.getPoint())){
                        isPlantSelected = true;
                        selected_plant = plantItem;
                    }
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {

                if(isPlantSelected){

                }
            }

        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        //System.out.println("Exibindo Game Principal");

        startGame(g2);
        repaint();
    }

    public void startGame(Graphics2D graphics){
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
        drawSuns(graphics);
        drawNumberOfCollectedSuns(graphics);
    }

    // Desenha a planta seguindo o cursor do mouse caso o usuário selecionar uma planta.
    private void drawSelectedCard(Graphics2D graphics, MouseEvent e) {

        mousePoint = e.getPoint();
        SwingUtilities.convertPointToScreen(mousePoint, GamePanel.this); // Converte as coordenadas para a tela

        selected_plant.setX(mousePoint.x);
        selected_plant.setY(mousePoint.y);
        selected_plant.drawItem(graphics);

    }

    private void drawNumberOfCollectedSuns(Graphics2D graphics) {

        System.out.println("Desenhando string: " + qntSois);

        graphics.setColor(Color.BLACK);
        graphics.setStroke(new BasicStroke(2.5f));
        Font font = graphics.getFont().deriveFont( 20.0f );
        graphics.setFont( font );
        graphics.drawString(""+ qntSois, 45, 102);
    }

    public void gerarSois(){

        System.out.println("Gerando sois...");

        SunItem sunItem = new SunItem();
        int x = (int) (Math.random() * getWidth()); // Posição X aleatória dentro da largura do painel
        int y = 10; // Posição Y no topo do painel

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
            plantCard.drawItem(graphics2D);
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


}
