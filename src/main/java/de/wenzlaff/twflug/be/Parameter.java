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
	// TODO: entfernen
	private String ip = "10.0.7.43";

	/**
	 * Der Port an dem gelauscht wird.
	 */
	private int port = 30003;

	/** Die Fenster Breite in Pixel der Anwendung. */
	private int breite = 600;
	/** Die Fenster Höhe in Pixel der Anwendung. */
	private int hoehe = 600;

	/** Wenn true, dann Debug Modus, zeigt mehr Infos an. */
	private boolean isDebug;

	/** Wenn true, wird keine GUI angezeigt. */
	private boolean isNoGui;

	/** Die Ausgabedatei mit dem Ergebnis bzw. der Anzahl der Flugzeuge. */
	private File outputDatei;

	/** Die Refresch Zeit für das schreiben der Daten in die Übergabe Datei in ms. */
	private int refreshTime = 1000 * 60 * 5;

	/** Die maximal angezeigten Flugzeuge in der GUI. */
	private int maxCount = 50;

	/** Die minimal angezeigten Flugzeuge in der GUI. Startet normalerweise mit 0. */
	private int minCount = 0;

	/** Die Zeit des Intervalls im ms, nach dem die Datendatei (Übergabedatei) auf das Zielsystem kopiert wird. */
	private int copyTime = 1000 * 60 * 60;

	/** Wenn true, wird die Ausgabedatei auf ein neues Ziel kopiert. */
	private boolean isCopy;

	/** Der Zieldatei Name wenn kopiert wird. */
	private File zielDatei;

	/** Die IP des Zielrechners Auf diesen Rechner wird die Datendatei kopiert. Da wo Fhem läuft. */
	private String zielIp = "pi-home";
	/** Der User für das kopieren auf das Zielsystem. */
	private String zielUser;
	/** Das Passwort zu dem Zieluser. */
	private String zielPasswort;

	/** Sichere Ath. über RSA bzw. SSH default nein. */
	private boolean isSSH;

	private File ssh_known_hosts;
	private String ssh_id_rsa;

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

	public int getCopyTime() {
		return copyTime;
	}

	public void setCopyTime(String copyTime) {
		if (copyTime != null) {
			this.copyTime = Integer.valueOf(copyTime);
		}
	}

	public void setCopyTime(int copyTime) {
		this.copyTime = copyTime;
	}

	public boolean isCopy() {
		return isCopy;
	}

	public void setCopy(boolean isCopy) {
		this.isCopy = isCopy;
	}

	public File getZielDatei() {
		return zielDatei;
	}

	public void setZielDatei(File zielDatei) {
		this.zielDatei = zielDatei;
	}

	public void setZielDatei(String zielDatei) {
		if (zielDatei != null) {
			this.zielDatei = new File(zielDatei);
		}
	}

	public String getZielIp() {
		return zielIp;
	}

	public void setZielIp(String zielIp) {
		if (zielIp != null) {
			this.zielIp = zielIp;
		}
	}

	public String getZielUser() {
		return zielUser;
	}

	public void setZielUser(String zielUser) {
		if (zielUser != null) {
			this.zielUser = zielUser;
		}
	}

	public String getZielPasswort() {
		return zielPasswort;
	}

	public void setZielPasswort(String zielPasswort) {
		if (zielPasswort != null) {
			this.zielPasswort = zielPasswort;
		}
	}

	public boolean isSSH() {
		return isSSH;
	}

	public void setSSH(boolean isSSH) {
		this.isSSH = isSSH;
	}

	public File getSsh_known_hosts() {
		return ssh_known_hosts;
	}

	public void setSsh_known_hosts(File ssh_known_hosts) {
		this.ssh_known_hosts = ssh_known_hosts;
	}

	public String getSsh_id_rsa() {
		return ssh_id_rsa;
	}

	public void setSsh_id_rsa(String ssh_id_rsa) {
		this.ssh_id_rsa = ssh_id_rsa;
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
		builder.append(", copyTime=");
		builder.append(copyTime);
		builder.append(", isCopy=");
		builder.append(isCopy);
		builder.append(", ");
		if (zielDatei != null) {
			builder.append("zielDatei=");
			builder.append(zielDatei);
			builder.append(", ");
		}
		if (zielIp != null) {
			builder.append("zielIp=");
			builder.append(zielIp);
			builder.append(", ");
		}
		if (zielUser != null) {
			builder.append("zielUser=");
			builder.append(zielUser);
			builder.append(", ");
		}
		if (zielPasswort != null) {
			builder.append("zielPasswort=");
			builder.append(zielPasswort);
			builder.append(", ");
		}
		builder.append("isSSH=");
		builder.append(isSSH);
		builder.append(", ");
		if (ssh_known_hosts != null) {
			builder.append("ssh_known_hosts=");
			builder.append(ssh_known_hosts);
			builder.append(", ");
		}
		if (ssh_id_rsa != null) {
			builder.append("ssh_id_rsa=");
			builder.append(ssh_id_rsa);
		}
		builder.append("]");
		return builder.toString();
	}

}
