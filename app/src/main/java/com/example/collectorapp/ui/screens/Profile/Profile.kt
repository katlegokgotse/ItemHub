import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.collectorapp.AddCategorySection
import com.example.collectorapp.CategorySection
import com.example.collectorapp.R
import com.example.collectorapp.ui.composables.Chips.CategoryChips
import com.example.collectorapp.ui.composables.HeadingText
import com.example.collectorapp.ui.screens.Authentication.AuthenticationViewModel
import com.example.collectorapp.ui.screens.Categories.AddCategoryViewModel


@Composable
fun Profile(a: AuthenticationViewModel, category: AddCategoryViewModel){
    Surface(
        modifier = Modifier.padding(30.dp)
    ) {
        LazyColumn {
            item {
                ProfileHeader(a)
            }
            item{
                MyItems(category)

            }
        }
    }
}

@Composable
fun ProfileHeader(a: AuthenticationViewModel){
    Row{
        ProfilePicture(painter = painterResource(id = R.drawable.cto))
        ProfileNameDetails(a)
        Notification()
    }
}

@Composable
fun ProfileNameDetails(a: AuthenticationViewModel) {
    Column {
        HeadingText(value = "${a._userReg.value.firstName} ${a._userReg.value.lastName}")
        Text(text = "Edit my profile")
    }
}

@Composable
fun MyItems(addCategoryViewModel: AddCategoryViewModel){
    Column {
        HeadingText(value = "My Items")
        LazyRow(){
                items(
                    addCategoryViewModel._categoryListState.value.categoryList.ifEmpty {
                        listOf(null)
                    }
                ) { category ->
                    if (category != null) {
                        CategoryChips(category = addCategoryViewModel._categoryListState.value.categoryList.toString(),
                            onExecuteSearch = { /**/ }
                            )
                    } else {
                        CategoryChips(category = "All",
                            onExecuteSearch = { /**/ }
                            )
                    }

                }
        }
    }
}

@Composable
fun ProfilePicture(painter: Painter){
    Box(modifier = Modifier
        .height(100.dp)
        .width(100.dp)
        .padding(0.dp)
    ){
        Image(painter = painter,
            contentDescription = "Me")
    }
}