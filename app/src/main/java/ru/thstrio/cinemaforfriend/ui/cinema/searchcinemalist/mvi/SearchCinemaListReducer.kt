package ru.thstrio.cinemaforfriend.ui.cinema.searchcinemalist.mvi

class SearchCinemaListReducer :
        (SearchCinemaListState, SearchCinemaListAction) -> SearchCinemaListState {
    override fun invoke(
        state: SearchCinemaListState,
        action: SearchCinemaListAction
    ): SearchCinemaListState = when (action) {
        is SearchCinemaListAction.LoadInitPage -> state.copy( listCinema = state.listCinema.plus(action.list),currentPage =  action.page)
        is SearchCinemaListAction.LoadNextPage -> state.copy( listCinema = action.list,  currentPage =  action.page)
        is SearchCinemaListAction.EnterSearchText -> state.copy(searchText = action.text,currentPage=-1,listCinema = listOf(),currentPosition =0)
        SearchCinemaListAction.NoAction -> state
        is SearchCinemaListAction.SaveCurrentPosition ->state.copy(currentPosition = action.position)
    }
}