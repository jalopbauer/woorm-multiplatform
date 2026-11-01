package com.indigo.woorm.common.book.cover

import com.indigo.woorm.books.BookPreview
import com.indigo.woorm.common.book.Author

val bookPreviewComplete = BookPreview(
    id = 1,
    coverUrl = "https://m.media-amazon.com/images/S/compressed.photo.goodreads.com/books/1482108618i/33413128.jpg",
    title = "Beartown",
    authors = listOf(Author(1, "Fredrik Backman")),
)
val bookPreviewNoUrl = BookPreview(
    id = 13,
    coverUrl = "",
    title = "Anxious People",
    authors = listOf(Author(1, "Fredrik Backman")),
)

val books = listOf(
    bookPreviewComplete,
    bookPreviewNoUrl,
    BookPreview(
        id = 2,
        coverUrl = "https://m.media-amazon.com/images/S/compressed.photo.goodreads.com/books/1672574587i/60531406.jpg",
        title = "Tress of the Emerald Sea",
        authors = listOf(Author(2, "Brandon Sanderson"), Author(3, "Howard Lyon")),
    ),
    BookPreview(
        id = 3,
        coverUrl = "https://m.media-amazon.com/images/S/compressed.photo.goodreads.com/books/1421618636i/30659.jpg",
        title = "Meditations",
        authors = listOf(Author(4, "Marcus Aurelius"))
    ),
    BookPreview(
        id = 4,
        coverUrl = "https://m.media-amazon.com/images/S/compressed.photo.goodreads.com/books/1349590163i/13356706.jpg",
        title = "The Fellowship of the Ring",
        authors = listOf(Author(5, "J.R.R. Tolkien"))
    ),
    BookPreview(
        id = 5,
        coverUrl = "https://m.media-amazon.com/images/S/compressed.photo.goodreads.com/books/1681797837i/62192261.jpg",
        title = "The Door-to-Door Bookstore",
        authors = listOf(Author(6, "Carsten Henn"))
    ),
    BookPreview(
        id = 6,
        coverUrl = "https://m.media-amazon.com/images/S/compressed.photo.goodreads.com/books/1580819415l/51947057.jpg",
        title = "Wild Seed",
        authors = listOf(Author(7, "Octavia E. Butler"))
    ),
    BookPreview(
        id = 7,
        coverUrl = "https://m.media-amazon.com/images/S/compressed.photo.goodreads.com/books/1429638085i/4929.jpg",
        title = "Kafka on the Shore",
        authors = listOf(Author(8, "Haruki Murakami"))
    ),
    BookPreview(
        id = 8,
        coverUrl = "https://m.media-amazon.com/images/S/compressed.photo.goodreads.com/books/1472074835i/31373633.jpg",
        title = "And Every Morning the Way Home Gets Longer and Longer",
        authors = listOf(Author(1, "Fredrik Backman"))
    ),
    BookPreview(
        id = 9,
        coverUrl = "https://m.media-amazon.com/images/S/compressed.photo.goodreads.com/books/1516019348i/36373463.jpg",
        title = "Us Against You",
        authors = listOf(Author(1, "Fredrik Backman"))
    ),
    BookPreview(
        id = 10,
        coverUrl = "https://m.media-amazon.com/images/S/compressed.photo.goodreads.com/books/1645483248i/60318890.jpg",
        title = "The Winners",
        authors = listOf(Author(1, "Fredrik Backman"))
    ),
    BookPreview(
        id = 11,
        coverUrl = "https://m.media-amazon.com/images/S/compressed.photo.goodreads.com/books/1664458703i/32620332.jpg",
        title = "The Seven Husbands of Evelyn Hugo",
        authors = listOf(Author(9, "Taylor Jenkins Reid"))
    ),
    BookPreview(
        id = 12,
        coverUrl = "https://m.media-amazon.com/images/S/compressed.photo.goodreads.com/books/1617768316i/68428.jpg",
        title = "Mistborn: The Final Empire",
        authors = listOf(Author(2, "Brandon Sanderson"))
    ),
)
