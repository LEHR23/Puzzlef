package juego;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Puzzle extends JFrame implements KeyListener {

    private JPanel tablerojuego;
    private JLabel tablerofondo;
    private int matriz[][], matrizganadora[][], tiempotranscurrido, min, seg;
    private JLabel matrizimagenes[][];
    private JLabel cronometro;
    private AudioClip music;

    public Puzzle() {
        tiempotranscurrido = 0;
        t = new Timer(1000, tiempo);
        matriz = new int[6][10];
        matrizganadora = new int[6][10];
        matrizimagenes = new JLabel[6][10];
        //musica
//        music = java.applet.Applet.newAudioClip(getClass().getResource
//        ("/imagenes/fairytailmusic.wav"));
//        music.play();

        //creacion de la ventana
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setBounds(0, 0, 1100, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        ImageIcon laimagen = new ImageIcon(getClass().getResource("/imagenes/fondo.jpg"));
        Icon fondo = new ImageIcon(laimagen.getImage().getScaledInstance(1100,
                700, Image.SCALE_DEFAULT));
        tablerofondo = new JLabel();
        tablerofondo.setBounds(0, 0, 1100, 700);
        tablerofondo.setIcon(fondo);
        add(tablerofondo, -1);

        // boton para empezar a jugar
        JButton jugar = new JButton();
        jugar.setBounds(10, 400, 80, 200);
        ImageIcon p = new ImageIcon(getClass().getResource("/imagenes/play.png"));
        Icon play = new ImageIcon(p.getImage().getScaledInstance(jugar.getWidth(),
                jugar.getHeight(), Image.SCALE_DEFAULT));
        jugar.setIcon(play);
        jugar.setContentAreaFilled(false);
        jugar.setBorderPainted(false);
        jugar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("PLAY");
                tablerojuego.setVisible(true);
                desordenarimagen();
                t.start();
                tiempotranscurrido = 0;
                jugar.setFocusable(false);
            }
        });
        add(jugar, 0);

        //creacion de tablero de juego
        tablerojuego = new JPanel();
        tablerojuego.setLayout(null);
        tablerojuego.setBounds(90, 50, 1000, 600);
        tablerojuego.setBackground(Color.black);
        tablerojuego.setVisible(false);
        add(tablerojuego, 0);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 10; j++) {
                matrizimagenes[i][j] = new JLabel();
                matrizimagenes[i][j].setBounds(j * 100, i * 100, 100, 100);
                tablerojuego.add(matrizimagenes[i][j]);
            }
        }

        //etiqueta tiempo
        cronometro = new JLabel("TIEMPO: 00:00");
        cronometro.setBounds(900, 10, 200, 30);
        cronometro.setFont(new Font("DigifaceWide", 2, 16));
        cronometro.setForeground(Color.WHITE);
        add(cronometro, 0);

        addKeyListener(this);
        setFocusable(true);
    }

    public void desordenarimagen() {
        int cont = 1;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 10; j++) {
                matriz[i][j] = 0;
                matrizganadora[i][j] = cont;
                cont++;
            }
        }
        Random r = new Random();
        Boolean salir = true;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 10; j++) {
                salir = true;
                do {
                    salir = true;
                    matriz[i][j] = r.nextInt(60) + 1;
                    for (int k = 0; k < 6; k++) {
                        for (int l = 0; l < 10; l++) {
                            if (!(i == k && j == l) && matriz[i][j] == matriz[k][l]) {
                                salir = false;
                            }
                        }
                    }
                } while (!salir);
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 10; j++) {
                matrizimagenes[i][j].setIcon(new ImageIcon(getClass().getResource(
                        "/imagenes/imagenp" + matriz[i][j] + ".jpg")));
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent ev) {
        int eve = ev.getKeyCode();
        if (eve == KeyEvent.VK_UP) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 10; j++) {
                    if (matriz[i][j] == 60) {
                        matriz[i][j] = matriz[i + 1][j];
                        matriz[i + 1][j] = 60;
                        matrizimagenes[i][j].setIcon(new ImageIcon(getClass().getResource(
                                "/imagenes/imagenp" + matriz[i][j] + ".jpg")));
                        matrizimagenes[i + 1][j].setIcon(new ImageIcon(getClass().getResource(
                                "/imagenes/imagenp" + matriz[i + 1][j] + ".jpg")));
                        j = 10;
                        i = 5;
                    }
                }
            }
            ganador();
        }
        if (eve == KeyEvent.VK_DOWN) {
            for (int i = 1; i < 6; i++) {
                for (int j = 0; j < 10; j++) {
                    if (matriz[i][j] == 60) {
                        matriz[i][j] = matriz[i - 1][j];
                        matriz[i - 1][j] = 60;
                        matrizimagenes[i][j].setIcon(new ImageIcon(getClass().getResource(
                                "/imagenes/imagenp" + matriz[i][j] + ".jpg")));
                        matrizimagenes[i - 1][j].setIcon(new ImageIcon(getClass().getResource(
                                "/imagenes/imagenp" + matriz[i - 1][j] + ".jpg")));
                        j = 10;
                        i = 6;
                    }
                }
            }
            ganador();
        }
        if (eve == KeyEvent.VK_RIGHT) {
            for (int i = 0; i < 6; i++) {
                for (int j = 1; j < 10; j++) {
                    if (matriz[i][j] == 60) {
                        matriz[i][j] = matriz[i][j - 1];
                        matriz[i][j - 1] = 60;
                        matrizimagenes[i][j].setIcon(new ImageIcon(getClass().getResource(
                                "/imagenes/imagenp" + matriz[i][j] + ".jpg")));
                        matrizimagenes[i][j - 1].setIcon(new ImageIcon(getClass().getResource(
                                "/imagenes/imagenp" + matriz[i][j - 1] + ".jpg")));
                        j = 10;
                        i = 6;
                    }
                }
            }
            ganador();
        }
        if (eve == KeyEvent.VK_LEFT) {
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 9; j++) {
                    if (matriz[i][j] == 60) {
                        matriz[i][j] = matriz[i][j + 1];
                        matriz[i][j + 1] = 60;
                        matrizimagenes[i][j].setIcon(new ImageIcon(getClass().getResource(
                                "/imagenes/imagenp" + matriz[i][j] + ".jpg")));
                        matrizimagenes[i][j + 1].setIcon(new ImageIcon(getClass().getResource(
                                "/imagenes/imagenp" + matriz[i][j + 1] + ".jpg")));
                        j = 10;
                        i = 6;
                    }
                }
            }
            ganador();
        }
        if (eve == KeyEvent.VK_ESCAPE) {
            t.stop();
        }
        if (eve == KeyEvent.VK_ENTER) {
            t.start();
        }
        if (eve == KeyEvent.VK_5) {
            int contadoratajo = 1;
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 10; j++) {
                    matriz[i][j] = contadoratajo;
                    matrizimagenes[i][j].setIcon(new 
                            ImageIcon(getClass().getResource(
                            "/imagenes/imagenp" + matriz[i][j] + ".jpg")));
                    contadoratajo++;
                }
            }
            ganador();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private void ganador() {
        Boolean ganador = true;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 10; j++) {
                if (matriz[i][j] != matrizganadora[i][j]) {
                    ganador = false;
                    i = 6;
                    j = 10;
                }
            }
        }
        if (ganador) {
            t.stop();
            matrizimagenes[5][9].setIcon(new ImageIcon(getClass().getResource(
                    "/imagenes/g.jpg")));
            JOptionPane.showMessageDialog(null, "FELICIDADES HAS GANADO!!!");
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 10; j++) {
                   matriz[i][j] = 0;
                }
            }
        }
    }

    private Timer t;

    private ActionListener tiempo = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            tiempotranscurrido++;
            min = tiempotranscurrido / 60;
            seg = tiempotranscurrido - min * 60;
            if (min < 10) {
                if (seg < 10) {
                    cronometro.setText("TIEMPO: 0" + min + ":0" + seg);
                } else {
                    cronometro.setText("TIEMPO: 0" + min + ":" + seg);
                }
            } else {
                if (seg < 10) {
                    cronometro.setText("TIEMPO: " + min + ":0" + seg);
                } else {
                    cronometro.setText("TIEMPO: " + min + ":" + seg);
                }
            }
        }
    };

}
