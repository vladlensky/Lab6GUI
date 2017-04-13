/**
 * Created by bespa on 11.04.2017.
        */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.io.*;
import java.util.List;
import classes.*;
import com.sun.webkit.ColorChooser;
import org.json.simple.*;

public class Interface extends JFrame {
    private static JPanel panelu = new JPanel();
    private static JPanel paneld= new JPanel();
    private static JPanel panelc= new JPanel();
    private static String myVar = null;
    private static String file="";
    private static LinkedList<NormalHuman> coll = new LinkedList<NormalHuman>();
    public static String getFile(){return file;}
    private static void colorChooser(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame colorFrame = new JFrame("Choose color!");
                colorFrame.setAutoRequestFocus(true);
                colorFrame.setSize(new Dimension(700,400));
                colorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                colorFrame.setLocationRelativeTo(null);
                colorFrame.setLayout(new GridBagLayout());
                JColorChooser cc = new JColorChooser();
                colorFrame.add(cc, new GridBagConstraints(
                        0,0,3,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH,
                        new Insets(0,0,0,0),0,0));
                JButton cb = new JButton("This color!");
                cb.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        colorFrame.dispose();
                        panelu.setBackground(cc.getColor());
                        panelc.setBackground(cc.getColor());
                        paneld.setBackground(cc.getColor());
                    }
                });
                colorFrame.add(cb, new GridBagConstraints(
                        2,1,1,1, 1,1, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                        new Insets(0,0,0,0), 0, 0));
                colorFrame.setVisible(true);
            }
        });
    }
    Interface(){
        JFrame jf = new JFrame();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setResizable(false);
        jf.setSize(new Dimension(600,400));
        jf.setTitle("Малыш и Карлсон");
        jf.setLocationRelativeTo(null);
        jf.setLayout(new GridLayout(3,1));

        CollectTable collt = new CollectTable();
        JTable collections = new JTable(collt);
        JScrollPane scroll = new JScrollPane(collections);

        scroll.setPreferredSize(new Dimension(300,500));
        panelu.add(scroll,new GridBagConstraints(0,0,1,1,1,1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,new Insets(1,1,1,1)
                ,0,0));
        panelu.setVisible(true);
        for(int i=0;i<coll.size();i++){
            String[] obj = {coll.get(i).getName(),coll.get(i).getAge().toString(), coll.get(i).getTroublesWithTheLaw().toString()};
            collt.addData(obj);
        }
        jf.add(panelu);

        JButton showThoughtsButton = new JButton("Show thoughts");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        ButtonsUnderTable but= new ButtonsUnderTable(collections, collt, coll);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                but.delete();
            }
        });

        JButton doButton = new JButton("Do");
        DefaultListModel<String> dlm= new DefaultListModel<>();
        dlm.addElement("Remove");
        dlm.addElement("Save");
        dlm.addElement("Add person");
        dlm.addElement("Add in json");
        dlm.addElement("Hust");
        JList<String> listCommands = new JList<>(dlm);
        listCommands.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ButtonsWithCommands bwc= new ButtonsWithCommands(listCommands, coll, collt, collections);

        doButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bwc.doCommand();
            }
        });

        panelc.setLayout(new GridBagLayout());
        panelc.add(deleteButton,new GridBagConstraints(2, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0,0,0,150), 0,0));
        panelc.add(showThoughtsButton,
                new GridBagConstraints(0, 0, 1, 1, 1, 1,
                        GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0,150,0,0), 0,0));
        panelc.add(editButton,new GridBagConstraints(1, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0,0,0,0), 0,0));
        panelc.add(doButton, new GridBagConstraints(0,1,1,2,1,1, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL
                                ,new Insets(0,200,0,0), 0,0));
        panelc.add(listCommands, new GridBagConstraints(1,1,2,2,1,1, GridBagConstraints.CENTER,
                                GridBagConstraints.VERTICAL, new Insets(0,0,0,200), 0, 0));

        jf.add(panelc);
        jf.setVisible(true);

        JButton colorChooserButton = new JButton("Choose background color!");
        colorChooserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorChooser();
            }
        });
        paneld.add(colorChooserButton);
        jf.add(paneld);
    }

    public static LinkedList<String> fromFileToString(String path) throws FileNotFoundException, SecurityException, IOException{
        Scanner sc=null;
        try {
            LinkedList<String> lines = new LinkedList<String>();
            File f = new File(path);
            if(f.exists()&&(!f.canRead()|| !f.canWrite())) throw new SecurityException();
            if(f.isDirectory()) throw new IOException();
            FileReader fin = new FileReader(f);
            sc = new Scanner(fin);
            while (sc.hasNextLine()) {
                lines.add(sc.nextLine());
            }
            return lines;
        }
        finally {
            if(sc!=null)
                sc.close();

        }
    }

    public static ArrayList<String> firstParse(List<String> lines) {
        ArrayList<String> jsonLines = new ArrayList<String>();
        int x = 0, y = 0, i1 = 0;
        int[] lastObj = {0, 0};
        while (i1 < lines.size()) {
            String a = lines.get(i1);
            for (int i2 = 0; i2 < a.length(); i2++) {
                if (a.charAt(i2) == '{') x++;
                if (a.charAt(i2) == '}') y++;
                if ((x != 0) && (y != 0) && (x == y)) {
                    StringBuffer sb = new StringBuffer("");
                    if (lastObj[0] == i1) sb.append(lines.get(i1).substring(lastObj[1], i2 + 1));
                    else {
                        sb.append(lines.get(lastObj[0]).substring(lastObj[1] + 1, lines.get(lastObj[0]).length()));
                        for (int j1 = lastObj[0] + 1; j1 < i1; j1++) {
                            sb = sb.append(lines.get(j1));
                        }
                        sb.append(lines.get(i1).substring(0, i2 + 1));
                    }
                    jsonLines.add(sb.toString());
                    lastObj[0] = i1;
                    lastObj[1] = i2;
                    x = 0;
                    y = 0;
                }
            }
            i1++;
        }
        return jsonLines;
    }

    public static LinkedList<NormalHuman> toObject(LinkedList<NormalHuman> coll, ArrayList<String> jsonLines) {
        int a=0;
        for (int i = 0; i < jsonLines.size(); i++) {
            NormalHuman nh;
            try{nh = StringToObject(jsonLines.get(i));
                coll.add(nh);}catch (NullPointerException e){a++;}
        }
        if(a==1)System.out.println(a + " of NormalHuaman's is not correct in the file");
        if(a>1) System.out.println(a + " of NormalHuman's are not correct in the file");
        return coll;
    }

    public static NormalHuman StringToObject(String str)  {

        NormalHuman nh = new NormalHuman();
        JSONObject obj = (JSONObject) JSONValue.parse(str);
        JSONArray ar = (JSONArray) obj.get("thoughts");
        try{for (int j = 0; j < ar.size(); j++) {
            String th = null;
            try{JSONObject object = (JSONObject) ar.get(j);
                th = (String) object.get("thought");}catch(NullPointerException e){}
            nh.thinkAbout(th);
        }}catch (NullPointerException e){}
        Long age=5l;
        String name = null;
        boolean troublesWithTheLaw = false;
        try{age= (Long)obj.get("age"); }catch(NullPointerException e){}catch (ClassCastException e){age=Long.parseLong((String)obj.get("age"));}
        try{name = (String) obj.get("name");}catch(NullPointerException e){}
        try{troublesWithTheLaw = (Boolean) obj.get("troublesWithTheLaw");}catch(NullPointerException e){troublesWithTheLaw=Boolean.parseBoolean((String)obj.get("age"));}
        try {
            nh.setName(name);
        } catch (KarlsonNameException e) {
            System.out.println("Karlson is a bad name for NormalHuman.");
        }
        nh.setAge(age);
        nh.setTroublesWithTheLaw(troublesWithTheLaw);
        return nh;

    }


    public static void main(String[] args){
        boolean er = false;
        final String var="myVar";
        List<String> lines=null;
        try{myVar= System.getenv(var);
            if(myVar==null) throw new NullPointerException();
            File f = new File(myVar);
            lines = fromFileToString(myVar);
            ArrayList<String> jsonLines = firstParse(lines);
            coll = toObject(coll, jsonLines);}
        catch(NullPointerException e){
            er = true;
            try{
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    ExceptionFrame.init("There is no environment variable "+var+".");
                }
            });
            } catch(Exception exception){}}
            catch (FileNotFoundException e){
            er = true;
            try{
                SwingUtilities.invokeAndWait(new Runnable() {
                    @Override
                    public void run() {
                        ExceptionFrame.init("Outter file is not exists. \nChange your environment variable.");
                    }
                });} catch(Exception exception){}
        }catch (SecurityException e){
            er = true;
            try{
                SwingUtilities.invokeAndWait(new Runnable() {
                    @Override
                    public void run() {
                        ExceptionFrame.init("You have no access for this file.\nChange your environment variable.");
                    }
                });} catch(Exception exception){}
        }catch(IOException e){
            er = true;
            try{
                SwingUtilities.invokeAndWait(new Runnable() {
                    @Override
                    public void run() {
                        ExceptionFrame.init("There is not a file in environment variable.");
                    }
                });} catch(Exception exception){}
        }
        file=myVar;
        if(!er)SwingUtilities.invokeLater(()-> new Interface());
    }
}