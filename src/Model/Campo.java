package Model;

import Control.Botao;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static Control.Botao.on;

public class Campo extends JFrame {
    public static ArrayList<Carta> cartas = new ArrayList<>();
    public static ArrayList<Carta> cartasEmDobro = new ArrayList<>();
    public static ArrayList<Botao> campo = new ArrayList<>();
    public static int rows = 4;
    public static int cols = 2;
    public static void criarCartas(){
        if(!cartas.isEmpty()){
            cartas.clear();
        }
        new Carta(1,"src/Images/brocoli.png");
        new Carta(2,"src/Images/darwin.png");
        new Carta(3,"src/Images/gatu.png");
        new Carta(4,"src/Images/homerchu.png");
        new Carta(5,"src/Images/joinha.png");
        new Carta(6,"src/Images/keney.png");
        new Carta(7,"src/Images/knickles.png");
        new Carta(8,"src/Images/lagarta.png");
        new Carta(9,"src/Images/mark.png");
        new Carta(10,"src/Images/monke.png");
        new Carta(11,"src/Images/monke2.png");
        new Carta(12,"src/Images/mordetwi.png");
        new Carta(13,"src/Images/parry.png");
        new Carta(14,"src/Images/silly.png");
        new Carta(15,"src/Images/sponge.png");
        new Carta(16,"src/Images/squid.png");
        new Carta(17,"src/Images/wolv.png");
        new Carta(18,"src/Images/wtf.png");
    }
    public Campo(){
        criarCartas();

        JPanel jp = new JPanel();
        jp.setLayout(new GridLayout(rows,cols));

        for (int i = 0; i < (rows*cols/2); i++) {
            Random random = new Random();
            int number = random.nextInt(0, cartas.size());

            cartasEmDobro.add(cartas.get(number));
            cartasEmDobro.add(cartas.get(number));
            cartas.remove(number);
        }

        for (int i = 0; i < rows*cols; i++) {
            Random random = new Random();
            int number = random.nextInt(0, cartasEmDobro.size());

            Botao b = new Botao(cartasEmDobro.get(number));
            cartasEmDobro.remove(number);

            jp.add(b);
        }

        add(jp);

        setTitle("Jogo da Memória");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setSize(700,700);
        setLocationRelativeTo(null);

        Thread wait = new Thread(() -> {

            JOptionPane.showMessageDialog(null,"Memorize as imagens e faça pares com suas correspondentes!");

            campo.forEach(e -> e.setIcon(e.getCarta().getImageIcon()));

            try {
                Thread.sleep(2000);
            } catch (Exception ex){}

            campo.forEach(e -> e.setIcon(null));
            on = true;

        });
        wait.setDaemon(true);
        wait.start();
    }
}