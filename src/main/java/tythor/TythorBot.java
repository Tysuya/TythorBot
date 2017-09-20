package tythor;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import tythor.listeners.MessageListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static tythor.servlet.BindToPort.bindToPort;
import static tythor.servlet.BindToPort.keepAwake;
import static tythor.utils.CycleGames.cycleGames;

public class TythorBot {
    public static JDA jda;
    public static long startTime = System.currentTimeMillis();
    public static void main(String[] args) {
        JDABuilder jdaBuilder = new JDABuilder(AccountType.BOT).setToken("MjcwMTEwMjEzNTIzMTExOTM2.C1zHQA.geSuOqz1FtfiMU86GOmRoiGPmOs");
                // MTU5MjAxNTI2MTE0NTQ5NzYw.C1kPQw.eT99T2xS8VjmTcWzOgyB8m3gg7I

        //.buildBlocking();  //There are 2 ways to login, blocking vs async. Blocking guarantees that JDA will be completely loaded.
        try {
            jda = jdaBuilder.buildBlocking();
            jda.addEventListener(new MessageListener());  //An instance of a class that will handle events.

            MessageChannel channel = jda.getTextChannelById("241064442429702144");
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy hh:mm:ssa");
            dateFormat.setTimeZone(TimeZone.getTimeZone("PST"));
            channel.sendMessage("Started on `" + dateFormat.format(new Date()) + "`").queue();

            cycleGames();
            keepAwake();
            bindToPort();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
