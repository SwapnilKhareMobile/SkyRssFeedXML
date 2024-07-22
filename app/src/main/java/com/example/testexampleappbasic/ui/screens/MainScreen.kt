package com.example.testexampleappbasic.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.testexampleappbasic.model.Item

@Composable
fun MainScreen(modifier: Modifier = Modifier, viewModel: MainViewModel = hiltViewModel()) {
    val uiState by viewModel.mRssList.collectAsStateWithLifecycle()

    when (uiState) {
        is MainUIState.Loading -> {
            // Display a loading indicator
            ShowProgress()
        }
        is MainUIState.Error -> {
            ShowError()
        }
        is MainUIState.Success -> {
            val items = (uiState as MainUIState.Success).data

            LazyColumn(modifier = modifier) {
                items(items) { item ->
                    ItemCard(item)
                }
            }
        }
        MainUIState.Empty -> {
            // Handle empty state if needed
        }
    }
}

@Composable
fun ItemCard(item: Item) {
    val context = LocalContext.current

    Card( shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                item.link?.let { url ->
                    // Open the link in a browser
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent)
                }
            }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            item.mediaThumbnail?.url?.let { imageUrl ->
                Image(
                    painter =  rememberAsyncImagePainter(model = imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = item.title ?: "No Title", style = MaterialTheme.typography.headlineMedium)
        }
    }
}
@Composable
fun ShowProgress() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(Modifier.testTag("progress_indicator"))
    }
}

@Composable
fun ShowError() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Display an error message
        Text(text = "Error loading data")
    }
}