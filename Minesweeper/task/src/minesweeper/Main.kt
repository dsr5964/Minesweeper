package minesweeper

import kotlin.random.Random

const val rows = 9
const val cols = 9
const val mine = "X"
const val empty = "."
const val mark = "*"
const val border = "-|—————————|"
const val divider = "|"
const val numberedRow = " |123456789|"
const val markMessage = "Set/delete mine marks (x and y coordinates):"
val regex = "-?[0-9]+(\\.[0-9]+)?".toRegex()
const val numberMessage = "There is a number here!"
var numMines = 0


/**
 * Print the board with the numbers and the border/dividers
 */
fun printBoard(board: Array<Array<String>>){
    println(numberedRow)
    println(border)
    for (i in 0 until rows){
        print(i + 1)
        print(divider)
        print(board[i].joinToString(""))
        println(divider)
    }
    println(border)

}

/**
 * Check if the current x (c) and y (r) coordinates are valid positions
 * If the x coordinate or y coordinate is less than 0 or greater than the last index of the size of the board
 * these aren't valid positions so stop looking at these cells
 * Return true for all other coordinates
 */
fun isValidPos(board: Array<Array<String>>, r: Int, c: Int): Boolean {
    val last = board.size-1
    if (r < 0) {
        return false
    } else if (c < 0){
        return false
    } else if (r > last){
        return false
    } else if (c > last){
        return false
    }
    return true
}

/**
 * Go through all the cells around a given cell in order to find all the mines around a cell.
 * Loop from the y coordinate (row) from the left of the current y coordinate to the right of the current y coordinate.
 * And loop from the left of the current x coordinate (column) to the right of the current x coordinate (column)
 * This makes sure the current coordinates being looked at are valid so it doesn't go out of bounds.
 * For example if the cell is 0,0 (x,y) then you can't visit the column to the left or the row above because that would result in
 * -1,0 or 0,-1 which would be out of bounds
 * If the position is valid then check if the current x or y doesn't equal the cell with the mine since you are checking
 * all the cells around the mine.
 * the space is empty if it is that means that cell is near a mine
 * And change the empty space to a 1.
 * If it's a mine already leave the mine there and go to the next cell
 * If it isn't empty then convert the current number in the cell to an int and increment by 1 since another mine
 * was found. Change that spot to the number of mines that cell is near.
 *
 */
fun findSurroundingMines(board: Array<Array<String>>, r: Int, c: Int) {
    for (i in r - 1..r + 1) {
        for (j in c - 1..c + 1) {
            if (isValidPos(board, i, j)){
                if (i != r || j != c) {
                    if (board[i][j] == empty) {
                        board[i][j] = "1"
                    } else if (board[i][j] == mine) {
                        board[i][j] = mine
                    } else {
                        val currCount = board[i][j].toInt()
                        board[i][j] = (currCount + 1).toString()
                    }
                }
            }
        }
    }
}

/**
 * Add all the mines based on the number of mines a user inputs
 * Keep generating x and y coordinates and check if that cell has a mine already. If it doesn't then
 * check if that coordinate pair already has a mine at that cell. If it does generate a new coordinate pair
 * If that's a new mine position then add it to the mine list and increment the count of mines until
 * the specified number of mines has been added to the board
 */
fun addMines(board: Array<Array<String>>, numMines: Int, mines: MutableList<String>){
    var count = 0
    while (count < numMines){
        val x = Random.nextInt(0, 9)
        val y = Random.nextInt(0, 9)
        if (board[x][y] != mine){
            board[x][y] = mine
            if (!mines.contains("$y,$x")){
                mines.add("$y,$x")
                count += 1
            }
        }
    }
}

/**
 * Checks each cell based on an x and y coordinate pair and allows a player to check a cell based on input
 * If the current box has a digit represented by the regular expression regex. Then tell the user
 * they can't place a mark there since a number is there.
 * If the spot is empty check if there is a mine there, if so then increment the number of mines found currently
 * Either case add a mark * even if there isn't a mine there
 * Else if the user selects a cell that has a mark already remove the mark since they don't think a mine is there
 */
fun checkCell(board: Array<Array<String>>, x: Int, y: Int, minesList: MutableList<String>, cellType: String,
              resultBoard: Array<Array<String>>): Array<Array<String>>{
    if (cellType == "mine"){
        if (board[x][y] == empty){
            board[x][y] = mark
        } else if (board[x][y] == mark) {
            board[x][y] = empty
        } else {
            println(numberMessage)
        }
    } else { // user types in free command

    }
//    if (board[x][y].matches(regex)){
//        println(numberMessage)
//    } else if (board[x][y] == empty){
//        if (isMine(minesList, x, y)){
//            numMines += 1
//        }
//        board[x][y] = mark
//    } else {
//        board[x][y] = empty // else if a cell is marked (*) then unmark the cell
//        // changing it to a . symbol instead
//    }
    return board
}

fun exploreFreeCell(board: Array<Array<String>>, x: Int, y: Int, minesList: MutableList<String>,
                    resultBoard: Array<Array<String>>) {

}

/**
 * Change the mine spaces represented by 'X' to empty spaces '.' after adding all the mines
 * This way a mine isn't generated and placed in the same spot as a number/hint
 * All the mines are covered with empty spaces
 */
fun coverMines(mines: MutableList<String>, board: Array<Array<String>>) {
    for (mine in mines){
        val coords = mine.split(",")
        val y = coords[0].toInt()
        val x = coords[1].toInt()
        board[x][y] = empty
    }
}

/**
 * Checks if the current x and y coordinate has a mine there. If it does return true else false
 */
fun isMine(mines: MutableList<String>, x: Int, y: Int): Boolean{
    if (mines.contains("$y,$x")){
        return true
    }
    return false
}

/**
 * Checks if the number of mines found currently is the same as the total number of mines (based on user input)
 * at the beginning of the game.
 * If a player has found all the mines they win the game
 */
fun isWin(totalMines: Int): Boolean {
    return numMines == totalMines
}

/**
 * Plays the actual game of minesweeper
 * Prints the board and asks a user to place coo
 */
fun playGame(totalMines: Int, board: Array<Array<String>>, mines: MutableList<String>, resultBoard: Array<Array<String>>) {
    while (!isWin(totalMines)){
        printBoard(board)
        val markPosition = readln().split(" ")
        val markX = markPosition[0].toInt()
        val markY = markPosition[1].toInt()
        val cellType = markPosition[2]
        println(markMessage)
        checkCell(board, markY-1, markX-1, mines, cellType, resultBoard)

    }
    printBoard(board)
    println("Congratulations! You found all the mines!")
}


fun main() {
    var resultBoard = Array<Array<String>>(rows) { Array<String>(cols) { empty } }
    var currBoard = Array<Array<String>>(rows) { Array<String>(cols) { empty } }
    var mines = mutableListOf<String>()
    println("How many mines do you want on the field?")
    val input = readln().toInt()
    addMines(resultBoard, input, mines)

    for (mine in mines){
        val mineCoord = mine.split(",")
        val mineX = mineCoord[0].toInt()
        val mineY = mineCoord[1].toInt()
        findSurroundingMines(resultBoard, mineY, mineX)
    }
    coverMines(mines, resultBoard)
    mines.forEach {
        println(it)
    }
    playGame(input, currBoard, mines, resultBoard)
    printBoard(currBoard)
}