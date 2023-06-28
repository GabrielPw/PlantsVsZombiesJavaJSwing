package gameZombies;

import gameUi.GamePanel;

import java.util.List;

public class ZombieAnimationThread extends Thread{

    private Zombie zombie;
    private GamePanel gamePanel;
    private volatile boolean isRunning;

    public ZombieAnimationThread(Zombie zombie, GamePanel gamePanel) {
        this.zombie = zombie;
        this.gamePanel = gamePanel;
        this.isRunning = true;
    }

    public void stopThread() {
        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning) {
            // Atualize a posição do zumbi e redesenhe o painel
            zombie.walk();
            zombie.animateBodyParts(List.of(5, 6, 7), 2,5); // Chame o método animateBodyPart para animar as partes do corpo do zumbi
            gamePanel.repaint();

            try {
                Thread.sleep(6000); // Aguarde um curto período de tempo entre cada atualização
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
