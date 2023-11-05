import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

// simulation of a music library app

public class MyAudioUI
{
	public static void main(String[] args)
	{

		AudioContentStore store = new AudioContentStore();
		
		Library mylibrary = new Library();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		while (scanner.hasNextLine())
		{
			String action = scanner.nextLine();

			if (action == null || action.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}
			else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
				return;
			
			else if (action.equalsIgnoreCase("STORE"))	// list all songs
			{
				store.listAll(); 
			}
			else if (action.equalsIgnoreCase("SONGS"))	// list all songs
			{
				mylibrary.listAllSongs(); 
			}
			else if (action.equalsIgnoreCase("BOOKS"))	// list all songs
			{
				mylibrary.listAllAudioBooks(); 
			}
			else if (action.equalsIgnoreCase("PODCASTS"))	// list all songs
			{
				mylibrary.listAllPodcasts(); 
			}
			else if (action.equalsIgnoreCase("ARTISTS"))	// list all songs
			{
				mylibrary.listAllArtists(); 
			}
			else if (action.equalsIgnoreCase("PLAYLISTS"))	// list all play lists
			{
				mylibrary.listAllPlaylists(); 
			}
			else if (action.equalsIgnoreCase("DOWNLOAD")) 
			{
				int fromIndex = 0;		// index for the first index for downloading
				int toIndex = 0;		// index for the last index for downloading
					
				System.out.print("From Store Content #: ");

				if (scanner.hasNextInt())
				{
					fromIndex = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}

				System.out.print("To Store Content #: ");

				if (scanner.hasNextInt()) {
					toIndex =  scanner.nextInt();
					scanner.nextLine();
				}
					

				while (fromIndex <= toIndex) {		// loop to go through indices starting from fromIndex until toIndex
					try {							// trying the download action with each index
						mylibrary.download(store.getContent(fromIndex));
					}
					catch (AudioContentAlreadyExistsException exception) {		// catching the index if the content is already downloaded
						System.out.println(exception.getMessage());
					}

					fromIndex++;		// incrementing fromIndex for the loop

				}
			}
			else if (action.equalsIgnoreCase("DOWNLOADA"))
			{
				String artistName = "";					// variable to store artist's name	
				System.out.print("Artist Name: ");	// printing for user to input artist name

				if (scanner.hasNext()) {
					artistName = scanner.nextLine();		
				}

				for (int i = 0; i < store.artistIndex(artistName).size(); i++) {				// looping through the indices of the selected artist's content in the store
					try {																		// trying the download action by downloading each song in the arraylist of artist
						mylibrary.download(store.getContent(store.artistIndex(artistName).get(i) + 1));
					}
					catch (AudioContentAlreadyExistsException exception) {		// catching exception
						System.out.println(exception.getMessage());
					}
				}
			}
			else if (action.equalsIgnoreCase("DOWNLOADG"))
			{
				String genreName = "";							// variable to store the genre name
				System.out.print("Genre: ");					// getting user input for genre

				if (scanner.hasNext()) {
					genreName = scanner.nextLine();
				}

				for (int i = 0; i < store.genreIndex(genreName).size(); i++) {					// looping through the indices for the selected genre
					try {																		// trying download action for all the content in the arraylist of the genre
						mylibrary.download(store.getContent(store.genreIndex(genreName).get(i) + 1));
					}
					catch (AudioContentAlreadyExistsException exception) {			// catching exception
						System.out.println(exception.getMessage());
					}
				}
			}
			else if (action.equalsIgnoreCase("PLAYSONG")) 
			{
				try {
					int index = 0;			// creating index variable and printing song number prompt for input
					System.out.print("Song Number: ");

					if (scanner.hasNextInt()) { // getting song number input and storing into index
						index = scanner.nextInt();
						scanner.nextLine();
					}

					mylibrary.playSong(index);
				}
				catch (AudioContentNotFoundException exception) {
					System.out.println(exception.getMessage());
				}

			}
			else if (action.equalsIgnoreCase("BOOKTOC")) 
			{
				try {
					int bookIndex = 0;	
					System.out.print("Audio Book Number: ");

					if (scanner.hasNextInt()) {
						bookIndex = scanner.nextInt();
						scanner.nextLine();
					}

					mylibrary.printAudioBookTOC(bookIndex);
				}
				catch (AudioContentNotFoundException exception) {
					System.out.println(exception.getMessage());
				}
				
			}
			else if (action.equalsIgnoreCase("PLAYBOOK")) 
			{
				try {
					int bookIndex = 0;				// creating two variables for book index and chapter index
					int chapterIndex = 0;
					System.out.print("Audio Book Number: ");

					if (scanner.hasNextInt()) {			// getting input for both variables
						bookIndex = scanner.nextInt();
						scanner.nextLine();
					}

					System.out.print("Chapter: ");

					if (scanner.hasNextInt()) {
						chapterIndex = scanner.nextInt();
						scanner.nextLine();
					}
					
					mylibrary.playAudioBook(bookIndex, chapterIndex);
				}
				catch (AudioContentNotFoundException exception) {
					System.out.println(exception.getMessage());
				}
				catch (ChapterNotFoundException exception) {
					System.out.println(exception.getMessage());
				}
				
			}
			
			else if (action.equalsIgnoreCase("PLAYALLPL")) 
			{
				try {
					String playlistTitle = "";
					System.out.print("Playlist Title: "); // getting input for playlist title

					if (scanner.hasNext()) {
						playlistTitle = scanner.next();
						scanner.nextLine();
					}

					mylibrary.playPlaylist(playlistTitle); // calling method to play everything in playlist
				}
				catch (PlaylistNotFoundException exception) {
					System.out.println(exception.getMessage());
				}
				
			}
			
			else if (action.equalsIgnoreCase("PLAYPL")) 
			{
				try{
					String playlistTitle = "";
					System.out.print("Playlist Title: ");

					if (scanner.hasNext()) {
						playlistTitle = scanner.next();
						scanner.nextLine();
					}

					int indexInPL = 0;
					System.out.print("Content Number: ");

					if (scanner.hasNextInt()) {
						indexInPL = scanner.nextInt();
						scanner.nextLine();
					}
				
					mylibrary.playPlaylist(playlistTitle, indexInPL - 1); // playing particular object in playlist, index - 1 to account for user input not beginning at 0
				}
				catch (PlaylistNotFoundException exception) {
					System.out.println(exception.getMessage());
				}
				
			}
			
			else if (action.equalsIgnoreCase("DELSONG")) 
			{
				try {
					int index = 0;
					System.out.print("Library Song #: ");

					if (scanner.hasNextInt()) {
						index = scanner.nextInt();
						scanner.nextLine();
					}
						mylibrary.deleteSong(index);	// deleting song from playlist

				}
				catch (AudioContentNotFoundException exception) {
					System.out.println(exception.getMessage());
				}
					
			}
			
			else if (action.equalsIgnoreCase("MAKEPL")) 
			{
				try {
					String title = "";
					System.out.print("Playlist Title: ");

					if (scanner.hasNext()) {
						title = scanner.next();
						scanner.nextLine();
					}
					
					mylibrary.makePlaylist(title);
				}
				catch (PlaylistAlreadyExistsException exception) {
					System.out.println(exception.getMessage());
				}

			}
			
			else if (action.equalsIgnoreCase("PRINTPL"))	// print playlist content
			{
				try {
					String title = "";
					System.out.print("Playlist Title: ");

					if (scanner.hasNext()) {
						title = scanner.next();
						scanner.nextLine();
					}

					mylibrary.printPlaylist(title);
				}
				catch (PlaylistNotFoundException exception) {
					System.out.println(exception.getMessage());
				}
			}
			
			else if (action.equalsIgnoreCase("ADDTOPL")) 
			{
				try {
					String playlistTitle = "";
					System.out.print("Playlist Title: ");

					if (scanner.hasNext()) {
						playlistTitle = scanner.next();
						scanner.nextLine();
					}

					String type = "";
					System.out.print("Content Type [SONG, PODCAST, AUDIOBOOK]: ");

					if (scanner.hasNext()) {
						type = scanner.next();
						scanner.nextLine();
					}

					int index = 0;
					System.out.print("Library Content #: ");

					if (scanner.hasNext()) {
						index = scanner.nextInt();
						scanner.nextLine();
					}

					mylibrary.addContentToPlaylist(type.toUpperCase(), index - 1, playlistTitle); // type uppercased so comparison to typename works, index - 1 to account for index beginning at 1 for user
				}
				catch (PlaylistNotFoundException exception) {
					System.out.println(exception.getMessage());
				}	
				
			}
			
			else if (action.equalsIgnoreCase("DELFROMPL")) 
			{
				try {
					String title = "";
					System.out.print("Playlist Title: ");

					if (scanner.hasNext()) {
						title = scanner.next();
						scanner.nextLine();
					}

					int index = 0;
					System.out.print("Playlist Content #: ");

					if (scanner.hasNextInt()) {
						index = scanner.nextInt();
						scanner.nextLine();
					}

					mylibrary.delContentFromPlaylist(index, title);
				}
				catch (PlaylistNotFoundException exception) {
					System.out.println(exception.getMessage());
				}	

			}
			
			else if (action.equalsIgnoreCase("SORTBYYEAR")) // sort songs by year
			{
				mylibrary.sortSongsByYear();
			}
			else if (action.equalsIgnoreCase("SORTBYNAME")) // sort songs by name (alphabetic)
			{
				mylibrary.sortSongsByName();
			}
			else if (action.equalsIgnoreCase("SORTBYLENGTH")) // sort songs by length
			{
				mylibrary.sortSongsByLength();
			}
			else if (action.equalsIgnoreCase("SEARCH")) // search songs
			{
				try {									// putting everything in a try for the exception
					String searchTitle = "";			// creating variable for the title to be searched
					System.out.print("Title: ");

					if (scanner.hasNext()) {
						searchTitle = scanner.nextLine();
					}

					store.searchContent(searchTitle);		// using method created in AudioContentStore to access map and search
				}
				catch (AudioContentNotFoundException exception) {		// catching exception
					System.out.println(exception.getMessage());
				}
			}
			else if (action.equalsIgnoreCase("SEARCHA")) 
			{
				try {									// putting in try for exception
					String artistName = "";				// creating variable for the artist name to be searched
					System.out.print("Artist: ");	

					if (scanner.hasNext()) {
						artistName = scanner.nextLine();
					}

					store.searchAuthor(artistName);			// using method created in other class to search for artist name
				}
				catch (AudioContentNotFoundException exception) {
					System.out.println(exception.getMessage());
				}
			}
			else if (action.equalsIgnoreCase("SEARCHG"))
			{
				try {									// putting try for exception
					String genreName = "";				// creating variable for the genre name to be searched
					System.out.print("Genre [POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL]: ");

					if (scanner.hasNext()) {
						genreName = scanner.nextLine();
					}

					store.searchGenreType(genreName);		// using method from other class to search for genre name
				}
				catch (AudioContentNotFoundException exception) {
					System.out.println(exception.getMessage());
				}
				
			}

			System.out.print("\n>");
		}
	}
}
