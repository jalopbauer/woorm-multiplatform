package com.indigo.woorm.common.search

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/** Shared trailing-debounce window applied to search query text on every searching screen. */
const val SEARCH_DEBOUNCE_MILLIS: Long = 300

/**
 * Shared search trigger pipeline owned by the ViewModel of every searching screen.
 * Query text changes arrive through [onQueryChange] and run after a shared trailing
 * debounce; submissions arrive through [submit] and run immediately, bypassing the
 * debounce. Both streams merge into a single `collectLatest`, so a new query or
 * submission cancels any [onQuery] execution still in flight.
 */
@OptIn(kotlinx.coroutines.FlowPreview::class)
class SearchQueryPipeline(
    scope: CoroutineScope,
    private val onQuery: suspend (String) -> Unit,
) {
    private val queryFlow = MutableStateFlow("")
    private val submitChannel = Channel<String>(capacity = Channel.UNLIMITED)

    private val collectJob: Job = scope.launch {
        merge(
            queryFlow
                .drop(1)
                .debounce(SEARCH_DEBOUNCE_MILLIS)
                .distinctUntilChanged(),
            submitChannel.receiveAsFlow(),
        ).collectLatest { onQuery(it) }
    }

    fun onQueryChange(query: String) {
        queryFlow.value = query
    }

    fun submit(query: String) {
        submitChannel.trySend(query)
    }

    fun cancel() {
        collectJob.cancel()
        submitChannel.close()
    }
}
