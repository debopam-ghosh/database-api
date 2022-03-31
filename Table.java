package database;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.LinkedHashSet;
import java.util.Objects;

@Path("table")
public class Table {
    String name;
    Column col;
    int n;//no. of columns in table
    LinkedHashSet<Row> rows;

    public Table() {
        name = "";
        col = null;
        rows = new LinkedHashSet<>();
    }


    @POST
    @Path("/createTable")
    @Consumes("application/json")
    public String createTable(Table tab) {
        this.name=tab.name;
        this.col=tab.col;
        this.n=tab.n;
        this.rows=tab.rows;
        return this.toString();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Table table = (Table) o;
        return name.equals(table.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @GET
    @Path("/getTableSchema")
    public String toString() {

        return name + col.toString();
    }

    @GET
    @Path("/displayTable")
    public String displayTable() {
        String result = "Table name : " + name + "\n";
        if (rows.size() == 0) {
            result += (name + " has no rows");
            return result;
        }
        result += Utility.printArray(col.attr_name.toArray());
        for (Row temp : rows)
            result += Utility.printArray(temp.elements.toArray());
        return result;

    }

    @POST
    @Path("/addRow")
    @Consumes("application/json")
    public Response addRow(Row row) {
        rows.add(row);
        return Response.status(200).entity(row).build();
    }

    @DELETE
    @Path("/deleteRow/{index}")
    @Produces("text/plain")
    public String removeRow(@PathParam("index") int index) {

        if (index < 0 || index >= rows.size()) {
            return Boolean.toString(false);
        }
        //Converting LinkedHashSet to array to help index the element
        Row[] LHSArray = new Row[rows.size()];
        LHSArray = rows.toArray(LHSArray);
        Row toBeRemoved = LHSArray[index];//Acquiring the element
        rows.remove(toBeRemoved);
        return Boolean.toString(true);
    }
}