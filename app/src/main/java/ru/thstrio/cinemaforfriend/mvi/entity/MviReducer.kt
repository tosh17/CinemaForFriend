package ru.thstrio.cinemaforfriend.mvi.entity

abstract class MviReducer<S : MviState, E : MviEffect> : (S, E)->S{
}