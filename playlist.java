import java.util.ArrayList;

public class Playlist
{
	private String title;
	private ArrayList<AudioContent> contents; // songs, books, or podcasts or even mixture
	
	public Playlist(String title)
	{
		this.title = title;
		contents = new ArrayList<AudioContent>();
	}
	
	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public void addContent(AudioContent content)
	{
		contents.add(content);
	}
	
	public ArrayList<AudioContent> getContent()
	{
		return contents;
	}

	public void setContent(ArrayList<AudioContent> contents)
	{
		this.contents = contents;
	}
	
	public void printContents()
	{
		for (int i = 0; i < contents.size(); i++) { // for loop to loop through every audio content object in contents arraylist
			System.out.print((i + 1) + ". ");	// printing the number index
			contents.get(i).printInfo();		// using printInfo() method to print information about each object
			System.out.print("\n");			// new line, formatting
		}
		
	}

	public void playAll()
	{
		for (int i = 0; i < contents.size(); i++) {		// for loop to go through all contents
			contents.get(i).play();						// playing each object
			System.out.print("\n");					// new line, formatting
		}
		
	}
	
	public void play(int index)
	{
		if (contains(index)) {				// using contains method below to check if index works
			contents.get(index).play();		// get the audiocontent object from the index and play it
		}
	}
	
	public boolean contains(int index)
	{
		return index >= 1 && index <= contents.size();
	}
	
	public boolean equals(Object other)
	{
		Playlist otherPlaylist = (Playlist) other; // casting Object other to Playlist object

		if (title.equals(otherPlaylist.getTitle())) {	// checking if titles are equal
			return true;
		}

		return false;
	}
	
	public void deleteContent(int index)
	{
		if (contains(index)) { 				// using contains method to check index
			contents.remove(index - 1);		// removing object from contents arraylist
		}


	}
	
	
}
