package de.wenzlaff.twflug;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import de.wenzlaff.twflug.be.Aircraft;
import de.wenzlaff.twflug.be.FieldDataRaw;

public class Json {

	public static void main(String args[]) {

		// generate JSON String in Java
		// writeJson("book.json");

		// let's read
		readJson("aircraft.json");
	}

	public static void readJson(String file) {
		JSONParser parser = new JSONParser();

		try {
			System.out.println("Reading JSON file from Java program");
			FileReader fileReader = new FileReader(file);
			JSONObject json = (JSONObject) parser.parse(fileReader);

			// Most fields should be selfexplanatory, except:

			// "now" is seconds-since-Jan-1-1970 when the JSON was generated;

			// "seen" is the number of seconds before "now" when a message was last seen;

			// "seen_pos" is the number of seconds before "now" when the position fields were last updated;

			// "rssi" is received signal strength of recent messages in dBFS.
			//
			// In the next release there will also be data/stats.json which has all of the reported stats from dump1090 in json form, also with last 1/5/15 min
			// aggregation (and, uh, not very many newlines):

			Aircraft f = new Aircraft();

			Long nowSekunden = (Long) json.get("now") * 1000;
			f.setNow(nowSekunden);

			// It is a counter of the total number of valid Mode S messages processed since dump1090 started; it is used on the webmap to show a message rate.
			// Same as the sum of data/stats.json: total.local.accepted[0..n] + total.remote.accepted[0..n], it is just in aircraft.json so the map doesn't have
			// to fetch an extra file all the time.

			Long validMessageFromStart = (Long) json.get("messages");
			f.setMessages(validMessageFromStart);

			System.out.println("now: " + nowSekunden + ", " + new Date(nowSekunden));
			System.out.println("message: " + validMessageFromStart + ", " + new Date(validMessageFromStart));
			System.out.println("Ausgabe: " + f);

			JSONArray aircraft = (JSONArray) json.get("aircraft");
			for (Object flugzeug : aircraft) {
				// {"seen_pos":2,"flight":"GWI87L  ","altitude":28825,"rssi":-33.8,"vert_rate":1408,"lon":9.578273,"speed":314,
				// "seen":0,"squawk":"1104","messages":529,"hex":"3c5ef3","track":188,"lat":52.787538}
				FieldDataRaw feld = new FieldDataRaw();
				JSONObject flug = (JSONObject) flugzeug;

				Double lon = (Double) flug.get("lon");
				if (lon != null) {
					String longitude = Double.toString(lon);
					feld.setLongitude(longitude);
				}
				Double lat = (Double) flug.get("lat");
				if (lat != null) {
					String latitude = Double.toString(lat);
					feld.setLatitude(latitude);
				}
				Object altitude = flug.get("altitude");
				if (altitude != null) {
					if (altitude instanceof String) {
						// ground
						feld.setAltitude((String) altitude);
					} else if (altitude instanceof Double) {
						String altitudeStr = Double.toString((Double) altitude);
						feld.setAltitude(altitudeStr);
					}
				}
				String hex = (String) flug.get("hex");
				if (hex != null) {
					feld.setHexIdent(hex);
				}

				Long speed = (Long) flug.get("speed");
				if (speed != null) {
					String speedStr = Double.toString(speed);
					feld.setGroundSpeed(speedStr);
				}

				String squawk = (String) flug.get("squawk");
				if (squawk != null) {
					feld.setSquawk(squawk);
				}

				Long track = (Long) flug.get("track");
				if (track != null) {
					String trackStr = Double.toString(track);
					feld.setTrack(trackStr);
				}

				Long vertRate = (Long) flug.get("vert_rate");
				if (vertRate != null) {
					String vertRateStr = Double.toString(vertRate);
					feld.setVerticalRate(vertRateStr);
				}

				String flugNummer = (String) flug.get("flight");
				if (flugNummer != null) {
					feld.setFlightId(flugNummer);
				}

				f.addAircraft(feld);

				System.out.println(feld);
			}

			System.out.println("Anzahl Flugzeuge: " + aircraft.size());

			System.out.println(f);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * Java Method to write JSON String to file
	 */
	public static void writeJson(String file) {
		JSONObject json = new JSONObject();
		json.put("title", "Der Titel");
		json.put("author", "von Thomas");

		JSONArray jsonArray = new JSONArray();
		jsonArray.add("Flug");
		jsonArray.add("Was");
		jsonArray.add("Alle");

		json.put("characters", jsonArray);

		try {
			System.out.println("Writting JSON into file ...");
			System.out.println(json);
			FileWriter jsonFileWriter = new FileWriter(file);
			jsonFileWriter.write(json.toJSONString());
			jsonFileWriter.flush();
			jsonFileWriter.close();
			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
