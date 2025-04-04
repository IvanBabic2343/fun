package test.app.data.model


data class RepoDto(
    val name: String,
    val forks: Int,
    val watchers: Int,
    val open_issues_count: Int,
    val owner: UserDto
)
