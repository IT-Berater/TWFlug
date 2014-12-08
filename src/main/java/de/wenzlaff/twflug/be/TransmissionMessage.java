package de.wenzlaff.twflug.be;

/**
 * Die 8 möglichen Transmission Message.
 * 
 * @author Thomas Wenzlaff
 * @version 0.1
 * @since 11.11.2014
 *
 */
public enum TransmissionMessage {

	// ID
	//
	// Type
	//
	// Description

	// ES Identification and Category
	//
	// DF17 BDS 0,8
	MSG_1,

	// ES Surface Position Message
	// DF17 BDS 0,6
	// Triggered by nose gear squat switch.
	MSG_2,

	// ES Airborne Position Message
	// DF17 BDS 0,5
	MSG_3,

	// ES Airborne Velocity Message
	// DF17 BDS 0,9
	MSG_4,

	// Surveillance Alt Message
	// DF4, DF20
	// Triggered by ground radar. Not CRC secured.
	// MSG,5 will only be output if the aircraft has previously sent a
	// MSG,1, 2, 3, 4 or 8 signal.
	MSG_5,

	// Surveillance ID Message
	// DF5, DF21
	// Triggered by ground radar. Not CRC secured.
	// MSG,6 will only be output if the aircraft has previously sent a
	// MSG,1, 2, 3, 4 or 8 signal.
	MSG_6,

	// Air To Air Message
	// DF16
	// Triggered from TCAS.
	MSG_7,

	// is now included in the SBS socket output.
	// All Call Reply
	// DF11
	// Broadcast but also triggered by ground radar
	MSG_8

}
