package MicrosoftSQL;

import java.sql.Date;
import java.util.List;

/**
 * The LineProcessor class is responsible for creating Line objects from a list of strings.
 */
public class LineProcessor {

    /**
     * Creates a Line object from a list of strings.
     *
     * @param line a list of strings containing data corresponding to OracleDataset.csv column names
     * @return a Line object with the data from the list
     */
    public static Line getLineObject(List<String> line) {

        // Data corresponding to OracleDataset.csv column names
        Date date = Date.valueOf(line.get(0));
        String opened = line.get(1);
        String high = line.get(2);
        String low = line.get(3);
        String closed = line.get(4);
        String adjClosed = line.get(5);
        String volume = line.get(6);

        return new Line(date, opened, high, low, closed, adjClosed, volume);

    }
}
