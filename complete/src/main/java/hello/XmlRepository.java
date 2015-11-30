package hello;

import org.basex.core.BaseXException;
import org.basex.core.Context;
import org.basex.core.cmd.Add;
import org.basex.core.cmd.CreateDB;
import org.basex.core.cmd.Delete;
import org.basex.core.cmd.InfoDB;
import org.basex.core.cmd.Optimize;
import org.basex.core.cmd.Set;
import org.springframework.stereotype.Component;

@Component
public class XmlRepository {
	
    public static void main(final String[] args) throws BaseXException {
        createCollectionAndImportXml("Collection", "src/test/resources/xmldb/xml");
    }

    public static void createCollectionAndImportXml(String name, String importDir) throws BaseXException {
        // Create database context
        Context context = new Context();

        System.out.println("=== CreateCollection ===");

        // You can modify the CREATEFILTER property to import XML
        // files with suffixes other than XML (for example KML):
        new Set("CREATEFILTER", "*.xml").execute(context);

        // Variant 1:
        // Create a collection and add all documents within the specified path
        System.out.println("\n* Create a collection.");

        new CreateDB(name, importDir).execute(context);

        // Show information on the currently opened database
        System.out.println("\n* Show database information:");

        System.out.println(new InfoDB().execute(context));
    }

    public static void createDatabase() throws BaseXException {
        // Create database context
        Context context = new Context();

        System.out.println("=== CreateCollection ===");

        new CreateDB("Collection").execute(context);
        new Add("", "src/main/resources/").execute(context);
        new Optimize().execute(context);

        // Remove a single document from the collection
        System.out.println("\n* Remove a single document.");

        new Delete("test.xml").execute(context);

        // Show information on the currently opened database
        System.out.println("\n* Show database information:");

        System.out.println(new InfoDB().execute(context));
    }
    
}
