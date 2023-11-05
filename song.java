public class Song extends AudioContent implements Comparable<Song>
{
	public static final String TYPENAME =	"SONG";
	
	public static enum Genre {POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL}; 
	private String artist; 	
	private String composer; 	
	private Genre  genre; 
	private String lyrics;
	
	
	
	public Song(String title, int year, String id, String type, String audioFile, int length, String artist,
			String composer, Song.Genre genre, String lyrics)
	{
		super(title, year, id, type, audioFile, length); // initializing variables from parent class
		this.artist = artist;
		this.composer = composer;
		this.genre = genre;
		this.lyrics = lyrics;
	}
	
	public String getType()
	{
		return TYPENAME;
	}
	
	public void printInfo()
	{
		super.printInfo();	// using printInfo() method from superclass
		System.out.println("Artist: " + artist + " Composer: " + composer + " Genre: " + genre);
		
	}
	
	public void play()
	{
		setAudioFile(lyrics); // set audioFile to lyrics string
		super.play();	// calling play() method from parent class
	}
	
	public String getComposer()
	{
		return composer;
	}
	public void setComposer(String composer)
	{
		this.composer = composer;
	}
	
	public String getArtist()
	{
		return artist;
	}
	public void setArtist(String artist)
	{
		this.artist = artist;
	}
	
	public String getLyrics()
	{
		return lyrics;
	}
	public void setLyrics(String lyrics)
	{
		this.lyrics = lyrics;
	}

	public Genre getGenre()
	{
		return genre;
	}

	public void setGenre(Genre genre)
	{
		this.genre = genre;
	}	
	
	public boolean equals(Object other)
	{
		Song otherSong = (Song) other;
		
		if (composer.equals(otherSong.getComposer()) && artist.equals(otherSong.getArtist()) && super.equals(other)) { // checking if composers and artists are equal, and checking if AudioContent information is equal using equals()
			return true;															
		}
		return false;
	}
	
	public int compareTo(Song other) // compareTo method that will be used to sort name
	{
		if (this.getTitle().compareTo(other.getTitle()) < 0) { // if the first title is "greater" than the second, the compareTo method returns a negative number less than 0
			return -1;	// in this condition it should return the negative number -1
		}
		if (this.getTitle().compareTo(other.getTitle()) > 0) { // if the second title is "greater" than the first, the compareTo method returns a positive number greater than 0
			return 1;	// in this condition it should return the positive number 1
		}

		return 0; // default return
		
	}
}