package yoktavian.com.mvsp

import io.mockk.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import yoktavian.com.mvsp.data.User
import yoktavian.com.mvsp.data.Wallet
import yoktavian.com.mvsp.data.source.UserRepository
import yoktavian.com.mvsp.screen.user.UserDetailScreen

/**
 * Created by YudaOktavian on 17-Aug-2019
 */
@RunWith(JUnit4::class)
class UserDetailScreenTest :
    BaseUnitTest<UserDetailScreen, UserDetailScreen.State, UserDetailScreen.Presenter>() {

    private val mockedUser = spyk(User("Tes", "Solo", 123))
    private val userRepository = spyk(UserRepository())

    @Before
    fun init() {
        view = spyk()
        state = spyk()
        presenter = spyk(UserDetailScreen.Presenter(state, view, userRepository))
        // presenter = mockk()
    }

    @Test
    fun `test stubbing mockk & spyk`() {
        /**
         * Tanpa stubbing, method yang di panggil
         * tidak akan tahu harus melakukan apa.
         */
        // setiap pemanggilan presenter.getBalance() akan di returns 0
        // every { presenter.getBalance() } returns 0
        print(presenter.getBalance())
        presenter.getBalance()
    }

    @Test
    fun `when fetch user detail, render loading should be called and getting data from repo`() {
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

    @Test
    fun `render balance should be called after success fetching balance from repo`() {
        // given
        every { view.renderLoading() } returns Unit
        every { view.renderBalance() } returns Unit
        every { userRepository.getBalance(captureLambda()) } answers {
            lambda<(Wallet) -> Unit>().invoke(Wallet(1000))
        }
        // when
        presenter.fetchBalance()
        // then
        verify {
            view.renderBalance()
        }
    }

    @Test
    fun `render balance shouldn't be called when balance 0`() {
        // given
        every { view.renderLoading() } returns Unit
        every { view.renderBalance() } returns Unit
        every { userRepository.getBalance(captureLambda()) } answers {
            lambda<(Wallet) -> Unit>().invoke(Wallet(0))
        }
        // when
        presenter.fetchBalance()
        // then
        verify(exactly = 0) {
            view.renderBalance()
        }
    }

    @Test
    fun `render loading should be called at most 2 times`() {
        // given
        every { view.renderLoading() } returns Unit
        every { view.renderBalance() } returns Unit
        every { userRepository.getBalance(captureLambda()) } answers {
            lambda<(Wallet) -> Unit>().invoke(Wallet(100))
        }
        // when
        presenter.fetchBalance()
        // then
        verify(atMost = 2) {
            view.renderLoading()
        }
    }

    @Test
    fun `sample using sequence`() {
        // given
        every { view.renderLoading() } returns Unit
        every { view.renderBalance() } returns Unit
        every { userRepository.getBalance(captureLambda()) } answers {
            lambda<(Wallet) -> Unit>().invoke(Wallet(1000))
        }
        // when
        presenter.fetchBalance()
        // then
        verifySequence {
            view.renderLoading()
            view.renderBalance()
            view.renderLoading()
        }
    }

    @Test
    fun `render loading should be called after render balance`() {
        // given
        every { view.renderLoading() } returns Unit
        every { view.renderBalance() } returns Unit
        every { userRepository.getBalance(captureLambda()) } answers {
            lambda<(Wallet) -> Unit>().invoke(Wallet(1000))
        }
        // when
        presenter.fetchBalance()
        // then
        verifyOrder {
            view.renderLoading()
            view.renderBalance()
            view.renderLoading()
        }
    }

    @Test
    fun `confirm verified`() {
        // given
        every { view.renderLoading() } returns Unit
        every { view.renderBalance() } returns Unit
        every { userRepository.getBalance(captureLambda()) } answers {
            lambda<(Wallet) -> Unit>().invoke(Wallet(1000))
        }
        // when
        presenter.fetchBalance()
        // then
        verifyAll {
            view.renderLoading()
            view.renderBalance()
        }
        confirmVerified(view)
    }
}