import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class VeckaFrame extends JFrame implements ActionListener {
    private VeckaPanel veckaKalenderPanel;
    private LocalDate localDate;

    public VeckaFrame() {
        veckaKalenderPanel = new VeckaPanel(); //skapar en instans
        add(veckaKalenderPanel);

        //lägger till knappar för att kunna gå fram och tillbaka mellan veckorna
        JButton bakVecka = new JButton("<-");
        bakVecka.setActionCommand("bak");

        bakVecka.addActionListener(this);

        add(bakVecka, BorderLayout.WEST);

        JButton framVecka = new JButton("->");

        framVecka.setActionCommand("fram");
        framVecka.addActionListener(this);

        add(framVecka, BorderLayout.EAST);


        localDate = LocalDate.now();
    }

    //ändrar vilken vecka som syns utifrån vad användaren tryckker
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if (actionCommand.equals("bak")) {
            localDate = localDate.minusWeeks(1);

        }
        else if (actionCommand.equals("fram")) {
            localDate = localDate.plusWeeks(1);
        }

        veckaKalenderPanel.updateVecka(localDate);
    }
}



