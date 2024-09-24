import java.io.File

/**
 * Data class to represent a task
 * @param description The description of the task
 * @param isComplete The status of the task (true if complete, false if not)
 * @return Task object
 */

data class Task(val description: String, var isComplete: Boolean)

/**
 * variable file is a File object that represents the file (todo.txt) for upload/save the tasks list.
 * variable toDo is a unmutable list of Task objects.
 */
val file = File("todo.txt")
var toDo = loadFile()

/**
 * loads the tasks from the file
 * @param file The file to load the tasks from
 * @return List<Task> list of Task objects
 */
fun loadFile(): List<Task> {
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
 * @param toDo The list of Task objects to save to the file.
 */

fun saveFile() {
    file.writeText("")
    toDo.forEach {task ->
        file.appendText("${task.description},${task.isComplete}\n")
    }
}

/**
 * Displays the list of tasks
 * @param toDo The list of Task objects to display
 */

fun showList() {
    println("\nTasks TO DO:")
    for (task in toDo) {
        if (task.isComplete) {
            println("${toDo.indexOf(task)+1}. [COMPLETE] - ${task.description}")
        }else {
            println("${toDo.indexOf(task)+1}. ${task.description}")
        }
    }
}

/**
 * Marks a task as complete in the list based in the user input.
 * @param toDo The list of Task objects
 * @param number The number of the task to mark as complete
 */

fun markTaskAsComplete() {
    print("Enter the task number to mark as complete: ")
    var ready = false
    while (!ready) {
        val number = readlnOrNull()?.toIntOrNull()
        if (number != null && number > 0 && number <= toDo.size) {
            if (toDo[number - 1].isComplete) {
                print("Task is already complete. Please try again: ")
            }else {
                toDo[number - 1].isComplete = true
                ready = true
            }
        }else {
            print("Invalid task number. Please try again: ")
        }
    }
}

/**
 * Adds a new task in the list.
 * @param toDo The list of Task objects
 */

fun addTask() {
    val workList = toDo.toMutableList()
    println("Enter the task description:")
    print(">: ")
    val description = readLine().toString()
    var newTask = Task(description, false)
    workList.add(newTask)
    toDo = workList.toList()
}

/**
 * Removes the completed tasks from the list
 * @param toDo The list of Task objects
 * @return List<Task> updated list of Task objects
 */
fun removeCompletedTask() {
    val workList = toDo.toMutableList()
    workList.removeAll { it.isComplete}
    toDo = workList.toList()
    println("Completed tasks removed.")
}

/**
 * Displays the user options menu
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
 * Displays the user greeting message and the initial list of tasks
 */
fun greetingUser() {
    println("\nWelcome to the Kotlin TO-DO List App!")
    println("_____________________________________")
    println ("Here is your current list of tasks:")
    showList()
}

/**
 * Main function to run the program
 */

fun main(){
    greetingUser()

    var userInput : String?
    do {
        displayMenu()
        userInput = readLine().toString().lowercase()
        when (userInput) {
            "a" -> {
                addTask()
            }
            "c" -> {
                markTaskAsComplete()
            }
            "r" -> {
                removeCompletedTask()
            }
            "q" -> {
                println("Goodbye!")
                saveFile()
            }else -> {
                println("Invalid input. Please try again.")
            }
        }
        showList()
    }while (userInput != "q")
}
