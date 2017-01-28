package tythor.commands;

import net.dv8tion.jda.core.entities.MessageChannel;
import tythor.TythorBot;

/**
 * Created by Tyler on 1/27/2017.
 */
public class Uptime {
    public static void uptime(MessageChannel channel) {
        long time = (System.currentTimeMillis() - TythorBot.uptime) / 1000;
        System.out.println("time: " + time);
        String period = "seconds";
        if(time >= 604800) {
            String leftover = " ";
            long tempTime = time / 86400 % 7;
            if(tempTime == 0)
                leftover = leftover;
            if(tempTime == 1)
                leftover += Long.toString(tempTime).toString() + " day ";
            else
                leftover += Long.toString(tempTime).toString() + " days ";

            tempTime = time / 3600 % 24;
            if(tempTime == 0)
                leftover = leftover;
            if(tempTime == 1)
                leftover += Long.toString(tempTime).toString() + " hour ";
            else
                leftover += Long.toString(tempTime).toString() + " hours ";

            tempTime = time / 60 % 60;
            if(tempTime == 0)
                leftover = leftover;
            else if(tempTime == 1)
                leftover += Long.toString(tempTime).toString() + " minute ";
            else
                leftover += Long.toString(tempTime).toString() + " minutes ";

            tempTime = time % 60;
            if(tempTime == 0)
                leftover = leftover;
            else if(tempTime == 1)
                leftover += Long.toString(tempTime).toString() + " second";
            else
                leftover += Long.toString(tempTime).toString() + " seconds";

            time /= 604800;
            if(time == 1)
                period = "week" + leftover;
            else
                period = "weeks" + leftover;
        } else if(time >= 86400) {
            String leftover = " ";
            long tempTime = time / 3600 % 24;
            if(tempTime == 0)
                leftover = leftover;
            if(tempTime == 1)
                leftover += Long.toString(tempTime).toString() + " hour ";
            else
                leftover += Long.toString(tempTime).toString() + " hours ";

            tempTime = time / 60 % 60;
            if(tempTime == 0)
                leftover = leftover;
            else if(tempTime == 1)
                leftover += Long.toString(tempTime).toString() + " minute ";
            else
                leftover += Long.toString(tempTime).toString() + " minutes ";

            tempTime = time % 60;
            if(tempTime == 0)
                leftover = leftover;
            else if(tempTime == 1)
                leftover += Long.toString(tempTime).toString() + " second";
            else
                leftover += Long.toString(tempTime).toString() + " seconds";

            time /= 86400;
            if(time == 1)
                period = "day" + leftover;
            else
                period = "days" + leftover;
        } else if(time >= 3600) {
            String leftover = " ";
            long tempTime = time / 60 % 60;
            if(tempTime == 0)
                leftover = leftover;
            else if(tempTime == 1)
                leftover += Long.toString(tempTime).toString() + " minute ";
            else
                leftover += Long.toString(tempTime).toString() + " minutes ";
            System.out.println(tempTime);

            tempTime = time % 60;
            if(tempTime == 0)
                leftover = leftover;
            else if(tempTime == 1)
                leftover += Long.toString(tempTime).toString() + " second";
            else
                leftover += Long.toString(tempTime).toString() + " second";

            time /= 3600;
            if(time == 1)
                period = "hour" + leftover;
            else
                period = "hours" + leftover;
        } else if(time >= 60) {
            String leftover = " ";
            long tempTime = time % 60;
            if(tempTime == 0)
                leftover = leftover;
            else if(tempTime == 1)
                leftover += Long.toString(tempTime).toString() + " second";
            else
                leftover += Long.toString(tempTime).toString() + " seconds";

            time /= 60;
            if(time == 1)
                period = "minute" + leftover;
            else
                period = "minutes" + leftover;
        }
        channel.sendMessage(Long.toString(time) + " " + period).queue();
    }
}
