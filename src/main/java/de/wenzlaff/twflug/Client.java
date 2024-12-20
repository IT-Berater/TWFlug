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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.wenzlaff.twflug.action.AnzahlProTagAction;
import de.wenzlaff.twflug.action.CopyAction;
import de.wenzlaff.twflug.action.EmergencyAction;
import de.wenzlaff.twflug.action.WriteAction;
import de.wenzlaff.twflug.be.FieldDataRaw;
import de.wenzlaff.twflug.be.FieldDataRaw.EMERGENCY;
import de.wenzlaff.twflug.be.FlugInfos;
import de.wenzlaff.twflug.be.FlugInfosProTag;
import de.wenzlaff.twflug.be.Parameter;
import de.wenzlaff.twflug.gui.HauptFenster;

/**
 * <pre>
 *  Der DUMP1090 Server liefert über Port 30003 die folgenden Daten.
 *  
 *  Eingabe in Browser zb.: http://10.6.7.43:30003 
 *  
 *  liefert z.B:
 *  
 * MSG,5,111,11111,3C6DCF,111111,2014/11/10,17:20:57.552,2014/11/10,17:20:57.543,GWI53X  ,28275,,,,,,,0,,0,0
 * MSG,8,111,11111,3C6DCF,111111,2014/11/10,17:20:57.565,2014/11/10,17:20:57.546,,,,,,,,,,,,0
 * MSG,8,111,11111,3C6598,111111,2014/11/10,17:20:57.568,2014/11/10,17:20:57.548,,,,,,,,,,,,0
 * MSG,8,111,11111,3C6DCF,111111,2014/11/10,17:20:57.580,2014/11/10,17:20:57.551,,,,,,,,,,,,0
 * MSG,8,111,11111,3C644A,111111,2014/11/10,17:20:57.581,2014/11/10,17:20:57.552,,,,,,,,,,,,0
 * MSG,8,111,11111,3C644A,111111,2014/11/10,17:20:57.595,2014/11/10,17:20:57.555,,,,,,,,,,,,0
 * MSG,7,111,11111,3C644A,111111,2014/11/10,17:20:57.601,2014/11/10,17:20:57.558,,33000,,,,,,,,,,0
 * MSG,8,111,11111,3C4A2C,111111,2014/11/10,17:20:57.608,2014/11/10,17:20:57.607,,,,,,,,,,,,0
 * MSG,7,111,11111,3C6652,111111,2014/11/10,17:20:57.621,2014/11/10,17:20:57.611,,25825,,,,,,,,,,0
 * MSG,8,111,11111,3C4A2C,111111,2014/11/10,17:20:57.634,2014/11/10,17:20:57.614,,,,,,,,,,,,0
 * MSG,7,111,11111,3C6DCF,111111,2014/11/10,17:20:57.635,2014/11/10,17:20:57.615,,28275,,,,,,,,,,0
 * MSG,8,111,11111,3C6DCF,111111,2014/11/10,17:20:57.639,2014/11/10,17:20:57.617,,,,,,,,,,,,0
 * MSG,3,111,11111,3C644A,111111,2014/11/10,17:20:57.652,2014/11/10,17:20:57.620,,33000,,,52.28677,10.14748,,,,,,0
 * MSG,4,111,11111,3C644A,111111,2014/11/10,17:20:57.653,2014/11/10,17:20:57.622,,,363,177,,,0,,,,,0
 * MSG,4,111,11111,3C6615,111111,2014/11/10,17:20:57.661,2014/11/10,17:20:57.624,,,506,84,,,64,,,,,0
 * MSG,3,111,11111,3C6DCF,111111,2014/11/10,17:20:57.683,2014/11/10,17:20:57.674,,28275,,,52.17840,9.04167,,,,,,0
 * MSG,7,111,11111,3C644A,111111,2014/11/10,17:20:57.695,2014/11/10,17:20:57.677,,33000,,,,,,,,,,0
 * MSG,6,111,11111,3C4971,111111,2014/11/10,17:20:57.740,2014/11/10,17:20:57.739,,,,,,,,5004,0,0,0,0
 * MSG,3,111,11111,3C6615,111111,2014/11/10,17:20:57.741,2014/11/10,17:20:57.740,,37000,,,52.66655,9.51055,,,,,,0
 * </pre>
 *
 * Format beschreibung hier:
 * http://www.homepages.mcb.net/bones/SBS/Article/Barebones42_Socket_Data.htm
 * 
 * @author Thomas Wenzlaff
 * @version 0.1
 * @since 11.11.2014
 * 
 */
public class Client {

	private static final Logger LOG = LogManager.getLogger(Client.class.getName());

	/** 1 Minute Verzögerungszeit in ms. */
	private static final int DELAY = 1000 * 60;

	/**
	 * Alle Flugzeug Infos werden hier gehalten. Wird in intervallen wieder auf 0
	 * zurückgesetzt für die aktualisierungen.
	 */
	private FlugInfos flugzeuge;

	/** Objekt für die Ermittlung der Anzahl der Flugzeuge pro Tag. */
	private FlugInfosProTag flugInfosProTag;

	/** Die Komandozeilen Parameter. */
	private Parameter parameter;

	/** Die Gui, das Hauptfenster mit dem Tacho der Anzahl der Flugzeuge. */
	private HauptFenster hauptFenster;

	/** Plant die Ausführungen. */
	private ScheduledExecutorService scheduler;

	public Client() {
		scheduler = Executors.newScheduledThreadPool(1);
		flugzeuge = new FlugInfos();
		flugInfosProTag = new FlugInfosProTag();
	}

	public void start(Parameter parameter) throws IOException {

		this.parameter = parameter;

		flugzeuge.setParameter(parameter);
		flugInfosProTag.setParameter(parameter);

		if (!parameter.isNoGui()) {
			hauptFenster = new HauptFenster(parameter);
		}
		Socket socket = new Socket(parameter.getIp(), parameter.getPort());

		resetFlugInfoTimer(parameter.getRefreshTime());

		startCopyTimer();

		startAnzahlProTagTimer();

		while (true) {

			try {
				// lese Daten vom DUMP1090 Server, jede empfangene Nachricht
				String empfangeneNachricht = leseNachricht(socket);

				if (isNachrichtValid(empfangeneNachricht)) {

					FieldDataRaw fd = Util.getFieldData(empfangeneNachricht);
					if (parameter.isDebug()) {
						System.out.println(fd);
					}

					flugzeuge.addNachricht(fd);
					flugInfosProTag.addNachricht(fd);

					// Notfall eingetreten, loggen
					if (fd.isEmergency() == EMERGENCY.YES) {
						new EmergencyAction(fd, parameter);
					}

					if (!parameter.isNoGui()) {
						hauptFenster.aktualisieren(flugzeuge.getMaxAnzahlFlugzeuge());
					}
				}
			} catch (Exception e) {
				// LOG.error("Error in Message loop. " +
				// e.getLocalizedMessage(), e);
			}
		}
	}

	private boolean isNachrichtValid(String empfangeneNachricht) {
		return empfangeneNachricht.codePointCount(0, empfangeneNachricht.length()) > 9;
	}

	private String leseNachricht(Socket socket) throws IOException {

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		char[] buffer = new char[200];
		int anzahlZeichen = bufferedReader.read(buffer, 0, 200);

		String nachricht = new String(buffer, 0, anzahlZeichen);
		return nachricht;
	}

	private void resetFlugInfoTimer(int ms) {

		Timer timer = new Timer("WriteAction");
		// nach DELAY (einer Minute) und dann jede ms (5 Minute), run() aufrufen
		timer.schedule(new WriteAction(flugzeuge, parameter), DELAY, ms);
	}

	private void startCopyTimer() {

		Timer timer = new Timer("CopyAction");
		// nach DELAY/2 (einer Minute/2) und dann jede ms (30 Minute), run() aufrufen
		timer.schedule(new CopyAction(parameter), DELAY / 2, parameter.getCopyTime());
	}

	private void startAnzahlProTagTimer() {
		// einmal am Tag um Mitternacht
		Long mitternacht = LocalDateTime.now().until(LocalDate.now().plusDays(1).atStartOfDay(), ChronoUnit.MINUTES);
		scheduler.scheduleAtFixedRate(new AnzahlProTagAction(flugInfosProTag, parameter), mitternacht, 1440, TimeUnit.MINUTES);
		// Todo: zum testen alle 10 Sekunden
		// scheduler.scheduleAtFixedRate(new AnzahlProTagAction(flugInfosProTag,
		// parameter), 1, 10, TimeUnit.SECONDS);
	}
}
