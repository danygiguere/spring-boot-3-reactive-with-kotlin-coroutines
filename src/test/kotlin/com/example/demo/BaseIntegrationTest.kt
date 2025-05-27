import com.example.demo.seeders.DataSeeder
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class BaseIntegrationTest {

    @Autowired
    lateinit var dataSeeder: DataSeeder

    @BeforeAll
    fun globalSetUp() {
        runBlocking {
            dataSeeder.recreateAndSeedDb()
        }
    }
}