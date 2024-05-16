import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.collectorapp.R

@Composable
fun CameraScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        val context = LocalContext.current
        var bitmap by remember { mutableStateOf<Bitmap?>(null) }
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicturePreview()
        ) { result ->
            bitmap = result
        }

        val launchImage = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->
            bitmap = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, uri!!)
                ImageDecoder.decodeBitmap(source)
            }
        }


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 100.dp)
        ) {
            bitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }
        var showDialog by remember { mutableStateOf(false) }

        if (showDialog) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.Gray)
                    ) {
                        Column(modifier = Modifier.padding(start = 60.dp)) {
                            Image(
                                painter = painterResource(id = R.drawable.camera),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clickable {
                                        launcher.launch()
                                        showDialog = false
                                    }
                            )
                            Text(
                                text = "Camera",
                                color = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.padding(30.dp))
                        Column {
                            Image(
                                painter = painterResource(id = R.drawable.camera),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clickable {
                                        launchImage.launch("image/*")
                                        showDialog = false
                                    }
                            )
                            Text(
                                text = "Gallery",
                                color = Color.White
                            )
                        }
                        Column(modifier = Modifier.padding(start = 50.dp, bottom = 80.dp)) {
                            Text(
                                text = "X",
                                color = Color.White,
                                modifier = Modifier.clickable { showDialog = false }
                            )
                        }
                    }
                }
            }
        }

        Image(
            painter = painterResource(id = R.drawable.camera),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .clip(CircleShape)
                .background(Color.Transparent)
                .size(50.dp)
                .padding(10.dp)
                .clickable { showDialog = true }
        )
    }
}