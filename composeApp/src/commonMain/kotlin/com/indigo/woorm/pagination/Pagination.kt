package com.indigo.woorm.pagination

import kotlinx.serialization.Serializable

const val DEFAULT_PAGE_SIZE: Int = 20
const val DEFAULT_PAGE: Int = 0

@Serializable
data class Link(val href: String)

@Serializable
data class Links(
    val first: Link,
    val prev: Link? = null,
    val next: Link? = null,
    val last: Link,
)

@Serializable
data class Page(
    val size: Int,
    val page: Int,
    val totalElements: Int,
    val totalPages: Int,
)

@Serializable
data class PagedResponse<T>(
    val content: List<T>,
    val links: Links,
    val page: Page
)
