package com.example.androidstudyjam.ui.home.extensionfunc

import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun <T> LifecycleOwner.collectOnLifeCycleScope(flow: Flow<T>, collect: suspend (T) -> Unit) {

    this.lifecycleScope.launchWhenStarted {

        repeatOnLifecycle(Lifecycle.State.STARTED) {

            flow.collectLatest(collect)

        }

    }
}


