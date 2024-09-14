import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.runComposeUiTest
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stevdza_san.testing.presentation.screen.home.HomeScreen
import data.FakeMongoDB
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import com.stevdza_san.testing.presentation.screen.home.HomeViewModel
import com.stevdza_san.testing.util.RequestState
import com.stevdza_san.testing.util.TestTag.ACTIVE_ERROR_TEXT
import com.stevdza_san.testing.util.TestTag.ACTIVE_LOADING_INDICATOR
import com.stevdza_san.testing.util.TestTag.COMPLETED_ERROR_TEXT
import com.stevdza_san.testing.util.TestTag.COMPLETED_LOADING_INDICATOR
import com.stevdza_san.testing.util.TestTag.HOME_SCREEN
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class HomeScreenTest {

    private var mongoDB = FakeMongoDB()

    @BeforeTest
    fun setUp() {
        mongoDB = FakeMongoDB()
    }

    private fun runWithContent(
        configure: (@Composable () -> Unit)? = null,
        block: ComposeUiTest.() -> Unit
    ) =
        runComposeUiTest {
            setContent {
                val viewModel = viewModel { HomeViewModel(mongoDB) }
                val activeTasks by viewModel.activeTasks
                val completedTasks by viewModel.completedTasks

                configure?.invoke()

                HomeScreen(
                    activeTasks = activeTasks,
                    completedTasks = completedTasks,
                    setAction = {},
                    navigateToTask = {}
                )
            }

            block()
        }

    @Test
    fun launchTheApp_assertHomeScreenIsDisplayed() = runComposeUiTest {
        setContent {
            HomeScreen(
                activeTasks = RequestState.Idle,
                completedTasks = RequestState.Idle,
                setAction = {},
                navigateToTask = {}
            )
        }

        onNodeWithTag(HOME_SCREEN).assertExists()
    }

    @Test
    fun activeTasksEmpty_assertEmptyMessageState() =
        runWithContent(
            configure = {
                addIdleOnCompletedTasks()
            }
        ) {
            runBlocking {
                delay(1000)
                onNodeWithTag(ACTIVE_ERROR_TEXT).assertTextEquals("Empty")
                delay(2000)
            }
        }

    @Test
    fun completedTasksEmpty_assertEmptyMessageState() =
        runWithContent(
            configure = {
                addIdleOnActiveTasks()
            }
        ) {
            runBlocking {
                delay(1000)
                onNodeWithTag(COMPLETED_ERROR_TEXT).assertTextEquals("Empty")
                delay(2000)
            }
        }

    @Test
    fun bothActiveAndCompletedTasksEmpty_assertEmptyMessageStateForBoth() =
        runWithContent {
            runBlocking {
                delay(1000)
                onNodeWithTag(ACTIVE_ERROR_TEXT).assertTextEquals("Empty")
                onNodeWithTag(COMPLETED_ERROR_TEXT).assertTextEquals("Empty")
                delay(2000)
            }
        }

    @Test
    fun activeTasksAreLoading_assertLoadingIndicatorVisible() =
        runWithContent(
            configure = { addLoadingOnActiveTasks() }
        ) {
            runBlocking {
                delay(1000)
                onNodeWithTag(ACTIVE_LOADING_INDICATOR).assertExists()
                delay(2000)
            }
        }

    @Test
    fun completedTasksAreLoading_assertLoadingIndicatorVisible() =
        runWithContent(
            configure = { addLoadingOnCompletedTasks() }
        ) {
            runBlocking {
                delay(1000)
                onNodeWithTag(COMPLETED_LOADING_INDICATOR).assertExists()
                delay(2000)
            }
        }

    @Test
    fun bothActiveAndCompletedTasksAreLoading_assertBothLoadingIndicatorsVisible() =
        runWithContent(
            configure = { addLoadingOnAllTasks() }
        ) {
            runBlocking {
                delay(1000)
                onNodeWithTag(ACTIVE_LOADING_INDICATOR).assertExists()
                onNodeWithTag(COMPLETED_LOADING_INDICATOR).assertExists()
                delay(2000)
            }
        }

    @Test
    fun errorAppearsForActiveTasks_assertCorrectErrorMessageDisplayed() =
        runWithContent(
            configure = { addErrorOnActiveTasks() }
        ) {
            runBlocking {
                delay(1000)
                onNodeWithTag(ACTIVE_ERROR_TEXT).assertTextEquals("Custom Test Error.")
                delay(2000)
            }
        }

    @Test
    fun errorAppearsForCompletedTasks_assertCorrectErrorMessageDisplayed() =
        runWithContent(
            configure = { addErrorOnCompletedTasks() }
        ) {
            runBlocking {
                delay(1000)
                onNodeWithTag(COMPLETED_ERROR_TEXT).assertTextEquals("Custom Test Error.")
                delay(2000)
            }
        }

    @Test
    fun errorAppearsForBothActiveAndCompletedTasks_assertCorrectErrorMessageDisplayed() =
        runWithContent(
            configure = { addErrorOnAllTasks() }
        ) {
            runBlocking {
                delay(1000)
                onNodeWithTag(ACTIVE_ERROR_TEXT).assertTextEquals("Custom Test Error.")
                onNodeWithTag(COMPLETED_ERROR_TEXT).assertTextEquals("Custom Test Error.")
                delay(2000)
            }
        }


    @Composable
    fun addIdleOnCompletedTasks() {
        LaunchedEffect(Unit) {
            mongoDB.addIdleOnCompletedTasks()
        }
    }

    @Composable
    fun addIdleOnActiveTasks() {
        LaunchedEffect(Unit) {
            mongoDB.addIdleOnActiveTasks()
        }
    }

    @Composable
    fun addLoadingOnActiveTasks() {
        LaunchedEffect(Unit) {
            mongoDB.addLoadingOnActiveTasks()
        }
    }

    @Composable
    fun addLoadingOnCompletedTasks() {
        LaunchedEffect(Unit) {
            mongoDB.addLoadingOnCompletedTasks()
        }
    }

    @Composable
    fun addLoadingOnAllTasks() {
        LaunchedEffect(Unit) {
            mongoDB.addLoadingOnAllTasks()
        }
    }

    @Composable
    fun addErrorOnActiveTasks() {
        LaunchedEffect(Unit) {
            mongoDB.addErrorOnActiveTasks(RequestState.Error(message = "Custom Test Error."))
        }
    }

    @Composable
    fun addErrorOnCompletedTasks() {
        LaunchedEffect(Unit) {
            mongoDB.addErrorOnCompletedTasks(RequestState.Error(message = "Custom Test Error."))
        }
    }

    @Composable
    fun addErrorOnAllTasks() {
        LaunchedEffect(Unit) {
            mongoDB.addErrorOnAllTask(RequestState.Error(message = "Custom Test Error."))
        }
    }
}