import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.SeparatorUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Mugenor on 23.04.2017.
 */
public class CloseFrame extends JFrame {
    ButtonsWithCommands bwc;
    static Color c = null;
    public static void setColor(Color col){
        c = col;
    }
    CloseFrame(ButtonsWithCommands bwc){
        this.bwc=bwc;
        setLocationRelativeTo(null);
        setFocusable(true);
        setResizable(false);
        setSize(250,100);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10,10));
        JButton yes = new JButton("Yes");
        JButton no = new JButton("No");
        if(c!=null) {
            yes.setBackground(c);
            no.setBackground(c);
        }
        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bwc.save();
                System.exit(0);
            }
        });
        no.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        JLabel label = new JLabel("Do you want to save collection?");
        label.setFont(new Font("Verdana", Font.BOLD,12));
        add(label);
        add(yes);
        add(no);
        setVisible(true);
    }
}
