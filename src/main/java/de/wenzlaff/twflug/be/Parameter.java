package de.wenzlaff.twflug.be;

import java.io.File;

/**
 * Die Programm Parameter
 * 
 * @author Thomas Wenzlaff
 * @version 0.1
 * @since 11.11.2014
 *
 */
public class Parameter {

	/**
	 * Die IP Adresse an der gelauscht wird.
	 */
	private String ip = "10.0.7.43";

	/**
	 * Der Port an dem gelauscht wird.
	 */
	private int port = 30003;

	private int breite = 600;
	private int hoehe = 600;

	private boolean isDebug;

	private boolean isNoGui;

	private File outputDatei = new File("flugdaten.log");

	private int refreshTime = 1000 * 60 * 5;

	private int maxCount = 50;

	private int minCount = 0;

	public Parameter() {
		super();
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		if (ip != null) {
			this.ip = ip;
		}
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setPort(String port) {
		if (port != null) {
			this.port = Integer.valueOf(port);
		}
	}

	public boolean isDebug() {
		return isDebug;
	}

	public void setDebug(String isDebug) {
		if (isDebug != null) {
			this.isDebug = Boolean.getBoolean(isDebug);
		}
	}

	public boolean isNoGui() {
		return isNoGui;
	}

	public void setNoGui(String isNoGui) {
		if (isNoGui != null) {
			this.isNoGui = Boolean.getBoolean(isNoGui);
		}
	}

	public void setDebug(boolean isDebug) {
		this.isDebug = isDebug;
	}

	public File getOutputDatei() {
		return outputDatei;
	}

	public void setOutputDatei(File outputDatei) {
		if (outputDatei != null) {
			this.outputDatei = outputDatei;
		}
	}

	public void setOutputDatei(String outputDatei) {
		if (outputDatei != null) {
			this.outputDatei = new File(outputDatei);
		}
	}

	public void setNoGui(boolean isNoGui) {
		this.isNoGui = isNoGui;
	}

	public int getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(String refreshTime) {
		if (refreshTime != null) {
			this.refreshTime = Integer.valueOf(refreshTime);
		}
	}

	public void setRefreshTime(int refreshTime) {
		this.refreshTime = refreshTime;
	}

	public int getBreite() {
		return breite;
	}

	public void setBreite(int breite) {
		this.breite = breite;
	}

	public void setBreite(String breite) {
		if (breite != null) {
			this.breite = Integer.valueOf(breite);
		}
	}

	public void setHoehe(String hoehe) {
		if (hoehe != null) {
			this.hoehe = Integer.valueOf(hoehe);
		}
	}

	public int getHoehe() {
		return hoehe;
	}

	public void setHoehe(int hoehe) {
		this.hoehe = hoehe;
	}

	public void setMaxCount(String maxCount) {
		if (maxCount != null) {
			this.maxCount = Integer.valueOf(maxCount);
		}
	}

	public int getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	public int getMinCount() {
		return minCount;
	}

	public void setMinCount(int minCount) {
		this.minCount = minCount;
	}

	public void setMinCount(String minCount) {
		if (minCount != null) {
			this.minCount = Integer.valueOf(minCount);
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Parameter [");
		if (ip != null) {
			builder.append("ip=");
			builder.append(ip);
			builder.append(", ");
		}
		builder.append("port=");
		builder.append(port);
		builder.append(", breite=");
		builder.append(breite);
		builder.append(", hoehe=");
		builder.append(hoehe);
		builder.append(", isDebug=");
		builder.append(isDebug);
		builder.append(", isNoGui=");
		builder.append(isNoGui);
		builder.append(", ");
		if (outputDatei != null) {
			builder.append("outputDatei=");
			builder.append(outputDatei);
			builder.append(", ");
		}
		builder.append("refreshTime=");
		builder.append(refreshTime);
		builder.append(", maxCount=");
		builder.append(maxCount);
		builder.append(", minCount=");
		builder.append(minCount);
		builder.append("]");
		return builder.toString();
	}

}
