package tythor.listeners;

import net.dv8tion.jda.client.entities.Group;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDAInfo;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.ShutdownEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.user.UserTypingEvent;
import net.dv8tion.jda.core.exceptions.PermissionException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import tythor.utils.CleverBotQuery;

import java.util.List;
import java.util.Random;

import static tythor.commands.Fact.fact;
import static tythor.commands.Should.should;
import static tythor.commands.Uptime.uptime;
import static tythor.utils.SendMentionMessage.sendMentionMessage;

public class MessageListener extends ListenerAdapter {
    public MessageListener() {

    }

    @Override
    public void onUserTyping(UserTypingEvent event) {
        JDA jda = event.getJDA();
        User user = event.getUser();
        MessageChannel channel = event.getChannel();
        JDAInfo jdaInfo = new JDAInfo();
    }

    @Override
    public void onShutdown(ShutdownEvent event) {
        System.out.println(event.getShutdownTime());
    }

    /**
     * NOTE THE @Override!
     * This method is actually overriding a method in the ListenerAdapter class! We place an @Override annotation
     * right before any method that is overriding another to guarantee to ourselves that it is actually overriding
     * a method from a super class properly. You should do this every time you override a method!
     * <p>
     * As stated above, this method is overriding a hook method in the
     * {@link net.dv8tion.jda.core.hooks.ListenerAdapter ListenerAdapter} class. It has convience methods for all JDA events!
     * Consider looking through the events it offers if you plan to use the ListenerAdapter.
     * <p>
     * In this example, when a message is received it is printed to the console.
     *
     * @param event An event containing information about a {@link net.dv8tion.jda.core.entities.Message Message} that was
     *              sent in a channel.
     */
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        //These are provided with every event in JDA
        JDA jda = event.getJDA();                       //JDA, the core of the api.
        long responseNumber = event.getResponseNumber();//The amount of discord events that JDA has received since the last reconnect.

        //Event specific information
        User author = event.getAuthor();                  //The user that sent the message
        Message message = event.getMessage();           //The message that was received.
        MessageChannel channel = event.getChannel();    //This is the MessageChannel that the message was sent to.
        //  This could be a TextChannel, PrivateChannel, or Group!

        String messageContent = message.getContent();              //This returns a human readable version of the Message. Similar to
        // what you would see in the client.

        boolean bot = author.isBot();                     //This boolean is useful to determine if the User that
        // sent the Message is a BOT or not!

        //isSpam(channel, message);

        if (event.isFromType(ChannelType.TEXT))         //If this message was sent to a Guild TextChannel
        {
            //Because we now know that this message was sent in a Guild, we can do guild specific things
            // Note, if you don't check the ChannelType before using these methods, they might return null due
            // the message possibly not being from a Guild!

            Guild guild = event.getGuild();             //The Guild that this message was sent in. (note, in the API, Guilds are Servers)
            TextChannel textChannel = event.getTextChannel(); //The TextChannel that this message was sent to.
            Member member = event.getMember();          //This Member that sent the message. Contains Guild specific information about the User!

            String name = member.getEffectiveName();    //This will either use the Member's nickname if they have one,
            // otherwise it will default to their username. (User#getName())

            System.out.printf("(%s)[%s]<%s>: %s\n", guild.getName(), textChannel.getName(), name, messageContent);
        } else if (event.isFromType(ChannelType.PRIVATE)) //If this message was sent to a PrivateChannel
        {
            //The message was sent in a PrivateChannel.
            //In this example we don't directly use the privateChannel, however, be sure, there are uses for it!
            PrivateChannel privateChannel = event.getPrivateChannel();

            System.out.printf("[PRIV]<%s>: %s\n", author.getName(), messageContent);
        } else if (event.isFromType(ChannelType.GROUP))   //If this message was sent to a Group. This is CLIENT only!
        {
            //The message was sent in a Group. It should be noted that Groups are CLIENT only.
            Group group = event.getGroup();
            String groupName = group.getName() != null ? group.getName() : "";  //A group name can be null due to it being unnamed.

            System.out.printf("[GRP: %s]<%s>: %s\n", groupName, author.getName(), messageContent);
        }

        //Now that you have a grasp on the things that you might see in an event, specifically MessageReceivedEvent,
        // we will look at sending / responding to messages!
        //This will be an extremely simplified example of command processing.

        //Remember, in all of these .equals checks it is actually comparing
        // message.getContent().equals, which is comparing a string to a string.
        // If you did message.equals() it will fail because you would be comparing a Message to a String!
        if(!message.getAuthor().isBot()) {
            if (messageContent.equals("tb!help")) {
                channel.sendMessage("Hello, I am TythorBot. My current commands are as follows:" +
                        "```!fact\n!kick\n!ping\n!roll\n!should\n!uptime```" +
                        "I am also a chatbot, so start a message with <@270110213523111936> and I will respond.\n" +
                        "I am in active development, so some things may break. Send any bugs to my creator, <@159201526114549760>.").queue();

            } else if (messageContent.equals("!ping")) {
                long start = System.currentTimeMillis();
                try {
                    Message ping = channel.sendMessage(":ping_pong:`...`").complete();
                    ping.editMessage(":ping_pong: `" + (System.currentTimeMillis() - start) + "ms`").queue();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (messageContent.equals("!uptime")) {
                uptime(channel);
            } else if (messageContent.equals("!roll")) {
                //In this case, we have an example showing how to use the Success consumer for a RestAction. The Success consumer
                // will provide you with the object that results after you execute your RestAction. As a note, not all RestActions
                // have object returns and will instead have Void returns. You can still use the success consumer to determine when
                // the action has been completed!

                Random rand = new Random();
                int roll = rand.nextInt(6) + 1; //This results in 1 - 6 (instead of 0 - 5)
                channel.sendMessage("Your roll: " + roll).queue(sentMessage ->  //This is called a lambda statement. If you don't know
                {                                                               // what they are or how they work, try google!
                    if (roll < 3) {
                        channel.sendMessage("The role for messageId: " + sentMessage.getId() + " wasn't very good... Must be bad luck!\n").queue();
                    }
                });
            } else if (messageContent.startsWith("!kick"))   //Note, I used "startsWith, not equals.
            {
                //This is an admin command. That means that it requires specific permissions to use it, in this case
                // it needs Permission.KICK_MEMBERS. We will have a check before we attempt to kick members to see
                // if the logged in account actually has the permission, but considering something could change after our
                // check we should also take into account the possibility that we don't have permission anymore, thus Discord
                // response with a permission failure!
                //We will use the error consumer, the second parameter in queue!

                //We only want to deal with message sent in a Guild.
                if (message.isFromType(ChannelType.TEXT)) {
                    //If no users are provided, we can't kick anyone!
                    if (message.getMentionedUsers().isEmpty()) {
                        channel.sendMessage("You must mention 1 or more Users to be kicked!").queue();
                    } else {
                        Guild guild = event.getGuild();
                        Member selfMember = guild.getSelfMember();  //This is the currently logged in account's Member object.
                        // Very similar to JDA#getSelfUser()!

                        //Now, we the the logged in account doesn't have permission to kick members.. well.. we can't kick!
                        if (!selfMember.hasPermission(Permission.KICK_MEMBERS)) {
                            channel.sendMessage("Sorry! I don't have permission to kick members in this Guild!").queue();
                            return; //We jump out of the method instead of using cascading if/else
                        }

                        //Loop over all mentioned users, kicking them one at a time. Mwauahahah!
                        List<User> mentionedUsers = message.getMentionedUsers();
                        for (User user : mentionedUsers) {
                            Member member = guild.getMember(user);  //We get the member object for each mentioned user to kick them!

                            //We need to make sure that we can interact with them. Interacting with a Member means you are higher
                            // in the Role hierarchy than they are. Remember, NO ONE is above the Guild's Owner. (Guild#getOwner())
                            if (!selfMember.canInteract(member)) {
                                channel.sendMessage("Cannot kicked member: " + member.getEffectiveName() + ", they are higher " +
                                        "in the hierachy than I am!").queue();
                                continue;   //Continue to the next mentioned user to be kicked.
                            }

                            //Remember, due to the fact that we're using queue we will never have to deal with RateLimits.
                            // JDA will do it all for you so long as you are using queue!
                            guild.getController().kick(member).queue(success -> channel.sendMessage("Kicked " + member.getEffectiveName() + "! Cya!").queue(), error -> {
                                //The failure consumer provides a throwable. In this case we want to check for a PermissionException.
                                if (error instanceof PermissionException) {
                                    PermissionException pe = (PermissionException) error;
                                    Permission missingPermission = pe.getPermission();  //If you want to know exactly what permission is missing, this is how.
                                    //Note: some PermissionExceptions have no permission provided, only an error message!

                                    channel.sendMessage("PermissionError kicking [" + member.getEffectiveName() + "]: " + error.getMessage()).queue();
                                } else {
                                    channel.sendMessage("Unknown error while kicking [" + member.getEffectiveName() + "]: " + "<" + error.getClass().getSimpleName() + ">: " + error.getMessage()).queue();
                                }
                            });
                        }
                    }
                } else {
                    channel.sendMessage("This is a Guild-Only command!").queue();
                }
            } else if (messageContent.contains("fact") && !message.getAuthor().isBot()) {
                fact(channel, message);
            } else if (messageContent.contains("!should")) {
                should(channel, message);
            } else if (messageContent.startsWith("@TythorBot")) {
                try {
                    String chat = messageContent.split("@TythorBot ")[1];
                    System.out.println(chat);

                    CleverBotQuery cleverBotQuery = new CleverBotQuery("c4c6ef1eeefdfa203806506b4a2d63c0", chat);
                    cleverBotQuery.sendRequest();
                    chat = cleverBotQuery.getResponse();
                    sendMentionMessage(channel, message, chat);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            /*try {
                MessageHistory messageHistory = new MessageHistory(channel);
                Message lastMessage = messageHistory.retrievePast(1).block().get(0);

                ChatterBotFactory factory = new ChatterBotFactory();

                ChatterBot cleverBot = factory.create(ChatterBotType.CLEVERBOT);
                ChatterBotSession cleverBotSession = cleverBot.createSession();

                ChatterBot pandoraBot = factory.create(ChatterBotType.PANDORABOTS, "b0dafd24ee35a477");
                ChatterBotSession pandoraBotSession = pandoraBot.createSession();

                String chat = messageContent.split("@TythorBot ")[1];
                System.out.println(chat);

                ChatterBotSession chatterBotSession = cleverBotSession;

                Random random = new Random();
                if (!author.equals(lastMessage.getAuthor())) {
                    if (random.nextBoolean()) {
                        chatterBotSession = cleverBotSession;
                    } else {
                        chatterBotSession = pandoraBotSession;
                    }
                }
                chat = chatterBotSession.think(chat);
                sendMentionMessage(channel, message, chat);

            } catch (Exception e) {
                e.printStackTrace();
            }*/
            }
        }
    }

    private void isSpam(MessageChannel channel, Message message) {
        MessageHistory messageHistory = new MessageHistory(channel);
        try {
            List<Message> messageHistoryList = messageHistory.retrievePast(5).complete();
            for (int i = 3; i < messageHistoryList.size(); i++) {
                if (message.getContent().equals(messageHistoryList.get(i).getContent())) {
                    message.delete().queue();
                    System.out.println("Deleted");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}