package Models;

public class DBQueryModel {
    public String table;
    public String where;
    public String[] whereValue;
    public String updateWhat;
    public String updateValue;
    public int quantity = 10;
    public boolean single = false;
    public String createValues;
    public boolean after = true;
}
