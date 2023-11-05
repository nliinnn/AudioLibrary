import java.util.ArrayList;

public class AudioBook extends AudioContent
{
	public static final String TYPENAME =	"AUDIOBOOK";
	
	private String author; 
	private String narrator;
	private ArrayList<String> chapterTitles;
	private ArrayList<String> chapters;
	private int currentChapter = 0;

	
	public AudioBook(String title, int year, String id, String type, String audioFile, int length,
									String author, String narrator, ArrayList<String> chapterTitles, ArrayList<String> chapters)
	{
		super(title, year, id, type, audioFile, length);
		this.author = author;
		this.narrator = narrator;
		this.chapterTitles = chapterTitles;
		this.chapters = chapters;
	}
	
	public String getType()
	{
		return TYPENAME;
	}

	public void printInfo()
	{
		super.printInfo();		// use method printInfo() from parent class
		System.out.println("Author: " + author + " Narrated by: " + narrator);	// print other information from AudioBook object
	}
	
	public void play()
	{
		setAudioFile(chapterTitles.get(currentChapter) + ".\n" + chapters.get(currentChapter));		// set audioFile to current chapter title and current chapter text
		super.play();	// using play() method of parent class
	}
	
	public void printTOC()
	{
		for (int i = 0; i < this.getNumberOfChapters(); i++) {
			System.out.println("Chapter " + (i + 1) + ". " + chapterTitles.get(i));		// using for loop to print index and title of every chapter in an audiobook
			System.out.print("\n");
		}

	}

	public void selectChapter(int chapter)
	{
		if (chapter >= 1 && chapter <= chapters.size())
		{
			currentChapter = chapter - 1;
		}
	}
	
	public boolean equals(Object other)
	{
		AudioBook otherAudioBook = (AudioBook) other;	// casting Object other to Audiobook otherAudioBook

		if (author.equals(otherAudioBook.getAuthor()) && narrator.equals(otherAudioBook.getNarrator()) && super.equals(other)) { 	// checking if author and narrator are equal and if equals() from parent class is true
			return true;
		}

		return false;
	}
	
	public int getNumberOfChapters()
	{
		return chapters.size();
	}

	public String getAuthor()
	{
		return author;
	}

	public void setAuthor(String author)
	{
		this.author = author;
	}

	public String getNarrator()
	{
		return narrator;
	}

	public void setNarrator(String narrator)
	{
		this.narrator = narrator;
	}

	public ArrayList<String> getChapterTitles()
	{
		return chapterTitles;
	}

	public void setChapterTitles(ArrayList<String> chapterTitles)
	{
		this.chapterTitles = chapterTitles;
	}

	public ArrayList<String> getChapters()
	{
		return chapters;
	}

	public void setChapters(ArrayList<String> chapters)
	{
		this.chapters = chapters;
	}

}
