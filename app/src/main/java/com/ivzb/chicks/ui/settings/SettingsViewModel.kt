package com.ivzb.chicks.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ivzb.chicks.domain.Event
import com.ivzb.chicks.model.Theme
import com.ivzb.chicks.util.map
import com.ivzb.chicks.domain.Result
import com.ivzb.chicks.domain.Result.Success
import com.ivzb.chicks.domain.settings.GetNSFWUseCase
import com.ivzb.chicks.domain.settings.SaveNSFWUseCase
import com.ivzb.chicks.domain.settings.GetAvailableThemesUseCase
import com.ivzb.chicks.domain.settings.GetThemeUseCase
import com.ivzb.chicks.domain.settings.SetThemeUseCase
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    val setThemeUseCase: SetThemeUseCase,
    getNSFWUseCase: GetNSFWUseCase,
    val saveNSFWUseCase: SaveNSFWUseCase,
    getThemeUseCase: GetThemeUseCase,
    getAvailableThemesUseCase: GetAvailableThemesUseCase
) : ViewModel() {

    // Theme setting
    private val themeResult = MutableLiveData<Result<Theme>>()
    val theme: LiveData<Theme>

    // Theme setting
    private val availableThemesResult = MutableLiveData<Result<List<Theme>>>()
    val availableThemes: LiveData<List<Theme>>

    private val _navigateToThemeSelector = MutableLiveData<Event<Unit>>()
    val navigateToThemeSelector: LiveData<Event<Unit>>
        get() = _navigateToThemeSelector

    // NSFW setting
    private val enableNSFWResult = MutableLiveData<Result<Boolean>>()
    val enableNSFW: LiveData<Boolean>

    init {
        getNSFWUseCase(Unit, enableNSFWResult)
        enableNSFW = enableNSFWResult.map {
            (it as? Success<Boolean>)?.data ?: false
        }

        getThemeUseCase(Unit, themeResult)
        theme = themeResult.map {
            (it as? Success<Theme>)?.data ?: Theme.SYSTEM
        }

        getAvailableThemesUseCase(Unit, availableThemesResult)
        availableThemes = availableThemesResult.map {
            (it as? Success<List<Theme>>)?.data ?: emptyList()
        }
    }

    fun toggleEnableNSFW(checked: Boolean) {
        saveNSFWUseCase(checked, enableNSFWResult)
    }

    fun setTheme(theme: Theme) {
        setThemeUseCase(theme)
    }

    fun onThemeSettingClicked() {
        _navigateToThemeSelector.value = Event(Unit)
    }
}
