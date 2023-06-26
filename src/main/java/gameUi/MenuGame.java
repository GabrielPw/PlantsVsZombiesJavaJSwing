package gameUi;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class MenuGame extends JPanel {

    BufferedImage backgroundMenuImage;
    BufferedImage backgroundMenuImage_play_selected;
    Rectangle buttonArea;
    AffineTransform transform;
    Shape rotatedRect;
    Boolean isMouseOverButton = false;
    Point mousePoint;
    Double mouseX, mouseY;
    boolean clicouEmJogar = false;
    private PlayButtonListener playButtonListener;
    public MenuGame(int windowWidth, int windowHeight){

        setBounds(0, 0, windowWidth, windowHeight);
        setVisible(true);

        try {
            // Carrega a imagem de fundo do arquivo localizado na pasta "resources"
            backgroundMenuImage = ImageIO.read(new File(getClass().getResource("/images/menu.JPG").toURI()));
            backgroundMenuImage_play_selected = ImageIO.read(new File(getClass().getResource("/images/menu_play_selected.JPG").toURI()));

            // Define a área do botão na imagem (exemplo)
            int buttonX = 522; // Coordenada X do canto superior esquerdo do botão
            int buttonY = 118; // Coordenada Y do canto superior esquerdo do botão
            int buttonWidth = 406; // Largura do botão
            int buttonHeight = 115; // Altura do botão
            buttonArea = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);

            // Adiciona um listener de mouse para detectar cliques na área do botão
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Verifica se o clique do mouse ocorreu dentro da área do botão
                    if (buttonArea.contains(e.getX(), e.getY())) {
                        // Ação a ser executada quando o botão é clicado
                        //System.out.println("Botão clicado!");
                        clicouEmJogar = true;
                        System.out.println("clicou no retangulo");

                        playButtonListener.onPlayButtonClicked();
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        //System.out.println("Exibindo menu.");

        mousePoint = MouseInfo.getPointerInfo().getLocation();

        // Convertendo as coordenadas do mouse para coordenadas relativas ao componente MenuGame
        SwingUtilities.convertPointFromScreen(mousePoint, this);
        mouseX = mousePoint.getX();
        mouseY = mousePoint.getY();

        if (backgroundMenuImage != null) {

            // Desenha a imagem de fundo no painel
            if(buttonArea.contains(mouseX, mouseY)){ // verifica se coordenada do mouse está dentro da área do retângulo.
                g2.drawImage(backgroundMenuImage_play_selected, 0, 0, getWidth(), getHeight(), this);
                System.out.println("Contido no retangulo");
            }else {
                g2.drawImage(backgroundMenuImage, 0, 0, getWidth(), getHeight(), this);
            }
        }

        // Rotaciona o retângulo para se ajustar à inclinação do botão presente na imagem de fundo.
        transform = AffineTransform.getRotateInstance(Math.toRadians(8), buttonArea.getCenterX(), buttonArea.getCenterY());
        rotatedRect = transform.createTransformedShape(buttonArea);

        // Retângulo que demarca a área de clique.
        //g2.setColor(Color.RED);
        //g2.setStroke(new BasicStroke(2f));
        //g2.draw(rotatedRect);

        repaint();
    }
    public void setPlayButtonListener(PlayButtonListener listener) {
        playButtonListener = listener;
    }

}
