/**
 * Created by bespa on 11.04.2017.
        */
import javax.swing.*;
import java.awt.*;

public class Interface {
    Interface(){
        JFrame jf = new JFrame();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setResizable(false);
        jf.setSize(new Dimension(600,400));
        jf.setTitle("Малыш и Карлсон");
        jf.setLocationRelativeTo(null);
        jf.setLayout(new GridLayout(3,1));
        JPanel panelu = new JPanel();
        CollectTable collt = new CollectTable();
        JTable collections = new JTable(collt);
        JScrollPane scroll = new JScrollPane(collections);
        scroll.setPreferredSize(new Dimension(300,500));
        panelu.add(scroll,new GridBagConstraints(0,0,1,1,1,1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,new Insets(1,1,1,1)
                ,0,0));
        panelu.setVisible(true);
        String[] a= {"a", "a","a"};
        String[] b={"b","b","b"};
        String[] c={"c","c","c"};
        String[] d={"d","d","d"};
        String[] e={"e","e","e"};
        String[] z={"z","z","z"};
        collt.addData(a);
        collt.addData(b);
        collt.addData(c);
        collt.addData(d);
        collt.addData(e);
        collt.addData(z);
        jf.add(panelu);
        JButton showThoughtsButton = new JButton("Show thoughts");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        JPanel panelc= new JPanel();
        panelc.add(showThoughtsButton);
        panelc.add(editButton);
        panelc.add(deleteButton);
        jf.add(panelc);
        jf.setVisible(true);

    }
    public static void main(String[] args){
        new Interface();
    }
}