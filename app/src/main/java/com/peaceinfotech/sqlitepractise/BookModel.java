package com.peaceinfotech.sqlitepractise;

/**
 * Created By BASIL AYYUBI on 26-11-2022.
 */
public class BookModel {
    String  bookTitle , authorName , content;
    Integer id,  pages;

    public BookModel(String bookTitle, String authorName, Integer pages , String content) {
        this.bookTitle = bookTitle;
        this.authorName = authorName;
        this.pages = pages;
        this.content = content;
    }

    public BookModel(Integer id, String bookTitle, String authorName, Integer pages , String content) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.authorName = authorName;
        this.pages = pages;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }
}
