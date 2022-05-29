package com.picpay.desafio.android.presentation

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import com.picpay.desafio.android.R
import com.picpay.desafio.android.data.repositories.FakeUserRepository
import com.picpay.desafio.android.di.RepositoryModule
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.model.UserListState
import com.picpay.desafio.android.presentation.view.MainActivity
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(RepositoryModule::class)
class MainActivityTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    private val user = listOf(
        User(
            img = "https://randomuser.me/api/portraits/men/9.jpg",
            name = "Eduardo Santos",
            id = 1001,
            username = "@eduardo.santos"
        ),
        User(
            img = "https://randomuser.me/api/portraits/men/8.jpg",
            name = "Joao Guilherme",
            id = 1002,
            username = "@joao.guilherme"
        )
    )

    @BindValue @JvmField
    val repository = FakeUserRepository()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun shouldDisplayTitle() {
        launchActivity<MainActivity>().apply {
            val expectedTitle = context.getString(R.string.title)

            moveToState(Lifecycle.State.RESUMED)

            onView(withText(expectedTitle)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun shouldDisplayListItem(): Unit = runBlocking{
        repository.user = UserListState(user)

        launchActivity<MainActivity>().apply {
            onView(withId(R.id.recyclerView))
                .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

            RecyclerViewMatchers.checkRecyclerViewItem(
                R.id.recyclerView,
                0,
                withText("Eduardo Santos")
            )
            RecyclerViewMatchers.checkRecyclerViewItem(
                R.id.recyclerView,
                1,
                withText("Joao Guilherme")
            )
        }
    }

    @Test
    fun shouldHideRecycleView_whenListIsEmpty(): Unit = runBlocking{
        repository.user = UserListState(emptyList(), false, Exception())

        launchActivity<MainActivity>().apply {
            onView(withId(R.id.recyclerView))
                .check(matches(withEffectiveVisibility(Visibility.GONE)))

            onView(withId(R.id.progress_bar))
                .check(matches(withEffectiveVisibility(Visibility.GONE)))
        }
    }
}