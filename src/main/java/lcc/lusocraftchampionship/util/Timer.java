package lcc.lusocraftchampionship.util;

/*
 * @author Smili
 */
public class Timer {
    /**
     * Returns a String object that as the format of a timer (00:00:00).
     * The ticks argument is a specifier that is relative to the thread.
     *
     * @param   ticks   the ticks of the thread
     *
     * @return          String format "minutes:seconds:milliseconds"
     */
    public static String formatMSM(int ticks) {
        int milliseconds = (ticks % 20) * 50;
        int totalSeconds = ticks / 20;

        int seconds = totalSeconds % 60;
        int minutes = totalSeconds / 60;

        return String.format("%02d:%02d:%03d", minutes, seconds, milliseconds);
    }

    /**
     * Returns a String object that as the format of a timer (00:00).
     * The ticks argument is a specifier that is relative to the thread.
     *
     * @param   ticks   the ticks of the thread
     *
     * @return          String format "minutes:seconds"
     */
    public static String formatMS(int ticks) {
        int seconds = 0;

        if((ticks % 20) > 0)
            seconds += 1;

        seconds += ticks / 20;
        int minutes = seconds / 60;
        seconds %= 60;

        return String.format("%02d:%02d", minutes, seconds);
    }

    /**
     * Returns an Integer that is equal to the ticks, but
     * converted to seconds.
     *
     * @param   ticks   the ticks of the thread
     *
     * @return          Integer represents ticks to seconds conversion
     */
    public static int ticksToSec(int ticks) {
        return (ticks / 20);
    }

    /**
     * Returns an Integer that is equal to the ticks, but
     * converted to minutes.
     *
     * @param   ticks   the ticks of the thread
     *
     * @return          Integer represents ticks to minutes conversion
     */
    public static int ticksToMin(int ticks) {
        return (ticks / 1200);
    }

    /**
     * Returns an Integer that is equal to the seconds, but
     * converted to ticks.
     *
     * @param   seconds the seconds of the thread
     *
     * @return          Integer represents seconds to ticks conversion
     */
    public static int secToTicks(int seconds) {
        return (seconds * 20);
    }

    /**
     * Returns an Integer that is equal to the minutes, but
     * converted to ticks.
     *
     * @param   minutes the minutes of the thread
     *
     * @return          Integer represents minutes to ticks conversion
     */
    public static int minToTicks(int minutes) {
        return (minutes * 1200);
    }

    /**
     * Used when it's need to know check when the ticks are equal
     * to the seconds
     *
     * @param   ticks   the ticks of the thread
     * @param   seconds the seconds that we want to check
     *
     * @return          Boolean
     */
    public static boolean isTicksEqualSec(int ticks, int seconds) {
        if ((ticks % 20) == 0)
            return (ticks / 20) == seconds;

        return false;
    }

    /**
     * Is true if it's been a second.
     *
     * @param   ticks   the ticks of the thread
     *
     * @return          Boolean
     */
    public static boolean isOneSec(int ticks) {
        return ((ticks % 20) == 0);
    }

    /**
     * Is true if it's been a ticks equals to zero.
     *
     * @param   ticks   the ticks of the thread
     *
     * @return          Boolean
     */
    public static boolean isZero(int ticks) {
        return (ticks == 0);
    }
}