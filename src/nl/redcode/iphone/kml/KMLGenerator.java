package nl.redcode.iphone.kml;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import nl.redcode.iphone.sqllite.DataPoint;

/**
 * Ugly class to quickly generate a KML file.
 * 
 * Also: The resulting file is quite unreadable in Google Earth, but it is a start!
 * Do with it what you want :-)
 * 
 * TODO: Maybe add more output generators, GPX?
 * TODO: Define (plugin-like) interface for handling the List<DataPoint>
 * 
 * @author Roy van Rijn
 *
 */
public class KMLGenerator {

	public void generateKML(File output, List<DataPoint> geoData) throws Exception {
		FileWriter writer = new FileWriter(output);
		
		writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		writer.write("<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n");
		writer.write("\t<Document>\n");
		for(DataPoint point:geoData) {
			writer.write("\t\t<Placemark>\n");
			writer.write("\t\t\t<name>"+point.getTimestamp()+"</name>\n");
			writer.write("\t\t\t<Point><coordinates>"+point.getLongitude()+","+point.getLatitude()+",0</coordinates></Point>\n");
			writer.write("\t\t</Placemark>\n");
		}
		writer.write("\t</Document>\n");
		writer.write("</kml>\n");
		writer.flush();
		writer.close();
	}
}
