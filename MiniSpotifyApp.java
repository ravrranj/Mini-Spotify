//playList and the play song feature
import java.util.*;

public class MiniSpotifyApp {
    static ArrayList<Song> playlist = new ArrayList<>();
    static HashSet<Song> songSet = new HashSet<>();
    static Stack<Song> recentlyPlayed = new Stack<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice = -1; // to fix the not intialize error 

        while (choice != 0)  {
            System.out.println("\n🎵🎵 Mini Spotify App 🎵🎵");
            System.out.println("1. Add Song to Playlist");
            System.out.println("2. Show Playlist");
            System.out.println("3. Play a Song");
            System.out.println("4. View Recently Played");
            System.out.println("0. Exit");
            System.out.println("Enter your choice: ");

            // input validation

            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine(); // clear buffer
            } else {
                System.out.println("Please enter a valid number. ");
                sc.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    addSong(sc);
                    break;
                case 2:
                    showPlaylist();
                    break;
                case 3:
                    playSong(sc); // Pass the scanner object here
                    break;
                case 4:
                    viewHistory();
                    break;
                case 0:
                    System.out.println("Exiting... Goodbye!👋");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 0);
    }

    static void addSong(Scanner sc) {
        System.out.println("Enter song title: ");
        String title = sc.nextLine();
        System.out.println("Enter artist name: ");
        String artist = sc.nextLine();

        Song newSong = new Song(title, artist);

        if (songSet.contains(newSong)) {
            System.out.println(" ⚡Song already exists in playlist!");
        } else {
            playlist.add(newSong);
            songSet.add(newSong);
            System.out.println("✅ Song added: " + newSong);
        }
    }

    static void showPlaylist() {
        if (playlist.isEmpty()) {
            System.out.println("📪 Playlist is empty. ");
            return;
        }

        System.out.println("🎶 Your Playlist: ");
        for (int i = 0; i < playlist.size(); i++) {
            System.out.println((i + 1) + ". " + playlist.get(i));
        }
    }

    // Modify the playSong method to track recently played songs using a stack
    public static void playSong(Scanner sc) {
        if (playlist.isEmpty()) {
            System.out.println("? Playlist is empty.");
        } else {
            System.out.println("? Your Playlist: ");
            for (int i = 0; i < playlist.size(); i++) {
                System.out.println((i + 1) + ". " + playlist.get(i).getTitle() + " by " + playlist.get(i).getArtist());
            }

            System.out.print("Enter song number to play: ");
            int songNumber = sc.nextInt();
            sc.nextLine();  // Consume the newline character

            if (songNumber >= 1 && songNumber <= playlist.size()) {
                Song songToPlay = playlist.get(songNumber - 1);
                System.out.println("?? Now playing: " + songToPlay.getTitle() + " by " + songToPlay.getArtist());

                // Add to recently played stack
                recentlyPlayed.push(songToPlay);
            } else {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // Modify the viewRecentlyPlayed method
    public static void viewHistory() {
        if (recentlyPlayed.isEmpty()) {
            System.out.println("? No recently played songs.");
        } else {
            System.out.println("? Recently Played Songs: ");
            
            // We need to pop songs from the stack to display them (LIFO order)
            
            Stack<Song> tempStack = new Stack<>(); // Temporary stack to hold songs temporarily
            while (!recentlyPlayed.isEmpty()) {
                Song song = recentlyPlayed.pop();
                System.out.println(song.getTitle() + " by " + song.getArtist());
                tempStack.push(song); // Push them back into the temporary stack
            }

            // Push the songs back into the original stack
            while (!tempStack.isEmpty()) {
                recentlyPlayed.push(tempStack.pop());
            }
        }
    }
}
