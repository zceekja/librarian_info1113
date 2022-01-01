- book1.csv , book2.csv and 'whitespace book.csv' is added to folder for testing

#Test Description

1.   commands - print help message +

2.   exit - exit +

3.   listAll1 - success (1 book)  
4.   listAll2 - success (17 books) 
5.   listAll3 - fail (no books in system) 
6.   listAllLong1 - success (1 book) 
7.   listAllLong2 - success  (17 books) 
8.   listAllLong3 - fail (no books in system) 

9.   listAvailable1 - list all available fail (no books in system) 
10.  listAvailable2 - list all available (no renter) 
11.  listAvailable3 - list all available (some renter) 
12.  listAvailable4 - list all available fail (no books available) 

13.  addBook1 - success 
14.  addBook2 - fail (no file) 
15.  addBook3 - fail (no book in file) 
16.  addBook4 - fail (Book already exists) 
17.  addBook5 - Test for csv file with whitespace 

18.  addCollection1 - success 
19.  addCollection2 - fail (no file) 
20.  addCollection3 - success (duplicate) 
21.  addCollection4 - fail (all book are duplicate) 
22.  addCollection5 - Test for csv file with whitespace 

23.  book1 - success 
24.  book2 - fail (no books in system.) 
25.  book3 - fail (no such book in system.) 
26.  bookLong1 - success 
27.  bookLong2 - fail (no books in system.) 
28.  bookLong3 - fail (no such books in system.) 

29.  addMember1 - add 1 member 
30.  addMember2 - check for member number correctness 
31.  addMember3 - member wih middle name 

32.  member1 - fail (no members in system) 
33.  member2 - fail (no such member in system) 
34.  member3 - success 

35.  memberBooks1 - fail (No members in system.) 
36.  memberBooks2 - fail (No such member in system.) 
37.  memberBooks3 - fail (Member is not currently renting) 
38.  memberBooks4 - Success (1 book) 
39.  memberBooks5 - Success (few books) 
40.  memberBooks6 - Success (1 renting , 1 returned) 

41.  rent1 - fail (No member in system) 
42.  rent2 - fail (No book in system) 
43.  rent3 - fail (No such member in system) 
44.  rent4 - fail (No such book in system) 
45.  rent5 - success 
46.  rent6 - fail (Book is currenly unavailable) 

47.  relinquishBook1 - fail (No members in system) 
48.  relinquishBook2 - fail (No books in system) 
49.  relinquishBook3 - fail (No such member in system) 
50.  relinquishBook4 - fail (No such book in system) 
51.  relinquishBook5 - success 
52.  relinquishBook6 - fail (Unable to return book) 

53.  relinquishAll1 - fail (No members in system.) 
54.  relinquishAll2 - fail (No such member in system) 
55.  relinquishAll3 - Success (No book to return) 
56.  relinquishAll4 - Success (return all books) 

57.  memberHistory1 - fail (No members in system) 
58.  memberHistory2 - fail (No such member in system) 
59.  memberHistory3 - fail (No rental history for member) 
60.  memberHistory4 - success (1 rented) 
61.  memberHistory5 - success (1 rented, 1 renting) 

62.  bookHistory1 - fail (No such book in system)
63.  bookHistory2 - fail (No rental history)
64.  bookHistory3 - success (3 member) 

65.  listGenres1 - fail (No books in system) 
66.  listGenres2 - Success (1 genre) 
67.  listGenres3 - Success (few genres) 
68.  listGenres4 - Success (few + duplicate) 

69.  listAuthors1 - fail (No books in system)
70.  listAuthors2 - Success (1 author) 
71.  listAuthors3 - Success (few authors) 
72.  listAuthors4 - Success (few + duplicate) 

73.  author1 - fail (No book in system) 
74.  author2 - fail (No books by author [author]) 
75.  author3 - success (book added by serial number) 
76.  author4 - success (book added randomly, not in order of serial number)

77.  genre1 - fail (No book in system) 
78.  genre2 - fail (No books by author genre [genre]) 
79.  genre3 - success (book added by serial number) 
80.  genre4 - success (book added randomly(not i norder of serial number)) 

81.  numberCopies1 - fail (No books in system) 
82.  numberCopies2 - success (no duplicate copy) 
83.  numberCopies3 - success (some book have copy) 

84.  common1 - fail (No members in system) 
85.  common2 - fail (No books in system)
86.  common3 - fail (No such member in system) 
87.  common4 - fail (Duplicate member provided) 
88.  common5 - fail (No common books 2 members) 
89.  coomon6 - fail (No common books 3 members) 
90.  common7 - success (compare 2 members) 
91.  common8 - success (compare 4 members) 
92.  common9 - success (compare 4 members + book added to history randomly(not in order of serial number))***

93.  saveCollection1 - fail (No book in system)
94.  saveCollection2 - Success

95.  integration1 - commands, add collection, add members, rent, list available, exit
96.  integration2 - add collection, add member, rent, rent fail, relinquish, rent, relinquish, common, exit
97.  integration3 - rent fail, add collection, add member, rent,  renlinquish, member books, member history, relinqish all, member book, member history, exit
98.  integration4 - add collections, number copies, author - exit
99.  integration5 - add collections, add members, rents, list genre, relinquish, common ,exit 
