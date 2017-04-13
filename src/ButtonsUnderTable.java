import classes.*;

import javax.swing.*;
import java.util.*;

/**
 * Created by Mugenor on 13.04.2017.
 */
public class ButtonsUnderTable {
    CollectTable collt;
    JTable collections;
    LinkedList<NormalHuman> coll;
    ButtonsUnderTable(JTable table, CollectTable ct, LinkedList<NormalHuman> coll){
        collt=ct;
        collections=table;
        this.coll=coll;
    }
    public void delete(){
        if(collections.getSelectedRow()!=-1){
        collt.removeData(collections.getSelectedRow());
        coll.remove(collections.getSelectedRow());
        CollectTable collt1=new CollectTable();
        for(int i=0;i<coll.size();i++){
            String[] obj = {coll.get(i).getName(),coll.get(i).getAge().toString(), coll.get(i).getTroublesWithTheLaw().toString()};
            collt1.addData(obj);
        }
        collections.setModel(collt1);
        }
    }
    public void edit(){
    }
    public void showThoughts(){

    }
}
