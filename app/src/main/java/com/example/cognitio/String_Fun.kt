package com.example.cognitio

import java.text.Normalizer

private val ACCENT_REGEX = "\\p{Mn}+".toRegex()

fun String.normalizeWord(): String =
    Normalizer.normalize(this.trim().lowercase(), Normalizer.Form.NFD)
        .replace(ACCENT_REGEX, "")