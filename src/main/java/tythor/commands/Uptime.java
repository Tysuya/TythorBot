package tythor.commands;

import net.dv8tion.jda.core.entities.MessageChannel;
import tythor.TythorBot;

/**
 * Created by Tyler on 1/27/2017.
 */
public class Uptime {
    public static void uptime(MessageChannel channel) {
        long time = (System.currentTimeMillis() - TythorBot.startTime) / 1000;
        System.out.println("time: " + time);
        long seconds = time % 60;
        long minutes = time / 60 % 60;
        long hours = time / 3600 % 24;
        long days = time / 86400;
        String secondsString = " seconds ";
        String minutesString = " minutes ";
        String hoursString = " hours ";
        String daysString = " days ";
        if(seconds == 1)
            secondsString = " second ";
        if(minutes == 1)
            minutesString = " minute ";
        if(hours == 1)
            hoursString = " hour ";
        if(days == 1)
            daysString = " day ";

        channel.sendMessage("TythorBot has been online for```" + days + daysString + hours + hoursString + minutes + minutesString + seconds + secondsString + "```").queue();
    }

}
