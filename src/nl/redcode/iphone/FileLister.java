package nl.redcode.iphone;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to list the files inside the iPhone backup directory.<br/> 
 * Translated from Python script:
 * http://stackoverflow.com/questions/3085153/how-to-parse-the-manifest-mbdb-file-in-an-ios-4-0-itunes-backup
 * 
 * @author Roy van Rijn
 * 
 */
public class FileLister {

	private static final byte[] HEX_CHAR_TABLE = { (byte) '0', (byte) '1', (byte) '2',
		(byte) '3', (byte) '4', (byte) '5', (byte) '6', (byte) '7',
		(byte) '8', (byte) '9', (byte) 'a', (byte) 'b', (byte) 'c',
		(byte) 'd', (byte) 'e', (byte) 'f' };

	final Map<Integer, String> encodedNames;
	final Map<Integer, FileInfo> fileInfoList;
	final File backupPath;

	public FileLister(File backupPath) throws Exception {
		this.backupPath = backupPath;
		
		//Get information from Manifest.mbdb:
		File mbdb = new File(backupPath, "Manifest.mbdb");
		fileInfoList = processMbdbFile(mbdb);

		//Get information from Manifest.mbdx:
		File mbdx = new File(backupPath, "Manifest.mbdx");
		encodedNames  = processMbdxFile(mbdx);
	}
	
	public File getHiddenFile() {
		for(Integer offset:fileInfoList.keySet()) {
			String filename = fileInfoList.get(offset).getFilename();
			if(filename.contains("consolidated")) {
				System.out.println(encodedNames.get(offset));
				return new File(backupPath, encodedNames.get(offset));
			}
		}
		throw new RuntimeException("File not found...");
	}

	
	private Map<Integer, String> processMbdxFile(File mbdx) throws Exception {
		Map<Integer, String> encodedNames = new HashMap<Integer, String>();
		BufferedInputStream stream = new BufferedInputStream(new FileInputStream(mbdx));
		byte[] checkFile = new byte[4];
		stream.read(checkFile, 0, 4);
		if (!Arrays.equals(checkFile, "mbdx".getBytes())) {
			throw new RuntimeException("mbdx file isn't valid!");
		}
		stream.read(); // Unknown bytes
		stream.read();
		offset = 6;
	    int fileCount = getInt(stream, 4);
	    System.out.println(fileCount+" files");
	    while(stream.available() > 0) {
	    	byte[] fileId = new byte[20];
	    	stream.read(fileId);
	    	offset += 20;
	    	int foundOffset = getInt(stream, 4);
	    	encodedNames.put(foundOffset + 6, getHexString(fileId));
	    	getInt(stream, 2); //mode
	    }
	    return encodedNames;
	}
	
	private String getHexString(byte[] raw) throws Exception {
		byte[] hex = new byte[2 * raw.length];
		int index = 0;

		for (byte b : raw) {
			int v = b & 0xFF;
			hex[index++] = HEX_CHAR_TABLE[v >>> 4];
			hex[index++] = HEX_CHAR_TABLE[v & 0xF];
		}
		return new String(hex, "ASCII");
	}

	int offset;

	public Map<Integer, FileInfo> processMbdbFile(File mbdb) throws Exception {
		BufferedInputStream stream = new BufferedInputStream(new FileInputStream(mbdb));
		byte[] checkFile = new byte[4];
		stream.read(checkFile, 0, 4);
		if (!Arrays.equals(checkFile, "mbdb".getBytes())) {
			throw new RuntimeException("mbdb file isn't valid!");
		}
		stream.read(); // Unknown bytes
		stream.read();
		offset = 6;

		Map<Integer, FileInfo> files = new HashMap<Integer, FileInfo>();
		while (stream.available() > 0) {

			FileInfo info = new FileInfo();
			info.setStartOffset(offset);
			info.setDomain(getString(stream));
			info.setFilename(getString(stream));
			info.setLinktarget(getString(stream));
			info.setDatahash(getString(stream));
			info.setUnknown1(getString(stream));
			info.setMode(getInt(stream, 2));
			info.setUnknown2(getInt(stream, 4));
			info.setUnknown3(getInt(stream, 4));
			info.setUserid(getInt(stream, 4));
			info.setGroupid(getInt(stream, 4));
			info.setMtime(getInt(stream, 4));
			info.setAtime(getInt(stream, 4));
			info.setCtime(getInt(stream, 4));
			info.setFilelen(getInt(stream, 8));
			info.setFlag(getInt(stream, 1));
			info.setNumprops(getInt(stream, 1));

			for (int prop = 0; prop < info.getNumprops(); prop++) {
				String propName = getString(stream);
				String propValue = getString(stream);
				info.getProperties().put(propName, propValue);
			}
			files.put(info.getStartOffset(), info);
		}
		return files;
	}

	private int getInt(final InputStream stream, int size) throws Exception {
		byte[] data = new byte[size];
		stream.read(data);
		offset += size;
		int value = 0;
		for (int i = 0; i < data.length; i++) {
			value = (value << 8) + (data[i] & 0xFF);
		}
		return value;
	}

	private String getString(final InputStream stream) throws Exception {
		int length = getInt(stream, 2);
		if (length == 0xFFFF) {
			return "";
		}
		offset += length;
		return getString(stream, length);
	}
	
	private String getString(final InputStream stream, int length) throws Exception {
		byte[] newString = new byte[length];
		stream.read(newString);
		return new String(newString);
	}
}
