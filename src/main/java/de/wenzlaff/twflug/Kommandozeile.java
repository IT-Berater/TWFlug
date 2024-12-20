package de.wenzlaff.twflug;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.wenzlaff.twflug.be.Parameter;

/**
 * Die Verarbeitung der Kommandozeile.
 * 
 * @author Thomas Wenzlaff
 */
public class Kommandozeile {

	private static final Logger LOG = LogManager.getLogger(Kommandozeile.class.getName());

	private static final String ANWENDUNG_NAME = "TWFlug";
	private static final String ANWENDUNG_VERSION = "0.1.0";
	private static final String ANWENDUNG_UND_VERSION = ANWENDUNG_NAME + " " + ANWENDUNG_VERSION;

	@SuppressWarnings("static-access")
	public static Parameter parseCommandline(String[] args) {
		// create the command line parser
		CommandLineParser parser = new BasicParser();

		// create the Options
		Options options = new Options();

		Option help = new Option("h", "help", false, "print help and exit");
		options.addOption(help);

		options.addOption("n", "no-gui", false, "display no GUI. Only logfile output (default: false) ");

		Option version = new Option("v", "version", false, "print the version information and exit");
		options.addOption(version);

		Option debug = new Option("d", "debug", false, "print debugging information (default: false)");
		options.addOption(debug);

		options.addOption(OptionBuilder.withLongOpt("ip").withDescription("ip adress from DUMP1090").hasArg().isRequired().create("i"));
		options.addOption(OptionBuilder.withLongOpt("port").withDescription("port from DUMP1090 (default: 30003)").hasArg().create("p"));

		options.addOption(OptionBuilder.withLongOpt("max-count").withDescription("set max count value (default: 50)").hasArg().create("max"));
		options.addOption(OptionBuilder.withLongOpt("min-count").withDescription("set min count value (default: 0) ").hasArg().create("min"));

		options.addOption(OptionBuilder.withLongOpt("window-width").withDescription("set window with (default: 600)").hasArg().create("width"));
		options.addOption(OptionBuilder.withLongOpt("window-height").withDescription("set window hight (default: 600)").hasArg().create("height"));

		options.addOption(OptionBuilder.withLongOpt("refresh-time").withDescription("refresh time in ms (default: 300000 ms = 5 Minuten)").hasArg().create("r"));

		options.addOption(OptionBuilder.withLongOpt("copy-time").withDescription("copy time in Minuten (default: 60 Minuten)").hasArg().create("c"));

		options.addOption("k", "copy", false, "copy output file to destination (default: false)");

		options.addOption(OptionBuilder.withLongOpt("ziel-ip").withDescription("ip adress for copy destination").hasArg().create("dip"));
		options.addOption(OptionBuilder.withLongOpt("ziel-user").withDescription("destination User (default: pi").hasArg().create("duser"));
		options.addOption(OptionBuilder.withLongOpt("ziel-passwort").withDescription("passwort from destination User").hasArg().create("dpsw"));

		Parameter parameter = new Parameter();

		try {
			// parse the command line arguments
			CommandLine line = parser.parse(options, args);

			parameter.setIp(line.getOptionValue("i"));
			parameter.setPort(line.getOptionValue("p"));

			parameter.setMaxCount(line.getOptionValue("max"));
			parameter.setMinCount(line.getOptionValue("min"));

			parameter.setBreite(line.getOptionValue("width"));
			parameter.setHoehe(line.getOptionValue("height"));

			// Ziel Passwort nicht vorhanden setzen
			if (line.hasOption("dpsw")) {
				// wenn Passwort angegeben auch setzen
				parameter.setZielPasswort(line.getOptionValue("dpsw"));
			}

			// Ziel User nicht vorhanden setzen
			if (line.hasOption("duser")) {
				// wenn User angegeben auch setzen
				parameter.setZielUser(line.getOptionValue("duser"));
			} else {
				// default ziel User
				parameter.setZielUser("pi");
			}

			// Ziel IP nicht vorhanden setzen
			if (line.hasOption("dip")) {
				// wenn Ausgabe Datei angegeben
				parameter.setZielIp(line.getOptionValue("dip"));
			} else {
				// default ziel IP
				parameter.setZielIp("pi-home");
			}

			// ThingSpeak nicht vorhanden setzen
			if (line.hasOption("channel")) {
				// wenn Channel gesetzt angegeben
				parameter.setThingSpeakChannelId(Integer.valueOf(line.getOptionValue("channel")));
			} else {
				// default Channel
				parameter.setThingSpeakChannelId(null);
			}

			parameter.setRefreshTime(line.getOptionValue("r"));

			parameter.setCopyTime(line.getOptionValue("c"));

			if (line.hasOption("d")) {
				System.out.println("Starte " + ANWENDUNG_UND_VERSION + " im Debug Modus ...");
				LOG.debug("Starte " + ANWENDUNG_UND_VERSION + " im Debug Modus ...");
				parameter.setDebug(true);
			}

			if (line.hasOption("n")) {
				System.out.println("Starte " + ANWENDUNG_UND_VERSION + " ohne GUI ...");
				parameter.setNoGui(true);
			}
			if (line.hasOption("k")) {
				parameter.setCopy(true);
			}

			if (line.hasOption("v")) {
				System.out.println(ANWENDUNG_UND_VERSION);
				return null;
			}
			if (line.hasOption("h")) {
				printHelp(options);
				return null;
			}

			if (parameter.isDebug()) {
				System.out.println("Starte " + ANWENDUNG_UND_VERSION + " mit Parameter: " + parameter);
			}

			if (LOG.isDebugEnabled()) {
				LOG.debug("Starte " + ANWENDUNG_UND_VERSION + " mit Parameter: " + parameter);
			}

		} catch (ParseException exp) {
			System.out.println("Fehler beim parsen der Kommandozeile: " + exp.getMessage());
			printHelp(options);
			return null;
		}
		return parameter;
	}

	private static void printHelp(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp(ANWENDUNG_NAME, options);
	}
}
