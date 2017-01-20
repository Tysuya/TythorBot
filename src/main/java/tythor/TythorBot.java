package tythor;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import tythor.listeners.MessageListener;

import static tythor.servlet.BindToPort.bindToPort;

public class TythorBot {
    public static JDA jda;
    public static void main(String[] args) {
        JDABuilder jdaBuilder = new JDABuilder(AccountType.BOT).setToken("MjcwMTEwMjEzNTIzMTExOTM2.C1zHQA.geSuOqz1FtfiMU86GOmRoiGPmOs");
                // MTU5MjAxNTI2MTE0NTQ5NzYw.C1kPQw.eT99T2xS8VjmTcWzOgyB8m3gg7I

        //.buildBlocking();  //There are 2 ways to login, blocking vs async. Blocking guarantees that JDA will be completely loaded.
        try {
            jda = jdaBuilder.buildBlocking();
            jda.addEventListener(new MessageListener());  //An instance of a class that will handle events.
        } catch(Exception e) {
            e.printStackTrace();
        }
        try {
            bindToPort();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
