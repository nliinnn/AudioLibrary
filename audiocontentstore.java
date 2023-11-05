import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class AudioContentStore
{
		private ArrayList<AudioContent> contents;	// arraylist of contents that are shown in store
		private Map<String, Integer> titleMap;	// first map used to search by title
		private Map<String, ArrayList<Integer>> artistMap;	// second map used to search by artist
		private Map<String, ArrayList<Integer>> genreMap;	// third map used to search by genre

		public AudioContentStore()
		{
			
			try {												// putting everything in try to catch in the case of IOException
				contents = new ArrayList<AudioContent>();

				store();								// using method created to read file and add to contents arraylist

				titleMap = new HashMap<String, Integer>();					// initializing maps as hashmaps
				artistMap = new HashMap<String, ArrayList<Integer>>();
				genreMap = new HashMap<String, ArrayList<Integer>>();
				

				for (int i = 0; i < contents.size(); i++) {						// for loop to put content in title map with title as key and index as value
					titleMap.put(contents.get(i).getTitle(), i + 1);
				}

				for (int i = 0; i < contents.size(); i++) {						// for loop to put content in artist map with artist as key and index of content in arraylist for artist
					if (contents.get(i).getType().equals("SONG")) {	// checking if content is a song
						Song userSong = (Song) contents.get(i);					// casting to use methods on object from Song class
						if (artistMap.containsKey(userSong.getArtist())) {	// seeing if the artist exists in the store
							artistMap.get(userSong.getArtist()).add(i);		// adding index to arraylist of indices in the artist is in the map
						}
						else { 
							artistMap.put(userSong.getArtist(), new ArrayList<Integer>());	// if artist is not already in map, creating new element
							artistMap.get(userSong.getArtist()).add(i);						// then adding the index
						}
					}
					if (contents.get(i).getType().equals("AUDIOBOOK")) {				// putting audiobook content in the map
						AudioBook userAudioBook = (AudioBook) contents.get(i);
						if (artistMap.containsKey(userAudioBook.getAuthor())) {
							artistMap.get(userAudioBook.getAuthor()).add(i);
						}
						else {
							artistMap.put(userAudioBook.getAuthor(), new ArrayList<Integer>());
							artistMap.get(userAudioBook.getAuthor()).add(i);
						}
					}
				}

				for (int i = 0; i < contents.size(); i++) {										// for loop to add content to genre map
					if (contents.get(i).getType().equals("SONG")) {					// checking if content is song as only songs have genres
						Song genreSong = (Song) contents.get(i);								// casting to Song object
						if (genreMap.containsKey(genreSong.getGenre().toString())) {			// checking if the map contains the genre already
							genreMap.get(genreSong.getGenre().toString()).add(i);			// adding the index if the genre already exists in the map
						}
						else {
							genreMap.put(genreSong.getGenre().toString(), new ArrayList<Integer>());		// if genre is not in map, creating new element
							genreMap.get(genreSong.getGenre().toString()).add(i);						// adding the index
						}

						
					}
					
				}


			}
			catch (IOException exception) {						// catching IOException
				System.out.println(exception.getMessage());
				System.exit(1);
			}
		}
		
		
		public AudioContent getContent(int index)
		{
			if (index < 1 || index > contents.size())
			{
				return null;
			}
			return contents.get(index-1);
		}
		
		public void listAll()
		{
			for (int i = 0; i < contents.size(); i++)
			{
				int index = i + 1;
				System.out.print("" + index + ". ");
				contents.get(i).printInfo();
				System.out.println();
			}
		}

		private ArrayList<AudioContent> store() throws IOException {		// method to read from file and return contents arraylist
			File newFile = new File("store.txt");							// creating new File object with file name as "store.txt"
			Scanner in = new Scanner(newFile);										// creating scanner to read the file

			while (in.hasNextLine()) {						// checking for input
				String nextType = in.nextLine();			// scanning the file for information about the content
				
				if (nextType.equals("SONG")) {		// reading for song content
					String nextID = in.nextLine();
					String nextTitle = in.nextLine();
					int nextYear = in.nextInt();
					int nextLength = in.nextInt();
					in.nextLine();
					String nextArtist = in.nextLine();
					String nextComposer = in.nextLine();
					String nextGenre = in.nextLine();

					Song.Genre newGenre = Song.Genre.POP;
					
					if (nextGenre.equals("ROCK")) {		// checking for the genre
						newGenre = Song.Genre.ROCK;
					}
					if (nextGenre.equals("JAZZ")) {
						newGenre = Song.Genre.JAZZ;
					}
					if (nextGenre.equals("HIPHOP")) {
						newGenre = Song.Genre.HIPHOP;
					}
					if (nextGenre.equals("RAP")) {
						newGenre = Song.Genre.RAP;
					}
					if (nextGenre.equals("CLASSICAL")) {
						newGenre = Song.Genre.CLASSICAL;
					}

					int nextLyrics = in.nextInt();
					String lyrics = "";
					
					for (int i = 0; i < nextLyrics; i++) {				// for loop to read the lyrics
						String nextLyric = in.nextLine();
						lyrics = lyrics + nextLyric + "\n";
					}

					Song newSong = new Song(nextTitle, nextYear, nextID, nextType, "", nextLength, nextArtist, nextComposer, newGenre, lyrics);	// constructing Song object
					contents.add(newSong);		// adding it to contents
				}

				if (nextType.equals("AUDIOBOOK")) {		// reading for audiobook content
					ArrayList<String> chapterTitles = new ArrayList<String>();
					ArrayList<String> chapters = new ArrayList<String>();

					String nextID = in.nextLine();
					String nextTitle = in.nextLine();
					int nextYear = in.nextInt();
					int nextLength = in.nextInt();
					in.nextLine();
					String nextAuthor = in.nextLine();
					String nextNarrator = in.nextLine();
					int chapterNumbers = in.nextInt();
					in.nextLine();

					for (int i = 0; i < chapterNumbers; i++) {		// for loop to read chapter titles
						String chapterTitle = in.nextLine();
						chapterTitles.add(chapterTitle);
					}


					int chapterLines = in.nextInt();

					for (int i = 0; i < chapterLines; i++) {		// for loop to read lines of chapters
						String line = in.nextLine();
						String chapterLine = "";
						chapterLine = chapterLine + line;
					}

					AudioBook newAudiobook = new AudioBook(nextTitle, nextYear, nextID, nextType, "", nextLength, nextAuthor, nextNarrator, chapterTitles, chapters);		// constructing AudioBook object
					contents.add(newAudiobook);		// adding to contents
				}
			}

			return contents;

		}

		public void searchContent(String title) {			// method to search for the content using maps
			if (titleMap.containsKey(title)) {			// checking if the content is in the map
				AudioContent newContent = (AudioContent) contents.get(titleMap.get(title) - 1);				// casting to AudioContent if it is in the map
				System.out.print(titleMap.get(title) + ". ");			// printing the content that is searched for
				newContent.printInfo();
			}
			else {
				throw new AudioContentNotFoundException("No matches for " + title);		// throwing exception if title can't be found
			}
			
		}

		public void searchAuthor(String artist) {			// searching for author in map
			if (artistMap.containsKey(artist)) {			// checking if the content is in the map
				for (int i = 0; i < artistMap.get(artist).size(); i++) {										// looping through the index arraylist for artists
					AudioContent artistContent = (AudioContent) contents.get(artistMap.get(artist).get(i));	// casting the current index to an AudioBook object
					System.out.print((artistMap.get(artist).get(i) + 1) + ". ");		// printing the content that has the artist
					artistContent.printInfo();
					System.out.print("\n");
				}
			}
			else {
				throw new AudioContentNotFoundException("No matches for " + artist);	// throwing exception
			}
		}

		public void searchGenreType(String genre) {			// method to search for genre in the map
			if (genreMap.containsKey(genre)) {
				for (int i = 0; i < genreMap.get(genre).size(); i++) {
					AudioContent genreContent = (AudioContent) contents.get(genreMap.get(genre).get(i));
					System.out.print((genreMap.get(genre).get(i) + 1) + ". ");
					genreContent.printInfo();
					System.out.print("\n");
				}
			}
			else {
				throw new AudioContentNotFoundException("No matches for " + genre);			// throwing exception
			}
		}

		public ArrayList<Integer> artistIndex(String artistName) {		// method to return the indices of artist content
			return artistMap.get(artistName);
		}

		public ArrayList<Integer> genreIndex(String genreName) {		// method to return the indices of genre content
			return genreMap.get(genreName);
		}
		
}
