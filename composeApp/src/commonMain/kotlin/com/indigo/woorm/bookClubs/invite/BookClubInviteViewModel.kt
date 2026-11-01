package com.indigo.woorm.bookClubs.invite

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.indigo.woorm.bookClubs.detail.InviteDetail
import com.indigo.woorm.bookClubs.service.BookClubService
import com.indigo.woorm.navigation.Route
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class BookClubInviteViewModel(
    private val bookClubService: BookClubService,
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    private val id = savedStateHandle.toRoute<Route.BookClub.Invite>().id

    private var _bookClubInvite = MutableStateFlow<InviteDetail?>(null)
    val bookClubInvite = _bookClubInvite.asStateFlow()

    private val _navigationEvent = Channel<Unit>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    init {
        bookClubInvite()
    }

    private fun bookClubInvite() {
        viewModelScope.launch {
            bookClubService.invite(id).onSuccess {
                _bookClubInvite.emit(it)
            }
        }
    }

    fun join(invite: InviteDetail) {
        viewModelScope.launch {
            bookClubService.join(invite.id).onSuccess {
                _navigationEvent.send(Unit)
            }
        }
    }

}