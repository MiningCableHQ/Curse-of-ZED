package Main;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed, shiftPressed;
    public boolean ePressed;
    public boolean iPressed;
    public boolean cPressed;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W){ upPressed = true; }
        if(code == KeyEvent.VK_A){ leftPressed = true; }
        if(code == KeyEvent.VK_S){ downPressed = true; }
        if(code == KeyEvent.VK_D){ rightPressed = true; }
        if(code == KeyEvent.VK_SHIFT){ shiftPressed = true; }
        if(code == KeyEvent.VK_E){ ePressed = true; }
        if(code == KeyEvent.VK_I){ iPressed = true; }
        if(code == KeyEvent.VK_C){ cPressed = true; }
        if(code == KeyEvent.VK_ESCAPE){
            // ESC handled separately in panels
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W){ upPressed = false; }
        if(code == KeyEvent.VK_A){ leftPressed = false; }
        if(code == KeyEvent.VK_S){ downPressed = false; }
        if(code == KeyEvent.VK_D){ rightPressed = false; }
        if(code == KeyEvent.VK_SHIFT){ shiftPressed = false; }
        if(code == KeyEvent.VK_E){ ePressed = false; }
        if(code == KeyEvent.VK_I){ iPressed = false; }
        if(code == KeyEvent.VK_C){ cPressed = false; }
    }
}