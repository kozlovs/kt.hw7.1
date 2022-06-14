object WallService {
    private var posts = emptyArray<Post>()
    private var reports = emptyArray<ReportComment>()
    private var lastId = 0L

    fun createComment(postId: Long, comment: Comment): Comment {
        for (post in posts) {
            if (post.id == postId) {
                post.comments.add(comment)
                return post.comments.last()
            }
        }
        throw PostNotFoundException()
    }

    fun createReport(postId: Long, commentId: Long, report: ReportComment) : ReportComment {
        if (report.reason < 0 || report.reason > 6) throw UnknownReasonException()
        for (post in posts) {
            if (post.id == postId) {
                for (comment in post.comments) {
                    if (comment.id == commentId) {
                        reports += report
                        return reports.last()
                    }
                }
                throw CommentNotFoundException()
            }
        }
        throw PostNotFoundException()
    }

    fun add(post: Post): Post {
        posts += post.copy(id = getId())
        return posts.last()
    }

    fun update(post: Post): Boolean {
        for ((index, thisPost) in posts.withIndex()) {
            if (thisPost.id == post.id) {
                posts[index] = thisPost.copy(
                    fromId = thisPost.fromId,
                    createdBy = thisPost.createdBy,
                    replyOwnerId = thisPost.replyOwnerId,
                    replyPostId = thisPost.replyPostId,
                    signerId = thisPost.signerId,
                    attachments = thisPost.attachments,
                    original = thisPost.original,
                    postSource = thisPost.postSource,
                    geo = thisPost.geo,
                    copyHistory = thisPost.copyHistory,
                    text = thisPost.text,
                    friendsOnly = thisPost.friendsOnly,
                    comments = thisPost.comments,
                    likes = thisPost.likes,
                    reposts = thisPost.reposts,
                    postType = thisPost.postType,
                    canPin = thisPost.canPin,
                    canDelete = thisPost.canDelete,
                    canEdit = thisPost.canEdit,
                    isPinned = thisPost.isPinned,
                    markedAsAds = thisPost.markedAsAds,
                    isFavorite = thisPost.isFavorite,
                    id = getId()
                )
                return true
            }
        }
        return false
    }

    private fun getId(): Long {
        lastId += 1
        return lastId
    }
}