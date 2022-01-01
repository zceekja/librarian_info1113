import java.util.*;
import java.io.File;

class Library{
	
	private List<Book> allBooks = new LinkedList<Book>();
	private List<Member> allMembers = new LinkedList<Member>();
	private int memberCount = 0;

	public Library() {
	}

	public void addBook(String bookFile, String serialNumber) {
		File bookfile = new File(bookFile);
		//Check for existence of file.
		if (!bookfile.exists()){
			System.out.println("No such file.");
			return;
		}
		else { 
			//Check for existence of book within file.
			Book bookToAdd = new Book(null,null,null,null);
			bookToAdd = bookToAdd.readBook(bookFile,serialNumber);
			if (bookToAdd == null) {
				System.out.println("No such book in file.");
			}
			//Check whether book is already in the system or not before add to the System.
			else {
				boolean checkExist = false;
				for (Book x: allBooks) {
					if (x.getSerialNumber().equals(bookToAdd.getSerialNumber())) {
						checkExist = true;
					}
				}
				if (checkExist) {
					System.out.println("Book already exists in system.");
				}
				else {
					allBooks.add(bookToAdd);
					System.out.println("Successfully added: " + bookToAdd.shortString() + ".");
				}
			}
		}
	}

	public void addCollection(String filename){
		//Check for existence of file.
		File bookfile = new File(filename);
		if (!bookfile.exists()){
			System.out.println("No such collection.");
			return;
		}
		List<Book> booksToAdd = new LinkedList<Book>();
		Book bookToAdd = new Book(null,null,null,null);
		booksToAdd = bookToAdd.readBookCollection(filename);
		if (booksToAdd == null) {
			System.out.println("No books have been added to the system.");
		}
		else {
			//Only add books that not already in the system to the system.
			List<Book> nonDupeBook = new LinkedList<Book>();
			boolean checkToadd = true;
			int countAdd = 0;
			for (Book x : booksToAdd) {
				for (Book y : allBooks){
					if (x.getSerialNumber().equals(y.getSerialNumber())) checkToadd = false;
				}
				if (checkToadd){
					nonDupeBook.add(x);
					allBooks.add(x);
					countAdd++;
				}
				checkToadd = true;
			}
			if (nonDupeBook.size() > 1) {
				System.out.println(countAdd + " books successfully added.");
			}
			else if (nonDupeBook.size() == 1) {
				System.out.println(countAdd + " book successfully added.");
			}
			else 
			{
				System.out.println("No books have been added to the system.");
			}
		}	
	}

	public void addMember(String name) {
		Member newMember = new Member(name, Integer.toString(memberCount+100000));
		allMembers.add(newMember);
		memberCount++;
		System.out.println("Success.");
		return;
	}

	public void bookHistory(String serialNumber) {
		//Check for existence of books in the system.
		if (allBooks.size() == 0){
			System.out.println("No such book in system.");
			return;
		}
		//Check whether given serial number is match with any book in the system.
		for (Book x : allBooks){
			if (x.getSerialNumber().equals(serialNumber)) {
				List<Member> renters = new LinkedList<Member>();
				renters = x.renterHistory();
				//Found match!. but there are no record
				if (renters.size() == 0){
					System.out.println("No rental history.");
					return;
				}
				//Found match! print all record!
				else {
					for (Member y : renters) {
					System.out.println(y.getMemberNumber());
					}
					return;
				}
			}
		}
		//Not match
		System.out.println("No such book in system.");
		return;
	}

	public void common(String[] memberNumbers) {
		//Check for existence of members in the system.
		if (allMembers.size() == 0) {
			System.out.println("No members in system.");
			return;
		}
		//Check for existence of books in the system.
		if (allBooks.size() == 0) {
			System.out.println("No books in system.");
			return;
		}
		boolean checkExist = false;
		//Check, if there are any given number that not match. 
		for (String x : memberNumbers){
			for (Member y: allMembers){
				if (y.getMemberNumber().equals(x)) checkExist = true;
			}
			if (!checkExist){
				System.out.println("No such member in system.");
				return;
			}
			checkExist = false;
		}
		//Check, whether there are duplicate member number.
		int arrLength = memberNumbers.length;
		for (int i =0 ; i < arrLength-1; i++){
			for (int j = i+1; j < arrLength; j++){
				if (memberNumbers[i].equals(memberNumbers[j])){
					System.out.println("Duplicate members provided.");
					return;
				}
			}
		}
		// Covert serial number to member.
		Member[] members = new Member[arrLength];
		int index = 0;
		for (String x: memberNumbers){
			for (Member y : allMembers){
				if (y.getMemberNumber().equals(x)) {
					members[index] = y;
					index++;
				}
			}
		}
		// get common book with commonBooks method
		List<Book> commonB = new LinkedList<Book>();
		commonB = members[0].commonBooks(members);
		if (commonB.size() == 0) {
			System.out.println("No common books.");
			return;
		}
		else {
			for (Book z : commonB) {
				System.out.println(z.shortString());
			}
		}
	}

	public void getAllBooks(boolean fullString) {
		//Check for existence of books in the system.
		if (allBooks.size() == 0){
			System.out.println("No books in system.");
			return;
		}
		int countBooks = allBooks.size();
		//Check whether Long command is given.
		if (fullString) {
			for (int i = 0; i < countBooks; i++) {
				System.out.println(allBooks.get(i).longString());
				if (i != countBooks-1) System.out.print("\n");
			}
		}
		else {
			for (int i = 0; i < countBooks; i++) {
				System.out.println(allBooks.get(i).shortString());
			}
		}
	}

	public void getAvailableBooks(boolean fullString) {
		//Check for existence of books in the system
		if (allBooks.size() == 0) {
			System.out.println("No books in system.");
			return;
		}
		boolean checkAvailable = false;
		int countBooks = allBooks.size();
		//Check whether LONG command is given
		if (fullString){
			for (int i = 0; i < countBooks; i++) {
				if (!allBooks.get(i).isRented()) {
					System.out.println(allBooks.get(i).longString());
					checkAvailable =true;
					if (i != countBooks-1) System.out.print("\n");
				}
			}
			if (!checkAvailable) {
				System.out.println("No books available.");
			}
			return;
		}
		else {			
			for (int i = 0; i < countBooks; i++) {
				if (!allBooks.get(i).isRented()) {
					System.out.println(allBooks.get(i).shortString());
					checkAvailable = true;
				}
			}
			if (!checkAvailable){
				System.out.println("No books available.");
			}
			return;
		}
	}

	public void getBook(String serialNumber, boolean fullString) {
		//Check for existence of books in the system.
		if (allBooks.size() == 0){
			System.out.println("No books in system.");
			return;
		}
		//Check whether LONG command is given.
		for (Book x : allBooks){
			if (x.getSerialNumber().equals(serialNumber)){
				if (fullString){
					System.out.println(x.longString());
				}
				else {
					System.out.println(x.shortString());
				}
				return;
			}
		}
		System.out.println("No such book in system.");
		return;
	}

	public void getBooksByAuthor(String author) {
		//Check for existence of books in the system.
		if (allBooks.size() == 0){
			System.out.println("No books in system.");
			return;
		}
		//Extract all book that match given author name.
		List<Book> booksByAuthor = new LinkedList<Book>();
		for (Book x : allBooks){
			if (x.getAuthor().equals(author)) {
				booksByAuthor.add(x);
			}
		}
		//If none found, tell user no books found.
		if (booksByAuthor.size() == 0) {
			System.out.println("No books by "+ author +".");
			return;
		}
		//Make sorted list by serial number.
		else {
			List<Book> sortedBooksByAuthor = new LinkedList<Book>();
			int listSize = booksByAuthor.size();
			String min = "999999";
			int index = 0;
			while (booksByAuthor.size()> 0) {
				if (booksByAuthor.size() == 1) {
					sortedBooksByAuthor.add(booksByAuthor.get(0));
					booksByAuthor.remove(0);
				}
				else {
					for (int i = 0; i < booksByAuthor.size(); i++){
						if (min.compareTo(booksByAuthor.get(i).getSerialNumber()) >0){
							min = booksByAuthor.get(i).getSerialNumber();
							index = i;
						}
					}
					sortedBooksByAuthor.add(booksByAuthor.get(index));
					booksByAuthor.remove(index);
					min = "999999";
					index = 0;
				}
			}
			//Print sorted list.
			for (Book x : sortedBooksByAuthor) {

				System.out.println(x.shortString());
			}
			return;
		 }
	}

	public void getBooksByGenre(String genre) {
		//Check for existence of books in the system.
		if (allBooks.size() == 0){
			System.out.println("No books in system.");
			return;
		}
		//Extract all book that match given genre.
		List<Book> booksByGenre = new LinkedList<Book>();
		for (Book x : allBooks) {
			if (x.getGenre().equals(genre)) {
				booksByGenre.add(x);
			}
		}
		//If none found, tell user no books found.
		if (booksByGenre.size() == 0){
			System.out.println("No books with genre "+genre+".");
			return;
		}
		else {
			//Make sorted list by serial number.
			List<Book> sortedBooksByGenre = new LinkedList<Book>();
			int listSize = booksByGenre.size();
			String min = "999999";
			int index = 0;
			while (booksByGenre.size()> 0) {
				if (booksByGenre.size() == 1) {
					sortedBooksByGenre.add(booksByGenre.get(0));
					booksByGenre.remove(0);
				}
				else {
					for (int i = 0; i < booksByGenre.size(); i++) {
						if (min.compareTo(booksByGenre.get(i).getSerialNumber()) >0) {
							min = booksByGenre.get(i).getSerialNumber();
							index = i;
						}
					}
					sortedBooksByGenre.add(booksByGenre.get(index));
					booksByGenre.remove(index);
					min = "999999";
					index = 0;
				}
			}
			//Print sorted list.
			for (Book x : sortedBooksByGenre) {
				System.out.println(x.shortString());
			}
			return;
		}
	}

	public void getCopies() {
		//Check for existence of books in system.
		if(allBooks.size() ==0) {
			System.out.println("No books in system.");
			return;
		}
		//make a map, contain Book as key, and number of copy as value.
		HashMap<String,Integer> books= new HashMap<String,Integer>();
		for (Book x: allBooks){
			if (!books.containsKey(x.shortString())) {
				books.put(x.shortString(),1);
			}
			else {
				books.put(x.shortString(),books.get(x.shortString())+1);
			}
		}
		// Make a new list to sort lexicographically.
		List<String> booksLexico = new ArrayList<String>();
		for (HashMap.Entry<String,Integer> i: books.entrySet()) {
			booksLexico.add(i.getKey());
		}
		booksLexico.sort((x,y) -> x.compareTo(y));
		// Print out number of copies of each book in system.
		for (String j : booksLexico){
			for (HashMap.Entry<String,Integer> y: books.entrySet()) {
				if (j.equals(y.getKey())){
					System.out.println(y.getKey() + ": "+y.getValue().toString());
				}
			}
		}
	}

	public void getGenres() {
		//Check for existence of books in system.
		if (allBooks.size() ==0) {
			System.out.println("No books in system.");
			return;
		}
		// Make a new list of genres, only add non duplicate.
		List<String> genres = new LinkedList<String>();
		for (Book x : allBooks) {
			if (!genres.contains(x.getGenre())) {
				genres.add(x.getGenre());
			}

		}
		// Sort lexigraphically.
		genres.sort((x, y) -> x.compareTo(y));
		for (String y: genres){
			System.out.println(y);
		}
		return;
	}

	public void getAuthors() {
		// Check for existence of books in system.
		if (allBooks.size() ==0) {
			System.out.println("No books in system.");
			return;
		}
		// Make a new list of authors, only add non duplicate.
		List<String> authors = new LinkedList<String>();
		for (Book x : allBooks) {
			if (!authors.contains(x.getAuthor())) {
				authors.add(x.getAuthor());
			}
		}
		// Sort lexigraphically.
		authors.sort((x, y) -> x.compareTo(y));
		for (String y: authors){
			System.out.println(y);
		}
		return;
	}

	public void getMember(String memberNumbers) {
		// Check for existence of members in system.
		if (allMembers.size() == 0) {
			System.out.println("No members in system.");
			return;
		}
		else {
			// Check whether given member number is match any member in the system.
			boolean checkExist = false;
			for (Member x : allMembers){
				if (x.getMemberNumber().equals(memberNumbers)) {
					System.out.println(x.getMemberNumber()+": "+x.getName());
					checkExist = true;
				}
			}
			if (!checkExist) System.out.println("No such member in system.");
			return;
		}
	}

	public void getMemberBooks(String memberNumbers) {
		// Check for existence of members in system.
		if (allMembers.size() == 0){
			System.out.println("No members in system.");
			return;
		}
		else {
			// Check whether given member number is match with any member in the system.
			for (Member x : allMembers) {
				if (x.getMemberNumber().equals(memberNumbers)) {
					// Check whether member is currently renting.
					if (x.renting().size() == 0) {
						System.out.println("Member not currently renting.");
					}

					else {
						for (Book y : x.renting()) {
							System.out.println(y.shortString());
						}
					}
					return;
				}
			}
		}
		// Given member number is not match with any member in system.
		System.out.println("No such member in system.");
		return;
	}

	public void memberRentalHistoy(String memberNumbers) {
		// Check for existence of member in system.
		if (allMembers.size() == 0){
			System.out.println("No members in system.");
			return;
		}
		// Check whether given member number is match with any member in system.
		else {
			for (Member x : allMembers){
				if (x.getMemberNumber().equals(memberNumbers)) {
					// Check whether member have rental history or not.
					if (x.history().size() ==0){
						System.out.println("No rental history for member.");
					}
					else {
						for (Book y : x.history()) {
							System.out.println(y.shortString());
						}	
					}
					return;
				}
			}
			// Given member number is not match with any member in system.
			System.out.println("No such member in system.");
			return;
		}
	}

	public void relinquishAll(String memberNumbers) {
		// Check for existence of member in system.
		if (allMembers.size() == 0){
			System.out.println("No members in system.");
			return;
		}
		// Check whether given member is exist in the system.
		else {
			for (Member x : allMembers) {
				// if found relinquish all books.
				if (x.getMemberNumber().equals(memberNumbers)) {
					x.relinquishAll();
					System.out.println("Success.");
					return;
				}
			}
		}
		// Given member number is not match with any member in system.
		System.out.println("No such member in system.");
		return;
	}

	public void relinquishBook(String memberNumbers, String serialNumber) {
		// Check for existence of members in system.
		if (allMembers.size() == 0) {
			System.out.println("No members in system.");
			return;
		}
		// Check for existence of books in system.	
		if (allBooks.size() ==0) {
			System.out.println("No books in system.");
			return;
		}
		// Extract a book from given serial number (if match).	
		Book book = new Book(null,null,null,null);	
		for (Book x : allBooks){
			if (x.getSerialNumber().equals(serialNumber)) {
				book = x;
			}
		}
		// Check whether given member number is exist in the system.
		boolean checkMember = false;
		for (Member i : allMembers) {
			if (i.getMemberNumber().equals(memberNumbers)) {
				checkMember = true;
			}
		}
		if (!checkMember){
			System.out.println("No such member in system.");
			return;
		} 
		// Serial number not match with any books in system.
		if (book.getSerialNumber() == null){
			System.out.println("No such book in system.");
			return;
		}
		// Check whether book is rented by anyone.
		if (!book.isRented()) {
			System.out.println("Unable to return book.");
			return;
		}
		// Check whether book is cururently renting by given member number.
		if (book.isRented() == true && !book.getRenter().getMemberNumber().equals(memberNumbers)) {
			System.out.println("Unable to return book."); 
			return;
		}
		// Relinquish given book!
		for (Member x : allMembers){
			if (x.getMemberNumber().equals(memberNumbers)) {
				x.relinquish(book);
				System.out.println("Success.");
				return;
			}
		}
	}

	public void rentBook(String memberNumbers, String serialNumber) {
		// Check for existence of members in system.
		if (allMembers.size() == 0) {
			System.out.println("No members in system.");
			return;
		}
		// Check for existence of books in system.
		if (allBooks.size() == 0){
			System.out.println("No books in system.");
			return;
		}
		// Extract book from given serial number (if match). 
		Book book = new Book(null,null,null,null);
		for (Book x : allBooks) {
			if (x.getSerialNumber().equals(serialNumber)) {
				book = x;
			}
		}
		// Check whether given member number is exist in the system.
		boolean memberExist =false;
		for (Member x : allMembers) {
			if (x.getMemberNumber().equals(memberNumbers)) {
				memberExist = true;
			}
		}
		if (!memberExist) {
			System.out.println("No such member in system.");
			return;			
		}
		// Serial number not match with any books in system.
		if (book.getSerialNumber() == null) {
			System.out.println("No such book in system.");
		}
		else {
			for (Member x : allMembers) {
				if (x.getMemberNumber().equals(memberNumbers)) {
					//Rent a book to member if it is available.
					if (x.rent(book)) {
						System.out.println("Success.");
					}
					else {
						System.out.println("Book is currently unavailable.");
					}
					return;
				}
			}
		}
	}

	public void saveCollection(String filename) {
		// Check for existence of file.
		if (filename == null) {
			return;
		}
		// Check for CSV file
		if (filename.length() > 4) {
			String checkCsv = filename.substring(filename.length() - 4);
			if (!checkCsv.equals(".csv")) {
				return;
			}
		}
		else {
			return;
		}
		// Check for existence of books in system.
		if (allBooks.size() == 0 | allBooks == null) {
			System.out.println("No books in system.");
			return;
		}
		// Save books to CSV file.
		Book bookToAdd = new Book(null,null,null,null);
		bookToAdd.saveBookCollection(filename, allBooks);		
	}

	public static void main(String[] args){
		try {
			// Create Library.
			Library gg = new Library();
			Scanner scan = new Scanner(System.in);
			String[] lines = new String[100];
			String line;
			// Ask user for input.
			System.out.print("user: ");
			line = scan.nextLine();
			// If user give valid command, keep asking for input.
			while (!line.equals("EXIT") && line.length() != 0) {
				lines = line.split(" ");
				// EXIT command .
				if (lines[0].equalsIgnoreCase("EXIT")) {
					System.out.println("Ending Library process.");
					return;
				}
				else if (lines[0].equalsIgnoreCase("LIST")) {
					// LIST ALL command.
					if (lines[1].equalsIgnoreCase("ALL")) {
							if (lines.length > 2 && lines[2].equalsIgnoreCase("LONG")) {
								gg.getAllBooks(true);
							}
							else gg.getAllBooks(false);
					}
					// LIST AVAILABLE command.
					else if(lines[1].equalsIgnoreCase("AVAILABLE")) {
					
							if (lines.length > 2 && lines[2].equalsIgnoreCase("LONG")) {
								gg.getAvailableBooks(true);
							}
							else gg.getAvailableBooks(false);
					}
					// LIST GENRES command.
					else if (lines[1].equalsIgnoreCase("GENRES")) {
							gg.getGenres();
					}
					// LIST AUTHORS command.
					else if (lines[1].equalsIgnoreCase("AUTHORS")) {
							gg.getAuthors();
					}
				}
				// NUMBER COPIES command.
				else if(lines[0].equalsIgnoreCase("NUMBER")) {
					if(lines[1].equalsIgnoreCase("COPIES")) {
							gg.getCopies();
					}
				}
				else if(lines[0].equalsIgnoreCase("BOOK")) {
					// BOOK HISTORY command.
					if(lines[1].equalsIgnoreCase("HISTORY")) {
							gg.bookHistory(lines[2]);
					}
					// BOOK command.
				 	else {
						if (lines.length > 2 && lines[2].equalsIgnoreCase("LONG")){
							gg.getBook(lines[1],true);
							}
						else {
							gg.getBook(lines[1],false);
						}
					}
				}
				else if (lines[0].equalsIgnoreCase("MEMBER")) {
					// MEMBER BOOKS command.
					if (lines[1].equalsIgnoreCase("BOOKS")) {
							gg.getMemberBooks(lines[2]);
					}
					// MEMBER HISTORY command.
					else if (lines[1].equalsIgnoreCase("HISTORY")) {
							gg.memberRentalHistoy(lines[2]);
					}
					// MEMBER command.
					else {
							gg.getMember(lines[1]);
					}
				}
				// RENT command.
				else if (lines[0].equalsIgnoreCase("RENT")) {
					gg.rentBook(lines[1],lines[2]);
				}
				else if (lines[0].equalsIgnoreCase("RELINQUISH")) {
					// RENLINQUISH ALL command.
					if (lines[1].equalsIgnoreCase("ALL")) {
							gg.relinquishAll(lines[2]);
					}
					// RELINQUISH command
					else {
							gg.relinquishBook(lines[1],lines[2]);
					}
				}
				else if (lines[0].equalsIgnoreCase("ADD")) {
					// ADD MEMBER command.
					if (lines[1].equalsIgnoreCase("MEMBER")) {
							String name = "";
							for (int i = 2; i < lines.length; i++) {
								name += lines[i] + " ";
							}
							gg.addMember(name.trim());
					}
					// ADD BOOK command.
					else if (lines[1].equalsIgnoreCase("BOOK")) {
						List<String> list = new LinkedList<String>();
						for (int i = 2; i < lines.length-1; i++) {
							list.add(lines[i]);
						}
						String fullBookName = "";
						int listLength = list.size();
						for (int i = 0; i < listLength; i++) {
							fullBookName += list.get(i);
							if (i != listLength -1) {
								fullBookName += " ";
							}
						}
							gg.addBook(fullBookName,lines[lines.length-1]);
					}
					// ADD COLLECTION command.
					else if (lines[1].equalsIgnoreCase("COLLECTION")){
						List<String> list = new LinkedList<String>();
						
						for (int i = 2; i <lines.length; i++) {
							list.add(lines[i]);
						}
						String fullBookName ="";
						int listLength = list.size();
						for (int i = 0; i < listLength; i++) {
							fullBookName += list.get(i);
							if (i != listLength - 1) {
								fullBookName += " ";
							}
						}
							gg.addCollection(fullBookName);
					}
				}
				// SAVE COLLECTION command.
				else if (lines[0].equalsIgnoreCase("SAVE")) {
					if (lines[1].equalsIgnoreCase("COLLECTION")) {
						List<String> list = new LinkedList<String>();
						for(int i = 2; i < lines.length; i++) {
							list.add(lines[i]);
						}
						String full = "";
						int listLength = list.size();
						for (int i = 0; i < listLength; i++) {
							full += list.get(i);
							if (i != listLength -1) {
								full += " ";
							}
						}
							gg.saveCollection(full);
					}
				}
				// COMMON command.
				else if (lines[0].equalsIgnoreCase("COMMON")) {
					ArrayList<String> list = new ArrayList<String>();
					for(int i = 1; i <lines.length; i++) {
						list.add(lines[i]);
					}
					String[] list2 = new String[list.size()];
					int j = 0;
					for(String x : list){
						list2[j] = x;
						j++;
					}
					gg.common(list2);
				}
				// GENRE command.
				else if (lines[0].equalsIgnoreCase("GENRE")) {
					List<String> list = new LinkedList<String>();
					for (int i = 1; i <lines.length; i++) {
						list.add(lines[i]);
					}
					String full = "";
					int listLength = list.size();
					for (int i = 0; i < listLength; i++) {
						full += list.get(i);
						if (i != listLength - 1) {
							full += " ";
						}
					}
					gg.getBooksByGenre(full);
				}
				// AUTHOR command.
				else if (lines[0].equalsIgnoreCase("AUTHOR")){
					List<String> list = new LinkedList<String>();
					for (int i = 1; i < lines.length; i++) {
						list.add(lines[i]);
					}
					String full = "";
					int listLength = list.size();
					for (int i = 0; i < listLength; i++) {
						full += list.get(i);
						if (i != listLength - 1) {
							full += " ";
						}
					}
					gg.getBooksByAuthor(full);
				}
				// COMMANDS command.
				else if (lines[0].equalsIgnoreCase("COMMANDS")) {
					System.out.print(HELP_STRING + "\n");
				}
				// No command given.
				else {
					System.out.println("Ending Library process.");
					return;
				}
				System.out.println("");
				System.out.print("user: ");
				line = scan.nextLine();
			}
			System.out.println("Ending Library process.");
		}catch (NoSuchElementException e){
		}
	}
	// HELP STRING.
	public static final String HELP_STRING ="EXIT ends the library process\nCOMMANDS outputs this help string\n\nLIST ALL [LONG] outputs either the short or long string for all books\nLIST AVAILABLE [LONG] outputs either the short of long string for all available books\nNUMBER COPIES outputs the number of copies of each book\nLIST GENRES outputs the name of every genre in the system\nLIST AUTHORS outputs the name of every author in the system\n\nGENRE <genre> outputs the short string of every book with the specified genre\nAUTHOR <author> outputs the short string of every book by the specified author\n\nBOOK <serialNumber> [LONG] outputs either the short or long string for the specified book\nBOOK HISTORY <serialNumber> outputs the rental history of the specified book\n\nMEMBER <memberNumber> outputs the information of the specified member\nMEMBER BOOKS <memberNumber> outputs the books currently rented by the specified member\nMEMBER HISTORY <memberNumber> outputs the rental history of the specified member\n\nRENT <memberNumber> <serialNumber> loans out the specified book to the given member\nRELINQUISH <memberNumber> <serialNumber> returns the specified book from the member\nRELINQUISH ALL <memberNumber> returns all books rented by the specified member\n\nADD MEMBER <name> adds a member to the system\nADD BOOK <filename> <serialNumber> adds a book to the system\n\nADD COLLECTION <filename> adds a collection of books to the system\nSAVE COLLECTION <filename> saves the system to a csv file\n\nCOMMON <memberNumber1> <memberNumber2> ... outputs the common books in members\' history";
}
