import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Library
{
	private ArrayList<Song> 			songs; 
	private ArrayList<AudioBook> 	audiobooks;
	private ArrayList<Playlist> 	playlists; 
	
	public Library()
	{
		songs 			= new ArrayList<Song>(); 
		audiobooks 	= new ArrayList<AudioBook>(); ;
		playlists   = new ArrayList<Playlist>();
	}
	
	public void download(AudioContent content)
	{
		if (content.getType() == Song.TYPENAME) { // comparing type to Song.TYPENAME to check if content is a song
			Song newSong = (Song) content;		// casting content to a Song object to add it to songs arraylist, which only has Song objects
			for (int i = 0; i < songs.size(); i++) { 
				if (songs.get(i).equals(newSong)) { // checking if song is already in songs arraylist using for loop
					throw new AudioContentAlreadyExistsException("Song " + content.getTitle() + " already downloaded");
				}
			}
	
			songs.add(newSong); // if not already in songs, add it
			System.out.println(content.getType() + " " + content.getTitle() + " Added to Library");
	
		}
	
		if (content.getType() == AudioBook.TYPENAME) { // checking if content is audiobook
			AudioBook contentAudioBook = (AudioBook) content; // casting to AudioBook object
			for (int i = 0; i < audiobooks.size(); i++) {
				if (audiobooks.get(i).equals(contentAudioBook)) { // checking if audiobook is already in the arraylist using for loop
					throw new AudioContentAlreadyExistsException("AudioBook " + content.getTitle() + " already downloaded");
				}
			}
	
			audiobooks.add(contentAudioBook); // if not, add to audiobooks
			System.out.println(content.getType() + " " + content.getTitle() + " Added to Library");
	
		}
	}
		
	
	public void listAllSongs()
	{
		for (int i = 0; i < songs.size(); i++)	// for loop to go through every song in arraylist
		{
			int index = i + 1;
			System.out.print("" + index + ". "); //
			songs.get(i).printInfo();
			System.out.println();	
		}
	}
	
	public void listAllAudioBooks()
	{
		for (int i = 0; i < audiobooks.size(); i++) { // for loop to go through audiobooks
			System.out.print((i + 1) + ". ");	// adding 1 to index because it doesn't start at 0
			audiobooks.get(i).printInfo();		// getting each audiobook and printing the information
			System.out.println();
		}
		
	}
	
	public void listAllPlaylists()
	{
		for (int i = 0; i < playlists.size(); i++) {
			System.out.print((i + 1) + ". " + playlists.get(i).getTitle());
			System.out.println();
		}
		
	}
	
	public void listAllArtists()
	{
		ArrayList<String> artists = new ArrayList<String>(); // creating new empty array list
		for (int i = 0; i < songs.size(); i++) {		// for loop for songs arraylist
			if (!artists.contains(songs.get(i).getArtist())) {		// checking that artists doesn't already contain the artist
				artists.add(songs.get(i).getArtist());				// if not, add the artist
			}
		}

		for (int j = 0; j < artists.size(); j++) {					// for loop to print all the artists
			int index = j + 1;
			System.out.println(index + ". " + artists.get(j));
		}
	}

	public void deleteSong(int index)
	{
		if (index < 1 || index > playlists.size()) {
			throw new AudioContentNotFoundException("Song not found");
		}

		for (int i = 0; i < playlists.size(); i++) {
			if (playlists.get(i).getContent().get(i).equals(songs.get(index - 1))) {	// if song from contents from a playlist is equal to the song given by the index in the songs arraylist
				playlists.get(i).deleteContent(index - 1);								// delete the content from the playlist
				songs.remove(index - 1);												// then remove the song from songs arraylist
			}
		}
	}
	
	public void sortSongsByYear()
	{
		Collections.sort(songs, new SongYearComparator());								
	
	}
 
	private class SongYearComparator implements Comparator<Song>
	{
		public int compare(Song songA, Song songB) {
			if (songA.getYear() > songB.getYear()) {			// if year of songA > year of songB, compare() returns 1
				return 1;
			}
			if (songA.getYear() < songB.getYear()) {			// if year of songA < year of songB, compare() returns -1
				return -1;
			}

			return 0;
		}
	}

	public void sortSongsByLength()
	{
	 Collections.sort(songs, new SongLengthComparator());
	}

	private class SongLengthComparator implements Comparator<Song>
	{
		public int compare(Song songA, Song songB) {
			if (songA.getLength() > songB.getLength()) {		// if length of songA > length of songB, return 1
				return 1;
			}
			if (songA.getLength() < songB.getLength()) {		// if length of songA < length of songB, return -1
				return -1;
			}

			return 0;

		}
		
	}

	public void sortSongsByName()
	{
		Collections.sort(songs);
	}

	public void playSong(int index)
	{
		if (index < 1 || index > songs.size())
		{
			throw new AudioContentNotFoundException("Song not found");
		}
		
		songs.get(index-1).play();
	}
	
	public boolean playPodcast(int index, int season, int episode)
	{
		return false;
	}
	
	public boolean printPodcastEpisodes(int index, int season)
	{
		return false;
	}
	
	public void playAudioBook(int index, int chapter)
	{
		if (index < 1 || index > audiobooks.size()) { // checking to see if book index is out of range
			throw new AudioContentNotFoundException("AudioBook Not Found");
		}

		if (chapter < 1 || chapter > audiobooks.get(index - 1).getNumberOfChapters()) {		// checking to see if chapter index is out of range
			throw new ChapterNotFoundException("Chapter Not Found");
		}

		audiobooks.get(index - 1).selectChapter(chapter);			// selecting the chapter to play
		audiobooks.get(index - 1).play();							// playing the chapter
	}
	
	public void printAudioBookTOC(int index)
	{
		if (index < 1 || index > audiobooks.size()) {	// checking to see if the book index is out of range
			throw new AudioContentNotFoundException("AudioBook Not Found");
		}

		audiobooks.get(index - 1).printTOC();			// printing table of contents of the audiobook

	}
	
	public void makePlaylist(String title)
	{
		Playlist newPlaylist = new Playlist(title);		// creating a new Playlist object to add
		
		if (playlists.contains(newPlaylist)) {						// checking that playlist does not already exist
			throw new PlaylistAlreadyExistsException("Playlist " + title + " Already Exists");
		}

		playlists.add(newPlaylist);			// if not, add the new Playlist to playlists arraylist
	}
	
	public void printPlaylist(String title)
	{
		for (int i = 0; i < playlists.size(); i++) {
			if (playlists.get(i).getTitle().equals(title)) {	// finding the playlist by seeing if title is equal
				playlists.get(i).printContents();		// printing contents of playlist
			}
			else {
				throw new PlaylistNotFoundException("Playlist not found");
			}
		}
	}
	
	public void playPlaylist(String playlistTitle)
	{
		for (int i = 0; i < playlists.size(); i++) {
			if (playlists.get(i).getTitle().equals(playlistTitle)) {	// finding the playlist
				playlists.get(i).playAll();			// playing all the content using playAll() method from Playlist class
			}
			else {
				throw new PlaylistNotFoundException("Playlist not found");
			}
		}
	}
	
	public void playPlaylist(String playlistTitle, int indexInPL)
	{
		for (int i = 0; i < playlists.size(); i++) {
			if (playlists.get(i).getTitle().equals(playlistTitle)) {		// finding the playlist
				playlists.get(i).play(indexInPL);		// playing the song or audiobook at the index given
			}
			else {
				throw new PlaylistNotFoundException("Playlist not found");
			}
		}
	}
	
	public void addContentToPlaylist(String type, int index, String playlistTitle)
	{
		for (int i = 0; i < playlists.size(); i++) {
			if (playlists.get(i).getTitle().equals(playlistTitle)) { // finding the playlist
				if (type.equals(Song.TYPENAME)) {	// checking if its a song
					playlists.get(i).addContent(songs.get(index));	// adding song to the specific playlist
				}
				if (type.equals(AudioBook.TYPENAME)) {	// checking if its an audiobook
					playlists.get(i).addContent(audiobooks.get(index));		// adding audiobook to the specific playlist
				}
			}
			else {
				throw new PlaylistNotFoundException("Playlist not found");
			}
		}

	}


	public void delContentFromPlaylist(int index, String title)
	{
		for (int i = 0; i < playlists.size(); i++) {
			if (playlists.get(i).getTitle().equals(title)) {		// finding the playlist
				playlists.get(i).deleteContent(index);				// deleting the content from the playlist using deleteContent() from Playlist class
			}
			else {
				throw new PlaylistNotFoundException("Playlist not found");
			}
		}
	}

}

class AudioContentNotFoundException extends RuntimeException {		// creating exception for when audiocontent does not exist
	public AudioContentNotFoundException() {
	}

	public AudioContentNotFoundException(String message) {
		super(message);
	}

}

class ChapterNotFoundException extends RuntimeException {			// creating exception for when chapter does not exist
	public ChapterNotFoundException() {
	}

	public ChapterNotFoundException(String message) {
		super(message);
	}
}

class PlaylistNotFoundException extends RuntimeException {				// creating exception for when playlist does not exist	
	public PlaylistNotFoundException() {
	}

	public PlaylistNotFoundException(String message) {
		super(message);
	}
}

class AudioContentAlreadyExistsException extends RuntimeException {		// creating exception for when audiocontent already exists
	public AudioContentAlreadyExistsException() {
	}

	public AudioContentAlreadyExistsException(String message) {
		super(message);
	}
}

class PlaylistAlreadyExistsException extends RuntimeException {			// creating exception for when playlist already exists
	public PlaylistAlreadyExistsException() {
	}

	public PlaylistAlreadyExistsException(String message) {
		super(message);
	}
}