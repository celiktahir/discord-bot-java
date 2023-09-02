package com.discord.bot.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "subreddits")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Subreddit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "genre")
    private String genre;

    @Column(name = "nsfw")
    private boolean nsfw;

    public Subreddit(String name, String genre, boolean nsfw) {
        this.name = name;
        this.genre = genre;
        this.nsfw = nsfw;
    }
}
