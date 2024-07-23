package MicrosoftSQL;

import java.sql.Date;

/**
 * Represents a line in the OracleDataset.csv
 *
 * @param date      the date of the line in the format "YYYY-MM-DD"
 * @param opened    the opening price of the line
 * @param high      the highest price of the line
 * @param low       the lowest price of the line
 * @param closed    the closing price of the line
 * @param adjClosed the adjusted closing price of the line
 * @param volume    the volume of trades on the line
 */
public record Line(Date date, String opened, String high, String low, String closed, String adjClosed, String volume) {
}