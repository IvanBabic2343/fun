package test.app.data.model

import test.app.domain.model.Repo
import test.app.domain.model.Tag
import test.app.domain.model.User


fun UserDto.toDomainModel() = User(
    username = this.login,
    avatarUrl = this.avatar_url
)

fun RepoDto.toDomainModel() = Repo(
    name = this.name,
    forks = this.forks,
    watchers = this.watchers,
    openIssuesCount = this.open_issues_count,
    owner = this.owner.toDomainModel()
)

fun TagDto.toDomainModel() = Tag(
    name = this.name,
    sha = this.commit.sha
)
