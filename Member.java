import java.util.*;

class Member {
	private String name;
	private String memberNumber;
	private LinkedList<Book> booksRenting = new LinkedList<Book>();
	private LinkedList<Book> booksRented = new LinkedList<Book>();

	public Member(String name, String memberNumber) {
		this.name = name;
		this.memberNumber = memberNumber;
	}

	public static List<Book> commonBooks(Member[] members) {
		// Check for valid parameter
		if (members == null) {
			return null;
		}
		// Check for valid parameter
		if (members.length == 0) {
			return null;
		}
		// Check for valid parameter
		for (Member x : members){
			if (x == null) {
				return null;
			}
			if (x.history()== null) {
				return null;
			}
		}
			// Create new list to collect commons book
			List<Book> books = new ArrayList<Book>(members[0].history());
			// Create temporally list to compare book
			List<Book> booksTmp = new ArrayList<Book>();
			// Compare value between list, keep only duplicate value.
			for (Member x: members){
				booksTmp = x.history();
				books.retainAll(booksTmp);
			}
			Book temp = new Book(null,null,null,null);
			// Create new list to Sort
			List<Book> sortBooks = new ArrayList<Book>();
			// use selection sort.
			int arrSize = books.size();
			int lowest = 999999;
			int previous = 999998;
			while (arrSize > 0) {
				for (Book x : books) {
					if(Integer.parseInt(x.getSerialNumber()) < lowest) {
						lowest = Integer.parseInt(x.getSerialNumber());
						temp = x;
					}
				}
				if (previous == lowest) {
					books.remove(temp);
					previous = lowest;
					lowest = 999999;
					arrSize--;
				}
				else {
					books.remove(temp);
					sortBooks.add(temp);
					previous = lowest;	
					lowest = 999999;
					arrSize--;
				}
			}
			// Return Sorted list
			return sortBooks;
	}

	public String getMemberNumber() {
		return memberNumber;	
	}

	public String getName() {
		return name;
	}

	public List<Book> history() {
		return booksRented;
		
	}

	public boolean relinquish(Book book) {
		// Check for valid parameter.
		if (book == null ) {
			return false;
		}
		// Check for valid parameter.
		if (book.getSerialNumber() == null) {
			return false;
		}
		// Check for valid parameter.
		if (book.getRenter() == null) {
			return false;
		}
		// Check for valid parameter.
		if (book.getSerialNumber() == null) {
			return false;
		}
		// Check whether book rented to someone.
		if (!book.getRenter().equals(this)) {
			return false;
		}
		// Relinquish book, and change status.
		else {
			book.relinquish(this);
			booksRented.add(book);
			booksRenting.remove(book);
			return true;
		}
	}

	public void relinquishAll() {
		// Check for valid parameter.
		if (booksRenting == null || booksRenting.size() == 0) {
			return;
		}
		// Relinquish book, and change status.
		for (Book x: booksRenting) {
			x.relinquish(this);
			booksRented.add(x);
		}
		booksRenting.clear();
	}

	public boolean rent(Book book) {
		// Check for valid parameter.
		if (book == null) {
			return false;
		}
		// Check whether book is rented to someone.
		if (book.isRented()) {
			return false;
		}
		// Rent book to member, and change status
		else {
			book.rent(this);
			booksRenting.add(book);
			return true;
		}
	}
	
	public List<Book> renting() {
		return booksRenting;
	}
}
