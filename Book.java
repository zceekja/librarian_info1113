import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

class Book{
	private String title;
	private String author;
	private String genre;
	private String serialNumber;
	private boolean avaliable;
	private Member currentRenter;
	private List<Member> renters = new LinkedList<Member>();

	public Book(String title, String author, String genre, String serialNumber) {
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.serialNumber = serialNumber;
		this.avaliable = true;
	}

	public static List<Book> filterAuthor(List<Book> books, String author){
		// Check for valid parameter.
		if (author == null || books  == null || books.isEmpty()) {
			return null;
		}
		// Create filter author book list.
		List<Book> books_by_author = new ArrayList<Book>();
		for (Book x: books) {
			if (x.getAuthor().equals(author)) {
				books_by_author.add(x);
			} 
		}
		return books_by_author;
	}

	public static List<Book> filterGenre(List<Book> books, String genre) {
		// Check for valid parameter.
		if (genre == null || books  == null || books.isEmpty()) {
			return null;
		}
		// Create filter genre book list.
		List<Book> books_by_genre = new ArrayList<Book>();
		for (Book x: books){
			if (x.getGenre().equals(genre)) {
				books_by_genre.add(x);
			} 
		}
		return books_by_genre;
	}

	public String getAuthor() {
		return author;
	}

	public String getGenre() {
		return genre;	
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public String getTitle() {
		return title;
	}

	public boolean isRented() {
		return !avaliable;
	}

	public Member getRenter() {
		return this.currentRenter;
	}

	public String longString() {
		if (avaliable){
			return serialNumber+": "+title+" ("+author+", "+genre+")\nCurrently available.";
		}
		else {
			return serialNumber+": "+title+" ("+author+", "+genre+")\nRented by: "+currentRenter.getMemberNumber()+".";
		}
	}

	public String shortString() {
		return title + " (" + author + ")";	
	}

	public static Book readBook(String filename, String serialNumber) {
		// Check for valid parameter.
		if (serialNumber == null) {
			return null;
		}
		// Check for valid parameter.
		if (filename == null) {
			return null;
		}
		File bookfile = new File(filename);
		try {
			Scanner scan = new Scanner(bookfile);
			String[] lineContent = new String[4]; 
			String line;
			// Extract a book from CSV file.
			while (scan.hasNextLine()) {
				line = scan.nextLine();
				lineContent = line.split(",");
				if (lineContent[0].equals(serialNumber)) {
					Book bookToAdd = new Book(lineContent[1],lineContent[2],lineContent[3],lineContent[0]);
					return bookToAdd;
				}
			}
		} catch (FileNotFoundException e) {
			return null;
		}
		return null;
	}

	public static List<Book> readBookCollection(String filename) {
		// Check for valid parameter.
		if(filename == null) {
			return null;
		}
		File bookfile = new File(filename);
		try {
			Scanner scan = new Scanner(bookfile);
			String[] lineContent = new String[4]; 
			String line;
			// Extract books from CSV file in to a list.
			List<Book> bookCollection = new LinkedList<Book>();
			while (scan.hasNextLine()){
				line = scan.nextLine();
				lineContent = line.split(",");
				if (lineContent[0].length() ==6) {
					Book bookToAdd = new Book(lineContent[1],lineContent[2],lineContent[3],lineContent[0]);
					bookCollection.add(bookToAdd);
				}
			}
			return bookCollection;
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	public boolean relinquish(Member member) {
		// Check for valid parameter.
		if (member == null) {
			return false;
		}
		// Check whether a book is being rent by member.
		if (currentRenter == null) {
			return false; 
		}
		// Check whether a book is being rent by given member.
		if (!currentRenter.getMemberNumber().equals(member.getMemberNumber())) {
			return false;
		}
		// relinquish book, and change status.
		else {
			avaliable = true;
			renters.add(member);
			currentRenter = null;
			return true;
		}
	}

	public boolean rent(Member member) {
		// Check for valid parameter.
		if (member == null) {
			return false;
		}
		// Check whether a book is available.
		if (avaliable) {
			currentRenter = member;
			avaliable = false;
			return true;
		}
		else {
			return false;
		}
	}

	public List<Member> renterHistory() {
		return renters;
	}

	public static void saveBookCollection(String filename, Collection<Book> books) {
		// Check for valid parameter.
		if (filename == null) {
			return;
		}
		// Check for valid parameter.
		if (filename.length() == 0) {
			return;
		}
		// Check for valid parameter.
		if (books == null) {
			return;
		}
		// Check for valid parameter.
		if (books.size() == 0) {
			return;
		}
		// Make a new list of books that have all values.
		List<Book> filterBook = new ArrayList<Book>();
		for (Book x: books){

			if ( x== null);
			else if (  x.getAuthor() == null || x.getTitle() ==null || x.getGenre() == null || x.getSerialNumber() == null ){
			}
			else{
				filterBook.add(x);
			}
		}
		File bookfile = new File(filename);

		try {
			// Write down a book detail to given file.
			PrintWriter writer = new PrintWriter(bookfile);
			writer.print("serialNumber,title,author,genre");
			for (Book x : filterBook) {
				writer.print("\n"+x.getSerialNumber() + ",");
				writer.print(x.getTitle() + ",");
				writer.print(x.getAuthor() + ",");
				writer.print(x.getGenre());
			}
			writer.close();
			System.out.println("Success.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
