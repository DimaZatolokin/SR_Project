package com.srproject.domain.mappers

interface PresentationMapper<INPUT_MODEL, PRESENTATION_MODEL> {

    fun toPresentation(model: INPUT_MODEL): PRESENTATION_MODEL
}