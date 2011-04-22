package nl.redcode.iphone.sqllite;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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

	private static final Date start = new GregorianCalendar(2001, 1, 1).getTime();
	
	public List<DataPoint> extractGeoData(File dbFile) throws Exception {
		List<DataPoint> geoData = new ArrayList<DataPoint>();
		SqlJetDb db = SqlJetDb.open(dbFile, false);
		db.beginTransaction(SqlJetTransactionMode.READ_ONLY);

		try {
//	List the tables:
//			for(String table:db.getSchema().getTableNames()) {
//				System.out.println(table+"----");
//				System.out.println(db.getTable(table).getDefinition().getColumns());
//			}
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
					//Extract the timestamp (in seconds from 01-01-2001), the latitude and longitude.
					double timestamp = cursor.getFloat("Timestamp");
					double latitude = cursor.getFloat("Latitude");
					double longitude = cursor.getFloat("Longitude");
					String dateStamp = new Date(start.getTime()+((long)timestamp*1000)).toString();
					geoData.add(new DataPoint(dateStamp, latitude, longitude));
					
				} while (cursor.next());
			}
		} finally {
			cursor.close();
		}
	}
}
