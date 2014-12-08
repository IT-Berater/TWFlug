package de.wenzlaff.twflug;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import de.wenzlaff.twflug.be.FieldDataRaw;
import de.wenzlaff.twflug.be.FlugInfos;
import de.wenzlaff.twflug.be.Parameter;
import de.wenzlaff.twflug.gui.HauptFenster;

/**
 * <pre>
 *  Der DUMP1090 Server liefert über Port 30003 die folgenden Daten.
 *  
 *  Eingabe in Browser: http://10.0.7.43:30003 
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
 * Format beschreibung hier: http://www.homepages.mcb.net/bones/SBS/Article/Barebones42_Socket_Data.htm
 * 
 * @author Thomas Wenzlaff
 * @version 0.1
 * @since 11.11.2014
 * 
 */
public class Client extends TimerTask {

	private FlugInfos flugzeuge = new FlugInfos();

	private HauptFenster hauptFenster;

	private Parameter parameter;

	void ausgabe(Parameter parameter) throws IOException {

		this.parameter = parameter;

		hauptFenster = new HauptFenster(parameter);

		Socket socket = new Socket(parameter.getIp(), parameter.getPort());

		resetFlugInfoTimer(parameter.getRefreshTime());

		while (true) {
			// lese Daten vom DUMP1090 Server, jede empfangene Nachricht
			String empfangeneNachricht = leseNachricht(socket);

			FieldDataRaw fd = Util.getFieldData(empfangeneNachricht);
			if (parameter.isDebug()) {
				System.out.println(fd);
			}

			flugzeuge.addNachricht(fd);

			hauptFenster.aktualisieren(flugzeuge.getMaxAnzahlFlugzeuge());

		}
	}

	private String leseNachricht(Socket socket) throws IOException {

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		char[] buffer = new char[200];
		int anzahlZeichen = bufferedReader.read(buffer, 0, 200);

		String nachricht = new String(buffer, 0, anzahlZeichen);
		return nachricht;
	}

	private void resetFlugInfoTimer(int ms) {
		Timer timer = new Timer();
		// in einer Minute und dann jede Minute, run() aufrufen
		timer.schedule(this, ms, ms);
	}

	@Override
	public void run() {

		Util.writeFlugdaten(flugzeuge, parameter.getOutputDatei());
		flugzeuge.setMaxAnzahlFlugzeugeAufNull();
	}

}
