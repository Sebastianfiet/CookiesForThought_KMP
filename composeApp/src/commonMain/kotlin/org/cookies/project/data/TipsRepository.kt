package org.cookies.project.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.cookies.project.model.Tip

class TipsRepository(
    initialTips: List<Tip>
) {
    private val _tips = MutableStateFlow(initialTips)
    val tips: StateFlow<List<Tip>> = _tips
}
