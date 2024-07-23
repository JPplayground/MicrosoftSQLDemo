package MicrosoftSQL;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The DatasetReader class is responsible for reading a CSV dataset file
 * and storing its contents into two lists: one for column names and another for rows.
 */
public class DatasetReader {

    // The name of the dataset file
    String oracleDataset = "OracleDataset.csv";

    // List of column names as strings in order
    List<String> columnNames = new ArrayList<>();

    // List of rows where each row is a List itself
    List<List<String>> rows = new ArrayList<>();

    /**
     * Constructor for DatasetReader. Initializes and reads the dataset.
     */
    public DatasetReader() {
        read();
    }

    /**
     * Returns the list of column names.
     *
     * @return List of column names
     */
    public List<String> getColumnNames() {
        return columnNames;
    }

    /**
     * Returns the list of rows.
     *
     * @return List of rows where each row is a List itself
     */
    public List<List<String>> getRows() {
        return rows;
    }

    /**
     * Reads the dataset file and populates the columnNames and rows lists.
     */
    private void read() {

        // Get dataset as resource
        URI datasetURI = null;
        try {
            datasetURI = DatasetReader.class.getClassLoader().getResource(oracleDataset).toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        // Convert to Path for processing
        Path dataset = Path.of(datasetURI);

        // Read file contents
        try {
            List<String> lines = Files.readAllLines(dataset);

            // Extract column names
            List<String> columns = Arrays.stream(lines.get(0).split(",")).toList();
            columnNames.addAll(columns);

            // Extract data
            lines.stream()
                    .skip(1)
                    .forEach((row) -> {
                        var singleRowAsAList = Arrays.stream(row.split(",")).toList();
                        rows.add(singleRowAsAList);
                    });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Returns the properties of the dataset including the number of columns and rows.
     *
     * @return DatasetProperties containing the number of columns and rows
     */
    public DatasetProperties getProperties() {
        return new DatasetProperties(columnNames.size(), rows.size());
    }

}
