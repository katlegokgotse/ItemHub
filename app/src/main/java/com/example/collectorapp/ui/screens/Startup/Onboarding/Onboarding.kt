package com.example.collectorapp.ui.screens.Startup.Onboarding

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.collectorapp.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun OnBoarding(navController: NavController) {
    val items = OnboardingItems.getData()
    val scope = rememberCoroutineScope()
    val pageState = rememberPagerState()

    Column(modifier = Modifier.fillMaxSize()) {
        TopSection(
            onBackClick = {
                if (pageState.currentPage + 1 > 1) scope.launch {
                    pageState.scrollToPage(pageState.currentPage - 1)
                }
            },
            onSkipClick = {
                if (pageState.currentPage + 1 <= items.size) scope.launch {
                    //pageState.scrollToPage(items.size - 1)
                    navController.navigate("call_to_action")
                }
            }
        )

        HorizontalPager(
            count = items.size,
            state = pageState,
            modifier = Modifier
                .fillMaxHeight(0.9f)
                .fillMaxWidth()
        ) { page ->
            OnBoardingItem(items = items[page])
        }
        BottomSection(size = items.size, index = pageState.currentPage) {
            if (pageState.currentPage + 1 < items.size) scope.launch {
                pageState.scrollToPage(pageState.currentPage + 1)
            }
            if (pageState.currentPage == items.size){
                scope.launch {
                    navController.navigate("registration_interface")
                }
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
fun TopSection(onBackClick: () -> Unit = {}, onSkipClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .background(color = Color.Transparent),
    ) {
        // Back button
        IconButton(onClick = onBackClick, modifier = Modifier.align(Alignment.CenterStart)) {
            Icon(imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowLeft, contentDescription = null)
        }

        // Skip Button
        TextButton(
            onClick = onSkipClick,
            modifier = Modifier.align(Alignment.CenterEnd),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(text = "Skip", color = MaterialTheme.colorScheme.onBackground)
        }
    }
}

@Composable
fun BottomSection(size: Int, index: Int, onButtonClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(color = Color.Transparent)

    ) {
        // Indicators
        Indicators(size, index)

        FloatingActionButton(
            onClick = { /* do something */ },
            containerColor = Color.Black,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clip(RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
        ) {
            Icon(
                Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                tint = Color.White,
                contentDescription = "Localized description")
        }
    }
}

@Composable
fun BoxScope.Indicators(size: Int, index: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.align(Alignment.CenterStart)
    ) {
        repeat(size) {
            Indicator(isSelected = it == index)
        }
    }
}

@Composable
fun Indicator(isSelected: Boolean) {
    val width = animateDpAsState(
        targetValue = if (isSelected) 25.dp else 10.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )
    Box(
        modifier = Modifier
            .height(10.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color(0XFFF8E2E7)
            )
    ) {

    }
}

@Composable
fun OnBoardingItem(items: OnboardingItems) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = items.image),
            contentDescription = "Image1",
            contentScale = ContentScale.None
        )
        Column {
            Spacer(modifier = Modifier.height(190.dp))
            Text(
                modifier = Modifier.fillMaxWidth().padding(1.dp),
                text = items.title,
                style = MaterialTheme.typography.headlineMedium,
                fontSize = 80.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                letterSpacing = 5.sp,
                lineHeight = 80.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = items.desc,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(10.dp),
                letterSpacing = 1.sp,
            )
        }


    }
}


class OnboardingItems(
    val image: Int,
    val title: String,
    val desc: String
){
    companion object{
        fun getData(): List<OnboardingItems>{
            return listOf(
                OnboardingItems(
                    image = R.drawable.cto,
                    title = "Capture Your Living",
                    desc = ""
                ), OnboardingItems(
                    image = R.drawable.ob1,
                    title = "Store your home on mobile",
                    desc = ""
                ), OnboardingItems(
                    image = R.drawable.ob2,
                    title = "Manage your home online",
                    desc = ""
                ),
            )
        }
    }
}