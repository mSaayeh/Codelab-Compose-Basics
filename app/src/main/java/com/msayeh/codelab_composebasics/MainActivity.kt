package com.msayeh.codelab_composebasics

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.msayeh.codelab_composebasics.ui.theme.CodelabComposeBasicsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CodelabComposeBasicsTheme {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun MyApp(
    modifier: Modifier = Modifier,
) {
    var isOnboardingShown by rememberSaveable {
        mutableStateOf(true)
    }

    Surface(modifier = modifier) {
        if (isOnboardingShown) {
            OnboardingScreen {
                isOnboardingShown = false
            }
        } else {
            Greetings()
        }
    }
}

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onContinueClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Welcome to compose basics codelab application.", textAlign = TextAlign.Center)
        Button(
            onClick = onContinueClicked,
            modifier = Modifier.padding(24.dp)
        ) {
            Text(text = "Continue")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    CodelabComposeBasicsTheme {
        OnboardingScreen {}
    }
}

@Composable
fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(1000) { "$it" }
) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(names) { name ->
            Greeting(name = name)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingsPreview() {
    CodelabComposeBasicsTheme {
        Greetings(Modifier.fillMaxSize())
    }
}

@Composable
private fun Greeting(name: String) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        var expanded by rememberSaveable { mutableStateOf(false) }
        val color by animateColorAsState(
            targetValue = if (expanded)
                MaterialTheme.colorScheme.secondary
            else MaterialTheme.colorScheme.primary,
            animationSpec = tween(durationMillis = 600),
            label = "Expanded card color animation"
        )
        val iconRotation by animateFloatAsState(
            targetValue = if (expanded) 0f else 180f,
            label = "Arrow Rotation Animation",
            animationSpec = tween(durationMillis = 600)
        )

        val textColor by animateColorAsState(
            targetValue = if (expanded)
                MaterialTheme.colorScheme.onSecondary
            else MaterialTheme.colorScheme.onPrimary,
            animationSpec = tween(durationMillis = 600),
            label = "Expanded card color animation"
        )

        Row(
            modifier = Modifier
                .background(color)
                .padding(24.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                ),
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 12.dp)
            ) {
                Text(text = "Hello, ", color = textColor)
                Text(
                    text = name, style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = textColor
                    )
                )
                if (expanded) {
                    Text(
                        text = ("Composem ipsum color sit lazy, " +
                                "padding theme elit, sed do bouncy. ").repeat(4),
                        color = textColor
                    )
                }
            }
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowUp,
                    contentDescription = if (expanded) stringResource(
                        R.string.show_less
                    ) else stringResource(
                        R.string.show_more
                    ),
                    tint = textColor,
                    modifier = Modifier.rotate(iconRotation)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CodelabComposeBasicsTheme {
        MyApp(modifier = Modifier.fillMaxSize())
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Composable
fun DefaultDarkPreview() {
    CodelabComposeBasicsTheme {
        MyApp(modifier = Modifier.fillMaxSize())
    }
}
