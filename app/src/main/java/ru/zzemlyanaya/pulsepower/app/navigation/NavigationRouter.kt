package ru.zzemlyanaya.pulsepower.app.navigation

import io.reactivex.disposables.Disposable
import kotlinx.coroutines.flow.MutableStateFlow
import ru.zzemlyanaya.pulsepower.app.navigation.MainDirections.back
import ru.zzemlyanaya.pulsepower.app.navigation.MainDirections.default

class NavigationRouter {

    var commands = MutableStateFlow(default)
    private val resultListeners: HashMap<Int, ResultListener> = hashMapOf()

    fun <T> getCurrentArgs() = commands.value.args.first() as T

    fun navigateTo(directions: NavigationCommand) {
        commands.value = directions
    }

    fun back() {
        commands.value = back
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
}


interface ResultListener {
    fun onResult(result: Any)
}