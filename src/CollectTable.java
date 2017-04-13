import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Created by bespa on 11.04.2017.
 */
public class CollectTable extends AbstractTableModel{

    public ArrayList<String []> data = new ArrayList<>();
    public CollectTable(){
        for(int i = 0;i < data.size();i++){
            data.add(new String[getColumnCount()]);
        }
    }
    @Override
    public int getRowCount() {
        return data.size();
    }
    @Override
    public String getColumnName(int column){
        switch(column){
            case 0: return "Name";
            case 1: return "Age";
            case 2: return "Troubles with the law";
            default: return null;
        }
    }
    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex)[columnIndex];
    }
    public void removeData(int i){
        data.remove(i);
    }
    public void addData(String []row){
        data.add(row);
    }
    public void editData(String []row, int numberRow ){
        removeData(numberRow-1);
        data.add(numberRow-1,row);
    }
}
