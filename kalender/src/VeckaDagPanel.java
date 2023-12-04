import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VeckaDagPanel extends JPanel {

    //Variabeler som håller dagens datum och notiserna
    public LocalDate datum;
    private Map<LocalDate, List<String>> notiserMap;
    private JTextArea notiserTextArea;



    //Konstruktor som tar emot dagens datum och booelan för att avgöra om det är dagens datum eller inte.
    public VeckaDagPanel(LocalDate datum, boolean isToday) {
        this.datum = datum;
        this.notiserMap = new HashMap<>();
        kalanderPanel(isToday); //Anropar en metod för att bygga panelen
    }



    //Metod för attn bygga upp panelen
    private void kalanderPanel(boolean isToday) {
        //Skapar en Borderlayout
        setLayout(new BorderLayout());

        //Skapar texten för veckodagarna och datumen
        String veckoDagText = String.valueOf(datum.getDayOfWeek());
        String datumText = datum.format(DateTimeFormatter.ofPattern("dd/MM"));

        //Skapar JLabels för veckodagarna och datumen
        JLabel veckoDagLabel = new JLabel(veckoDagText);
        JLabel datumLabel = new JLabel(datumText);

        //Sätter texten i mitten (av panelen som byggs undertill)
        veckoDagLabel.setHorizontalAlignment(JLabel.CENTER);
        datumLabel.setHorizontalAlignment(JLabel.CENTER);


        //Skapar en panel för veckodagarna och datumen med layouten GridLayout (där jag vll ha två rader och en kolumn för att få datumen under veckodagen)
        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.add(veckoDagLabel); // Lägger till veckodagen i toppanelen längst upp
        topPanel.add(datumLabel); // Lägger till datumen i toppanelen under veckodagarna

        //Lägger till toppanelen i huvudpanelen (längst upp)
        add(topPanel, BorderLayout.NORTH);

        notiserTextArea = new JTextArea();
        notiserTextArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(notiserTextArea); //GÖr att man kan scrolla
        add(scrollPane, BorderLayout.CENTER); //Lägger till "scrollningen" i mitten av huvudpanelen

        //Skapar en panel för text, en knapp och för notiser med layouten BorderLayout
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JTextField text = new JTextField(); //Skapar en JTextField för att få in notiser


        JButton knappText = new JButton("Lägg till notis"); //Gör en knapp för att kunna lägga till notiser
        knappText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String notisText = text.getText();
                notiserTextArea.append(text.getText() + "\n"); //Lägger till texten från textrutan som notis och sätter en radbrytning för att börja på en nny rad
                text.setText(""); //för att rensa textrutan

                //Kollar om det redan finns för det datumet, om det inte finns "!" skapas en ny tom map
                if (!notiserMap.containsKey(datum)) {
                    notiserMap.put(datum, new ArrayList<>());
                }
                notiserMap.get(datum).add(notisText);
            }
        });

        //hämtar notiserna från datum
        List<String> notiserForDatum = notiserMap.get(datum);
        //om det finns notiser för det datumet, lägger det till en radbryting
        if (notiserForDatum != null) {
            for (String notis : notiserForDatum) {
                notiserTextArea.append(notis + "\n");
            }
        }


        bottomPanel.add(knappText, BorderLayout.SOUTH); //Lägger till knappen längst ned i bottenpanelen
        bottomPanel.add(text, BorderLayout.NORTH); //Lägger till textrutan ovanför knappen
        add(bottomPanel, BorderLayout.SOUTH); //Lägger till bottenpanelen i huvudpanelen, längst nee

        //När det är dagens datum ska det finnas en grön ram runt om och nedre panelen ska också vara grön
        if (isToday) {
            setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));
            setBackground(Color.GREEN);
        }
    }


    //en mentod för att notiserna ska uppdateras
    public void updateNotiser(Map<LocalDate, List<String>> notiserMap) {
        this.notiserMap = notiserMap;
        List<String> notiserForDatum = notiserMap.get(datum);
        if (notiserForDatum != null) {
            notiserTextArea.setText(""); //Rensa textområdet för att undvika dubbla notiser
            for (String notis : notiserForDatum) {
                notiserTextArea.append(notis + "\n");
            }
        }
    }
}
