

package com.android.example.github.data.repository

import androidx.lifecycle.LiveData
import com.android.example.github.AppExecutors
import com.android.example.github.api.GithubService
import com.android.example.github.data.db.UserDao
import com.android.example.github.vo.Resource
import com.android.example.github.vo.User
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val userDao: UserDao,
    private val githubService: GithubService
) {

    fun loadUser(login: String): LiveData<Resource<User>> {
        return object : NetworkBoundResource<User, User>(appExecutors) {
            override fun saveCallResult(item: User) {
                userDao.insert(item)
            }

            override fun shouldFetch(data: User?) = data == null

            override fun loadFromDb() = userDao.findByLogin(login)

            override fun createCall() = githubService.getUser(login)
        }.asLiveData()
    }
}
