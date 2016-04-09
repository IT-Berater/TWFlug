package de.wenzlaff.twflug;

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
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.wenzlaff.twflug.be.FieldDataRaw;
import de.wenzlaff.twflug.be.FlugInfos;
import de.wenzlaff.twflug.be.FlugInfosProTag;
import de.wenzlaff.twflug.be.Parameter;

/**
 * Hilfsfunktionen.
 * 
 * @author Thomas Wenzlaff
 * @version 0.1
 * @since 11.11.2014
 */
public class Util {

	/**
	 * Verzeichnis für die Ziel Datei auf dem entfernten Rechner.
	 */
	private static final String ENTFERNTER_PFAD = "/home/pi/fhem/log/";

	private static final Logger LOG = LogManager.getLogger(Util.class.getName());

	private static final DateTimeFormatter ZEITSTEMPEL_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");

	private static final DateTimeFormatter ZEITSTEMPEL_FORMAT_JAHR = DateTimeFormatter.ofPattern("yyyy-MM");

	private static final String DATEINAME_FLUGDATEN = "flugdaten-";
	private static final String DATEINAME_EXTENSION_FLUGDATEN = ".log";

	/**
	 * Liefert die Empfangene Nachricht als FieldDataRaw Objekt.
	 * 
	 * @param empfangeneNachricht
	 *            Kommasepariert.
	 * @return {@link FieldDataRaw} die Rohdaten.
	 */
	public static FieldDataRaw getFieldData(final String empfangeneNachricht) {

		if (empfangeneNachricht == null) {
			throw new IllegalArgumentException("Die empfangeneNachricht ist null");
		}
		if (empfangeneNachricht.isEmpty()) {
			throw new IllegalArgumentException("Die empfangeneNachricht ist leer");
		}

		// alle Komma separirten Felder aufteilen
		String nachricht[] = empfangeneNachricht.split(",");

		// die Nachricht in FieldData ablegen
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

		// Optionale Felder
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

	/**
	 * Liefert den Namen der OutputDatei inkl. Pfad. Der Dateiname ist mit einen Zeitstempel versehen. Format für Fhem.
	 * 
	 * Jeden Monat eine neue Datei: flugdaten-%Y-%m.log
	 * 
	 * z.B. flugdaten-2015-01.log mit führenden Nullen beim Monat
	 * 
	 * @return der Dateiname für die Output Datei.
	 */
	public static File getLokaleOutputDatei() {

		LocalDate heute = LocalDate.now();
		final String jahrMonat = ZEITSTEMPEL_FORMAT_JAHR.format(heute);
		final String dateiName = DATEINAME_FLUGDATEN + jahrMonat + DATEINAME_EXTENSION_FLUGDATEN;
		File dateiname = new File(dateiName);
		return dateiname;
	}

	/**
	 * Liefert die Zieldatei mit Path auf dem Entfernten Rechner. Ist jeden Monat eine neue Datei.
	 * 
	 * @return der Zielpath und Name
	 */
	public static File getEntfernteOutputDatei() {

		String zielDatei = ENTFERNTER_PFAD + Util.getLokaleOutputDatei();
		final File entfernteOutputDatei = new File(zielDatei);
		return entfernteOutputDatei;
	}

	private static String getZeitstempel() {
		// 2014-01-31_15:12:00
		LocalDateTime heute = LocalDateTime.now();
		String zeitstempel = ZEITSTEMPEL_FORMAT.format(heute);
		return zeitstempel;
	}

	/**
	 * Schreibt einen Eintrag der Flugdaten in die Logdatei.
	 * 
	 * Speichern der Daten in eine Datei im Format:
	 * 
	 * 2014-01-31_15:12:00 flugdaten anzahl: 12
	 * 
	 * @param flugzeuge
	 * @param parameter
	 */
	public static void writeFlugdaten(final FlugInfos flugzeuge, final Parameter parameter) {

		if (flugzeuge == null || parameter == null) {
			throw new IllegalArgumentException("Die FlugInfos oder/und die Parameter sind null");
		}

		File lokaleOutputDatei = getLokaleOutputDatei();

		final String zeitstempel = getZeitstempel();
		final String zeile = zeitstempel + " flugdaten anzahl: " + flugzeuge.getMaxAnzahlFlugzeuge() + System.getProperty("line.separator");

		try {
			FileUtils.writeStringToFile(lokaleOutputDatei, zeile, true);
			if (parameter.isDebug()) {
				if (LOG.isInfoEnabled()) {
					LOG.info("Daten in " + lokaleOutputDatei + " Datei geschrieben: " + zeile);
				}
			}
		} catch (IOException e) {
			LOG.error("Fehler beim schreiben der Flugdaten.", e);
		}
	}

	/**
	 * Schreibt einen Eintrag der Flugdaten in die Logdatei.
	 * 
	 * Speichern der Daten in eine Datei im Format:
	 * 
	 * 2014-01-31_15:12:00 flugdaten summe-pro-tag: 12
	 * 
	 * @param flugzeuge
	 * @param parameter
	 */
	public static void writeFlugdatenProTag(final FlugInfosProTag flugzeuge, final Parameter parameter) {

		if (flugzeuge == null || parameter == null) {
			throw new IllegalArgumentException("Die FlugInfosProTag oder/und die Parameter sind null");
		}

		final File lokaleOutputDatei = getLokaleOutputDatei();

		final String zeitstempel = getZeitstempel();
		final String zeile = zeitstempel + " flugdaten summe-pro-tag: " + flugzeuge.getAnzahlFlugzeugeProTag() + System.getProperty("line.separator");

		try {
			FileUtils.writeStringToFile(lokaleOutputDatei, zeile, true);
			if (parameter.isDebug()) {
				if (LOG.isInfoEnabled()) {
					LOG.info("Daten in " + lokaleOutputDatei + " Datei geschrieben: " + zeile);
				}
			}
		} catch (IOException e) {
			LOG.error("Fehler beim schreiben der Flugdaten.", e);
		}
	}

}
