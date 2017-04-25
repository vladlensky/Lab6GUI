import classes.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by Mugenor on 13.04.2017.
 */
public class ButtonsUnderTable {
    private static Color c = null;
    CollectTable collt;
    JTable collections;
    LinkedList<NormalHuman> coll;
    public static void setColor(Color col){
        c = col;
    }
    ButtonsUnderTable(JTable table, CollectTable ct, LinkedList<NormalHuman> coll){
        collt=ct;
        collections=table;
        this.coll=coll;
    }
    public void delete(){
        if(collections.getSelectedRow()!=-1){
            coll.remove(collections.getSelectedRow());
            Interface.setIsChanged(true);
            collt.removeData(collections.getSelectedRow());}
    }
    public void edit(){
        if(collections.getSelectedRow()!=-1)
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new EditWindow(coll.get(collections.getSelectedRow()), collt, collections.getSelectedRow());
                }
            });
    }
    public void showThoughts(){
        if(collections.getSelectedRow()!=-1){
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JFrame jf= new JFrame("Thoughts Frame");
                    jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    jf.setResizable(false);
                    jf.setSize(new Dimension(400,200));
                    jf.setLocationRelativeTo(null);
                    DefaultListModel<String> dlm= new DefaultListModel<>();
                    for(int i=0;i<coll.get(collections.getSelectedRow()).getThoughtsCount();i++){
                        dlm.addElement(coll.get(collections.getSelectedRow()).getThoughts(i));
                    }
                    JList<String> list = new JList<>(dlm);
                    if(c!=null){
                        list.setForeground(c);
                    }
                    list.setFont(new Font("Verdana", Font.PLAIN, 13));
                    list.setLayoutOrientation(JList.VERTICAL);
                    list.setVisibleRowCount(3);
                    JScrollPane scroll = new JScrollPane(list);
                    scroll.setSize(150,75);
                    scroll.setLocation(30,100);
                    jf.add(scroll);
                    jf.setVisible(true);
                }
            });}
    }
}
