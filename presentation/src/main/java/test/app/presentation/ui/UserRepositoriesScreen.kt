package test.app.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import org.koin.compose.getKoin
import test.app.domain.model.Repo
import test.app.domain.model.User
import test.app.presentation.navigation.NavigationRoute
import test.app.presentation.utils.UIState
import test.app.presentation.viewmodel.UserRepositoriesViewModel
import test.app.presentation.viewmodel.UserViewModel

@Composable
fun UserRepositoriesScreen(
    userRepositoriesViewModel: UserRepositoriesViewModel = getKoin().get(),
    userViewModel: UserViewModel = getKoin().get(),
    navController: NavHostController
) {
    val repositoriesState by remember { userRepositoriesViewModel.repositoriesState }.collectAsState()
    val userState by remember { userViewModel.userState }.collectAsState()

    LaunchedEffect(key1 = true) {
        userViewModel.getUser()
        userRepositoriesViewModel.fetchRepositories()
    }

    val isLoading = when {
        userState is UIState.Loading -> true
        repositoriesState is UIState.Loading -> true
        else -> false
    }

    Column(modifier = Modifier.padding(16.dp)) {

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        UserHeader(userState)

        Spacer(modifier = Modifier.height(16.dp))

        RepositoriesList(repositoriesState, navController)
    }
}

@Composable
fun UserHeader(userState: UIState<User>) {
    when (userState) {
        is UIState.Success -> {
            val user = userState.data
            Column(modifier = Modifier.padding(bottom = 16.dp)) {
                AsyncImage(
                    model = user.avatarUrl,
                    contentDescription = "User Avatar",
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "All repos by: ${user.username}", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
        }

        is UIState.Error -> {
            Text(text = "User Info:", color = Color.Gray)
            Spacer(modifier = Modifier.height(4.dp))
            val errorMessage = (userState).message
            Text(text = errorMessage, color = Color.Red, fontWeight = FontWeight.Bold)
        }

        UIState.Loading -> {}
    }
}

@Composable
fun RepositoriesList(
    repositoriesState: UIState<List<Repo>>,
    navController: NavHostController
) {
    when (repositoriesState) {
        is UIState.Loading -> {}

        is UIState.Success -> {
            val repositories = repositoriesState.data
            if (repositories.isEmpty()) {
                Text(text = "No repositories found.", color = Color.Gray)
            } else {
                LazyColumn {
                    items(repositories) { repo ->
                        RepoCard(repo, navController)
                    }
                }
            }
        }

        is UIState.Error -> {
            Text(text = "User repositories:", color = Color.Gray)
            Spacer(modifier = Modifier.height(4.dp))
            val errorMessage = (repositoriesState).message
            Text(text = errorMessage, color = Color.Red, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun RepoCard(repo: Repo, navController: NavHostController) {
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth()
            .clickable {
                navController.navigate(NavigationRoute.RepoDetails.createRoute(repo.name))
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row {
                Text(text = "Repo name:", color = Color.Gray)
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = repo.name, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Text(text = "Number of open issues:", color = Color.Gray)
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = repo.openIssuesCount.toString(), fontWeight = FontWeight.Bold)
            }
        }
    }
}
