package database;

import java.util.LinkedHashSet;
import java.util.Objects;

public class Schema {
    LinkedHashSet<Table> tables;
    String name;

    public Schema() {
        tables = new LinkedHashSet<>();
        name = "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schema schema = (Schema) o;
        return tables.equals(schema.tables) && name.equals(schema.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tables, name);
    }

    public void setSchemaName(String name) {

        this.name = name;
    }


    public String getSchemaName() {

        return name;
    }

    public String printSchema() {
        String entireSchema = "Schema name : " + getSchemaName() + "\n";
        if (tables.size() == 0) {
            entireSchema += getSchemaName() + " has no tables\n";
            return entireSchema;
        }
        for (Table temp : tables) {
            entireSchema += temp.toString() + "\n";
        }
        return entireSchema;
    }

    public void createSchema(String name) {

        setSchemaName(name);
    }


    public boolean removeTable(int index) {
        if (index < 0 || index >= tables.size()) {
            return false;
        }
        //Coverting LinkedHashSet to array to help index the element
        Table[] LHSArray = new Table[tables.size()];
        LHSArray = tables.toArray(LHSArray);
        Table toBeRemoved = LHSArray[index];//Aquiring the element
        tables.remove(toBeRemoved);
        return true;    }
}