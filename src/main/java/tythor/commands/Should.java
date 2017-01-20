package tythor.commands;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.util.Random;

import static tythor.utils.SendMentionMessage.sendMentionMessage;

/**
 * Created by Tyler on 1/17/2017.
 */
public class Should {
    public static void should(MessageChannel channel, Message message) {
        Random random = new Random();
        if (random.nextBoolean()) {
            sendMentionMessage(channel, message, "YES");
        } else {
            sendMentionMessage(channel, message, "NO");
        }
    }
}
