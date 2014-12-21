package de.wenzlaff.twflug.be;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * FieldDataRaw sind 22 Felder getrennt durch Komma.
 * 
 * Key Feld eindeutig: hexIdent
 * 
 * Alle als String so wie sie vom DUMP1090 Server geliefert werden. Alle als String.
 * 
 * http://www.homepages.mcb.net/bones/SBS/Article/Barebones42_Socket_Data.htm
 * 
 * @author Thomas Wenzlaff
 * @version 0.1
 * @since 11.11.2014
 */
public class FieldDataRaw {

	@SuppressWarnings("unused")
	private static final Logger LOG = LogManager.getLogger(FieldDataRaw.class.getName());

	/**
	 * Flag das anzeigt ob das Flugzeug auf dem Boden ist.
	 * 
	 * @author Thomas Wenzlaff
	 *
	 */
	public enum ON_GROUND {
		/** Flugzeug am Boden. */
		YES,
		/** Flugzeug nicht am Boden. In der Luft. */
		NO,
		/** Unbekannter Flugstatus. Nicht benutzt. */
		NOT_USED
	}

	/**
	 * Flag das anzeigt ob das Flugzeug einen Notfall meldet.
	 * 
	 * @author Thomas Wenzlaff
	 *
	 */
	public enum EMERGENCY {
		/** Notfall. */
		YES,
		/** Kein Notfall */
		NO,
		/** Unbekannter Notfallstatus. Nicht benutzt. */
		NOT_USED
	}

	/**
	 * Flag das anzeigt ob der Transponder aktiviert wurde.
	 * 
	 * @author Thomas Wenzlaff
	 *
	 */
	public enum SPI_IDENT {
		/** Yes. */
		YES,
		/** No */
		NO,
		/** Nicht benutzt. */
		NOT_USED
	}

	/**
	 * Flag das anzeigt ob Squawk gewechselt wurde.
	 * 
	 * @author Thomas Wenzlaff
	 *
	 */
	public enum SQUAWK_CHANGE {
		/** Yes. */
		YES,
		/** No */
		NO,
		/** Nicht benutzt. */
		NOT_USED
	}

	// Field 1-10: The above basic data fields are standard for all messages

	/**
	 * (Field 2 used only for MSG). Field 1: Message type (MSG, STA, ID, AIR, SEL or CLK)
	 */
	private String messageType;

	/**
	 * Field 2: Transmission Type MSG sub types 1 to 8. Not used by other message types.
	 */
	private String transmissionType;

	/**
	 * Field 3: Session ID Database Session record number
	 */
	private String sessionId;

	/**
	 * Field 4: AircraftID Database Aircraft record number
	 */
	private String aircraftId;

	/**
	 * Field 5: HexIdent Aircraft Mode S hexadecimal code. Key Feld. Kann das Flugzeug eindeutig identifzieren.
	 */
	private String hexIdent;

	/**
	 * Field 6: FlightID Database Flight record number
	 */
	private String flightId;

	/**
	 * Field 7: Date message generated As it says
	 */
	private String dateMessageGenerated;

	/**
	 * Field 8: Time message generated As it says
	 */
	private String timeMessageGenerated;

	/**
	 * Field 9: Date message logged As it says
	 */
	private String dateMessageLogged;

	/**
	 * Field 10: Time message logged As it says
	 */
	private String timeMessageLogged;

	// Field 11-22: The fields below contain specific aircraft information.

	/**
	 * Field 11: Callsign An eight digit flight ID - can be flight number or registration (or even nothing). Field 11 (Callsign) is an 8 character (6 bit ASCII
	 * subset) field. In BaseStation a NULL is shown as a '@' which is ASCII for NULL. In the cockpit it is just a space on the transponder window, but is sent
	 * as a NULL. Therefore, if a crew enter eight spaces in the cockpit this will show in BaseStation as @@@@@@@@.
	 */
	private String callsign;

	/**
	 * Field 12: Altitude Mode C altitude. Height relative to 1013.2mb (Flight Level). Not height AMSL. Field 12 (Altitude) can be 25ft or 100 foot resolution.
	 * Mode-C is 100 ft, but many aircraft today send out 25 ft resolution to be able to fly in Europe IFR (RVSM) space. BaseStation only displays Barometer
	 * altitude but in the data are HAE (height above ellipsoid), which is sent as the difference between GPS altitude and barometric altitude.
	 */
	private String altitude;

	/**
	 * Field 13: GroundSpeed Speed over ground (not indicated airspeed)
	 */
	private String groundSpeed;

	/**
	 * Field 14: Track Track of aircraft (not heading). Derived from the velocity E/W and velocity N/S
	 */
	private String track;

	/**
	 * Field 15: Latitude North and East positive. South and West negative.
	 */
	private String latitude;

	/**
	 * Field 16: Longitude North and East positive. South and West negative.
	 */
	private String longitude;

	/**
	 * Field 17: VerticalRate 64ft resolution
	 */
	private String verticalRate;

	/**
	 * Field 18: Squawk Assigned Mode A squawk code.
	 */
	private String squawk;

	/**
	 * Field 19: Alert (Squawk change) Flag to indicate squawk has changed. The socket data outputs a -1 for true, and a 0 for false. Neither means it is not
	 * used.
	 */
	private String alertSquawkChange;

	/**
	 * Field 20: Emergency Flag to indicate emergency code has been set The socket data outputs a -1 for true, and a 0 for false. Neither means it is not used.
	 */
	private String emergency;

	/**
	 * Field 21: SPI (Ident) Flag to indicate transponder Ident has been activated. The socket data outputs a -1 for true, and a 0 for false. Neither means it
	 * is not used.
	 */
	private String spiIdent;

	/**
	 * Field 22: IsOnGround Flag to indicate ground squat switch is active The socket data outputs a -1 for true, and a 0 for false. Neither means it is not
	 * used.
	 */
	private String isOnGround;

	private static final SimpleDateFormat DATUMS_FORMAT_DE = new SimpleDateFormat("dd.MM.YY");

	private static final DateFormat DATUMS_FORMAT_US = new SimpleDateFormat("yyyy/MM/dd");

	public FieldDataRaw() {
		super();
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getTransmissionType() {
		return transmissionType;
	}

	public void setTransmissionType(String transmissionType) {
		this.transmissionType = transmissionType;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getAircraftId() {
		return aircraftId;
	}

	public void setAircraftId(String aircraftId) {
		this.aircraftId = aircraftId;
	}

	public String getHexIdent() {
		return hexIdent;
	}

	public void setHexIdent(String hexIdent) {
		this.hexIdent = hexIdent;
	}

	public String getFlightId() {
		return flightId;
	}

	public void setFlightId(String flightId) {
		this.flightId = flightId;
	}

	public String getDateMessageGenerated() {
		return dateMessageGenerated;
	}

	public void setDateMessageGenerated(String dateMessageGenerated) {
		this.dateMessageGenerated = dateMessageGenerated;
	}

	public String getTimeMessageGenerated() {
		return timeMessageGenerated;
	}

	public void setTimeMessageGenerated(String timeMessageGenerated) {
		this.timeMessageGenerated = timeMessageGenerated;
	}

	public String getDateMessageLogged() {
		return dateMessageLogged;
	}

	public void setDateMessageLogged(String dateMessageLogged) {
		this.dateMessageLogged = dateMessageLogged;
	}

	public String getTimeMessageLogged() {
		return timeMessageLogged;
	}

	public void setTimeMessageLogged(String timeMessageLogged) {
		this.timeMessageLogged = timeMessageLogged;
	}

	public String getCallsign() {
		return callsign;
	}

	public void setCallsign(String callsign) {
		this.callsign = callsign;
	}

	public String getAltitude() {
		return altitude;
	}

	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}

	public String getGroundSpeed() {
		return groundSpeed;
	}

	public void setGroundSpeed(String groundSpeed) {
		this.groundSpeed = groundSpeed;
	}

	public String getTrack() {
		return track;
	}

	public void setTrack(String track) {
		this.track = track;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getVerticalRate() {
		return verticalRate;
	}

	public void setVerticalRate(String verticalRate) {
		this.verticalRate = verticalRate;
	}

	public String getSquawk() {
		return squawk;
	}

	public void setSquawk(String squawk) {
		this.squawk = squawk;
	}

	public SQUAWK_CHANGE isSquawkChangey() {
		if (alertSquawkChange == null) {
			return SQUAWK_CHANGE.NOT_USED;
		} else if (alertSquawkChange.equals("0")) {
			return SQUAWK_CHANGE.NO;
		} else if (alertSquawkChange.equals("1")) {
			return SQUAWK_CHANGE.YES;
		} else {
			return SQUAWK_CHANGE.NOT_USED;
		}
	}

	public String getAlertSquawkChange() {
		return alertSquawkChange;
	}

	public void setAlertSquawkChange(String alertSquawkChange) {
		this.alertSquawkChange = alertSquawkChange;
	}

	public String getEmergency() {
		return emergency;
	}

	public void setEmergency(String emergency) {
		this.emergency = emergency;
	}

	public EMERGENCY isEmergency() {
		if (emergency == null) {
			return EMERGENCY.NOT_USED;
		} else if (emergency.equals("0")) {
			return EMERGENCY.NO;
		} else if (emergency.equals("1")) {
			return EMERGENCY.YES;
		} else {
			return EMERGENCY.NOT_USED;
		}
	}

	public String getSpiIdent() {
		return spiIdent;
	}

	public void setSpiIdent(String spiIdent) {
		this.spiIdent = spiIdent;
	}

	public SPI_IDENT isSpiIdent() {
		if (spiIdent == null) {
			return SPI_IDENT.NOT_USED;
		} else if (spiIdent.equals("0")) {
			return SPI_IDENT.NO;
		} else if (spiIdent.equals("1")) {
			return SPI_IDENT.YES;
		} else {
			return SPI_IDENT.NOT_USED;
		}
	}

	public String getIsOnGround() {
		return isOnGround;
	}

	public ON_GROUND isOnGround() {
		if (isOnGround == null) {
			return ON_GROUND.NOT_USED;
		} else if (isOnGround.equals("0")) {
			return ON_GROUND.NO;
		} else if (isOnGround.equals("1")) {
			return ON_GROUND.YES;
		} else {
			return ON_GROUND.NOT_USED;
		}
	}

	public void setIsOnGround(String isOnGround) {
		this.isOnGround = isOnGround.trim(); // am letzten Datesatz hängt immer /n/r das wird hier gelöscht
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hexIdent == null) ? 0 : hexIdent.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FieldDataRaw other = (FieldDataRaw) obj;

		if (hexIdent == null) {
			if (other.hexIdent != null)
				return false;
		} else if (!hexIdent.equals(other.hexIdent))
			return false;
		return true;
	}

	public String getDatumFormatiert(Date date) {
		return DATUMS_FORMAT_DE.format(date);
	}

	public Date getMessageLogged() {
		Date date = parseDatum(dateMessageLogged);
		return date;
	}

	public Date getMessageGenerated() {
		Date date = parseDatum(dateMessageGenerated);
		return date;
	}

	private Date parseDatum(String datum) {

		Date date = null;
		try {
			date = DATUMS_FORMAT_US.parse(datum);
		} catch (ParseException e) {
			// TODO: was soll hier gemacht werden new Date() klapt nicht
			System.out.println("Parse Error mit Datum (" + datum + ") deshalb mit Systemdatum gefixt");
			date = new Date(System.currentTimeMillis());
			System.out.println("Verwende als Datum nun: " + datum);
		}
		return date;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FieldDataRaw: ");
		// alle Felder in der Reihenfolge wie sie kommen, nur hexIdent nach vorne geholt
		// Feld 1-10 bei allen MSG gefüllt
		if (hexIdent != null && !hexIdent.isEmpty()) {
			// Feld 5
			builder.append("hexIdent=");
			builder.append(hexIdent);
			builder.append(", ");
		}
		if (messageType != null && !messageType.isEmpty()) {
			// Feld 1
			builder.append("messageType=");
			builder.append(messageType);
			builder.append(", ");
		}
		if (transmissionType != null && !transmissionType.isEmpty()) {
			// Feld 2
			builder.append("transmissionType=");
			builder.append(transmissionType);
			builder.append(", ");
		}
		if (sessionId != null && !sessionId.isEmpty()) {
			// Feld 3
			builder.append("sessionId=");
			builder.append(sessionId);
			builder.append(", ");
		}
		if (aircraftId != null && !aircraftId.isEmpty()) {
			// Feld 4
			builder.append("aircraftId=");
			builder.append(aircraftId);
			builder.append(", ");
		}

		if (flightId != null && !flightId.isEmpty()) {
			// Feld 6
			builder.append("flightId=");
			builder.append(flightId);
			builder.append(", ");
		}
		if (dateMessageGenerated != null && !dateMessageGenerated.isEmpty()) {
			// Feld 7
			builder.append("dateMessageGenerated=");
			builder.append(getDatumFormatiert(getMessageGenerated()));
			builder.append(", ");
		}
		if (timeMessageGenerated != null && !timeMessageGenerated.isEmpty()) {
			// Feld 8
			builder.append("timeMessageGenerated=");
			builder.append(timeMessageGenerated);
			builder.append(", ");
		}
		if (dateMessageLogged != null && !dateMessageLogged.isEmpty()) {
			// Feld 9
			builder.append("dateMessageLogged=");
			builder.append(getDatumFormatiert(getMessageLogged()));
			builder.append(", ");
		}
		if (timeMessageLogged != null && !timeMessageLogged.isEmpty()) {
			// Feld 10
			builder.append("timeMessageLogged=");
			builder.append(timeMessageLogged);
			builder.append(", ");
		}
		// Feld 11-22 nicht immer gefüllt, je nach Msg Typ
		if (callsign != null && !callsign.isEmpty()) {
			// Feld 10
			builder.append("callsign=");
			builder.append(callsign);
			builder.append(", ");
		}
		if (altitude != null && !altitude.isEmpty()) {
			// Feld 12
			builder.append("altitude=");
			builder.append(altitude);
			builder.append(", ");
		}
		if (groundSpeed != null && !groundSpeed.isEmpty()) {
			// Feld 13
			builder.append("groundSpeed=");
			builder.append(groundSpeed);
			builder.append(", ");
		}
		if (track != null && !track.isEmpty()) {
			// Feld 14
			builder.append("track=");
			builder.append(track);
			builder.append(", ");
		}
		if (latitude != null && !latitude.isEmpty()) {
			// Feld 15
			builder.append("latitude=");
			builder.append(latitude);
			builder.append(", ");
		}
		if (longitude != null && !longitude.isEmpty()) {
			// Feld 16
			builder.append("longitude=");
			builder.append(longitude);
			builder.append(", ");
		}
		if (verticalRate != null && !verticalRate.isEmpty()) {
			// Feld 17
			builder.append("verticalRate=");
			builder.append(verticalRate);
			builder.append(", ");
		}
		if (squawk != null && !squawk.isEmpty()) {
			// Feld 18
			builder.append("squawk=");
			builder.append(squawk);
			builder.append(", ");
		}
		if (alertSquawkChange != null && !alertSquawkChange.isEmpty() && isSquawkChangey() != SQUAWK_CHANGE.NOT_USED) {
			// Feld 19
			builder.append("alertSquawkChange=");
			builder.append(isSquawkChangey());
			builder.append(", ");
		}
		if (emergency != null && !emergency.isEmpty() && isEmergency() != EMERGENCY.NOT_USED) {
			// Feld 20
			builder.append("emergency=");
			builder.append(isEmergency());
			builder.append(", ");
		}
		if (spiIdent != null && !spiIdent.isEmpty() && isSpiIdent() != SPI_IDENT.NOT_USED) {
			// Feld 21
			builder.append("spiIdent=");
			builder.append(isSpiIdent());
			builder.append(", ");
		}
		if (isOnGround != null && !isOnGround.isEmpty() && isOnGround() != ON_GROUND.NOT_USED) {
			// Feld 22
			builder.append("isOnGround=");
			builder.append(isOnGround());
		}
		return builder.toString();
	}

}
