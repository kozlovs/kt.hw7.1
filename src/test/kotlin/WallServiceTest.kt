import org.junit.Test

import org.junit.Assert.*

class WallServiceTest {

    @Test
    fun addNoZero() {
        val post = WallService.add(Post(0L, 0L, 0L, 0L, 0L, 0L))
        assertNotEquals(0, post.id)
    }

    @Test
    fun updateExists() {
        val post = WallService.add(Post(0L, 0L, 0L, 0L, 0L, 0L))
        val exists = WallService.update(post.copy(text = "another text"))
        assert(exists)
    }

    @Test
    fun updateNotExists() {
        val post = WallService.add(Post(0L, 0L, 0L, 0L, 0L, 0L))
        val exists = WallService.update(post.copy(text = "another text", id = post.id + 1))
        assert(!exists)
    }

    @Test
    fun commentShouldNotThrow() {
        val post = WallService.add(Post(0L, 0L, 0L, 0L, 0L, 0L))
        val postId = post.id
        val comment = Comment(postId, 0, "some thread", 0, null)
        val returnComment = WallService.createComment(postId, comment)
        assertEquals(
            comment,
            returnComment
        )
    }

    @Test(expected = PostNotFoundException::class)
    fun commentShouldThrow() {
        WallService.add(Post(0L, 0L, 0L, 0L, 0L, 0L))
        val comment = Comment(0, 0, "some thread", 0, null)
        WallService.createComment(
            6,
            comment
        )
    }

    @Test
    fun reportShouldNotThrow() {
        val post = WallService.add(Post(0L, 0L, 0L, 0L, 0L, 0L))
        val postId = post.id
        val comment = Comment(postId, 0, "some thread", 0, null)
        WallService.createComment(postId, comment)
        val report = ReportComment(0, comment.id, 1)
        val returnReport = WallService.createReport(post.id, comment.id, report)
        assertEquals(
            report,
            returnReport
        )
    }

    @Test(expected = UnknownReasonException::class)
    fun reportShouldThrowUnknownReasonException() {
        val post = WallService.add(Post(0L, 0L, 0L, 0L, 0L, 0L))
        val postId = post.id
        val comment = Comment(postId, 0, "some thread", 0, null)
        WallService.createComment(postId, comment)
        val report = ReportComment(0, comment.id, -1)
        WallService.createReport(post.id, comment.id, report)
    }

    @Test(expected = PostNotFoundException::class)
    fun reportShouldThrowPostNotFoundException() {
        val post = WallService.add(Post(0L, 0L, 0L, 0L, 0L, 0L))
        val anotherPostId = post.id + 1 // создаем неправильный id
        val comment = Comment(anotherPostId, 0, "some thread", 0, null)
        WallService.createComment(anotherPostId, comment)
    }

    @Test(expected = CommentNotFoundException::class)
    fun reportShouldThrowCommentNotFoundException() {
        val post = WallService.add(Post(0L, 0L, 0L, 0L, 0L, 0L))
        val comment = Comment(post.id, 0, "some thread", 0, null)
        WallService.createComment(post.id, comment)
        val commentId = comment.id + 1 // создаем неправильный id
        val report = ReportComment(0, commentId, -1)
        WallService.createReport(post.id, commentId, report)
    }
}