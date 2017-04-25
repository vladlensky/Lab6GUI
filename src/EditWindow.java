/**
 * Created by Mugenor on 14.04.2017.
 */
/**
 * Created by Mugenor on 14.04.2017.
 */
import classes.KarlsonNameException;
import classes.NormalHuman;

import javax.management.openmbean.KeyAlreadyExistsException;
import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class EditWindow extends JFrame {
    private static Color c = null;
    int numberRow=-1;
    CollectTable collections;
    LinkedList<NormalHuman> linkedList;
    JPanel panel = new JPanel();
    NormalHuman nh= new NormalHuman();
    JTextField field = new JTextField(20);
    JTextField thoughtsField = new JTextField();
    JButton Add = new JButton("Add");
    JButton Del = new JButton("Delete");
    JRadioButton True = new JRadioButton("true");
    JRadioButton False = new JRadioButton("false");
    JButton ok = new JButton("Ok");
    JButton canc = new JButton("Cancel");
    DefaultListModel<String> dlm = new DefaultListModel();
    JList<String> list = new JList(dlm);
    SpinnerNumberModel snm = new SpinnerNumberModel(1,1,100,1);
    JSpinner spin = new JSpinner(snm);
    JTextField tf = ((JSpinner.DefaultEditor) spin.getEditor()).getTextField();
    public EditWindow(CollectTable collections, LinkedList<NormalHuman> linkedList){
        this.collections=collections;
        this.linkedList=linkedList;
        try{
            nh.setName("SetName");
        }catch (KarlsonNameException e){}
        init();
    }
    public EditWindow(NormalHuman nh, CollectTable collections, int numberRow){
        this.numberRow=numberRow;
        this.nh=nh;
        this.collections=collections;
        init();
    }
    private void init(){
        for(int i=0;i<nh.getThoughtsCount();i++){
            dlm.addElement(nh.getThoughts(i));
        }
        setLocationRelativeTo(null);
        setFocusable(true);
        setResizable(false);
        setSize(300,420);
        panel.setBackground(Color.white);
        panel.setLayout(null);
        panel.setFocusable(true);
        setFocusable(true);
        add(panel);
        field.setSelectionColor(c);
        field.setSize(240,20);
        field.setLocation(27,30);
        field.setText(nh.getName());
        //
        JLabel label = new JLabel("Age:");
        label.setLocation(28,48);
        label.setSize(50,30);
        label.setFont(new Font("Verdana", Font.PLAIN, 13));
        //
        JLabel name = new JLabel("Name:");
        name.setLocation(27,0);
        name.setSize(50,30);
        name.setFont(new Font("Verdana", Font.PLAIN, 13));
        //
        spin.setSize(60,20);
        tf.setText(nh.getAge().toString());
        tf.setEditable(true);
        tf.setForeground(c);
        tf.setBackground(Color.WHITE);
        spin.setLocation(70,55);
        spin.setValue(nh.getAge());
        //
        JLabel trob = new JLabel("");
        trob.setLocation(27,70);
        trob.setSize(80,30);
        trob.setFont(new Font("Verdana", Font.BOLD, 13));
        //
        thoughtsField.setSize(new Dimension(200,20));
        thoughtsField.setLocation(new Point(27,180));
        //
        JLabel trobL = new JLabel("Troubles with the law:");
        trobL.setLocation(27,200);
        trobL.setSize(180,30);
        trobL.setFont(new Font("Verdana", Font.BOLD, 13));
        //
        ButtonGroup group = new ButtonGroup();
        if(nh.getTroublesWithTheLaw()) True.setSelected(true); else False.setSelected(true);
        False.setFont(new Font("Verdana", Font.PLAIN, 12));
        True.setFont(new Font("Verdana", Font.PLAIN, 12));
        True.setSize(80,30);
        False.setSize(80,30);
        True.setLocation(30,230);
        False.setLocation(29,252);
        group.add(True);
        group.add(False);
        True.setForeground(Color.BLACK);
        True.setBackground(Color.WHITE);
        False.setForeground(Color.BLACK);
        False.setBackground(Color.WHITE);
        //
        if(c!=null)canc.setBackground(c);
        if(c!=null)ok.setBackground(c);
        canc.setSize(80,30);
        canc.setLocation(40,285);
        ok.setSize(80,30);
        ok.setLocation(160,285);
        //

        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(3);
        list.setFont(new Font("Verdana", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(list);
        scroll.setSize(150,75);
        scroll.setLocation(30,100);
        //

        if(c!=null)Add.setBackground(c);
        if(c!=null)Del.setBackground(c);
        Add.setSize(80,30);
        Add.setLocation(200,100);
        Del.setSize(80,30);
        Del.setLocation(200,143);
        panel.add(True);
        panel.add(name);
        panel.add(False);
        panel.add(Add);
        panel.add(Del);
        panel.add(spin);
        panel.add(trob);
        panel.add(trobL);
        panel.add(field);
        panel.add(label);
        panel.add(ok);
        panel.add(canc);
        panel.add(scroll);
        panel.add(thoughtsField);
        addListeners();
        setLocation(1000,155);
        setVisible(true);
    }
    public static void setColor(Color colo){
        c = colo;
    }
    private void addThought(){
        if(!thoughtsField.getText().equals("")){
            nh.thinkAbout(thoughtsField.getText());
            dlm.addElement(nh.getThoughts(nh.getThoughtsCount()-1));
        }
    }
    private void deleteThought(){
        nh.forgetThought(list.getSelectedIndex());
        dlm.remove(list.getSelectedIndex());
    }
    private void exit(){
        try{
            nh.setName(field.getText());
            nh.setAge(snm.getNumber().longValue());
            nh.setTroublesWithTheLaw(True.isSelected());
            if(numberRow==(-1)) {collections.addData(nh);
                linkedList.add(nh);}
            else
                collections.editData(nh, numberRow);
            dispose();
        }catch(KarlsonNameException exc){
            JLabel label = new JLabel("NormalHuman can't be Karlson");
            label.setForeground(Color.RED);
            label.setLocation(70,0);
            label.setSize(500,30);
            label.setFont(new Font("Verdana", Font.BOLD, 13));
            panel.add(label);
            panel.updateUI();
        }
    }
    private void addListeners(){
        EditWindow ew = this;
        field.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        });
        thoughtsField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addThought();
            }
        });
        Del.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteThought();
            }
        });
        canc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ew.dispose();
            }
        });
        Add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addThought();
            }
        });
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Interface.setIsChanged(true);
                exit();
            }
        });
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyPressed(e);
                if(e.getExtendedKeyCode()==KeyEvent.VK_ENTER){
                    exit();
                }
            }
        });
        list.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if(e.getKeyChar()==KeyEvent.VK_DELETE){
                    if(list.getSelectedIndex()!=-1)
                        deleteThought();
                }
            }
        });
    }
}