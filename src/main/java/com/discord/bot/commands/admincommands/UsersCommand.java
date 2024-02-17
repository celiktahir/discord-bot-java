package com.discord.bot.commands.admincommands;

import com.discord.bot.commands.ISlashCommand;
import com.discord.bot.Entity.User;
import com.discord.bot.Service.UserService;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.FileUpload;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@AllArgsConstructor
public class UsersCommand implements ISlashCommand {
    final UserService userService;
    private final String adminUserId;

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        if (event.getUser().getId().equals(adminUserId)) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            List<User> users = userService.getUsers();
            if (users.isEmpty()) {
                embedBuilder.setDescription("No users found.");
                event.replyEmbeds(embedBuilder.build()).queue();
            } else {
                try {
                    File usersFile = new File("users.txt");
                    BufferedWriter writer = new BufferedWriter(new FileWriter(usersFile, true));
                    writer.write("USER COUNT: " + users.size() + "\nT: Text   H: Hentai   P: Porn   " +
                            "R: Reddit   T: Todo" +
                            "\n        ID             USER      T H P R T");
                    for (User user : users) {
                        writer.append("\n").append(user.getUserId())
                                .append(" ").append(user.getUserWithTag())
                                .append(" ").append(String.valueOf(user.getTextCount()))
                                .append(" ").append(String.valueOf(user.getHCount()))
                                .append(" ").append(String.valueOf(user.getPCount()))
                                .append(" ").append(String.valueOf(user.getRedditCount()))
                                .append(" ").append(String.valueOf(user.getTodoCount()));
                    }
                    writer.close();
                    event.replyFiles(FileUpload.fromData(usersFile)).queue();
                    Thread.sleep(100);
                    //noinspection ResultOfMethodCallIgnored
                    usersFile.delete();
                } catch (IOException | InterruptedException e) {
                    //noinspection CallToPrintStackTrace
                    e.printStackTrace();
                }
            }
        }
    }
}
