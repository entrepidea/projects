package com.entrepidea.java.xquery;

import org.basex.core.BaseXException;
import org.basex.core.Context;
import org.basex.core.cmd.CreateDB;
import org.basex.core.cmd.DropDB;
import org.basex.core.cmd.XQuery;
import org.basex.data.Result;
import org.basex.query.QueryException;
import org.basex.query.QueryProcessor;
import org.basex.query.iter.Iter;
import org.basex.query.value.item.Item;

/**
 * This class demonstrates collection relevant queries.
 * It shows how to find and query specific documents.
 *
 * @author BaseX Team 2005-12, BSD License
 */
public final class QueryCollection {
  /**
   * Runs the example code.
   * @param args (ignored) command-line arguments
   * @throws BaseXException if a database command fails
 * @throws QueryException 
   */
  public static void main(final String[] args) throws BaseXException, QueryException {
    // Database context.
    Context context = new Context();

    System.out.println("=== QueryCollection ===");

    // ------------------------------------------------------------------------
    // Create a collection from all XML documents in the specified directory
    System.out.println("\n* Create a collection.");

    new CreateDB("Collection", "src/main/resources/").execute(context);

    // ------------------------------------------------------------------------
    // List all documents in the database
    System.out.println("\n* List all documents in the database:");

    // The XQuery base-uri() function returns a file path
    System.out.println(new XQuery(
        "for $doc in collection('Collection')" +
        "return <doc path='{ base-uri($doc) }'/>"
    ).execute(context));

    // ------------------------------------------------------------------------
    // Evaluate a query on a single document
    System.out.println("\n* Evaluate a query on a single document:");

    // If the name of the database is omitted in the collection() function,
    // the currently opened database will be referenced
   /* System.out.println(new XQuery(
        "for $doc in collection()" +
        "let $file-path := base-uri($doc)" +
        "where ends-with($file-path, 'factbook.xml')" +
        "return concat($file-path, ' has ', count($doc//*), ' elements')"
    ).execute(context));*/
    
    String query = new XQuery( 
    		"doc('src/main/resources/factbook.xml')/name").execute(context);
   
   // System.out.println(query);
    
 // Create a query processor
   
    
    
    QueryProcessor proc = new QueryProcessor(query, context);

    // ------------------------------------------------------------------------
    // Store the pointer to the result in an iterator:
    Iter iter = proc.iter();

    // ------------------------------------------------------------------------
    // Iterate through all items and serialize
    for(Item item; (item = iter.next()) != null;) {
      System.out.println(item.toJava());
    }

    // ------------------------------------------------------------------------
    // Close the query processor
    proc.close();
   
    
/*    QueryProcessor proc = new QueryProcessor(query, context);
    // ------------------------------------------------------------------------
    // Execute the query
    Result result = proc.execute();

    // ------------------------------------------------------------------------
    // Print result as string.
    System.out.println(result);*/
    
    // ------------------------------------------------------------------------
    // Drop the database
    System.out.println("\n* Drop the database.");

    new DropDB("Collection").execute(context);

    // ------------------------------------------------------------------------
    // Close the database context
    context.close();
  }
  
}