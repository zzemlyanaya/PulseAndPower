package ru.zzemlyanaya.pulsepower.app.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.*
import ru.zzemlyanaya.pulsepower.app.navigation.MainDirections.back
import ru.zzemlyanaya.pulsepower.app.navigation.MainDirections.default

class NavigationRouter {

    private val _commands = MutableLiveData(default)
    val commands: LiveData<NavigationCommand> = _commands

    private val resultListeners: HashMap<Int, ResultListener> = hashMapOf()

    fun <T> getCurrentArgs() = _commands.value?.args?.firstOrNull() as? T

    fun navigateTo(directions: NavigationCommand) {
        _commands.postValue(directions)
    }

    fun back() {
        _commands.postValue(back)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> addResultListener(id: Int, listener: (T) -> Unit) {
        resultListeners[id] = object : ResultListener {
            override fun onResult(result: Any) {
                (result as? T)?.let { listener(it) }
            }
        }
    }

    fun removeResultListener(id: Int) {
        resultListeners.remove(id)
    }

    fun sendResult(id: Int, result: Any) {
        resultListeners[id]?.onResult(result)
    }

    fun sendResultAll(result: Any) {
        resultListeners.values.forEach { it.onResult(result) }
    }
}


interface ResultListener {
    fun onResult(result: Any)
}