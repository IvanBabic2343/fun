package test.app.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import org.koin.compose.getKoin
import test.app.domain.model.Repo
import test.app.domain.model.Tag
import test.app.presentation.R
import test.app.presentation.utils.UIState
import test.app.presentation.viewmodel.UserRepoDetailsViewModel

@Composable
fun UserRepositoriesDetailsScreen(
    userRepoDetailsViewModel: UserRepoDetailsViewModel = getKoin().get(),
    repoName: String
) {
    val repoDetails by remember { userRepoDetailsViewModel.repoDetails }.collectAsState()
    val repoTags by remember { userRepoDetailsViewModel.repoTags }.collectAsState()

    LaunchedEffect(repoName) {
        userRepoDetailsViewModel.fetchRepoDetails(repoName)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when (repoDetails) {
            is UIState.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }

            is UIState.Success -> RepoDetailsHeader((repoDetails as UIState.Success<Repo?>).data)
            is UIState.Error -> Text(
                text = (repoDetails as UIState.Error).message,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        when (repoTags) {
            is UIState.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }

            is UIState.Success -> RepoTagsList((repoTags as UIState.Success<List<Tag>>).data)
            is UIState.Error -> Text(
                text = (repoTags as UIState.Error).message,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
fun RepoDetailsHeader(repo: Repo?) {
    if (repo == null) {
        Text(text = "No details available")
        return
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = repo.owner.avatarUrl),
            contentDescription = "Avatar",
            modifier = Modifier.size(64.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Repo: ${repo.name}",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "by ${repo.owner.username}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_star),
                    contentDescription = "Watchers",
                    tint = Color(0xFFFFC107),
                    modifier = Modifier
                        .size(20.dp)
                        .padding(end = 4.dp)
                )
                Text(
                    text = repo.watchers.toString(),
                    modifier = Modifier.padding(end = 16.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_fork),
                    contentDescription = "Forks",
                    tint = Color(0xFF9E9E9E),
                    modifier = Modifier
                        .size(20.dp)
                        .padding(end = 4.dp)
                )
                Text(text = repo.forks.toString())
            }
        }
    }
}

@Composable
fun RepoTagsList(tags: List<Tag>) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Tags:",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(tags) { tag ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = tag.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = tag.sha,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}
