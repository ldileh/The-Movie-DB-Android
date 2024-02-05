package com.themoviedb.test.model.ui

sealed class MovieDetailModel{

    data class Default(val text: String): MovieDetailModel()
    data class Title(val text: String): MovieDetailModel()
    data class SubTitle(val text: String): MovieDetailModel()
    data class Field(val label: String, val value: String): MovieDetailModel()
}
