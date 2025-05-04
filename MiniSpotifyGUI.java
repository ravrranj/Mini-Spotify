
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Objects;


public class MiniSpotifyGUI {
    private JFrame frame;
    private DefaultListModel<Song> songListModel;
    private JList<Song> songJList;
    private JTextField titleField, artistField;
    private JLabel nowPlayingLabel;
    private DefaultListModel<Song> recentlyPlayedModel;
    private JList<Song> recentlyPlayedJList;

    private ArrayList<Song> playlist = new ArrayList<>();
    private HashSet<Song> songSet = new HashSet<>();
    private Stack<Song> recentlyPlayed = new Stack<>();

    public MiniSpotifyGUI() {
        frame = new JFrame("MiniSpotify 🎵");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        // Top - Now Playing
        nowPlayingLabel = new JLabel("Now Playing: None", SwingConstants.CENTER);
        frame.add(nowPlayingLabel, BorderLayout.NORTH);

      // Center - Playlist
       songListModel = new DefaultListModel<>();
       songJList = new JList<>(songListModel);
       JScrollPane scrollPane = new JScrollPane(songJList);
       scrollPane.setBorder(BorderFactory.createTitledBorder("Playlist")); 
       frame.add(scrollPane, BorderLayout.CENTER);



        // Recently Played List
        recentlyPlayedModel = new DefaultListModel<>();
        recentlyPlayedJList = new JList<>(recentlyPlayedModel);
        recentlyPlayedJList.setPreferredSize(new Dimension(150, 0));
        recentlyPlayedJList.setBorder(BorderFactory.createTitledBorder("Recently Played."));
        frame.add(new JScrollPane(recentlyPlayedJList), BorderLayout.EAST);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        if (recentlyPlayed.size() >= 10) {
        recentlyPlayed.remove(0); // Remove oldest
        recentlyPlayedModel.remove(0);
        }

        

        // Bottom - Controls
        JPanel controlPanel = new JPanel();
        titleField = new JTextField(10);
        artistField = new JTextField(10);
        JButton addButton = new JButton("Add song");
        JButton playButton = new JButton("Play");
        JButton shuffleButton = new JButton("Shuffle Play");
        controlPanel.add(shuffleButton);


        controlPanel.add(new JLabel("Title:"));
        controlPanel.add(titleField);
        controlPanel.add(new JLabel("Artist:"));
        controlPanel.add(artistField);
        controlPanel.add(addButton);
        controlPanel.add(playButton);

        frame.add(controlPanel, BorderLayout.SOUTH);

        //BUTTON actions
        
        addButton.addActionListener(e -> addSong());
        playButton.addActionListener(e -> playSong());
        shuffleButton.addActionListener(e -> shufflePlay());


        frame.setVisible(true);
    }

    private void addSong() {
        String title = titleField.getText().trim();
        String artist = artistField.getText().trim();

        if (title.isEmpty() || artist.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Both title and artist are required.");
            return;
        }

        Song newSong = new Song(title, artist);

        if (songSet.contains(newSong)) {
            JOptionPane.showMessageDialog(frame, "⚠️ Song already exists.");
        } else {
            playlist.add(newSong);
            songSet.add(newSong);
            songListModel.addElement(newSong);
            titleField.setText("");
            artistField.setText("");
        }
    }

    private void playSong() {
        Song selectedSong = songJList.getSelectedValue();
    
        if (selectedSong == null) {
            JOptionPane.showMessageDialog(frame, "Please select a song to play.");
            return;
        }
    
        nowPlayingLabel.setText("Now Playing: " + selectedSong);
    
        if (recentlyPlayed.isEmpty() || !recentlyPlayed.peek().equals(selectedSong)) {
            recentlyPlayed.push(selectedSong);
            recentlyPlayedModel.addElement(selectedSong);
        }
    }
     
    private void shufflePlay() {
        
    
        Song lastPlayed = recentlyPlayed.isEmpty() ? null : recentlyPlayed.peek();
        Song randomSong;
        Random rand = new Random();
    
        do {
            randomSong = playlist.get(rand.nextInt(playlist.size()));
        } while (playlist.size() > 1 && randomSong.equals(lastPlayed));
    
        nowPlayingLabel.setText("Now Playing: " + randomSong);
        recentlyPlayed.push(randomSong);
        recentlyPlayedModel.addElement(randomSong);
    }
    

    public static void main(String[] args) {
        new MiniSpotifyGUI();
    }
}