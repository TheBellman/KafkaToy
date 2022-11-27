package net.parttimepolymath.kafkatoy;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;


@Slf4j
public class Main {
    public static void main(String[] args) {
        System.out.printf("%s (%s)%n",
                ApplicationProperties.getAppName(),
                ApplicationProperties.getAppVersion()
        );

        Options options = options();
        try {
            CommandLine line = new DefaultParser().parse(options, args);

            if (line.hasOption('?')) {
                help(options);
            } else {
                if (line.hasOption("p")) {
                    Producer instance = Producer.builder()
                            .messageCount(messages(line))
                            .topic(line.hasOption("t") ? line.getOptionValue("t") : ApplicationProperties.getDefaultTopic())
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
        options.addOption(Option.builder("t").longOpt("topic").desc("topic name used").build());
        options.addOption(Option.builder("n").longOpt("count").desc("number of messages to produce").hasArg().numberOfArgs(1).argName("count").build());
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

    /**
     * determine the number of messages specified in the command line.
     * The approach here is not necessarily the best way because a mal-formed option value will be
     * treated as the default.

     * @param line the non-null command line to examine
     * @return the number of messages specified, or the 0 if none.
     */
    private static int messages(final CommandLine line) {
        if (line.hasOption('n') && NumberUtils.isParsable(line.getOptionValue("n"))) {
            return NumberUtils.toInt(StringUtils.strip(line.getOptionValue("n")));
        } else {
            return 0;
        }
    }
}