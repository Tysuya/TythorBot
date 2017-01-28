package tythor.utils;

import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.managers.Presence;
import tythor.TythorBot;
import tythor.commands.Uptime;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Tyler on 1/17/2017.
 */
public class CycleGames {
    public static void cycleGames() {
        try {
            Presence presence = TythorBot.jda.getPresence();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    String[] games = {"League of Legends", "Starcraft II", "Overwatch", "Twitch.tv", "Pokémon", "Stardew Valley", "CS:GO", "Halo 3", "Heroku", "Animal Crossing", "Mega Man", "Pac-Man", "Ping-Pong", "Magic", "Hearthstone", "CodeFights", "CodeWars", "The Land of Chai 2", "Chainary", "Temple Run", "Flappy Bird", "Reddit", "Music", "Nothing"};
                    int random = new Random().nextInt(games.length);
                    presence.setGame(Game.of(games[random] + " | Use tb!help"));
                    System.out.println(games[random] + " | Use tb!help");
                }
            }, 0, 300000);

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    MessageChannel channel = TythorBot.jda.getTextChannelById("241064442429702144");
                    Uptime.uptime(channel);
                }
            }, 0, 300000 * 12);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
