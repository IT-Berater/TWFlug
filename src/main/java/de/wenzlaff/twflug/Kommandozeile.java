package de.wenzlaff.twflug;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import de.wenzlaff.twflug.be.Parameter;

/**
 * Die Verarbeitung der Kommandozeile.
 * 
 * @author Thomas Wenzlaff
 * @version 0.1
 * @since 11.11.2014
 */
public class Kommandozeile {

	private static final String ANWENDUNG_NAME = "TWFlug";
	private static final String ANWENDUNG_VERSION = "0.0.1";

	public static Parameter parseCommandline(String[] args) {
		// create the command line parser
		CommandLineParser parser = new BasicParser();

		// create the Options
		Options options = new Options();

		Option help = new Option("h", "help", false, "print help and exit");
		options.addOption(help);

		options.addOption("n", "no-gui", false, "display no GUI. Only logfile output");

		Option version = new Option("v", "version", false, "print the version information and exit");
		options.addOption(version);

		Option debug = new Option("d", "debug", false, "print debugging information");
		options.addOption(debug);

		options.addOption(OptionBuilder.withLongOpt("ip").withDescription("ip adress from DUMP1090").hasArg().create("i"));
		options.addOption(OptionBuilder.withLongOpt("port").withDescription("port from DUMP1090").hasArg().create("p"));

		options.addOption(OptionBuilder.withLongOpt("max-count").withDescription("set max count value").hasArg().create("max"));
		options.addOption(OptionBuilder.withLongOpt("min-count").withDescription("set min count value").hasArg().create("min"));

		options.addOption(OptionBuilder.withLongOpt("window-width").withDescription("set window with").hasArg().create("width"));
		options.addOption(OptionBuilder.withLongOpt("window-height").withDescription("set window hight").hasArg().create("height"));

		options.addOption(OptionBuilder.withArgName("file").hasArg().withDescription("use given file for DUMP output").withLongOpt("outputfile").create("o"));

		options.addOption(OptionBuilder.withLongOpt("refresh-time").withDescription("refresh time in ms").hasArg().create("r"));

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

			parameter.setOutputDatei(line.getOptionValue("o"));
			parameter.setRefreshTime(line.getOptionValue("r"));

			if (line.hasOption("d")) {
				parameter.setDebug(true);
			}

			if (line.hasOption("n")) {
				parameter.setNoGui(true);
			}

			if (line.hasOption("v")) {
				System.out.println(ANWENDUNG_NAME + " " + ANWENDUNG_VERSION);
				return null;
			}
			if (line.hasOption("h")) {
				printHelp(options);
				return null;
			}

			if (parameter.isDebug()) {
				System.out.println("Starte " + ANWENDUNG_NAME + " mit Parameter: " + parameter);
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
