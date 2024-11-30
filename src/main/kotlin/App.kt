import java.io.File
import kotlin.collections.forEach
import kotlin.collections.removeAll
import kotlin.collections.toMutableList
import kotlin.io.appendText
import kotlin.io.forEachLine
import kotlin.io.writeText
import kotlin.text.lowercase
import kotlin.text.split
import kotlin.text.toBoolean
import kotlin.text.toIntOrNull
import kotlin.toString

/**
 * Data class to represent a task
 * @param description The description of the task
 * @param isComplete The status of the task (true if complete, false if not)
 * @return Task object
 */

data class Task(val description: String, var isComplete: Boolean)

/**
 * loads the tasks from the file
 * @param file The file to load the tasks from
 * @return List<Task> list of Task objects
 */
fun toDoList(file: File): List<Task> {
    if (file.exists()) {
        val fileTasks = mutableListOf<Task>()
        file.forEachLine {
            val (description, isComplete) = it.split(",")
            fileTasks.add(Task(description, isComplete.toBoolean()))
        }
        return fileTasks
    }else {
        return emptyList()
    }
}

/**
 * Saves the tasks to the file
 * @param file The file to save the tasks to.
 * @param list The list of Task objects to save to the file.
 */

fun saveFile(list: List<Task>, file: File) {
    file.writeText("")
    list.forEach { task ->
        file.appendText("${task.description},${task.isComplete}\n")
    }
}

/**
 * Displays the list of tasks
 * @param list The list of Task objects to display
 */

fun showList(list: List<Task>) {
    println("\nTasks TO DO:")
    list.forEach() {
        if (it.isComplete) {
            println("${list.indexOf(it) + 1}. [COMPLETE] - ${it.description}")
        }else {
            println("${list.indexOf(it) + 1}. ${it.description}")
        }
    }
}

/**
 * Marks a task as complete in the list based in the user input.
 * @param list The list of Task objects
 * Asks the user for the number of the task to mark as complete
 * @return List<Task> updated list of Task objects
 */

fun markTaskAsComplete(list: List<Task>): List<Task> {
    print("Enter the task number to mark as complete: ")
    var workList = list.toMutableList()
    var ready = false
    while (!ready) {
        val number = readlnOrNull()?.toIntOrNull()
        if (number != null && number > 0 && number <= workList.size) {
            if (workList[number - 1].isComplete) {
                print("Task is already complete. Please try again: ")
            }else {
                workList[number - 1].isComplete = true
                ready = true
            }
        }else {
            print("Invalid task number. Please try again: ")
        }
    }
    return list
}

/**
 * Adds a new task in the list.
 * @param list The list of Task objects
 * @param task The new Task object to add to the list
 * @return List<Task> updated list of Task objects
 */

fun addNewTask(task: Task, list: List<Task>): List<Task> {
    val workList = list.toMutableList()
    workList.add(task)
    return workList
}

/**
 * Asks the user for the task description and creates a new Task object
 * @return Task object
 */

fun defineNewTask(): Task {
    println("Enter the task description:")
    print(">: ")
    val description = readLine().toString()
    return Task(description, false)
}

/**
 * Removes the completed tasks from the list
 * @param list The list of Task objects
 * @return List<Task> updated list of Task objects
 */
fun removeCompletedTask(list: List<Task>): List<Task> {
    val workList = list.toMutableList()
    workList.removeAll { it.isComplete}
    return workList
}

/**
 * Action to display the user options menu
 */

fun displayMenu() {
    println("\nWhat would you like to do?")
    println("a - Add a task")
    println("c - Mark a task as complete")
    println("r - Remove completed tasks")
    println("q - Exit")
    print(">: ")
}

/**
 * Action tod display the user greeting message and the initial list of tasks
 */
fun greetingUser() {
    println("\nWelcome to the Kotlin TO-DO List App!")
    println("_____________________________________")
    println("Here is your current list of tasks:")
}

/**
 * Main function to run the program
 */

fun main(){

    val file = File("todo.txt")

    greetingUser()
    showList(toDoList(file))

    var userInput : String?
    do {
        displayMenu()
        userInput = readLine().toString().lowercase()
        when (userInput) {
            "a" -> {
                saveFile(addNewTask(defineNewTask(), toDoList(file)), file)
            }
            "c" -> {
                saveFile(markTaskAsComplete(toDoList(file)), file)
            }
            "r" -> {
                saveFile(removeCompletedTask(toDoList(file)), file)
                println("Completed tasks removed.")
            }
            "q" -> {
                println("Goodbye!")
            }else -> {
            println("Invalid input. Please try again.")
            }
        }
        showList(toDoList(file))
    }while (userInput != "q")
}
