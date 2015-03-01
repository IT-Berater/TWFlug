package de.wenzlaff.twflug;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.Test;

import de.wenzlaff.twflug.be.FieldDataRaw;
import de.wenzlaff.twflug.be.FlugInfos;
import de.wenzlaff.twflug.be.Parameter;

public class UtilTest {

	@Test
	public void testGetFieldData() {

		// Test Feld:--1--,2,-3-,--4--,--5---,--6---,------7---,-----8------,----9-----,-----10-----,--11----,-12-
		String data = "MSG,5,111,11111,3C6DCF,111111,2014/11/10,17:20:57.552,2014/11/10,17:20:57.543,GWI53X  ,28275,,,,,,,0,,0,0";
		FieldDataRaw raw = Util.getFieldData(data);
		assertNotNull(raw);

		assertEquals("MSG", raw.getMessageType()); // Feld 1
		assertEquals("5", raw.getTransmissionType()); // 2
		assertEquals("111", raw.getSessionId()); // 3
		assertEquals("11111", raw.getAircraftId()); // 4
		assertEquals("3C6DCF", raw.getHexIdent()); // 5
		assertEquals("111111", raw.getFlightId()); // 6
		assertEquals("2014/11/10", raw.getDateMessageGenerated()); // 7
		assertEquals("17:20:57.552", raw.getTimeMessageGenerated()); // 8
		assertEquals("2014/11/10", raw.getDateMessageLogged()); // 9
		assertEquals("17:20:57.543", raw.getTimeMessageLogged()); // 10
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetFieldDataLeer() {
		Util.getFieldData("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetFieldDataNull() {
		Util.getFieldData(null);
	}

	@Test
	public void testGetOutputDatei() {
		File datei = Util.getLokaleOutputDatei();
		assertNotNull(datei);
		assertEquals("flugdaten-2015-03.log", datei.getName());
		assertEquals("/Users/thomaswenzlaff/Java-Projekt/tw/TWFlug/flugdaten-2015-03.log", datei.getAbsolutePath());
	}

	@Test
	public void testGetEntfernteOutputDatei() {
		File datei = Util.getEntfernteOutputDatei();
		assertNotNull(datei);
		assertEquals("flugdaten-2015-03.log", datei.getName());
		assertEquals("/home/pi/fhem/log/flugdaten-2015-03.log", datei.getAbsolutePath());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWriteFlugdaten() {
		FlugInfos flugzeuge = null;
		Parameter parameter = null;
		Util.writeFlugdaten(flugzeuge, parameter);
	}

}
