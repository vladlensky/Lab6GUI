import classes.NormalHuman;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import org.json.simple.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.io.*;

/**
 * Created by Mugenor on 13.04.2017.
 */
public class ButtonsWithCommands {
    JList<String> listCommands;
    LinkedList<NormalHuman> coll;
    CollectTable collt;
    JTable collections;
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
                    try{FOS.close();
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
                try{
                JFrame jf = new JFrame();
                InputStream fis = new FileInputStream("C:\\Users\\Mugenor\\IdeaProjects\\Lab6GUI\\disturbed.mp3");
                AdvancedPlayer player = new AdvancedPlayer(fis);
                jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                jf.setResizable(false);
                jf.setSize(new Dimension(600,400));
                jf.setTitle("Do hust");
                jf.setLocationRelativeTo(null);
                jf.setLayout(new GridLayout());
                JButton play = new JButton("play");
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                player.play();
                            }catch (JavaLayerException e){}
                        }
                    });
                play.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        thread.start();
                    }
                });
                JButton stop = new JButton("stop");
                stop.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        player.stop();
                        player.close();
                        thread.stop();
                    }
                });
                jf.add(play);
                jf.add(stop);
                jf.pack();
                jf.setVisible(true);}catch (Exception e){}
            }
        });
        /**SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream fis = new FileInputStream("C:\\Users\\Mugenor\\IdeaProjects\\Lab6GUI\\disturbed.mp3");
                    AdvancedPlayer player = new AdvancedPlayer(fis);
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                player.play();
                            }catch (JavaLayerException e){}
                        }
                    });
                    thread.start();

                } catch (FileNotFoundException | JavaLayerException e) {
                }
            }
        });*/
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
