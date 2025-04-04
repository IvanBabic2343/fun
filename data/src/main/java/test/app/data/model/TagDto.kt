package test.app.data.model

data class TagDto(
    val name: String,
    val commit: CommitInfo
)

data class CommitInfo(
    val sha: String
)
