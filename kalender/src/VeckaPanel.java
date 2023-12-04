import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class VeckaPanel extends JPanel {
    private LocalDate localDate; //Varibel för att hålla datumet
    private Map<LocalDate, VeckaDagPanel> veckaDagPanelMap;//en map som har samma datum som VeckaDagPanel

    public VeckaPanel() {
        setLayout(new GridLayout(1, 7));//layout för varje dag
        this.veckaDagPanelMap = new HashMap<>();//en tom map för att ta in det från VeckaDagPanel
        updateVecka(LocalDate.now());
    }

    //metod för VeckaPanel så den visar rätt vecka
    public void updateVecka(LocalDate date) {
        this.localDate = date;
        LocalDate startOfWeek = date.minusDays(date.getDayOfWeek().getValue() - 1); //så att första dagen blir måndag

        removeAll();

        //en loop för att gå igenom alla sju dagarna på veckan, funkar på grund av ISO standarden där 1 är måndag osv
        for (int i = 0; i < 7; i++) {
            LocalDate day = startOfWeek.plusDays(i); //räknar på varje dag utifrån förstadagen i indexet
            boolean isToday = day.isEqual(LocalDate.now());

            VeckaDagPanel veckaDagPanel = veckaDagPanelMap.getOrDefault(day, new VeckaDagPanel(day, isToday));
            veckaDagPanelMap.put(day, veckaDagPanel);
            add(veckaDagPanel);
        }

        revalidate();
        repaint();
    }
}

