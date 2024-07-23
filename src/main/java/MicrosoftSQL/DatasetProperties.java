package MicrosoftSQL;

/**
 * The DatasetProperties record is used to store the properties of a dataset,
 * specifically the number of columns and rows.
 *
 * @param numberOfColumns the number of columns in the dataset
 * @param numberOfRows    the number of rows in the dataset
 */
public record DatasetProperties(int numberOfColumns, int numberOfRows) {
}
