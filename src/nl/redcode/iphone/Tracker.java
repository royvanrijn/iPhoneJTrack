package nl.redcode.iphone;

import java.io.File;
import java.util.List;

import nl.redcode.iphone.kml.KMLGenerator;
import nl.redcode.iphone.sqllite.DataPoint;
import nl.redcode.iphone.sqllite.GeoDataExtractor;

public class Tracker {

	public static void main(String[] args) throws Exception {
		if(args == null || args.length != 1) {
			System.out.println("Usage: ");
			System.out.println("java -jar iPhoneJTrack.jar \"<location of backup directory>\"");
			System.out.println("");
			System.out.println("For example:");
			System.out.println("java -jar iPhoneJTrack.jar \"C:\\Users\\roy\\AppData\\Roaming\\Apple Computer\\MobileSync\\Backup\\b4d1c79c2f5fc1da044c18d1066b80b39b575590\"");
			System.out.println("");
			System.out.println("The output is a file named 'iPhoneData.kml' which can be loaded into Google Earth for example.");
			System.exit(1);
		}
		
		File directory = new File(args[0].replace("\\", "/"));
		
		FileLister f = new FileLister(directory);
		File sqlLite = f.getHiddenFile();
		System.out.println(sqlLite);
		
		GeoDataExtractor e = new GeoDataExtractor();
		List<DataPoint> geoData = e.extractGeoData(sqlLite);
		
		KMLGenerator kmlGenerator = new KMLGenerator();
		kmlGenerator.generateKML(new File("iPhoneData.kml"), geoData);
	}

}
