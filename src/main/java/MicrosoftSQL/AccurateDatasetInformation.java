package MicrosoftSQL;

import java.util.List;

/**
 * A class representing factual information about the OracleDataset.csv to design test cases
 * with, ensuring MicrosoftSQL statements are working accurately.
 */
public class AccurateDatasetInformation {

    // Basic Information
    /**
     * The number of columns in the dataset.
     */
    public static final int NUMBER_OF_COLUMNS = 7;

    /**
     * The number of rows in the dataset, excluding column titles.
     */
    public static final int NUMBER_OF_ROWS_EXCLUDING_COLUMN_TITLES = 9647;

    // Column names
    /**
     * The name of the first column: DateRecorded.
     */
    public static final String COLUMN_1 = "DateRecorded";

    /**
     * The name of the second column: OpeningPrice.
     */
    public static final String COLUMN_2 = "OpeningPrice";

    /**
     * The name of the third column: HighestPrice.
     */
    public static final String COLUMN_3 = "HighestPrice";

    /**
     * The name of the fourth column: LowestPrice.
     */
    public static final String COLUMN_4 = "LowestPrice";

    /**
     * The name of the fifth column: ClosingPrice.
     */
    public static final String COLUMN_5 = "ClosingPrice";

    /**
     * The name of the sixth column: AdjClosingPrice.
     */
    public static final String COLUMN_6 = "AdjClosingPrice";

    /**
     * The name of the seventh column: Volume.
     */
    public static final String COLUMN_7 = "Volume";

    // Column names as a list
    /**
     * A list of all column names in the dataset.
     */
    public static final List<String> columnNames = List.of(COLUMN_1, COLUMN_2, COLUMN_3, COLUMN_4, COLUMN_5, COLUMN_6, COLUMN_7);

}
