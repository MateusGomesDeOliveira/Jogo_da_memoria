package Control;

import Model.Carta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import static Model.Campo.*;

public class Botao extends JButton implements MouseListener {
    private static int clicks;
    public static boolean on = false;
    private Carta carta;
    private static int cardsOpen;
    private boolean open;
    private boolean right = false;
    public Botao(Carta carta){
        setBackground(Color.BLACK);
        this.carta = carta;
        addMouseListener(this);
        campo.add(this);
    }

    public Carta getCarta() {
        return carta;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {
        if(cardsOpen < 2 && !right && !open && on){
            clicks += 1;
            open = true;
            setIcon(carta.getImageIcon());
            cardsOpen +=1;
            if (cardsOpen == 2){

                Predicate<Botao> botoesOpen = b -> b.open;
                List<Botao> open = campo.stream().filter(botoesOpen).toList();

                Thread wait = new Thread(() -> {

                    open.forEach(c -> c.open = false);

                    if (open.get(0).carta.getId() == open.get(1).carta.getId()){
                        open.forEach(c -> c.right = true);
                        open.forEach(c -> c.setBackground(Color.GREEN));
                    } else {
                        open.forEach(c -> c.setBackground(Color.RED));
                    }

                    try {
                        Thread.sleep(500);
                    } catch (Exception ex){}

                    open.forEach(c -> c.setBackground(Color.BLACK));

                    if (open.get(0).carta.getId() != open.get(1).carta.getId()){
                        open.forEach(c -> c.setIcon(null));
                    }

                    Predicate<Botao> botoesRight = b -> b.right;
                    List<Botao> right = campo.stream().filter(botoesRight).toList();

                    if (right.size() == rows*cols){
                        if(clicks == rows*cols) {
                            JOptionPane.showMessageDialog(null,"Partida perfeita! "+clicks+" clicks!");
                        } else {
                            JOptionPane.showMessageDialog(null,"Você venceu em "+clicks+" clicks!");
                        }

                        // processo de restart ----------
                        clicks = 0;
                        cardsOpen = 0;
                        on = false;

                        right.forEach(x->{
                            x.right = false;
                            x.setIcon(null);
                        });

                        criarCartas();

                        for (int i = 0; i < (rows*cols/2); i++) {
                            Random random = new Random();
                            int number = random.nextInt(0, cartas.size());

                            cartasEmDobro.add(cartas.get(number));
                            cartasEmDobro.add(cartas.get(number));
                            cartas.remove(number);
                        }

                        campo.forEach(x->{
                            Random random = new Random();
                            int number = random.nextInt(0, cartasEmDobro.size());
                            x.carta = cartasEmDobro.get(number);
                            cartasEmDobro.remove(number);
                        });

                        JOptionPane.showMessageDialog(null,"Nova partida carregada!");

                        campo.forEach(x -> x.setIcon(x.getCarta().getImageIcon()));

                        try {
                            Thread.sleep(2000);
                        } catch (Exception ex){}

                        campo.forEach(x -> x.setIcon(null));

                        on = true;
                        //até aqui --------------

                    } else {
                        cardsOpen = 0;
                    }

                });
                wait.setDaemon(true);
                wait.start();
            }
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}