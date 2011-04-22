package nl.redcode.iphone;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * This class holds all the information stored in the Manifest files in the iPhone backup directory.
 * 
 * @author Roy van Rijn
 * 
 */
public class FileInfo {
	
	private int startOffset;
	private String domain;
	private String filename;
	private String linktarget;
	private String datahash;
	private String unknown1;
	private int mode;
	private int unknown2;
	private int unknown3;
	private int userid;
	private int groupid;
	private int mtime;
	private int atime;
	private int ctime;
	private int filelen;
	private int flag;
	private int numprops;
	
	private Map<String, String> properties = new HashMap<String, String>();
	
	public int getStartOffset() {
		return startOffset;
	}
	public void setStartOffset(int startOffset) {
		this.startOffset = startOffset;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getLinktarget() {
		return linktarget;
	}
	public void setLinktarget(String linktarget) {
		this.linktarget = linktarget;
	}
	public String getDatahash() {
		return datahash;
	}
	public void setDatahash(String datahash) {
		this.datahash = datahash;
	}
	public String getUnknown1() {
		return unknown1;
	}
	public void setUnknown1(String unknown1) {
		this.unknown1 = unknown1;
	}
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	public int getUnknown2() {
		return unknown2;
	}
	public void setUnknown2(int unknown2) {
		this.unknown2 = unknown2;
	}
	public int getUnknown3() {
		return unknown3;
	}
	public void setUnknown3(int unknown3) {
		this.unknown3 = unknown3;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getGroupid() {
		return groupid;
	}
	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}
	public int getMtime() {
		return mtime;
	}
	public void setMtime(int mtime) {
		this.mtime = mtime;
	}
	public int getAtime() {
		return atime;
	}
	public void setAtime(int atime) {
		this.atime = atime;
	}
	public int getCtime() {
		return ctime;
	}
	public void setCtime(int ctime) {
		this.ctime = ctime;
	}
	public int getFilelen() {
		return filelen;
	}
	public void setFilelen(int filelen) {
		this.filelen = filelen;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public int getNumprops() {
		return numprops;
	}
	public void setNumprops(int numprops) {
		this.numprops = numprops;
	}
	public Map<String, String> getProperties() {
		return properties;
	}
	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}
}
