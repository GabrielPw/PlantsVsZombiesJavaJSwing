package gameUi;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {

    public JPanel mainJpanel;
    public GameWindow(){

        // Defina a largura e a altura desejadas para a janela
        int windowWidth = 1012;
        int windowHeight = 785;
        setSize(windowWidth, windowHeight);

        setTitle("Zombie vs Plants - JAVA Version.");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        // Layout e Panels.
        CardLayout cardLayout = new CardLayout(); // CardLayout para alternar entre telas (tela menu e tela do jogo principal (panel))

        mainJpanel = new JPanel(); // Panel principal onde ficará contido o cardLayout que segura a tela de menu e de jogo.
        mainJpanel.setLayout(cardLayout);

        GamePanel gamePanel = new GamePanel(windowWidth, windowHeight);
        MenuGame menuPanel = new MenuGame(windowWidth, windowHeight);

        mainJpanel.add(menuPanel, "menu");
        mainJpanel.add(gamePanel, "game");

        mainJpanel.setSize(windowWidth, windowHeight);
        mainJpanel.setVisible(true);
        getContentPane().add(mainJpanel);
        setVisible(true);
        setLocationRelativeTo(null);

        menuPanel.setPlayButtonListener(new PlayButtonListener() {
            @Override
            public void onPlayButtonClicked() {
                System.out.println("chamou o listener.");
                mainJpanel.remove(menuPanel);
                mainJpanel.revalidate();
                cardLayout.show(mainJpanel, "game");
                mainJpanel.repaint();

                gamePanel.startSunGeneratorTimer();
                //gamePanel.startGame();
            }
        });
    }


    public static void main(String[] args) {

        GameWindow window = new GameWindow();
    }
}
