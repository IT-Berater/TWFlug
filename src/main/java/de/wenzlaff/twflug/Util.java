package de.wenzlaff.twflug;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.wenzlaff.twflug.be.FieldDataRaw;
import de.wenzlaff.twflug.be.FlugInfos;
import de.wenzlaff.twflug.be.Parameter;

/**
 * Hilfsfunktionen.
 * 
 * @author Thomas Wenzlaff
 * @version 0.1
 * @since 11.11.2014
 */
public class Util {

	private static final Logger LOG = LogManager.getLogger(Util.class.getName());

	public static FieldDataRaw getFieldData(final String empfangeneNachricht) {

		if (empfangeneNachricht == null) {
			throw new IllegalArgumentException("Die empfangeneNachricht ist null");
		}
		if (empfangeneNachricht.isEmpty()) {
			throw new IllegalArgumentException("Die empfangeneNachricht ist leer");
		}

		// alle Komma separirten Felder aufteilen
		String nachricht[] = empfangeneNachricht.split(",");

		// nachricht in FieldData ablegen
		FieldDataRaw d = new FieldDataRaw();

		// alle Felder in die FieldData mappen
		d.setMessageType(nachricht[0]);
		d.setTransmissionType(nachricht[1]);
		d.setSessionId(nachricht[2]);
		d.setAircraftId(nachricht[3]);
		d.setHexIdent(nachricht[4]);
		d.setFlightId(nachricht[5]);
		d.setDateMessageGenerated(nachricht[6]);
		d.setTimeMessageGenerated(nachricht[7]);
		d.setDateMessageLogged(nachricht[8]);
		d.setTimeMessageLogged(nachricht[9]);
		d.setCallsign(nachricht[10]);
		d.setAltitude(nachricht[11]);
		d.setGroundSpeed(nachricht[12]);
		d.setTrack(nachricht[13]);
		d.setLatitude(nachricht[14]);
		d.setLongitude(nachricht[15]);
		d.setVerticalRate(nachricht[16]);
		d.setSquawk(nachricht[17]);
		d.setAlertSquawkChange(nachricht[18]);
		d.setEmergency(nachricht[19]);
		d.setSpiIdent(nachricht[20]);
		d.setIsOnGround(nachricht[21]);

		return d;
	}

	public static File getOutputDatei() {
		// Format für Fhem. Jeden Monat eine neue Datei:
		// flugdaten-%Y-%m.log
		// z.B.
		// flugdaten-2014-02.log
		Date d = new Date(System.currentTimeMillis());
		SimpleDateFormat DATE_FORMAT_YYYY_MM = new SimpleDateFormat("yyyy-MM");
		String dateiName = "flugdaten-" + DATE_FORMAT_YYYY_MM.format(d) + ".log";
		File dateiname = new File(dateiName);
		return dateiname;
	}

	public static void writeFlugdaten(final FlugInfos flugzeuge, final Parameter parameter) {

		if (flugzeuge == null || parameter == null) {
			throw new IllegalArgumentException("Die FlugInfos oder/und die Parameter sind null");
		}

		File outputDatei = parameter.getOutputDatei();

		// Speichern der Daten in eine Datei im Format:
		// 2014-01-31_15:12:00 flugdaten anzahl: 12
		String zeitstempel = getZeitstempel();
		String zeile = zeitstempel + " flugdaten anzahl: " + flugzeuge.getMaxAnzahlFlugzeuge() + System.getProperty("line.separator");

		try {
			FileUtils.writeStringToFile(outputDatei, zeile, true);
			if (parameter.isDebug()) {
				if (LOG.isInfoEnabled()) {
					LOG.info("Daten in " + outputDatei + " Datei geschrieben: " + zeile);
				}
			}
		} catch (IOException e) {
			LOG.error("Fehler beim schreiben der Flugdaten.", e);
		}
	}

	private static String getZeitstempel() {
		// 2014-01-31_15:12:00
		Date d = new Date(System.currentTimeMillis());
		SimpleDateFormat DATE_FORMAT_UND_UHRZEIT = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String zeitstempel = DATE_FORMAT_UND_UHRZEIT.format(d);
		return zeitstempel;
	}

}
