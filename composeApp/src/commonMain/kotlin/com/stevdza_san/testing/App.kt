import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.stevdza_san.testing.navigation.SetupNavGraph
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

val lightRedColor = Color(color = 0xFFF57D88)
val darkRedColor = Color(color = 0xFF77000B)

@Composable
@Preview
fun App() {
    KoinContext {
        val lightColors = lightColorScheme(
            primary = lightRedColor,
            onPrimary = darkRedColor,
            primaryContainer = lightRedColor,
            onPrimaryContainer = darkRedColor
        )
        val darkColors = darkColorScheme(
            primary = lightRedColor,
            onPrimary = darkRedColor,
            primaryContainer = lightRedColor,
            onPrimaryContainer = darkRedColor
        )
        val colors by mutableStateOf(
            if (isSystemInDarkTheme()) darkColors else lightColors
        )

        MaterialTheme(colorScheme = colors) {
            val navController = rememberNavController()
            SetupNavGraph(navController = navController)
        }
    }
}