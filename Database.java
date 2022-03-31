package database;


import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/database")
public class Database {
    static ArrayList<Schema> schemas = new ArrayList<>();


    @GET
    @Path("/menu/{i}")
    @Produces("text/plain")
    public String getMenu(@PathParam("i") int i) {
        StringBuilder grandMenu = new StringBuilder();
        grandMenu.append("\n1. Enlist all schema\n")
                .append("2. Create schema\n")
                .append("3. Delete Schema\n");

        StringBuilder tableMenu = new StringBuilder();
        tableMenu.append("\n1. Enlist all tables\n")
                .append("2. Create table\n")
                .append("3. Delete table\n")
                .append("Press any other key to go back to previous menu...\n");

        StringBuilder tableOptions = new StringBuilder();
        tableOptions.append("\n1. Display table\n")
                .append("2. Add Row\n")
                .append("3. Delete Row\n")
                .append("Press any other key to go back to previous menu...\n");
        StringBuilder[] list = {grandMenu, tableMenu, tableOptions};
        return list[i].append("Enter your choice : ").toString();
    }

    @GET
    @Path("/allSchemas")
    @Produces("application/json")
    public ArrayList<Schema> getAllSchemas() {
        return schemas;
    }

    @POST
    @Path("/createSchema")
    @Consumes("text/plain")
    public Response createSchema(String name) {

        Schema obj = new Schema();
        obj.createSchema(name);
        schemas.add(obj);

        return Response.status(200).entity(obj.getSchemaName() + " Schema created\n").build();
    }

    @DELETE
    @Path("/deleteSchema/{num}")
    @Produces("text/plain")
    public String deleteSchema(@PathParam("num") int num) {
        num--;
        if (num < 0 || num >= schemas.size()) {
            return "Invalid choice";
        }
        schemas.remove(num);
        return "Schema removed.";
    }

    @GET
    @Path("/schema/{c}/allTables")
    @Produces("text/plain")
    public String getAllTables(@PathParam("c") int c) {
        c--;
        if (c < 0 || c >= schemas.size())
            return "Invalid schema number";
        int tableno = 1;
        String result = "";
        for (Table temp : schemas.get(c).tables) {
            result += (tableno++ + ". --> ");
            result += temp.displayTable();
        }
        if (tableno == 1)
            return "This schema has no tables.";
        else
            return result;
    }

    @POST
    @Path("/schema/createTable")
    @Produces("text/plain")
    public String addNewTableToSchema(ArrayList<Object> userRequest) {
        int c = (Integer) userRequest.get(0);//schema number
        c--;
        String tname = (String) userRequest.get(1);//table name;
        Column col = (Column) userRequest.get(2);//Getting column names and types
        Table tempTable = new Table();
        tempTable.name = tname;
        tempTable.col = col;
        tempTable.n = col.attr_name.size();
        return tempTable.createTable(tempTable);
    }

    @DELETE
    @Path("/schema/{c}/deleteTable/{table_num}")
    public String deleteTable(@PathParam("c") int c,@PathParam("table_num") int table_num ) {
        c--;
        table_num--;
        boolean flag=schemas.get(c).removeTable(table_num);
        if(flag)
            return "Table has been removed";
        else
            return "Invalid index";

    }

}
