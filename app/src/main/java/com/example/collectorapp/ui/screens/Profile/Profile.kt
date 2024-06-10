import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.collectorapp.R
import com.example.collectorapp.ui.composables.Chips.CategoryChips
import com.example.collectorapp.ui.composables.HeadingText
import com.example.collectorapp.ui.screens.Authentication.AuthenticationViewModel
import com.example.collectorapp.ui.screens.Categories.AddCategoryViewModel
import com.example.collectorapp.ui.screens.Categories.Categories
import com.example.collectorapp.ui.screens.Items.AddItemsViewModel
import com.example.collectorapp.ui.screens.Items.ItemInformation
import com.example.collectorapp.ui.screens.Items.MyItemsCard


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
        ProfilePicture(painter = painterResource(id = R.drawable.user))
        ProfileNameDetails(a)
      //  Notification()
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
    val categoryListState by addCategoryViewModel._categoryListState

    Column {
        HeadingText(value = "My Items")
        LazyRow {
            items(
                addCategoryViewModel._categoryListState.value.categoryList
            ) { category ->
                if (category != null) {
                    CategoryChipWithItems(category = category, addCategoryViewModel = addCategoryViewModel)
                } else {
                    CategoryChips(
                        category = "All",
                        onExecuteSearch = { /**/ }
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryChipWithItems(category: Categories, items: ItemInformation, addItemsViewModel: AddItemsViewModel) {
    Column {
        CategoryChips(
            category = category.categoryName,
            onExecuteSearch = { /**/ }
        )
        LazyColumn {
            items(items) { item ->
                MyItemsCard(
                    addItemsViewModel = addItemsViewModel,
                    imageUri = items.itemImage,
                    contentDescription = items.itemDescription,
                    title = items.itemDescription,
                    modifier = Modifier.fillMaxWidth()
                )
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
        .clip(CircleShape)
    ){
        Image(painter = painter,
            contentDescription = "Me",
            contentScale = ContentScale.Crop)
    }
}