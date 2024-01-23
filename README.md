**LMS: Library Management Service**

This spring webflux service is created for library management. This isa sample project for learning purpose.

Key Points:
1. This is developed in Spring webflux.
2. Spring data JPA is used as ORM tool and MySql database is used.
3. For Authentication we are using Spring security with JWT tokens.
4. For mapping, Map struct library is used.

Endpoints:
1. /login: For login purpose.
2. /register: Registering user.
3. /issue-books: User can issue book(s).
4. /submit-books: User can submit already issued book(s).
5. /book-by-id?id={bookId}: To get book details.
6. /user-books?emailId={userEmailId}: To get details of all books issued by user.
7. /author-books?id={authorId}: Get all books written by any author.
8. /books: To get list of all books in library.
9. /add-book: Adding a book in library.
