package test.app.domain.model

data class Repo(
    val name: String,
    val forks: Int,
    val watchers: Int,
    val openIssuesCount: Int,
    val owner: User
)
