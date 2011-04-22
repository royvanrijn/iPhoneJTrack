package nl.redcode.iphone.sqllite;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

/**
 * This class opens the iPhone file as SQLLite database and extracts the geo-data.
 * 
 * @author Roy van Rijn
 * 
 */
public class GeoDataExtractor {

	public List<DataPoint> extractGeoData(File dbFile) throws Exception {
		List<DataPoint> geoData = new ArrayList<DataPoint>();
		SqlJetDb db = SqlJetDb.open(dbFile, false);
		db.beginTransaction(SqlJetTransactionMode.READ_ONLY);

		try {

			ISqlJetTable table = db.getTable("CellLocation");
			extractGeoDataFromTable(geoData, table);
			
		} finally {
			db.commit();
		}
		return geoData;
	}

	public void extractGeoDataFromTable(List<DataPoint> geoData, ISqlJetTable table) throws SqlJetException {
		ISqlJetCursor cursor = table.open();
		try {
			if (!cursor.eof()) {
				do {
					double latitude = cursor.getFloat("Latitude");
					double longitude = cursor.getFloat("Longitude");
					double timestamp = cursor.getFloat("Timestamp");
					int confidence = (int) cursor.getInteger("Confidence");
					
					Calendar c = new GregorianCalendar();
					c.set(Calendar.YEAR, 2001);
					c.set(Calendar.DAY_OF_MONTH, 0);
					c.set(Calendar.MONTH, 0);
					c.add(Calendar.SECOND, (int)timestamp);
					String formattedDate = DateFormat.getDateTimeInstance().format(c.getTime());

					if(confidence >= 70) {
						geoData.add(new DataPoint(formattedDate, latitude, longitude));
					}
					
				} while (cursor.next());
			}
		} finally {
			cursor.close();
		}
	}
}
