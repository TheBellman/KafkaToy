package net.parttimepolymath.kafkatoy;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;


@Slf4j
public class Main {

    public static final String OPT_PRODUCER = "p";
    public static final String OPT_TOPIC = "t";
    public static final String OPT_COUNT = "n";
    public static final String OPT_BOOTSTRAP = "b";
    public static final String OPT_HELP = "?";
    public static final String OPT_CONSUMER = "c";

    public static void main(String[] args) {
        System.out.printf("%s (%s)%n", ApplicationProperties.getAppName(), ApplicationProperties.getAppVersion());

        Options options = options();
        try {
            CommandLine line = new DefaultParser().parse(options, args);

            if (line.hasOption(OPT_HELP)) {
                help(options);
            } else {
                if (line.hasOption(OPT_PRODUCER)) {
                    Producer instance = Producer.builder()
                            .messageCount(messages(line))
                            .topic(findTopic(line))
                            .bootstrap(findBootstrap(line))
                            .build();
                    instance.run();
                } else if (line.hasOption(OPT_CONSUMER)) {
                    Consumer instance = Consumer.builder()
                            .topic(findTopic(line))
                            .bootstrap(findBootstrap(line))
                            .build();
                    instance.run();
                } else {
                    help(options);
                }
            }
        } catch (ParseException ex) {
            help(options);
        }
    }

    /**
     * figure out a usable boostrap string - either what is supplied on command line, or a default.
     * @param line the command line to parse
     * @return a non-null string
     * @throws ParseException if the discovered string is not like "host:port"
     */
    private static String findBootstrap(final CommandLine line) throws ParseException {
        if (line.hasOption(OPT_BOOTSTRAP)) {
            return validateBootstrap(line.getOptionValue(OPT_BOOTSTRAP));
        } else {
            return ApplicationProperties.getBootstrap();
        }
    }

    /**
     * figure out a usable topic name - either what is supplied on command line, or a default.
     * @param line the command line to examine.
     * @return a non null string
     */
    private static String findTopic(final CommandLine line) {
        if (line.hasOption(OPT_TOPIC)) {
            return line.getOptionValue(OPT_TOPIC);
        } else {
            return ApplicationProperties.getDefaultTopic();
        }
    }

    /**
     * validate that the provided bootstrap looks something like "characters:integer".
     * Package private to support teesting.
     *
     * @param bootstrap the provided bootstrap string to check.
     * @return the provided string if it is good
     * @throws ParseException if the provided bootstrap is malformed.
     */
    static String validateBootstrap(final String bootstrap) throws ParseException {
        String[] parts = bootstrap.split(":");
        if (parts.length != 2 || !NumberUtils.isDigits(parts[1])) {
            throw new ParseException("expected the bootstrap to be host:port");
        }
        return bootstrap;
    }

    /**
     * construct the set of options for the command line.
     *
     * @return a non-null set of options.
     */
    private static Options options() {
        Options options = new Options();
        options.addOption(Option.builder(OPT_PRODUCER).longOpt("producer").desc("run as a data producer").build());
        options.addOption(Option.builder(OPT_CONSUMER).longOpt("consumer").desc("run as a data consumer").build());
        options.addOption(Option.builder(OPT_TOPIC).longOpt("topic").desc("topic name used").build());
        options.addOption(Option.builder(OPT_COUNT).longOpt("count").desc("number of messages to produce").hasArg().argName("count").build());
        options.addOption(Option.builder(OPT_BOOTSTRAP).longOpt("bootstrap-server").desc("initial server to connect to (e.g. " + "localhost:9092)").hasArg().argName("broker").build());
        options.addOption((Option.builder(OPT_HELP).longOpt("help").desc("print this help message").build()));

        return options;
    }

    /**
     * print the command line help associated with the supplied options.
     *
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
     *
     * @param line the non-null command line to examine
     * @return the number of messages specified, or the 0 if none.
     */
    private static int messages(final CommandLine line) {
        if (line.hasOption('n') && NumberUtils.isParsable(line.getOptionValue(OPT_COUNT))) {
            return NumberUtils.toInt(StringUtils.strip(line.getOptionValue(OPT_COUNT)));
        } else {
            return 0;
        }
    }
}