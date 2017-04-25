import classes.NormalHuman;
import org.json.simple.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.LinkedList;
import java.io.*;

/**
 * Created by Mugenor on 13.04.2017.
 */
public class ButtonsWithCommands {
    JButton resume;
    JButton stop;
    JButton pause;
    JButton play;
    private static Color c = new Color(12,255,12);
    public static void setColor(Color colo){
        c = colo;
    }
    JList<String> listCommands;
    LinkedList<NormalHuman> coll;
    CollectTable collt;
    JTable collections;
    private static File f = new File("C:\\Users\\bespa\\Lab6GUI-master (4)\\Rammstein_-_Du_hast.wav");
    private static MyPlayer sound = new MyPlayer(f);
    ButtonsWithCommands(JList<String> listCommands, LinkedList<NormalHuman> coll, CollectTable collt, JTable collections){
        this.listCommands=listCommands;
        this.coll=coll;
        this.collt=collt;
        this.collections=collections;
    }
    public void doCommand(){
        switch(listCommands.getSelectedIndex()){
            case 0: remove();
                break;
            case 1: save();
                break;
            case 2: addPerson();
                break;
            case 3: addInJson();
                break;
            case 4: hust();
                break;
        }
    }
    public void remove(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame jf= new JFrame("Deleting frame");
                JLabel label= new JLabel("Put here NormalHuman in json: ");
                JLabel alabel = new JLabel("");
                JTextField tf = new JTextField("", 50);
                simpleFrame(jf, label, alabel, tf);
                tf.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String s = tf.getText();
                        try{NormalHuman nh=Interface.StringToObject(s);
                            collt.removeData(coll.indexOf(nh));
                            Interface.setIsChanged(true);
                            coll.remove(nh);
                            CollectTable collt1=new CollectTable();
                            for(int i=0;i<coll.size();i++){
                                String[] obj = {coll.get(i).getName(),coll.get(i).getAge().toString(), coll.get(i).getTroublesWithTheLaw().toString()};
                                collt1.addData(obj);
                            }
                            collections.setModel(collt1);
                            jf.dispose();}
                        catch (NullPointerException exc){
                            alabel.setText("Wrong NormalHuman!");
                        }
                    }
                });
            }
        });
    }
    public void save() {
        Thread t= new Thread(new Runnable() {
            @Override
            public void run() {
                File f = new File(Interface.getFile());
                FileOutputStream FOS=null;
                PrintWriter pw=null;
                try{
                    FOS = new FileOutputStream(f);
                    pw = new PrintWriter(FOS, true);
                    for (int i = 0; i < coll.size(); i++) {
                        JSONArray ar = new JSONArray();
                        for (int j = 0; j < coll.get(i).countOfThoughts(); j++) {
                            JSONObject itemOfCollectionThoughts = new JSONObject();
                            itemOfCollectionThoughts.put("thought", coll.get(i).getThoughts(j));
                            ar.add(itemOfCollectionThoughts);
                        }
                        JSONObject obj = new JSONObject();
                        obj.put("age", coll.get(i).getAge());
                        obj.put("name", coll.get(i).getName());
                        obj.put("troublesWithTheLaw", coll.get(i).getTroublesWithTheLaw());
                        obj.put("thoughts", ar);
                        pw.println(obj.toString());

                    }
                } catch (FileNotFoundException e) {
                    System.out.println("File not found");
                } catch (NullPointerException e){
                    System.out.println("Null point");
                }
                finally{
                    try{Interface.setIsChanged(false);
                        FOS.close();
                        pw.close();
                        System.out.println("Collection saved!");}
                    catch(IOException | NullPointerException e)
                    {
                        System.out.println("Can't save collection into " + f);
                    }
                }
            }
        });
        t.start();
    }
    public void addPerson(){
        new EditWindow(collt, coll);
    }
    public void addInJson(){
        JFrame jf= new JFrame("Deleting frame");
        JLabel label= new JLabel("Put here NormalHuman in json: ");
        JLabel alabel = new JLabel("");
        JTextField tf = new JTextField("", 50);
        simpleFrame(jf, label, alabel, tf);
        tf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str=tf.getText();
                try{NormalHuman nh=Interface.StringToObject(str);
                    String [] strArray={nh.getName(), nh.getAge().toString(),nh.getTroublesWithTheLaw().toString()};
                    collt.addData(strArray);
                    coll.add(nh);
                    CollectTable collt1=new CollectTable();
                    for(int i=0;i<coll.size();i++){
                        String[] obj = {coll.get(i).getName(),coll.get(i).getAge().toString(), coll.get(i).getTroublesWithTheLaw().toString()};
                        collt1.addData(obj);
                    }
                    collections.setModel(collt1);
                    jf.dispose();}
                catch (NullPointerException exc){
                    alabel.setText("Wrong NormalHuman!");
                }
            }
        });
    }
    public void hust() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if(!sound.isReleased()) return;
                try{
                    JFrame jf = new JFrame();
                    jf.setLocation(300,30);
                    jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    jf.setResizable(false);
                    jf.setTitle("Do hust");
                    jf.setLayout(new GridBagLayout());
                    play = new JButton("play");
                    play.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            sound.setVolume(1f);
                            sound.play();
                        }
                    });
                    stop = new JButton("stop");
                    stop.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            sound.stop();
                        }
                    });
                    pause = new JButton("pause");
                    pause.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            sound.pause();
                        }
                    });
                    resume = new JButton("resume");
                    resume.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            sound.resume();
                        }
                    });
                    play.setBackground(c);
                    stop.setBackground(c);
                    resume.setBackground(c);
                    pause.setBackground(c);
                    ImageIcon du_hust= new ImageIcon("C:\\Users\\bespa\\Lab6GUI-master (4)\\du_hust.jpg");
                    JLabel du = new JLabel(du_hust);
                    jf.add(du, new GridBagConstraints(0,0,4,1,0,
                            0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(10,0,0,0),0,0));
                    jf.add(play, new GridBagConstraints(0,1,1,1,0,
                            0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,new Insets(10,200,10,10),0,0));
                    jf.add(stop, new GridBagConstraints(1,1,1,1,0,
                            0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,new Insets(10,10,10,10),0,0));
                    jf.add(pause, new GridBagConstraints(2,1,1,1,0,
                            0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,new Insets(10,10,10,10),0,0));
                    jf.add(resume, new GridBagConstraints(3,1,1,1,0,
                            0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,new Insets(10,10,10,190),0,0));
                    jf.pack();
                    System.out.println(jf.getSize());
                    jf.setVisible(true);}catch (Exception e){}
            }
        });
    }
    private void simpleFrame(JFrame jf, JLabel label, JLabel alabel, JTextField tf){
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setResizable(false);
        jf.setSize(new Dimension(600,100));
        jf.setLocationRelativeTo(null);
        jf.setLayout(new FlowLayout());
        jf.add(label);
        jf.add(tf);
        jf.add(alabel);
        jf.setVisible(true);
    }
}
