import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

// -------------------------------------------------------------------------
/**
 * Class that reads dictionary.txt and put them in an array of String.
 *
 * @author ericfouh
 * @version Oct 22, 2017
 */
public class Words
{
    private String[] words;


    // ----------------------------------------------------------
    /**
     * Create a new Words object.
     */
    public Words()
    {
        try
        {
            Scanner inFile = new Scanner(new File("dictionary.txt"));
            ArrayList<String> tempW = new ArrayList<>();
            while (inFile.hasNext())
            {
                // find next line
                String token = inFile.next();
                tempW.add(token);

            }
            inFile.close();
            words = new String[tempW.size()];
            words = tempW.toArray(words);

        }
        catch (FileNotFoundException e)
        {
            System.out.println("File is missing.");
            e.printStackTrace();
        }

    }


    // ----------------------------------------------------------
    /**
     * Return an array of String.
     *
     * @return the array of words
     */
    public String[] getWords()
    {
        return words;
    }

}
