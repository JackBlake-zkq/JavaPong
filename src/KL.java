import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KL implements KeyListener{

    private boolean[] keyPressed = new boolean[360];

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyPressed[e.getKeyCode()] = true;
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyPressed[e.getKeyCode()] = false;
        
    }

    public boolean isKeyPressed(int keyCode){
        return keyPressed[keyCode];
    }
    
}
