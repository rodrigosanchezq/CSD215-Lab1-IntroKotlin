import org.junit.jupiter.api.Test
import kotlin.test.BeforeTest
import kotlin.test.assertNotEquals
import kotlin.test.assertEquals

data class Task(val name: String, val completed: Boolean)
val listToDo = listOf(
    Task("Task 1", false),
    Task("Task 2", false),
    Task("Task 3", false),
    Task("Task 4", false),
    Task("Task 5", false)
)



class AppTest {
    @Test
    fun testToDoList() {

    }

    @Test
    fun saveFile() {
    }

    @Test
    fun showList() {
    }

    @Test
    fun testMarkTaskAsComplete() {
        val task = Task("Task 1", true)
        assertEquals(task, markTaskAsComplete(listToDo,0)[0])
        assertNotEquals(task, markTaskAsComplete(listToDo,0)[0])
    }

    @Test
    fun testAddNewTask() {
        val newTask= Task("Task 6", false)
        assertEquals(newTask, addNewTask(newTask, listToDo)[5])
        assertNotEquals(newTask, addNewTask(newTask, listToDo)[4])
    }

    @Test
    fun defineNewTask() {

    }

    @Test
    fun removeCompletedTask() {
    }

}