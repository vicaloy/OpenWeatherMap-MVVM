package com.android.example.github.presentation.weather

import androidx.lifecycle.ViewModel
import com.android.example.github.api.RequestCompleteListener
import com.android.example.github.data.repository.WeatherRepository
import com.android.example.github.vo.CurrentWeather
import javax.inject.Inject

class WeatherViewModel @Inject constructor(val repository: WeatherRepository) : ViewModel() {

    fun loadWeather(cityId: Int) {

        repository.loadWeather(cityId, object :
            RequestCompleteListener<CurrentWeather> {
            override fun onRequestSuccess(data: CurrentWeather) {

                //live data con data: currentWeather

            }

            override fun onRequestFailed(errorMessage: String) {
                //callback.onRequestFailed(errorMessage)
                //livedata
            }
        })


    }

/*private val _repoId: MutableLiveData<RepoId> = MutableLiveData()
    val repoId: LiveData<RepoId>
        get() = _repoId
    val repo: LiveData<Resource<Repo>> = _repoId.switchMap { input ->
        input.ifExists { owner, name ->
            repository.loadRepo(owner, name)
        }
    }
    val contributors: LiveData<Resource<List<Contributor>>> = _repoId.switchMap { input ->
        input.ifExists { owner, name ->
            repository.loadContributors(owner, name)
        }
    }

    fun retry() {
        val owner = _repoId.value?.owner
        val name = _repoId.value?.name
        if (owner != null && name != null) {
            _repoId.value = RepoId(owner, name)
        }
    }

    fun setId(owner: String, name: String) {
        val update = RepoId(owner, name)
        if (_repoId.value == update) {
            return
        }
        _repoId.value = update
    }

    data class RepoId(val owner: String, val name: String) {
        fun <T> ifExists(f: (String, String) -> LiveData<T>): LiveData<T> {
            return if (owner.isBlank() || name.isBlank()) {
                AbsentLiveData.create()
            } else {
                f(owner, name)
            }
        }
    }*/
}
