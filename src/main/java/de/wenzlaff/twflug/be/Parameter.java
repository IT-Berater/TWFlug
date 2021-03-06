package de.wenzlaff.twflug.be;

/*
 * #%L
 * twflug
 * %%
 * Copyright (C) 2015 Thomas Wenzlaff
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Die Programm Parameter
 * 
 * @author Thomas Wenzlaff
 * @version 0.1
 * @since 11.11.2014
 */
public class Parameter {

	@SuppressWarnings("unused")
	private static final Logger LOG = LogManager.getLogger(Parameter.class.getName());

	/** Die IP Adresse an der gelauscht wird wo der DUMP1090 Server läuft. */
	private String ip;

	/** Der Port an dem gelauscht wird. */
	private int port = 30003;

	/** Die Fenster Breite in Pixel der Anwendung. */
	private int breite = 600;

	/** Die Fenster Höhe in Pixel der Anwendung. */
	private int hoehe = 600;

	/** Wenn true, dann Debug Modus, zeigt mehr Infos an. */
	private boolean isDebug;

	/** Wenn true, wird keine GUI angezeigt. */
	private boolean isNoGui;

	/**
	 * Die Refresch Zeit für das schreiben der Daten in die Übergabe Datei in ms.
	 */
	private int refreshTime = 1000 * 60 * 5;

	/** Die maximal angezeigten Flugzeuge in der GUI. */
	private int maxCount = 50;

	/**
	 * Die minimal angezeigten Flugzeuge in der GUI. Startet normalerweise mit 0.
	 */
	private int minCount = 0;

	/**
	 * Die Zeit des Intervalls im ms, nach dem die Datendatei (Übergabedatei) auf das Zielsystem kopiert wird.
	 */
	private int copyTime = 1000 * 60 * 60;

	/** Alle 5 Minuten wird der Web-Service ThingSpeak aktualisiert. */
	private long sentToThingSpeakTime = 1000 * 60 * 5;

	/** Wenn true, wird die Ausgabedatei auf ein neues Ziel kopiert. */
	private boolean isCopy;

	/**
	 * Die IP des Zielrechners Auf diesen Rechner wird die Datendatei kopiert. Da wo Fhem läuft.
	 */
	private String zielIp;
	/** Der User für das kopieren auf das Zielsystem. */
	private String zielUser;
	/** Das Passwort zu dem Zieluser. */
	private String zielPasswort;

	/** Sichere Ath. über RSA bzw. SSH default nein. */
	private boolean isSSH;

	private File ssh_known_hosts;
	private String ssh_id_rsa;

	/** Der lese Key für den ThingSpeak Web-Service. */
	private String thingSpeakApiWriteKey;

	/** Die Channel ID für den ThingSpeak Web-Service. */
	private Integer thingSpeakChannelId;

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

	public long getSendToThingSpeakTime() {
		return this.sentToThingSpeakTime;
	}

	public boolean isCopy() {
		return isCopy;
	}

	public void setCopy(boolean isCopy) {
		this.isCopy = isCopy;
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

	public String getThingSpeakApiWriteKey() {
		return this.thingSpeakApiWriteKey;
	}

	public Integer getThingSpeakChannelId() {
		return this.thingSpeakChannelId;
	}

	public void setThingSpeakApiWriteKey(String thingSpeakApiWriteKey) {
		this.thingSpeakApiWriteKey = thingSpeakApiWriteKey;
	}

	public void setThingSpeakChannelId(Integer thingSpeakChannelId) {
		this.thingSpeakChannelId = thingSpeakChannelId;
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
			builder.append("*****");
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
