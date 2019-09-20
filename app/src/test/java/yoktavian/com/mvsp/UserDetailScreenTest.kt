package yoktavian.com.mvsp

import io.mockk.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import yoktavian.com.mvsp.data.User
import yoktavian.com.mvsp.data.source.UserRepository
import yoktavian.com.mvsp.screen.user.UserDetailScreen

/**
 * Created by YudaOktavian on 17-Aug-2019
 */
@RunWith(JUnit4::class)
class UserDetailScreenTest :
    BaseUnitTest<UserDetailScreen, UserDetailScreen.State, UserDetailScreen.Presenter>() {

    private val mockedUser = spyk(User("Tes", "Solo", 123))
    private val mockedDefaultUser: User = mockk(relaxed = true)
    private val userRepository = spyk(UserRepository())

    @Before
    fun init() {
        view = spyk()
        state = spyk()
        presenter = spyk(UserDetailScreen.Presenter(state, view, userRepository))
    }

    @Test
    fun `when fetch user detail, render loading should be called and must getting data from repo`() {
        // given
        every { presenter.repository.getUserData(captureLambda()) } answers {
            lambda<(User) -> Unit>().invoke(mockedUser)
        }
        every { view.renderLoading() } returns Unit
        // when
        presenter.fetchUserDetail()
        print(mockedUser.name)
        print(mockedUser.address)
        print(mockedUser.phone)
        // then
        with(state) {
            Assert.assertEquals(userData, mockedUser)
            Assert.assertEquals(isLoading, false)
        }
        verify(exactly = 2) {
            view.renderLoading()
        }
    }
}