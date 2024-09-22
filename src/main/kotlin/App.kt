import java.io.File

data class Task(val description: String, var isComplete: Boolean)

//var toDo: List<Task> = listOf(
//    Task("Do the dishes", false),
//    Task("Walk the dog", true),
//    Task("Buy groceries", false),
//    Task("Cook dinner", true),
//    Task("Do the laundry", false)
//)
val file = File("todo.txt")

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
var toDo = loadFile()

fun saveFile() {
    file.writeText("")
    toDo.forEach {
        file.appendText("${it.description},${it.isComplete}\n")
    }
}

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

fun markTaskAsComplete() {
    print("Enter the task number to mark as complete: ")
    val task = toDo[readLine().toString().toInt()-1]
    val index = toDo.indexOf(task)
    if (index != -1) {
        toDo[index].isComplete = true
    }
}

fun addTask() {
    val workList = toDo.toMutableList()
    println("Enter the task description: ")
    print(">: ")
    val description = readLine().toString()
    var newTask = Task(description, false)
    workList.add(newTask)
    toDo = workList.toList()
}

fun removeCompletedTask() {
    val workList = toDo.toMutableList()
    for (task in toDo) {
        if (task.isComplete) {
            workList.remove(task)
        }
    }
    toDo = workList.toList()
    println("Completed tasks removed.")
}

fun displayMenu() {
    println("\nWhat would you like to do?")
    println("a - Add a task")
    println("c - Mark a task as complete")
    println("r - Remove completed tasks")
    println("q - Exit")
    print(">: ")
}

fun greetingUser() {
    println("\nWelcome to the Kotlin TO-DO List App!")
    println("_____________________________________")
    println ("Here is your current list of tasks:")
    showList()
}

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
