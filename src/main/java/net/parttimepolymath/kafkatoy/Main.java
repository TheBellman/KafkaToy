package net.parttimepolymath.kafkatoy;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;


@Slf4j
public class Main {
    public static void main(String[] args) {

        Options options = options();
        try {
            CommandLine line = new DefaultParser().parse(options, args);

            if (line.hasOption('?')) {
                help(options);
            } else {
                if (line.hasOption("p")) {
                    Producer instance = Producer.builder()
                            .debugMode(line.hasOption('d'))
                            .build();
                    instance.run();
                }
                else {
                    help(options);
                }
            }
        } catch (ParseException ex) {
            help(options);
        }
    }

    /**
     * construct the set of options for the command line.
     * @return a non-null set of options.
     */
    private static Options options() {
        Options options = new Options();
        options.addOption(Option.builder("d").longOpt("debug").desc("enable debug mode").build());
        options.addOption(Option.builder("p").longOpt("producer").desc("run as a data producer").build());
        options.addOption((Option.builder("?").longOpt("help").desc("print this help message").build()));

        return options;
    }

    /**
     * print the command line help associated with the supplied options.
     * @param options a non-null set of CLI options.
     */
    private static void help(final Options options) {
        HelpFormatter help = new HelpFormatter();
        help.printHelp(ApplicationProperties.getAppName(), options);
    }
}