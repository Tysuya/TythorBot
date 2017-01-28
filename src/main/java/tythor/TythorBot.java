package tythor;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import tythor.commands.Uptime;
import tythor.listeners.MessageListener;

import java.util.Timer;
import java.util.TimerTask;

import static tythor.servlet.BindToPort.bindToPort;
import static tythor.servlet.BindToPort.keepAwake;

public class TythorBot {
    public static JDA jda;
    public static long uptime = System.currentTimeMillis();
    public static void main(String[] args) {
        JDABuilder jdaBuilder = new JDABuilder(AccountType.BOT).setToken("MjcwMTEwMjEzNTIzMTExOTM2.C1zHQA.geSuOqz1FtfiMU86GOmRoiGPmOs");
        System.out.println(uptime);
                // MTU5MjAxNTI2MTE0NTQ5NzYw.C1kPQw.eT99T2xS8VjmTcWzOgyB8m3gg7I

        //.buildBlocking();  //There are 2 ways to login, blocking vs async. Blocking guarantees that JDA will be completely loaded.
        try {
            jda = jdaBuilder.buildBlocking();
            jda.addEventListener(new MessageListener());  //An instance of a class that will handle events.
        } catch(Exception e) {
            e.printStackTrace();
        }
        try {
            keepAwake();
            bindToPort();
        } catch(Exception e) {
            e.printStackTrace();
        }
        MessageChannel channel = TythorBot.jda.getTextChannelById("241064442429702144");
        Uptime.uptime(channel);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                MessageChannel channel = TythorBot.jda.getTextChannelById("241064442429702144");
                Uptime.uptime(channel);
            }
        }, 0, 300000 * 6);

    }
}
