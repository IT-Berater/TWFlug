package de.wenzlaff.twflug;

import java.io.IOException;

import de.wenzlaff.twflug.be.Parameter;

/**
 * Die Hauptanwendung die gestartet wird.
 * 
 * Damit es im rPI läuft:
 * 
 * In your SSH session type:
 *
 * export DISPLAY=:0.0
 * 
 * or
 * 
 * If you put that line into /etc/profile (or ~/.bashrc) it'll set it automatically at startup / when you log in.
 */
public class TWFlug {

	public static void main(String[] args) {

		Parameter parameter = Kommandozeile.parseCommandline(args);
		if (parameter == null) {
			return;
		}
		OSXAppearance.applyIfApplicable();

		try {
			Client client = new Client();
			client.ausgabe(parameter);
		} catch (IOException e) {
			System.out.println("Fehler beim Starten des Client: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
