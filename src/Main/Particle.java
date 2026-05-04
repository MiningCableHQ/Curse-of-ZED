package Main;

import java.awt.*;

// ═════════════════════════════════════════════════════════════
//  Particle
// ═════════════════════════════════════════════════════════════
public class Particle {
    float x, y, dx, dy, life, maxLife;
    Color color;
    Particle(float x, float y, float dx, float dy, Color c, float life) {
        this.x=x; this.y=y; this.dx=dx; this.dy=dy;
        this.color=c; this.life=life; this.maxLife=life;
    }
    void update() { x+=dx; y+=dy; life--; }
    boolean alive() { return life>0; }
}
