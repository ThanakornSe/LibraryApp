package com.example.mylibrary.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mylibrary.data.BookDao;
import com.example.mylibrary.data.BookPlaylistDao;
import com.example.mylibrary.data.PlaylistDao;
import com.example.mylibrary.model.Book;
import com.example.mylibrary.model.BookPlaylistLink;
import com.example.mylibrary.model.Playlist;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Book.class, Playlist.class, BookPlaylistLink.class},version = 1)
public abstract class LibraryDatabase extends RoomDatabase {
    public abstract BookDao bookDao();
    public abstract PlaylistDao playlistDao();
    public abstract BookPlaylistDao bookPlaylistDao();

    private static LibraryDatabase instance;

    public static synchronized LibraryDatabase getInstance(Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(context, LibraryDatabase.class,"library_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .addCallback(initialCallback)
                    .build();
        }
        return instance;
    }

    public static final int NUMBER_OF_THREAD = 6;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREAD);

    public static final String FAVORITE = "Favorite";
    public static final String ALREADY_READ = "AlreadyRead";
    public static final String CURRENTLY_READ = "CurrentlyRead";
    public static final String WISHLIST = "Wishlist";

    public static Callback initialCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    BookDao bookDao = instance.bookDao();
                    PlaylistDao playlistDao = instance.playlistDao();
                    BookPlaylistDao bookPlaylistDao = instance.bookPlaylistDao();

                    String LD1 = "\t\t\t\tThe year is 1984 and the city is Tokyo.\n" +
                            "\n" +
                            "\t\tA young woman named Aomame follows a taxi driver’s enigmatic suggestion and begins to notice puzzling discrepancies in the world around her. She has entered, she realizes, a parallel existence, which she calls 1Q84 “Q is for ‘question mark.’ A world that bears a question.” Meanwhile, an aspiring writer named Tengo takes on a suspect ghostwriting project. He becomes so wrapped up with the work and its unusual author that, soon, his previously placid life begins to come unraveled.\n" +
                            "\n" +
                            "\t\tAs Aomame’s and Tengo’s narratives converge over the course of this single year, we learn of the profound and tangled connections that bind them ever closer: a beautiful, dyslexic teenage girl with a unique vision; a mysterious religious cult that instigated a shoot-out with the metropolitan police; a reclusive, wealthy dowager who runs a shelter for abused women; a hideously ugly private investigator; a mild-mannered yet ruthlessly efficient bodyguard; and a peculiarly insistent television-fee collector.\n" +
                            "\n" +
                            "\t\tA love story, a mystery, a fantasy, a novel of self-discovery, a dystopia to rival George Orwell’s 1Q84 is Haruki Murakami’s most ambitious undertaking yet: an instant best seller in his native Japan, and a tremendous feat of imagination from one of our most revered contemporary writers.";

                    String LD2 = "\t\tSisyphus is probably more famous for his punishment in the underworld than for what he did in his life. According to the Greek myth, Sisyphus is condemned to roll a rock up to the top of a mountain, only to have the rock roll back down to the bottom every time he reaches the top. The gods were wise, Camus suggests, in perceiving that an eternity of futile labor is a hideous punishment.\n" +
                            "\n" +
                            "There are a number of stories—ones which are not mutually exclusive—that explain how Sisyphus came to earn his punishment in the underworld. According to one story, Zeus carried off Aegina, a mortal woman who was the daughter of Asopus. Sisyphus witnessed this kidnapping in his home city of Corinth. Sisyphus agreed to inform Asopus as to who had kidnapped Aegina if Asopus would give the citadel at Corinth a fresh-water spring. In making this deal and bearing witness against Zeus, Sisyphus earned the wrath of the gods while earning earthly wealth and happiness for himself and his people.";

                    String LD3 = "A grand vision defined: The CEO of The Walt Disney Company shares the ideas and values he has used to reinvent one of the most beloved companies in the world.\n" +
                            "In 2005, Bob Iger became CEO of The Walt Disney Company during a difficult time. Morale had deteriorated; competition was more intense, and technology changing faster than at any time in the company\\'s history.\n" +
                            "        \"I knew there was nothing to be gained from arguing over the past,\" Iger writes. \"The only thing that mattered was the future, and I believed I had a vision for it. It came down to three clear ideas:\n" +
                            "        1) We needed to make things that were great, the highest quality creative content we could produce. 2) We could not be afraid of change; we had to embrace technology and foster innovation.\n" +
                            "        And 3) We needed to think bigger to turn Disney into a stronger global brand, penetrating large growth markets, like China, more deeply.\"\n" +
                            "     Twelve years later, Disney is the largest, most respected media company in the world — counting Pixar, Marvel, and Lucasfilm among its properties —\n" +
                            "        and its value is nearly four times what it was when Iger took over. Iger is recognized as one of the most inventive and successful CEOs of our time.\n" +
                            "Now, he's sharing the lessons he's learned while running Disney and leading its 200,000 employees — taking big risks in the face of historic disruption;\n" +
                            "        learning to inspire the people who work for you; leading with fairness and communicating principles clearly; and it's about the relentless curiosity\n" +
                            "        that has driven Iger for 43 years, since the day he started as a studio supervisor at ABC. It's also a book about thoughtfulness and respect:\n" +
                            "        the basis of every project and partnership Iger pursues, from a deep friendship with Steve Jobs in his final years, to an abiding love of the evolving Star Wars myth.\n" +
                            "\"Over the past twelve years, I think I've learned what real leadership is,\" Iger writes. \"But I couldn't have articulated all of this until I lived it. You can't fake it — that's one of the lessons.\"";

                    String LD4 = "Hyperkinetic and relentlessly inventive, Hard-Boiled Wonderland and the End of the World is Haruki Murakami’s deep dive into the very nature of consciousness. Across two parallel narratives,\n" +
                            "        Murakami draws readers into a mind-bending universe in which Lauren Bacall, Bob Dylan, a split-brained data processor,\n" +
                            "        a deranged scientist, his shockingly undemure granddaughter, and various thugs, librarians, and subterranean monsters collide to dazzling effect.\n" +
                            "        What emerges is a novel that is at once hilariously funny and a deeply serious meditation on the nature and uses of the mind.";

                    String LD5 = "His name was Biron Farrill and he was a student at the University of Earth. A native of one of the helpless Nebular Kingdoms, " +
                            "he saw his home world conquered and controlled by the planet Tyrann&;a ruthless, barbaric Empire that was building a dynasty of cruelty and domination among the stars. " +
                            "Farrill&;s own father had been executed for trying to resist the Tyrann dictatorship and now someone was trying to kill Biron. But why? " +
                            "His only hope for survival lay in fleeing Earth and joining the rebellion that was rumored to be forming somewhere in the Kingdoms. But once he cast his lot with the freedom fighters, " +
                            "he would find himself guarding against treachery on every side and facing the most difficult choice of all: to betray either the woman he loved or the revolution that was the last hope for the future.";

                    String LD6 = "A messianic blue fox who slips through warrens of time and space on a mysterious mission. A homeless woman haunted by a demon who finds the key to all things in a strange journal. A giant leviathan of a fish, centuries old, who hides a secret, remembering a past that may not be its own. Three ragtag rebels waging an endless war for the fate of the world against an all-powerful corporation. A raving madman who wanders the desert lost in the past, haunted by his own creation: an invisible monster whose name he has forgotten and whose purpose remains hidden.\n" +
                            "\n" +
                            "\n" +
                            "Jeff VanderMeer's Dead Astronauts presents a City with no name of its own where, in the shadow of the all-powerful Company, lives human and otherwise converge in terrifying and miraculous ways. At stake: the fate of the future, the fate of Earth – all the Earths.";

                    bookDao.insertBook(new Book("1Q84", "Haruki Murakami", 1350, "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1483103331i/10357575._UY453_SS453_.jpg",
                            "A work of maddening brilliance", LD1, "https://readery.co/9786165630085"));
                    bookDao.insertBook(new Book("The Myth of Sisyphus", "Albert Camus", 250, "https://images-na.ssl-images-amazon.com/images/I/71PLcOtnFQL.jpg"
                            , "One of the most influential works of this century, this is a crucial exposition of existentialist thought", LD2, "https://thailand.kinokuniya.com/bw/9780525564454"));
                    bookDao.insertBook(new Book("The Ride of a Lifetime","Robert Iger",272,"https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/lrg/9780/3995/9780399592096.jpg",
                            "A grand vision defined: The CEO of The Walt Disney Company shares the ideas and values he has used to reinvent one of the most beloved companies in the world.",LD3,"https://www.asiabooks.com/ride-of-a-lifetime-the-lessons-learned-from-15-years-as-ceo-of-the-walt-disney-237359.html"));
                    bookDao.insertBook(new Book("Hard-boiled WonderLand and the End of the World","Haruki Murakami",416,"https://kbimages1-a.akamaihd.net/8170d985-aa10-4a14-99d9-9fd1be35fabe/1200/1200/False/hard-boiled-wonderland-and-the-end-of-the-world.jpg",
                            "A narrative particle accelerator that zooms between Wild Turkey Whiskey and Bob Dylan",LD4,"https://www.asiabooks.com/hard-boiled-wonderland-and-the-end-of-th-6385.html"));
                    bookDao.insertBook(new Book("THE STARS, LIKE DUST","ASIMOV, ISAAC",256,"https://img1.od-cdn.com/ImageType-400/1191-1/457/E48/E6/%7B457E48E6-3AFA-4918-AF30-FC1038511CEE%7DImg400.jpg",
                            "The first book in the Galactic Empire series, the spectacular precursor to the classic Foundation series, by one of history&;s most influential writers of science fiction, Isaac Asimov",
                            LD5,"https://www.asiabooks.com/the-stars-like-dust-252978.html"));
                    bookDao.insertBook(new Book("DEAD ASTRONAUTS","VANDERMEER, JEFF",336,"https://kbimages1-a.akamaihd.net/7b6c0a4e-97d3-4ebf-b044-5b13d5515d16/353/569/90/False/dead-astronauts-3.jpg",
                            "Under the watchful eye of The Company, three characters Grayson, Morse and Chen shapeshifters, amorphous, part human, part extensions of the landscape, make their way through forces that would consume them. A blue fox, a giant fish and language stretched to the limit.",
                            LD6,"https://www.asiabooks.com/dead-astronauts-258915.html"));
                    bookDao.insertBook(new Book("ASSASSIN'S CREED ODYSSEY","DOHERTY, GORDON",368,"https://static.wikia.nocookie.net/assassinscreed/images/7/73/AC_OD_novel.jpg/revision/latest?cb=20191030151859",
                            "Greece, 5th century BCE. Kassandra is a mercenary of Spartan blood, sentenced to death by her family","Greece, 5th century BCE. Kassandra is a mercenary of Spartan blood, sentenced to death by her family, cast out into exile. Now she will embark on an epic journey to become a legendary hero - and uncover the truth about her mysterious lineage.",
                            "https://www.asiabooks.com/assassin-s-creed-odyssey-225639.html"));



                    playlistDao.insertPlaylist(new Playlist(FAVORITE));
                    playlistDao.insertPlaylist(new Playlist(ALREADY_READ));
                    playlistDao.insertPlaylist(new Playlist(CURRENTLY_READ));
                    playlistDao.insertPlaylist(new Playlist(WISHLIST));

                }
            });
        }
    };


}


