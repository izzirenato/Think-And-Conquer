package trivia;

import javax.swing.*;
import gameSetup.UIStyleUtils;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Componente personalizzato che mostra un timer circolare con countdown
 * Ogni secondo, un cerchio completo appare e scompare
 */
public class CircularTimer extends JPanel
{
    private int _timeLeft;
    private Color _normalColor = UIStyleUtils.GOLDEN_COLOR; // Oro
    private Color _warningColor = new Color(255, 0, 0);  // Rosso
    private int _warningThreshold = 5; // Secondi
    private Font _timerFont;
    private float _opacity = 1.0f;
    
    // Variabili per l'animazione
    private float _animationProgress = 0.0f; // 0.0 - 1.0
    private boolean _isAnimating = false;
    private javax.swing.Timer _animationTimer;
    private static final int ANIMATION_DURATION = 1000; // 1 secondo per l'animazione completa
    private static final int ANIMATION_FPS = 60; // Frame per secondo
    private static final int ANIMATION_DELAY = 1000 / ANIMATION_FPS;

    public CircularTimer(int maxTime) {
        _timeLeft = maxTime;
        _timerFont = new Font("Arial", Font.BOLD, 36);
        setPreferredSize(new Dimension(90, 90));
        setOpaque(false);
        
        // Configura il timer dell'animazione
        _animationTimer = new javax.swing.Timer(ANIMATION_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAnimation();
            }
        });
        _animationTimer.setRepeats(true);
        _animationTimer.start();
    }

    public void setTime(int seconds) {
        // Se il tempo cambia, avvia una nuova animazione
        if (_timeLeft != seconds) {
            _timeLeft = seconds;
            resetAnimation();
        }
        repaint();
    }

    public void setOpacity(float opacity) {
        _opacity = opacity;
        repaint();
    }
    
    private void resetAnimation() {
        _animationProgress = 0.0f;
        _isAnimating = true;
    }
    
    private void updateAnimation() {
        if (_isAnimating) {
            // Incrementa il progresso dell'animazione
            _animationProgress += (float)ANIMATION_DELAY / ANIMATION_DURATION;
            
            // Se l'animazione è completa, ricomincia
            if (_animationProgress >= 1.0f) {
                _animationProgress = 0.0f;
            }
            
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        
        // Applica l'opacità
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, _opacity));
        
        // Antialiasing per bordi più lisci
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        int width = getWidth();
        int height = getHeight();
        int size = Math.min(width, height) - 16;
        int x = (width - size) / 2;
        int y = (height - size) / 2;
        
        // Determina il colore basato sul tempo rimanente
        Color timerColor = _timeLeft <= _warningThreshold ? _warningColor : _normalColor;
        
        // Calcola l'angolo dell'arco basato sul progresso dell'animazione
        // L'animazione forma e distrugge un cerchio completo ogni secondo
        int arcAngle;
        
        if (_animationProgress < 0.5f) {
            // Prima metà: il cerchio si forma (0° -> 360°)
            arcAngle = (int)(360 * (_animationProgress * 2));
        } else {
            // Seconda metà: il cerchio si distrugge (360° -> 0°)
            arcAngle = (int)(360 * (2 - _animationProgress * 2));
        }
        
        // Disegna l'arco basato sul progresso dell'animazione
        g2d.setColor(timerColor);
        g2d.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        
        // MODIFICA: invertito la direzione dell'arco
        g2d.drawArc(x, y, size, size, 90, arcAngle); // Cambio da -arcAngle a arcAngle per invertire il senso
        
        // Disegna il numero al centro
        g2d.setFont(_timerFont);
        
        String timeText = String.valueOf(_timeLeft);
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(timeText);
        int textHeight = fm.getHeight();
        
        // Ombra leggera per il testo
        g2d.setColor(new Color(0, 0, 0, 50));
        g2d.drawString(timeText, width/2 - textWidth/2 + 1, height/2 + textHeight/3 + 1);
        
        // Testo principale
        g2d.setColor(timerColor);
        g2d.drawString(timeText, width/2 - textWidth/2, height/2 + textHeight/3);
        
        g2d.dispose();
    }
}