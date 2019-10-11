package com.srproject.presentation.common

interface PresentationMapper<INPUT_MODEL, PRESENTATION_MODEL> {

    fun toPresentation(model: INPUT_MODEL): PRESENTATION_MODEL
}