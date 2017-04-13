import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Mugenor on 13.04.2017.
 */
public class ExceptionFrame {
    static String exc;
    static void init(String exc) {
        JFrame exceptionFrame = new JFrame(exc);
        exceptionFrame.setSize(new Dimension(300,100));
        exceptionFrame.setResizable(false);
        exceptionFrame.setLocationRelativeTo(null);
        exceptionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel exceptionLabel = new JLabel(exc);
        exceptionFrame.setLayout(new GridLayout(2,1));
        JButton exceptionButton = new JButton("Ok");
        exceptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });
        exceptionFrame.add(exceptionLabel);
        exceptionFrame.add(exceptionButton);
        exceptionFrame.setVisible(true);
    }
}
