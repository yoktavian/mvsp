package yoktavian.com.mvsp

/**
 * Created by YudaOktavian on 17-Aug-2019
 */
abstract class BaseUnitTest<V: Any, S: Any, P: Any> {
    lateinit var view: V
    lateinit var presenter: P
    lateinit var state: S
}