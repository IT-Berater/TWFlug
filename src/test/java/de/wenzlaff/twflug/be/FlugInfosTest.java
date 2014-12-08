package de.wenzlaff.twflug.be;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class FlugInfosTest {

	private FlugInfos flugInfo;

	@Before
	public void setUp() {
		flugInfo = new FlugInfos();
	}

	@Test
	public void testFlugInfos() {
		assertNotNull(flugInfo);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddNull() {
		FieldDataRaw nachricht = null;
		flugInfo.addNachricht(nachricht);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddOhneKey() {
		FieldDataRaw nachricht = new FieldDataRaw();
		flugInfo.addNachricht(nachricht);
		assertEquals(0, flugInfo.getMaxAnzahlFlugzeuge());
	}

	@Test
	public void testAddEinSatz() {
		FieldDataRaw nachricht = new FieldDataRaw();
		nachricht.setHexIdent("123456");
		flugInfo.addNachricht(nachricht);
		assertEquals(1, flugInfo.getMaxAnzahlFlugzeuge());
	}

	@Test
	public void testAddZweiSatz() {
		FieldDataRaw nachricht = new FieldDataRaw();
		nachricht.setHexIdent("123456");
		flugInfo.addNachricht(nachricht);

		FieldDataRaw nachricht2 = new FieldDataRaw();
		nachricht2.setHexIdent("2222");
		flugInfo.addNachricht(nachricht2);
		assertEquals(2, flugInfo.getMaxAnzahlFlugzeuge());
	}

	@Test
	public void testAddZweiSatzZweiNachrichten() {
		FieldDataRaw nachricht = new FieldDataRaw();
		nachricht.setHexIdent("123456");
		flugInfo.addNachricht(nachricht);

		FieldDataRaw nachricht2 = new FieldDataRaw();
		nachricht2.setHexIdent("2222");
		flugInfo.addNachricht(nachricht2);
		nachricht.setAircraftId("2");
		flugInfo.addNachricht(nachricht2);
		assertEquals(2, flugInfo.getAlleNachrichtenZumFlugzeug("2222").size());
		assertEquals(1, flugInfo.getAlleNachrichtenZumFlugzeug("123456").size());

		assertEquals(2, flugInfo.getMaxAnzahlFlugzeuge());
	}
}
